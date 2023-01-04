package cn.poryoung.htcpmd.center.domain.entity.es;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class EsStructureTag extends BaseDo {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String uuid;

    /**
     * 标签名称
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 标签描述
     */
    @Field(type = FieldType.Text)
    private String description;
}
