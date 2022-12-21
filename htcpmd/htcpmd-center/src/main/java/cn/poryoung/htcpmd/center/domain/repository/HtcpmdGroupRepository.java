package cn.poryoung.htcpmd.center.domain.repository;

import java.util.List;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdGroup;

/**
 * 群组 Repository接口
 * 
 * @author PorYoung
 * @date 2022-12-12
 */
public interface HtcpmdGroupRepository
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
     * 删除群组
     * 
     * @param id 群组主键
     * @return 结果
     */
    public int deleteHtcpmdGroupById(String id);

    /**
     * 批量删除群组
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHtcpmdGroupByIds(String[] ids);
}
