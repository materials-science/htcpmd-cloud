package cn.poryoung.htcpmd.common.aspect;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ruoyi.common.core.context.SecurityContextHolder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author PorYoung
 * @description: use to set SecurityContextHolder for RabbitListener handler
 * @// TODO: 2023/1/12 currencyConsumer may cause thread local to be override
 */
@Aspect
@Component
public class SCHAspect {
    private final Log log = LogFactory.get();

    @Before(value = "@annotation(rabbitListener)&&args(..,message)", argNames = "rabbitListener,message")
    public void before(RabbitListener rabbitListener, Message message) {
        log.info("SCH Aspect before {}", message.getMessageProperties().getMessageId());
        SecurityContextHolder.setLocalMap(message.getMessageProperties().getHeaders());
    }

    @After("@annotation(rabbitListener)")
    public void after(RabbitListener rabbitListener) {
        log.info("SCH Aspect after {}.", rabbitListener.queues());
        SecurityContextHolder.remove();
    }
}
