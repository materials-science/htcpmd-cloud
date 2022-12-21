package cn.poryoung.htcpmd.center.domain.service;


import cn.poryoung.htcpmd.center.domain.entity.CommonFile;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.xuyanwu.spring.file.storage.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件信息 领域Service接口
 *
 * @author PorYoung
 * @date 2022-12-14
 */
public interface CommonFileDomainService {

    /**
     * upload files
     */
    public List<FileInfo> upload(List<MultipartFile> fileList) throws BusinessException, SystemException;

    /**
     * 查询文件信息
     *
     * @param id 文件信息主键
     * @return 文件信息
     */
    public CommonFile selectCommonFileById(String id);

    /**
     * 查询文件信息列表
     *
     * @param commonFile 文件信息
     * @return 文件信息集合
     */
    public List<CommonFile> selectCommonFileList(CommonFile commonFile);

    /**
     * 新增文件信息
     *
     * @param commonFile 文件信息
     * @return 结果
     */
    public int insertCommonFile(CommonFile commonFile);

    /**
     * 修改文件信息
     *
     * @param commonFile 文件信息
     * @return 结果
     */
    public int updateCommonFile(CommonFile commonFile);

    /**
     * 批量删除文件信息
     *
     * @param ids 需要删除的文件信息主键集合
     * @return 结果
     */
    public int deleteCommonFileByIds(String[] ids);

    /**
     * 删除文件信息信息
     *
     * @param id 文件信息主键
     * @return 结果
     */
    public int deleteCommonFileById(String id);
}
