package cn.poryoung.htcpmd.center.domain.service;

import java.util.List;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTag;

/**
 * structure 标签信息 领域Service接口
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
public interface HtcpmdStructureTagDomainService
{
    /**
     * 查询structure 标签信息
     * 
     * @param id structure 标签信息主键
     * @return structure 标签信息
     */
    public HtcpmdStructureTag selectHtcpmdStructureTagById(String id);

    /**
     * 查询structure 标签信息列表
     * 
     * @param htcpmdStructureTag structure 标签信息
     * @return structure 标签信息集合
     */
    public List<HtcpmdStructureTag> selectHtcpmdStructureTagList(HtcpmdStructureTag htcpmdStructureTag);

    /**
     * 新增structure 标签信息
     * 
     * @param htcpmdStructureTag structure 标签信息
     * @return 结果
     */
    public int insertHtcpmdStructureTag(HtcpmdStructureTag htcpmdStructureTag);

    /**
     * 修改structure 标签信息
     * 
     * @param htcpmdStructureTag structure 标签信息
     * @return 结果
     */
    public int updateHtcpmdStructureTag(HtcpmdStructureTag htcpmdStructureTag);

    /**
     * 批量删除structure 标签信息
     * 
     * @param ids 需要删除的structure 标签信息主键集合
     * @return 结果
     */
    public int deleteHtcpmdStructureTagByIds(String[] ids);

    /**
     * 删除structure 标签信息信息
     * 
     * @param id structure 标签信息主键
     * @return 结果
     */
    public int deleteHtcpmdStructureTagById(String id);
}
