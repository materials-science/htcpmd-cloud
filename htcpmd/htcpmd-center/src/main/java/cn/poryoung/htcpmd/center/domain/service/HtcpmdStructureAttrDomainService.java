package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureAttr;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureAttrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HtcpmdStructureAttrDomainService {
    @Autowired
    private HtcpmdStructureAttrRepository htcpmdStructureAttrRepository;

    /**
     * 保存 structure attr信息
     */
    public HtcpmdStructureAttr save(HtcpmdStructureAttr structureAttr) throws Exception {
        return htcpmdStructureAttrRepository.save(structureAttr);
    }

    /**
     * 批量保存 structure attr信息
     */
    public List<HtcpmdStructureAttr> saveAll(Iterable<HtcpmdStructureAttr> structureAttrList) throws Exception {
        return htcpmdStructureAttrRepository.saveAll(structureAttrList);
    }
}
