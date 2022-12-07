<template>
	<d2-container
		class="calctasks-submit-index-container"
		v-loading="load_calctask_categories"
		better-scroll
	>
		<el-row :gutter="20">
			<el-col
				:xl="6"
				:sm="8"
				v-for="(category, index) in calctask_categories"
				:key="category.uuid"
				:name="category.uuid"
			>
				<el-card class="box-card d2-card d2-mt-16" shadow="hover">
					<div slot="header" class="d2-text-center">
						<span>{{ category.category_name }}</span>
					</div>
					<el-progress
						:percentage="
							parseFloat(
								(Math.random() * 100 * index) / 10
							).toFixed(0)
						"
						:color="customColorMethod"
					></el-progress>
					<div class="d2-text-center d2-mt-20">
						<el-button
							:icon="
								category.disabled
									? 'el-icon-close'
									: 'el-icon-plus'
							"
							:disabled="category.disabled"
							:type="category.disabled ? 'danger' : 'primary'"
							plain
							@click.native.prevent.stop="
								AddNewTasks(
									category.uuid,
									category.category_name
								)
							"
							>New Task</el-button
						>
						<el-button
							icon="el-icon-reading"
							:disabled="category.disabled"
							:type="category.disabled ? 'danger' : 'primary'"
							plain
							@click.native.prevent.stop="
								goToDocsPage(
									'http://aiida-shengbte.ias.poryoung.cn/index.html'
								)
							"
							>Watch Docs</el-button
						>
					</div>
				</el-card>
			</el-col>
		</el-row>
	</d2-container>
</template>

<script>
export default {
	name: "calctasks-submit-index",
	data() {
		return {
			percentage: 20,
			calctask_categories: [],
			calctasks_types: [],
			load_calctask_categories: true,
			activeCards: []
		};
	},
	methods: {
		customColorMethod(percentage) {
			if (percentage < 30) {
				return "#909399";
			} else if (percentage < 70) {
				return "#e6a23c";
			} else {
				return "#67c23a";
			}
		},
		AddNewTasks(category_id, category_name) {
			if (category_name == "") {
				this.$message.error("Invalid category name.");
			} else {
				category_name = category_name.toLowerCase();
				this.$router.replace(`/calctasks/submit/${category_name}`);
			}
		},
		goToDocsPage(url) {
			window.open(url, "_blank");
		}
	},
	mounted() {
		// TODO: Store locally to reduce requests.
		this.$api.GetList(`/calctask_categories/`).then(resp => {
			if (resp.code == 0) {
				this.calctask_categories = resp.data;
				this.calctask_categories.forEach(element => {
					this.activeCards.push(element.uuid);
				});
			}
			this.load_calctask_categories = false;
		});
	}
};
</script>

<style lang="scss">
.calctasks-submit-index-container {
	.box-card {
		background: transparent;
	}
}
</style>
