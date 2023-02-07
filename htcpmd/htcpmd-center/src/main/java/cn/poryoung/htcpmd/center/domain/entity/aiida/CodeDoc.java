package cn.poryoung.htcpmd.center.domain.entity.aiida;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("htcpmd_aiida_code")
public class CodeDoc extends BaseDo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    private String label;

    private String remote_exec_path;

    private String input_plugin;

    private String description;

    private String prepend_text;

    private String append_text;

    private String computerId;
}
