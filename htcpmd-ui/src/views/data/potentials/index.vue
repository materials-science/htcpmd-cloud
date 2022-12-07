<template>
	<d2-container>
		<el-table
			v-loading="tableLoading"
			:data="tableData"
			:default-sort="{ prop: 'uuid', order: 'descending' }"
			class="data-table"
			height="100%"
		>
			<el-table-column type="expand" fixed>
				<template slot-scope="props">
					<el-tabs
						type="border-card"
						@tab-click="
							handleTabSwitch($event, props.$index, props.row)
						"
					>
						<el-tab-pane label="Attributes">
							<el-form
								label-position="top"
								class="data-table-expand--wrap"
							>
								<el-form-item prop="aiida_pk" label="aiida_pk">
									<el-input
										disabled
										:value="props.row.aiida_pk"
									></el-input>
								</el-form-item>
								<el-form-item prop="dirpath" label="dirpath">
									<el-input
										disabled
										:value="props.row.dirpath"
									></el-input>
								</el-form-item>
								<el-form-item label="created time">
									<el-date-picker
										v-model="props.row.created_time"
										type="datetime"
										placeholder="created time"
										readonly
										class="structure-date-picker"
									>
									</el-date-picker>
								</el-form-item>
								<el-form-item
									prop="description"
									label="description"
								>
									<el-input
										type="textarea"
										:autosize="{ minRows: 2, maxRows: 8 }"
										:value="props.row.description"
										disabled
									>
									</el-input>
								</el-form-item>
							</el-form>
						</el-tab-pane>
						<el-tab-pane label="User">
							<el-form
								label-position="left"
								class="data-table-expand--wrap"
							>
								<el-form-item
									v-for="(val, key) in props.row.user"
									:key="key"
									:label="key"
								>
									<el-input disabled :value="val"></el-input>
								</el-form-item>
							</el-form>
						</el-tab-pane>
						<el-tab-pane label="Logs">
							<el-card>
								<d2-highlight
									:code="props.row.logs"
									lang="bash"
								/>
							</el-card>
						</el-tab-pane>
					</el-tabs>
				</template>
			</el-table-column>
			<el-table-column prop="uuid" label="uuid" sortable align="center">
			</el-table-column>
			<el-table-column prop="label" label="label" align="center">
			</el-table-column>
			<el-table-column prop="type" label="type" align="center">
			</el-table-column>
			<el-table-column prop="count" label="count" align="center">
			</el-table-column>
			<el-table-column prop="activated" label="activated" align="center">
				<template slot-scope="scope">
					<el-button
						:type="scope.row.activated ? 'success' : 'danger'"
						circle
					></el-button>
				</template>
			</el-table-column>
			<el-table-column label="operations" align="center" min-width="150">
				<template slot-scope="scope">
					<el-button
						size="mini"
						type="danger"
						plain
						@click="handleDelete(scope.$index, scope.row)"
						>delete</el-button
					>
				</template>
			</el-table-column>
		</el-table>
		<template slot="footer">
			<el-row type="flex">
				<el-col :span="12">
					<el-button
						class="add-new-button"
						plain
						type="primary"
						@click="newPotentialsDialogVisible = true"
						>Add New Potentials</el-button
					>
				</el-col>
				<el-col :span="12" class="flex-end-row">
					<el-pagination
						background
						@size-change="handlePageSizeChange"
						@current-change="handleCurrentPageChange"
						:current-page="currentPage"
						:page-sizes="[2, 10, 20, 30, 40]"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next"
						:total="totalCount"
					></el-pagination>
				</el-col>
			</el-row>
		</template>
		<el-dialog
			title="add new potentials"
			:center="true"
			:visible.sync="newPotentialsDialogVisible"
		>
			<potentials-add
				v-on:cancel="newPotentialsDialogVisible = false"
				v-on:success="handleCurrentPageChange(currentPage)"
			></potentials-add>
		</el-dialog>
	</d2-container>
</template>

<script>
import setting from "@/setting.js";
import PotentialsAdd from "./potentials-add";
const apiPrefix = "/potentials/";
export default {
	name: "data-potentials",
	components: { PotentialsAdd },
	data() {
		return {
			tableData: [
				{
					uuid: "1325121640423690200",
					name: "pbe",
					type: "vasp-potcar",
					count: 0
				},
				{
					uuid: "1325121640423690200",
					name: "oncv",
					type: "upf"
				}
			],
			tableLoading: true,
			currentPage: 1,
			totalCount: 0,
			pageSize: 10,
			queryForm: {},
			newPotentialsDialogVisible: false
		};
	},
	methods: {
		pageRequest(
			page = this.currentPage,
			size = this.pageSize,
			options = this.queryForm
		) {
			return this.$api.GetList(
				apiPrefix,
				Object.assign({ page, size }, options)
			);
		},
		handleCurrentPageChange(page) {
			this.tableLoading = true;
			this.pageRequest(page).then(resp => {
				let data = resp.data;
				this.tableData = data.results;
				this.totalCount = data.count;
				this.currentPage = page;
				this.tableLoading = false;
			});
		},
		handlePageSizeChange(size) {
			this.tableLoading = true;
			this.pageSize = size;
			this.pageRequest().then(resp => {
				let data = resp.data;
				this.tableData = data.results;
				this.totalCount = data.count;
				this.tableLoading = false;
			});
		},
		handleDelete(index, row) {
			if (row.uuid == "") {
				return this.$message.error("UUID is None!");
			}
			this.$confirm("This will delete permanently, sure?", "Warning", {
				confirmButtonText: "confirm",
				cancelButtonText: "cancel",
				type: "warning"
			})
				.then(() => {
					this.tableLoading = true;
					this.$api.DelObj(apiPrefix, row.uuid).then(resp => {
						if (resp.code == 0) {
							this.$message.success("Delete Success");
							return this.handleCurrentPageChange(
								this.currentPage
							);
						}
						this.tableLoading = false;
					});
				})
				.catch(() => {
					this.$message({
						type: "info",
						message: "canceled"
					});
				});
		},
		handleTabSwitch(event, index, row) {}
	},
	mounted() {
		this.pageRequest().then(resp => {
			let data = resp.data;
			this.tableData = data.results;
			this.totalCount = data.count;
			this.tableLoading = false;
		});
	}
};
</script>

<style lang="scss" scoped>
.add-tasks-button {
	position: absolute;
	right: 16px;
	bottom: 16px;
}
.volume-input input {
	background: none !important;
	border: none !important;
	color: inherit !important;
	cursor: default !important;
}
.inline-display-block {
	text-align: center;
	> .el-row > .el-col {
		border-bottom: 1px solid #99a9bf;
		border-top: 1px solid #99a9bf;
	}
	.el-col {
		font-size: 14px;
		padding: 8px;
	}
}
</style>
