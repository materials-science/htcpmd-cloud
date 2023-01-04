package cn.poryoung.htcpmd.center.domain.entity;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

/**
 * structure 信息对象 htcpmd_structure
 *
 * @author PorYoung
 * @date 2022-12-16
 */
@Data
public class HtcpmdStructure extends BaseDo {
    private static final long serialVersionUID = 1L;

    /**
     * structure ID
     */
    private String id;

    /**
     * UUID
     */
    @Excel(name = "UUID")
    private String uuid;

    /**
     * structure 文件访问地址
     */
    @Excel(name = "structure 文件访问地址")
    private String filepath;

    /**
     * structure 图片访问地址
     */
    @Excel(name = "structure 图片访问地址")
    private String coverImg;

    /**
     * 群组ID
     */
    @Excel(name = "群组ID")
    private String groupId;
}
