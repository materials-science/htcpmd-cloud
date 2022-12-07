import { request } from "@/api/service";
export const crudOptions = vm => {
  return {
    format: {
      flatData: {
        disabled: true, //启用数据扁平化
        symbol: ".", //默认使用#号连接， key需要配置成 user#gender
        deleteOnUnFlat: true // 是否在反扁平化后清理扁平化过的痕迹，默认true
      }
    },
    options: {
      height: "100%" // 表格高度100%, 使用toolbar必须设置
    },
    rowHandle: {
      // columnHeader: '操作',
      width: 180,
      view: {
        thin: true,
        text: null
      },
      edit: {
        thin: true,
        text: null,
        disabled() {
          //   return !vm.hasPermissions('usersphere:user:edit')
          return true;
        }
      },
      remove: {
        thin: true,
        text: null,
        disabled() {
          //   return !vm.hasPermissions('usersphere:user:del')
          return true;
        }
      },
      custom: [
        {
          thin: true,
          text: null,
          type: "warning",
          size: "small",
          emit: "authz",
          icon: "el-icon-s-flag",
          disabled() {
            //   return !vm.hasPermissions('usersphere:user:authz')
            return true;
          }
        }
      ]
    },
    columns: [
      {
        title: "uuid",
        key: "uuid",
        width: 70,
        search: {
          disabled: false
        }, // 开启查询
        sortable: "custom"
      },
      {
        title: "elements",
        key: "attributes.elements",
        width: 70
      },
      {
        title: "formula",
        key: "attributes.formula",
        width: 70
      },
      {
        title: "volume",
        key: "attributes.volume",
        type: "number",
        component: {
          name: "el-input-number",
          props: {
            precision: 4,
            disabled: true,
            size: "small",
            "controls-position": "right"
          }
        },
        width: 70
      },
      {
        title: "spacegroup",
        key: "attributes.spacegroup",
        width: 70
      },
      {
        title: "number of atoms",
        key: "attributes.number_of_atoms",
        width: 70
      },
      //   {
      //     title: 'center of mass',
      //     key: 'attributes.center_of_mass',
      //     width: 70,
      //   },
      //   {
      //     title: 'cell',
      //     key: 'attributes.cell',
      //     width: 140,
      //   },
      {
        title: "created time",
        key: "created_time",
        width: 70,
        type: "datetime",
        sortable: "custom"
      },
      {
        title: "last modified",
        key: "last_modified_time",
        width: 70,
        type: "datetime",
        sortable: "custom"
      }
    ]
  };
};
