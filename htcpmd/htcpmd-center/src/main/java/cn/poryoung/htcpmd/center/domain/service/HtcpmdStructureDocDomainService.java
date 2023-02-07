package cn.poryoung.htcpmd.center.domain.service;

import cn.poryoung.htcpmd.center.domain.entity.structure.StructureDoc;
import cn.poryoung.htcpmd.center.domain.repository.HtcpmdStructureDocRepository;
import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.util.CustRequestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HtcpmdStructureDocDomainService {
    @Autowired
    private HtcpmdStructureDocRepository structureDocRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存 structure 信息
     */
    public StructureDoc save(StructureDoc structureAttr) throws Exception {
        return structureDocRepository.save(structureAttr);
    }

    /**
     * 批量保存 structure 信息
     */
    public List<StructureDoc> saveAll(Iterable<StructureDoc> structureDocIterable) throws Exception {
        return structureDocRepository.saveAll(structureDocIterable);
    }

    public StructureDoc findOneByUuidAndUpdate(StructureDoc structureDoc) throws BusinessException {
        BusinessException.throwExceptionIfTrue(!existsHtcpmdStructureDocByUuid(structureDoc.getUuid()), BusinessErrorStatusEnum.STRUCTURE_NOT_FOUND, null);
        return structureDocRepository.save(structureDoc);

    }

    public StructureDoc findHtcpmdStructureDocByUuid(String uuid) {
        return structureDocRepository.findHtcpmdStructureDocByUuidAndGroupId(uuid, CustRequestHelper.getGroupId());
    }

    public StructureDoc findHtcpmdStructureDocById(String id) {
        return structureDocRepository.findHtcpmdStructureDocByUuidOrIdAndGroupId(id, id, CustRequestHelper.getGroupId());
    }

    public StructureDoc deleteById(String id) {
        return structureDocRepository.deleteByUuidOrIdAndGroupId(id, id, CustRequestHelper.getGroupId());
    }

    public Boolean existsHtcpmdStructureDocByUuid(String uuid) {
        return structureDocRepository.existsHtcpmdStructureDocByUuidAndGroupId(uuid, CustRequestHelper.getGroupId());
    }

    public StructureDoc pushObjToRefs(String uuid, String key, Object obj) {
//        return mongoTemplate.update(HtcpmdStructureDoc.class)
//                .matching(Criteria.where("uuid").is(uuid))
//                .apply(new Update().push("phononList", o))
        return mongoTemplate.findAndModify(Query.query(Criteria.where("uuid").is(uuid)),
                new Update().push(key, obj),
                StructureDoc.class
        );
    }
}
