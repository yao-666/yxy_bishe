package com.ruoyi.neuqer.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ValidatorUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.neuqer.domain.NeuqOrderItem;
import com.ruoyi.neuqer.domain.OrderCheckMsg;
import com.ruoyi.neuqer.domain.bo.NeuqOrderItemBo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemImportVo;
import com.ruoyi.neuqer.excel.OrderExcelListener;
import com.ruoyi.neuqer.excel.OrderExcelResult;
import com.ruoyi.neuqer.service.INeuqOrderItemService;
import com.ruoyi.neuqer.service.INeuqOrderService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单列表自定义导入
 *
 * @author yxy
 */
@Slf4j
public class NeuqerOrderItemImportListener extends AnalysisEventListener<NeuqOrderItemImportVo> implements OrderExcelListener<NeuqOrderItemImportVo> {

    private final INeuqOrderItemService orderItemService;
    private final INeuqOrderService orderService;

    /**
     * 用于保存传进来的是否更新已有数据标识
     */
    private final boolean isUpdateSupport;

    /**
     * 用于保存传进来的是否导入标志确定当前是校验还是导入
     */
    private final boolean isImport;

    /**
     * 用于更新创建者
     */
    private final String operName;

    /**
     * 每隔100条存储数据库，实际使用中可以100条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * excel 表头数据
     */
    private Map<Integer, String> headMap;

    /**
     * 插入时缓存的数据,设置初始大小为(BATCH_COUNT/0.75 + 1)提高性能
     */
    private HashMap<String, NeuqOrderItem> insertDataMap = new HashMap<>((int) (BATCH_COUNT / 0.75 + 1));

    /**
     * 更新时缓存的数据,设置初始大小为(BATCH_COUNT/0.75 + 1)提高性能
     */
    private HashMap<String, NeuqOrderItem> updateDataMap = new HashMap<>((int) (BATCH_COUNT / 0.75 + 1));

    /**
     * 存放订单列表校验时传过来的成功列表号
     * OrderCheckMsg类存放对于的订单编号和订单导入成功的信息。
     */
    private final HashMap<String, OrderCheckMsg> successMsg;
    private List<OrderCheckMsg> successList;
    private final List<OrderCheckMsg> failureMsg;

    //构造方法
    public NeuqerOrderItemImportListener(Boolean isUpdateSupport, Boolean isImport,
                                         HashMap<String, OrderCheckMsg> successMsg, List<OrderCheckMsg> failureMsg) {
        this.isUpdateSupport = isUpdateSupport;
        this.operName = LoginHelper.getUsername();
        this.orderItemService = SpringUtils.getBean(INeuqOrderItemService.class);
        this.orderService = SpringUtils.getBean(INeuqOrderService.class);
        this.isImport = isImport;
        this.successMsg = successMsg;
        this.failureMsg = failureMsg;
        this.successList = new ArrayList<>(this.successMsg.size());
    }

    /**
     * 读取表头数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
    }

    @Override
    public void invoke(NeuqOrderItemImportVo orderItemImportVo, AnalysisContext context) {
        if (orderItemImportVo.getOrderSn() != null) {
            NeuqOrderItemBo dborderItemBo = this.orderItemService.selectByVo(orderItemImportVo);
            NeuqOrderItemBo orderItemBo = BeanUtil.toBean(orderItemImportVo, NeuqOrderItemBo.class);
            try {
                if (ObjectUtil.isNotNull(successMsg.get(orderItemBo.getOrderSn()))) {
                    //如果该订单明细的订单号存在
                    //验证订单列表对象值是否合法
                    orderItemCheck(orderItemBo);
                    //验证是否导入的数据中是否存在相同的数据，
                    if (!(orderItemBo.equals(insertDataMap.get(getMapKey(orderItemBo))) ||
                        orderItemBo.equals(updateDataMap.get(getMapKey(orderItemBo))))) {
                        //如果导入的数据中不存在相同的订单明细
                        if (ObjectUtil.isNull(dborderItemBo)) {
                            setMsgCount(successMsg.get(orderItemBo.getOrderSn()));
                            // 如果数据库中存在这个订单明细
                            if (isImport) {
                                //增加需要插入的内容
                                //设置创建者字段数组为当前登录的用户
                                orderItemBo.setCreateBy(operName);
                                //将当前行的数据保存到列表中
                                insertDataMap.put(getMapKey(orderItemBo), BeanUtil.toBean(orderItemBo, NeuqOrderItem.class));
                                //将当前订单号存入orderSnMap中，方便订单明细校验使用
                                //增加成功信息
                                //successMsg.add(new OrderCheckMsg("订单列表",
                                //    "订单编号：" + neuqOrderBo.getOrderSn(), "导入成功"));
                                if (insertDataMap.size() >= BATCH_COUNT) {
                                    //如果list中数据达到BAT_COUNT的数量，则批量保存，防止内存溢出
                                    doSave(true);
                                    insertDataMap.clear();
                                }
                            }
                        } else if (isUpdateSupport) {
                            //如果选择了更新已有数据，则执行以下操作
                            setMsgCount(successMsg.get(orderItemBo.getOrderSn()));
                            if (isImport) {
                                //如果用户点击了导入订单明细则导入到数据库
                                //将原来记录的id赋值给新对象，指定更新那一条记录
                                orderItemBo.setId(dborderItemBo.getId());
                                //将当前行的数据保存到列表中
                                updateDataMap.put(getMapKey(orderItemBo), BeanUtil.toBean(orderItemBo, NeuqOrderItem.class));
                                if (updateDataMap.size() >= BATCH_COUNT) {
                                    //如果list中数据达到BAT_COUNT的数量，则批量保存，防止内存溢出
                                    doSave(false);
                                    updateDataMap.clear();
                                }
                            }
                        } else {
                            String locationMsg = StringUtils.format("第{}行",
                                context.readRowHolder().getRowIndex() + 1);
                            failureMsg.add(new OrderCheckMsg(orderItemBo.getOrderSn(), context.readSheetHolder().getSheetName(), locationMsg, "数据库中已有该订单", null));
                            if (log.isDebugEnabled()) {
                                log.error(context.readSheetHolder().getSheetName() + locationMsg + "数据库中已有该订单");
                            }
                        }
                    } else {
                        //如果导入的数据中存在相同的数据，则不导入，给出错误提示。
                        String locationMsg = StrUtil.format("第{}行",
                            context.readRowHolder().getRowIndex() + 1);
                        failureMsg.add(new OrderCheckMsg(orderItemBo.getOrderSn(),
                            context.readSheetHolder().getSheetName(), locationMsg, "重复的订单明细", null));
                    }
                } else {
                    //如果导入的订单明细的订单编号没有找到对应的订单编号，则不导入，给出错误提示。
                    String locationMsg = StrUtil.format("第{}行",
                        context.readRowHolder().getRowIndex() + 1);
                    failureMsg.add(new OrderCheckMsg(orderItemBo.getOrderSn(),
                        context.readSheetHolder().getSheetName(), locationMsg, "未找到对应的订单编号的订单", null));
                }

            } catch (Exception e) {
                if (e instanceof ConstraintViolationException) {
                    ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
                    Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
                    String constraintViolationsMsg = constraintViolations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                    String locationMsg = StrUtil.format("第{}行",
                        context.readRowHolder().getRowIndex() + 1);
                    failureMsg.add(new OrderCheckMsg(orderItemBo.getOrderSn(), context.readSheetHolder().getSheetName(), locationMsg,
                        "数据校验异常：" + constraintViolationsMsg, null));
                    if (log.isDebugEnabled()) {
                        log.error(context.readSheetHolder().getSheetName() + locationMsg + "数据校验异常：" + constraintViolationsMsg);
                    }
                } else {
                    String locationMsg = StrUtil.format("第{}行",
                        context.readRowHolder().getRowIndex() + 1);
                    failureMsg.add(new OrderCheckMsg(orderItemBo.getOrderSn(), context.readSheetHolder().getSheetName(), locationMsg, "未知错误，导入失败", null));
                    log.error(context.readSheetHolder().getSheetName() + locationMsg + "导入失败");
                }
            }
        } else {
            String locationmsg = "第" + (context.readRowHolder().getRowIndex() + 1) + "行";
            failureMsg.add(new OrderCheckMsg(null, context.readSheetHolder().getSheetName(), locationmsg, "订单编号为空，导入失败", null));
            log.error(context.readSheetHolder().getSheetName() + locationmsg + "订单编号为空，导入失败");
        }
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            String locationMsg = StringUtils.format("第{}行第{}列表头{}", excelDataConvertException.getRowIndex(),
                excelDataConvertException.getColumnIndex(), headMap.get(excelDataConvertException.getColumnIndex()));
            String errMessages = StringUtils.format("数据：{}解析错误", excelDataConvertException.getCellData());
            failureMsg.add(new OrderCheckMsg(null, context.readSheetHolder().getSheetName(), locationMsg, errMessages, null));
            if (log.isDebugEnabled()) {
                log.error("订单明细表" + locationMsg + errMessages);
            }
        } else {
            String locationMsg = StringUtils.format("第{}行", context.readRowHolder().getRowIndex());
            failureMsg.add(new OrderCheckMsg(null, context.readSheetHolder().getSheetName(), locationMsg, "发生错误", null));
            if (log.isDebugEnabled()) {
                log.error(context.readSheetHolder().getSheetName() + locationMsg + "发生错误");
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (isImport) {
            if (insertDataMap.size() != 0) {
                doSave(true);
                insertDataMap.clear();
            }
            if (updateDataMap.size() != 0) {
                doSave(false);
                updateDataMap.clear();
            }
            log.info("sheet2所有数据导入数据库完成！");
        }
        List<OrderCheckMsg> orderCheckList = new ArrayList<>(successMsg.values());
        List<String> orderSnList = new ArrayList<>();
        //遍历保存导入成功信息的订单列表HashMap，生成成功信息列表
        for (OrderCheckMsg orderCheckMsg : orderCheckList) {
            if (orderCheckMsg.getCount() > 0) {
                //如果对应的订单编号下有订单明细，则保存
                Integer count = new Integer(orderCheckMsg.getErrLocation().substring(1, orderCheckMsg.getErrLocation().length() - 1));
                orderCheckMsg.setCount(count);
                successList.add(orderCheckMsg);
            } else {
                //    如果对应订单编号下没有订单明细，则删除对应的订单记录
                orderSnList.add(orderCheckMsg.getOrderSn());
                failureMsg.add(new OrderCheckMsg(orderCheckMsg.getOrderSn(), context.readSheetHolder().getSheetName(),
                    "", "该订单没有对应的订单明细", null));
            }
        }
        //删除对应的订单记录
        if (isImport) {
            if (orderSnList.size() > 0) {
                orderService.deleteEmptyOrder(orderSnList);
            }
        }
        successList.sort(new Comparator<OrderCheckMsg>() {
            @Override
            public int compare(OrderCheckMsg o1, OrderCheckMsg o2) {
                return o1.getCount()-o2.getCount();
            }
        });
    }

    //订单excel导入结果对象
    @Override
    public OrderExcelResult getOrderExcelResult() {
        return new OrderExcelResult() {

            @Override
            public List<OrderCheckMsg> getFailMsgList() {
                return failureMsg;
            }

            @Override
            public HashMap<String, OrderCheckMsg> getAnalysis() {
                return null;
            }

            @Override
            public List<OrderCheckMsg> getSuccessList() {
                return successList;
            }
        };
    }

    private final void doSave(boolean isInsert) {
        if (isInsert) {
            log.info("sheet2{}条数据，开始存储到数据库！", insertDataMap.size());
            try {
                List<NeuqOrderItem> insertDataList = new ArrayList(insertDataMap.values());
                orderItemService.saveBatch(insertDataList);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new ServiceException("系统出错，无法导入订单明细表，请联系管理员");
            }
            log.info("sheet2{}条数据，存储到数据库成功！", insertDataMap.size());
        } else {
            log.info("sheet2{}条数据，开始更新到数据库！", updateDataMap.size());
            try {
                List<NeuqOrderItem> updateDataList = new ArrayList(updateDataMap.values());
                orderItemService.updateBatch(updateDataList);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new ServiceException("系统出错，无法导入订单明细表，请联系管理员");
            }
            log.info("sheet2{}条数据，更新到数据库成功！", updateDataMap.size());
        }
    }

    /**
     * 校验导入的订单明细数据
     *
     * @author yxy
     * @Date: 2022/4/27
     **/
    private final void orderItemCheck(NeuqOrderItemBo neuqOrderItemBo) {
        try {
            ValidatorUtils.validate(neuqOrderItemBo, AddGroup.class);
        } catch (ConstraintViolationException e) {
            throw e;
        }

        if (neuqOrderItemBo.getSingleNumber() > neuqOrderItemBo.getTotalQuantity()) {
            throw new ConstraintViolationException("订单明细的单次数量不能大于总数量", null);
        }
        if (ObjectUtil.isNull(neuqOrderItemBo.getTakeNumber())) {
            neuqOrderItemBo.setTakeNumber(0L);
        } else {
            if (neuqOrderItemBo.getTakeNumber() > neuqOrderItemBo.getTotalQuantity()) {
                throw new ConstraintViolationException("订单明细的已取数量不能大于总数量", null);
            }
        }
        BigDecimal amount = new BigDecimal(neuqOrderItemBo.getAmount().toString());
        BigDecimal subtotal = neuqOrderItemBo.getUnitPrice().multiply(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (subtotal.compareTo(neuqOrderItemBo.getSubtotal()) != 0) {
            throw new ConstraintViolationException("订单明细的单价*数量不等于小计", null);
        }
    }

    /**
     * 设置insertDataMap和updataDataMap的键值，用于查找是否存在相同的订单明细。
     *
     * @author yxy
     * @Date: 2022/4/27
     **/
    private final String getMapKey(NeuqOrderItemBo neuqOrderItemBo) {
        String mapKey = neuqOrderItemBo.getOrderSn() + neuqOrderItemBo.getProductName() + neuqOrderItemBo.getClassName()
            + neuqOrderItemBo.getShoppingOptions();
        return mapKey;
    }

    /**
     * 设置successMsg内的订单明细数量。
     *
     * @author yxy
     * @Date: 2022/4/27
     **/
    private final void setMsgCount(OrderCheckMsg orderCheckMsg) {
        Integer count = orderCheckMsg.getCount();
        count++;
        orderCheckMsg.setCount(count);
    }
}
