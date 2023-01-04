package cn.poryoung.htcpmd.center.application.dto;

import cn.poryoung.htcpmd.common.pojo.BaseReqDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class StructureUploadDiagramDataDto extends BaseReqDto {
    /**
     * structure uuid
     */
    @NotBlank
    private String structureId;

    /**
     * file type
     */
    private String type;

    /**
     * file data
     */
    @NotBlank
    private String data;

    /**
     * supplement files List
     */
    private List<String> supplements;

    private String field;

    private String description;

    private String groupBy;
}
