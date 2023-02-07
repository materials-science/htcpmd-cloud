export default {
  path: "/cluster",
  title: "Cluster",
  icon: "microchip",
  children: (pre => [
    {
      path: `${pre}server`,
      title: "AiidaServer",
      icon: "server",
      children: (pre => [
        {
          path: `${pre}`,
          title: "List",
          icon: "bars"
        }
      ])("/cluster/server/")
    },
    // {
    // 	path: `${pre}profiles`,
    // 	title: "Profiles",
    // 	icon: "server"
    // },
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
