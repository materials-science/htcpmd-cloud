package cn.poryoung.htcpmd.center.test;

import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.center.constant.HtcpmdCenterMqConstant;
import com.ruoyi.common.core.context.SecurityContextHolder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqListenerTest {
    private final Log log = LogFactory.get();

    @RabbitListener(queues = {HtcpmdCenterMqConstant.EVENT_TEST_CALLBACK_QUEUE_NAME})
    public void handleTest(JSONObject jsonObject, Message message) throws Exception {
        SecurityContextHolder.getLocalMap();
        log.info("before sleep userId: {}", SecurityContextHolder.get("userId"));
//        log.info(jsonObject.toStringPretty());
//        log.info(message.getMessageProperties().toString());
        Thread.sleep(5000);
        log.info("after sleep userId: {}", SecurityContextHolder.get("userId"));
    }
}
