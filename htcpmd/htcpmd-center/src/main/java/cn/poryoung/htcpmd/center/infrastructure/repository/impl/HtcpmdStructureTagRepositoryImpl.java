package cn.poryoung.htcpmd.center.infrastructure.repository.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cn.poryoung.htcpmd.center.infrastructure.persistence.mapper.HtcpmdStructureTagMapper;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTag;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureTagRepository;

/**
 * structure 标签信息 Repository实现
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
@Repository
public class HtcpmdStructureTagRepositoryImpl implements HtcpmdStructureTagRepository
{
    @Autowired
    private HtcpmdStructureTagMapper htcpmdStructureTagMapper;

    /**
     * 查询structure 标签信息
     * 
     * @param id structure 标签信息主键
     * @return structure 标签信息
     */
    @Override
    public HtcpmdStructureTag selectHtcpmdStructureTagById(String id)
    {
        return htcpmdStructureTagMapper.selectHtcpmdStructureTagById(id);
    }

    /**
     * 查询structure 标签信息列表
     * 
     * @param htcpmdStructureTag structure 标签信息
     * @return structure 标签信息
     */
    @Override
    public List<HtcpmdStructureTag> selectHtcpmdStructureTagList(HtcpmdStructureTag htcpmdStructureTag)
    {
        return htcpmdStructureTagMapper.selectHtcpmdStructureTagList(htcpmdStructureTag);
    }

    /**
     * 新增structure 标签信息
     * 
     * @param htcpmdStructureTag structure 标签信息
     * @return 结果
     */
    @Override
    public int insertHtcpmdStructureTag(HtcpmdStructureTag htcpmdStructureTag)
    {
        htcpmdStructureTag.setCreateTime(DateUtils.getNowDate());
        return htcpmdStructureTagMapper.insertHtcpmdStructureTag(htcpmdStructureTag);
    }

    /**
     * 修改structure 标签信息
     * 
     * @param htcpmdStructureTag structure 标签信息
     * @return 结果
     */
    @Override
    public int updateHtcpmdStructureTag(HtcpmdStructureTag htcpmdStructureTag)
    {
        htcpmdStructureTag.setUpdateTime(DateUtils.getNowDate());
        return htcpmdStructureTagMapper.updateHtcpmdStructureTag(htcpmdStructureTag);
    }

    /**
     * 批量删除structure 标签信息
     * 
     * @param ids 需要删除的structure 标签信息主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureTagByIds(String[] ids)
    {
        return htcpmdStructureTagMapper.deleteHtcpmdStructureTagByIds(ids);
    }

    /**
     * 删除structure 标签信息信息
     * 
     * @param id structure 标签信息主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureTagById(String id)
    {
        return htcpmdStructureTagMapper.deleteHtcpmdStructureTagById(id);
    }
}
