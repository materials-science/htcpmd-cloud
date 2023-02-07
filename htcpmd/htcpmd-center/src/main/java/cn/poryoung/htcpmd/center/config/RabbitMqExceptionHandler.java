package cn.poryoung.htcpmd.center.config;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class RabbitMqExceptionHandler implements ErrorHandler {

    private final Log log = LogFactory.get();

    @Override
    public void handleError(Throwable t) {
        throw new AmqpRejectAndDontRequeueException("Error Handler converted exception to fatal", t);
    }
}
