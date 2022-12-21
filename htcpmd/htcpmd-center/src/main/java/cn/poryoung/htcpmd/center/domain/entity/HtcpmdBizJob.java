package cn.poryoung.htcpmd.center.domain.entity;

import cn.poryoung.htcpmd.center.constant.HtcpmdBizJobConstant;
import cn.poryoung.htcpmd.common.constant.HtcpmdCommonConstant;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Htcpmd业务任务对象 htcpmd_biz_job
 *
 * @author PorYoung
 * @date 2022-12-13
 */
public class HtcpmdBizJob extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    private String name;

    /**
     * 任务描述
     */
    @Excel(name = "任务描述")
    private String description;

    /**
     * 任务类型
     */
    @Excel(name = "任务类型")
    private String type;

    /**
     * 关联ID
     */
    @Excel(name = "关联ID")
    private String rid;

    /**
     * 关联名称
     */
    @Excel(name = "关联名称")
    private String rname;

    /**
     * 任务状态
     */
    @Excel(name = "任务状态")
    private Integer status;

    /**
     * 任务状态信息
     */
    @Excel(name = "任务状态信息")
    private String statusMsg;

    /**
     * 任务日志
     */
    @Excel(name = "任务日志")
    private String logs;

    /**
     * 详细属性JSON
     */
    @Excel(name = "详细属性JSON")
    private String attributes;

    /**
     * 群组ID
     */
    @Excel(name = "群组ID")
    private Long groupId;

    /**
     * 删除状态
     */
    private Integer delSts;

    /**
     * 版本号
     */
    private Long verNo;

    /**
     * create a new Task
     */
    public HtcpmdBizJob() {
    }

    public HtcpmdBizJob(String name, String type) {
        this.name = name;
        this.type = type;
        this.status = HtcpmdBizJobConstant.StatusEnum.SUBMITTING.getStatus();
        this.setCreateTime(DateUtils.getNowDate());
        this.groupId = HtcpmdCommonConstant.PUBLIC_GROUP_ID;
        this.verNo = 1L;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getDelSts() {
        return delSts;
    }

    public void setDelSts(Integer delSts) {
        this.delSts = delSts;
    }

    public Long getVerNo() {
        return verNo;
    }

    public void setVerNo(Long verNo) {
        this.verNo = verNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("description", getDescription())
                .append("type", getType())
                .append("rid", getRid())
                .append("rname", getRname())
                .append("status", getStatus())
                .append("statusMsg", getStatusMsg())
                .append("logs", getLogs())
                .append("attributes", getAttributes())
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
