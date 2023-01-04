package cn.poryoung.htcpmd.center.application.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTag;
import cn.poryoung.htcpmd.center.domain.entity.mongo.StructureTagDoc;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureTagDomainService;
import cn.poryoung.htcpmd.center.domain.service.StructureTagDocDomainService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * structure 标签信息 Application Service
 *
 * @author PorYoung
 * @date 2022-12-15
 */
@RestController
@RequestMapping("/structure_tag")
public class HtcpmdStructureTagApplicationService extends BaseApplicationService {
    @Autowired
    private HtcpmdStructureTagDomainService htcpmdStructureTagDomainService;
    @Autowired
    private StructureTagDocDomainService structureTagDocDomainService;

    /**
     * 查询structure 标签信息列表
     */
    @RequiresPermissions("htcpmd-center:structure_tag:search")
    @GetMapping("/search")
    public TableDataInfo search(@RequestParam("name") String name) {
        Page<StructureTagDoc> structureTagDocPage = structureTagDocDomainService.searchByName(name);
        return getDataTable(structureTagDocPage);
    }


    /**
     * 查询structure 标签信息列表
     */
    @RequiresPermissions("htcpmd-center:structure_tag:list")
    @GetMapping("/list")
    public TableDataInfo list() {
//        startPage();
//        List<HtcpmdStructureTag> list = htcpmdStructureTagDomainService.selectHtcpmdStructureTagList(htcpmdStructureTag);
        Page<StructureTagDoc> structureTagDocPage = structureTagDocDomainService.findAll(PageSupport.getPageable());
        return getDataTable(structureTagDocPage);
    }

    /**
     * 导出structure 标签信息列表
     */
    @RequiresPermissions("htcpmd-center:structure_tag:export")
    @Log(title = "structure 标签信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HtcpmdStructureTag htcpmdStructureTag) {
//        List<HtcpmdStructureTag> list = htcpmdStructureTagDomainService.selectHtcpmdStructureTagList(htcpmdStructureTag);
        List<StructureTagDoc> list = structureTagDocDomainService.findAll();
        ExcelUtil<StructureTagDoc> util = new ExcelUtil<StructureTagDoc>(StructureTagDoc.class);
        util.exportExcel(response, list, "structure 标签信息数据");
    }

    /**
     * 获取structure 标签信息详细信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:query")
    @GetMapping(value = "/{name}")
    public AjaxResult getInfo(@PathVariable("name") String name) {
//        return success(htcpmdStructureTagDomainService.selectHtcpmdStructureTagById(id));
        return success(structureTagDocDomainService.findStructureTagDocByName(name));
    }

    /**
     * 新增structure 标签信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:add")
    @Log(title = "structure 标签信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StructureTagDoc structureTagDoc) throws BusinessException {
//        return toAjax(htcpmdStructureTagDomainService.insertHtcpmdStructureTag(htcpmdStructureTag));
        BusinessException.throwExceptionIfTrue(structureTagDocDomainService.existsByName(structureTagDoc.getName()), BusinessErrorStatusEnum.DUPLICATE_STRUCTURE_TAG, null);
        structureTagDoc.setUuid(IdUtil.simpleUUID());
        structureTagDoc.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        structureTagDoc.setCreateTime(DateTime.now());
        structureTagDoc.setGroupId(CustRequestHelper.getGroupId());
        return success(structureTagDocDomainService.save(structureTagDoc));
    }

    /**
     * 修改structure 标签信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:edit")
    @Log(title = "structure 标签信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StructureTagDoc structureTagDoc) {
//        return toAjax(htcpmdStructureTagDomainService.updateHtcpmdStructureTag(htcpmdStructureTag));
        structureTagDoc.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        structureTagDoc.setUpdateTime(DateTime.now());
        return success(structureTagDocDomainService.save(structureTagDoc));
    }

    /**
     * 删除structure 标签信息
     */
    @RequiresPermissions("htcpmd-center:structure_tag:remove")
    @Log(title = "structure 标签信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
//        return toAjax(htcpmdStructureTagDomainService.deleteHtcpmdStructureTagByIds(ids));
        structureTagDocDomainService.deleteById(id);
        return success();
    }
}
