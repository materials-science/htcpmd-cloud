package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.aiida.AiidaServerDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AiidaServerDocRepository extends MongoRepository<AiidaServerDoc, String> {
    AiidaServerDoc findAiidaServerDocByLabelAndGroupId(String label, String groupId);

    Boolean existsByLabelAndGroupId(String label, String groupId);

    void deleteAllByLabelInAndGroupId(List<String> labels, String groupId);

    void deleteByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    Page<AiidaServerDoc> findAllByGroupId(String groupId, Pageable pageable);

    List<AiidaServerDoc> findAllByGroupId(String groupId);

    Page<AiidaServerDoc> findAllByLabelLikeIgnoreCaseAndGroupId(String label, String groupId, Pageable pageable);
}
