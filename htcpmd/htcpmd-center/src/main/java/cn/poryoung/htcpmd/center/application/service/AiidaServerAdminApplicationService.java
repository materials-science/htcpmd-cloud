package cn.poryoung.htcpmd.center.application.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.poryoung.htcpmd.center.domain.entity.aiida.AiidaServerDoc;
import cn.poryoung.htcpmd.center.domain.service.AiidaServerDocDomainService;
import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.BaseApplicationService;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.annotation.RequiresRoles;
import com.ruoyi.common.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "AiidaServerAdminApplicationService", description = "Aiida Server对象管理 管理员接口")
@RestController
@RequestMapping("/admin/server")
public class AiidaServerAdminApplicationService extends BaseApplicationService {
    @Autowired
    private AiidaServerDocDomainService aiidaServerDocDomainService;

    /**
     * 查询aiidaServer信息列表
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:search")
    @GetMapping("/search")
    public TableDataInfo search(@RequestParam("label") String label) {
        Page<AiidaServerDoc> aiidaServerDocPage = aiidaServerDocDomainService.searchByLabel(label);
        return getDataTable(aiidaServerDocPage);
    }

    /**
     * 查询aiidaServer信息列表 (Admin)
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:admin:list")
    @RequiresRoles("admin")
    @GetMapping("/list")
    public TableDataInfo list() {
        Page<AiidaServerDoc> aiidaServerDocPage = aiidaServerDocDomainService.findAll(PageSupport.getPageableBeginWith0());
        return getDataTable(aiidaServerDocPage);
    }

    /**
     * 导出aiidaServer信息列表
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:admin:export")
    @RequiresRoles("admin")
    @Log(title = "aiidaServer信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AiidaServerDoc aiidaServerDoc) {
        List<AiidaServerDoc> list = aiidaServerDocDomainService.findAll();
        ExcelUtil<AiidaServerDoc> util = new ExcelUtil<AiidaServerDoc>(AiidaServerDoc.class);
        util.exportExcel(response, list, "aiidaServer信息数据");
    }

    /**
     * 获取aiidaServer信息详细信息
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:admin:query")
    @RequiresRoles("admin")
    @GetMapping(value = "/{label}")
    public AjaxResult getInfo(@PathVariable("label") String label) {
        return success(aiidaServerDocDomainService.findServerDocByLabel(label));
    }

    /**
     * 新增aiidaServer信息
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:add")
    @RequiresRoles("admin")
    @Log(title = "aiidaServer信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AiidaServerDoc aiidaServerDoc) throws BusinessException {
        // TODO
        BusinessException.throwExceptionIfTrue(aiidaServerDocDomainService.existsByLabel(aiidaServerDoc.getLabel()), BusinessErrorStatusEnum.DUPLICATE_RECORD, null);
        aiidaServerDoc.setUuid(IdUtil.simpleUUID());
        aiidaServerDoc.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        aiidaServerDoc.setCreateTime(DateTime.now());
        aiidaServerDoc.setGroupId(CustRequestHelper.getGroupId());
        return success(aiidaServerDocDomainService.save(aiidaServerDoc));
    }

    /**
     * 修改aiidaServer信息
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:edit")
    @RequiresRoles("admin")
    @Log(title = "aiidaServer信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AiidaServerDoc aiidaServerDoc) throws BusinessException {
        BusinessException.throwExceptionIfTrue(StrUtil.isBlank(aiidaServerDoc.getId()) && StrUtil.isBlank(aiidaServerDoc.getUuid()), BusinessErrorStatusEnum.INVALID_REQUEST_PARAMETERS.getCode(), "uuid or id cannot be null.");
        aiidaServerDoc.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        aiidaServerDoc.setUpdateTime(DateTime.now());
        return success(aiidaServerDocDomainService.save(aiidaServerDoc));
    }

    /**
     * 删除aiidaServer信息
     */
    @RequiresPermissions("htcpmd-center:aiidaServer:remove")
    @RequiresRoles("admin")
    @Log(title = "aiidaServer信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
        aiidaServerDocDomainService.deleteById(id);
        return success();
    }
}
