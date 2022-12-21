package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdGroup;

import java.util.List;

/**
 * 群组 领域Service接口
 *
 * @author PorYoung
 * @date 2022-12-12
 */
public interface HtcpmdGroupDomainService
{
    /**
     * 查询群组
     *
     * @param id 群组主键
     * @return 群组
     */
    public HtcpmdGroup selectHtcpmdGroupById(String id);

    /**
     * 查询群组列表
     *
     * @param htcpmdGroup 群组
     * @return 群组集合
     */
    public List<HtcpmdGroup> selectHtcpmdGroupList(HtcpmdGroup htcpmdGroup);

    /**
     * 新增群组
     *
     * @param htcpmdGroup 群组
     * @return 结果
     */
    public int insertHtcpmdGroup(HtcpmdGroup htcpmdGroup);

    /**
     * 修改群组
     *
     * @param htcpmdGroup 群组
     * @return 结果
     */
    public int updateHtcpmdGroup(HtcpmdGroup htcpmdGroup);

    /**
     * 批量删除群组
     *
     * @param ids 需要删除的群组主键集合
     * @return 结果
     */
    public int deleteHtcpmdGroupByIds(String[] ids);

    /**
     * 删除群组信息
     *
     * @param id 群组主键
     * @return 结果
     */
    public int deleteHtcpmdGroupById(String id);
}
