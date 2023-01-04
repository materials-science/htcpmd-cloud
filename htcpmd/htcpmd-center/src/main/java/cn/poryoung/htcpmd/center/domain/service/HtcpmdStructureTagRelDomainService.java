package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTagRel;

import java.util.List;
import java.util.Set;

/**
 * structure 标签关联 领域Service接口
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
public interface HtcpmdStructureTagRelDomainService
{
    /**
     * 查询structure 标签关联
     * 
     * @param id structure 标签关联主键
     * @return structure 标签关联
     */
    public HtcpmdStructureTagRel selectHtcpmdStructureTagRelById(String id);

    /**
     * 查询structure 标签关联列表
     * 
     * @param htcpmdStructureTagRel structure 标签关联
     * @return structure 标签关联集合
     */
    public List<HtcpmdStructureTagRel> selectHtcpmdStructureTagRelList(HtcpmdStructureTagRel htcpmdStructureTagRel);

    /**
     * 新增structure 标签关联
     * 
     * @param htcpmdStructureTagRel structure 标签关联
     * @return 结果
     */
    public int insertHtcpmdStructureTagRel(HtcpmdStructureTagRel htcpmdStructureTagRel);

    /**
     * 修改structure 标签关联
     * 
     * @param htcpmdStructureTagRel structure 标签关联
     * @return 结果
     */
    public int updateHtcpmdStructureTagRel(HtcpmdStructureTagRel htcpmdStructureTagRel);

    /**
     * 批量删除structure 标签关联
     * 
     * @param ids 需要删除的structure 标签关联主键集合
     * @return 结果
     */
    public int deleteHtcpmdStructureTagRelByIds(String[] ids);

    /**
     * 删除structure 标签关联信息
     * 
     * @param id structure 标签关联主键
     * @return 结果
     */
    public int deleteHtcpmdStructureTagRelById(String id);

    public int insertBatch(List<HtcpmdStructureTagRel> htcpmdStructureTagRels);
}
