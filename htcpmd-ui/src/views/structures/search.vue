<template>
	<d2-container class="structures-search-container">
		<template slot="header">
			<el-row>
				<el-button
					class="add-new-button"
					plain
					type="primary"
					@click="addNewStructures"
					>Add New Structures</el-button
				>
				<el-button
					class="add-new-button"
					plain
					type="primary"
					@click="addNewTasks"
					>Add New Tasks</el-button
				>
			</el-row>
		</template>
		<el-table
			v-loading="tableLoading"
			:data="tableData"
			style="width: 100%"
			:default-sort="{ prop: 'formula', order: 'ascending' }"
			@row-click="viewDetail"
			@selection-change="handleSelectionChange"
			class="structure-table"
		>
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column type="expand">
				<template slot-scope="props">
					<!-- <el-form
						label-position="left"
						inline
						class="structure-table-expand"
					> -->
					<el-row class="structure-table-expand">
						<el-row :gutter="20" type="flex" align="middle">
							<el-col :span="DetailSpan / 3">
								<div class="label">
									<el-tag effect="plain"> UUID </el-tag>
								</div>
							</el-col>
							<el-col :span="(DetailSpan * 2) / 3">
								<div class="content">
									<el-tag>{{ props.row.uuid }}</el-tag>
								</div>
							</el-col>
						</el-row>
						<el-row :gutter="20" type="flex" align="middle">
							<el-col :span="DetailSpan / 3">
								<div class="label">
									<el-tag effect="plain">
										created time
									</el-tag>
								</div>
							</el-col>
							<el-col :span="(DetailSpan * 2) / 3">
								<div class="content">
									<el-date-picker
										v-model="props.row.created_time"
										type="datetime"
										placeholder="created time"
										readonly
										class="structure-date-picker"
									>
									</el-date-picker>
								</div>
							</el-col>
						</el-row>
						<el-row :gutter="20" type="flex" align="middle">
							<el-col :span="DetailSpan / 3">
								<div class="label">
									<el-tag effect="plain">
										last modified
									</el-tag>
								</div>
							</el-col>
							<el-col :span="(DetailSpan * 2) / 3">
								<div class="content">
									<el-date-picker
										v-model="props.row.last_modified_time"
										type="datetime"
										placeholder="last modified"
										readonly
										class="structure-date-picker"
									>
									</el-date-picker>
								</div>
							</el-col>
						</el-row>
						<el-row :gutter="20" type="flex" align="middle">
							<el-col :span="DetailSpan / 3">
								<div class="label">
									<el-tag effect="plain">
										center of mass
									</el-tag>
								</div>
							</el-col>
							<el-col :span="(DetailSpan * 2) / 3">
								<div class="content">
									<number-table
										:span="(DetailSpan * 2) / 3"
										:data="
											props.row.attributes.center_of_mass
										"
										:precision="2"
										disabled
									></number-table>
								</div>
							</el-col>
						</el-row>
						<el-row :gutter="20" type="flex" align="middle">
							<el-col :span="DetailSpan / 3">
								<div class="label">
									<el-tag effect="plain">
										cell
									</el-tag>
								</div>
							</el-col>
							<el-col :span="(DetailSpan * 2) / 3">
								<div class="content">
									<number-table
										:span="(DetailSpan * 2) / 3"
										:data="props.row.attributes.cell"
										:precision="6"
										disabled
									></number-table>
								</div>
							</el-col>
						</el-row>
					</el-row>
				</template>
			</el-table-column>
			<!-- <el-table-column prop="uuid" label="uuid" sortable align="center"> -->
			<!-- </el-table-column> -->
			<el-table-column label="formula" align="center">
				<template slot-scope="props">
					<span v-html="props.row.attributes.formula"></span>
				</template>
			</el-table-column>
			<el-table-column
				label="volume"
				prop="attributes.volume"
				sortable
				align="center"
			>
				<template slot-scope="props">
					<el-input-number
						v-model="props.row.attributes.volume"
						label="volume"
						:precision="4"
						:controls="false"
						disabled
						class="volume-input"
					></el-input-number>
				</template>
			</el-table-column>
			<el-table-column
				prop="attributes.spacegroup"
				label="spacegroup"
				sortable
				align="center"
			>
			</el-table-column>
			<el-table-column
				prop="attributes.number_of_atoms"
				label="number of atoms"
				sortable
				align="center"
			>
			</el-table-column>
			<el-table-column label="tags" align="center">
				<template slot-scope="props">
					<el-tag
						v-for="(tag, index) in props.row.tags.slice(0, 2)"
						effect="plain"
						:key="index"
						@click.stop="
							showCurrentRowTags(props.$index, props.row)
						"
						class="d2-mr-10"
						>{{ tag.name }}</el-tag
					>
					<el-button
						v-if="props.row.tags.length > 2"
						size="small"
						@click.stop="
							showCurrentRowTags(props.$index, props.row)
						"
						>...</el-button
					>
				</template>
			</el-table-column>
			<el-table-column label="operations" align="center" min-width="150">
				<template slot-scope="scope">
					<el-button
						size="mini"
						type="danger"
						plain
						@click.stop="handleDelete(scope.$index, scope.row)"
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
		<el-dialog
			title="Tags"
			:visible.sync="tags_dialog_visible"
			destroy-on-close
		>
			<el-table :data="current_showed_tags">
				<el-table-column
					property="name"
					label="name"
					width="150"
				></el-table-column>
				<el-table-column label="created time" width="250">
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
					property="description"
					label="description"
				></el-table-column>
			</el-table>
		</el-dialog>
	</d2-container>
</template>

<script>
import * as api from "./api";
import setting from "@/setting.js";
import NumberTable from "@/components/number-table";
const apiPrefix = "/structures/";
export default {
	name: "structures-search",
	components: {
		NumberTable
	},
	data() {
		return {
			tableData: [
				{
					uuid: "1325121640423690200",
					attributes: {
						formula: "C<sub>4</sub>Si<sub>4</sub>",
						number_of_atoms: 8,
						spacegroup: 216,
						volume: 84.0024
					},
					tags: []
				},
				{
					uuid: "1325121640423690200",
					attributes: {
						formula: "C<sub>4</sub>Si<sub>4</sub>",
						number_of_atoms: 8,
						spacegroup: 216,
						volume: 84.0024
					},
					tags: []
				},
				{
					uuid: "1325121640423690200",
					attributes: {
						formula: "C<sub>4</sub>Si<sub>4</sub>",
						number_of_atoms: 8,
						spacegroup: 216,
						volume: 84.0024
					},
					tags: []
				}
			],
			tableLoading: true,
			currentPage: 1,
			totalCount: 0,
			pageSize: 10,
			queryForm: {},
			multipleSelection: [],
			current_showed_tags: [],
			tags_dialog_visible: false,
			DetailSpan: 24
		};
	},
	methods: {
		pageRequest(
			page = this.currentPage,
			size = this.pageSize,
			options = this.queryForm
		) {
			return api.GetList(Object.assign({ page, size }, options));
		},
		requestData() {
			if (
				(!this.$route.query.elements && !this.$route.query.tag) ||
				!this.$route.query.mode
			) {
				this.$message.warning(
					"No searching criteria. Ready to close this page!"
				);
				setTimeout(() => {
					this.$store.dispatch("d2admin/page/close", {
						tagName: this.$route.fullPath
					});
				}, 1000);
			}
			this.tableLoading = true;
			this.queryForm = this.$route.query;
			this.pageRequest().then(resp => {
				let data = resp.data;
				this.tableData = data.results;
				this.totalCount = data.count;
				this.tableLoading = false;
			});
			this.$store.dispatch("d2admin/page/update", {
				tagName: this.$route.fullPath,
				title: `Search-${this.$route.query.elements ||
					this.$route.query.tag}`
			});
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
		handleSelectionChange(val) {
			this.multipleSelection = val;
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
		viewDetail(row, column, event) {
			let uuid = row.uuid;
			if (uuid == "") {
				return this.$message.error("UUID is None!");
			}
			this.$router.push({
				path: `/structures/detail/${uuid}`
			});
		},
		showCurrentRowTags(index, row) {
			this.current_showed_tags = row.tags;
			this.tags_dialog_visible = true;
		},
		async addNewTasks() {
			if (this.multipleSelection.length == 0) {
				return this.$message.error("You must choose one at least.");
			} else if (
				this.multipleSelection.length >
				setting.calcjobs.max_structures_once
			) {
				return this.$message.error(
					`Only support ${setting.calcjobs.max_structures_once} structures once currently.`
				);
			}
			const db = await this.$store.dispatch("d2admin/db/database", {
				user: true
			});
			db.set("selectedStructures", this.multipleSelection).write();

			this.$router.push({
				path: `/calctasks/submit`
			});
		},
		addNewStructures() {
			this.$router.push({
				path: `/data/upload/structures`
			});
		}
	},
	watch: {
		"$route.name"(name) {
			if (
				name == "structures-search" &&
				this.$route.params.query_updated
			) {
				this.$route.params.query_updated = false;
				this.requestData();
			}
		}
	},
	mounted() {
		this.requestData();
	}
};
</script>

<style lang="scss" scoped>
@import "@/assets/style/public.scss";
.add-tasks-button {
	position: absolute;
	right: 16px;
	bottom: 16px;
}
.structure-date-picker input {
	border: none !important;
}
.structure-table-expand {
	font-size: 0;
}
.structure-table-expand label {
	width: 20%;
	color: #99a9bf;
	font-size: 16px;
	font-weight: bold;
}
.structure-table-expand {
	> .el-row {
		margin: 8px 0;
		width: 100%;
	}
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
<style lang="scss">
.structures-list-container {
	.structure-table {
		.el-table__body-wrapper {
			@include scrollBar();
		}
	}
	.volume-input input {
		background: none !important;
		border: none !important;
		color: inherit !important;
		cursor: default !important;
	}
}
</style>
