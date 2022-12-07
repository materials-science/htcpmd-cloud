<template>
	<d2-container class="cluster-plugins-container" v-loading="loading_plugins">
		<el-row :gutter="20">
			<el-col
				:xl="6"
				:sm="8"
				v-for="(plugin, index) in plugins"
				:key="plugin.name"
				:name="plugin.name"
			>
				<el-card class="box-card d2-card d2-mt-16" shadow="hover">
					<div
						slot="header"
						class="color-text-main"
						@click="showPluginDetailDialog(plugin)"
					>
						<span>{{ plugin.name }}</span>
						<a target="_blank" :href="plugin.url">
							<d2-icon name="home" class="d2-fr" />
						</a>
					</div>
					<p class="color-text-sub text-size-12 d2-pb-8">
						{{ plugin.author }}
						<span class="d2-fr">{{ plugin.version }}</span>
					</p>
					<p class="color-text-normal break-spaces">
						{{ plugin.description }}
					</p>
				</el-card>
			</el-col>
		</el-row>
		<el-dialog
			:visible.sync="detail_dialog_visible"
			destroy-on-close
			class="plugin-detail-dialog"
		>
			<h5 class="color-text-main d2-text-center d2-p-8">
				Calculations
			</h5>
			<div
				v-for="(item, key) in detail_calculations"
				:key="item.class_name"
			>
				<el-card class="d2-m-4" shadow="never">
					<div slot="header">
						{{ key }}
						<span class="d2-fr color-text-sub">{{
							item.class_name
						}}</span>
					</div>
					<p class="color-text-sub break-spaces">
						{{ item.description }}
					</p>
				</el-card>
			</div>
			<h5 class="color-text-main d2-text-center d2-p-8">
				Workflows
			</h5>
			<div v-for="(item, key) in detail_workflows" :key="item.class_name">
				<el-card class="d2-m-4" shadow="never">
					<div slot="header">
						{{ key }}
						<span class="d2-fr color-text-sub">{{
							item.class_name
						}}</span>
					</div>
					<p class="color-text-sub break-spaces">
						{{
							item.description != null
								? item.description
								: "No description."
						}}
					</p>
				</el-card>
			</div>
		</el-dialog>
	</d2-container>
</template>

<script>
export default {
	name: "cluster-plugins",
	data() {
		return {
			loading_plugins: true,
			detail_dialog_visible: false,
			plugins: [],
			detail_calculations: {},
			detail_workflows: {}
		};
	},
	methods: {
		showPluginDetailDialog(plugin) {
			console.log(plugin);
			this.detail_dialog_visible = true;
			this.detail_calculations = plugin.calculations;
			this.detail_workflows = plugin.workflows;
		}
	},
	mounted() {
		// TODO: Store locally to reduce requests.
		this.$api.GetList(`/aiida_plugins/`).then(resp => {
			if (resp.code == 0) {
				this.plugins = resp.data;
			}
			this.loading_plugins = false;
		});
	}
};
</script>

<style lang="scss">
.cluster-plugins-container {
	.plugin-detail-dialog {
		.el-card__body {
			padding: 8px 20px;
		}
	}
}
</style>
