package com.ruoyi.neuqer.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemVo;
import com.ruoyi.neuqer.domain.bo.NeuqOrderItemBo;
import com.ruoyi.neuqer.service.INeuqOrderItemService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiOperation;

/**
 * 订单明细Controller
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Validated
@Api(value = "订单明细控制器", tags = {"订单明细管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/neuqer/orderItem")
public class NeuqOrderItemController extends BaseController {

    private final INeuqOrderItemService iNeuqOrderItemService;

    /**
     * 查询订单明细列表
     */
    @ApiOperation("查询订单明细列表")
    @SaCheckPermission("neuqer:orderItem:list")
    @GetMapping("/list")
    public TableDataInfo<NeuqOrderItemVo> list(@Validated(QueryGroup.class) NeuqOrderItemBo bo, PageQuery pageQuery) {
        return iNeuqOrderItemService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单明细列表
     */
    @ApiOperation("导出订单明细列表")
    @SaCheckPermission("neuqer:orderItem:export")
    @Log(title = "订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated NeuqOrderItemBo bo, HttpServletResponse response) {
        List<NeuqOrderItemVo> list = iNeuqOrderItemService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单明细", NeuqOrderItemVo.class, response);
    }

    /**
     * 获取订单明细详细信息
     */
    @ApiOperation("获取订单明细详细信息")
    @SaCheckPermission("neuqer:orderItem:query")
    @GetMapping("/{id}")
    public R<NeuqOrderItemVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") String id) {
        return R.ok(iNeuqOrderItemService.queryById(id));
    }

    /**
     * 新增订单明细
     */
    @ApiOperation("新增订单明细")
    @SaCheckPermission("neuqer:orderItem:add")
    @Log(title = "订单明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody NeuqOrderItemBo bo) {
        return toAjax(iNeuqOrderItemService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改订单明细
     */
    @ApiOperation("修改订单明细")
    @SaCheckPermission("neuqer:orderItem:edit")
    @Log(title = "订单明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody NeuqOrderItemBo bo) {
        return toAjax(iNeuqOrderItemService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除订单明细
     */
    @ApiOperation("删除订单明细")
    @SaCheckPermission("neuqer:orderItem:remove")
    @Log(title = "订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable String[] ids) {
        return toAjax(iNeuqOrderItemService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
