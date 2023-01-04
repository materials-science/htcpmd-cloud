package cn.poryoung.htcpmd.center.application.service;


import cn.poryoung.htcpmd.center.domain.entity.CommonFile;
import cn.poryoung.htcpmd.center.domain.service.impl.CommonFileDomainServiceImpl;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息 Application Service
 *
 * @author PorYoung
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/common_file")
public class CommonFileApplicationService extends BaseController {
    @Autowired
    private CommonFileDomainServiceImpl commonFileDomainService;
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * upload file
     */
    @RequiresPermissions("htcpmd-common:common_file:upload")
    @PostMapping("/upload")
    public AjaxResult upload(@RequestPart("file") List<MultipartFile> fileList) throws BusinessException, SystemException {
        return AjaxResult.success(commonFileDomainService.upload(fileList));
    }

    /**
     * download file
     */
    @RequiresPermissions("htcpmd-common:common_file:download")
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] download(@RequestParam("url") String url) {
        return fileStorageService.download(url).bytes();
    }

    /**
     * 查询文件信息列表
     */
    @RequiresPermissions("htcpmd-common:common_file:list")
    @GetMapping("/list")
    public TableDataInfo list(CommonFile commonFile) {
        startPage();
        List<CommonFile> list = commonFileDomainService.selectCommonFileList(commonFile);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @RequiresPermissions("htcpmd-common:common_file:export")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CommonFile commonFile) {
        List<CommonFile> list = commonFileDomainService.selectCommonFileList(commonFile);
        ExcelUtil<CommonFile> util = new ExcelUtil<CommonFile>(CommonFile.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @RequiresPermissions("htcpmd-common:common_file:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(commonFileDomainService.selectCommonFileById(id));
    }

    /**
     * 新增文件信息
     */
    @RequiresPermissions("htcpmd-common:common_file:add")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CommonFile commonFile) {
        return toAjax(commonFileDomainService.insertCommonFile(commonFile));
    }

    /**
     * 修改文件信息
     */
    @RequiresPermissions("htcpmd-common:common_file:edit")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CommonFile commonFile) {
        return toAjax(commonFileDomainService.updateCommonFile(commonFile));
    }

    /**
     * 删除文件信息
     */
    @RequiresPermissions("htcpmd-common:common_file:remove")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(commonFileDomainService.deleteCommonFileByIds(ids));
    }
}

