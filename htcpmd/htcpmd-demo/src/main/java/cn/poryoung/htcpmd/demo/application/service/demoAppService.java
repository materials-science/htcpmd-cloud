package cn.poryoung.htcpmd.demo.application.service;

import cn.poryoung.htcpmd.common.pojo.CommonResp;
import com.ruoyi.common.core.domain.R;

public interface demoAppService {

    public CommonResp getDemoInfo(String id) throws Exception;

    public R<?> getAiidaServerEndpoints() throws Exception;
}
