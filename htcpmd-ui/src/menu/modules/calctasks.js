export default {
	path: "/calctasks",
	title: "Calctasks",
	icon: "tasks",
	children: (pre => [
		{
			path: `${pre}`,
			title: "Mine Tasks",
			icon: "list"
		},
		{
			path: `${pre}submit`,
			title: "New Tasks",
			icon: "plus"
		}
	])("/calctasks/")
};
