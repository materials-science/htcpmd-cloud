package cn.poryoung.htcpmd.center.infrastructure.repository.impl;

import cn.poryoung.htcpmd.center.domain.entity.CommonFile;
import cn.poryoung.htcpmd.center.domain.repository.CommonFileRepository;
import cn.poryoung.htcpmd.center.infrastructure.persistence.mapper.CommonFileMapper;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件信息 Repository实现
 *
 * @author PorYoung
 * @date 2022-12-14
 */
@Repository
public class CommonFileRepositoryImpl implements CommonFileRepository {
    @Autowired
    private CommonFileMapper commonFileMapper;

    /**
     * 查询文件信息
     *
     * @param id 文件信息主键
     * @return 文件信息
     */
    @Override
    public CommonFile selectCommonFileById(String id) {
        return commonFileMapper.selectCommonFileById(id);
    }

    @Override
    public CommonFile selectCommonFileByUrl(String url) {
        return commonFileMapper.selectCommonFileByUrl(url);
    }

    /**
     * 查询文件信息列表
     *
     * @param commonFile 文件信息
     * @return 文件信息
     */
    @Override
    public List<CommonFile> selectCommonFileList(CommonFile commonFile) {
        return commonFileMapper.selectCommonFileList(commonFile);
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
        return commonFileMapper.insertCommonFile(commonFile);
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
        return commonFileMapper.updateCommonFile(commonFile);
    }

    /**
     * 批量删除文件信息
     *
     * @param ids 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonFileByIds(String[] ids) {
        return commonFileMapper.deleteCommonFileByIds(ids);
    }

    /**
     * 删除文件信息信息
     *
     * @param id 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteCommonFileById(String id) {
        return commonFileMapper.deleteCommonFileById(id);
    }

    @Override
    public int deleteCommonFileByUrl(String url) {
        return commonFileMapper.deleteCommonFileByUrl(url);
    }
}
