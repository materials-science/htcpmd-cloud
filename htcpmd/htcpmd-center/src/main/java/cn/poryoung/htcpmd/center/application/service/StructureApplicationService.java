package cn.poryoung.htcpmd.center.application.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.application.dto.EsStructureSearchDto;
import cn.poryoung.htcpmd.center.application.dto.StructureUploadDiagramDataDto;
import cn.poryoung.htcpmd.center.domain.entity.structure.EsStructure;
import cn.poryoung.htcpmd.center.domain.entity.structure.StructureDiagramDoc;
import cn.poryoung.htcpmd.center.domain.entity.structure.StructureDoc;
import cn.poryoung.htcpmd.center.domain.repository.StructureDiagramDocRepository;
import cn.poryoung.htcpmd.center.domain.service.CommonFileDomainService;
import cn.poryoung.htcpmd.center.domain.service.EsStructureDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdBizJobDomainService;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureDocDomainService;
import cn.poryoung.htcpmd.center.infrastructure.event.StructureEventPublisher;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.BaseApplicationService;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * structure 信息 Application Service
 *
 * @author PorYoung
 * @date 2022-12-15
 */
@Api(tags = "StructureApplicationService", description = "材料结构管理")
@RestController
@RequestMapping("/structure")
public class StructureApplicationService extends BaseApplicationService {
    private static final cn.hutool.log.Log log = LogFactory.get();

    @Autowired
    private HtcpmdStructureDocDomainService htcpmdStructureDocDomainService;
    @Autowired
    private HtcpmdBizJobDomainService htcpmdBizJobDomainService;
    @Autowired
    private StructureEventPublisher structureEventPublisher;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CommonFileDomainService commonFileDomainService;
    @Autowired
    private StructureDiagramDocRepository structureDiagramDocRepository;
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

    @ApiOperation("从aiida导出压缩包导入structure结构")
    @PostMapping("/import/aiida")
    public AjaxResult importStructureFromAiida(@RequestPart("file") MultipartFile file, @RequestParam("tagNameList") Set<String> tagNameList) throws BusinessException, SystemException {
        // upload files
        List<FileInfo> fileInfoList = commonFileDomainService.upload(Collections.singletonList(file));
        // publish an event
        Map<String, Object> eventObj = new HashMap<>();
        eventObj.put("file", fileInfoList.get(0));
        eventObj.put("tags", tagNameList);
        structureEventPublisher.publishImportStructureEvent("aiida", eventObj);

        return success("已成功提交`导入结构信息`任务队列，请稍后查询结果。");
    }

    /**
     * upload structure phonon data
     */
    @ApiOperation("upload structure diagram data")
    @RequiresPermissions("htcpmd-center:structure:upload:diagram")
    @PostMapping("/upload/{key}")
    public AjaxResult upload(@RequestBody StructureUploadDiagramDataDto reqDto, @PathVariable("key") String key) throws BusinessException, SystemException {
        StructureDiagramDoc structureDiagramDoc = BeanUtil.copyProperties(reqDto, StructureDiagramDoc.class);
        structureDiagramDoc.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        structureDiagramDoc.setCreateTime(DateTime.now());

        return success(htcpmdStructureDocDomainService.pushObjToRefs(reqDto.getStructureId(), key + "List", structureDiagramDoc));
    }

    /**
     * 查询EsStructure 信息列表
     */
    @ApiOperation("搜索structure信息")
    @RequiresPermissions("htcpmd-center:structure:search")
    @PostMapping("/search")
    public TableDataInfo search(@RequestBody EsStructureSearchDto reqDto) {
        Page<EsStructure> structurePage = esStructureDomainService.search(reqDto, PageSupport.getPageableBeginWith0());
        return getDataTable(structurePage);
    }

    /**
     * 获取structure详细信息
     */
    @ApiOperation("获取structure详细信息")
    @RequiresPermissions("htcpmd-center:structure:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(htcpmdStructureDocDomainService.findHtcpmdStructureDocById(id));
    }

    /**
     * 删除structure 信息
     */
    @RequiresPermissions("htcpmd-center:structure:remove")
    @Log(title = "structure 信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id) {
        StructureDoc deleted = htcpmdStructureDocDomainService.deleteById(id);
        if (Objects.isNull(deleted)) {
            esStructureDomainService.deleteByUuid(id);
            structureDiagramDocRepository.deleteAllByStructureId(id);
        } else {
            esStructureDomainService.deleteByUuid(deleted.getUuid());
            structureDiagramDocRepository.deleteAllByStructureId(deleted.getUuid());
        }
        return success();
    }
}
