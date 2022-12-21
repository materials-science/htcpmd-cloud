<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import util from "@/libs/util";
import { menuHeader, menuAside } from "@/menu";
export default {
  name: "app",
  watch: {
    "$i18n.locale": "i18nHandle",
    // 菜单联动
    "$route.matched"(val) {
      const _side = menuHeader.filter(menu => menu.path === val[0].path);
      this.$store.commit(
        "d2admin/menu/asideSet",
        _side.length > 0 ? _side[0].children : []
      );
    }
  },
  created() {
    this.i18nHandle(this.$i18n.locale);
  },
  methods: {
    i18nHandle(val, oldVal) {
      util.cookies.set("lang", val);
      document.querySelector("html").setAttribute("lang", val);
    }
  }
};
</script>

<style lang="scss">
@import "~@/assets/style/public-class.scss";
</style>
