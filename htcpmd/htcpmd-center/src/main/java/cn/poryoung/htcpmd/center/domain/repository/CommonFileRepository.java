package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.CommonFile;

import java.util.List;

/**
 * 文件信息 Repository接口
 *
 * @author PorYoung
 * @date 2022-12-14
 */
public interface CommonFileRepository {
    /**
     * 查询文件信息
     *
     * @param id 文件信息主键
     * @return 文件信息
     */
    public CommonFile selectCommonFileById(String id);

    /**
     * 通过url查询文件信息
     *
     * @param url 文件url
     * @return 文件信息
     */
    public CommonFile selectCommonFileByUrl(String url);

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
     * 删除文件信息
     *
     * @param id 文件信息主键
     * @return 结果
     */
    public int deleteCommonFileById(String id);

    /**
     * 批量删除文件信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommonFileByIds(String[] ids);

    /**
     * 通过Url删除文件信息
     *
     * @param url 文件url
     * @return 结果
     */
    public int deleteCommonFileByUrl(String url);
}
