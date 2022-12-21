package cn.poryoung.htcpmd.center.domain.repository;

import cn.poryoung.htcpmd.center.domain.entity.EsStructure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsStructureRepository extends ElasticsearchRepository<EsStructure, String> {

}
