package cn.poryoung.htcpmd.center.domain.service.impl;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructure;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureRepository;
import cn.poryoung.htcpmd.center.domain.service.HtcpmdStructureDomainService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * structure 信息 领域Service业务层实现
 *
 * @author PorYoung
 * @date 2022-12-16
 */
@Service
public class HtcpmdStructureDomainServiceImpl implements HtcpmdStructureDomainService {
    @Autowired
    private HtcpmdStructureRepository htcpmdStructureRepository;

    /**
     * 查询structure 信息
     *
     * @param id structure 信息主键
     * @return structure 信息
     */
    @Override
    public HtcpmdStructure selectHtcpmdStructureById(String id) {
        return htcpmdStructureRepository.selectHtcpmdStructureById(id);
    }

    /**
     * 查询structure 信息
     *
     * @param uuid structure 信息主键
     * @return structure 信息
     */
    @Override
    public HtcpmdStructure selectHtcpmdStructureByUuid(String uuid) {
        return htcpmdStructureRepository.selectHtcpmdStructureByUuid(uuid);
    }

    /**
     * 查询structure 信息列表
     *
     * @param htcpmdStructure structure 信息
     * @return structure 信息
     */
    @Override
    public List<HtcpmdStructure> selectHtcpmdStructureList(HtcpmdStructure htcpmdStructure) {
        return htcpmdStructureRepository.selectHtcpmdStructureList(htcpmdStructure);
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
        return htcpmdStructureRepository.insertHtcpmdStructure(htcpmdStructure);
    }

    @Override
    public int insertBatch(List<HtcpmdStructure> htcpmdStructureList) {
        return htcpmdStructureRepository.insertBatch(htcpmdStructureList);
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
        return htcpmdStructureRepository.updateHtcpmdStructure(htcpmdStructure);
    }

    /**
     * 批量删除structure 信息
     *
     * @param ids 需要删除的structure 信息主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureByIds(String[] ids) {
        return htcpmdStructureRepository.deleteHtcpmdStructureByIds(ids);
    }

    /**
     * 删除structure 信息信息
     *
     * @param id structure 信息主键
     * @return 结果
     */
    @Override
    public int deleteHtcpmdStructureById(String id) {
        return htcpmdStructureRepository.deleteHtcpmdStructureById(id);
    }
}
