package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.aiida.CodeDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AiidaCodeDocRepository extends MongoRepository<CodeDoc, String> {
    CodeDoc findCodeDocByLabelAndGroupId(String label, String groupId);

    Boolean existsByLabelAndGroupId(String label, String groupId);

    void deleteAllByLabelInAndGroupId(List<String> labels, String groupId);

    void deleteByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    Page<CodeDoc> findAllByGroupId(String groupId, Pageable pageable);

    List<CodeDoc> findAllByGroupId(String groupId);

    Page<CodeDoc> findAllByLabelLikeIgnoreCaseAndGroupId(String label, String groupId, Pageable pageable);
}
