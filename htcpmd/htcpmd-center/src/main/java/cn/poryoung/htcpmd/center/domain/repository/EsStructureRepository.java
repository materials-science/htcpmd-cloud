package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.es.EsStructure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsStructureRepository extends ElasticsearchRepository<EsStructure, String> {
    EsStructure getEsStructureByUuidAndGroupId(String uuid, String groupId);

    EsStructure deleteByUuidAndGroupId(String uuid, String groupId);
}
