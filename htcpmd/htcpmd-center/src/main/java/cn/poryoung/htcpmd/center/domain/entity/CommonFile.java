package cn.poryoung.htcpmd.center.domain.entity;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件信息对象 common_file
 *
 * @author PorYoung
 * @date 2022-12-14
 */
public class CommonFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    private String id;

    /**
     * 文件访问地址
     */
    @Excel(name = "文件访问地址")
    private String url;

    /**
     * 文件大小，单位字节
     */
    @Excel(name = "文件大小，单位字节")
    private Long size;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String filename;

    /**
     * 原始文件名
     */
    @Excel(name = "原始文件名")
    private String originalFilename;

    /**
     * 基础存储路径
     */
    @Excel(name = "基础存储路径")
    private String basePath;

    /**
     * 存储路径
     */
    @Excel(name = "存储路径")
    private String path;

    /**
     * 文件扩展名
     */
    @Excel(name = "文件扩展名")
    private String ext;

    /**
     * MIME类型
     */
    @Excel(name = "MIME类型")
    private String contentType;

    /**
     * 存储平台
     */
    @Excel(name = "存储平台")
    private String platform;

    /**
     * 缩略图访问路径
     */
    @Excel(name = "缩略图访问路径")
    private String thUrl;

    /**
     * 缩略图名称
     */
    @Excel(name = "缩略图名称")
    private String thFilename;

    /**
     * 缩略图大小，单位字节
     */
    @Excel(name = "缩略图大小，单位字节")
    private Long thSize;

    /**
     * 缩略图MIME类型
     */
    @Excel(name = "缩略图MIME类型")
    private String thContentType;

    /**
     * 文件所属对象id
     */
    @Excel(name = "文件所属对象id")
    private String objectId;

    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    @Excel(name = "文件所属对象类型，例如用户头像，评价图片")
    private String objectType;

    /**
     * 群组ID
     */
    @Excel(name = "群组ID")
    private Long groupId;

    /**
     * 附加属性
     */
    @Excel(name = "附加属性")
    private String attr;

    /**
     * 删除状态
     */
    private Integer delSts;

    /**
     * 版本号
     */
    private Long verNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getThUrl() {
        return thUrl;
    }

    public void setThUrl(String thUrl) {
        this.thUrl = thUrl;
    }

    public String getThFilename() {
        return thFilename;
    }

    public void setThFilename(String thFilename) {
        this.thFilename = thFilename;
    }

    public Long getThSize() {
        return thSize;
    }

    public void setThSize(Long thSize) {
        this.thSize = thSize;
    }

    public String getThContentType() {
        return thContentType;
    }

    public void setThContentType(String thContentType) {
        this.thContentType = thContentType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
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
                .append("url", getUrl())
                .append("size", getSize())
                .append("filename", getFilename())
                .append("originalFilename", getOriginalFilename())
                .append("basePath", getBasePath())
                .append("path", getPath())
                .append("ext", getExt())
                .append("contentType", getContentType())
                .append("platform", getPlatform())
                .append("thUrl", getThUrl())
                .append("thFilename", getThFilename())
                .append("thSize", getThSize())
                .append("thContentType", getThContentType())
                .append("objectId", getObjectId())
                .append("objectType", getObjectType())
                .append("groupId", getGroupId())
                .append("attr", getAttr())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delSts", getDelSts())
                .append("verNo", getVerNo())
                .toString();
    }
}
