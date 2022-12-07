/*
 * @Author: your name
 * @Date: 2020-12-22 09:01:16
 * @LastEditTime: 2021-10-14 14:22:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /web-admin-kit/src/router/modules/data.js
 */
import layoutHeaderAside from "@/layout/header-aside";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

const meta = {
	auth: true
};
export default {
	path: "/data",
	name: "data",
	meta,
	redirect: {
		name: "data-index"
	},
	component: layoutHeaderAside,
	children: (pre => [
		// potentials
		{
			path: "potentials",
			name: `${pre}potentials`,
			component: _import("data/potentials"),
			meta: {
				...meta,
				title: "Potentials List"
			}
		},
	])("data-")
};
