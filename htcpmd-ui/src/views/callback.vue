<script>
import api from "@/api";
import util from "@/libs/util.js";
import { errorCreate } from "@/api/tools";

export default {
  methods: {
    async load() {
      const dispatch = this.$store.dispatch;

      let userData = await api.SYS_LOGIN_USER_INFO();
      userData = userData.data;
      if (!userData.sysUser || !userData.sysUser.userId) {
        return errorCreate("No available user info found!");
      }

      util.cookies.set("uuid", userData.sysUser.userId);
      // 设置 vuex 用户信息
      await dispatch(
        "d2admin/user/set",
        {
          ...userData,
          username: userData.sysUser.userName
        },
        {
          root: true
        }
      );
      // 用户登录后从持久化数据加载一系列的设置
      await dispatch("d2admin/account/load");
    }
  },
  created() {
    const { params, query } = this.$route;
    const { path, token, expire } = query;
    delete query.path && delete query.token && delete query.expire;

    util.cookies.set("token", token);

    this.load()
      .then(() => {
        this.$router.push({ path: path, query });
      })
      .catch(() => {
        errorCreate("Permission Denied!");
      });
  },
  render: function(h) {
    return h(); // avoid warning message
  }
};
</script>
