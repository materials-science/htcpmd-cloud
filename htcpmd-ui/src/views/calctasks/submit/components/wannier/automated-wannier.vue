<template>
	<d2-container class="calctasks-submit-wannier-automated_wannier">
		<el-row>
			<el-col :xl="16"
				><h1>{{ TaskInfo.title }}</h1></el-col
			>
		</el-row>
		<!-- Load Template -->
		<el-row class="form-box">
			<el-row>
				<el-col :xl="16">
					<el-divider content-position="center"
						><el-tag effect="plain"
							><h2>Load Template</h2></el-tag
						></el-divider
					>
				</el-col>
			</el-row>
			<el-row>
				<el-row>
					<el-col :xl="16"
						><el-tag><h3>Load Template</h3></el-tag>
					</el-col>
				</el-row>
				<el-row>
					<el-col :xl="16">
						<el-divider content-position="left"
							>Load parameters from a template</el-divider
						>
					</el-col>
				</el-row>
				<el-row>
					<el-col :xl="16">
						<el-select
							v-model="params_templates_selected"
							placeholder="Choose A Template"
							filterable
							default-first-option
							remote
							@change="paramsTemplatesChange"
						>
							<el-option
								v-for="(item, index) in params_templates_list"
								:key="item.label"
								:label="item.label"
								:value="index"
							>
							</el-option>
						</el-select>
					</el-col>
				</el-row>
			</el-row>
			<!-- Structures -->
			<el-row>
				<el-row>
					<el-col :xl="16"
						><el-tag><h3>Structures</h3></el-tag></el-col
					>
				</el-row>
				<el-row>
					<el-col :xl="16">
						<el-divider content-position="left"
							>Set Structures for your calculations</el-divider
						>
					</el-col>
				</el-row>
				<el-row>
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
		</el-row>
		<!-- Task Settings and Options-->
		<el-row :gutter="20">
			<!-- Task Settings -->
			<el-col :xl="12">
				<el-divider content-position="center"
					><el-tag effect="plain"
						><h2>{{ TaskInfo.title }} Task Settings</h2></el-tag
					></el-divider
				>
				<div class="form-box">
					<!-- CalcJob Label -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag
									><h3>CalcTask Label</h3></el-tag
								></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set a label for your task</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-input
									v-model="calctasks_settings.label"
								></el-input>
							</el-col>
						</el-row>
					</el-row>
					<!-- CalcJob Type -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag><h3>CalcTask Type</h3></el-tag></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left">
									Select calculations or workchains model you
									want</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-select
									v-model="calctasks_settings.calctasks_type"
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
						<el-row>
							<el-col :xl="16"
								><el-tag><h3>Computer</h3></el-tag>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set a computer where your calculations
									submit to</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-select
									v-model="calctasks_settings.computer"
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
										:label="
											`${item.label} - (${item.uuid})`
										"
										:value="item.uuid"
									>
									</el-option>
								</el-select>
							</el-col>
						</el-row>
					</el-row>
					<!-- code -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag
									><h3>Executable Codes</h3></el-tag
								></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set codes for your calculations
								</el-divider>
							</el-col>
						</el-row>
						<el-row>
							<!-- pw code -->
							<el-col :xl="6" :sm="12">
								<el-select
									v-model="calctasks_settings.pw_code"
									placeholder="PW Code"
									filterable
									default-first-option
									remote
									:remote-method="getCodesList"
									:loading="loading_codes_list"
								>
									<el-option
										v-for="item in codes_list"
										:key="item.uuid"
										:label="
											`${item.label} | ${item.computer.label} | (${item.uuid})`
										"
										:value="item.uuid"
									>
									</el-option>
								</el-select>
							</el-col>
							<!-- pw2wannier90 code -->
							<el-col :xl="6" :sm="12">
								<el-select
									v-model="
										calctasks_settings.pw2wannier90_code
									"
									placeholder="pw2wannier90"
									filterable
									default-first-option
									remote
									:remote-method="getCodesList"
									:loading="loading_codes_list"
								>
									<el-option
										v-for="item in codes_list"
										:key="item.uuid"
										:label="
											`${item.label} | ${item.computer.label} | (${item.uuid})`
										"
										:value="item.uuid"
									>
									</el-option>
								</el-select>
							</el-col>
						</el-row>
						<el-row class="d2-mt">
							<!-- projwfc code -->
							<el-col :xl="6" :sm="12">
								<el-select
									v-model="calctasks_settings.projwfc_code"
									placeholder="projwfc"
									filterable
									default-first-option
									remote
									:remote-method="getCodesList"
									:loading="loading_codes_list"
								>
									<el-option
										v-for="item in codes_list"
										:key="item.uuid"
										:label="
											`${item.label} | ${item.computer.label} | (${item.uuid})`
										"
										:value="item.uuid"
									>
									</el-option>
								</el-select>
							</el-col>
							<!-- wannier90 code -->
							<el-col :xl="6" :sm="12">
								<el-select
									v-model="calctasks_settings.wannier90_code"
									placeholder="wannier90"
									filterable
									default-first-option
									remote
									:remote-method="getCodesList"
									:loading="loading_codes_list"
								>
									<el-option
										v-for="item in codes_list"
										:key="item.uuid"
										:label="
											`${item.label} | ${item.computer.label} | (${item.uuid})`
										"
										:value="item.uuid"
									>
									</el-option>
								</el-select>
							</el-col>
						</el-row>
					</el-row>
					<!-- pseudo family -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag><h3>Pseudo Family</h3></el-tag>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set the pseudo_family for your
									calculations</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-select
									v-model="calctasks_settings.pseudo_family"
									placeholder="Choose the pseudo_family"
									filterable
									default-first-option
									remote
									:remote-method="getPseudoFamilyList"
									:loading="loading_pseudo_family_list"
								>
									<el-option
										v-for="item in pseudo_family_list"
										:key="item.uuid"
										:label="
											`${item.label} | (${item.uuid})`
										"
										:value="item.uuid"
									>
									</el-option>
								</el-select>
							</el-col>
						</el-row>
					</el-row>
					<!-- CalcJob Description -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag
									><h3>CalcTask Description</h3></el-tag
								></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Description of your task</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-input
									type="textarea"
									:autosize="{ minRows: 2 }"
									placeholder=""
									v-model="
										calctasks_settings.calctasks_description
									"
									show-word-limit
								>
								</el-input>
							</el-col>
						</el-row>
					</el-row>
					<!-- CalcJob Group -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag
									><h3>CalcTask Group</h3></el-tag
								></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set a group for your task</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-input
									v-model="calctasks_settings.calctasks_group"
								></el-input>
							</el-col>
						</el-row>
					</el-row>
				</div>
			</el-col>
			<!-- Task Options -->
			<el-col :xl="12">
				<el-divider content-position="center"
					><el-tag effect="plain"
						><h2>{{ TaskInfo.title }} Task Settings</h2></el-tag
					></el-divider
				>
				<div class="form-box">
					<!-- number of machines -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag><h3>num_machines</h3></el-tag></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set number of machines</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-input-number
									v-model="calctasks_options.num_machines"
									:min="1"
									:precision="0"
								></el-input-number>
							</el-col>
						</el-row>
					</el-row>
					<!-- process per machine -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag
									><h3>num_mpiprocs_per_machine</h3></el-tag
								></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set number of mpi process per
									machine</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-input-number
									v-model="
										calctasks_options.num_mpiprocs_per_machine
									"
									:min="1"
									:precision="0"
								></el-input-number>
							</el-col>
						</el-row>
					</el-row>
					<!-- PBS queue -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag><h3>queue</h3></el-tag></el-col
							>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-divider content-position="left"
									>Set queue of PBS</el-divider
								>
							</el-col>
						</el-row>
						<el-row>
							<el-col :xl="16">
								<el-input
									v-model="calctasks_options.queue"
								></el-input>
							</el-col>
						</el-row>
					</el-row>
					<!-- should_run_dft_bands, should_plot_bands -->
					<el-row>
						<el-row>
							<el-col :xl="16"
								><el-tag
									><h3>should_run_others</h3></el-tag
								></el-col
							>
						</el-row>
						<el-form
							:label-width="formLabelWidth"
							label-position="left"
							size="small"
						>
							<el-form-item label="should_run_dft_bands">
								<p class="color-text-sub">run dft bands</p>
								<el-switch
									v-model="
										calctasks_settings.should_run_dft_bands
									"
									active-text="True"
									inactive-text="False"
								></el-switch>
							</el-form-item>
							<el-form-item label="should_plot_bands">
								<p class="color-text-sub">
									Whether or not to plot bands
								</p>
								<el-switch
									v-model="
										calctasks_settings.should_plot_bands
									"
									active-text="True"
									inactive-text="False"
								></el-switch>
							</el-form-item>
							<el-form-item label="should_run_relax">
								<p class="color-text-sub">
									Whether or not to relax structure
								</p>
								<el-switch
									v-model="
										calctasks_settings.should_run_relax
									"
									active-text="True"
									inactive-text="False"
								></el-switch>
							</el-form-item>
						</el-form>
					</el-row>
				</div>
			</el-col>
		</el-row>
		<!-- Parameters -->
		<el-row>
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

		<div class="task-parameters form-group">
			<!-- @todo[1] -->
			<el-row>
				<el-col :xl="16">
					<el-form
						:label-width="formLabelWidth"
						label-position="left"
						size="small"
						class="flex-wrap-row"
					>
						<el-form-item label="only_valence" prop="only_valence">
							<p class="color-text-sub"></p>
							<el-switch
								v-model="inputsForm.only_valence"
							></el-switch>
						</el-form-item>
						<el-form-item
							label="retrieve_hamiltonian"
							prop="retrieve_hamiltonian"
						>
							<p class="color-text-sub"></p>
							<el-switch
								v-model="inputsForm.retrieve_hamiltonian"
							>
							</el-switch>
						</el-form-item>
						<el-form-item
							label="plot_wannier_functions"
							prop="plot_wannier_functions"
						>
							<p class="color-text-sub"></p>
							<el-switch
								v-model="inputsForm.plot_wannier_functions"
							></el-switch>
						</el-form-item>
						<el-form-item
							label="do_disentanglement"
							prop="do_disentanglement"
						>
							<p class="color-text-sub"></p>
							<el-switch
								v-model="inputsForm.do_disentanglement"
							></el-switch>
						</el-form-item>
						<el-form-item label="do_mlwf" prop="do_mlwf">
							<p class="color-text-sub"></p>
							<el-switch v-model="inputsForm.do_mlwf"></el-switch>
						</el-form-item>
						<el-form-item
							label="write_u_matrices"
							prop="write_u_matrices"
						>
							<p class="color-text-sub"></p>
							<el-switch
								v-model="inputsForm.write_u_matrices"
							></el-switch>
						</el-form-item>
						<el-form-item
							label="spin_polarized"
							prop="spin_polarized"
						>
							<p class="color-text-sub"></p>
							<el-switch
								v-model="inputsForm.spin_polarized"
							></el-switch>
						</el-form-item>
						<el-form-item
							label="spin_orbit_coupling"
							prop="spin_orbit_coupling"
						>
							<p class="color-text-sub"></p>
							<el-switch
								v-model="inputsForm.spin_orbit_coupling"
							></el-switch>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>
			<!-- protocol and parameters -->
			<el-row>
				<el-col :xl="16">
					<el-form label-position="left" size="small">
						<el-form-item label="Protocol">
							<p class="color-text-sub">
								Currently available protocols are 'theos-ht-1.0'
								and 'testing'
								<router-link
									to="/data/potentials"
									class="d2-ml color-text-placeholder"
									>Setup (sssp) pseudo_family
									first</router-link
								>
							</p>
							<el-cascader
								:options="protocols_list"
								v-model="calctasks_settings.protocol"
							>
								<template slot-scope="{ node, data }">
									<span>{{ data.label }}</span>
									<span v-if="!node.isLeaf">
										({{ data.children.length }})
									</span>
								</template>
							</el-cascader>
						</el-form-item>
						<el-form-item label="kpoints_mesh">
							<p class="color-text-sub">
								If not set, kpoints_distance of protocol will be
								used.
							</p>
							<number-table
								:span="6"
								:data="inputsForm.kpoints_mesh"
								:precision="0"
							></number-table>
						</el-form-item>
						<el-form-item label="cutoff">
							<p class="color-text-sub">
								If not set, cutoff of sssp will be used.
							</p>
							<el-input-number
								v-model="inputsForm.cutoff"
								:min="0"
								:precision="0"
							></el-input-number>
						</el-form-item>
						<el-form-item label="dual">
							<p class="color-text-sub">
								If not set, dual of sssp will be used.
							</p>
							<el-input-number
								v-model="inputsForm.dual"
								:min="0"
								:precision="0"
							></el-input-number>
						</el-form-item>
						<el-form-item label="system_2d">
							<p class="color-text-sub">
								Set it to True if the system is 2D.
							</p>
							<el-switch
								v-model="inputsForm.system_2d"
							></el-switch>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>
		</div>
		<!-- upload -->
		<!-- <el-row> -->
		<el-row>
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
		<el-row :gutter="20">
			<el-col>
				<!-- <el-button
						style="margin-left: 10px;"
						type="success"
						plain
						@click="testInputs"
						v-loading.fullscreen.lock="fullscreenLoading"
						>Test Inputs Params</el-button
					> -->
				<el-button
					style="margin-left: 10px"
					type="success"
					plain
					@click="uploadSubmit"
					v-loading.fullscreen.lock="fullscreenLoading"
					>Submit Task</el-button
				>
				<el-popover
					placement="top-start"
					title="Tips"
					width="200"
					trigger="hover"
					content="It will save data you input as a template."
				>
					<el-button
						slot="reference"
						style="margin-left: 10px"
						plain
						@click="template_dialog_visible = true"
						>Save as Templates</el-button
					>
				</el-popover>
			</el-col>
		</el-row>
		<!-- </el-row> -->
		<!-- dialog -->
		<el-dialog title="Template" :visible.sync="template_dialog_visible">
			<el-form label-position="left">
				<el-form-item
					label="Template Label"
					:label-width="formLabelWidth"
				>
					<!-- <el-input
						autocomplete="off"
						v-model="new_template_name"
						:suffix-icon="
							!!new_template_is_valid
								? 'el-icon-check'
								: 'el-icon-circle-close'
						"
					></el-input> -->
					<el-select
						v-model="new_template_name"
						filterable
						allow-create
						default-first-option
						placeholder="overide or add one new"
					>
						<el-option
							v-for="item in params_templates_list"
							:key="item.label"
							:label="item.label"
							:value="item.label"
						>
						</el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="template_dialog_visible = false"
					>cancel</el-button
				>
				<el-button type="primary" @click="saveAsTemplates"
					>confirm</el-button
				>
			</div>
		</el-dialog>
	</d2-container>
</template>

<script>
/*******************************************************************************
 * TODO: [1]New approche to support mutiple calculations.
 ******************************************************************************/
import util from "@/libs/util";
import CollapseTransition from "@/components/collapse-transition";
const apiPrefix = "/calctasks/";
import NumberTable from "@/components/number-table";
export default {
	name: "calctasks-submit-wannier-automated_wannier",
	components: {
		CollapseTransition,
		NumberTable
	},
	data() {
		return {
			formLabelWidth: "200px",
			fullscreenLoading: false,
			loading_counter: 3,
			TaskInfo: {
				title: "Automated Wannier"
			},
			calctasks_type_selected: "automated_wannier",
			calctasks_types: [
				{
					label: "automated_wannier",
					value: "automated_wannier"
				}
			],
			load_calctasks_types: true,
			// structures
			loading_structures: true,
			structures: [],
			new_structure_id: "",
			// computers
			loading_computers_list: false,
			computers_list: [],
			computers_selected: "",
			// codes
			loading_codes_list: false,
			codes_list: [],
			// pseudo family
			loading_pseudo_family_list: false,
			pseudo_family_list: [],
			// protocol
			loading_protocol: true,
			protocols_list: [],
			// parameters
			inputsForm: {
				only_valence: false,
				retrieve_hamiltonian: false,
				plot_wannier_functions: false,
				do_disentanglement: false,
				do_mlwf: false,
				write_u_matrices: false,
				spin_polarized: false,
				spin_orbit_coupling: false,
				kpoints_mesh: [0, 0, 0],
				cutoff: null,
				dual: null,
				system_2d: false
			},
			// inputsFormRules: {},
			// parameters template
			template_dialog_visible: false,
			params_templates_selected: 0,
			params_templates_list: [
				{
					label: "default",
					template: {
						inputs: {
							only_valence: false,
							retrieve_hamiltonian: true,
							plot_wannier_functions: true,
							do_disentanglement: false,
							do_mlwf: false,
							write_u_matrices: true,
							kpoints_mesh: [0, 0, 0],
							cutoff: null,
							dual: null,
							system_2d: false
						},
						calctasks_settings: {
							calctasks_group: "wannier",
							should_run_relax: false,
							should_run_dft_bands: false,
							should_plot_bands: false,
							pseudo_family: null
						},
						calctasks_options: {
							withmpi: true,
							num_machines: 1,
							num_mpiprocs_per_machine: 1,
							queue: null
						}
					}
				}
			],
			new_template_name: "",
			// form data
			formData: {},
			calctasks_settings: {
				calcTask_category: "automated_wannier",
				label: "",
				calctasks_type: null,
				calctasks_description: "Automated Wannier",
				calctasks_group: null,
				computer: null,
				pw_code: null,
				pw2wannier90_code: null,
				projwfc_code: null,
				wannier90_code: null,
				pseudo_family: null,
				protocol: null,
				structures: [],
				should_run_relax: false,
				should_run_dft_bands: false,
				should_plot_bands: false,
				user: util.cookies.get("uuid")
			},
			calctasks_options: {
				withmpi: true,
				num_machines: 1,
				num_mpiprocs_per_machine: 1,
				queue: null
			}
		};
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
							this.saveStructures(this.structures);
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
			if (
				this.calctasks_settings.computer == "" ||
				this.calctasks_settings.computer == null
			) {
				return this.$message.info("Please select a computer first!");
			}
			this.loading_codes_list = true;
			this.$api
				.GetList("/codes/", {
					label: query,
					uuid: query,
					computer: this.calctasks_settings.computer
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
		getPseudoFamilyList(query) {
			if (query == "") {
				return (this.pseudo_family_list = []);
			}
			this.loading_pseudo_family_list = true;
			this.$api
				.GetList("/potentials/", {
					label: query,
					uuid: query
				})
				.then(resp => {
					if (resp.code == 0) {
						this.pseudo_family_list = resp.data.results;
					} else {
						this.$message.error("Retrivied failed! Please retry.");
					}
					this.loading_pseudo_family_list = false;
				});
		},
		async loadData() {
			// load from draft
			const data = await this.$store.dispatch("d2admin/db/pageGet", {
				instance: this,
				user: true
			});
			if (Object.keys(data).length > 0) {
				Object.assign(this.$data, data);
				this.$store.dispatch("d2admin/db/pageClear", {
					user: true
				});
			} else {
				this.loadTemplates();
			}
			this.loadStructures();
		},
		async loadStructures() {
			const db = await this.$store.dispatch("d2admin/db/database", {
				user: true
			});
			// TODO: Remove selected structures stored in local database
			this.structures = db.get("selectedStructures").value() || [];
			// @todo[1]
			if (this.structures.length > 1) {
				this.$message.info(
					"Only support for single calculation of ShengBTE Now. It will use the first structure."
				);
				this.structures = this.structures[0];
			}
			this.loading_structures = false;
			this.loading_counter -= 1;
		},
		async saveStructures(structures) {
			const db = await this.$store.dispatch("d2admin/db/database", {
				user: true
			});
			db.set("selectedStructures", structures).write();
		},
		// template
		async loadTemplates() {
			// load from local storage;
			// TODO: `computers`, `codes` only have uuid but no name.
			const db = await this.$store.dispatch("d2admin/db/databasePage", {
				user: true
			});
			let templates = db.get("templates").value() || null;
			if (templates != null) {
				this.params_templates_list = templates;
			}
			this.paramsTemplatesChange(this.params_templates_selected);
		},
		paramsTemplatesChange(val) {
			console.log(this.params_templates_list);
			let template = this.params_templates_list[val].template;
			// TODO: assign won't overide all properties
			Object.assign(this.inputsForm, util.deepcopy(template.inputs));
			Object.assign(
				this.calctasks_settings,
				util.deepcopy(template.calctasks_settings)
			);
			Object.assign(
				this.calctasks_options,
				util.deepcopy(template.calctasks_options)
			);
		},
		async saveAsTemplates() {
			if (this.new_template_name == "") {
				return this.$notify.error(
					"Please select or add one new template."
				);
			}
			let index = this.params_templates_list.findIndex((val, index) => {
				return val.label == this.new_template_name;
			});
			let template = util.deepcopy({
				inputs: this.inputsForm,
				calctasks_settings: this.calctasks_settings,
				calctasks_options: this.calctasks_options
			});
			if (index == -1) {
				this.params_templates_list.push({
					label: this.new_template_name,
					template: template
				});
			} else {
				this.params_templates_list[index].template = template;
			}
			const db = await this.$store.dispatch("d2admin/db/databasePage", {
				user: true
			});
			db.set("templates", this.params_templates_list).write();
			this.template_dialog_visible = false;
		},
		SaveAsDrafts() {
			this.$store.dispatch("d2admin/db/pageSet", {
				instance: this,
				user: true
			});
		},
		preUploadCheck(callback) {
			this.fullscreenLoading = true;
			try {
				const settings = this.calctasks_settings;
				let failed = false;
				(this.structures.length == 0 ||
					settings.calctasks_type == "" ||
					settings.computer == "" ||
					settings.label == "" ||
					settings.pw_code == "" ||
					settings.pw2wannier90_code == "" ||
					settings.projwfc_code == "" ||
					settings.wannier90_code == "" ||
					!settings.protocol) &&
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
			// this.formData.structures = { uuid: this.structures.uuid };
			this.formData.structures = this.structures.map(item => {
				return { uuid: item.uuid };
			});
			this.formData.calctasks_settings = this.calctasks_settings;
			this.formData.calctasks_options = this.calctasks_options;
			this.formData.inputs = this.inputsForm;
			typeof callback === "function" && callback();
		},
		uploadSubmit() {
			this.preUploadCheck(() => {
				this.$api
					.AddObj(
						"/calctasks/wannier/automated_wannier/",
						this.formData
					)
					.then(resp => {
						console.log(resp);
						this.fullscreenLoading = false;
						this.$message.success("Submit successfully!");
					});
			});
			/* this.preUploadCheck(() => {
				console.log(JSON.stringify(this.formData));
				this.fullscreenLoading = false;
			}); */
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
			.GetList(`/calctask_types/`, { category_name: "Wannier" })
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
				this.loading_counter -= 1;
			});
		this.$api
			.GetList(`/available_protocols/`, { type: "pw" })
			.then(resp => {
				if (resp.code == 0) {
					let protocols_data = resp.data;
					let protocols = [];
					for (let key in protocols_data) {
						let prot = {
							label: key,
							value: key
						};
						let children = [];
						for (let name in protocols_data[key].parameters) {
							children.push({
								label: name,
								value: name
							});
						}
						prot.children = children;
						protocols.push(prot);
					}
					this.protocols_list = protocols;
					console.log(protocols);
				} else {
					this.$message.error(
						"Errors occured while getting available protocols."
					);
					setTimeout(() => {
						this.$store.dispatch("d2admin/page/close", {
							tagName: "/calctasks/submit"
						});
					}, 1500);
					return;
				}
				this.loading_protocol = false;
				this.loading_counter -= 1;
			});
		this.loadData();
	},
	beforeRouteLeave(to, from, next) {
		this.SaveAsDrafts();
		next();
	}
};
</script>
