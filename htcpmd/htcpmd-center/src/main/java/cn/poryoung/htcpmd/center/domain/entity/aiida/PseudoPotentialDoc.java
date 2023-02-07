package cn.poryoung.htcpmd.center.domain.entity.aiida;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("htcpmd_aiida_pseudo")
public class PseudoPotentialDoc extends BaseDo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    private String label;

    /* type: `upf`, `potcar` */
    private String type;

    private String description;

    private String url;
}
