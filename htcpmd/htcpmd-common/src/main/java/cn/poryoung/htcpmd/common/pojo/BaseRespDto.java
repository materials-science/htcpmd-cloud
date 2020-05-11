package cn.poryoung.htcpmd.common.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class BaseRespDto {
    private Date createTime;

    private Date updateTime;

    private Integer verNo;

    private Boolean delSts;
}
