<template>
  <div>
    userId: {{ id }}
  </div>
</template>

<script>
export default {
  name: "user-profile",
  data() {
    return {
      id: "",
      user: {}
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    if (this.id == "") {
      this.$message.error("Whoops! This user id does not exist.");
      return setTimeout(() => {
        this.$router.back();
      }, 1500);
    }
    this.id = this.$route.params.id;
    // load user info
  }
}
</script>
