<template>
	<d2-container class="dashboard-calctasks-container">
		<el-row :gutter="20" style="height: 100%">
			<el-col :xl="8" :lg="8" :md="8" v-loading="loading_daemon_status">
				<el-card class="d2-mb-32">
					<div slot="header" class="clearfix">
						<span>Tasks</span>
					</div>
					<el-row
						:gutter="20"
						type="flex"
						justify="space-between"
						class="d2-mb-8 color-text-sub"
					>
						<el-col>Running</el-col>
						<el-col>{{ calctasks_info.count }}</el-col>
					</el-row>
					<el-row
						:gutter="20"
						type="flex"
						justify="space-between"
						class="d2-mb-8 color-text-sub"
					>
						<el-col>Check Interval</el-col>
						<el-col>{{
							calctasks_info.check_interval_time
						}}</el-col>
					</el-row>
					<el-row
						:gutter="20"
						type="flex"
						justify="space-between"
						class="d2-mb-8 color-text-sub"
					>
						<el-col>Last Check Date</el-col>
						<el-col>{{
							dayjs(calctasks_info.last_check_tasks_date).format(
								"YYYY-M-D HH:mm:ss"
							)
						}}</el-col>
					</el-row>
				</el-card>
				<el-card class="d2-mb-32">
					<div slot="header" class="clearfix">
						<span>Daemon</span>
						<el-button
							style="float: right"
							:type="daemon.is_running ? 'success' : 'danger'"
							circle
							size="mini"
						></el-button>
					</div>
					<el-row :gutter="20" class="d2-mb-8">
						<el-col :span="12">
							<el-progress
								type="circle"
								:percentage="daemon_response.info.cpu"
								:format="
									percentage =>
										percentageFormator(percentage, 'cpu')
								"
							></el-progress>
						</el-col>
						<el-col :span="12">
							<el-progress
								type="circle"
								:percentage="daemon_response.info.mem"
								:format="
									percentage =>
										percentageFormator(percentage, 'mem')
								"
							></el-progress>
						</el-col>
					</el-row>
					<el-row
						:gutter="20"
						type="flex"
						justify="space-between"
						class="d2-mb-8 color-text-sub"
					>
						<el-col>Created</el-col>
						<el-col>{{
							dayjs(daemon_response.create_time).format(
								"YYYY-M-D HH:mm:ss"
							)
						}}</el-col>
					</el-row>
					<el-row
						:gutter="20"
						type="flex"
						justify="space-between"
						class="d2-mb-8 color-text-sub"
					>
						<el-col>Mem Info</el-col>
						<el-col
							>{{ daemon_response.info.mem_info1 }} /
							{{ daemon_response.info.mem_info2 }}</el-col
						>
					</el-row>
				</el-card>
				<el-card class="d2-mb-32">
					<div slot="header" class="clearfix">
						<span>Workers</span>
					</div>
					<el-row
						v-for="(item, key) in worker_response.info"
						:key="key"
						class="d2-mb-8"
					>
						<el-row :gutter="20" class="d2-mb-8">
							<el-col :span="12">
								<el-progress
									type="circle"
									:percentage="item.cpu"
									:format="
										percentage =>
											percentageFormator(
												percentage,
												'cpu'
											)
									"
								></el-progress>
							</el-col>
							<el-col :span="12">
								<el-progress
									type="circle"
									:percentage="item.mem"
									:format="
										percentage =>
											percentageFormator(
												percentage,
												'mem'
											)
									"
								></el-progress>
							</el-col>
						</el-row>
						<el-row
							:gutter="20"
							type="flex"
							justify="space-between"
							class="d2-mb-8 color-text-sub"
						>
							<el-col>Created</el-col>
							<el-col>{{
								dayjs(item.create_time).format(
									"YYYY-M-D HH:mm:ss"
								)
							}}</el-col>
						</el-row>
						<el-row
							:gutter="20"
							type="flex"
							justify="space-between"
							class="d2-mb-8 color-text-sub"
						>
							<el-col>Mem Info</el-col>
							<el-col
								>{{ item.mem_info1 }} /
								{{ item.mem_info2 }}</el-col
							>
						</el-row>
					</el-row>
				</el-card>
			</el-col>
			<el-col :xl="16" :lg="16" :md="16" style="height: 100%">
				<el-table
					v-loading="tableLoading"
					:data="tableData"
					:default-sort="{ prop: 'label', order: 'descending' }"
					@row-click="viewDetail"
					class="data-table shallow-box-shadow border-radius-4"
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
										<el-form-item
											prop="aiida_pk"
											label="aiida pk"
										>
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
												:value="
													props.row.status_message
												"
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
											<el-input
												disabled
												:value="val"
											></el-input>
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
					<el-table-column
						prop="label"
						label="label"
						sortable
						align="center"
					>
					</el-table-column>
					<!-- TODO: Filter -->
					<el-table-column
						prop="calctask_type"
						label="calc task type"
						align="center"
					>
					</el-table-column>
					<el-table-column
						label="structure"
						prop="structure.formula"
						sortable
						align="center"
					>
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
						prop="supports"
						sortable
						label="supports"
						align="center"
					>
					</el-table-column>
					<el-table-column label="status" align="center">
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
			</el-col>
		</el-row>
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
import util from "@/libs/util";
const apiPrefix = "/calctasks/";
const wsPrefix = "/daemon/";
export default {
	name: "dashboard-calctasks",
	data() {
		return {
			dayjs,
			tableData: [
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
				},
				{
					uuid: "1325121640423690200",
					structure: { uuid: "1325121640423690200", formula: "Si" },
					calc_jobs_type: "ShengBTE",
					calc_jobs_label: "ShengBTEWorkChain",
					user: "IAS",
					supports: 0,
					status: "running"
				}
			],
			tableLoading: true,
			currentPage: 1,
			totalCount: 0,
			pageSize: 10,
			queryForm: { status_exclude: "failed,completed,deleted" },
			socket: null,
			calctasks_info: {},
			daemon: {},
			daemon_response: {
				id: "fc9e71bb076949b5a2477025aaa92d29",
				status: "ok",
				time: 1622185416.449202,
				info: {
					age: 10465.129193544388,
					children: [],
					cmdline: "python",
					cpu: 0,
					create_time: 1622174951.32,
					ctime: "0:08.50",
					mem: 0.155,
					mem_info1: "79M",
					mem_info2: "1G",
					nice: 0,
					pid: 28616,
					username: "ias"
				}
			},
			worker_response: {
				id: "32f1e6a7420746889eb13a725a21dd8e",
				name: "aiida-htcpmd_dev",
				status: "ok",
				time: 1622185416.437131,
				info: {
					10723: {
						age: 50.609389543533325,
						children: [],
						cmdline: "python",
						cpu: 0,
						create_time: 1622185365.01,
						ctime: "0:01.36",
						mem: 0.174,
						mem_info1: "89M",
						mem_info2: "1G",
						nice: 0,
						pid: 10723,
						started: 1622185365.8261983,
						username: "ias",
						wid: 2
					}
				}
			},
			loading_daemon_status: true
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
			this.$router.push({
				path: `/data/calctasks/detail/${uuid}`
			});
		},
		goToStructurePage(uuid) {
			if (uuid == "") {
				this.$message.error("Structure UUID is None!");
			}
			this.$router.push({
				path: `/structures/detail/${uuid}`
			});
		},
		addNewTasks() {
			this.$router.replace({
				path: `/calctasks/submit`
			});
		},
		closeWebSocket() {
			this.socket && this.socket.close();
		},
		onWSMessage(data) {
			data = JSON.parse(data.data);
			if (data.data) {
				data = data.data;
				if (data.hasOwnProperty("daemon") && data.daemon) {
					let daemon = data.daemon;
					this.daemon = {
						is_running: daemon.is_running,
						profile: daemon.profile,
						status: daemon.status
					};
					this.daemon_response = daemon.daemon_response || {};
					this.worker_response = daemon.worker_response || {};
					this.calctasks_info = {
						check_interval_time: data.check_interval_time,
						count: data.count,
						last_check_tasks_date: data.last_check_tasks_date
					};
				}
			} else {
				this.$message.info(data.msg);
			}
            this.loading_daemon_status = false
		},
		onWSError() {},
		percentageFormator(per, text) {
			return `${text} ${per}%`;
		}
	},
	mounted() {
		this.pageRequest().then(resp => {
			let data = resp.data;
			this.tableData = data.results;
			this.totalCount = data.count;
			this.tableLoading = false;
		});
		this.$connect(util.getWebsocketUrl(wsPrefix));
		this.socket = this.$socket;
		this.socket.onmessage = data => this.onWSMessage(data);
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
<style lang="scss">
.dashboard-calctasks-container {
	.data-table {
		.el-table__body-wrapper {
			@include scrollBar();
		}
	}
}
</style>
