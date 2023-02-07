package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.structure.StructureDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HtcpmdStructureDocRepository extends MongoRepository<StructureDoc, String> {
    StructureDoc findHtcpmdStructureDocByUuidAndGroupId(String uuid, String groupId);

    StructureDoc findHtcpmdStructureDocByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    StructureDoc deleteByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    Boolean existsHtcpmdStructureDocByUuidAndGroupId(String uuid, String groupId);
}
