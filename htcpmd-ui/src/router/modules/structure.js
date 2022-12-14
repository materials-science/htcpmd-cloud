import layoutHeaderAside from "@/layout/header-aside";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

const meta = {
  auth: true
};
export default {
  path: "/structure",
  name: "structure",
  meta,
  redirect: {
    name: "structure-index"
  },
  component: layoutHeaderAside,
  children: (pre => [
    {
      path: "",
      name: `${pre}index`,
      component: _import("structure"),
      meta: {
        ...meta,
        cache: true,
        single: true,
        title: "Periodic Table"
      }
    },
    {
      path: "search",
      name: `${pre}search`,
      component: _import("structure/search"),
      meta: {
        ...meta,
        cache: true,
        single: true,
        title: "Structures Search Results"
      }
    },
    {
      path: "detail/:uuid",
      name: `${pre}detail`,
      component: _import("structure/detail.vue"),
      meta: {
        ...meta,
        cache: true,
        title: "Structure Details"
      }
    },
    {
      path: "upload",
      name: `${pre}upload`,
      component: _import("structure/uploads/upload.vue"),
      meta: {
        ...meta,
        title: "Upload Structure"
      }
    },
    {
      path: "import/aiida",
      name: `${pre}import-aiida`,
      component: _import("structure/uploads/import.vue"),
      meta: {
        ...meta,
        title: "Import from AiiDA"
      }
    }
  ])("structure-")
};
