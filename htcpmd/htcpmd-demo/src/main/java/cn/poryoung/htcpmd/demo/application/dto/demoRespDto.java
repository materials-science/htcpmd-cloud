package cn.poryoung.htcpmd.demo.application.dto;

import cn.poryoung.htcpmd.common.pojo.BaseRespDto;
import lombok.Data;

@Data
public class demoRespDto extends BaseRespDto {

    private String id;

    private String demoName;
}
