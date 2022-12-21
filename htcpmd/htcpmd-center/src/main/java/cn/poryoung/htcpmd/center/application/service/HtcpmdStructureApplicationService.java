package cn.poryoung.htcpmd.center.application.service;

import cn.poryoung.htcpmd.center.application.dto.EsStructureSearchDto;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructure;
import cn.poryoung.htcpmd.center.domain.service.CommonFileDomainService;
import cn.poryoung.htcpmd.center.domain.service.EsStructureDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureDomainService;
import cn.poryoung.htcpmd.center.infrastructure.event.StructureEventPublisher;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * structure 信息 Application Service
 *
 * @author PorYoung
 * @date 2022-12-15
 */
@Api(tags = "StructureApplicationService", description = "材料结构管理")
@RestController
@RequestMapping("/structure")
public class HtcpmdStructureApplicationService extends BaseController {
    @Autowired
    private HtcpmdStructureDomainService htcpmdStructureDomainService;
    @Autowired
    private HtcpmdBizJobDomainService htcpmdBizJobDomainService;
    @Autowired
    private StructureEventPublisher structureEventPublisher;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CommonFileDomainService commonFileDomainService;

    @Autowired
    private EsStructureDomainService esStructureDomainService;

    private void cleanTempFiles(List<FileInfo> fileInfoList) {
        for (var fileInfo : fileInfoList) {
            fileStorageService.delete(fileInfo);
        }
    }

    @ApiOperation("上传structure结构")
    @PostMapping("/upload")
    public AjaxResult uploadStructureFiles(@RequestPart("file") List<MultipartFile> fileList) throws BusinessException, SystemException {
        // upload files
        List<FileInfo> fileInfoList = commonFileDomainService.upload(fileList);
        // publish an event
        Map<String, Object> eventObj = new HashMap<>();
        eventObj.put("fileList", fileInfoList);
        structureEventPublisher.publishUploadStructureEvent(eventObj);

        return success("已成功提交`导入结构信息`任务队列，请稍后查询结果。");
    }

    /**
     * 查询EsStructure 信息列表
     */
    @RequiresPermissions("htcpmd-center:structure:search")
    @GetMapping("/search")
    public TableDataInfo search(EsStructureSearchDto reqDto) {
        esStructureDomainService.search(reqDto);

        return null;
    }

    /**
     * 查询structure 信息列表
     */
    @RequiresPermissions("htcpmd-center:structure:list")
    @GetMapping("/list")
    public TableDataInfo list(HtcpmdStructure htcpmdStructure) {
        startPage();
        List<HtcpmdStructure> list = htcpmdStructureDomainService.selectHtcpmdStructureList(htcpmdStructure);
        return getDataTable(list);
    }

    /**
     * 导出structure 信息列表
     */
    @RequiresPermissions("htcpmd-center:structure:export")
    @Log(title = "structure 信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HtcpmdStructure htcpmdStructure) {
        List<HtcpmdStructure> list = htcpmdStructureDomainService.selectHtcpmdStructureList(htcpmdStructure);
        ExcelUtil<HtcpmdStructure> util = new ExcelUtil<HtcpmdStructure>(HtcpmdStructure.class);
        util.exportExcel(response, list, "structure 信息数据");
    }

    /**
     * 获取structure 信息详细信息
     */
    @RequiresPermissions("htcpmd-center:structure:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(htcpmdStructureDomainService.selectHtcpmdStructureById(id));
    }

    /**
     * 新增structure 信息
     */
    @RequiresPermissions("htcpmd-center:structure:add")
    @Log(title = "structure 信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HtcpmdStructure htcpmdStructure) {
        return toAjax(htcpmdStructureDomainService.insertHtcpmdStructure(htcpmdStructure));
    }

    /**
     * 修改structure 信息
     */
    @RequiresPermissions("htcpmd-center:structure:edit")
    @Log(title = "structure 信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HtcpmdStructure htcpmdStructure) {
        return toAjax(htcpmdStructureDomainService.updateHtcpmdStructure(htcpmdStructure));
    }

    /**
     * 删除structure 信息
     */
    @RequiresPermissions("htcpmd-center:structure:remove")
    @Log(title = "structure 信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(htcpmdStructureDomainService.deleteHtcpmdStructureByIds(ids));
    }
}
