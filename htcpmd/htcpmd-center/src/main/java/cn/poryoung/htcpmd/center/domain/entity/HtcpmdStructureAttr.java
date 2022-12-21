package cn.poryoung.htcpmd.center.domain.entity;

import cn.hutool.json.JSONArray;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Structure NoSql文档数据
 */
@Data
@Document("htcpmd_structure_detail")
public class HtcpmdStructureAttr {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    private JSONArray elements;

    private JSONArray cell;

    private JSONArray reciprocal_cell;

    private Integer number_of_atoms;

    private Double volume;

    private Integer spacegroup;

    private String formula;

    private JSONArray positions;
}
