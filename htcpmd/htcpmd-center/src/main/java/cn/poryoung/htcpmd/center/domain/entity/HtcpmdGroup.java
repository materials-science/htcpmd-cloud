package cn.poryoung.htcpmd.center.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 群组对象 htcpmd_group
 * 
 * @author PorYoung
 * @date 2022-12-12
 */
public class HtcpmdGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 群组名称 */
    @Excel(name = "群组名称")
    private String name;

    /** group标签 */
    @Excel(name = "group标签")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String profile;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** group状态 */
    @Excel(name = "group状态")
    private Integer status;

    /** group可见状态 */
    @Excel(name = "group可见状态")
    private Integer visiable;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setProfile(String profile) 
    {
        this.profile = profile;
    }

    public String getProfile() 
    {
        return profile;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setVisiable(Integer visiable) 
    {
        this.visiable = visiable;
    }

    public Integer getVisiable() 
    {
        return visiable;
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
            .append("title", getTitle())
            .append("profile", getProfile())
            .append("avatar", getAvatar())
            .append("status", getStatus())
            .append("visiable", getVisiable())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delSts", getDelSts())
            .append("verNo", getVerNo())
            .toString();
    }
}
