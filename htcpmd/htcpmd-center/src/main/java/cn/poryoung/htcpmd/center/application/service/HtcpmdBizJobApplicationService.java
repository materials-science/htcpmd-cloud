package cn.poryoung.htcpmd.center.application.service;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdBizJob;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;
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
 * Htcpmd业务任务 Application Service
 * 
 * @author PorYoung
 * @date 2022-12-13
 */
@RestController
@RequestMapping("/bizjob")
public class HtcpmdBizJobApplicationService extends BaseController
{
    @Autowired
    private HtcpmdBizJobDomainService htcpmdBizJobDomainService;

    /**
     * 查询Htcpmd业务任务列表
     */
    @RequiresPermissions("htcpmd-center:bizjob:list")
    @GetMapping("/list")
    public TableDataInfo list(HtcpmdBizJob htcpmdBizJob)
    {
        startPage();
        List<HtcpmdBizJob> list = htcpmdBizJobDomainService.selectHtcpmdBizJobList(htcpmdBizJob);
        return getDataTable(list);
    }

    /**
     * 导出Htcpmd业务任务列表
     */
    @RequiresPermissions("htcpmd-center:bizjob:export")
    @Log(title = "Htcpmd业务任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HtcpmdBizJob htcpmdBizJob)
    {
        List<HtcpmdBizJob> list = htcpmdBizJobDomainService.selectHtcpmdBizJobList(htcpmdBizJob);
        ExcelUtil<HtcpmdBizJob> util = new ExcelUtil<HtcpmdBizJob>(HtcpmdBizJob.class);
        util.exportExcel(response, list, "Htcpmd业务任务数据");
    }

    /**
     * 获取Htcpmd业务任务详细信息
     */
    @RequiresPermissions("htcpmd-center:bizjob:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(htcpmdBizJobDomainService.selectHtcpmdBizJobById(id));
    }

    /**
     * 新增Htcpmd业务任务
     */
    @RequiresPermissions("htcpmd-center:bizjob:add")
    @Log(title = "Htcpmd业务任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HtcpmdBizJob htcpmdBizJob)
    {
        return toAjax(htcpmdBizJobDomainService.insertHtcpmdBizJob(htcpmdBizJob));
    }

    /**
     * 修改Htcpmd业务任务
     */
    @RequiresPermissions("htcpmd-center:bizjob:edit")
    @Log(title = "Htcpmd业务任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HtcpmdBizJob htcpmdBizJob)
    {
        return toAjax(htcpmdBizJobDomainService.updateHtcpmdBizJob(htcpmdBizJob));
    }

    /**
     * 删除Htcpmd业务任务
     */
    @RequiresPermissions("htcpmd-center:bizjob:remove")
    @Log(title = "Htcpmd业务任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(htcpmdBizJobDomainService.deleteHtcpmdBizJobByIds(ids));
    }
}
