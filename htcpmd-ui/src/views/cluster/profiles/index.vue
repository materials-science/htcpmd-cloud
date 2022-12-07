<template>
	<d2-container type="full" class="cluster-profiles-container">
		<el-table
			v-loading="tableLoading"
			:data="tableData"
			:default-sort="{ prop: 'uuid', order: 'descending' }"
			class="data-table"
			height="100%"
		>
			<el-table-column type="expand">
				<template slot-scope="props">
					<el-tabs type="border-card">
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
					</el-tabs>
				</template>
			</el-table-column>
			<el-table-column
				prop="uuid"
				label="uuid"
				sortable
				align="center"
			></el-table-column>
			<el-table-column prop="name" label="name" align="center">
			</el-table-column>
			<el-table-column prop="user.email" label="user" align="center">
			</el-table-column>
			<el-table-column label="created time">
				<template slot-scope="props">
					<el-date-picker
						v-model="props.row.created_time"
						type="datetime"
						placeholder="created time"
						readonly
						class="structure-date-picker"
					>
					</el-date-picker>
				</template>
			</el-table-column>
		</el-table>
		<template slot="footer">
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
			<!-- <el-button
				class="add-button"
				plain
				type="primary"
				@click="addNewTasks"
				>Add New Profiles</el-button
			> -->
		</template>
	</d2-container>
</template>

<script>
const apiPrefix = "/profiles/";
export default {
	data() {
		return {
			tableData: [
				{
					uuid: "474f05e0-47fe-4af6-b977-8b9f9cc74823",
					name: "aiida",
					user: {
						uuid: "474f05e0-47fe-4af6-b977-8b9f9cc74823",
						email: "user@eamil.com",
						username: "user"
					},
					created_time: "",
					last_modified_time: "",
					attributes: {
						database_engine: "postgresql_psycopg2",
						database_backend: "sqlalchemy",
						database_name: "aiida",
						database_port: 5432,
						database_hostname: "localhost",
						database_username: "aiida",
						database_password: "aiida",
						broker_protocol: "amqp",
						broker_username: "guest",
						broker_password: "guest",
						broker_host: "localhost",
						broker_port: 5672,
						broker_virtual_host: "",
						repository_uri: "file://~/.aiida/repository/aiida"
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

<style lang="scss">
// @import "@/assets/style/data-list.scss";
</style>
