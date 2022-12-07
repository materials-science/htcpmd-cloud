import { Message, MessageBox } from "element-ui";
import util from "@/libs/util.js";
import router from "@/router";
import api from "@/api";
import { errorCreate } from "@/api/tools";

export default {
  namespaced: true,
  actions: {
    /**
     * @description 登录
     * @param {Object} context
     * @param {Object} payload username {String} 用户名
     * @param {Object} payload password {String} 密码
     * @param {Object} payload code {String} 验证码
     * @param {Object} payload uuid {String} uuid
     * @param {Object} payload route {Object} 登录成功后定向的路由对象 任何 vue-router 支持的格式
     */
    async login(
      { dispatch },
      { username = "", password = "", code = "", uuid = "" } = {}
    ) {
      let res = await api.AUTH_LOGIN({
        username,
        password,
        code,
        uuid
      });

      // 设置 cookie 一定要存 uuid 和 token 两个 cookie
      // 整个系统依赖这两个数据进行校验和存储
      // uuid 是用户身份唯一标识 用户注册的时候确定 并且不可改变 不可重复
      // token 代表用户当前登录状态 建议在网络请求中携带 token
      // 如有必要 token 需要定时更新，默认保存一天
      res = res.data;
      util.cookies.set("token", res.access_token);

      let userData = await api.SYS_LOGIN_USER_INFO();
      userData = userData.data;
      if (!userData.sysUser || !userData.sysUser.userId) {
        return errorCreate("No available user info found!");
      }

      util.cookies.set("uuid", userData.sysUser.userId);
      // 设置 vuex 用户信息
      await dispatch(
        "d2admin/user/set",
        {
          ...userData,
          username: username
        },
        {
          root: true
        }
      );
      // 用户登录后从持久化数据加载一系列的设置
      await dispatch("load");
    },
    /**
     * @description 注册
     * @param {Object} context
     * @param {Object} payload email {String} 用户邮箱
     * @param {Object} payload password1 {String} 密码
     * @param {Object} payload password2 {String} 确认密码
     * @param {Object} payload route {Object} 注册成功后定向的路由对象 任何 vue-router 支持的格式
     */
    async signup(
      { dispatch },
      { username = "", email = "", password1 = "", password2 = "" } = {}
    ) {
      let res = await api.SYS_USER_SIGNUP({
        username,
        email,
        password1,
        password2
      });
      res = res.data;
      Message({
        message: res.detail,
        type: "success",
        duration: 5 * 1000
      });
    },
    /**
     * @description 邮箱验证
     * @param {Object} context
     * @param {Object} payload key {String} Token
     */
    async verify_email({ dispatch }, { key = "" } = {}) {
      let res = await api.SYS_USER_VERIFY_EMAIL({
        key
      });
      Message({
        message: res.data.detail || "No response",
        type: res.msg || "fail",
        duration: 5 * 1000
      });
    },
    /**
     * @description 注销用户并返回登录页面
     * @param {Object} context
     * @param {Object} payload confirm {Boolean} 是否需要确认
     */
    logout({ commit, dispatch }, { confirm = false } = {}) {
      /**
       * @description 注销
       */
      async function logout() {
        let res = await api.AUTH_LOGOUT();
        // 删除cookie
        util.cookies.remove("token");
        util.cookies.remove("uuid");
        // 清空 vuex 用户信息
        await dispatch(
          "d2admin/user/set",
          {},
          {
            root: true
          }
        );
        // 跳转路由
        router.push({
          name: "login"
        });
      }
      // 判断是否需要确认
      if (confirm) {
        commit("d2admin/gray/set", true, {
          root: true
        });
        MessageBox.confirm("确定要注销当前用户吗", "注销用户", {
          type: "warning"
        })
          .then(() => {
            commit("d2admin/gray/set", false, {
              root: true
            });
            logout();
          })
          .catch(() => {
            commit("d2admin/gray/set", false, {
              root: true
            });
            Message({
              message: "取消注销操作"
            });
          });
      } else {
        logout();
      }
    },
    /**
     * @description 用户登录后从持久化数据加载一系列的设置
     * @param {Object} context
     */
    async load({ dispatch }) {
      // 加载用户名
      await dispatch("d2admin/user/load", null, {
        root: true
      });
      // 加载主题
      await dispatch("d2admin/theme/load", null, {
        root: true
      });
      // 加载页面过渡效果设置
      await dispatch("d2admin/transition/load", null, {
        root: true
      });
      // 持久化数据加载上次退出时的多页列表
      await dispatch("d2admin/page/openedLoad", null, {
        root: true
      });
      // 持久化数据加载侧边栏配置
      await dispatch("d2admin/menu/asideLoad", null, {
        root: true
      });
      // 持久化数据加载全局尺寸
      await dispatch("d2admin/size/load", null, {
        root: true
      });
      // 持久化数据加载颜色设置
      await dispatch("d2admin/color/load", null, {
        root: true
      });
    }
  }
};
