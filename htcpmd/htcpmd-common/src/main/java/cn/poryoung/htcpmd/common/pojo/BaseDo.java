package cn.poryoung.htcpmd.common.pojo;

import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class BaseDo extends BaseEntity {
    private String updateBy;

    private String createBy;

    private Boolean delSts;

    private Integer verNo;
}
