package com.ruoyi.neuqer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.neuqer.mapper.NeuqOrderItemBufferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.neuqer.domain.bo.NeuqOrderItemBufferBo;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemBufferVo;
import com.ruoyi.neuqer.domain.NeuqOrderItemBuffer;
import com.ruoyi.neuqer.service.INeuqOrderItemBufferService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单明细导入缓冲区Service业务层处理
 *
 * @author yxy
 * @date 2022-04-21
 */
@RequiredArgsConstructor
@Service
public class NeuqOrderItemBufferServiceImpl implements INeuqOrderItemBufferService {

    private final NeuqOrderItemBufferMapper baseMapper;

    /**
     * 查询订单明细导入缓冲区
     *
     * @param id 订单明细导入缓冲区主键
     * @return 订单明细导入缓冲区
     */
    @Override
    public NeuqOrderItemBufferVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询订单明细导入缓冲区列表
     *
     * @param bo 订单明细导入缓冲区
     * @return 订单明细导入缓冲区
     */
    @Override
    public TableDataInfo<NeuqOrderItemBufferVo> queryPageList(NeuqOrderItemBufferBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<NeuqOrderItemBuffer> lqw = buildQueryWrapper(bo);
        Page<NeuqOrderItemBufferVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询订单明细导入缓冲区列表
     *
     * @param bo 订单明细导入缓冲区
     * @return 订单明细导入缓冲区
     */
    @Override
    public List<NeuqOrderItemBufferVo> queryList(NeuqOrderItemBufferBo bo) {
        LambdaQueryWrapper<NeuqOrderItemBuffer> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<NeuqOrderItemBuffer> buildQueryWrapper(NeuqOrderItemBufferBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<NeuqOrderItemBuffer> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderSn()), NeuqOrderItemBuffer::getOrderSn, bo.getOrderSn());
        lqw.eq(StringUtils.isNotBlank(bo.getGroupId()), NeuqOrderItemBuffer::getGroupId, bo.getGroupId());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderStatus()), NeuqOrderItemBuffer::getOrderStatus, bo.getOrderStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getAfterStatus()), NeuqOrderItemBuffer::getAfterStatus, bo.getAfterStatus());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), NeuqOrderItemBuffer::getProductName, bo.getProductName());
        lqw.like(StringUtils.isNotBlank(bo.getClassName()), NeuqOrderItemBuffer::getClassName, bo.getClassName());
        lqw.eq(StringUtils.isNotBlank(bo.getShoppingOptions()), NeuqOrderItemBuffer::getShoppingOptions, bo.getShoppingOptions());
        lqw.eq(bo.getUnitPrice() != null, NeuqOrderItemBuffer::getUnitPrice, bo.getUnitPrice());
        lqw.eq(bo.getAmount() != null, NeuqOrderItemBuffer::getAmount, bo.getAmount());
        lqw.eq(bo.getSubtotal() != null, NeuqOrderItemBuffer::getSubtotal, bo.getSubtotal());
        lqw.eq(bo.getSingleNumber() != null, NeuqOrderItemBuffer::getSingleNumber, bo.getSingleNumber());
        lqw.eq(bo.getTotalQuantity() != null, NeuqOrderItemBuffer::getTotalQuantity, bo.getTotalQuantity());
        lqw.eq(bo.getTakeNumber() != null, NeuqOrderItemBuffer::getTakeNumber, bo.getTakeNumber());
        lqw.eq(bo.getRefundAmount() != null, NeuqOrderItemBuffer::getRefundAmount, bo.getRefundAmount());
        lqw.eq(bo.getMemberDiscount() != null, NeuqOrderItemBuffer::getMemberDiscount, bo.getMemberDiscount());
        lqw.eq(StringUtils.isNotBlank(bo.getNote()), NeuqOrderItemBuffer::getNote, bo.getNote());
        lqw.eq(StringUtils.isNotBlank(bo.getMemberAccount()), NeuqOrderItemBuffer::getMemberAccount, bo.getMemberAccount());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), NeuqOrderItemBuffer::getMemberName, bo.getMemberName());
        lqw.eq(StringUtils.isNotBlank(bo.getAdditionForm()), NeuqOrderItemBuffer::getAdditionForm, bo.getAdditionForm());
        lqw.like(StringUtils.isNotBlank(bo.getName()), NeuqOrderItemBuffer::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getAddress()), NeuqOrderItemBuffer::getAddress, bo.getAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), NeuqOrderItemBuffer::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getBuyerMessage()), NeuqOrderItemBuffer::getBuyerMessage, bo.getBuyerMessage());
        lqw.eq(StringUtils.isNotBlank(bo.getDeliveryMode()), NeuqOrderItemBuffer::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDeliveryPrice() != null, NeuqOrderItemBuffer::getDeliveryPrice, bo.getDeliveryPrice());
        lqw.eq(StringUtils.isNotBlank(bo.getDeliveryNum()), NeuqOrderItemBuffer::getDeliveryNum, bo.getDeliveryNum());
        lqw.eq(bo.getDeliveryTime() != null, NeuqOrderItemBuffer::getDeliveryTime, bo.getDeliveryTime());
        lqw.eq(bo.getEstimatedTime() != null, NeuqOrderItemBuffer::getEstimatedTime, bo.getEstimatedTime());
        lqw.eq(StringUtils.isNotBlank(bo.getPickUpAddress()), NeuqOrderItemBuffer::getPickUpAddress, bo.getPickUpAddress());
        lqw.eq(StringUtils.isNotBlank(bo.getPickUpSite()), NeuqOrderItemBuffer::getPickUpSite, bo.getPickUpSite());
        return lqw;
    }

    /**
     * 新增订单明细导入缓冲区
     *
     * @param bo 订单明细导入缓冲区
     * @return 结果
     */
    @Override
    public Boolean insertByBo(NeuqOrderItemBufferBo bo) {
        NeuqOrderItemBuffer add = BeanUtil.toBean(bo, NeuqOrderItemBuffer.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改订单明细导入缓冲区
     *
     * @param bo 订单明细导入缓冲区
     * @return 结果
     */
    @Override
    public Boolean updateByBo(NeuqOrderItemBufferBo bo) {
        NeuqOrderItemBuffer update = BeanUtil.toBean(bo, NeuqOrderItemBuffer.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(NeuqOrderItemBuffer entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除订单明细导入缓冲区
     *
     * @param ids 需要删除的订单明细导入缓冲区主键
     * @return 结果
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean saveBatch(List<NeuqOrderItemBuffer> list) {
        return baseMapper.insertBatch(list);
    }
}
