export default {
	path: "/cluster",
	title: "Cluster",
	icon: "microchip",
	children: (pre => [
		{
			path: `${pre}profiles`,
			title: "Profiles",
			icon: "server"
		},
		{
			path: `${pre}plugins`,
			title: "Plugins",
			icon: "plug"
		},
		{
			path: `${pre}computers`,
			title: "Computers",
			icon: "television"
		},
		{
			path: `${pre}codes`,
			title: "Codes",
			icon: "puzzle-piece"
		}
	])("/cluster/")
};
