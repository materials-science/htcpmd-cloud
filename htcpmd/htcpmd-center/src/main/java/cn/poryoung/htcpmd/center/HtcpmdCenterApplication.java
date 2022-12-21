package cn.poryoung.htcpmd.center;


import cn.poryoung.htcpmd.common.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@SpringBootApplication
public class HtcpmdCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtcpmdCenterApplication.class, args);
    }

}
