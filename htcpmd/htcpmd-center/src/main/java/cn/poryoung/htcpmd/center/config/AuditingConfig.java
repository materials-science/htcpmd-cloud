package cn.poryoung.htcpmd.center.config;

import com.ruoyi.common.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class AuditingConfig {
    @Bean
    AuditorAware<String> mongoAuditorAware() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.ofNullable(SecurityContextHolder.getUserName());
            }
        };
    }
}
