package com.ruoyi.neuqer.service;

import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.neuqer.domain.NeuqOrder;
import com.ruoyi.neuqer.domain.bo.NeuqOrderBo;
import com.ruoyi.neuqer.domain.dto.OrderDetail;
import com.ruoyi.neuqer.domain.vo.NeuqOrderVo;
import com.ruoyi.neuqer.excel.OrderExcelResult;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * 订单列表Service接口
 *
 * @author ruoyi
 * @date 2022-04-18
 */
public interface INeuqOrderService {

    /**
     * 查询订单列表
     *
     * @param id 订单列表主键
     * @return 订单列表
     */
    NeuqOrderVo queryById(String id);

    /**
     * 查询订单列表列表
     *
     * @param  bo 订单列表
     * @param  pageQuery 分页参数
     * @return 订单列表集合
     */
    TableDataInfo<OrderDetail> queryPageList(NeuqOrderBo bo, PageQuery pageQuery);

    /**
     * 查询订单列表列表
     *
     * @param bo 订单列表
     * @return 订单列表集合
     */
    List<NeuqOrderVo> queryList(NeuqOrderBo bo);

    /**
     * 修改订单列表
     *
     * @param neuqOrder 订单列表
     * @return 结果
     */
    Boolean insertByBo(NeuqOrderBo bo);

    /**
     * 修改订单列表
     *
     * @param bo 订单列表
     * @return 结果
     */
    Boolean updateByBo(NeuqOrderBo bo);

    /**
     * 校验并批量删除订单列表信息
     *
     * @param ids 需要删除的订单列表主键集合
     * @param isValid 是否校验,true-删除前校验,false-不校验
     * @return 结果
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderSn 订单编号
     * @return 结果
     */
    NeuqOrderBo selectOrderByOrderSn(String orderSn);

    /**
     * 批量插入数据
     *
     * @param list 对象列表
     * @return 结果
     */
    boolean saveBatch(List<NeuqOrder> list);

    /**
     * 批量更新数据
     *
     * @param  List List<NeuqOrder>对象列表
     * @return 结果
     */
    boolean updateBatch(List<NeuqOrder> List);

    /**
     * 批量更新数据
     *
     * @param  List List<NeuqOrder>对象列表
     * @return 结果
     */
    boolean insertOrUpdateBatch(List<NeuqOrder> List);

    /**
     * 下载订单导入excel模板
     *
     * @param  response HttpServletResponse Http请求
     */
    void importTemplate(HttpServletResponse response);

    /**
     * 校验或者导入excel
     *
     * @param  is InputStream 前端传过来的文件流
     * @param  updateSupport boolean 是否更新已有的数据
     * @param  isImport boolean 是否导入数据（用于区别用户点击的是校验还是导入）
     */
    OrderExcelResult orderImport(InputStream is, boolean updateSupport, boolean isImport);

    /**
     * 获取订单明细为空的订单id，方便进行删除
     *
     */
    List<String> getEmptyOrderList();

    void deleteEmptyOrder(List<String> list);
}
