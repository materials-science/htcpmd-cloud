<template>
	<d2-container type="full" class="periodic-table-page-container">
		<el-row class="periodic-container">
			<PeriodicTable
				v-on:selectedElements="selectedElements"
				:updated="updated_elements"
				class="periodic-table-wrap"
			/>
		</el-row>
		<el-drawer
			title="More options"
			:visible.sync="show_more_options"
			direction="rtl"
		>
			<el-form
				:model="queryForm"
				class="query-container d2-pl-16 d2-pr-16"
				label-position="top"
			>
				<el-form-item label="Elements">
					<h5 slot="label">
						<span>Elements</span>
						<el-tooltip
							class="d2-pl-16"
							content="elements that your structures contain."
							placement="bottom-start"
						>
							<el-button circle class="no-bg no-border">
								<d2-icon name="question-circle-o"></d2-icon>
							</el-button>
						</el-tooltip>
					</h5>
					<el-input
						v-model="queryForm.elements"
						placeholder="Elements"
						@input="updateElements"
					>
					</el-input>
				</el-form-item>
				<el-form-item label="Mode">
					<h5 slot="label">
						<span>Mode</span>
						<el-tooltip
							class="d2-pl-16"
							content="search criterion that your structures must contain all or any of these elements."
							placement="bottom-start"
						>
							<el-button circle class="no-bg no-border">
								<d2-icon name="question-circle-o"></d2-icon>
							</el-button>
						</el-tooltip>
					</h5>
					<el-select v-model="queryForm.mode" placeholder="ALL/ANY">
						<el-option label="ALL" value="ALL"></el-option>
						<el-option label="ANY" value="ANY"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="Tag">
					<h5 slot="label">
						<span>Tag</span>
						<el-tooltip
							class="d2-pl-16"
							content="search elements by tag name that user has tagged."
							placement="bottom-start"
						>
							<el-button circle class="no-bg no-border">
								<d2-icon name="question-circle-o"></d2-icon>
							</el-button>
						</el-tooltip>
					</h5>
					<el-input v-model="queryForm.tag" placeholder="tag name">
					</el-input>
				</el-form-item>
				<el-form-item label="Number of Atoms">
					<h5 slot="label">
						<span>Number of Atoms</span>
						<el-tooltip
							class="d2-pl-16"
							content="Number of Atoms."
							placement="bottom-start"
						>
							<el-button circle class="no-bg no-border">
								<d2-icon name="question-circle-o"></d2-icon>
							</el-button>
						</el-tooltip>
					</h5>
					<el-input
						type="number"
						v-model="queryForm.number_of_atoms"
						placeholder="Number of Atoms"
					>
					</el-input>
				</el-form-item>
				<el-form-item label="Volume">
					<h5 slot="label">
						<span>Volume</span>
						<el-tooltip
							class="d2-pl-16"
							content="the volume range of your structures."
							placement="bottom-start"
						>
							<el-button circle class="no-bg no-border">
								<d2-icon name="question-circle-o"></d2-icon>
							</el-button>
						</el-tooltip>
					</h5>
					<el-row :gutter="20">
						<el-col :span="12">
							<el-input
								type="number"
								placeholder="Min"
								v-model="queryForm.volume_min"
							></el-input>
						</el-col>
						<el-col :span="12">
							<el-input
								type="number"
								placeholder="Max"
								v-model="queryForm.volume_max"
							></el-input>
						</el-col>
					</el-row>
				</el-form-item>
				<el-form-item label="Get All">
					<h5 slot="label">
						<span>Get All</span>
						<el-tooltip
							class="d2-pl-16"
							content="This is a debug option for developers. Please input the secret code."
							placement="bottom-start"
						>
							<el-button circle class="no-bg no-border">
								<d2-icon name="question-circle-o"></d2-icon>
							</el-button>
						</el-tooltip>
					</h5>
					<el-input
						v-model="queryForm.get_all"
						placeholder="get all structures"
					>
					</el-input>
				</el-form-item>
				<el-form-item>
					<el-button icon="el-icon-search" @click="querySubmit"
						>Search</el-button
					>
				</el-form-item>
			</el-form>
		</el-drawer>
		<template slot="header">
			<el-row class="query-container">
				<el-form
					:inline="true"
					:model="queryForm"
					class="query-form-inline d2-text-center"
				>
					<el-form-item label="Elements">
						<el-input
							v-model="queryForm.elements"
							placeholder="Elements"
							@input="updateElements"
						>
						</el-input>
					</el-form-item>
					<el-form-item label="Mode">
						<el-select
							v-model="queryForm.mode"
							placeholder="ALL/ANY"
						>
							<el-option label="ALL" value="ALL"></el-option>
							<el-option label="ANY" value="ANY"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button @click="show_more_options = true"
							>More Options</el-button
						>
					</el-form-item>
					<el-form-item>
						<el-button
							icon="el-icon-search"
							circle
							@click="querySubmit"
						></el-button>
					</el-form-item>
					<!-- <el-form-item>
						<el-tooltip
							class="item"
							effect="dark"
							content="Click the search icon to search or click the more-options button to show more options."
							placement="top-start"
						>
							<d2-icon name="question-circle-o"></d2-icon>
						</el-tooltip>
					</el-form-item> -->
				</el-form>
			</el-row>
		</template>
	</d2-container>
</template>

<script>
import PeriodicTable from "./components/PeriodicTable";
let _query_updated = false;
export default {
	name: "structures-index",
	components: {
		PeriodicTable
	},
	data() {
		return {
			show_more_options: false,
			queryForm: {
				elements: "",
				mode: "ALL",
				number_of_atoms: "",
				volume_max: "",
				volume_min: "",
				tag: "",
				get_all: ""
			},
			elements: [],
			updated_elements: ""
		};
	},
	methods: {
		selectedElements(elements) {
			this.elements = elements;
			this.queryForm.elements = elements.join("-");
		},
		updateElements() {
			this.queryForm.elements = this.queryForm.elements.replace(
				/\s/g,
				""
			);
			this.updated_elements = this.queryForm.elements;
		},
		querySubmit() {
			let queryForm = this.queryForm;
			let not_none_check = ["elements", "tag"];

			if (
				not_none_check.every(key => {
					return queryForm[key] === "";
				})
			) {
				return this.$message.error(
					`${not_none_check.join(" or ")} cannot be null!`
				);
			}
			let query = {};
			let not_none_assignment = [
				"number_of_atoms",
				"volume_max",
				"volume_min",
				"get_all",
				"tag"
			];
			query.elements = queryForm.elements;
			query.mode = queryForm.mode;
			not_none_assignment.forEach(key => {
				queryForm[key] != "" && (query[key] = queryForm[key]);
			});

			let query_updated = _query_updated;
			_query_updated && (_query_updated = false);
			this.$router.push({
				name: "structures-search",
				params: {
					queryForm: query,
					query_updated: query_updated
				},
				query: {
					...query
				}
			});
		}
	},
	beforeUpdate() {
		_query_updated = true;
	}
};
</script>

<style lang="scss">
.periodic-table-page-container {
	@import "@/assets/style/variable.scss";
	@import "@/assets/style/public.scss";
	.periodic-container {
		@include scrollBar();
		height: 100%;
		overflow-y: auto;
	}
	.periodic-table-wrap {
		@extend %unable-select;
		margin-top: 4rem;
		.periodictable {
			margin: 0 auto;
		}
	}
	.d2-container-full__header {
		background: rgba(255, 255, 255, 0.8) !important;
		.query-form-inline {
			.el-form-item {
				margin-bottom: initial;
			}
		}
	}
}
</style>
