import layoutHeaderAside from "@/layout/header-aside";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

const meta = {
  auth: true
};
export default {
  path: "/user",
  name: "user",
  redirect: {
    name: "user-index"
  },
  component: layoutHeaderAside,
  children: (pre => [
    {
      path: "",
      name: `${pre}index`,
      component: _import("user"),
      meta: {
        ...meta,
        cache: true,
        single: true,
        title: "User Table"
      }
    },
    {
      path: ":id",
      name: `${pre}profile`,
      component: _import("user/profile"),
      meta: {
        ...meta,
        title: "User Profile"
      }
    }
  ])("user-")
};
