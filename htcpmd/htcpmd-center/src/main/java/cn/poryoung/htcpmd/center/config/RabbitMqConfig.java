package cn.poryoung.htcpmd.center.config;

import cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqEnum;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RabbitMqConfig {
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * binding queue to exchange
     */
    @Bean
    Declarables rabbitMqBindings() {
        DirectExchange htcpmdEventDirect = (DirectExchange) ExchangeBuilder.directExchange(HtcpmdCenterMqEnum.EVENT_DEFAULT_QUEUE.getExchange()).durable(true).build();
        List<Declarable> declarables = new ArrayList<>();
        declarables.add(htcpmdEventDirect);
        Arrays.stream(HtcpmdCenterMqEnum.values()).collect(Collectors.toList()).forEach(o -> {
            Queue queue = new Queue(o.getName());
            declarables.add(queue);
            declarables.add(BindingBuilder.bind(queue).to(htcpmdEventDirect).with(o.getRouteKey()));
        });

        return new Declarables(declarables);
    }
}
