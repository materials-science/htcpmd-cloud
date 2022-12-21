package cn.poryoung.htcpmd.center.domain.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
public class EsStructureTag implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

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

    /**
     * 群组ID
     */
    private Long groupId;

    /**
     * 版本号
     */
    private Long verNo;
}
