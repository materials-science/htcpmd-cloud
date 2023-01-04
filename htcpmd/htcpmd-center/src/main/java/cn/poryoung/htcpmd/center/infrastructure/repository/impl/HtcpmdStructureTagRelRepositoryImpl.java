package cn.poryoung.htcpmd.center.infrastructure.repository.impl;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureTagRel;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureTagRelRepository;
import cn.poryoung.htcpmd.center.infrastructure.persistence.mapper.HtcpmdStructureTagRelMapper;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * structure 标签关联 Repository实现
 *
 * @author PorYoung
 * @date 2022-12-15
 */
@Repository
public class HtcpmdStructureTagRelRepositoryImpl implements HtcpmdStructureTagRelRepository {
    @Autowired
    private HtcpmdStructureTagRelMapper htcpmdStructureTagRelMapper;

    /**
     * 查询structure 标签关联
     *
     * @param id structure 标签关联主键
     * @return structure 标签关联
     */
    @Override
    public HtcpmdStructureTagRel selectHtcpmdStructureTagRelById(String id) {
        return htcpmdStructureTagRelMapper.selectHtcpmdStructureTagRelById(id);
    }

    /**
     * 查询structure 标签关联列表
     *
     * @param htcpmdStructureTagRel structure 标签关联
     * @return structure 标签关联
     */
    @Override
    public List<HtcpmdStructureTagRel> selectHtcpmdStructureTagRelList(HtcpmdStructureTagRel htcpmdStructureTagRel) {
        return htcpmdStructureTagRelMapper.selectHtcpmdStructureTagRelList(htcpmdStructureTagRel);
    }

    /**
     * 新增structure 标签关联
     *
     * @param htcpmdStructureTagRel structure 标签关联
     * @return 结果
     */
    @Override
    public int insertHtcpmdStructureTagRel(HtcpmdStructureTagRel htcpmdStructureTagRel) {
        htcpmdStructureTagRel.setCreateTime(DateUtils.getNowDate());
        return htcpmdStructureTagRelMapper.insertHtcpmdStructureTagRel(htcpmdStructureTagRel);
    }

    /**
     * 修改structure 标签关联
     *
     * @param htcpmdStructureTagRel structure 标签关联
     * @return 结果
     */
    @Override
    public int updateHtcpmdStructureTagRel(HtcpmdStructureTagRel htcpmdStructureTagRel) {
        htcpmdStructureTagRel.setUpdateTime(DateUtils.getNowDate());
        return htcpmdStructureTagRelMapper.updateHtcpmdStructureTagRel(htcpmdStructureTagRel);
    }

    /**
     * 批量删除structure 标签关联
     *
     * @param ids 需要删除的structure 标签关联主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureTagRelByIds(String[] ids) {
        return htcpmdStructureTagRelMapper.deleteHtcpmdStructureTagRelByIds(ids);
    }

    /**
     * 删除structure 标签关联信息
     *
     * @param id structure 标签关联主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureTagRelById(String id) {
        return htcpmdStructureTagRelMapper.deleteHtcpmdStructureTagRelById(id);
    }

    @Override
    public int insertBatch(List<HtcpmdStructureTagRel> htcpmdStructureTagRels) {
        return htcpmdStructureTagRelMapper.insertBatch(htcpmdStructureTagRels);
    }
}
