<template>
	<d2-container class="dashboard-calctasks-container">
		<el-table
			v-loading="tableLoading"
			:data="tableData"
			:default-sort="{ prop: 'uuid', order: 'descending' }"
			@row-click="viewDetail"
			class="data-table"
			height="100%"
		>
			<el-table-column type="expand">
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
								<el-form-item label="uuid">
									<el-input
										disabled
										:value="props.row.uuid"
									></el-input>
								</el-form-item>
								<el-form-item prop="label" label="task label">
									<el-input
										disabled
										:value="props.row.label"
									></el-input>
								</el-form-item>
								<el-form-item label="created time">
									<el-date-picker
										v-model="props.row.created_time"
										type="datetime"
										placeholder="created time"
										readonly
									>
									</el-date-picker>
								</el-form-item>
								<el-form-item
									prop="description"
									label="task description"
								>
									<el-input
										disabled
										type="textarea"
										autosize
										:value="props.row.description"
									></el-input>
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
									:code="props.row.logs.join('\n')"
									lang="bash"
								/>
							</el-card>
						</el-tab-pane>
					</el-tabs>
				</template>
			</el-table-column>
			<el-table-column prop="uuid" label="uuid" align="center">
			</el-table-column>
			<el-table-column prop="label" label="label" sortable align="center">
			</el-table-column>
			<el-table-column prop="user.username" label="user" align="center">
			</el-table-column>
			<el-table-column
				prop="created_time"
				label="created time"
				sortable
				align="center"
			>
				<template slot-scope="props">
					<el-date-picker
						v-model="props.row.created_time"
						type="datetime"
						placeholder="created time"
						readonly
						class="data-date-picker"
					>
					</el-date-picker>
				</template>
			</el-table-column>
			<!-- TODO: Filter -->
			<el-table-column label="status" align="center">
				<template slot-scope="props">
					<i
						class="el-icon-warning-outline"
						v-if="props.row.status == 'failed'"
					></i>
					<i
						class="el-icon-loading"
						v-else-if="
							['running', 'waiting'].includes(props.row.status)
						"
					></i>
					<i
						class="el-icon-check"
						v-else-if="props.row.status == 'completed'"
					></i>
					<i class="el-icon-question" v-else></i>
					<span>{{ props.row.status }}</span>
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
		</template>
	</d2-container>
</template>

<script>
const apiPrefix = "/tasks/";
export default {
	name: "dashboard-system-tasks",
	data() {
		return {
			tableData: [
				{
					uuid: "1325121640423690200",
					celery_id: "e96741e4-f36a-448c-affa-4d7a1bcb70ef",
					user: { uuid: "1325121640423690200", name: "ias" },
					code: {},
					status: "failed"
				},
				{
					uuid: "1325121640423690200",
					user: "IAS",
					status: "watting"
				},
				{
					uuid: "1325121640423690200",
					user: "IAS",
					status: "running"
				}
			],
			tableLoading: true,
			currentPage: 1,
			totalCount: 0,
			pageSize: 10,
			// queryForm: { status_exclude: "failed,completed" }
			queryForm: {}
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
		viewDetail(row, column, event) {
			let uuid = row.uuid;
			if (uuid == "") {
				this.$message.error("UUID is None!");
			}
			// this.$router.push({
			// 	path: `/data/tasks/${uuid}`
			// });
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
		handleTabSwitch() {}
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
<style lang="scss">
.dashboard-tasks-container {
}
</style>
