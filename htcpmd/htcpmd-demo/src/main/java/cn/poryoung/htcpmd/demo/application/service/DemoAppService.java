package cn.poryoung.htcpmd.demo.application.service;

import cn.poryoung.htcpmd.common.pojo.CommonResp;
import com.ruoyi.common.core.domain.R;

public interface DemoAppService {

    CommonResp getDemoInfo(String id) throws Exception;

    R<?> getAiidaServerEndpoints() throws Exception;
}
