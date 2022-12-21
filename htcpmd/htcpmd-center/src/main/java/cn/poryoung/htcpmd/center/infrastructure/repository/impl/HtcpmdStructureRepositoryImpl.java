package cn.poryoung.htcpmd.center.infrastructure.repository.impl;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructure;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureRepository;
import cn.poryoung.htcpmd.center.infrastructure.persistence.mapper.HtcpmdStructureMapper;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * structure 信息 Repository实现
 *
 * @author PorYoung
 * @date 2022-12-16
 */
@Repository
public class HtcpmdStructureRepositoryImpl implements HtcpmdStructureRepository {
    @Autowired
    private HtcpmdStructureMapper htcpmdStructureMapper;

    /**
     * 查询structure 信息
     *
     * @param id structure 信息主键
     * @return structure 信息
     */
    @Override
    public HtcpmdStructure selectHtcpmdStructureById(String id) {
        return htcpmdStructureMapper.selectHtcpmdStructureById(id);
    }

    /**
     * 查询structure 信息列表
     *
     * @param htcpmdStructure structure 信息
     * @return structure 信息
     */
    @Override
    public List<HtcpmdStructure> selectHtcpmdStructureList(HtcpmdStructure htcpmdStructure) {
        return htcpmdStructureMapper.selectHtcpmdStructureList(htcpmdStructure);
    }

    /**
     * 新增structure 信息
     *
     * @param htcpmdStructure structure 信息
     * @return 结果
     */
    @Override
    public int insertHtcpmdStructure(HtcpmdStructure htcpmdStructure) {
        htcpmdStructure.setCreateTime(DateUtils.getNowDate());
        return htcpmdStructureMapper.insertHtcpmdStructure(htcpmdStructure);
    }

    @Override
    public int insertBatch(Set<HtcpmdStructure> htcpmdStructureList) {
        return htcpmdStructureMapper.insertBatch(htcpmdStructureList);
    }

    /**
     * 修改structure 信息
     *
     * @param htcpmdStructure structure 信息
     * @return 结果
     */
    @Override
    public int updateHtcpmdStructure(HtcpmdStructure htcpmdStructure) {
        htcpmdStructure.setUpdateTime(DateUtils.getNowDate());
        return htcpmdStructureMapper.updateHtcpmdStructure(htcpmdStructure);
    }

    /**
     * 批量删除structure 信息
     *
     * @param ids 需要删除的structure 信息主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureByIds(String[] ids) {
        return htcpmdStructureMapper.deleteHtcpmdStructureByIds(ids);
    }

    /**
     * 删除structure 信息信息
     *
     * @param id structure 信息主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureById(String id) {
        return htcpmdStructureMapper.deleteHtcpmdStructureById(id);
    }
}
