import layoutHeaderAside from "@/layout/header-aside";

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require("@/libs/util.import." + process.env.NODE_ENV);

const meta = {
	auth: true
};
export default {
	path: "/structures",
	name: "structures",
	meta,
	redirect: {
		name: "structures-index"
	},
	component: layoutHeaderAside,
	children: (pre => [
		{
			path: "",
			name: `${pre}index`,
			component: _import("structures"),
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
			component: _import("structures/search"),
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
			component: _import("structures/detail.vue"),
			meta: {
				...meta,
				cache: true,
				title: "Structure Details"
			}
		},
		{
			path: "import",
			name: `${pre}import`,
			component: _import("structures/import.vue"),
			meta: {
				...meta,
				title: "Import Structure"
			}
		}
	])("structures-")
};
