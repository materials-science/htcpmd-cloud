package cn.poryoung.htcpmd.demo.application.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import cn.poryoung.htcpmd.common.constant.CommonConstant;
import cn.poryoung.htcpmd.common.constant.SystemErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.BussinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.poryoung.htcpmd.demo.application.service.UserApplicationService;
import cn.poryoung.htcpmd.demo.infrastructure.proxy.AuthProxyService;
import cn.poryoung.htcpmd.demo.infrastructure.proxy.UserProxyService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "UserApplicationService", description = "用户登录注册管理")
@RestController
@RequestMapping("/sso")
public class UserApplicationServiceImpl implements UserApplicationService {
    @Autowired
    private AuthProxyService authProxyService;

    @Autowired
    private UserProxyService userProxyService;

    @Autowired
    private RedisService redisService;

    @Override
    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R<?> login(@RequestParam String username, @RequestParam String password) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", CommonConstant.DEMO_CLIENT_ID);
        params.put("client_secret", "demo-secret");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        return authProxyService.token(params, SecurityConstants.INNER);
    }

    @Override
    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public R<LoginUser> profile() throws BussinessException, SystemException {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        BussinessException.throwExceptionIfTrue(ObjectUtil.isNull(loginUser), BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), "用户信息不存在(请检查网关配置)");
        // todo: get user info from cache
        LoginUser respDto = BeanUtil.copyProperties(redisService.getCacheMapValue(CommonConstant.REDIS_KEY_USER, String.valueOf(loginUser.getUserid())), LoginUser.class);
        if (ObjectUtil.isNull(respDto)) {
            // todo: load user form remote call
            respDto = BeanUtil.copyProperties(userProxyService.getUserInfo(SecurityConstants.INNER).get(AjaxResult.DATA_TAG), LoginUser.class);
            SystemException.throwExceptionIfTrue(ObjectUtil.isNull(respDto), SystemErrorStatusEnum.FAILED, "User cannot find both in cache and db!");
        }

        return R.ok(respDto);
    }
}
