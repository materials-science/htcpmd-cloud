package cn.poryoung.htcpmd.common.pojo.user;

import cn.poryoung.htcpmd.common.pojo.BaseReqDto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
public class UserQueryReqDto extends BaseReqDto {
    @Length(max = 10)
    private String id;

    @Email
    private String email;

    private String mobile;
}
