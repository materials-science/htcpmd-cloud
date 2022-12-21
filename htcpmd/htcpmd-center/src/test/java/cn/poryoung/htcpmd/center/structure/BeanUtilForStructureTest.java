package cn.poryoung.htcpmd.center.structure;

import cn.hutool.json.JSONUtil;
import cn.poryoung.htcpmd.center.domain.entity.HtcpmdStructureAttr;
import org.junit.jupiter.api.Test;

public class BeanUtilForStructureTest {
    @Test
    void testCopyPositionsFromJson() {
        String jsonStr = "{\"positions\":[[1,2,3],[4,5,6]]}";
        HtcpmdStructureAttr entity = JSONUtil.toBean(jsonStr, HtcpmdStructureAttr.class);
        assert entity.getPositions().size() == 2;
        assert entity.getPositions().toString().equals("[[1.0, 2.0, 3.0], [4.0, 5.0, 6.0]]");
    }
}
