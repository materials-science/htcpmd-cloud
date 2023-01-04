package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.mongo.StructureTagDoc;
import cn.poryoung.htcpmd.center.domain.repository.StructureTagDocRepository;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StructureTagDocDomainService {
    @Autowired
    private StructureTagDocRepository structureTagDocRepository;

    public void deleteById(String id) {
        structureTagDocRepository.deleteByUuidOrIdAndGroupId(id, id, CustRequestHelper.getGroupId());
    }

    public StructureTagDoc findStructureTagDocByName(String name) {
        return structureTagDocRepository.findStructureTagDocByNameAndGroupId(name, CustRequestHelper.getGroupId());
    }

    public void deleteAllByNameIn(List<String> names) {
        structureTagDocRepository.deleteAllByNameInAndGroupId(names, CustRequestHelper.getGroupId());
    }

    public Page<StructureTagDoc> findAll(Pageable pageable) {
        return structureTagDocRepository.findAllByGroupId(CustRequestHelper.getGroupId(), pageable);
    }

    public List<StructureTagDoc> findAll() {
        return structureTagDocRepository.findAllByGroupId(CustRequestHelper.getGroupId());
    }

    public Page<StructureTagDoc> searchByName(String name) {
        return structureTagDocRepository.findAllByNameLikeIgnoreCaseAndGroupId(name, CustRequestHelper.getGroupId(), PageSupport.getPageable());
    }

    public StructureTagDoc save(StructureTagDoc structureTagDoc) {
        return structureTagDocRepository.save(structureTagDoc);
    }

    public List<StructureTagDoc> saveAll(Iterable<StructureTagDoc> structureTagDocs) {
        return structureTagDocRepository.saveAll(structureTagDocs);
    }

    public Boolean existsByName(String name) {
        return structureTagDocRepository.existsByNameAndGroupId(name, CustRequestHelper.getGroupId());
    }
}
