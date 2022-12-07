import layoutHeaderAside from "@/layout/header-aside";
import dataModules from "./modules/data";
import dashboard from "./modules/dashboard";
import cluster from "./modules/cluster";
import structures from "./modules/structures";
import calctasks from "./modules/calctasks";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

/**
 * 在主框架内显示
 */
const frameIn = [
  {
    path: "/",
    redirect: {
      name: "index"
    },
    component: layoutHeaderAside,
    children: [
      // 首页
      {
        path: "index",
        name: "index",
        meta: {
          title: "Home",
          auth: true,
          cache: true,
          single: true
        },
        component: _import("index")
      },
      {
        path: "users",
        name: "users",
        meta: {
          title: "User Table",
          auth: true
        },
        component: _import("dashboard/user-table")
      },
      // 系统 前端日志
      {
        path: "log",
        name: "log",
        meta: {
          title: "前端日志",
          auth: true
        },
        component: _import("system/log")
      },
      // 刷新页面 必须保留
      {
        path: "refresh",
        name: "refresh",
        hidden: true,
        component: _import("system/function/refresh")
      },
      // 页面重定向 必须保留
      {
        path: "redirect/:route*",
        name: "redirect",
        hidden: true,
        component: _import("system/function/redirect")
      }
    ]
  },
  dataModules,
  dashboard,
  cluster,
  structures,
  calctasks
];

/**
 * 在主框架之外显示
 */
const frameOut = [
  // sso回调
  {
    path: "/callback",
    name: "callback",
    component: _import("callback")
  },
  // 登录
  {
    path: "/login",
    name: "login",
    component: _import("system/account/login"),
    beforeEnter: (to, from) => {
      const client = process.env.VUE_APP_CLIENT_NAME;
      const callback_url = process.env.VUE_APP_CALLBACK_URL;
      const url =
        process.env.VUE_APP_SSO_URL +
        `?client=${encodeURI(client)}&redirect_url=${encodeURI(callback_url)}`;

      return (window.location.href = url);
    }
  },
  // 注册
  {
    path: "/signup",
    name: "registration",
    component: _import("system/account/registration")
  },
  // 验证邮箱
  {
    path: "/verify-email",
    name: "verify-email",
    component: _import("system/account/verify")
  }
];

/**
 * 错误页面
 */
const errorPage = [
  {
    path: "*",
    name: "404",
    component: _import("system/error/404")
  }
];

// 导出需要显示菜单的
export const frameInRoutes = frameIn;

// 重新组织后导出
export default [...frameIn, ...frameOut, ...errorPage];
