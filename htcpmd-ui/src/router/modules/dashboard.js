import layoutHeaderAside from "@/layout/header-aside";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

const meta = {
	auth: true
};
export default {
	path: "/dashboard",
	name: "dashboard",
	meta,
	redirect: {
		name: "dashboard-index"
	},
	component: layoutHeaderAside,
	children: (pre => [
		{
			path: "system-tasks",
			name: `${pre}system-tasks`,
			component: _import("dashboard/system-tasks"),
			meta: {
				...meta,
				title: "System Tasks"
			}
		},
		{
			path: "calctasks",
			name: `${pre}calctasks`,
			component: _import("dashboard/calctasks"),
			meta: {
				...meta,
				title: "CalcJobs Monitor"
			}
		}
	])("dashboard-")
};
