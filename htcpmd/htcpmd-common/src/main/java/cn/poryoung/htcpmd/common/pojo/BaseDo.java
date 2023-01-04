package cn.poryoung.htcpmd.common.pojo;

import cn.poryoung.htcpmd.common.constant.HtcpmdCommonConstant;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class BaseDo extends BaseEntity {
    /**
     * 群组ID
     */
    private String groupId = HtcpmdCommonConstant.PUBLIC_GROUP_ID;

    /**
     * 删除状态
     */
    private Boolean delSts = false;

    /**
     * 版本号
     */
    private Long verNo = 1L;
}
