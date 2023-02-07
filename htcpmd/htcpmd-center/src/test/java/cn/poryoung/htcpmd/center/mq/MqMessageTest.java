package cn.poryoung.htcpmd.center.mq;

import cn.hutool.core.lang.UUID;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MqMessageTest {
    private static final String TEST_QUEUE = "htcpmd.test.mq.message";

    private static final String TEST_CALLBACK_QUEUE = "htcpmd.test.mq.message.callback";

    private final Log log = LogFactory.get();

    @Autowired
    private RabbitTemplate template;

    @Test
    public void test() throws Exception {
        int i = 0;
        while (i < 1000) {
            runDemo(template, i);
            ++i;
        }
    }

    private void runDemo(RabbitTemplate template, int count) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("hello", 1);
        Map<String, Object> event = new HashMap<>();
        event.put("data", data);
        Map<String, Object> headers = new HashMap<>();
        headers.put("userId", count);
        template.convertAndSend(TEST_QUEUE, event, m -> {
            m.getMessageProperties().getHeaders().putAll(headers);
            m.getMessageProperties().setMessageId(UUID.fastUUID().toString());
            return m;
        });
//        Thread.sleep(5000);
    }
}
