package cn.poryoung.htcpmd.center.application.service;

import cn.poryoung.htcpmd.common.exception.BusinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import com.ruoyi.common.core.domain.R;

public interface UserApplicationService {

    R<?> userinfo(String userId) throws BusinessException, SystemException;

    R<?> profile() throws BusinessException, SystemException;

}
