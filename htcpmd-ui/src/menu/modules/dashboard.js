export default {
  path: "/dashboard",
  title: "Dashboard",
  icon: "tachometer",
  children: (pre => [
    {
      path: `${pre}bizjob`,
      title: "BizJob",
      icon: "list-alt"
    },
    {
      path: `${pre}system-tasks`,
      title: "SystemTasks",
      icon: "list-alt"
    },
    {
      path: `${pre}calctasks`,
      title: "CalcTasks",
      icon: "tasks"
    }
  ])("/dashboard/")
};
