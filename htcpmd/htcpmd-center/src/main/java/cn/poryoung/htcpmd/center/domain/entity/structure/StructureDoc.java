package cn.poryoung.htcpmd.center.domain.entity.structure;

import cn.hutool.json.JSONArray;
import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

/**
 * Structure NoSql文档数据
 */
@Data
@Document("htcpmd_structure_detail")
public class StructureDoc extends BaseDo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    /**
     * Base Attr
     */
    private JSONArray elements;

    private JSONArray cell;

    private JSONArray reciprocal_cell;

    private Integer number_of_atoms;

    private Double volume;

    private Integer spacegroup;

    private String formula;

    private JSONArray positions;

    /**
     * structure 文件访问地址
     */
    private String filepath;

    /**
     * structure 图片访问地址
     */
    private String coverImg;

    /**
     * Structure Tags
     */
    @DocumentReference
    private List<StructureTagDoc> tags;

    /**
     * E dos Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'structureId':?#{#self.uuid}, 'field':'eDos' }")
    private List<StructureDiagramDoc> eDosList;

    /**
     * Energy bands Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'structureId':?#{#self.uuid}, 'field':'band' }")
    private List<StructureDiagramDoc> bandList;

    /**
     * Phonon Dos Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'structureId':?#{#self.uuid}, 'field':'phononDos' }")
    private List<StructureDiagramDoc> phononDosList;

    /**
     * Phonon Dispersion Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'structureId':?#{#self.uuid}, 'field':'phonon' }")
    private List<StructureDiagramDoc> phononList;

    /**
     * Mobility Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'structureId':?#{#self.uuid}, 'field':'mobility' }")
    private List<StructureDiagramDoc> mobilityList;

    /**
     * sigma Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'structureId':?#{#self.uuid}, 'field':'sigma' }")
    private List<StructureDiagramDoc> sigmaList;

    /**
     * Electrical Kappa Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = "{ 'structureId':?#{#self.uuid}, 'field':'eKappa' }")
    private List<StructureDiagramDoc> eKappaList;

    /**
     * Lattice Kappa Diagram data
     */
    @ReadOnlyProperty
    @DocumentReference(lazy = true, lookup = " {'structureId':?#{#self.uuid}, 'field':'lKappa' }")
    private List<StructureDiagramDoc> lKappaList;
}
