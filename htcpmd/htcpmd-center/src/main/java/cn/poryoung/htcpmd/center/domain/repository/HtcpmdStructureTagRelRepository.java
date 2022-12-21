package cn.poryoung.htcpmd.center.domain.repository;

import java.util.List;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTagRel;

/**
 * structure 标签关联 Repository接口
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
public interface HtcpmdStructureTagRelRepository
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
     * 删除structure 标签关联
     * 
     * @param id structure 标签关联主键
     * @return 结果
     */
    public int deleteHtcpmdStructureTagRelById(String id);

    /**
     * 批量删除structure 标签关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHtcpmdStructureTagRelByIds(String[] ids);
}
