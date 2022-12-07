<template>
	<d2-container class="calctasks-submit-qe-pw">
		<el-row type="flex" justify="center">
			<el-col :xl="16"
				><h1>{{ TaskInfo.title }}</h1></el-col
			>
		</el-row>
		<!-- Load Template -->
		<el-row type="flex" justify="center">
			<el-col :xl="16">
				<el-divider content-position="center"
					><el-tag effect="plain"
						><h2>Load Template</h2></el-tag
					></el-divider
				>
			</el-col>
		</el-row>
		<el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16"
					><el-tag><h3>Load Template</h3></el-tag>
				</el-col>
			</el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<el-divider content-position="left"
						>Load parameters from a template</el-divider
					>
				</el-col>
			</el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<el-select
						v-model="params_templates_selected"
						placeholder="Choose A Template"
						filterable
						default-first-option
						remote
						@change="params_templates_changed"
					>
						<el-option
							v-for="item in params_templates_list"
							:key="item.id"
							:label="`${item.label} - (${item.id})`"
							:value="item.id"
						>
						</el-option>
					</el-select>
				</el-col>
			</el-row>
		</el-row>
		<!-- Task Settings -->
		<el-row type="flex" justify="center">
			<el-col :xl="16">
				<el-divider content-position="center"
					><el-tag effect="plain"
						><h2>{{ TaskInfo.title }} Task Settings</h2></el-tag
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
			<!-- number of machines -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>num_machines</h3></el-tag></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set number of machines</el-divider
						>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-input-number
							v-model="num_machines"
							:min="1"
						></el-input-number>
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
			<!-- Parent Folder -->
			<el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16"
						><el-tag><h3>PW Calculation</h3></el-tag></el-col
					>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set the parent pw calculation for ph calculations
						</el-divider>
					</el-col>
				</el-row>
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-select
							v-model="codes_selected"
							placeholder="Choose A Parent Calculation"
							filterable
							default-first-option
							remote
							:remote-method="getPWCalculationList"
							:loading="loading_pw_list"
							no-data-text="No completed PW Task"
						>
							<el-option
								v-for="item in pw_calc_list"
								:key="item.uuid"
								:label="`${item.label} - (${item.uuid})`"
								:value="item.uuid"
							>
							</el-option>
						</el-select>
					</el-col>
				</el-row>
			</el-row>
		</div>
		<!-- Parameters -->
		<el-row type="flex" justify="center">
			<el-col :xl="16">
				<el-divider content-position="center">
					<el-divider content-position="center"
						><el-tag effect="plain"
							><h2>{{ TaskInfo.title }} Parameters</h2></el-tag
						></el-divider
					>
				</el-divider>
			</el-col>
		</el-row>

		<!-- @todo[1] -->
		<div class="task-parameters form-group">
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<h5 style="text-transform: uppercase;">
						workflows params
					</h5>
					<el-form
						:model="inputsForm"
						:rules="inputsForm"
						:label-width="formLabelWidth"
						ref="inputsForm"
						label-position="top"
						size="small"
					>
						<el-form-item
							label="kpoints_distance"
							prop="kpoints_distance"
						>
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="inputsForm.kpoints_distance"
							></el-input-number>
						</el-form-item>
						<template
							v-if="
								['pw_relax', 'pw_bands'].includes(
									calctasks_type_selected
								)
							"
						>
							<el-form-item
								label="relaxation_scheme"
								prop="relaxation_scheme"
							>
								<p class="color-text-sub">
									The relaxation scheme to use: choose either
									`relax` or `vc-relax` for variable cell
									relax.
								</p>
								<el-select
									v-model="inputsForm.relaxation_scheme"
									default-first-option
								>
									<el-option
										v-for="item in ['relax', 'vc-relax']"
										:key="item"
										:label="item"
										:value="item"
									>
									</el-option>
								</el-select>
							</el-form-item>
							<el-form-item
								label="meta_convergence"
								prop="meta_convergence"
							>
								<p class="color-text-sub">
									If `True` the workchain will perform a
									meta-convergence on the cell volume.
								</p>
								<el-switch
									v-model="inputsForm.meta_convergence"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item
								label="max_meta_convergence_iterations"
								prop="max_meta_convergence_iterations"
							>
								<p class="color-text-sub">
									The maximum number of variable cell relax
									iterations in the meta convergence cycle.
								</p>
								<el-input-number
									v-model="
										inputsForm.max_meta_convergence_iterations
									"
								></el-input-number>
							</el-form-item>
							<el-form-item
								label="volume_convergence"
								prop="volume_convergence"
							>
								<p class="color-text-sub">
									The volume difference threshold between two
									consecutive meta convergence iterations.
								</p>
								<el-input-number
									v-model="inputsForm.volume_convergence"
								></el-input-number>
							</el-form-item>
						</template>
						<template v-if="calctasks_type_selected == 'pw_bands'">
							<el-form-item
								label="nbands_factor"
								prop="nbands_factor"
							>
								<p class="color-text-sub">
									The number of bands for the BANDS
									calculation is that used for the SCF
									multiplied by this factor.
								</p>
								<el-input-number
									v-model="inputsForm.nbands_factor"
								></el-input-number>
							</el-form-item>
							<el-form-item
								label="bands_kpoints_distance"
								prop="bands_kpoints_distance"
							>
								<p class="color-text-sub">
									Minimum kpoints distance for the BANDS
									calculation. Specify either this or
									`bands_kpoints`.
								</p>
								<el-input-number
									v-model="inputsForm.bands_kpoints_distance"
								></el-input-number>
							</el-form-item>
						</template>
					</el-form>
				</el-col>
			</el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<h5 style="text-transform: uppercase;">
						QE inputs params
					</h5>
					<el-form
						:model="parametersForm"
						:rules="parametersFormRules"
						:label-width="formLabelWidth"
						ref="parametersForm"
						label-position="top"
						size="small"
						class="flex-wrap-row"
					>
						<el-form-item label="restart_mode" prop="restart_mode">
							<p class="color-text-sub"></p>
							<el-input
								v-model="parametersForm.restart_mode"
							></el-input>
						</el-form-item>
						<el-form-item label="ecutwfc" prop="ecutwfc">
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.ecutwfc"
							></el-input-number>
						</el-form-item>
						<el-form-item label="ecutrho" prop="ecutrho">
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.ecutrho"
							></el-input-number>
						</el-form-item>
						<el-form-item label="conv_thr" prop="conv_thr">
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.conv_thr"
							></el-input-number>
						</el-form-item>
						<el-form-item label="smearing" prop="smearing">
							<p class="color-text-sub"></p>
							<el-input
								v-model="parametersForm.smearing"
							></el-input>
						</el-form-item>
						<el-form-item label="degauss" prop="degauss">
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.degauss"
							></el-input-number>
						</el-form-item>
						<el-form-item label="occupations" prop="occupations">
							<p class="color-text-sub"></p>
							<el-input
								v-model="parametersForm.occupations"
							></el-input>
						</el-form-item>
						<el-form-item label="mixing_beta" prop="mixing_beta">
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.mixing_beta"
							></el-input-number>
						</el-form-item>
						<el-form-item label="mixing_ndim" prop="mixing_ndim">
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.mixing_ndim"
							></el-input-number>
						</el-form-item>
						<el-form-item
							label="etot_conv_thr"
							prop="etot_conv_thr"
						>
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.etot_conv_thr"
							></el-input-number>
						</el-form-item>
						<el-form-item
							label="forc_conv_thr"
							prop="forc_conv_thr"
						>
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.forc_conv_thr"
							></el-input-number>
						</el-form-item>
						<el-form-item
							label="press_conv_thr"
							prop="press_conv_thr"
						>
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.press_conv_thr"
							></el-input-number>
						</el-form-item>
						<el-form-item label="nstep" prop="nstep">
							<p class="color-text-sub"></p>
							<el-input-number
								v-model="parametersForm.nstep"
							></el-input-number>
						</el-form-item>
						<el-form-item label="wf_collect" prop="wf_collect">
							<p class="color-text-sub"></p>
							<el-switch
								v-model="parametersForm.wf_collect"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item label="tstress" prop="tstress">
							<p class="color-text-sub"></p>
							<el-switch
								v-model="parametersForm.tstress"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item label="tprnfor" prop="tprnfor">
							<p class="color-text-sub"></p>
							<el-switch
								v-model="parametersForm.tprnfor"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>
		</div>
		<!-- upload -->
		<el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<el-divider content-position="center">
						<el-divider content-position="center">
							<el-tag effect="plain">
								<h2>Submit CalcTask</h2>
							</el-tag>
						</el-divider>
					</el-divider>
				</el-col>
			</el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<el-button
						style="margin-left: 10px;"
						type="success"
						plain
						@click="uploadSubmit"
						v-loading.fullscreen.lock="fullscreenLoading"
						>Submit CalcTask</el-button
					>
				</el-col>
			</el-row>
		</el-row>
	</d2-container>
</template>

<script>
/*******************************************************************************
 * TODO: [1]New approche to support mutiple calculations.
 ******************************************************************************/
import util from "@/libs/util";
import CollapseTransition from "@/components/collapse-transition";
const apiPrefix = "/calctasks/";
export default {
	name: "calctasks-submit-qe-ph",
	components: {
		CollapseTransition
	},
	data() {
		return {
			formLabelWidth: "200px",
			fullscreenLoading: false,
			TaskInfo: {
				title: "PW"
			},
			label: "",
			calctasks_type_selected_id: null,
			calctasks_type_selected: "ph",
			calctasks_types: [
				{
					label: "PH",
					value: "ph"
				}
			],
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
			// pseudo family
			loading_pseudo_family_list: false,
			pseudo_family_list: [],
			pseudo_family_selected: "",
			num_machines: 1,
			num_mpiprocs_per_machine: 1,
			// parameters
			inputsForm: {
				relaxation_scheme: "vc-relax",
				meta_convergence: true,
				max_meta_convergence_iterations: 10,
				volume_convergence: 0.01,
				kpoints_distance: 0.1,
				nbands_factor: 1,
				bands_kpoints_distance: 0.1
			},
			parametersForm: {
				ecutwfc: 80,
				ecutrho: 240,
				restart_mode: "from_scratch",
				wf_collect: true,
				conv_thr: 1e-9,
				smearing: "marzari-vanderbilt",
				degauss: 0.02,
				occupations: "smearing",
				tstress: true,
				tprnfor: true,
				mixing_beta: 0.3,
				mixing_ndim: 8,
				etot_conv_thr: 1e-5,
				forc_conv_thr: 1e-4,
				press_conv_thr: 0.1,
				nstep: 60
			},
			parametersFormRules: {},
			relax_structure_flag: false,
			suggested_cutoff_flag: true,
			// parameters template
			params_templates_selected: "default",
			params_templates_list: [{ label: "default", id: "default" }],
			default_parameters_template: {
				parameters: {
					// 'calculation': 'vc-relax',
					ecutwfc: 80,
					ecutrho: 240,
					restart_mode: "from_scratch",
					wf_collect: true,
					conv_thr: 1e-9,
					smearing: "marzari-vanderbilt",
					degauss: 0.02,
					occupations: "smearing",
					tstress: true,
					tprnfor: true,
					mixing_beta: 0.3,
					mixing_ndim: 8,
					etot_conv_thr: 1e-5,
					forc_conv_thr: 1e-4,
					press_conv_thr: 0.1,
					nstep: 60
				},
				inputs: {
					relaxation_scheme: "vc-relax",
					meta_convergence: true,
					max_meta_convergence_iterations: 10,
					volume_convergence: 0.01,
					kpoints_distance: 0.1
				},
				calctasks_settings: {
					withmpi: true,
					num_mpiprocs_per_machine: 1,
					num_machines: 1
				}
			},
			formData: {}
		};
	},
	computed: {
		calctasks_settings() {
			return {
				calcTask_category: "QuantumEspresso",
				label: this.label,
				calctasks_type: this.calctasks_type_selected_id,
				calctasks_description: this.calctasks_description,
				computer: this.computers_selected,
				code: this.codes_selected,
				pseudo_family: this.pseudo_family_selected,
				structures: this.structures,
				withmpi: true,
				num_machines: this.num_machines,
				num_mpiprocs_per_machine: this.num_mpiprocs_per_machine,
				user: util.cookies.get("uuid"),
				suggested_cutoff_flag: this.suggested_cutoff_flag
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
				path: `/structures/detail/${uuid}`
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
					`Only support for single calculation of ${this.TaskInfo.title} Now.`
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
		},
		getPWCalculationList(query) {
			if (query == "") {
				return (this.pw_calc_list = []);
			}
			this.loading_pw_list = true;
			this.$api
				.GetList("/calctasks/", {
					_all: true,
					category: "pw",
					status: "completed",
					label: query
				})
				.then(resp => {
					if (resp.code == 0) {
						this.pw_calc_list = resp.data.results;
					} else {
						this.$message.error("Retrivied failed! Please retry.");
					}
					this.loading_pw_list = false;
				});
		},
		// template
		params_templates_changed(val) {
			if (val == "default") {
				this.parametersForm = util.deepcopy(
					this.default_parameters_template.parameters
				);
				this.inputsForm = util.deepcopy(
					this.default_parameters_template.inputs
				);
			}
		},
		preUploadCheck(callback) {
			this.fullscreenLoading = true;
			try {
				const settings = this.calctasks_settings;
				let failed = false;
				(settings.structures.length == 0 ||
					settings.calctasks_type == "" ||
					settings.computer == "" ||
					settings.label == "" ||
					settings.code == "" ||
					settings.pseudo_family == "") &&
					(failed = true);
				if (failed) {
					this.$message.error(
						"Invaid inputs, please check your calctask settings."
					);
					this.fullscreenLoading = false;
					return false;
				}
			} catch (err) {
				this.$message.error("Invaid calctask settings. Please check.");
				this.fullscreenLoading = false;
				return false;
			}
			this.formData.calctasks_settings = this.calctasks_settings;
			this.formData.inputs = this.inputsForm;
			this.formData.parameters = this.parametersForm;
			typeof callback === "function" && callback();
		},
		uploadSubmit() {
			this.preUploadCheck(() => {
				this.$api
					.AddObj("/calctasks/qe/PWWorkChain/", this.formData)
					.then(resp => {
						console.log(resp);
						this.fullscreenLoading = false;
						this.$message.success("Submit successfully!");
					});
			});
		},
		calctasksTypeChange(val) {
			const type = this.calctasks_types.find(item => {
				return item.value == val;
			});
			this.calctasks_type_selected = type.label;
		}
	},
	mounted() {
		this.$api
			.GetList(`/calctask_types/`, { category_name: "ph" })
			.then(resp => {
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
