<template>
  <transition :name="
    transitionActive
      ? 'fade-transverse'
      : ''
  ">
    <keep-alive :include="keepAlive">
      <router-view :key="routerViewKey" />
    </keep-alive>
  </transition>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
export default {
  name: 'layout-empty',
  ...mapState("d2admin", {
    keepAlive: state => state.page.keepAlive,
    transitionActive: state => state.transition.active
  }),
  computed: {
    /**
     * @description 用来实现带参路由的缓存
     */
    routerViewKey() {
      // 默认情况下 key 类似 __transition-n-/foo
      // 这里的字符串操作是为了最终 key 的格式和原来相同 类似 __transition-n-__stamp-time-/foo
      const stamp = this.$route.meta[`__stamp-${this.$route.path}`] || "";
      return `${stamp ? `__stamp-${stamp}-` : ""}${this.$route.path}`;
    }
  }
}
</script>
