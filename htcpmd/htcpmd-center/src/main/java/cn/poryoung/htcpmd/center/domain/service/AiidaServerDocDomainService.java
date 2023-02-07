package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.aiida.AiidaServerDoc;
import cn.poryoung.htcpmd.center.domain.repository.AiidaServerDocRepository;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiidaServerDocDomainService {
    @Autowired
    private AiidaServerDocRepository aiidaServerDocRepository;

    public void deleteById(String id) {
        aiidaServerDocRepository.deleteByUuidOrIdAndGroupId(id, id, CustRequestHelper.getGroupId());
    }

    public AiidaServerDoc findServerDocByLabel(String label) {
        return aiidaServerDocRepository.findAiidaServerDocByLabelAndGroupId(label, CustRequestHelper.getGroupId());
    }

    public void deleteAllByLabelIn(List<String> labels) {
        aiidaServerDocRepository.deleteAllByLabelInAndGroupId(labels, CustRequestHelper.getGroupId());
    }

    public Page<AiidaServerDoc> findAll(Pageable pageable) {
        return aiidaServerDocRepository.findAllByGroupId(CustRequestHelper.getGroupId(), pageable);
    }

    public List<AiidaServerDoc> findAll() {
        return aiidaServerDocRepository.findAllByGroupId(CustRequestHelper.getGroupId());
    }

    public Page<AiidaServerDoc> searchByLabel(String label) {
        return aiidaServerDocRepository.findAllByLabelLikeIgnoreCaseAndGroupId(label, CustRequestHelper.getGroupId(), PageSupport.getPageableBeginWith0());
    }

    public AiidaServerDoc save(AiidaServerDoc aiidaServerDoc) {
        return aiidaServerDocRepository.save(aiidaServerDoc);
    }

    public List<AiidaServerDoc> saveAll(Iterable<AiidaServerDoc> computerDocs) {
        return aiidaServerDocRepository.saveAll(computerDocs);
    }

    public Boolean existsByLabel(String label) {
        return aiidaServerDocRepository.existsByLabelAndGroupId(label, CustRequestHelper.getGroupId());
    }
}
