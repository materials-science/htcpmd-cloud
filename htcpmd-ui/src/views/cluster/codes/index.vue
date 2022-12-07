<template>
	<d2-container type="full" class="cluster-codes-container">
		<el-table
			v-loading="tableLoading"
			:data="tableData"
			style="width: 100%"
			:default-sort="{ prop: 'label', order: 'descending' }"
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
								<el-form-item label="uuid">
									<el-input
										disabled
										:value="props.row.uuid"
									></el-input>
								</el-form-item>
								<el-form-item
									v-for="(val, key) in props.row.attributes"
									:key="key"
									:label="key"
								>
									<el-input disabled :value="val"></el-input>
								</el-form-item>
							</el-form>
						</el-tab-pane>
						<el-tab-pane label="Computer">
							<el-form
								label-position="left"
								class="data-table-expand--wrap"
							>
								<el-form-item
									v-for="(val, key) in props.row.computer"
									:key="key"
									:label="key"
								>
									<el-input disabled :value="val"></el-input>
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
					</el-tabs>
				</template>
			</el-table-column>
			<el-table-column prop="label" sortable label="label" align="center">
			</el-table-column>
			<el-table-column
				prop="computer.label"
				label="computer"
				align="center"
			></el-table-column>
			<el-table-column
				prop="attributes.input_plugin"
				label="input plugin"
				align="center"
			></el-table-column>
			<el-table-column prop="version" label="version" align="center">
			</el-table-column>
			<el-table-column
				prop="user.email"
				label="user"
				align="center"
			></el-table-column>
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
			<el-table-column label="operations" align="center" min-width="150">
				<template slot-scope="scope">
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
					<router-link to="/cluster/computers">
						<el-button class="add-new-button" plain type="primary"
							>Add New Codes</el-button
						></router-link
					>
				</el-col>
			</el-row>
		</template>
	</d2-container>
</template>

<script>
const apiPrefix = "/codes/";
export default {
	name: "cluster-codes",
	data() {
		return {
			tableData: [
				{
					uuid: "9676960c-8547-4fad-8614-954e0760715c",
					label: "aiida",
					computer: {
						uuid: "9676960c-8547-4fad-8614-954e0760715c",
						label: "test",
						email: "user@eamil.com",
						username: "user",
						hostname: "172.31.220.82"
					},
					user: {
						uuid: "9676960c-8547-4fad-8614-954e0760715c",
						email: "user@eamil.com",
						username: "user"
					},
					version: "1.0",
					created_time: "",
					last_modified_time: "",
					attributes: {
						label: "",
						pk: "",
						uuid: "",
						description: "",
						remote_exec_path: "/bin/bash",
						input_plugin: "",
						prepend_text: "",
						append_text: ""
					}
				}
			],
			tableLoading: true,
			currentPage: 1,
			totalCount: 0,
			pageSize: 10
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
				return this.$message.error("uuid is None!");
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
		}
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
