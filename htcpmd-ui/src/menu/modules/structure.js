export default {
  path: "/structure",
  title: "Structure",
  icon: "cubes",
  children: (pre => [
    {
      path: `${pre}`,
      title: "Search",
      icon: "search"
    },
    {
      path: `${pre}upload`,
      title: "Upload from Files",
      icon: "square-o"
    },
    {
      path: `${pre}import/aiida`,
      title: "Import from AiiDA",
      icon: "square-o"
    }
  ])("/structure/")
};
