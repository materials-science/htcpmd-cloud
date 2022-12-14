package cn.poryoung.htcpmd.common.annotation;

import cn.xuyanwu.spring.file.storage.EnableFileStorage;
import com.ruoyi.common.security.config.ApplicationConfig;
import com.ruoyi.common.security.feign.FeignAutoConfiguration;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("cn.poryoung.htcpmd.**.infrastructure.persistence.mapper")
// 开启线程异步执行
@EnableAsync
// 自动加载类
@EnableCustomFeignClients
@EnableDiscoveryClient
@EnableCustomSwagger2
@EnableFileStorage
@Import({ApplicationConfig.class, FeignAutoConfiguration.class})
public @interface EnableCustomConfig {
}
