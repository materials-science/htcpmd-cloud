<template>
	<div class="page-login--layer">
		<div
			class="page-login--content"
			flex="dir:top main:justify cross:stretch box:justify"
		>
			<div class="page-login--content-header">
				<p class="page-login--content-header-motto">
					<!-- 时间是一切财富中最宝贵的财富 -->
					Life is valued treasure time
				</p>
			</div>
			<div
				class="page-login--content-main"
				flex="dir:top main:center cross:center"
			>
				<slot></slot>
			</div>
			<div class="page-login--content-footer">
				<!-- <p class="page-login--content-footer-locales">
					<a
						v-for="language in $languages"
						:key="language.value"
						@click="onChangeLocale(language.value)"
					>
						{{ language.label }}
					</a>
				</p> -->
				<p class="page-login--content-footer-copyright">
					Copyright
					<d2-icon name="copyright" />
					2020 Shenzhen University
					<a href="https://github.com/PorYoung"> @PorYoung </a>
				</p>
				<p class="page-login--content-footer-options">
					<a href="#">Help</a>
					<a href="#">Privacy</a>
					<a href="#">Terms</a>
				</p>
			</div>
		</div>
	</div>
</template>

<script>
export default {};
</script>

<style lang="scss">
.page-login {
	// 注册页面控件的容器
	.page-login--content {
		height: 100%;
		min-height: 500px;
	}
	// header
	.page-login--content-header {
		padding: 1em 0;
		.page-login--content-header-motto {
			margin: 0px;
			padding: 0px;
			color: $color-text-normal;
			text-align: center;
			font-size: 12px;
		}
	}
	// main
	.page-login--logo {
		width: 240px;
		margin-bottom: 2em;
		margin-top: -2em;
	}
	// 注册表单
	.page-login--form {
		width: 280px;
		// 卡片
		.el-card {
			margin-bottom: 15px;
			background: rgba(255, 255, 255, 0.7);
		}
		// 注册按钮
		.button-login {
			width: 100%;
		}
		// 输入框左边的图表区域缩窄
		.el-input-group__prepend {
			padding: 0px 14px;
		}
		.login-code {
			height: 40px - 2px;
			display: block;
			margin: 0px -20px;
			border-top-right-radius: 2px;
			border-bottom-right-radius: 2px;
		}
		// 注册选项
		.page-login--options {
			margin: 0px;
			padding: 0px;
			font-size: 14px;
			color: $color-primary;
			margin-bottom: 15px;
			font-weight: bold;
		}
		.page-login--quick {
			width: 100%;
		}
	}
}
</style>
