package cn.poryoung.htcpmd.demo.infrastructure.proxy;

import com.ruoyi.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("htcpmd-aiida-server")
public interface HtcpmdAiidaProxyService {

    @GetMapping("/system/endpoint")
    R<?> getAiidaServerEndpoints();
}
