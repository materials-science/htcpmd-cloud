package cn.poryoung.htcpmd.center.application.dto;

import com.ruoyi.common.core.web.page.PageDomain;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class EsStructureSearchDto extends PageDomain {
    private String id;

    private String uuid;

    /**
     * search mode
     */
    @NotBlank
    private String mode;

    /**
     * elements seperated by "-", eg. As-In
     */
    @NotBlank
    private String elements;

    @Positive
    private Integer number_of_atoms;

    @Positive
    private Double volume_min;

    @Positive
    private Double volume_max;

    @Positive
    private Integer spacegroup;

    /**
     * structure tag names
     */
    private List<String> tags;
}
