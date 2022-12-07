<template>
	<d2-container class="calctasks-index-container">
		<el-table
			v-loading="tableLoading"
			:data="tableData"
			:default-sort="{ prop: 'label', order: 'descending' }"
			@sort-change="sortChange"
			@row-click="viewDetail"
			class="data-table"
			height="100%"
		>
			<el-table-column type="expand">
				<template slot-scope="props">
					<el-tabs type="border-card">
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
								<el-form-item prop="aiida_pk" label="aiida pk">
									<el-input
										disabled
										:value="props.row.aiida_pk"
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
								<el-form-item
									prop="status_message"
									label="status message"
								>
									<el-input
										disabled
										type="textarea"
										autosize
										:value="props.row.status_message"
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
			<el-table-column prop="label" label="label" sortable align="center">
			</el-table-column>
			<!-- TODO: Filter -->
			<el-table-column
				prop="calctask_type"
				label="calc task type"
				align="center"
			>
			</el-table-column>
			<el-table-column prop="structure.formula" label="structure" sortable align="center">
				<template slot-scope="props">
					<el-button
						@click.native.prevent="
							goToStructurePage(props.row.structure.uuid)
						"
						type="text"
						size="small"
						v-html="props.row.structure.formula"
					>
					</el-button>
				</template>
			</el-table-column>
			<el-table-column
				sortable="custom"
				label="created time"
				prop="created_time"
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
			<el-table-column
				prop="supports"
				sortable
				label="supports"
				align="center"
			>
			</el-table-column>
			<el-table-column prop="user.username" label="user" align="center">
			</el-table-column>
			<el-table-column
				prop="status"
				label="status"
				sortable
				align="center"
			>
				<template slot-scope="props">
					<i
						class="el-icon-warning-outline"
						v-if="props.row.status == 'failed'"
					></i>
					<i
						class="el-icon-loading"
						v-else-if="
							[
								'running',
								'submitted',
								'created',
								'creating'
							].includes(props.row.status)
						"
					></i>
					<i
						class="el-icon-circle-close"
						v-else-if="props.row.status == 'deleted'"
					></i>
					<i
						class="el-icon-check"
						v-else-if="props.row.status == 'completed'"
					></i>
					<i class="el-icon-question" v-else></i>
					<span>{{ props.row.status }}</span>
				</template>
			</el-table-column>
		</el-table>
		<template slot="footer">
			<el-row :gutter="20" class="color-text-sub d2-text-center d2-m-10">
				<el-col class="flex-center-row">
					<span class="d2-mr-10">Tasks</span>
					<el-tag class="d2-mr-10" effect="plain" size="small"
						>{{ calctasks_info.count }} pending</el-tag
					>
					<span class="d2-mr-10">Checking Interval</span>
					<el-tag class="d2-mr-10" effect="plain" size="small"
						>{{ calctasks_info.check_interval_time }} s</el-tag
					>
					<span class="d2-mr-10">Last Checking Date</span>
					<el-tag class="d2-mr-10" effect="plain" size="small">{{
						dayjs(calctasks_info.last_check_tasks_date).format(
							"YYYY-M-D HH:mm:ss"
						)
					}}</el-tag>
					<span class="d2-mr-10">daemon</span>
					<!-- <el-tag class="d2-mr-10" effect="plain" size="small">{{
					calctasks_info.daemon.status
				}}</el-tag> -->
					<el-button
						:type="
							calctasks_info.daemon.is_running
								? 'success'
								: 'danger'
						"
						circle
						size="mini"
					></el-button>
				</el-col>
			</el-row>
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
			<el-button
				class="add-tasks-button"
				plain
				type="primary"
				@click="addNewTasks"
				>Add New Tasks</el-button
			>
		</template>
	</d2-container>
</template>

<script>
import dayjs from "dayjs";
const apiPrefix = "/calctasks/";
export default {
	name: "calctasks-index",
	data() {
		return {
			dayjs,
			tableData: [
				{
					uuid: "1325121640423690200",
					structure: { uuid: "1325121640423690200", formula: "Si" },
					calc_jobs_type: "ShengBTE",
					calc_jobs_label: "ShengBTEWorkChain",
					user: "IAS",
					supports: 0,
					status: "running"
				},
				{
					uuid: "1325121640423690200",
					celery_id: "e96741e4-f36a-448c-affa-4d7a1bcb70ef",
					structure: { uuid: "1325121640423690200", formula: "Si" },
					user: { uuid: "1325121640423690200", name: "ias" },
					code: {},
					calc_jobs_type: "ShengBTE",
					calc_jobs_label: "ShengBTEWorkChain",
					supports: 1,
					status: "failed"
				},
				{
					uuid: "1325121640423690200",
					structure: { uuid: "1325121640423690200", formula: "Si" },
					calc_jobs_type: "ShengBTE",
					calc_jobs_label: "ShengBTEWorkChain",
					user: "IAS",
					supports: 2,
					status: "created"
				}
			],
			tableLoading: true,
			currentPage: 1,
			totalCount: 0,
			pageSize: 10,
			queryForm: {},
			calctasks_info: {
				daemon: {},
				count: 0,
				last_check_tasks_date: null
			},
			loading_calctasks_info: true
		};
	},
	methods: {
		getTasksInfo() {
			return this.$api.ApiRequest(`${apiPrefix}info`).then(resp => {
				if (resp.code == 0) {
					this.calctasks_info = resp.data;
					this.loading_calctasks_info = false;
					console.log(this.calctasks_info);
				}
			});
		},
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
			this.$router.push({
				path: `/calctasks/detail/${uuid}`
			});
		},
		goToStructurePage(uuid) {
			if (uuid == "") {
				this.$message.error("Structure UUID is None!");
			}
			this.$router.push({
				path: `/structures/${uuid}`
			});
		},
		addNewTasks() {
			this.$router.replace({
				path: `/calctasks/submit/`
			});
		},
		sortChange(column) {
			console.log(column);
			if (column.order == null) return;
			if (column.prop == "created_time") {
				this.queryForm = {
					ordering: `${
						column.order == "descending" ? "-" : ""
					}created_time`
				};
				this.handleCurrentPageChange(this.currentPage);
			}
		}
	},
	mounted() {
		this.pageRequest().then(resp => {
			let data = resp.data;
			this.tableData = data.results;
			this.totalCount = data.count;
			this.tableLoading = false;
		});
		this.getTasksInfo();
	}
};
</script>

<style lang="scss" scoped>
.add-tasks-button {
	position: absolute;
	right: 16px;
	bottom: 16px;
}
</style>
