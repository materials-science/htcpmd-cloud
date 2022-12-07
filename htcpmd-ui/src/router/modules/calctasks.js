import layoutHeaderAside from "@/layout/header-aside";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

const meta = {
	auth: true
};
export default {
	path: "/calctasks",
	name: "calctasks",
	meta,
	redirect: {
		name: "calctasks-index"
	},
	component: layoutHeaderAside,
	children: (pre => [
		{
			path: "",
			name: `${pre}index`,
			component: _import("calctasks"),
			meta: {
				...meta,
				title: "CalcTasks List"
			}
		},
		{
			path: "detail/:uuid",
			name: `${pre}detail`,
			component: _import("calctasks/detail"),
			meta: {
				...meta,
				cache: true,
				title: "CalcTask Detail"
			}
		},
		{
			path: "submit/",
			name: `${pre}submit-index`,
			component: _import("calctasks/submit"),
			meta: {
				...meta,
				cache: true,
				title: "Start CalcTasks",
				single: true
			}
		}
	])("calctasks-")
};
