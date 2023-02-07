package cn.poryoung.htcpmd.center.application.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.poryoung.htcpmd.center.domain.entity.aiida.ComputerDoc;
import cn.poryoung.htcpmd.center.domain.service.AiidaComputerDocDomainService;
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

@Api(tags = "AiidaComputerApplicationService", description = "Aiida Computer对象管理")
@RestController
@RequestMapping("/computer")
public class AiidaComputerApplicationService extends BaseApplicationService {
    @Autowired
    private AiidaComputerDocDomainService aiidaComputerDocDomainService;

    /**
     * 查询computer信息列表
     */
    @RequiresPermissions("htcpmd-center:computer:search")
    @GetMapping("/search")
    public TableDataInfo search(@RequestParam("label") String label) {
        Page<ComputerDoc> computerDocPage = aiidaComputerDocDomainService.searchByLabel(label);
        return getDataTable(computerDocPage);
    }


    /**
     * 查询computer信息列表
     */
    @RequiresPermissions("htcpmd-center:computer:list")
    @GetMapping("/list")
    public TableDataInfo list() {
        Page<ComputerDoc> computerDocPage = aiidaComputerDocDomainService.findAll(PageSupport.getPageableBeginWith0());
        return getDataTable(computerDocPage);
    }

    /**
     * 导出computer信息列表
     */
    @RequiresPermissions("htcpmd-center:computer:export")
    @Log(title = "computer信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ComputerDoc computerDoc) {
        List<ComputerDoc> list = aiidaComputerDocDomainService.findAll();
        ExcelUtil<ComputerDoc> util = new ExcelUtil<ComputerDoc>(ComputerDoc.class);
        util.exportExcel(response, list, "computer信息数据");
    }

    /**
     * 获取computer信息详细信息
     */
    @RequiresPermissions("htcpmd-center:computer:query")
    @GetMapping(value = "/{label}")
    public AjaxResult getInfo(@PathVariable("label") String label) {
        return success(aiidaComputerDocDomainService.findComputerDocByLabel(label));
    }

    /**
     * 新增computer信息
     */
    @RequiresPermissions("htcpmd-center:computer:add")
    @Log(title = "computer信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ComputerDoc computerDoc) throws BusinessException {
        BusinessException.throwExceptionIfTrue(aiidaComputerDocDomainService.existsByLabel(computerDoc.getLabel()), BusinessErrorStatusEnum.DUPLICATE_RECORD, null);
        computerDoc.setUuid(IdUtil.simpleUUID());
        computerDoc.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        computerDoc.setCreateTime(DateTime.now());
        computerDoc.setGroupId(CustRequestHelper.getGroupId());
        return success(aiidaComputerDocDomainService.save(computerDoc));
    }

    /**
     * 修改computer信息
     */
    @RequiresPermissions("htcpmd-center:computer:edit")
    @Log(title = "computer信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ComputerDoc computerDoc) {
        computerDoc.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        computerDoc.setUpdateTime(DateTime.now());
        return success(aiidaComputerDocDomainService.save(computerDoc));
    }

    /**
     * 删除computer信息
     */
    @RequiresPermissions("htcpmd-center:computer:remove")
    @Log(title = "computer信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
        aiidaComputerDocDomainService.deleteById(id);
        return success();
    }
}
