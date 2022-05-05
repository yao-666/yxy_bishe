package com.ruoyi.neuqer.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ValidatorUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.neuqer.domain.NeuqOrder;
import com.ruoyi.neuqer.domain.OrderCheckMsg;
import com.ruoyi.neuqer.domain.bo.NeuqOrderBo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderImportVo;
import com.ruoyi.neuqer.excel.OrderExcelListener;
import com.ruoyi.neuqer.excel.OrderExcelResult;
import com.ruoyi.neuqer.service.INeuqOrderService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单列表自定义导入
 *
 * @author yxy
 */
@Slf4j
public class NeuqerOrderImportListener extends AnalysisEventListener<NeuqOrderImportVo> implements OrderExcelListener<NeuqOrderImportVo> {

    private final INeuqOrderService neuqOrderService;

    //用于保存传进来的是否更新已有数据标识
    private final Boolean isUpdateSupport;

    //用于保存传进来的是否导入标志确定当前是校验还是导入
    private final Boolean isImport;

    //用于更新创建者
    private final String operName;

    /**
     * excel 表头数据
     */
    private Map<Integer, String> headMap;

    //每隔100条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
    private static final int BATCH_COUNT = 1000;

    /**
     * 插入时缓存的数据
     */
    private List<NeuqOrder> insertDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 更新时缓存的数据
     */
    private List<NeuqOrder> updateDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 存放成功信息
     * OrderCheckMsg类存放对于的订单编号和订单导入成功的信息。
     */
    private final HashMap<String,OrderCheckMsg> successMsg = new HashMap<>();
    /**
     * 存放错误信息
     * OrderCheckMsg类存放对于的订单编号和订单导入成功的信息。
     */
    private final List<OrderCheckMsg> failureMsg = new ArrayList<>();

    //构造方法
    public NeuqerOrderImportListener(Boolean isUpdateSupport, Boolean isImport) {
        this.isUpdateSupport = isUpdateSupport;
        this.operName = LoginHelper.getUsername();
        this.neuqOrderService = SpringUtils.getBean(INeuqOrderService.class);
        this.isImport = isImport;
    }

    /**
     * 读取表头数据
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
    }

    @Override
    public void invoke(NeuqOrderImportVo neuqOrderImportVo, AnalysisContext context) {
        if (neuqOrderImportVo.getOrderSn() != null) {
            NeuqOrderBo neuqOrderBo = BeanUtil.toBean(neuqOrderImportVo, NeuqOrderBo.class);
            try {
                //验证订单列表对象值是否合法
                ValidatorUtils.validate(neuqOrderBo, AddGroup.class);
                setAddress(neuqOrderBo);
                if (ObjectUtil.isNull(successMsg.get(neuqOrderBo.getOrderSn()))) {
                    //如果导入的数据中没有存在相同的数据
                    //从数据库查询是否有相同的订单
                    NeuqOrderBo dbNeuqOrderBo = this.neuqOrderService.selectOrderByOrderSn(neuqOrderBo.getOrderSn());

                    if (ObjectUtil.isNull(dbNeuqOrderBo)) {
                        // 如果数据库不存在这个订单
                        //增加成功信息
                        successMsg.put(neuqOrderBo.getOrderSn(),new OrderCheckMsg(neuqOrderBo.getOrderSn(),context.readSheetHolder().getSheetName(),
                            "第" + (context.readRowHolder().getRowIndex() +1) + "行","导入成功",0));
                        if (isImport) {
                            //增加需要插入的内容
                            //设置创建者字段数组为当前登录的用户
                            neuqOrderBo.setCreateBy(operName);
                            //将当前行的数据保存到列表中
                            insertDataList.add(BeanUtil.toBean(neuqOrderBo, NeuqOrder.class));
                            //将当前订单号存入orderSnMap中，方便订单明细校验使用
                            if(insertDataList.size() >= BATCH_COUNT){
                                doSave(true);
                                insertDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                            }
                        }
                    } else if (isUpdateSupport) {
                        //如果数据库中存在这个订单，且用户选择更新已有订单
                        successMsg.put(neuqOrderBo.getOrderSn(),new OrderCheckMsg(neuqOrderBo.getOrderSn(), context.readSheetHolder().getSheetName(),
                            "第" + (context.readRowHolder().getRowIndex() +1) + "行", "更新成功",0));
                        if(isImport){
                            //如果选择了更新已有数据，则执行以下操作
                            //将原来记录的id赋值给新对象，指定更新那一条记录
                            neuqOrderBo.setId(dbNeuqOrderBo.getId());
                            neuqOrderBo.setUpdateBy(operName);
                            //将当前行的数据保存到列表中
                            updateDataList.add(BeanUtil.toBean(neuqOrderBo, NeuqOrder.class));
                            if(updateDataList.size() >= BATCH_COUNT){
                                doSave(false);
                                updateDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                            }
                        }
                    } else {
                        //如果数据库中存在相同的订单，且不更新已有订单
                        String locationMsg = StringUtils.format("第{}行",
                            context.readRowHolder().getRowIndex() + 1);
                        failureMsg.add(new OrderCheckMsg(neuqOrderBo.getOrderSn(),context.readSheetHolder().getSheetName(), locationMsg, "数据库中以存在该订单",null));
                        if (log.isDebugEnabled()) {
                            log.error(context.readSheetHolder().getSheetName()+locationMsg+"该订单数据库中已存在,无法保存，");
                        }
                    }
                } else {
                    //如果导入的数据中存在相同的数据，则不导入，给出错误提示。
                    String locationMsg = StrUtil.format("第{}行",
                        context.readRowHolder().getRowIndex() + 1);
                    failureMsg.add(new OrderCheckMsg(neuqOrderBo.getOrderSn(),
                        context.readSheetHolder().getSheetName(), locationMsg, "该订单与其他订单重复",null));
                }
            } catch (Exception e) {
                if (e instanceof ExcelDataConvertException) {
                    // 如果是某一个单元格的转换异常 能获取到具体行号
                    ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) e;
                    Integer rowIndex = excelDataConvertException.getRowIndex();
                    Integer columnIndex = excelDataConvertException.getColumnIndex();
                    String locationMsg = StrUtil.format("第{}行-第{}列-表头{}",
                        rowIndex + 1, columnIndex + 1, headMap.get(columnIndex));
                    failureMsg.add(new OrderCheckMsg(neuqOrderBo.getOrderSn(),
                        context.readSheetHolder().getSheetName(), locationMsg, "数据解析错误",null));
                    if (log.isDebugEnabled()) {
                        log.error(context.readSheetHolder().getSheetName()+ locationMsg + "数据解析错误");
                    }
                } else if (e instanceof ConstraintViolationException) {
                    ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
                    Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
                    String constraintViolationsMsg = constraintViolations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                    String locationMsg = StrUtil.format("第{}行", context.readRowHolder().getRowIndex() + 1);
                    failureMsg.add(new OrderCheckMsg(neuqOrderBo.getOrderSn(),
                        context.readSheetHolder().getSheetName(), locationMsg,
                        "数据校验异常：" + constraintViolationsMsg,null));
                    if (log.isDebugEnabled()) {
                        log.error(context.readSheetHolder().getSheetName() + locationMsg + "数据校验异常：" + constraintViolationsMsg);
                    }
                } else {
                    String locationMsg = StrUtil.format("第{}行",
                        context.readRowHolder().getRowIndex() + 1);
                    failureMsg.add(new OrderCheckMsg(neuqOrderBo.getOrderSn(),
                        context.readSheetHolder().getSheetName(), locationMsg, e.getMessage(),null));
                    log.error(context.readSheetHolder().getSheetName()+locationMsg+"导入失败");
                }
            }
        } else {
            String locationmsg = "第" + (context.readRowHolder().getRowIndex() + 1) + "行订单编号为空";
            failureMsg.add(new OrderCheckMsg(null, context.readSheetHolder().getSheetName(), locationmsg, "订单编号为空，导入失败",null));
            log.error(context.readSheetHolder().getSheetName()+locationmsg+"订单编号为空，导入失败");
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
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
           String locationMsg = StringUtils.format("第{}行第{}列表头{}", excelDataConvertException.getRowIndex(),
                excelDataConvertException.getColumnIndex(),headMap.get(excelDataConvertException.getColumnIndex()));
           String errMessages = StringUtils.format("数据：{}解析错误",excelDataConvertException.getCellData());
            failureMsg.add(new OrderCheckMsg(null, context.readSheetHolder().getSheetName(), locationMsg, errMessages,null));
            if (log.isDebugEnabled()) {
                log.error(context.readSheetHolder().getSheetName() + locationMsg + errMessages);
            }
        }else{
            String locationMsg = StringUtils.format("第{}行", context.readRowHolder().getRowIndex());
            failureMsg.add(new OrderCheckMsg(null, context.readSheetHolder().getSheetName(), locationMsg,"发生错误",null));
            if (log.isDebugEnabled()) {
                log.error(context.readSheetHolder().getSheetName()+ locationMsg + "发生错误");
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(isImport){
            if (insertDataList.size() != 0) {
                doSave(true);
                insertDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
            if (updateDataList.size() != 0) {
                doSave(false);
                updateDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
            log.info("所有数据导入数据库完成！");
        }

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
                return successMsg;
            }

            @Override
            public List<OrderCheckMsg> getSuccessList() {
                return null;
            }
        };
    }

    private final void doSave(boolean isInsert) {
        if (isInsert) {
            log.info("sheet1{}条数据，开始存储到数据库！",insertDataList.size());
            try {
                neuqOrderService.saveBatch(insertDataList);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new ServiceException("系统出错，无法导入订单列表，请联系管理员");
            }
            log.info("sheet1{}条数据，存储到数据库成功！", insertDataList.size());
        } else {
            log.info("sheet1{}条数据，开始更新到数据库！", updateDataList.size());
            try {
                neuqOrderService.updateBatch(updateDataList);
            } catch (Exception e) {
                log.info(e.getMessage());
                throw new ServiceException("系统出错，无法导入订单列表，请联系管理员");
            }
            log.info("sheet1{}条数据，更新到数据库成功！", updateDataList.size());
        }
    }


    private final void setAddress(NeuqOrderBo neuqOrderBo){
        if(neuqOrderBo.getShippingMethod().equals("0")){
            if(ObjectUtil.isNull(neuqOrderBo.getSelfPickUpAddress())){
                throw new ServiceException("自提订单的自提地址不能为空");
            }else{
                neuqOrderBo.setReceiverDetailAddress(neuqOrderBo.getSelfPickUpAddress());
            }
        }else{
            if(ObjectUtil.isNull(neuqOrderBo.getReceiverDetailAddress())){
                throw new ServiceException("订单地址不能为空");
            }
        }
    }

}
