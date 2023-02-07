package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.aiida.ComputerDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AiidaComputerDocRepository extends MongoRepository<ComputerDoc, String> {
    ComputerDoc findComputerDocByLabelAndGroupId(String label, String groupId);

    Boolean existsByLabelAndGroupId(String label, String groupId);

    void deleteAllByLabelInAndGroupId(List<String> labels, String groupId);

    void deleteByUuidOrIdAndGroupId(String uuid, String id, String groupId);

    Page<ComputerDoc> findAllByGroupId(String groupId, Pageable pageable);

    List<ComputerDoc> findAllByGroupId(String groupId);

    Page<ComputerDoc> findAllByLabelLikeIgnoreCaseAndGroupId(String label, String groupId, Pageable pageable);
}
