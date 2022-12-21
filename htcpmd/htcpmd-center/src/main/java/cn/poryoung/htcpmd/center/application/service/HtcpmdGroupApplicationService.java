package cn.poryoung.htcpmd.center.application.service;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdGroup;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdGroupDomainService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 群组 Application Service
 * 
 * @author PorYoung
 * @date 2022-12-12
 */
@RestController
@RequestMapping("/group")
public class HtcpmdGroupApplicationService extends BaseController
{
    @Autowired
    private HtcpmdGroupDomainService htcpmdGroupDomainService;

    /**
     * 查询群组列表
     */
    @RequiresPermissions("htcpmd-center:group:list")
    @GetMapping("/list")
    public TableDataInfo list(HtcpmdGroup htcpmdGroup)
    {
        startPage();
        List<HtcpmdGroup> list = htcpmdGroupDomainService.selectHtcpmdGroupList(htcpmdGroup);
        return getDataTable(list);
    }

    /**
     * 导出群组列表
     */
    @RequiresPermissions("htcpmd-center:group:export")
    @Log(title = "群组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HtcpmdGroup htcpmdGroup)
    {
        List<HtcpmdGroup> list = htcpmdGroupDomainService.selectHtcpmdGroupList(htcpmdGroup);
        ExcelUtil<HtcpmdGroup> util = new ExcelUtil<HtcpmdGroup>(HtcpmdGroup.class);
        util.exportExcel(response, list, "群组数据");
    }

    /**
     * 获取群组详细信息
     */
    @RequiresPermissions("htcpmd-center:group:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(htcpmdGroupDomainService.selectHtcpmdGroupById(id));
    }

    /**
     * 新增群组
     */
    @RequiresPermissions("htcpmd-center:group:add")
    @Log(title = "群组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HtcpmdGroup htcpmdGroup)
    {
        return toAjax(htcpmdGroupDomainService.insertHtcpmdGroup(htcpmdGroup));
    }

    /**
     * 修改群组
     */
    @RequiresPermissions("htcpmd-center:group:edit")
    @Log(title = "群组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HtcpmdGroup htcpmdGroup)
    {
        return toAjax(htcpmdGroupDomainService.updateHtcpmdGroup(htcpmdGroup));
    }

    /**
     * 删除群组
     */
    @RequiresPermissions("htcpmd-center:group:remove")
    @Log(title = "群组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(htcpmdGroupDomainService.deleteHtcpmdGroupByIds(ids));
    }
}
