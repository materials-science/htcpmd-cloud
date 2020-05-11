package cn.poryoung.htcpmd.demo;

import cn.poryoung.htcpmd.common.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@SpringBootApplication
public class HtcpmdDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtcpmdDemoApplication.class, args);
    }

}
