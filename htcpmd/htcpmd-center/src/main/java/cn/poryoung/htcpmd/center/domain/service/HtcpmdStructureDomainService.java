package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructure;

import java.util.List;

/**
 * structure 信息 领域Service接口
 *
 * @author PorYoung
 * @date 2022-12-16
 */
public interface HtcpmdStructureDomainService {
    /**
     * 查询structure 信息
     *
     * @param id structure 信息主键
     * @return structure 信息
     */
    public HtcpmdStructure selectHtcpmdStructureById(String id);

    /**
     * 查询structure 信息
     *
     * @param uuid structure 信息主键
     * @return structure 信息
     */
    public HtcpmdStructure selectHtcpmdStructureByUuid(String uuid);

    /**
     * 查询structure 信息列表
     *
     * @param htcpmdStructure structure 信息
     * @return structure 信息集合
     */
    public List<HtcpmdStructure> selectHtcpmdStructureList(HtcpmdStructure htcpmdStructure);

    /**
     * 新增structure 信息
     *
     * @param htcpmdStructure structure 信息
     * @return 结果
     */
    public int insertHtcpmdStructure(HtcpmdStructure htcpmdStructure);

    /**
     * 批量新增structure 信息
     *
     * @param Set<HtcpmdStructure> htcpmdStructureList
     * @return 结果
     */
    public int insertBatch(List<HtcpmdStructure> htcpmdStructureList);

    /**
     * 修改structure 信息
     *
     * @param htcpmdStructure structure 信息
     * @return 结果
     */
    public int updateHtcpmdStructure(HtcpmdStructure htcpmdStructure);

    /**
     * 批量删除structure 信息
     *
     * @param ids 需要删除的structure 信息主键集合
     * @return 结果
     */
    public int deleteHtcpmdStructureByIds(String[] ids);

    /**
     * 删除structure 信息信息
     *
     * @param id structure 信息主键
     * @return 结果
     */
    public int deleteHtcpmdStructureById(String id);
}
