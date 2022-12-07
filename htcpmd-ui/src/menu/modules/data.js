export default {
	path: "/data",
	title: "Data",
	icon: "bar-chart",
	children: (pre => [
		{
			path: `${pre}potentials`,
			title: "Potentials",
			icon: "fire"
		}
	])("/data/")
};
