package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.mongo.StructureTagDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StructureTagDocRepository extends MongoRepository<StructureTagDoc, String> {
    StructureTagDoc findStructureTagDocByNameAndGroupId(String name, String groupId);

    Boolean existsByNameAndGroupId(String name, String groupId);

    void deleteAllByNameInAndGroupId(List<String> names, String groupId);

    void deleteByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    Page<StructureTagDoc> findAllByGroupId(String groupId, Pageable pageable);

    List<StructureTagDoc> findAllByGroupId(String groupId);

    Page<StructureTagDoc> findAllByNameLikeIgnoreCaseAndGroupId(String name, String groupId, Pageable pageable);
}
