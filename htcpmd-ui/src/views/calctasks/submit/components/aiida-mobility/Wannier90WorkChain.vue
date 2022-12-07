<template>
	<el-tabs type="border-card" class="no-box-shadow no-bg">
		<el-tab-pane :label="type_selected.class_name">
			<el-row class="form-box">
				<h5>Description</h5>
				<p>
					{{ type_selected.description || "No Description." }}
				</p>
			</el-row>
			<el-row class="form-box">
				<h5>Helps</h5>
				<p>
					{{ type_selected.help || "No Help Docs." }}
				</p>
			</el-row>
		</el-tab-pane>
		<el-tab-pane label="Test">
			<el-row class="form-box">
				<h5>
					<span>CalcTask Label</span>
					<el-tooltip
						class="d2-pl-16"
						effect="dark"
						content="Set a label for your task"
						placement="bottom-end"
					>
						<el-button circle class="no-bg no-border">
							<d2-icon name="question-circle-o"></d2-icon>
						</el-button>
					</el-tooltip>
				</h5>
				<el-input v-model="calctask.label"></el-input>
			</el-row>
		</el-tab-pane>
	</el-tabs>
</template>

<script>
import NumberTable from "@/components/number-table";
export default {
	name: "Wannier90WorkChain",
	components: {
		NumberTable
	},
	props: {
		type_selected: { require: true },
		calctask: { required: true }
	}
	// data() {}
};
</script>

<style></style>
