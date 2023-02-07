package cn.poryoung.htcpmd.center.domain.entity.structure;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("htcpmd_structure_diagram")
public class StructureDiagramDoc extends BaseDo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    /**
     * file type
     */
    private String type;

    /**
     * file path
     */
    private String filepath;

    /**
     * file data
     */
    private String data;

    /**
     * diagram physic field like `phonon`, `band`
     */
    private String field;

    /**
     * diagram classification keys like temperate `100K`, concentration `E12N1`, `type-N` or `type-P`
     */
    private StructureDiagramKey key;

    /**
     * diagram description keywords
     */
    private List<String> keywords;

    /**
     * diagram description
     */
    private String description;

    /**
     * supplement files List
     */
    private List<String> supplements;

    /**
     * structure ref uuid
     */
    private String structureId;
}
