package cn.poryoung.htcpmd.center.domain.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.application.dto.EsStructureSearchDto;
import cn.poryoung.htcpmd.center.constant.HtcpmdStructureConstant;
import cn.poryoung.htcpmd.center.domain.entity.es.EsStructure;
import cn.poryoung.htcpmd.center.domain.repository.EsStructureRepository;
import cn.poryoung.htcpmd.common.pojo.PageSupport;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import com.ruoyi.common.core.web.page.PageDomain;
import lombok.var;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EsStructureDomainService {
    private final Log log = LogFactory.get();

    @Autowired
    private EsStructureRepository esStructureRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<EsStructure> search(EsStructureSearchDto reqDto) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        // 分页
        Pageable pageable = PageSupport.getPageable(BeanUtil.copyProperties(reqDto, PageDomain.class));
        nativeSearchQueryBuilder.withPageable(pageable);

        if (StrUtil.isNotBlank(reqDto.getGet_all()) && reqDto.getGet_all().equals(HtcpmdStructureConstant.getAllDebugCode)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
            NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
            log.info("DSL:{}", Objects.requireNonNull(searchQuery.getQuery()).toString());
            SearchHits<EsStructure> searchHits = elasticsearchRestTemplate.search(searchQuery, EsStructure.class);
            if (searchHits.getTotalHits() <= 0) {
                return new PageImpl<>(new ArrayList<>(), pageable, 0);
            }
            List<EsStructure> searchStructureList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

            return new PageImpl<>(searchStructureList, pageable, searchHits.getTotalHits());
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 通过 uuid 查询
        if (StrUtil.isNotBlank(reqDto.getUuid())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("uuid", reqDto.getUuid()));
        }

        Set<String> elements = Arrays.stream(reqDto.getElements().split(HtcpmdStructureConstant.elementsSeparator)).collect(Collectors.toSet());
        // 通过 elements 查询
        if (Objects.equals(HtcpmdStructureConstant.SearchModeEnum.Any.getCode(), reqDto.getMode())) {
            boolQueryBuilder.must(QueryBuilders.termsQuery("elements", elements));
        } else {
            elements.forEach(element -> {
                boolQueryBuilder.must(QueryBuilders.termQuery("elements", element));
            });
        }

        // 通过 tags 查询
        if (CollectionUtil.isNotEmpty(reqDto.getTags())) {
            BoolQueryBuilder tagBoolQuery = QueryBuilders.boolQuery();
            for (var tag : reqDto.getTags()) {
                tagBoolQuery.must(QueryBuilders.termQuery("name", tag));
            }
            boolQueryBuilder.must(
                    QueryBuilders.nestedQuery("tags", tagBoolQuery, ScoreMode.Avg)
            );
        }

        // 其他属性过滤
        if (Objects.nonNull(reqDto.getVolume_max()) || Objects.nonNull(reqDto.getVolume_min())) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("volume");
            if (Objects.nonNull(reqDto.getVolume_min())) {
                rangeQuery.gte(reqDto.getVolume_min());
            }
            if (Objects.nonNull(reqDto.getVolume_max())) {
                rangeQuery.lte(reqDto.getVolume_max());
            }
            boolQueryBuilder.must(rangeQuery);
        }
        if (Objects.nonNull(reqDto.getNumber_of_atoms())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("number_of_atoms", reqDto.getNumber_of_atoms()));
        }
        if (Objects.nonNull(reqDto.getSpacegroup())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("spacegroup", reqDto.getSpacegroup()));
        }

        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        log.info("DSL:{}", Objects.requireNonNull(searchQuery.getQuery()).toString());
        SearchHits<EsStructure> searchHits = elasticsearchRestTemplate.search(searchQuery, EsStructure.class);
        if (searchHits.getTotalHits() <= 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, 0);
        }
        List<EsStructure> searchStructureList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        return new PageImpl<>(searchStructureList, pageable, searchHits.getTotalHits());
    }

    public EsStructure getEsStructureByUuid(String uuid) {
        return esStructureRepository.getEsStructureByUuidAndGroupId(uuid, CustRequestHelper.getGroupId());
    }

    public EsStructure deleteByUuid(String uuid) {
        return esStructureRepository.deleteByUuidAndGroupId(uuid, CustRequestHelper.getGroupId());
    }
}
