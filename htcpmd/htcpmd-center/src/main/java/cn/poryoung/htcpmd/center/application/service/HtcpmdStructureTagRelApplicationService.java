package cn.poryoung.htcpmd.center.application.service;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTagRel;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureTagRelDomainService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * structure 标签关联 Application Service
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
@RestController
@RequestMapping("/structure_tag_rel")
public class HtcpmdStructureTagRelApplicationService extends BaseController
{
    @Autowired
    private HtcpmdStructureTagRelDomainService htcpmdStructureTagRelDomainService;

    /**
     * 查询structure 标签关联列表
     */
    @RequiresPermissions("htcpmd-center:structure_tag_rel:list")
    @GetMapping("/list")
    public TableDataInfo list(HtcpmdStructureTagRel htcpmdStructureTagRel)
    {
        startPage();
        List<HtcpmdStructureTagRel> list = htcpmdStructureTagRelDomainService.selectHtcpmdStructureTagRelList(htcpmdStructureTagRel);
        return getDataTable(list);
    }

    /**
     * 导出structure 标签关联列表
     */
    @RequiresPermissions("htcpmd-center:structure_tag_rel:export")
    @Log(title = "structure 标签关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HtcpmdStructureTagRel htcpmdStructureTagRel)
    {
        List<HtcpmdStructureTagRel> list = htcpmdStructureTagRelDomainService.selectHtcpmdStructureTagRelList(htcpmdStructureTagRel);
        ExcelUtil<HtcpmdStructureTagRel> util = new ExcelUtil<HtcpmdStructureTagRel>(HtcpmdStructureTagRel.class);
        util.exportExcel(response, list, "structure 标签关联数据");
    }

    /**
     * 获取structure 标签关联详细信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag_rel:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(htcpmdStructureTagRelDomainService.selectHtcpmdStructureTagRelById(id));
    }

    /**
     * 新增structure 标签关联
     */
    @RequiresPermissions("htcpmd-center:structure_tag_rel:add")
    @Log(title = "structure 标签关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HtcpmdStructureTagRel htcpmdStructureTagRel)
    {
        return toAjax(htcpmdStructureTagRelDomainService.insertHtcpmdStructureTagRel(htcpmdStructureTagRel));
    }

    /**
     * 修改structure 标签关联
     */
    @RequiresPermissions("htcpmd-center:structure_tag_rel:edit")
    @Log(title = "structure 标签关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HtcpmdStructureTagRel htcpmdStructureTagRel)
    {
        return toAjax(htcpmdStructureTagRelDomainService.updateHtcpmdStructureTagRel(htcpmdStructureTagRel));
    }

    /**
     * 删除structure 标签关联
     */
    @RequiresPermissions("htcpmd-center:structure_tag_rel:remove")
    @Log(title = "structure 标签关联", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(htcpmdStructureTagRelDomainService.deleteHtcpmdStructureTagRelByIds(ids));
    }
}
