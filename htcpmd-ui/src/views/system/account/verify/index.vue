<template>
	<div class="page">
		<page-background>
			<p class="page_title">
				{{ key ? page_title : "Failed to Verify Email!" }}
			</p>
			<el-button
				class="d2-mt"
				@click="$router.replace({ path: key ? '/login' : '/signup' })"
			>
				{{ key ? message : "前往注册" }}
			</el-button>
		</page-background>
	</div>
</template>

<script>
import PageBackground from "../components/page-background";
import { mapActions } from "vuex";
import { Message } from "element-ui";
export default {
	components: {
		PageBackground
	},
	data() {
		return {
			key: "",
			page_title: "Verify Email Successfully!",
			message: "前往登录"
		};
	},
	beforeCreate() {
		if (!this.$route.query.hasOwnProperty("key")) {
			console.log(this.$router.replace("/"));
		}
	},
	created() {
		const key = this.$route.query.key;
		this.verifi_email(key);
	},
	methods: {
		...mapActions("d2admin/account", ["verify_email"]),
		async verifi_email(key) {
			const res = await this.$api.SYS_USER_VERIFY_EMAIL({ key });
			let msg = "";
			let type = "success";
			if (res.code == 0) {
				msg = "Success!";
				this.key = key;
			} else if (res.code == 404) {
				msg = "Invalid Key!";
				type = "fail";
			} else {
				msg = "No Response";
				type = "fail";
			}
			Message({
				message: msg,
				type: type,
				duration: 3 * 1000
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.page {
	height: 100%;

	.page_title {
		font-size: 20px;
		color: #303133;
	}

	.page-login {
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}

	.d2-mt {
		z-index: 0;
	}
}
</style>
