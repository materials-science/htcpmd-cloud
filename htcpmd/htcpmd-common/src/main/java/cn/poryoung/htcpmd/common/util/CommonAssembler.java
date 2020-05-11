package cn.poryoung.htcpmd.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.poryoung.htcpmd.common.constant.SystemErrorStatusEnum;
import cn.poryoung.htcpmd.common.exception.SystemException;
import cn.poryoung.htcpmd.common.pojo.BaseReqDto;
import cn.poryoung.htcpmd.common.pojo.BaseRespDto;
import cn.poryoung.htcpmd.common.pojo.CommonResp;


public class CommonAssembler {
    public static <T, K extends BaseRespDto> CommonResp<K> covert2Dto(T tDo, Class<K> clazz) throws SystemException {
        SystemException.throwExceptionIfTrue(ObjectUtil.isNull(tDo) || ObjectUtil.isNull(clazz), SystemErrorStatusEnum.FAILED.getCode(), "Errors Occurred In Converting Do to Dto!");

        return CommonResp.success(BeanUtil.copyProperties(tDo, clazz));
    }

    public static <T extends BaseReqDto, K> K covert2Do(T tDto, Class<K> clazz) throws SystemException {
        SystemException.throwExceptionIfTrue(ObjectUtil.isNull(tDto) || ObjectUtil.isNull(clazz), SystemErrorStatusEnum.FAILED.getCode(), "Errors Occurred In Converting Dto to DO!");

        return BeanUtil.copyProperties(tDto, clazz);
    }
}
