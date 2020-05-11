package cn.poryoung.htcpmd.demo.application.service;

import cn.poryoung.htcpmd.common.exception.BussinessException;
import cn.poryoung.htcpmd.common.exception.SystemException;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.model.LoginUser;

public interface UserApplicationService {
    public R<?> login(String username, String password);

    public R<LoginUser> profile() throws BussinessException, SystemException;
}
