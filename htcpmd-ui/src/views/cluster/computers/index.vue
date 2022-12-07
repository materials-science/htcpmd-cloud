<template>
	<d2-container type="full" class="cluster-computers-container">
		<el-table
			v-loading="tableLoading"
			:data="tableData"
			style="width: 100%"
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
								label-position="left"
								class="data-table-expand--wrap"
							>
								<el-form-item
									v-for="(val, key) in props.row.attributes"
									:key="key"
									:label="key"
								>
									<el-input disabled :value="val"></el-input>
								</el-form-item>
							</el-form>
						</el-tab-pane>
						<el-tab-pane
							label="Test"
							@tab-click="handleTest(props.$index, props.row)"
						>
							<el-card>
								<transition-group name="log-list" tag="div">
									<d2-highlight
										v-for="item in testLogs[props.row.uuid]"
										:key="item.id"
										:code="item.msg"
										lang="bash"
										:class="`log--${item.type}`"
									/>
								</transition-group>
							</el-card>
						</el-tab-pane>
					</el-tabs>
				</template>
			</el-table-column>
			<el-table-column
				prop="uuid"
				label="uuid"
				sortable
				align="center"
			></el-table-column>
			<el-table-column prop="label" label="label" align="center">
			</el-table-column>
			<el-table-column
				prop="attributes.hostname"
				label="hostname"
				align="center"
			>
			</el-table-column>
			<el-table-column prop="user.email" label="user" align="center">
			</el-table-column>
			<el-table-column label="operations" align="center" min-width="150">
				<template slot-scope="scope">
					<el-button
						size="mini"
						type="primary"
						plain
						@click="handleNewCode(scope.$index, scope.row)"
						>new code</el-button
					>
					<!-- TODO: editable -->
					<!-- <el-button
						size="mini"
						type="warning"
						plain
						@click="handleEdit(scope.$index, scope.row)"
						>Edit</el-button
					> -->
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
			<el-row type="flex" align="middle" :gutter="20">
				<el-col :span="18">
					<el-pagination
						background
						@size-change="handlePageSizeChange"
						@current-change="handleCurrentPageChange"
						:current-page="currentPage"
						:page-sizes="[10, 20, 30, 40]"
						:page-size="pageSize"
						layout="total, sizes, prev, pager, next"
						:total="totalCount"
					></el-pagination>
				</el-col>
				<el-col :span="6" style="text-align: end">
					<el-button
						class="add-new-button"
						plain
						type="primary"
						@click="addNewComputers"
						>Add New Computers</el-button
					>
				</el-col>
			</el-row>
		</template>

		<el-dialog
			title="Add A New Computer"
			:center="true"
			:visible.sync="dialogFormVisible"
		>
			<el-form
				:model="form"
				:rules="formRules"
				ref="form"
				label-position="left"
			>
				<el-form-item
					label="ssh configure"
					:label-width="formLabelWidth"
				>
					<el-popover
						placement="bottom"
						:title="$t(languagePath('rsa_pub_tip.desc'))"
						width="300"
					>
						<el-link
							type="primary"
							href="https://aiida.readthedocs.io/projects/aiida-core/en/latest/howto/ssh.html"
							target="_blank"
							>help: click here for details</el-link
						>
						<d2-highlight :code="rsa_pub" />
						<el-button
							slot="reference"
							@click="showRSAPub"
							type="primary"
							plain
							>{{
								$t(languagePath("rsa_pub_tip.title"))
							}}</el-button
						>
					</el-popover>
				</el-form-item>
				<el-form-item
					label="label"
					prop="label"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.label"
						autocomplete="off"
					></el-input>
				</el-form-item>
				<el-form-item
					label="description"
					prop="description"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.description"
						autocomplete="off"
					></el-input>
				</el-form-item>
				<el-form-item
					label="hostname"
					prop="hostname"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.hostname"
						autocomplete="off"
						placeholder="127.0.0.1"
					></el-input>
				</el-form-item>
				<el-form-item
					label="port"
					prop="port"
					:label-width="formLabelWidth"
				>
					<el-input v-model="form.port" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item
					label="remote username"
					prop="username"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.username"
						autocomplete="off"
					></el-input>
				</el-form-item>
				<el-form-item
					label="transport type"
					prop="transport_type"
					:label-width="formLabelWidth"
				>
					<el-select
						v-model="form.transport_type"
						placeholder="select transport type"
					>
						<el-option label="remote ssh" value="ssh"></el-option>
						<el-option
							label="local computer"
							value="local"
							disabled
						></el-option>
					</el-select>
				</el-form-item>
				<el-form-item
					label="scheduler type"
					prop="scheduler_type"
					:label-width="formLabelWidth"
				>
					<el-select
						v-model="form.scheduler_type"
						placeholder="select scheduler type"
					>
						<el-option label="Direct" value="direct"></el-option>
						<el-option label="PBSPro" value="pbspro"></el-option>
						<el-option label="SLURM" value="slurm"></el-option>
						<el-option
							label="Oracle Grid Engine"
							value="sge"
						></el-option>
						<el-option label="LSF" value="lsf" disabled></el-option>
						<el-option label="Torque" value="torque"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item
					label="workdir"
					prop="workdir"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.workdir"
						autocomplete="off"
						placeholder="make sure you have permission to access. (/home/user/workdir)"
					></el-input>
				</el-form-item>
				<el-form-item
					label="shebang"
					prop="shebang"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.shebang"
						autocomplete="off"
					></el-input>
				</el-form-item>
				<el-form-item
					label="mpirun command"
					prop="mpirun_command"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.mpirun_command"
						autocomplete="off"
					></el-input>
				</el-form-item>
				<el-form-item
					label="prepend text"
					prop="prepend_text"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.prepend_text"
						autocomplete="off"
						placeholder="command before"
					></el-input>
				</el-form-item>
				<el-form-item
					label="append text"
					prop="append_text"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model="form.append_text"
						autocomplete="off"
						placeholder="command after"
					></el-input>
				</el-form-item>
				<el-form-item
					label="default mpi procs per machine"
					prop="default_mpiprocs_per_machine"
					:label-width="formLabelWidth"
				>
					<el-input
						type="number"
						v-model.number="form.default_mpiprocs_per_machine"
						autocomplete="off"
					></el-input>
				</el-form-item>
				<el-form-item
					label="minimum job poll interval"
					prop="minimum_job_poll_interval"
					:label-width="formLabelWidth"
				>
					<el-input
						v-model.number="form.minimum_job_poll_interval"
						autocomplete="off"
					></el-input>
				</el-form-item>
				<el-form-item
					label="key policy"
					prop="key_policy"
					:label-width="formLabelWidth"
				>
					<el-select
						v-model="form.key_policy"
						placeholder="select key policy"
					>
						<el-option
							label="RejectPolicy"
							value="RejectPolicy"
						></el-option>
						<el-option
							label="WarningPolicy"
							value="WarningPolicy"
						></el-option>
						<el-option
							label="AutoAddPolicy"
							value="AutoAddPolicy"
						></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisible = false">Cancel</el-button>
				<el-button
					type="primary"
					@click="newComputersSubmit('form')"
					v-loading.fullscreen.lock="fullscreenLoading"
					>Submit</el-button
				>
			</span>
		</el-dialog>
		<el-dialog
			title="add a new code"
			:center="true"
			:visible.sync="newCodeDialogVisible"
		>
			<code-add
				:computer="selectedComputer"
				v-on:cancel="newCodeDialogVisible = false"
			></code-add>
		</el-dialog>
	</d2-container>
</template>

<script>
import * as clipboard from "clipboard-polyfill";
// import {
// 	openWebsocket,
// 	sendWebsocket,
// 	closeWebsocket
// } from "@/libs/websocket.js";
import util from "@/libs/util";
import CodeAdd from "../codes/code-add";
const apiPrefix = "/computers/";
const wsPrefix = "/computers/";
export default {
	name: "cluster-computers",
	components: {
		CodeAdd
	},
	data() {
		return {
			tableData: [
				{
					uuid: "9676960c-8547-4fad-8614-954e0760715c",
					label: "vasp",
					user: {
						uuid: "9676960c-8547-4fad-8614-954e0760715c",
						email: "user@eamil.com",
						username: "user"
					},
					attributes: {
						label: "computer.label",
						pk: "computer.pk",
						uuid: "computer.uuid",
						hostname: "computer.hostname",
						description: "computer.description",
						transport_type: "computer.transport_type",
						scheduler_type: "computer.scheduler_type",
						workdir: "computer.get_workdir()",
						shebang: "computer.get_shebang()",
						mpirun_command: "computer.get_mpirun_command()",
						prepend_text: "computer.get_prepend_text()",
						append_text: "computer.get_append_text()"
					}
				}
			],
			tableLoading: true,
			currentPage: 1,
			totalCount: 0,
			pageSize: 10,
			formLabelWidth: "200px",
			form: {
				label: "",
				username: "root",
				hostname: "localhost",
				description: "localhost computer only for test",
				transport_type: "ssh",
				scheduler_type: "direct",
				workdir: "",
				shebang: "#!/bin/bash",
				mpirun_command: "mpirun -np {tot_num_mpiprocs}",
				prepend_text: "",
				append_text: "",
				minimum_job_poll_interval: 10,
				default_mpiprocs_per_machine: 4,
				port: 22,
				key_policy: "AutoAddPolicy"
			},
			formRules: {
				label: [{ required: true, trigger: "blur" }],
				username: [{ required: true, trigger: "blur" }],
				hostname: [{ required: true, trigger: "blur" }],
				workdir: [{ required: true, trigger: "blur" }],
				shebang: [{ required: true, trigger: "blur" }],
				mpirun_command: [{ required: true, trigger: "blur" }],
				minimum_job_poll_interval: [
					{ required: true, type: "integer", min: 0, trigger: "blur" }
				],
				default_mpiprocs_per_machine: [
					{ required: true, type: "integer", min: 1, trigger: "blur" }
				],
				port: [{ required: true, trigger: "blur" }]
			},
			dialogFormVisible: false,
			rsa_pub: "",
			RSAPubVisible: false,
			fullscreenLoading: false,
			socket: null,
			testLogs: {},
			selectedComputer: "",
			newCodeDialogVisible: false
		};
	},
	methods: {
		handleTabSwitch(event, index, row) {
			if (event.label == "Test") {
				this.handleTest(index, row);
			}
		},
		languagePath(key) {
			return `page.${this.$route.name.split("-").join(".")}.${key}`;
		},
		getRSAPub() {
			this.$api.GetObj("/security/", "rsa_pub").then(resp => {
				this.rsa_pub = resp.data;
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
		addNewComputers() {
			this.dialogFormVisible = true;
		},
		newComputersSubmit(formName) {
			this.fullscreenLoading = true;
			this.$refs[formName].validate(valid => {
				if (valid) {
					let data = Object.assign({}, this.form);
					data.mpirun_command = data.mpirun_command.split(" ");
					data.user = util.cookies.get("uuid");
					this.$api.AddObj(apiPrefix, data).then(resp => {
						if (resp.code == 0) {
							this.$message.success("Upload Success!");
						}
						this.handleCurrentPageChange(this.currentPage);
						this.fullscreenLoading = false;
						this.dialogFormVisible = false;
					});
				} else {
					this.$notify.error("Invaid Input");
					this.fullscreenLoading = false;
					return false;
				}
			});
		},
		showRSAPub() {
			this.RSAPubVisible = true;
			clipboard.writeText(this.rsa_pub);
			this.$message.success("copied id_rsa.pub to clipboard");
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
		handleTest(index, row) {
			if (row.uuid == "") {
				return this.$message.error("UUID is None!");
			}
			if (!(row.uuid in this.testLogs)) {
				this.$confirm(
					"This will make a series of tests to check if connection is available",
					"Notice",
					{
						confirmButtonText: "confirm",
						cancelButtonText: "cancel",
						type: "info"
					}
				)
					.then(() => {
						this.tableLoading = true;
						this.$set(this.testLogs, row.uuid, []);
						this.currentTestComputer = row.uuid;
						// this.socket = openWebsocket(
						// 	`${wsPrefix}${row.uuid}/test/`,
						// 	this.onWSMessage,
						// 	this.onWSError
						// );
						this.$connect(
							util.getWebsocketUrl(`${wsPrefix}${row.uuid}/test/`)
						);
						this.socket = this.$socket;
						this.socket.onmessage = data => this.onWSMessage(data);
					})
					.catch(e => {
						this.$log.push({
							message: e.toString(),
							type: "danger"
						});
						console.error(e);
						this.$message({
							type: "info",
							message: "canceled"
						});
					});
			}
		},
		closeComputerTest() {
			// closeWebsocket();
			this.socket && this.socket.close();
			this.tableLoading = false;
		},
		onWSMessage(data) {
			data = JSON.parse(data.data);
			this.tableLoading = false;
			console.log(data);
			data.id = new Date().getTime();
			let logs = this.testLogs[this.currentTestComputer];
			logs.push(data);
			this.$set(this.testLogs, this.currentTestComputer, logs);
		},
		onWSError() {},
		handleNewCode(index, row) {
			if (row.uuid == "") {
				return this.$message.error("UUID is None!");
			}
			this.newCodeDialogVisible = true;
			this.selectedComputer = row.uuid;
		}
	},
	mounted() {
		this.pageRequest().then(resp => {
			let data = resp.data;
			this.tableData = data.results;
			this.totalCount = data.count;
			this.tableLoading = false;
		});
		this.getRSAPub();
	},
	beforeDestroy() {
		this.tableLoading = false;
		// closeWebsocket();
		this.socket && this.socket.close();
	}
};
</script>

<style lang="scss">
.cluster-computers-container {
	.data-table {
		.el-table__body-wrapper {
			@include scrollBar();
		}
	}
	.d2-highlight {
		border-radius: initial !important;
	}
	.log-list-enter-active,
	.list-leave-active {
		transition: all 1s;
	}
	.log-list-enter,
	.list-leave-to {
		opacity: 0;
		transform: translateY(30px);
	}
}
</style>
