package cn.poryoung.htcpmd.center.application.dto;

import com.ruoyi.common.core.web.page.PageDomain;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class EsStructureSearchDto extends PageDomain {
    private String id;

    private String uuid;

    /**
     * search mode
     */
    @NotNull
    private Integer mode;

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

    /**
     * debug mode
     */
    private String get_all;
}
