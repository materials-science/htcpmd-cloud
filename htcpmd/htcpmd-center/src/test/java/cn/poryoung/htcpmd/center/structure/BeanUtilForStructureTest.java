package cn.poryoung.htcpmd.center.structure;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.poryoung.htcpmd.center.domain.entity.structure.EsStructure;
import cn.poryoung.htcpmd.center.domain.entity.structure.StructureDoc;
import org.junit.jupiter.api.Test;

public class BeanUtilForStructureTest {
    @Test
    void testCopyPositionsFromJson() {
        String jsonStr = "{\"positions\":[[1,2,3],[4,5,6]]}";
        StructureDoc entity = JSONUtil.toBean(jsonStr, StructureDoc.class);
        assert entity.getPositions().size() == 2;
        assert entity.getPositions().toString().equals("[[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]");
    }

    @Test
    void testCopyBeanToList() {
        JSONObject jsonObject = new JSONObject();
        JSONObject band = new JSONObject();
        band.set("createBy", "PorYoung");
        band.set("uuid", IdUtil.simpleUUID());
        JSONArray bandList = new JSONArray();
        bandList.add(band);
        jsonObject.set("bandList", bandList);

        StructureDoc structureDoc = jsonObject.toBean(StructureDoc.class);

        assert true;
    }

    @Test
    void testCopyBeanToBean() {
        JSONObject jsonObject = new JSONObject();
        JSONObject tag = new JSONObject();
        tag.set("createBy", "PorYoung");
        tag.set("uuid", IdUtil.simpleUUID());
        tag.set("name", "test");
        JSONArray tags = new JSONArray();
        tags.add(tag);
        jsonObject.set("tags", tags);

        StructureDoc structureDoc = jsonObject.toBean(StructureDoc.class);
        EsStructure esStructure = BeanUtil.copyProperties(structureDoc, EsStructure.class);

        assert true;
    }
}
