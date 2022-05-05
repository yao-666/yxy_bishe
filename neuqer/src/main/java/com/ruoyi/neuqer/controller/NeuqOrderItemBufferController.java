package com.ruoyi.neuqer.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.excel.ExcelResult;
import com.ruoyi.neuqer.domain.NeuqOrderItemBuffer;
import com.ruoyi.neuqer.domain.vo.NeuqOrderImportVo;
import com.ruoyi.neuqer.listener.NeuqerOrderImportListener;
import io.swagger.annotations.*;
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
import com.ruoyi.neuqer.domain.vo.NeuqOrderItemBufferVo;
import com.ruoyi.neuqer.domain.bo.NeuqOrderItemBufferBo;
import com.ruoyi.neuqer.service.INeuqOrderItemBufferService;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 订单明细导入缓冲区Controller
 *
 * @author yxy
 * @date 2022-04-21
 */
@Validated
@Api(value = "订单明细导入缓冲区控制器", tags = {"订单明细导入缓冲区管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/neuqer/orderItemBuffer")
public class NeuqOrderItemBufferController extends BaseController {

    private final INeuqOrderItemBufferService iNeuqOrderItemBufferService;

    /**
     * 查询订单明细导入缓冲区列表
     */
    @ApiOperation("查询订单明细导入缓冲区列表")
    @SaCheckPermission("neuqer:orderItemBuffer:list")
    @GetMapping("/list")
    public TableDataInfo<NeuqOrderItemBufferVo> list(@Validated(QueryGroup.class) NeuqOrderItemBufferBo bo, PageQuery pageQuery) {
        return iNeuqOrderItemBufferService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单明细导入缓冲区列表
     */
    @ApiOperation("导出订单明细导入缓冲区列表")
    @SaCheckPermission("neuqer:orderItemBuffer:export")
    @Log(title = "订单明细导入缓冲区", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated NeuqOrderItemBufferBo bo, HttpServletResponse response) {
        List<NeuqOrderItemBufferVo> list = iNeuqOrderItemBufferService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单明细导入缓冲区", NeuqOrderItemBufferVo.class, response);
    }

    @ApiOperation("导入订单明细列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file", value = "导入文件", dataType = "java.io.File", required = true),
    })
    @Log(title = "导入订单列表", businessType = BusinessType.IMPORT)
    @SaCheckPermission("neuqer:orderItemBuffer:import")
    @PostMapping("/importData")
    public R<Void> importData(@RequestPart("file") MultipartFile file) throws Exception {
        ExcelResult<NeuqOrderItemBufferVo> result = ExcelUtil.importExcel(file.getInputStream(), NeuqOrderItemBufferVo.class, true);
        List<NeuqOrderItemBufferVo> volist = result.getList();
        List<NeuqOrderItemBuffer> list = BeanUtil.copyToList(volist, NeuqOrderItemBuffer.class);
        iNeuqOrderItemBufferService.saveBatch(list);
        return R.ok(result.getAnalysis());
    }

    /**
     * 获取订单明细导入缓冲区详细信息
     */
    @ApiOperation("获取订单明细导入缓冲区详细信息")
    @SaCheckPermission("neuqer:orderItemBuffer:query")
    @GetMapping("/{id}")
    public R<NeuqOrderItemBufferVo> getInfo(@ApiParam("主键")
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        return R.ok(iNeuqOrderItemBufferService.queryById(id));
    }

    /**
     * 新增订单明细导入缓冲区
     */
    @ApiOperation("新增订单明细导入缓冲区")
    @SaCheckPermission("neuqer:orderItemBuffer:add")
    @Log(title = "订单明细导入缓冲区", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody NeuqOrderItemBufferBo bo) {
        return toAjax(iNeuqOrderItemBufferService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改订单明细导入缓冲区
     */
    @ApiOperation("修改订单明细导入缓冲区")
    @SaCheckPermission("neuqer:orderItemBuffer:edit")
    @Log(title = "订单明细导入缓冲区", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody NeuqOrderItemBufferBo bo) {
        return toAjax(iNeuqOrderItemBufferService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除订单明细导入缓冲区
     */
    @ApiOperation("删除订单明细导入缓冲区")
    @SaCheckPermission("neuqer:orderItemBuffer:remove")
    @Log(title = "订单明细导入缓冲区", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iNeuqOrderItemBufferService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
