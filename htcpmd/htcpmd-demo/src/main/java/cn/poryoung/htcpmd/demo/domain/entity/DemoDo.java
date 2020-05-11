package cn.poryoung.htcpmd.demo.domain.entity;

import cn.poryoung.htcpmd.common.pojo.BaseDo;
import lombok.Data;

@Data
public class DemoDo extends BaseDo {

    private String id;

    private String demoName;

    private String secret;
}
