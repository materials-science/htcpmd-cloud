import { uniqueId } from "lodash";
import dashboard from "@/menu/modules/dashboard";
import data from "@/menu/modules/data";
import cluster from "@/menu/modules/cluster";
import structures from "@/menu/modules/structures";
import calctasks from "@/menu/modules/calctasks";

/**
 * @description 给菜单数据补充上 path 字段
 * @description https://github.com/d2-projects/d2-admin/issues/209
 * @param {Array} menu 原始的菜单数据
 */
function supplementPath(menu) {
  return menu.map(e => ({
    ...e,
    path: e.path || uniqueId("d2-menu-empty-"),
    ...(e.children
      ? {
          children: supplementPath(e.children)
        }
      : {})
  }));
}

export const menuHeader = supplementPath([
  dashboard,
  structures,
  cluster,
  calctasks,
  data,
  {
    path: "/users",
    title: "Users",
    icon: "user"
  }
]);

export const menuAside = supplementPath([
  {
    path: "/index",
    title: "Home",
    icon: "home"
  }
]);
