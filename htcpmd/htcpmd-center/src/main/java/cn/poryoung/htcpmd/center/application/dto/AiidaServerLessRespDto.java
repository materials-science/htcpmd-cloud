package cn.poryoung.htcpmd.center.application.dto;

import cn.poryoung.htcpmd.common.pojo.BaseRespDto;
import lombok.Data;

@Data
public class AiidaServerLessRespDto extends BaseRespDto {
    private String id;

    private String uuid;

    private String label;

    private String description;
}
