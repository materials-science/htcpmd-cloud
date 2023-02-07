package cn.poryoung.htcpmd.center.domain.entity.aiida;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("htcpmd_aiida_server")
public class AiidaServerDoc extends BaseDo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    private String label;

    private String description;

    private String hostname;

    private String ip;

    private String port;

    private Boolean disable = false;
}
