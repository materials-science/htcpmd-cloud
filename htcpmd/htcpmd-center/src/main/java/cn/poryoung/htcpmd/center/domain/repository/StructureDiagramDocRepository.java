package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.structure.StructureDiagramDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StructureDiagramDocRepository extends MongoRepository<StructureDiagramDoc, String> {
    void deleteAllByStructureId(String id);
}
