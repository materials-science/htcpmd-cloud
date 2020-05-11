package cn.poryoung.htcpmd.common.pojo.user;

import com.ruoyi.system.api.model.LoginUser;
import lombok.Data;

@Data
public class LoginUserDo extends LoginUser {
    /**
     * 登录客户端ID
     */
    private String clientId;

}
