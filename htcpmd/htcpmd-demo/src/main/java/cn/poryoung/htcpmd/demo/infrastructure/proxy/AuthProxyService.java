package cn.poryoung.htcpmd.demo.infrastructure.proxy;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("ruoyi-auth")
public interface AuthProxyService {
    @PostMapping(value = "/login")
    public R<?> token(@RequestBody Map<String, String> parameters, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
