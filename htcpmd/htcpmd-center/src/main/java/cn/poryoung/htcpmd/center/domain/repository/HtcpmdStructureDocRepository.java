package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.mongo.HtcpmdStructureDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HtcpmdStructureDocRepository extends MongoRepository<HtcpmdStructureDoc, String> {
    HtcpmdStructureDoc findHtcpmdStructureDocByUuidAndGroupId(String uuid, String groupId);

    HtcpmdStructureDoc findHtcpmdStructureDocByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    HtcpmdStructureDoc deleteByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    Boolean existsHtcpmdStructureDocByUuidAndGroupId(String uuid, String groupId);
}
