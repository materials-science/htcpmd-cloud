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
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTag;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureTagDomainService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * structure 标签信息 Application Service
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
@RestController
@RequestMapping("/structure_tag")
public class HtcpmdStructureTagApplicationService extends BaseController
{
    @Autowired
    private HtcpmdStructureTagDomainService htcpmdStructureTagDomainService;

    /**
     * 查询structure 标签信息列表
     */
    @RequiresPermissions("htcpmd-center:structure_tag:list")
    @GetMapping("/list")
    public TableDataInfo list(HtcpmdStructureTag htcpmdStructureTag)
    {
        startPage();
        List<HtcpmdStructureTag> list = htcpmdStructureTagDomainService.selectHtcpmdStructureTagList(htcpmdStructureTag);
        return getDataTable(list);
    }

    /**
     * 导出structure 标签信息列表
     */
    @RequiresPermissions("htcpmd-center:structure_tag:export")
    @Log(title = "structure 标签信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HtcpmdStructureTag htcpmdStructureTag)
    {
        List<HtcpmdStructureTag> list = htcpmdStructureTagDomainService.selectHtcpmdStructureTagList(htcpmdStructureTag);
        ExcelUtil<HtcpmdStructureTag> util = new ExcelUtil<HtcpmdStructureTag>(HtcpmdStructureTag.class);
        util.exportExcel(response, list, "structure 标签信息数据");
    }

    /**
     * 获取structure 标签信息详细信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(htcpmdStructureTagDomainService.selectHtcpmdStructureTagById(id));
    }

    /**
     * 新增structure 标签信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:add")
    @Log(title = "structure 标签信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HtcpmdStructureTag htcpmdStructureTag)
    {
        return toAjax(htcpmdStructureTagDomainService.insertHtcpmdStructureTag(htcpmdStructureTag));
    }

    /**
     * 修改structure 标签信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:edit")
    @Log(title = "structure 标签信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HtcpmdStructureTag htcpmdStructureTag)
    {
        return toAjax(htcpmdStructureTagDomainService.updateHtcpmdStructureTag(htcpmdStructureTag));
    }

    /**
     * 删除structure 标签信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:remove")
    @Log(title = "structure 标签信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(htcpmdStructureTagDomainService.deleteHtcpmdStructureTagByIds(ids));
    }
}
