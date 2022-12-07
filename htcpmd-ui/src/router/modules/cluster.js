import layoutHeaderAside from "@/layout/header-aside";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

const meta = {
	auth: true
};
export default {
	path: "/cluster",
	name: "cluster",
	meta,
	redirect: {
		name: "cluster-index"
	},
	component: layoutHeaderAside,
	children: (pre => [
		{
			path: "profiles",
			name: `${pre}profiles`,
			component: _import("cluster/profiles"),
			meta: {
				...meta,
				title: "Profiles List"
			}
		},
		{
			path: "plugins",
			name: `${pre}plugins`,
			component: _import("cluster/plugins"),
			meta: {
				...meta,
				title: "Plugins List"
			}
		},
		{
			path: "computers",
			name: `${pre}computers`,
			component: _import("cluster/computers"),
			meta: {
				...meta,
				title: "Computers List"
			}
		},
		{
			path: "codes",
			name: `${pre}codes`,
			component: _import("cluster/codes"),
			meta: {
				...meta,
				title: "Codes List"
			}
		}
	])("cluster-")
};
