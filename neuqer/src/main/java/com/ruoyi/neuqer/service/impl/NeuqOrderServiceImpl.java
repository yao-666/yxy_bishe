package com.ruoyi.neuqer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.convert.ExcelBigNumberConvert;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.neuqer.domain.NeuqOrder;
import com.ruoyi.neuqer.domain.bo.NeuqOrderBo;
import com.ruoyi.neuqer.domain.dto.OrderDetail;
import com.ruoyi.neuqer.domain.vo.NeuqOrderImportVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemImportVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemVo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderVo;
import com.ruoyi.neuqer.excel.OrderExcelResult;
import com.ruoyi.neuqer.listener.NeuqerOrderImportListener;
import com.ruoyi.neuqer.listener.NeuqerOrderItemImportListener;
import com.ruoyi.neuqer.mapper.NeuqOrderMapper;
import com.ruoyi.neuqer.service.INeuqOrderItemService;
import com.ruoyi.neuqer.service.INeuqOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.ruoyi.common.utils.poi.ExcelUtil.encodingFilename;

/**
 * 订单列表Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class NeuqOrderServiceImpl implements INeuqOrderService {

    private final NeuqOrderMapper baseMapper;
    private final INeuqOrderItemService itemService;

    /**
     * 查询订单列表
     *
     * @param id 订单列表主键
     * @return 订单列表
     */
    @Override
    public NeuqOrderVo queryById(String id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询订单列表列表
     *
     * @param bo 订单列表
     * @return 订单列表
     */
    @Override
    public TableDataInfo<OrderDetail> queryPageList(NeuqOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<NeuqOrder> lqw = buildQueryWrapper(bo);
        Page<NeuqOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<NeuqOrderVo> orderList = result.getRecords();
        List<OrderDetail> orderDetailList = new ArrayList<>(orderList.size());
        if(orderList.size() > 0){
            List<NeuqOrderItemVo> itemList = itemService.selectListByOrderSn(orderList);
            HashMap<String, List<NeuqOrderItemVo>> itemMap = new HashMap<>();
            for (NeuqOrderItemVo item : itemList) {
                item.setTakeNumber(item.getTotalQuantity() - item.getTakeNumber());
                if (ObjectUtil.isNull(itemMap.get(item.getOrderSn()))) {
                    List<NeuqOrderItemVo> mapList = new ArrayList<>();
                    mapList.add(item);
                    itemMap.put(item.getOrderSn(), mapList);
                } else {
                    List<NeuqOrderItemVo> mapList = itemMap.get(item.getOrderSn());
                    mapList.add(item);
                }
            }

            for (NeuqOrderVo orderVo : orderList) {
                OrderDetail orderDetail = BeanUtil.toBean(orderVo, OrderDetail.class);
                orderDetail.setItemVos(itemMap.get(orderDetail.getOrderSn()));
                orderDetailList.add(orderDetail);
            }
        }
        Page<OrderDetail> orderDetailPage = new Page(result.getCurrent(),result.getSize(),result.getTotal(),result.searchCount());
        orderDetailPage.setRecords(orderDetailList);
        return TableDataInfo.build(orderDetailPage);
    }

    /**
     * 查询订单列表列表
     *
     * @param bo 订单列表
     * @return 订单列表
     */
    @Override
    public List<NeuqOrderVo> queryList(NeuqOrderBo bo) {
        LambdaQueryWrapper<NeuqOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }


    private LambdaQueryWrapper<NeuqOrder> buildQueryWrapper(NeuqOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<NeuqOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderSn()), NeuqOrder::getOrderSn, bo.getOrderSn());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderStatus()), NeuqOrder::getOrderStatus, bo.getOrderStatus());
        lqw.eq(bo.getPaymentTime() != null, NeuqOrder::getPaymentTime, bo.getPaymentTime());
        lqw.eq(bo.getAccomplishTime() != null, NeuqOrder::getAccomplishTime, bo.getAccomplishTime());
        lqw.eq(bo.getOrderAmount() != null, NeuqOrder::getOrderAmount, bo.getOrderAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderType()), NeuqOrder::getOrderType, bo.getOrderType());
        lqw.eq(StringUtils.isNotBlank(bo.getShippingMethod()), NeuqOrder::getShippingMethod, bo.getShippingMethod());
        lqw.like(StringUtils.isNotBlank(bo.getReceiverName()), NeuqOrder::getReceiverName, bo.getReceiverName());
        lqw.eq(StringUtils.isNotBlank(bo.getReceiverPhone()), NeuqOrder::getReceiverPhone, bo.getReceiverPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getReceiverDetailAddress()), NeuqOrder::getReceiverDetailAddress, bo.getReceiverDetailAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getBuyerMessage()), NeuqOrder::getBuyerMessage, bo.getBuyerMessage());
        lqw.eq(bo.getShippingPrice() != null, NeuqOrder::getShippingPrice, bo.getShippingPrice());
        lqw.eq(StringUtils.isNotBlank(bo.getShippingManId()), NeuqOrder::getShippingManId, bo.getShippingManId());
        lqw.eq(StringUtils.isNotBlank(bo.getPickUpAddress()), NeuqOrder::getPickUpAddress, bo.getPickUpAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getNote()), NeuqOrder::getNote, bo.getNote());
        return lqw;
    }

    /**
     * 新增订单列表
     *
     * @param bo 订单列表
     * @return 结果
     */
    @Override
    public Boolean insertByBo(NeuqOrderBo bo) {
        NeuqOrder add = BeanUtil.toBean(bo, NeuqOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改订单列表
     *
     * @param bo 订单列表
     * @return 结果
     */
    @Override
    public Boolean updateByBo(NeuqOrderBo bo) {
        NeuqOrder update = BeanUtil.toBean(bo, NeuqOrder.class);
        validEntityBeforeSave(update);
        System.out.println("NeuqOrderServiceImpl: " + update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(NeuqOrder entity) {
        //TODO 做一些数据校验,如唯一约束

    }


    /**
     * 批量删除订单列表
     *
     * @param ids 需要删除的订单列表主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderSn 需要查询的订单编号
     * @return 结果
     */
    @Override
    public NeuqOrderBo selectOrderByOrderSn(String orderSn) {
        return baseMapper.selectOrderByOrderSn(orderSn);
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param list 需要插入的数据列表
     * @return 结果
     */
    @Override
    @Transactional
    public boolean saveBatch(List<NeuqOrder> list) {
        try {
            return baseMapper.insertBatch(list);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据订单编号更新订单列表
     *
     * @param List 需要更新的数据列表
     * @return 结果
     */
    @Override
    @Transactional
    public boolean updateBatch(List<NeuqOrder> List) {
        try {
            return baseMapper.updateBatchById(List);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 批量插入或更新数据
     *
     * @param List List<NeuqOrder>对象列表
     * @return 结果
     */
    @Override
    public boolean insertOrUpdateBatch(List<NeuqOrder> List) {
        return baseMapper.insertOrUpdateBatch(List);
    }

    /**
     * 下载订单导入excel模板
     *
     * @param response HttpServletResponse Http对象
     */
    @Override
    public void importTemplate(HttpServletResponse response) {
        //ExcelUtil.exportExcel(new ArrayList<>(), "用户数据", NeuqOrderImportVo.class, response);
        ExcelWriter excelWriter = null;
        try {
            String filename = encodingFilename("订单导入模板");
            response.reset();
            ////在响应头插入Access-Control-Allow-Origin为’*‘,解决跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            FileUtils.setAttachmentResponseHeader(response, filename);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            //新建excelWriter
            excelWriter = EasyExcel.write(response.getOutputStream()).build();
            //写入订单列表（sheet1）
            WriteSheet sheet1 = EasyExcel.writerSheet(0, "订单列表").head(NeuqOrderImportVo.class)
                // 自动适配
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 大数值自动转换 防止失真
                .registerConverter(new ExcelBigNumberConvert())
                .build();
            //向sheet0写入数据 传入空list这样只导出表头
            excelWriter.write(new ArrayList<>(), sheet1);
            //写入订单明细表表头（sheet2）
            WriteSheet sheet2 = EasyExcel.writerSheet(1, "订单明细表").head(NeuqOrderItemImportVo.class)
                // 自动适配
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 大数值自动转换 防止失真
                .registerConverter(new ExcelBigNumberConvert())
                .build();
            //向sheet0写入数据 传入空list这样只导出表头
            excelWriter.write(new ArrayList<>(), sheet2);

        } catch (IOException e) {
            throw new RuntimeException("导出Excel模板异常");
        } finally {
            //无论是否成功，都关闭导出流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * @param is            InputStream 导入时，前端传过来的文件
     * @param updateSupport boolean 是否更新已有数据
     * @param isImport      boolean 是否执行校验，订单导入前必须进行校验通过
     * @author yxy
     * @Date 2022/4/27
     * @returns
     **/
    @Override
    public OrderExcelResult orderImport(InputStream is, boolean updateSupport, boolean isImport) {
        ExcelReader excelReader = null;
        try {
            //构造订单列表导入监听器
            NeuqerOrderImportListener orderImportListener = new NeuqerOrderImportListener(updateSupport, isImport);
            //声明一个excelReader，读取导入的文件。
            excelReader = EasyExcel.read(is).build();
            ReadSheet readSheet1 = EasyExcel.readSheet(0).head(NeuqOrderImportVo.class)
                .registerReadListener(orderImportListener).build();
            excelReader.read(readSheet1);
            //构建订单明细表导入监听器
            NeuqerOrderItemImportListener itemImportListener = new NeuqerOrderItemImportListener(updateSupport, isImport,
                orderImportListener.getOrderExcelResult().getAnalysis(), orderImportListener.getOrderExcelResult().getFailMsgList());
            ReadSheet readSheet2 = EasyExcel.readSheet(1).head(NeuqOrderItemImportVo.class)
                .registerReadListener(itemImportListener).build();
            excelReader.read(readSheet2);
            return itemImportListener.getOrderExcelResult();
        } catch (Exception e) {
            List<String> list = this.getEmptyOrderList();
            if (list.size() > 0) {
                this.deleteEmptyOrder(list);
            }
            throw new RuntimeException("Excel导入异常");
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    @Override
    public List<String> getEmptyOrderList() {
        List<String> list = baseMapper.getEmptyOrderList();
        return list;
    }


    /**
     * 删除没有订单明细的订单记录
     *
     * @author yxy
     * @Date 2022/4/27
     **/
    @Override
    public void deleteEmptyOrder(List<String> list) {
        baseMapper.deleteEmptyOrder(list);
    }

}
