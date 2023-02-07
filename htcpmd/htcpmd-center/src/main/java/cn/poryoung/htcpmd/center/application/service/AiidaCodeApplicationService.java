package cn.poryoung.htcpmd.center.application.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.poryoung.htcpmd.center.domain.entity.aiida.CodeDoc;
import cn.poryoung.htcpmd.center.domain.service.AiidaCodeDocDomainService;
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
import com.ruoyi.common.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "AiidaCodeApplicationService", description = "Aiida Code对象管理")
@RestController
@RequestMapping("/cluster/code")
public class AiidaCodeApplicationService extends BaseApplicationService {
    @Autowired
    private AiidaCodeDocDomainService aiidaCodeDocDomainService;

    /**
     * 查询code信息列表
     */
    @RequiresPermissions("htcpmd-center:code:search")
    @GetMapping("/search")
    public TableDataInfo search(@RequestParam("label") String label) {
        Page<CodeDoc> codeDocPage = aiidaCodeDocDomainService.searchByLabel(label);
        return getDataTable(codeDocPage);
    }


    /**
     * 查询code信息列表
     */
    @RequiresPermissions("htcpmd-center:code:list")
    @GetMapping("/list")
    public TableDataInfo list() {
        Page<CodeDoc> codeDocPage = aiidaCodeDocDomainService.findAll(PageSupport.getPageableBeginWith0());
        return getDataTable(codeDocPage);
    }

    /**
     * 导出code信息列表
     */
    @RequiresPermissions("htcpmd-center:code:export")
    @Log(title = "code信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CodeDoc codeDoc) {
        List<CodeDoc> list = aiidaCodeDocDomainService.findAll();
        ExcelUtil<CodeDoc> util = new ExcelUtil<CodeDoc>(CodeDoc.class);
        util.exportExcel(response, list, "code信息数据");
    }

    /**
     * 获取code信息详细信息
     */
    @RequiresPermissions("htcpmd-center:code:query")
    @GetMapping(value = "/{label}")
    public AjaxResult getInfo(@PathVariable("label") String label) {
        return success(aiidaCodeDocDomainService.findCodeDocByLabel(label));
    }

    /**
     * 新增code信息
     */
    @RequiresPermissions("htcpmd-center:code:add")
    @Log(title = "code信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CodeDoc codeDoc) throws BusinessException {
        BusinessException.throwExceptionIfTrue(aiidaCodeDocDomainService.existsByLabel(codeDoc.getLabel()), BusinessErrorStatusEnum.DUPLICATE_RECORD, null);
        codeDoc.setUuid(IdUtil.simpleUUID());
        codeDoc.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        codeDoc.setCreateTime(DateTime.now());
        codeDoc.setGroupId(CustRequestHelper.getGroupId());
        return success(aiidaCodeDocDomainService.save(codeDoc));
    }

    /**
     * 修改code信息
     */
    @RequiresPermissions("htcpmd-center:code:edit")
    @Log(title = "code信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CodeDoc codeDoc) {
        codeDoc.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        codeDoc.setUpdateTime(DateTime.now());
        return success(aiidaCodeDocDomainService.save(codeDoc));
    }

    /**
     * 删除code信息
     */
    @RequiresPermissions("htcpmd-center:code:remove")
    @Log(title = "code信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
        aiidaCodeDocDomainService.deleteById(id);
        return success();
    }
}
