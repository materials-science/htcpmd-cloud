package cn.poryoung.htcpmd.system.application.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import cn.poryoung.htcpmd.common.constant.CommonConstant;
import cn.poryoung.htcpmd.common.constant.SystemErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.BussinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.poryoung.htcpmd.system.application.service.UserApplicationService;
import cn.poryoung.htcpmd.system.infrastructure.proxy.UserProxyService;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.model.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "UserApplicationService", description = "用户管理")
@RestController
@RequestMapping("/sso")
public class UserApplicationServiceImpl implements UserApplicationService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserProxyService userProxyService;

    @Override
    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public R<?> userinfo(@PathVariable("id") String userId) throws BussinessException, SystemException {
        BussinessException.throwExceptionIfTrue(StrUtil.isEmpty(userId), BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), "用户信息不存在(请检查网关配置)");
        // get user info from cache
        LoginUser respDto = BeanUtil.copyProperties(redisService.getCacheMapValue(CommonConstant.REDIS_KEY_USER, userId), LoginUser.class);
        if (ObjectUtil.isNull(respDto)) {
            // load user form remote calling system service
            respDto = BeanUtil.copyProperties(userProxyService.getUser(SecurityConstants.INNER, userId).get(AjaxResult.DATA_TAG), LoginUser.class);
            SystemException.throwExceptionIfTrue(ObjectUtil.isNull(respDto), SystemErrorStatusEnum.FAILED, "User cannot find both in cache and db!");
        }

        return R.ok(respDto);
    }

    @Override
    @ApiOperation("获取当前用户信息")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public R<?> profile() throws BussinessException, SystemException {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        BussinessException.throwExceptionIfTrue(ObjectUtil.isNull(loginUser), BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), "用户信息不存在(请检查网关配置)");
        // get user info from cache
        LoginUser respDto = BeanUtil.copyProperties(redisService.getCacheMapValue(CommonConstant.REDIS_KEY_USER, String.valueOf(loginUser.getUserid())), LoginUser.class);
        if (ObjectUtil.isNull(respDto)) {
            // load user form remote calling system service
            respDto = BeanUtil.copyProperties(userProxyService.info(SecurityConstants.INNER, loginUser.getUsername()).get(AjaxResult.DATA_TAG), LoginUser.class);
            SystemException.throwExceptionIfTrue(ObjectUtil.isNull(respDto), SystemErrorStatusEnum.FAILED, "User cannot find both in cache and db!");
        }

        return R.ok(respDto);
    }
}
