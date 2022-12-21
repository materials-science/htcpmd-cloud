package cn.poryoung.htcpmd.center.domain.entity;

import cn.hutool.json.JSONArray;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

@Document(indexName = "htcpmd_structure")
@Data
public class EsStructure implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String uuid;

    /**
     * structure 文件访问地址
     */
    private String filepath;

    /**
     * structure 图片访问地址
     */
    private String coverImg;

    /**
     * 群组ID
     */
    private String groupId;

    /**
     * 版本号
     */
    private Long verNo;

    @Field(type = FieldType.Keyword)
    private JSONArray elements;

    private JSONArray cell;

    private JSONArray reciprocal_cell;

    @Field(type = FieldType.Double)
    private Integer number_of_atoms;

    @Field(type = FieldType.Double)
    private Double volume;

    @Field(type = FieldType.Integer)
    private Integer spacegroup;

    @Field(type = FieldType.Keyword)
    private String formula;

    private JSONArray positions;

    /**
     * structure nested tags
     */
    @Field(type = FieldType.Nested)
    private List<EsStructureTag> tags;
}
