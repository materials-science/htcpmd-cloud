package cn.poryoung.htcpmd.demo.infrastructure.proxy;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("ruoyi-system")
public interface UserProxyService {
    @GetMapping(value = "/user/profile")
//    public AjaxResult getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    public AjaxResult getUserInfo(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
