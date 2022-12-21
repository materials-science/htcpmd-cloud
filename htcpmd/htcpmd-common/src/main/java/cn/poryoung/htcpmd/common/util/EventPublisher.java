package cn.poryoung.htcpmd.common.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EventPublisher {
    private static final Log log = LogFactory.get();
    @Autowired
    private RabbitTemplate template;


    /**
     * 传递用户信息请求头，防止丢失
     */
    private Map<String, Object> getHeaders(Map<String, Object> extra) {
        Map<String, Object> headers = new HashMap<>();

        // get header from http request
        HttpServletRequest httpServletRequest = ServletUtils.getRequest();
        if (Objects.nonNull(httpServletRequest)) {
            Map<String, String> userHeaders = ServletUtils.getHeaders(httpServletRequest);
            String userId = userHeaders.get(SecurityConstants.DETAILS_USER_ID);
            if (StringUtils.isNotEmpty(userId)) {
                headers.put(SecurityConstants.DETAILS_USER_ID, userId);
            }
            String userKey = userHeaders.get(SecurityConstants.USER_KEY);
            if (StringUtils.isNotEmpty(userKey)) {
                headers.put(SecurityConstants.USER_KEY, userKey);
            }
            String userName = userHeaders.get(SecurityConstants.DETAILS_USERNAME);
            if (StringUtils.isNotEmpty(userName)) {
                headers.put(SecurityConstants.DETAILS_USERNAME, userName);
            }
            String authentication = userHeaders.get(SecurityConstants.AUTHORIZATION_HEADER);
            if (StringUtils.isNotEmpty(authentication)) {
                headers.put(SecurityConstants.AUTHORIZATION_HEADER, authentication);
            }
            String group_id = CustRequestHelper.getGroupId();
            if (StringUtils.isNotEmpty(group_id)) {
                headers.put(SecurityConstants.GROUP_ID, group_id);
            }
            // 配置客户端IP
            headers.put("X-Forwarded-For", IpUtils.getIpAddr(httpServletRequest));
        }
        // get header from received message
        if (MapUtil.isNotEmpty(extra)) {
            headers.putAll(extra);
        }

        return headers;
    }

    public void publishEvent(String exchange, String queue, Object event) {
        publishEvent(exchange, queue, event, null);
    }

    public void publishEvent(String exchange, String queue, Object event, Map<String, Object> extraHeaders) {
        Map<String, Object> pack = new HashMap<>();
        Map<String, Object> headers = getHeaders(extraHeaders);
        pack.put("message_id", UUID.fastUUID());
        pack.put("headers", headers);
        pack.put("data", event);
        template.convertAndSend(exchange, queue, pack);
        log.info("Sent Message to Queue {}.", queue);
    }
}
