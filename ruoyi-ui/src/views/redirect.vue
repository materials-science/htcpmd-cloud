<script>
export default {
  created() {
    const { params, query } = this.$route;
    const { path } = params;

    delete query.token && delete query.expire;

    this.$router.replace({ path: "/" + path, query });
  },
  render: function (h) {
    return h(); // avoid warning message
  },
};
</script>
