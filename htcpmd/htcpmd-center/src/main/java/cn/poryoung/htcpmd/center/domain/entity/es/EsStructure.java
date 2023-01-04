package cn.poryoung.htcpmd.center.domain.entity.es;

import cn.hutool.json.JSONArray;
import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Document(indexName = "htcpmd_structure")
public class EsStructure extends BaseDo {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String uuid;

    /**
     * 群组ID
     */
    private String groupId;

    /**
     * structure 文件访问地址
     */
    private String filepath;

    /**
     * structure 图片访问地址
     */
    private String coverImg;

    /**
     * 创建者
     */
    private String createBy;

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
