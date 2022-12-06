package cn.poryoung.htcpmd.demo.application.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.poryoung.htcpmd.common.constant.BusinessErrorStatusEnum;
import cn.poryoung.htcpmd.common.constant.SystemErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.BussinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.poryoung.htcpmd.common.pojo.CommonResp;
import cn.poryoung.htcpmd.common.util.CommonAssembler;
import cn.poryoung.htcpmd.demo.application.dto.demoRespDto;
import cn.poryoung.htcpmd.demo.application.service.DemoAppService;
import cn.poryoung.htcpmd.demo.domain.service.DemoDomainService;
import cn.poryoung.htcpmd.demo.infrastructure.proxy.HtcpmdAiidaProxyService;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "DemoApplication", description = "示例接口")
@RestController
@RequestMapping("/")
public class demoAppServiceImpl implements DemoAppService {

    @Autowired
    private DemoDomainService demoDomainService;
    @Autowired
    private HtcpmdAiidaProxyService htcpmdAiidaProxyService;

    @Override
    @RequestMapping("/info/{id}")
    public CommonResp getDemoInfo(@PathVariable("id") String id) throws BussinessException, SystemException {
        BussinessException.throwExceptionIfTrue(!NumberUtil.isNumber(id), BusinessErrorStatusEnum.BUSINESS_ERROR.getCode(), "The id must be a valid number.");
        SystemException.throwExceptionIfTrue(NumberUtil.isOdd(Convert.toInt(id)), SystemErrorStatusEnum.FAILED.getCode(), "ids that are odds not available!");

        return CommonAssembler.covert2Dto(demoDomainService.getDemoInfo(id), demoRespDto.class);
    }

    @Override
    @RequestMapping("/aiida")
    public R<?> getAiidaServerEndpoints() throws Exception {
        return htcpmdAiidaProxyService.getAiidaServerEndpoints();
    }
}
