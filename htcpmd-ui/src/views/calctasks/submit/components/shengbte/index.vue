<template>
	<d2-container class="calctasks-submit-shengbte-container">
		<el-row type="flex" justify="center">
			<el-col :xl="16"><h1>ShengBTE</h1></el-col>
		</el-row>
		<el-row type="flex" justify="center">
			<el-col :xl="16">
				<el-divider content-position="center"
					><el-tag effect="plain"
						><h2>ShengBTE Task Settings</h2></el-tag
					></el-divider
				>
			</el-col>
		</el-row>
		<div class="form-box">
			<!-- CalcJob Label -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>CalcTask Label</h3></el-tag></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set a label for your task</el-divider
						>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-input v-model="label"></el-input>
					</el-col>
				</el-row>
			</el-row>
			<!-- CalcJob Type -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>CalcTask Type</h3></el-tag></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left">
							Select calculations or workchains model you
							want</el-divider
						>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-select
							v-model="calctasks_type_selected_id"
							placeholder="Choose A Type"
							filterable
							autocomplete
							default-first-option
							:loading="load_calctasks_types"
							@change="calctasksTypeChange"
						>
							<el-option
								v-for="item in calctasks_types"
								:key="item.value"
								:label="item.label"
								:value="item.value"
							>
							</el-option>
						</el-select>
					</el-col>
				</el-row>
			</el-row>
			<!-- Computer -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>Computer</h3></el-tag>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set a computer where your calculations submit
							to</el-divider
						>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-select
							v-model="computers_selected"
							placeholder="Choose A Computer"
							filterable
							default-first-option
							remote
							:remote-method="getComputersList"
							:loading="loading_computers_list"
						>
							<el-option
								v-for="item in computers_list"
								:key="item.uuid"
								:label="`${item.label} - (${item.uuid})`"
								:value="item.uuid"
							>
							</el-option>
						</el-select>
					</el-col>
				</el-row>
			</el-row>
			<!-- code -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>Code</h3></el-tag></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set a code for your calculations
						</el-divider>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-select
							v-model="codes_selected"
							placeholder="Choose A Code"
							filterable
							default-first-option
							remote
							:remote-method="getCodesList"
							:loading="loading_codes_list"
						>
							<el-option
								v-for="item in codes_list"
								:key="item.uuid"
								:label="`${item.label} - (${item.uuid})`"
								:value="item.uuid"
							>
							</el-option>
						</el-select>
					</el-col>
				</el-row>
			</el-row>
			<!-- CalcJob Description -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>CalcTask Description</h3></el-tag></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Description of your task</el-divider
						>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-input
							type="textarea"
							:autosize="{ minRows: 2 }"
							placeholder=""
							v-model="calctasks_description"
							show-word-limit
						>
						</el-input>
					</el-col>
				</el-row>
			</el-row>
			<!-- Structures -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>Structures</h3></el-tag></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set Structures for your calculations</el-divider
						>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-table
							:data="structures"
							v-loading="loading_structures"
							style="width: 100%"
						>
							<el-table-column
								prop="uuid"
								label="uuid"
								align="center"
							>
							</el-table-column>
							<el-table-column label="formula" align="center">
								<template slot-scope="props">
									<span
										v-html="props.row.attributes.formula"
									></span>
								</template>
							</el-table-column>
							<el-table-column align="right">
								<template slot="header" slot-scope="scope">
									<el-row
										type="flex"
										justify="space-around"
										style="align-items: center"
									>
										<el-col :span="20">
											<el-input
												v-model="new_structure_id"
												size="mini"
												placeholder="new structure uuid"
											/>
										</el-col>
										<el-col :span="4">
											<el-button
												icon="el-icon-plus"
												circle
												@click.native.prevent="
													addNewStructure
												"
												size="small"
											>
											</el-button
										></el-col>
									</el-row>
								</template>
								<template slot-scope="scope">
									<el-popover
										placement="top-start"
										title="help"
										width="200"
										trigger="hover"
										content="Details of this structure"
										style="margin-right: 8px"
									>
										<el-button
											slot="reference"
											@click.native.prevent="
												viewDetail(scope.row)
											"
											icon="el-icon-view"
											size="small"
										></el-button>
									</el-popover>
									<el-popover
										placement="top-start"
										title="help"
										width="200"
										trigger="hover"
										content="Remove this structure"
									>
										<el-button
											slot="reference"
											@click.native.prevent="
												deleteRow(
													scope.$index,
													structures
												)
											"
											icon="el-icon-circle-close"
											size="small"
										></el-button>
									</el-popover>
								</template>
							</el-table-column>
						</el-table>
					</el-col>
				</el-row>
			</el-row>
			<!-- process per machine -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag
							><h3>num_mpiprocs_per_machine</h3></el-tag
						></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set number of mpi process per machine</el-divider
						>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-input-number
							v-model="num_mpiprocs_per_machine"
							:min="1"
						></el-input-number>
					</el-col>
				</el-row>
			</el-row>
		</div>
		<transition name="el-fade-in">
			<el-row
				v-show="calctasks_type_selected"
				type="flex"
				justify="center"
			>
				<el-col :xl="16">
					<el-divider content-position="center">
						<el-divider content-position="center"
							><el-tag effect="plain"
								><h2>
									{{ calctasks_type_selected }} Parameters
								</h2></el-tag
							></el-divider
						>
					</el-divider>
				</el-col>
			</el-row>
		</transition>
		<!-- @todo[1] -->
		<component
			:is="calctasks_type_selected"
			:calctasks_settings="calctasks_settings"
		></component>
	</d2-container>
</template>

<script>
/*******************************************************************************
 * TODO: [1]New approche to support mutiple calculations.
 ******************************************************************************/
import ShengBTECalculation from "./components/ShengBTECalculation";
import ShengBTEWorkChain from "./components/ShengBTEWorkChain";
import util from "@/libs/util";
const apiPrefix = "/calctasks/";
export default {
	name: "calctasks-submit-shengbte",
	components: { ShengBTECalculation, ShengBTEWorkChain },
	data() {
		return {
			label: "",
			calctasks_type_selected_id: null,
			calctasks_type_selected: null,
			calctasks_types: [],
			load_calctasks_types: true,
			calctasks_description: "",
			structures: [],
			loading_structures: true,
			new_structure_id: "",
			// computers
			loading_computers_list: false,
			computers_list: [],
			computers_selected: "",
			// codes
			loading_codes_list: false,
			codes_list: [],
			codes_selected: "",
			num_mpiprocs_per_machine: 1
		};
	},
	computed: {
		calctasks_settings() {
			return {
				label: this.label,
				calctasks_type: this.calctasks_type_selected_id,
				calctasks_description: this.calctasks_description,
				computer: this.computers_selected,
				code: this.codes_selected,
				num_mpiprocs_per_machine: this.num_mpiprocs_per_machine,
				structures: this.structures,
				user: util.cookies.get("uuid")
			};
		}
	},
	methods: {
		deleteRow(index, rows) {
			rows.splice(index, 1);
		},
		viewDetail(row, column, event) {
			let uuid = row.uuid;
			if (uuid == "") {
				this.$message.error("UUID is None!");
			}
			this.$router.push({
				path: `structures/detail/${uuid}`
			});
		},
		async loadStructures() {
			const db = await this.$store.dispatch("d2admin/db/database", {
				user: true
			});
			// TODO: Remove selected structures stored in local database
			this.structures = db.get("selectedStructures").value() || [];
			console.log(this.structures);
			// @todo[1]
			if (this.structures.length > 1) {
				this.$message.info(
					"Only support for single calculation of ShengBTE Now. It will use the first structure."
				);
				this.structures = this.structures[0];
			}
			this.loading_structures = false;
		},
		addNewStructure() {
			if (this.new_structure_id == "") {
				return this.$message.error(
					"Please input a valid structure uuid."
				);
			}
			// @todo[1]
			if (this.structures.length > 0) {
				return this.$message.info(
					"Only support for single calculation of ShengBTE Now."
				);
			}
			this.loading_structures = true;
			this.$api
				.GetObj("/structures/", this.new_structure_id)
				.then(resp => {
					this.loading_structures = false;
					try {
						if (resp.code == 0) {
							this.structures.push(resp.data);
							console.log(this.structures);
							this.new_structure_id = "";
							return this.$message.success(
								"Added a new structure."
							);
						}
						return this.$message.error("Structure does not exist.");
					} catch {
						return this.$message.error(
							"Whoops! Something was wrong."
						);
					}
				});
		},
		calctasksTypeChange(val) {
			const type = this.calctasks_types.find(item => {
				return item.value == val;
			});
			this.calctasks_type_selected = type.label;
		},
		getComputersList(query) {
			if (query == "") {
				return (this.computers_list = []);
			}
			this.loading_computers_list = true;
			this.$api
				.GetList("/computers/", { label: query, uuid: query })
				.then(resp => {
					if (resp.code == 0) {
						this.computers_list = resp.data.results;
					} else {
						this.$message.error("Retrivied failed! Please retry.");
					}
					this.loading_computers_list = false;
				});
		},
		getCodesList(query) {
			if (query == "") {
				return (this.codes_list = []);
			}
			if (this.computers_selected == "") {
				return this.$message.info("Please select a computer first!");
			}
			this.loading_codes_list = true;
			this.$api
				.GetList("/codes/", {
					label: query,
					uuid: query,
					computer: this.computers_selected
				})
				.then(resp => {
					if (resp.code == 0) {
						this.codes_list = resp.data.results;
					} else {
						this.$message.error("Retrivied failed! Please retry.");
					}
					this.loading_codes_list = false;
				});
		}
	},
	mounted() {
		// load calctasks type
		this.$api.GetList(`/calctask_types/`).then(resp => {
			if (resp.code == 0) {
				let data = resp.data;
				let calctasks_types = [];
				data.forEach(item => {
					calctasks_types.push({
						label: item.type_name,
						value: item.uuid,
						disabled: item.disabled
					});
				});
				this.calctasks_types = calctasks_types;
			} else {
				this.$message.error("No CalcTasks Types Avaliable.");
				setTimeout(() => {
					this.$store.dispatch("d2admin/page/close", {
						tagName: "/calctasks/submit"
					});
				}, 1500);
				return;
			}
			this.load_calctasks_types = false;
		});
		this.loadStructures();
	}
};
</script>

<style lang="scss">
.calctasks-submit-shengbte-container {
	.form-box {
		> .el-row {
			margin: 0 8px 64px;
		}
	}
}
</style>
