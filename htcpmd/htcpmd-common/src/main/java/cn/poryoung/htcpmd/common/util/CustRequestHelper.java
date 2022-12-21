package cn.poryoung.htcpmd.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.poryoung.htcpmd.common.constant.HtcpmdCommonConstant;
import com.ruoyi.common.core.constant.SecurityConstants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CustRequestHelper {
    private static final Log log = LogFactory.get();


    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }

    public static String getHeader(String name) {
        HttpServletRequest request = getCurrentRequest();
        return request.getHeader(name);
    }

    public static String getGroupId() {
        String group_id = getHeader(SecurityConstants.GROUP_ID);
        return StrUtil.isBlank(group_id) ? String.valueOf(HtcpmdCommonConstant.PUBLIC_GROUP_ID) : group_id;
    }

    public static String getGroupId(Map<String, Object> headers) {
        String group_id = (String) headers.get(SecurityConstants.GROUP_ID);
        return StrUtil.isBlank(group_id) ? getGroupId() : group_id;
    }

    public static String getValue(String name) {
        HttpServletRequest request = null;
        try {
            request = getCurrentRequest();
        } catch (IllegalStateException illegalStateException) {
            log.error(illegalStateException);

            return null;
        }

        return request.getHeader(name);
    }

    public static String getValue(String name, Map<String, Object> headers) {
        String v = (String) headers.get(name);
        return StrUtil.isBlank(v) ? getValue(name) : v;
    }
}
