import setting from "@/setting";
import { find, assign } from "lodash";

const htcpmd_system = setting.api.htcpmd_system;

const users = [
  {
    email: "admin",
    password: "admin",
    uuid: "admin-uuid",
    name: "Admin"
  },
  {
    email: "editor",
    password: "editor",
    uuid: "editor-uuid",
    name: "Editor"
  },
  {
    email: "user1",
    password: "user1",
    uuid: "user1-uuid",
    name: "User1"
  }
];

export default ({
  service,
  request,
  serviceForMock,
  requestForMock,
  mock,
  faker,
  tools
}) => ({
  /**
   * @description 获取用户详细信息
   */
  SYS_LOGIN_USER_INFO() {
    return request({
      url: `${htcpmd_system}/sso/profile`,
      method: "get"
    });
  },
  /**
   * @description 登录
   * @param {Object} data 登录携带的信息
   */
  SYS_USER_LOGIN(data = {}) {
    return request({
      url: "/accounts/login/",
      method: "post",
      data
    });
  },
  /**
   * @description 注册
   * @param {Object} data 注册携带的信息
   */
  SYS_USER_SIGNUP(data = {}) {
    return request({
      url: "/accounts/registration/",
      method: "post",
      data
    });
  },
  /**
   * @description 邮箱验证
   * @param {Object} data 验证携带的信息
   */
  SYS_USER_VERIFY_EMAIL(data = {}) {
    return request({
      url: "/accounts/registration/verify-email/",
      method: "post",
      data
    });
  },
  /**
   * @description 请求重新发送邮箱验证
   * @param {Object} data 验证携带的信息
   */
  SYS_USER_RESENT_EMAIL(data = {}) {
    return request({
      url: "/accounts/registration/account-email-verification-resent/",
      method: "post",
      data
    });
  }
});
