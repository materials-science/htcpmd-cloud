// Vue
import Vue from "vue";
import i18n from "./i18n";
import App from "./App";
// 核心插件
import d2Admin from "@/plugin/d2admin";
// store
import store from "@/store/index";

// 菜单和路由设置
import router from "./router";
import { menuHeader, menuAside } from "@/menu";
import { frameInRoutes } from "@/router/routes";

// d2-crud-plus 安装与初始化
import "./crud";

import vuetify from "@/plugin/vuetify";

// verify plugin
import SlideVerify from "vue-monoplasty-slide-verify";

// util
import util from "@/libs/util";
import vueJsonTreeView from "vue-json-tree-view";

// style
// import "@/assets/style/public-class.scss";

// vue native socket
import VueNativeSock from "vue-native-websocket";

Vue.use(d2Admin);

Vue.use(SlideVerify);

Vue.use(vueJsonTreeView);

Vue.use(VueNativeSock, util.getWebsocketUrl(), {
  store: store,
  format: "json",
  connectManually: true,
  passToStoreHandler: function(eventName, event) {
    if (!eventName.startsWith("SOCKET_")) {
      return;
    }
    let method = "commit";
    let target = eventName.toUpperCase();
    let msg = event;
    if (this.format === "json" && event.data) {
      msg = JSON.parse(event.data);
      if (msg.mutation) {
        target = [msg.namespace || "", msg.mutation].filter(e => !!e).join("/");
      } else if (msg.action) {
        method = "dispatch";
        target = [msg.namespace || "", msg.action].filter(e => !!e).join("/");
      }
    }
    // Write this if multiple stores are used and namespace options are enabled
    target = "socket/" + target;
    this.store[method](target, msg);
  }
});

new Vue({
  router,
  store,
  i18n,
  render: h => h(App),
  created() {
    // 处理路由 得到每一级的路由设置
    this.$store.commit("d2admin/page/init", frameInRoutes);
    // 设置顶栏菜单
    this.$store.commit('d2admin/menu/headerSet', menuHeader)
    // 设置侧边栏菜单
    this.$store.commit("d2admin/menu/asideSet", menuAside);
    // 初始化菜单搜索功能
    this.$store.commit("d2admin/search/init", menuHeader);
  },
  mounted() {
    // 展示系统信息
    this.$store.commit("d2admin/releases/versionShow");
    // 用户登录后从数据库加载一系列的设置
    this.$store.dispatch("d2admin/account/load");
    // 获取并记录用户 UA
    this.$store.commit("d2admin/ua/get");
    // 初始化全屏监听
    this.$store.dispatch("d2admin/fullscreen/listen");
    Array.prototype.remove = function(val) {
      var index = this.indexOf(val);
      if (index > -1) {
        this.splice(index, 1);
      }
    };
  },
  vuetify
}).$mount("#app");
