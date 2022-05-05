package com.ruoyi.neuqer.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.neuqer.domain.bo.NeuqOrderBo;
import com.ruoyi.neuqer.domain.dto.OrderDetail;
import com.ruoyi.neuqer.domain.vo.NeuqOrderVo;
import com.ruoyi.neuqer.excel.OrderExcelResult;
import com.ruoyi.neuqer.service.INeuqOrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;


/**
 * 订单列表Controller
 *
 * @author ruoyi
 * @date 2022-04-18
 */
@Validated
@Api(value = "订单列表控制器", tags = {"订单列表管理"})
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/neuqer/order")
public class NeuqOrderController extends BaseController {

    private final INeuqOrderService iNeuqOrderService;

    /**
     * 查询订单列表列表
     */
    @ApiOperation("查询订单列表列表")
    @SaCheckPermission("neuqer:order:list")
    @GetMapping("/list")
    public TableDataInfo<OrderDetail> list(@Validated(QueryGroup.class) NeuqOrderBo bo, PageQuery pageQuery) {
        return iNeuqOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单列表列表
     */
    @ApiOperation("导出订单列表列表")
    @SaCheckPermission("neuqer:order:export")
    @Log(title = "订单列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated NeuqOrderBo bo, HttpServletResponse response) {
        List<NeuqOrderVo> list = iNeuqOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单列表", NeuqOrderVo.class, response);
    }

    @ApiOperation("导入订单列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "file", value = "导入文件", dataType = "java.io.File", required = true),
    })
    @Log(title = "导入订单列表", businessType = BusinessType.IMPORT)
    @SaCheckPermission("neuqer:order:import")
    @PostMapping("/importData")
    public R<?> importData(@RequestPart("file") MultipartFile file, boolean updateSupport, boolean isImport) throws Exception {
        if ((file.getSize() / 1048576.0) > 10.0) {
            return R.fail("上传的文件大小不能超过10M");
        }
        OrderExcelResult result = iNeuqOrderService.orderImport(file.getInputStream(), updateSupport, isImport);
        if (result.getFailMsgList().size() > 0) {
            return R.ok("错误信息", result.getFailMsgList());
        }else if(isImport){
            return R.ok("成功",result.getSuccessList());
        }else {
            return R.ok("成功");
        }
    }

    @ApiOperation("下载订单导入模板")
    @SaCheckPermission("neuqer:order:export")
    @PostMapping("/importTemplate")
    @Log(title = "下载订单导入模板", businessType = BusinessType.EXPORT)
    public void importTemplate(HttpServletResponse response) {
        iNeuqOrderService.importTemplate(response);
    }


    /**
     * 获取订单列表详细信息
     */
    @ApiOperation("获取订单列表详细信息")
    @SaCheckPermission("neuqer:order:query")
    @GetMapping("/{id}")
    public R<NeuqOrderVo> getInfo(@ApiParam("主键")
                                  @NotNull(message = "主键不能为空")
                                  @PathVariable("id") String id) {
        return R.ok(iNeuqOrderService.queryById(id));
    }

    /**
     * 新增订单列表
     */
    @ApiOperation("新增订单列表")
    @SaCheckPermission("neuqer:order:add")
    @Log(title = "订单列表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody NeuqOrderBo bo) {
        return toAjax(iNeuqOrderService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改订单列表
     */
    @ApiOperation("修改订单列表")
    @SaCheckPermission("neuqer:order:edit")
    @Log(title = "订单列表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody NeuqOrderBo bo) {
        return toAjax(iNeuqOrderService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除订单列表
     */
    @ApiOperation("删除订单列表")
    @SaCheckPermission("neuqer:order:remove")
    @Log(title = "订单列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable String[] ids) {
        return toAjax(iNeuqOrderService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
