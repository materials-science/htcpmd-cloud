package cn.poryoung.htcpmd.system.infrastructure.proxy;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("ruoyi-system")
public interface UserProxyService {

    @GetMapping(value = "/user/{userId}")
    AjaxResult getUser(@RequestHeader(SecurityConstants.FROM_SOURCE) String source, @PathVariable(value = "userId") String userId);

    @GetMapping(value = "/user/profile")
    AjaxResult profile(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/user/info/{username}")
    AjaxResult info(@RequestHeader(SecurityConstants.FROM_SOURCE) String source, @PathVariable(value = "username") String username);
}
