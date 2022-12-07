<template>
	<d2-container type="full" class="periodic-table-page-container">
		<el-row :gutter="20">
			<el-col :xl="16" :lg="20">
				<PeriodicTable
					v-on:selectedElements="selectedElements"
					class="periodic-table-wrap"
				/>
			</el-col>
			<el-col :xl="8" lg="4">
				<el-form :model="queryForm">
					<el-form-item label="Elements">
						<el-input
							v-model="queryForm.elements"
							placeholder="Elements"
							readonly
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
						<el-button
							icon="el-icon-search"
							circle
							@click="querySubmit"
						></el-button>
					</el-form-item>
					<el-tooltip
						class="item"
						effect="dark"
						content="Click the blank to show more info"
						placement="top-start"
					>
						<el-button
							circle
							icon="el-icon-question"
							style="
												background: none;
												border: none;
												position: relative;
												top: -3px;
											"
						></el-button>
					</el-tooltip>
					<el-form-item label="Number of Atoms">
						<el-input
							type="number"
							v-model="queryForm.number_of_atoms"
							placeholder="Number of Atoms"
						>
						</el-input>
					</el-form-item>
					<el-form-item label="Volume">
						<el-col :span="11">
							<el-input
								type="number"
								placeholder="Min"
								v-model="queryForm.volume_min"
								style="width: 100%"
							></el-input>
						</el-col>
						<el-col class="line" :span="2">-</el-col>
						<el-col :span="11">
							<el-input
								type="number"
								placeholder="Max"
								v-model="queryForm.volume_max"
								style="width: 100%"
							></el-input>
						</el-col>
					</el-form-item>
					<el-form-item label="Get All">
						<el-input
							v-model="queryForm.get_all"
							placeholder="get all structures"
						>
						</el-input>
					</el-form-item>
				</el-form>
			</el-col>
		</el-row>
		<!-- <el-row class="periodic-container flex-center-col">
			<PeriodicTable
				v-on:selectedElements="selectedElements"
				class="periodic-table-wrap"
			/>
		</el-row> -->
		<!-- <template slot="footer">
			<div class="query-container">
				<el-form
					:inline="true"
					:model="queryForm"
					class="query-form-inline"
				>
					<el-collapse class="footer-collapse">
						<el-collapse-item>
							<template slot="title">
								<div v-on:click.stop>
									<el-form-item label="Elements">
										<el-input
											v-model="queryForm.elements"
											placeholder="Elements"
											readonly
										>
										</el-input>
									</el-form-item>
									<el-form-item label="Mode">
										<el-select
											v-model="queryForm.mode"
											placeholder="ALL/ANY"
										>
											<el-option
												label="ALL"
												value="ALL"
											></el-option>
											<el-option
												label="ANY"
												value="ANY"
											></el-option>
										</el-select>
									</el-form-item>

									<el-form-item>
										<el-button
											icon="el-icon-search"
											circle
											@click="querySubmit"
										></el-button>
									</el-form-item>
									<el-tooltip
										class="item"
										effect="dark"
										content="Click the blank to show more info"
										placement="top-start"
									>
										<el-button
											circle
											icon="el-icon-question"
											style="
												background: none;
												border: none;
												position: relative;
												top: -3px;
											"
										></el-button>
									</el-tooltip>
								</div>
							</template>
							<div class="collapse-content">
								<el-form-item label="Number of Atoms">
									<el-input
										type="number"
										v-model="queryForm.number_of_atoms"
										placeholder="Number of Atoms"
									>
									</el-input>
								</el-form-item>
								<el-form-item label="Volume">
									<el-col :span="11">
										<el-input
											type="number"
											placeholder="Min"
											v-model="queryForm.volume_min"
											style="width: 100%"
										></el-input>
									</el-col>
									<el-col class="line" :span="2">-</el-col>
									<el-col :span="11">
										<el-input
											type="number"
											placeholder="Max"
											v-model="queryForm.volume_max"
											style="width: 100%"
										></el-input>
									</el-col>
								</el-form-item>
								<el-form-item label="Get All">
									<el-input
										v-model="queryForm.get_all"
										placeholder="get all structures"
									>
									</el-input>
								</el-form-item>
							</div>
						</el-collapse-item>
					</el-collapse>
				</el-form>
			</div>
		</template> -->
	</d2-container>
</template>

<script>
import PeriodicTable from "./components/PeriodicTable";
export default {
	name: "structures-index",
	components: {
		PeriodicTable
	},
	data() {
		return {
			queryForm: {
				elements: "",
				mode: "ALL",
				number_of_atoms: "",
				volume_max: "",
				volume_min: "",
				get_all: ""
			},
			elements: []
		};
	},
	methods: {
		selectedElements(elements) {
			this.elements = elements;
			this.queryForm.elements = elements.join("-");
		},
		querySubmit() {
			let queryForm = this.queryForm;
			if (queryForm.elements == "") {
				return this.$message.error("Elements cannot be null!");
			}
			let query = {};
			query.elements = queryForm.elements;
			query.mode = queryForm.mode;
			queryForm.number_of_atoms != "" &&
				(query.number_of_atoms = queryForm.number_of_atoms);
			queryForm.volume_max != "" &&
				(query.volume_max = queryForm.volume_max);
			queryForm.volume_min != "" &&
				(query.volume_min = queryForm.volume_min);
			queryForm.get_all != "" && (query.get_all = queryForm.get_all);
			this.$router.push({
				name: "data-structures",
				params: {
					queryForm: query
				},
				query: {
					...query
				}
			});
		}
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
		// position: absolute;
		// left: 0;
		// right: 0;
		// top: 0;
		// bottom: 0;
		// padding: 4vh 1vw;
		transform: scale(0.95);
		border-radius: 6px;
		// background: rgba(255, 255, 255, 0.8) !important;
		// overflow: hidden;
		@extend %unable-select;
	}
	.d2-container-full__footer {
		background: rgba(255, 255, 255, 0.8) !important;
		.el-collapse {
			border: none;
		}
		.el-collapse-item__header {
			background: none;
			justify-content: center;
			border: none;
			.el-form-item {
				margin: 0 20px;
				height: 40px;
				line-height: 40px;
			}
			.footer-collapse {
				background: none;
			}
			.el-collapse-item__arrow.el-icon-arrow-right {
				display: none;
			}
		}
		.el-collapse-item__wrap {
			background: none;
			border: none;
			.el-collapse-item__content {
				padding: 20px 0 0 0;
				.collapse-content {
					@extend %flex-row;
					justify-content: center;
					align-items: center;
					text-align: center;
					.el-form-item {
						margin: 0 20px;
						height: 40px;
						line-height: 40px;
					}
				}
			}
		}
	}
}
</style>
