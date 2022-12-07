import setting from "@/setting";
const auth = setting.api.auth;

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
   * @description 登录
   * @param {Object} data 登录携带的信息
   */
  AUTH_LOGIN(data = {}) {
    return request({
      url: `${auth}/login`,
      method: "post",
      headers: {
        isToken: false
      },
      data
    });
  },
  /**
   * @description 注册
   * @param {Object} data 注册携带的信息
   */
  AUTH_SIGNUP(data = {}) {
    return request({
      url: `${auth}/register`,
      method: "post",
      headers: {
        isToken: false
      },
      data
    });
  },
  /**
   * @description 退出登录
   */
  AUTH_LOGOUT() {
    return request({
      url: `${auth}/logout`,
      method: "delete"
    });
  },
  /**
   * @description 刷新Token
   */
  AUTH_REFRESH() {
    return request({
      url: `${auth}/refresh`,
      method: "post"
    });
  },
  /**
   * @description 获取图片验证码
   */
  AUTH_CODE_IMG() {
    return request({
      url: "/code",
      method: "get",
      headers: {
        isToken: false
      },
      timeout: 20000
    });
  }
});
