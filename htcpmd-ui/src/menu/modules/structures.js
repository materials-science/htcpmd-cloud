export default {
	path: "/structures",
	title: "Structures",
	icon: "cubes",
	children: (pre => [
        {
			path: `${pre}`,
			title: "Search",
			icon: "search"
		},
		{
			path: `${pre}import`,
			title: "Import",
			icon: "cubes"
		}
	])("/structures/")
};
