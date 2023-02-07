<template>
  <div class="user-link">
    <router-link to="/user" v-if="uuid">
      UserID
      <el-link type="primary">{{ uuid }}</el-link>
    </router-link>
    <span v-else>{{ $_t('no data') }}</span>
  </div>
</template>

<script>
export default {
  name: "user-link",
  props: ["uuid"]
}
</script>
