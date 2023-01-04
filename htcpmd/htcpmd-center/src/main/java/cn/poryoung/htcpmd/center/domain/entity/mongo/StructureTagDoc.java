package cn.poryoung.htcpmd.center.domain.entity.mongo;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("htcpmd_structure_tag")
public class StructureTagDoc extends BaseDo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    /**
     * 标签名称
     */
    @Excel(name = "标签名称")
    private String name;

    /**
     * 标签描述
     */
    @Excel(name = "标签描述")
    private String description;
}
