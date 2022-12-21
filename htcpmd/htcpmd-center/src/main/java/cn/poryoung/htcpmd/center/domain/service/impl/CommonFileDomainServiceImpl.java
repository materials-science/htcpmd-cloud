package cn.poryoung.htcpmd.center.domain.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.poryoung.htcpmd.center.domain.entity.CommonFile;
import cn.poryoung.htcpmd.center.domain.repository.CommonFileRepository;
import cn.poryoung.htcpmd.center.domain.service.CommonFileDomainService;
import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommonFileDomainServiceImpl implements CommonFileDomainService {

    @Autowired
    private CommonFileRepository commonFileRepository;
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * clean unused files
     */
    private void cleanTempFiles(List<FileInfo> fileInfoList) {
        for (var fileInfo : fileInfoList) {
            fileStorageService.delete(fileInfo);
        }
    }

    @Override
    public List<FileInfo> upload(List<MultipartFile> fileList) throws BusinessException, SystemException {
        // upload files
        BusinessException.throwExceptionIfTrue(ArrayUtil.isEmpty(fileList), BusinessErrorStatusEnum.INVALID_REQUEST_PARAMETERS.getCode(), "上传文件为空！");
        List<FileInfo> fileInfoList = new ArrayList<>(fileList.size());
        for (var file : fileList) {
            FileInfo fileInfo = fileStorageService.of(file).upload();
            if (Objects.isNull(fileInfo)) {
                cleanTempFiles(fileInfoList);
                throw new BusinessException(BusinessErrorStatusEnum.FILE_UPLOAD_ERROR);
            }
            fileInfoList.add(fileInfo);
        }

        return fileInfoList;
    }

    /**
     * 查询文件信息
     *
     * @param id 文件信息主键
     * @return 文件信息
     */
    @Override
    public CommonFile selectCommonFileById(String id) {
        return commonFileRepository.selectCommonFileById(id);
    }

    /**
     * 查询文件信息列表
     *
     * @param commonFile 文件信息
     * @return 文件信息
     */
    @Override
    public List<CommonFile> selectCommonFileList(CommonFile commonFile) {
        return commonFileRepository.selectCommonFileList(commonFile);
    }

    /**
     * 新增文件信息
     *
     * @param commonFile 文件信息
     * @return 结果
     */
    @Override
    public int insertCommonFile(CommonFile commonFile) {
        commonFile.setCreateTime(DateUtils.getNowDate());
        return commonFileRepository.insertCommonFile(commonFile);
    }

    /**
     * 修改文件信息
     *
     * @param commonFile 文件信息
     * @return 结果
     */
    @Override
    public int updateCommonFile(CommonFile commonFile) {
        commonFile.setUpdateTime(DateUtils.getNowDate());
        return commonFileRepository.updateCommonFile(commonFile);
    }

    /**
     * 批量删除文件信息
     *
     * @param ids 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonFileByIds(String[] ids) {
        return commonFileRepository.deleteCommonFileByIds(ids);
    }

    /**
     * 删除文件信息信息
     *
     * @param id 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonFileById(String id) {
        return commonFileRepository.deleteCommonFileById(id);
    }
}
