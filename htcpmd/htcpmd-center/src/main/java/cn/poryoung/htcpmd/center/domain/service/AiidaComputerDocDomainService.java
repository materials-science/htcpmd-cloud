package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.aiida.ComputerDoc;
import cn.poryoung.htcpmd.center.domain.repository.AiidaComputerDocRepository;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiidaComputerDocDomainService {
    @Autowired
    private AiidaComputerDocRepository aiidaComputerDocRepository;

    public void deleteById(String id) {
        aiidaComputerDocRepository.deleteByUuidOrIdAndGroupId(id, id, CustRequestHelper.getGroupId());
    }

    public ComputerDoc findComputerDocByLabel(String label) {
        return aiidaComputerDocRepository.findComputerDocByLabelAndGroupId(label, CustRequestHelper.getGroupId());
    }

    public void deleteAllByLabelIn(List<String> labels) {
        aiidaComputerDocRepository.deleteAllByLabelInAndGroupId(labels, CustRequestHelper.getGroupId());
    }

    public Page<ComputerDoc> findAll(Pageable pageable) {
        return aiidaComputerDocRepository.findAllByGroupId(CustRequestHelper.getGroupId(), pageable);
    }

    public List<ComputerDoc> findAll() {
        return aiidaComputerDocRepository.findAllByGroupId(CustRequestHelper.getGroupId());
    }

    public Page<ComputerDoc> searchByLabel(String label) {
        return aiidaComputerDocRepository.findAllByLabelLikeIgnoreCaseAndGroupId(label, CustRequestHelper.getGroupId(), PageSupport.getPageableBeginWith0());
    }

    public ComputerDoc save(ComputerDoc computerDoc) {
        return aiidaComputerDocRepository.save(computerDoc);
    }

    public List<ComputerDoc> saveAll(Iterable<ComputerDoc> computerDocs) {
        return aiidaComputerDocRepository.saveAll(computerDocs);
    }

    public Boolean existsByLabel(String label) {
        return aiidaComputerDocRepository.existsByLabelAndGroupId(label, CustRequestHelper.getGroupId());
    }
}
