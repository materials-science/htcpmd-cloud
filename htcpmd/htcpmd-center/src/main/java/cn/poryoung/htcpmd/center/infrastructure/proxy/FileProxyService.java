package cn.poryoung.htcpmd.center.infrastructure.proxy;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("ruoyi-file")
public interface FileProxyService {
    @PostMapping("/upload")
    R<SysFile> upload(@RequestHeader(SecurityConstants.FROM_SOURCE) String source, MultipartFile file);
}
