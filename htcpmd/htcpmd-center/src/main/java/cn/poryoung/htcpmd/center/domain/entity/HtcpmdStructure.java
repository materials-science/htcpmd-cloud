package cn.poryoung.htcpmd.center.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * structure 信息对象 htcpmd_structure
 * 
 * @author PorYoung
 * @date 2022-12-16
 */
public class HtcpmdStructure extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** structure ID */
    private String id;

    /** UUID */
    @Excel(name = "UUID")
    private String uuid;

    /** structure 文件访问地址 */
    @Excel(name = "structure 文件访问地址")
    private String filepath;

    /** structure 图片访问地址 */
    @Excel(name = "structure 图片访问地址")
    private String coverImg;

    /** 群组ID */
    @Excel(name = "群组ID")
    private String groupId;

    /** structure 属性 */
    @Excel(name = "structure 属性")
    private String attributes;

    /** 删除状态 */
    @Excel(name = "删除状态")
    private Integer delSts;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long verNo;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setUuid(String uuid) 
    {
        this.uuid = uuid;
    }

    public String getUuid() 
    {
        return uuid;
    }
    public void setFilepath(String filepath) 
    {
        this.filepath = filepath;
    }

    public String getFilepath() 
    {
        return filepath;
    }
    public void setCoverImg(String coverImg) 
    {
        this.coverImg = coverImg;
    }

    public String getCoverImg() 
    {
        return coverImg;
    }
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupId()
    {
        return groupId;
    }
    public void setAttributes(String attributes) 
    {
        this.attributes = attributes;
    }

    public String getAttributes() 
    {
        return attributes;
    }
    public void setDelSts(Integer delSts) 
    {
        this.delSts = delSts;
    }

    public Integer getDelSts() 
    {
        return delSts;
    }
    public void setVerNo(Long verNo) 
    {
        this.verNo = verNo;
    }

    public Long getVerNo() 
    {
        return verNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("uuid", getUuid())
            .append("filepath", getFilepath())
            .append("coverImg", getCoverImg())
            .append("groupId", getGroupId())
            .append("attributes", getAttributes())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delSts", getDelSts())
            .append("verNo", getVerNo())
            .toString();
    }
}
