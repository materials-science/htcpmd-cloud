package cn.poryoung.htcpmd.center.infrastructure.repository.impl;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdGroup;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdGroupRepository;
import cn.poryoung.htcpmd.center.infrastructure.persistence.mapper.HtcpmdGroupMapper;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 群组 Repository实现
 * 
 * @author PorYoung
 * @date 2022-12-12
 */
@Repository
public class HtcpmdGroupRepositoryImpl implements HtcpmdGroupRepository
{
    @Autowired
    private HtcpmdGroupMapper htcpmdGroupMapper;

    /**
     * 查询群组
     * 
     * @param id 群组主键
     * @return 群组
     */
    @Override
    public HtcpmdGroup selectHtcpmdGroupById(String id)
    {
        return htcpmdGroupMapper.selectHtcpmdGroupById(id);
    }

    /**
     * 查询群组列表
     * 
     * @param htcpmdGroup 群组
     * @return 群组
     */
    @Override
    public List<HtcpmdGroup> selectHtcpmdGroupList(HtcpmdGroup htcpmdGroup)
    {
        return htcpmdGroupMapper.selectHtcpmdGroupList(htcpmdGroup);
    }

    /**
     * 新增群组
     * 
     * @param htcpmdGroup 群组
     * @return 结果
     */
    @Override
    public int insertHtcpmdGroup(HtcpmdGroup htcpmdGroup)
    {
        htcpmdGroup.setCreateTime(DateUtils.getNowDate());
        return htcpmdGroupMapper.insertHtcpmdGroup(htcpmdGroup);
    }

    /**
     * 修改群组
     * 
     * @param htcpmdGroup 群组
     * @return 结果
     */
    @Override
    public int updateHtcpmdGroup(HtcpmdGroup htcpmdGroup)
    {
        htcpmdGroup.setUpdateTime(DateUtils.getNowDate());
        return htcpmdGroupMapper.updateHtcpmdGroup(htcpmdGroup);
    }

    /**
     * 批量删除群组
     * 
     * @param ids 需要删除的群组主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdGroupByIds(String[] ids)
    {
        return htcpmdGroupMapper.deleteHtcpmdGroupByIds(ids);
    }

    /**
     * 删除群组信息
     * 
     * @param id 群组主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdGroupById(String id)
    {
        return htcpmdGroupMapper.deleteHtcpmdGroupById(id);
    }
}
