package cn.poryoung.htcpmd.system.application.service;

import cn.poryoung.htcpmd.common.exception.BussinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import com.ruoyi.common.core.domain.R;

public interface UserApplicationService {

    R<?> userinfo(String userId) throws BussinessException, SystemException;

    R<?> profile() throws BussinessException, SystemException;

}
