package cn.poryoung.htcpmd.system;


import cn.poryoung.htcpmd.common.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCustomConfig
@SpringBootApplication
public class HtcpmdSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HtcpmdSystemApplication.class, args);
    }

}
