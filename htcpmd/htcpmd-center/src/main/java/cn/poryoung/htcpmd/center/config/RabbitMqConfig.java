package cn.poryoung.htcpmd.center.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.constant.HtcpmdDeadLetterMqEnum;
import cn.poryoung.htcpmd.center.constant.HtcpmdEventMqEnum;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RabbitMqConfig {
    private static final String TEST_QUEUE = "htcpmd.test.mq.message";

    private static final String TEST_CALLBACK_QUEUE = "htcpmd.test.mq.message.callback";

    private Log log = LogFactory.get();

    @Autowired
    private RabbitMqExceptionHandler rabbitMqExceptionHandler;

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * binding queue to exchange
     */
    @Bean
    Declarables rabbitMqBindings() {
        DirectExchange htcpmdEventDirect = (DirectExchange) ExchangeBuilder.directExchange(HtcpmdEventMqEnum.EVENT_DEFAULT_QUEUE.getExchange()).durable(true).build();
        List<Declarable> declarables = new ArrayList<>();

        DirectExchange dlqDirect = (DirectExchange) ExchangeBuilder.directExchange(HtcpmdDeadLetterMqEnum.DLQ_DEFAULT_QUEUE.getExchange()).durable(true).build();
        declarables.add(dlqDirect);
        Arrays.stream(HtcpmdDeadLetterMqEnum.values()).collect(Collectors.toList()).forEach(o -> {
            Queue queue = new Queue(o.getName());
            declarables.add(queue);
            declarables.add(BindingBuilder.bind(queue).to(dlqDirect).with(o.getRouteKey()));
        });

        declarables.add(htcpmdEventDirect);
        Arrays.stream(HtcpmdEventMqEnum.values()).collect(Collectors.toList()).forEach(o -> {
            Queue queue = QueueBuilder.durable(o.getName())
                    .withArgument("x-dead-letter-exchange", HtcpmdDeadLetterMqEnum.DLQ_DEFAULT_QUEUE.getExchange())
                    .withArgument("x-dead-letter-routing-key", HtcpmdDeadLetterMqEnum.DLQ_DEFAULT_QUEUE.getRouteKey())
                    .build();
            declarables.add(queue);
            declarables.add(BindingBuilder.bind(queue).to(htcpmdEventDirect).with(o.getRouteKey()));
        });

        return new Declarables(declarables);
    }

    @Bean("customContainerFactory")
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                              ConnectionFactory connectionFactory,
                                                                              ObjectProvider<ContainerCustomizer<SimpleMessageListenerContainer>> simpleContainerCustomizer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);

//        factory.setAfterReceivePostProcessors(message -> {
//            SecurityContextHolder.setLocalMap(message.getMessageProperties().getHeaders());
//            return message;
//        });
//        factory.setBeforeSendReplyPostProcessors(message -> {
//            message.getMessageProperties().getHeaders().putAll(SecurityContextHolder.getLocalMap());
//            SecurityContextHolder.remove();
//
//            return message;
//        });
        factory.setErrorHandler(rabbitMqExceptionHandler);
        simpleContainerCustomizer.ifUnique(factory::setContainerCustomizer);

        return factory;
    }
}
