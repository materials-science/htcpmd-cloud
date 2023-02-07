package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.aiida.CodeDoc;
import cn.poryoung.htcpmd.center.domain.repository.AiidaCodeDocRepository;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiidaCodeDocDomainService {
    @Autowired
    private AiidaCodeDocRepository aiidaCodeDocRepository;

    public void deleteById(String id) {
        aiidaCodeDocRepository.deleteByUuidOrIdAndGroupId(id, id, CustRequestHelper.getGroupId());
    }

    public CodeDoc findCodeDocByLabel(String label) {
        return aiidaCodeDocRepository.findCodeDocByLabelAndGroupId(label, CustRequestHelper.getGroupId());
    }

    public void deleteAllByLabelIn(List<String> labels) {
        aiidaCodeDocRepository.deleteAllByLabelInAndGroupId(labels, CustRequestHelper.getGroupId());
    }

    public Page<CodeDoc> findAll(Pageable pageable) {
        return aiidaCodeDocRepository.findAllByGroupId(CustRequestHelper.getGroupId(), pageable);
    }

    public List<CodeDoc> findAll() {
        return aiidaCodeDocRepository.findAllByGroupId(CustRequestHelper.getGroupId());
    }

    public Page<CodeDoc> searchByLabel(String label) {
        return aiidaCodeDocRepository.findAllByLabelLikeIgnoreCaseAndGroupId(label, CustRequestHelper.getGroupId(), PageSupport.getPageableBeginWith0());
    }

    public CodeDoc save(CodeDoc codeDoc) {
        return aiidaCodeDocRepository.save(codeDoc);
    }

    public List<CodeDoc> saveAll(Iterable<CodeDoc> codeDocs) {
        return aiidaCodeDocRepository.saveAll(codeDocs);
    }

    public Boolean existsByLabel(String label) {
        return aiidaCodeDocRepository.existsByLabelAndGroupId(label, CustRequestHelper.getGroupId());
    }
}
