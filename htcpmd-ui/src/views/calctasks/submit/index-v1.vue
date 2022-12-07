<template>
	<d2-container
		class="data-calctasks-upload-index-container"
		v-loading="load_calctask_categories || load_calctasks_types"
		better-scroll
	>
		<el-collapse v-model="activeCards">
			<el-collapse-item
				class="box-card"
				v-for="category in calctask_categories"
				:key="category.id"
				:name="category.id"
			>
				<template slot="title">
					<el-row
						type="flex"
						justify="space-between"
						align="center"
						style="flex: 1"
					>
						<el-col :xl="6" :sm="8">
							<h2>{{ category.category_name }}</h2>
						</el-col>
						<el-col :xl="6" :sm="16">
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
										category.id,
										category.category_name
									)
								"
								>Add A Task</el-button
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
						</el-col>
					</el-row>
				</template>
				<el-card class="box-card d2-card" shadow="hover">
					<template v-for="type in calctasks_types">
						<template v-if="type.category.id == category.id">
							<div :key="type.id" class="types-list">
								<el-row>
									<el-col :span="6">
										<h3>
											<el-link
												type="primary"
												:href="type.doc_link"
												:disabled="!type.doc_link"
												target="_blank"
												>{{ type.type_name }}</el-link
											><i
												class="el-icon-warning-outline"
												style="color: #f56c6c; padding: 8px"
												v-if="type.disabled"
											></i>
										</h3>
									</el-col>
								</el-row>
								<el-row>
									<el-col :span="12">
										<p>
											{{
												type.description ||
													"No description"
											}}
										</p>
									</el-col>
								</el-row>
							</div>
						</template>
					</template>
				</el-card>
			</el-collapse-item>
		</el-collapse>
	</d2-container>
</template>

<script>
export default {
	name: "data-calctasks-upload-index",
	data() {
		return {
			calctask_categories: [],
			calctasks_types: [],
			load_calctask_categories: true,
			load_calctasks_types: true,
			activeCards: []
		};
	},
	methods: {
		AddNewTasks(category_id, category_name) {
			this.$router.replace(`/calctasks/submit/${category_name}`);
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
					this.activeCards.push(element.id);
				});
			}
			this.load_calctask_categories = false;
		});
		this.$api.GetList(`/calctask_types/`).then(resp => {
			if (resp.code == 0) {
				this.calctasks_types = resp.data;
			} else {
				this.$message.error("No CalcTasks Types Avaliable.");
				setTimeout(() => {
					this.$store.dispatch("d2admin/page/close", {
						tagName: "/calctasks/submit"
					});
				}, 1500);
				return;
			}
			this.load_calctasks_types = false;
		});
	}
};
</script>

<style lang="scss">
.data-calctasks-upload-index-container {
	.box-card {
		.el-collapse-item__header {
			padding: 36px 0;
		}
		.el-collapse-item__wrap {
			background-color: transparent;
		}
		.types-list {
			margin: 0 8px 32px;
			p {
				padding: 8px 16px;
			}
		}
	}
}
</style>
