<template>
	<div class="page-login">
		<page-background>
			<account-form>
				<!-- logo -->
				<img
					class="page-login--logo"
					style="margin-bottom:initial;"
					src="@/assets/images/all@light.png"
				/>
				<!-- form -->
				<div class="page-login--form">
					<el-card shadow="never">
						<el-form
							ref="regitrationForm"
							label-position="top"
							:rules="rules"
							:model="formSignup"
							size="default"
						>
							<el-form-item prop="username">
								<el-input
									type="text"
									v-model="formSignup.username"
									placeholder="Username"
								>
									<i
										slot="prepend"
										class="fa fa-user-circle-o"
									></i>
								</el-input>
							</el-form-item>
							<el-form-item prop="email">
								<el-input
									type="email"
									v-model="formSignup.email"
									placeholder="Email"
								>
									<i
										slot="prepend"
										class="fa fa-envelope-o"
									></i>
								</el-input>
							</el-form-item>
							<el-form-item prop="password1">
								<el-input
									type="password"
									v-model="formSignup.password1"
									placeholder="Password"
								>
									<i
										slot="prepend"
										class="fa fa-keyboard-o"
									></i>
								</el-input>
							</el-form-item>
							<el-form-item prop="password2">
								<el-input
									type="password"
									v-model="formSignup.password2"
									placeholder="Confirmation"
								>
									<i
										slot="prepend"
										class="fa fa-keyboard-o"
									></i>
								</el-input>
							</el-form-item>
							<el-popover
								placement="top"
								width="400"
								trigger="click"
							>
								<slide-verify
									:l="42"
									:r="10"
									:w="310"
									:h="155"
									slider-text="Slide to right"
									@success="onVerifySuccess"
									@fail="onVerifyFail"
									@refresh="onVerifyRefresh"
									@again="onVerifyAgain"
									ref="slideblock"
								></slide-verify>
								<el-button
									size="default"
									type="primary"
									class="button-login"
									slot="reference"
								>
									Sign up
								</el-button>
							</el-popover>
						</el-form>
					</el-card>
					<p
						class="page-login--options"
						flex="main:justify cross:center"
					>
						<span @click="redirect_to_login()">Sign in</span>
						<span @click="verfication_email_resent()"
							><d2-icon name="question-circle" /> Resent
							email</span
						>
					</p>
				</div>
			</account-form>
		</page-background>
	</div>
</template>

<script>
import PageBackground from "../components/page-background";
import AccountForm from "../components/account-form";
import { mapActions } from "vuex";
import localeMixin from "@/locales/mixin.js";
export default {
	components: { PageBackground, AccountForm },
	mixins: [localeMixin],
	data() {
		return {
			users: [
				{
					name: "Admin",
					email: "admin",
					password: "admin"
				},
				{
					name: "Editor",
					email: "editor",
					password: "editor"
				},
				{
					name: "User1",
					email: "user1",
					password: "user1"
				}
			],
			// ??????
			formSignup: {
				username: "ias",
				email: "ias@szu.edu.cn",
				password1: "",
				password2: ""
				// code: "v9am",
			},
			// ????????????
			rules: {
				username: [
					{
						required: true,
						message: "??????????????????",
						trigger: "blur"
					}
				],
				email: [
					{
						required: true,
						message: "?????????Email",
						trigger: "blur"
					}
				],
				password1: [
					{
						required: true,
						message: "???????????????",
						trigger: "blur"
					}
				],
				password2: [
					{
						required: true,
						message: "???????????????",
						trigger: "blur"
					}
				]
				// code: [
				// 	{
				// 		required: true,
				// 		message: "??????????????????",
				// 		trigger: "blur",
				// 	},
				// ],
			},
			verifyOk: false
		};
	},
	methods: {
		...mapActions("d2admin/account", ["signup"]),
		redirect_to_login() {
			this.$router.replace("/login");
		},
		onVerifySuccess() {
			this.verifyOk = true;
			this.submit();
		},
		onVerifyFail() {
			this.verifyOk = false;
			this.$message.warning("Failed! Try again!");
			this.$refs.slideblock.reset();
		},
		onVerifyAgain() {
			this.$message.warning("Failed! Try again!");
			// ??????
			this.$refs.slideblock.reset();
		},
		/**
		 * @description ????????????
		 */
		// ??????????????????
		submit() {
			this.$refs.regitrationForm.validate(valid => {
				if (
					valid &&
					this.formSignup.password1 == this.formSignup.password2
				) {
					// ??????
					// ?????? ?????????????????????????????????
					// ????????????????????????????????????????????????
					this.signup({
						username: this.formSignup.username,
						email: this.formSignup.email,
						password1: this.formSignup.password1,
						password2: this.formSignup.password2
					}).then(() => {
						// ?????????????????????????????????????????????
						this.$router.replace(this.$route.query.redirect || "/");
					});
				} else {
					// ????????????????????????
					this.$message.error("??????????????????????????????");
				}
			});
		},
		verfication_email_resent() {
			this.$refs.regitrationForm.validate(valid => {
				if (
					valid &&
					this.formSignup.password1 == this.formSignup.password2
				) {
					this.$api
						.SYS_USER_RESENT_EMAIL({
							username: this.formSignup.username,
							email: this.formSignup.email,
							password1: this.formSignup.password1,
							password2: this.formSignup.password2
						})
						.then(resp => {
							if (resp.code == 200) {
								this.$message.success(
									"Verification Email sent."
								);
							}
							this.$router.replace(
								this.$route.query.redirect || "/"
							);
						});
				} else {
					// ????????????????????????
					this.$message.error("??????????????????????????????");
				}
			});
		}
	}
};
</script>

<style lang="scss">
#slideVerify {
	margin: 0 auto;
}
</style>
