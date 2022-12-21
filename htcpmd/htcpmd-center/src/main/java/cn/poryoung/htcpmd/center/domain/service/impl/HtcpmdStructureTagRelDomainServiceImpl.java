package cn.poryoung.htcpmd.center.domain.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureTagRelRepository;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTagRel;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureTagRelDomainService;

/**
 * structure 标签关联 领域Service业务层实现
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
@Service
public class HtcpmdStructureTagRelDomainServiceImpl implements HtcpmdStructureTagRelDomainService
{
    @Autowired
    private HtcpmdStructureTagRelRepository htcpmdStructureTagRelRepository;

    /**
     * 查询structure 标签关联
     * 
     * @param id structure 标签关联主键
     * @return structure 标签关联
     */
    @Override
    public HtcpmdStructureTagRel selectHtcpmdStructureTagRelById(String id)
    {
        return htcpmdStructureTagRelRepository.selectHtcpmdStructureTagRelById(id);
    }

    /**
     * 查询structure 标签关联列表
     * 
     * @param htcpmdStructureTagRel structure 标签关联
     * @return structure 标签关联
     */
    @Override
    public List<HtcpmdStructureTagRel> selectHtcpmdStructureTagRelList(HtcpmdStructureTagRel htcpmdStructureTagRel)
    {
        return htcpmdStructureTagRelRepository.selectHtcpmdStructureTagRelList(htcpmdStructureTagRel);
    }

    /**
     * 新增structure 标签关联
     * 
     * @param htcpmdStructureTagRel structure 标签关联
     * @return 结果
     */
    @Override
    public int insertHtcpmdStructureTagRel(HtcpmdStructureTagRel htcpmdStructureTagRel)
    {
        htcpmdStructureTagRel.setCreateTime(DateUtils.getNowDate());
        return htcpmdStructureTagRelRepository.insertHtcpmdStructureTagRel(htcpmdStructureTagRel);
    }

    /**
     * 修改structure 标签关联
     * 
     * @param htcpmdStructureTagRel structure 标签关联
     * @return 结果
     */
    @Override
    public int updateHtcpmdStructureTagRel(HtcpmdStructureTagRel htcpmdStructureTagRel)
    {
        htcpmdStructureTagRel.setUpdateTime(DateUtils.getNowDate());
        return htcpmdStructureTagRelRepository.updateHtcpmdStructureTagRel(htcpmdStructureTagRel);
    }

    /**
     * 批量删除structure 标签关联
     * 
     * @param ids 需要删除的structure 标签关联主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureTagRelByIds(String[] ids)
    {
        return htcpmdStructureTagRelRepository.deleteHtcpmdStructureTagRelByIds(ids);
    }

    /**
     * 删除structure 标签关联信息
     * 
     * @param id structure 标签关联主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureTagRelById(String id)
    {
        return htcpmdStructureTagRelRepository.deleteHtcpmdStructureTagRelById(id);
    }
}
