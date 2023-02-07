package cn.poryoung.htcpmd.center.domain.entity.aiida;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document("htcpmd_aiida_computer")
public class ComputerDoc extends BaseDo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    /* label */
    private String label;

    /* remote computer hostname */
    private String hostname;

    /* remote computer port */
    private Integer port;

    /* remote computer username */
    private String username;

    /* remote computer password */
    private String password;

    private String description;

    /* type: `ssh` */
    private String transport_type;

    /* type: `direct`, `torque` */
    private String scheduler_type;

    private String key_policy;

    private String workdir;

    private String shebang;

    private String mpirun_command;

    private String prepend_text;

    private String append_text;

    private Integer default_mpiprocs_per_machine;

    private Long minimum_job_poll_interval;

    /**
     * code data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'computerId':?#{#self.uuid} }")
    private List<CodeDoc> codeList;
}
