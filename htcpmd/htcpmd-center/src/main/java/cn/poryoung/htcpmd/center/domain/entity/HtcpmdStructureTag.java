package cn.poryoung.htcpmd.center.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * structure 标签信息对象 htcpmd_structure_tag
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
public class HtcpmdStructureTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** tag ID */
    private String id;

    /** 标签名称 */
    @Excel(name = "标签名称")
    private String name;

    /** 标签描述 */
    @Excel(name = "标签描述")
    private String description;

    /** 群组ID */
    @Excel(name = "群组ID")
    private Long groupId;

    /** 删除状态 */
    private Integer delSts;

    /** 版本号 */
    private Long verNo;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
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
            .append("name", getName())
            .append("description", getDescription())
            .append("groupId", getGroupId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delSts", getDelSts())
            .append("verNo", getVerNo())
            .toString();
    }
}
