package cn.poryoung.htcpmd.center.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * structure 标签关联对象 htcpmd_structure_tag_rel
 * 
 * @author PorYoung
 * @date 2022-12-15
 */
public class HtcpmdStructureTagRel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** structure ID */
    private String id;

    /** structure ID */
    @Excel(name = "structure ID")
    private String structureId;

    /** tag ID */
    @Excel(name = "tag ID")
    private String tagId;

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
    public void setStructureId(String structureId) 
    {
        this.structureId = structureId;
    }

    public String getStructureId() 
    {
        return structureId;
    }
    public void setTagId(String tagId) 
    {
        this.tagId = tagId;
    }

    public String getTagId() 
    {
        return tagId;
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
            .append("structureId", getStructureId())
            .append("tagId", getTagId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delSts", getDelSts())
            .append("verNo", getVerNo())
            .toString();
    }
}
