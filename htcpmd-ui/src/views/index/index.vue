<template>
  <div class="page">
    <p class="page_title">Hello World!</p>
    <el-button class="d2-mt" @click="$router.replace({ path: '/' })">
      返回首页
    </el-button>
  </div>
</template>
<script>
export default {
  name: "index",
  beforeCreate() {
    this.$router.replace("/structures/");
  }
};
</script>
<style lang="scss" scoped>
.page {
  background: #303133;
  background-blend-mode: multiply, multiply;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  .page_title {
    font-size: 20px;
    color: #fff;
  }
}
</style>
