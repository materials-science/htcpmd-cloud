package cn.poryoung.htcpmd.center.domain.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.poryoung.htcpmd.center.application.dto.EsStructureSearchDto;
import cn.poryoung.htcpmd.center.domain.entity.EsStructure;
import lombok.var;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class EsStructureDomainService {
    public Page<EsStructure> search(EsStructureSearchDto reqDto) {
        Integer pageNum = reqDto.getPageNum();
        Integer pageSize = reqDto.getPageSize();
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        // 分页
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 通过 id 过滤
        if (StrUtil.isNotBlank(reqDto.getId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("id", reqDto.getId()));
        }
        if (StrUtil.isNotBlank(reqDto.getUuid())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("uuid", reqDto.getUuid()));
        }

        // 通过 tags 过滤
        if (ArrayUtil.isNotEmpty(reqDto.getTags())) {
            BoolQueryBuilder tagBoolQuery = QueryBuilders.boolQuery();
            for (var tag : reqDto.getTags()) {
                tagBoolQuery.must(QueryBuilders.termQuery("name", tag));
            }
            boolQueryBuilder.must(
                    QueryBuilders.nestedQuery("tags", tagBoolQuery, ScoreMode.Avg)
            );
        }

//        // 其他属性过滤
//        if(Objects.nonNull(reqDto.getVolume_min())){
//            boolQueryBuilder.filter()
//        }

        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);

        return null;
    }
}
