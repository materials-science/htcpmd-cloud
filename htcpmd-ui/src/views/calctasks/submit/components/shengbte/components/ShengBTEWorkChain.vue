<template>
	<div class="task-parameters" v-loading="lockComponentFlag">
		<!-- Control -->
		<el-row class="data-control">
			<el-row type="flex" justify="center">
				<el-col :xl="16"
					><el-tag><h3>CONTROL File</h3></el-tag></el-col
				>
			</el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<el-divider content-position="left"
						>Set parameters in CONTROL file</el-divider
					>
				</el-col>
			</el-row>
			<el-row class="form-group">
				<!-- &allocations -->
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-form
							:model="allocationsForm"
							:rules="allocationsFormRules"
							:label-width="formLabelWidth"
							ref="allocationsForm"
							label-position="top"
							size="small"
						>
							<h5 style="text-transform: uppercase;">
								allocations
							</h5>
							<el-form-item label="ngrid" prop="ngrid">
								<p class="color-text-sub">
									number of grid planes along each axis in
									reciprocal space e.g. [3,3,3]
								</p>
								<number-table
									:span="6"
									:data="allocationsForm.ngrid"
									:precision="0"
								></number-table>
							</el-form-item>
							<el-form-item
								label="norientations"
								prop="norientations"
							>
								<p class="color-text-sub">
									number of orientations along which to study
									nanowires
								</p>
								<el-input-number
									v-model="allocationsForm.norientations"
									:min="0"
								></el-input-number>
							</el-form-item>
						</el-form>
					</el-col>
				</el-row>
				<!-- &crystal namelist -->
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-form
							:model="crystalForm"
							:rules="crystalFormRules"
							:label-width="formLabelWidth"
							ref="crystalForm"
							label-position="top"
							size="small"
						>
							<h5 style="text-transform: uppercase;">crystal</h5>
							<el-form-item label="scell" prop="scell">
								<p class="color-text-sub">
									supercell sizes along each crystal axis used
									for the 2nd-order force constant calculation
								</p>
								<number-table
									:span="6"
									:data="crystalForm.scell"
									:precision="0"
								></number-table>
							</el-form-item>
							<el-form-item label="lfactor" prop="lfactor">
								<p class="color-text-sub">
									unit of measurement for lattice vectors
								</p>
								<el-input-number
									v-model="crystalForm.lfactor"
									:precision="6"
									:step="0.1"
								></el-input-number>
							</el-form-item>
							<el-form-item label="epsilon" prop="epsilon">
								<p class="color-text-sub">
									dielectric tensor of the system in the
									Cartesian basis. (default=1)
									<el-button
										type="info"
										plain
										@click="
											epsilonToggleFlag = !epsilonToggleFlag
										"
										>{{
											epsilonToggleFlag
												? "Cancel"
												: "Modify"
										}}</el-button
									>
								</p>
								<template v-if="epsilonToggleFlag">
									<number-table
										:span="6"
										:data="crystalForm.epsilon"
									></number-table>
								</template>
							</el-form-item>
							<el-form-item label="born" prop="born">
								<p class="color-text-sub">
									dielectric tensor of the system in the
									Cartesian basis. (default=1)
									<el-button
										type="info"
										plain
										@click="
											bornToggleFlag = !bornToggleFlag
										"
										>{{
											bornToggleFlag ? "Cancel" : "Modify"
										}}</el-button
									>
								</p>
								<template v-if="bornToggleFlag">
									<el-row type="flex" style="flex-wrap:wrap;">
										<el-col
											v-for="(item,
											num) in crystalForm.born"
											:key="num"
											style="margin: 8px;"
											:lg="6"
										>
											<number-table
												:span="6"
												:data="item"
											></number-table>
										</el-col>
									</el-row>
								</template>
							</el-form-item>
							<el-form-item label="masses" prop="masses">
								<p class="color-text-sub">
									atomic masses corresponding to each element.
									If they are omitted and autoisotopes is true
									and the element names are known, they are
									computed automatically. (g/mol,
									default=automatic)
									<el-button
										type="info"
										plain
										@click="
											massesToggleFlag = !massesToggleFlag
										"
										>{{
											massesToggleFlag
												? "Cancel"
												: "Modify"
										}}</el-button
									>
								</p>

								<template v-if="massesToggleFlag">
									<number-table
										:span="6"
										:data="crystalForm.masses"
										:precision="6"
									></number-table>
								</template>
							</el-form-item>
							<el-form-item label="gfactors" prop="gfactors">
								<p class="color-text-sub">
									g factors for isotopic scattering associated
									to each element. If they are omitted and
									autoisotopes is true and the element names
									are known, they are computed automatically.
									(default=automatic)
									<el-button
										type="info"
										plain
										@click="
											gfactorsToggleFlag = !gfactorsToggleFlag
										"
										>{{
											gfactorsToggleFlag
												? "Cancel"
												: "Modify"
										}}</el-button
									>
								</p>
								<template v-if="gfactorsToggleFlag">
									<number-table
										:span="6"
										:data="crystalForm.gfactors"
										:precision="6"
									></number-table>
								</template>
							</el-form-item>
							<template v-if="orientationsToggleFlag">
								<el-form-item
									label="orientations"
									prop="orientations"
								>
									<p class="color-text-sub">
										terns of integer indices defining the
										crystallographic directions along which
										to study nanowires
									</p>
									<number-table
										:span="6"
										:data="crystalForm.orientations"
										:precision="0"
									></number-table>
								</el-form-item>
							</template>
						</el-form>
					</el-col>
				</el-row>
				<!-- &parameters namelist -->
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-form
							:model="parametersForm"
							:rules="parametersFormRules"
							:label-width="formLabelWidth"
							ref="parametersForm"
							label-position="top"
							size="small"
						>
							<h5 style="text-transform: uppercase;">
								parameters
							</h5>
							<el-form-item label="T" prop="T">
								<p class="color-text-sub">
									temperature to be used in the case of single
									temperature calculation (K)
								</p>
								<el-input-number
									v-model="parametersForm.T"
									:min="0"
								></el-input-number>
							</el-form-item>
							<el-form-item label="T_min/T_max/T_step">
								<p class="color-text-sub">
									the minimum temperature, the maximum
									temperature and the increment to be used for
									multiple-temperature calculation. T takes
									the priority if it is present.
								</p>
								<el-button
									type="info"
									plain
									@click="
										temperatureRangeToggleFlag = !temperatureRangeToggleFlag
									"
									>{{
										temperatureRangeToggleFlag
											? "Cancel"
											: "Modify"
									}}</el-button
								>
								<template v-if="temperatureRangeToggleFlag">
									<el-input-number
										v-model="parametersForm.T_min"
										:min="0"
										label="T_min"
										:controls="false"
									></el-input-number>
									<el-input-number
										v-model="parametersForm.T_max"
										:min="parametersForm.T_min"
										label="T_max"
										:controls="false"
									></el-input-number>
									<el-input-number
										v-model="parametersForm.T_step"
										:min="0"
										label="T_step"
										:controls="false"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="omega_max" prop="omega_max">
								<p class="color-text-sub">
									the max angular frequency up to which the
									anharmonic scattering properties are
									calculated for limited purposes.
									(default=1.e100)
									<el-button
										type="info"
										plain
										@click="
											omega_maxToggleFlag = !omega_maxToggleFlag
										"
										>{{
											omega_maxToggleFlag
												? "Cancel"
												: "Modify"
										}}</el-button
									>
								</p>
								<template v-if="omega_maxToggleFlag">
									<el-input-number
										v-model="parametersForm.omega_max"
										:controls="false"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="scalebroad" prop="scalebroad">
								<p class="color-text-sub">
									scale parameter for Gaussian smearing. The
									default is theoretically guaranteed to work,
									but significant speedups can sometimes be
									achieved by reducing it, with negligible
									loss of precision. (default=1.0)
									<el-button
										type="info"
										plain
										@click="
											scalebroadToggleFlag = !scalebroadToggleFlag
										"
										>{{
											scalebroadToggleFlag
												? "Cancel"
												: "Modify"
										}}</el-button
									>
								</p>
								<template v-if="scalebroadToggleFlag">
									<el-input-number
										v-model="parametersForm.scalebroad"
										:controls="false"
										:precision="6"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="rmin" prop="rmin">
								<p class="color-text-sub">
									minimum radius of nanowires whose thermal
									conductivity will be computed. (default=5.0)
									<el-button
										type="info"
										plain
										@click="
											rminToggleFlag = !rminToggleFlag
										"
										>{{
											rminToggleFlag ? "Cancel" : "Modify"
										}}</el-button
									>
								</p>
								<template v-if="rminToggleFlag">
									<el-input-number
										v-model="parametersForm.rmin"
										:controls="false"
										:precision="6"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="rmax" prop="rmax">
								<p class="color-text-sub">
									minimum radius of nanowires whose thermal
									conductivity will be computed.
									(default=505.0)
									<el-button
										type="info"
										plain
										@click="
											rmaxToggleFlag = !rmaxToggleFlag
										"
										>{{
											rmaxToggleFlag ? "Cancel" : "Modify"
										}}</el-button
									>
								</p>
								<template v-if="rmaxToggleFlag">
									<el-input-number
										v-model="parametersForm.rmax"
										:controls="false"
										:precision="1"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="dr" prop="dr">
								<p class="color-text-sub">
									radius increment to be used when simulating
									nanowires from rmin to rmax. (default=100.0)
									<el-button
										type="info"
										plain
										@click="drToggleFlag = !drToggleFlag"
										>{{
											drToggleFlag ? "Cancel" : "Modify"
										}}</el-button
									>
								</p>
								<template v-if="drToggleFlag">
									<el-input-number
										v-model="parametersForm.dr"
										:controls="false"
										:precision="1"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="maxiter" prop="maxiter">
								<p class="color-text-sub">
									maximum number of iterations allowed in the
									BTE convergence process. (default=1000)
									<el-button
										type="info"
										plain
										@click="
											maxiterToggleFlag = !maxiterToggleFlag
										"
										>{{
											maxiterToggleFlag
												? "Cancel"
												: "Modify"
										}}</el-button
									>
								</p>
								<template v-if="maxiterToggleFlag">
									<el-input-number
										v-model="parametersForm.maxiter"
										:controls="false"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="nticks" prop="nticks">
								<p class="color-text-sub">
									number of different values of the mean free
									path at which to compute the cumulative
									thermal conductivity. (default=100)
									<el-button
										type="info"
										plain
										@click="
											nticksToggleFlag = !nticksToggleFlag
										"
										>{{
											nticksToggleFlag
												? "Cancel"
												: "Modify"
										}}</el-button
									>
								</p>
								<template v-if="nticksToggleFlag">
									<el-input-number
										v-model="parametersForm.nticks"
										:controls="false"
									></el-input-number>
								</template>
							</el-form-item>
							<el-form-item label="eps" prop="eps">
								<p class="color-text-sub">
									the iterative solver of the BTE will stop
									when the relative change in the thermal
									conductivity tensor is less than eps. Such
									change between steps n-1 and n is measured
									as
									||&Kappa;<sub>n</sub>-&Kappa;<sub>n-1</sub>||,
									where ||&sdot;|| denotes a matrix 2-norm.
									(default=10<sup>-5</sup>)
									<el-button
										type="info"
										plain
										@click="epsToggleFlag = !epsToggleFlag"
										>{{
											epsToggleFlag ? "Cancel" : "Modify"
										}}</el-button
									>
								</p>
								<template v-if="epsToggleFlag">
									<el-input-number
										v-model="parametersForm.eps"
										:controls="false"
									></el-input-number>
								</template>
							</el-form-item>
						</el-form>
					</el-col>
				</el-row>
				<!-- &flags namelist -->
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-form
							:model="flagsForm"
							:label-width="formLabelWidth"
							ref="flagsForm"
							label-position="top"
							size="small"
						>
							<h5 style="text-transform: uppercase;">
								flags
							</h5>
							<el-form-item label="espresso" prop="espresso">
								<p class="color-text-sub">
									read second-order force constants from
									espresso.ifc2 (Quantum Espresso format)
									instead of the default FORCE_CONSTANTS_2ND
									(Phonopy format).
								</p>
								<el-switch
									v-model="flagsForm.espresso"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="nanowires" prop="nanowires">
								<p class="color-text-sub">
									study the thermal conductivity of nanowires
									in addition to that of the bulk.
								</p>
								<el-switch
									v-model="flagsForm.nanowires"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item
								label="nonanalytic"
								prop="nonanalytic"
							>
								<p class="color-text-sub">
									compute and use the nonanalytic part of the
									dynamical matrix.
								</p>
								<el-switch
									v-model="flagsForm.nonanalytic"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item
								label="convergence"
								prop="convergence"
							>
								<p class="color-text-sub">
									if true, iterate the BTE solver until
									convergence is achieved. If false, compute
									thermal conductivities in the relaxation
									time approximation.
								</p>
								<el-switch
									v-model="flagsForm.convergence"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="isotopes" prop="isotopes">
								<p class="color-text-sub">
									include isotopic scattering in the
									relaxation times.
								</p>
								<el-switch
									v-model="flagsForm.isotopes"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item
								label="autoisotopes"
								prop="autoisotopes"
							>
								<p class="color-text-sub">
									compute atomic masses and g factors
									automatically.
								</p>
								<el-switch
									v-model="flagsForm.autoisotopes"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item
								label="onlyharmonic"
								prop="onlyharmonic"
							>
								<p class="color-text-sub">
									stop the program after computing the
									specific heat and small-grain thermal
									conductivity.
								</p>
								<el-switch
									v-model="flagsForm.onlyharmonic"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
						</el-form>
					</el-col>
				</el-row>
			</el-row>
		</el-row>
		<!-- Force Constants -->
		<el-row class="data-force-constants">
			<el-row type="flex" justify="center">
				<el-col :xl="16"
					><el-tag><h3>ForceConstants Files</h3></el-tag></el-col
				>
			</el-row>
			<el-row type="flex" justify="center">
				<el-col :xl="16">
					<el-divider content-position="left"
						>Upload ForceConstants files</el-divider
					>
				</el-col>
			</el-row>
			<el-row type="flex" justify="center" class="d2-p-24">
				<el-col :xl="16">
					<el-row type="flex" justify="start">
						<el-col :md="12">
							<el-upload
								class="upload-box"
								ref="forceConstantsUpload"
								:action="uploadApi"
								:headers="uploadHeaders"
								:auto-upload="false"
								multiple
								:data="formData"
								:limit="2"
								list-type="text"
								:on-exceed="exceedFileInfo"
								:on-change="fileInfoChange"
								:on-error="uploadError"
								:on-success="uploadSuccess"
								:file-list="fileList"
								:http-request="uploadFile"
							>
								<el-button
									slot="trigger"
									size="small"
									type="primary"
									plain
									>choose files</el-button
								>
								<div slot="tip" class="el-upload__tip">
									FORCE_CONSTANTS_2ND and FORCE_CONSTANTS_3RD
								</div>
							</el-upload>
						</el-col>
					</el-row>
				</el-col>
			</el-row>
		</el-row>
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
						@click="previewControlFile"
						v-loading.fullscreen.lock="fullscreenLoading"
						>Preview Control File</el-button
					>
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
		<!-- dialog -->
		<el-dialog
			title="CONTROL"
			:visible.sync="previewControlFileToggleFlag"
			width="40%"
			center
		>
			<d2-highlight :code="ControlFile" />
			<span slot="footer" class="dialog-footer">
				<el-button @click="previewControlFileToggleFlag = false"
					>Close</el-button
				>
				<el-button @click="previewControlFileDownload"
					>Download</el-button
				>
			</span>
		</el-dialog>
	</div>
</template>

<script>
/*******************************************************************************
 * TODO: [1]New approche to support mutiple calculations.
 ******************************************************************************/
import NumberTable from "@/components/number-table";
import validate from "@/libs/util.validate";
import util from "@/libs/util";
import setting from "@/setting";
export default {
	name: "ShengBTEWorkChain",
	components: {
		NumberTable
	},
	props: {
		calctasks_settings: { required: true }
	},
	data() {
		let validateArray = validate.elements_of_array_are_not_null;
		return {
			lockComponentFlag: false,
			structures: [],
			allocationsForm: {
				ngrid: [3, 3, 3],
				norientations: 0
			},
			crystalForm: {
				lfactor: 1.0,
				masses: [0],
				gfactors: [0],
				epsilon: [
					[0, 0, 0],
					[0, 0, 0],
					[0, 0, 0]
				],
				born: [
					[
						[0, 0, 0],
						[0, 0, 0],
						[0, 0, 0]
					]
				],
				scell: [3, 3, 3],
				orientations: [[]]
			},
			parametersForm: {
				T: 300,
				T_min: 0,
				T_max: 300,
				T_step: 100,
				omega_max: 1e100,
				scalebroad: 1.0,
				rmin: 5.0,
				rmax: 505.0,
				dr: 100.0,
				maxiter: 1000,
				nticks: 100,
				eps: 1e-5
			},
			flagsForm: {
				nonanalytic: true,
				convergence: true,
				isotopes: true,
				autoisotopes: true,
				nanowires: false,
				onlyharmonic: false,
				espresso: false
			},
			allocationsFormRules: {
				ngrid: [{ required: true, trigger: "blur" }],
				norientations: [{ type: "number", trigger: "blur" }]
			},
			crystalFormRules: {
				lfactor: [{ required: true, trigger: "blur" }],
				scell: [{ required: true, trigger: "blur" }]
			},
			parametersFormRules: {
				maxiter: [{ type: "integer", trigger: "blur" }],
				nticks: [{ type: "integer", trigger: "blur" }]
			},
			formLabelWidth: "200px",
			// optional parameters flags
			massesToggleFlag: false,
			gfactorsToggleFlag: false,
			epsilonToggleFlag: false,
			bornToggleFlag: false,
			orientationsToggleFlag: false,
			temperatureRangeToggleFlag: false,
			omega_maxToggleFlag: false,
			scalebroadToggleFlag: false,
			rminToggleFlag: false,
			rmaxToggleFlag: false,
			drToggleFlag: false,
			maxiterToggleFlag: false,
			nticksToggleFlag: false,
			epsToggleFlag: false,
			// file
			fileList: [],
			formData: {},
			uploadApi: "/api/calctasks/shengbte/ShengBTEWorkChain/",
			uploadHeaders: {
				Authorization: `Token ${util.cookies.get("token")}`
			},
			previewControlFileToggleFlag: false,
			ControlFile: "",
			// upload
			fullscreenLoading: false,
			formChecked: false,
			fileData: []
		};
	},
	methods: {
		exceedFileInfo() {
			this.$message.info("Files exceed!");
		},
		fileInfoChange(file, fileList) {
			if (
				file.name != "FORCE_CONSTANTS_2ND" &&
				file.name != "FORCE_CONSTANTS_3RD"
			) {
				fileList.pop();
				this.$message.error(`Invalid FORCE_CONSTANTS filename!`);
			}
			const preList = fileList.slice(0, fileList.length - 1);
			const existed = preList.some(item => {
				return item.name == file.name;
			});
			if (existed) {
				fileList.pop();
				this.$message.error(`Replicated ${file.name} file!`);
			}
			this.fileList = fileList;
		},
		uploadFile(file) {
			this.fileData.append(file.file.name, file.file);
		},
		selectAElement(val, index) {
			// TODO: Allow to fill array by user
			this.crystalForm.elements.fill(
				val,
				index,
				this.crystalForm.elements.length
			);
		},
		getValX3Array(val, defaultVal = 0) {
			const arr1 = Array.isArray(defaultVal)
				? defaultVal
				: new Array(3).fill(defaultVal);
			const arr2 = new Array(val).fill(JSON.parse(JSON.stringify(arr1)));
			return JSON.parse(JSON.stringify(arr2));
		},
		preUploadCheckHandle() {
			let control = {};
			control.allocations = this.allocationsForm;
			control.crystal = {};
			for (let key in this.crystalForm) {
				let toggleFlag = true;
				switch (key) {
					case "masses": {
						if (!this.massesToggleFlag) toggleFlag = false;
						break;
					}
					case "gfactors": {
						if (!this.gfactorsToggleFlag) toggleFlag = false;
						break;
					}
					case "epsilon": {
						if (!this.epsilonToggleFlag) toggleFlag = false;
						break;
					}
					case "born": {
						if (!this.bornToggleFlag) toggleFlag = false;
						break;
					}
					case "orientations": {
						if (!this.orientationsToggleFlag) toggleFlag = false;
						break;
					}
				}
				toggleFlag && (control.crystal[key] = this.crystalForm[key]);
			}
			control.parameters = {};
			for (let key in this.parametersForm) {
				let toggleFlag = true;
				switch (key) {
					case "T_min":
					case "T_max": {
						toggleFlag = false;
						break;
					}
					case "T_step": {
						if (this.temperatureRangeToggleFlag) {
							control.parameters.T_min = this.parametersForm.T_min;
							control.parameters.T_max = this.parametersForm.T_max;
							control.parameters.T_step = this.parametersForm.T_step;
						}
						toggleFlag = false;
						break;
					}
					case "omega_max": {
						if (!this.omega_maxToggleFlag) toggleFlag = false;
						break;
					}
					case "scalebroad": {
						if (!this.scalebroadToggleFlag) toggleFlag = false;
						break;
					}
					case "rmin": {
						if (!this.rminToggleFlag) toggleFlag = false;
						break;
					}
					case "rmax": {
						if (!this.rmaxToggleFlag) toggleFlag = false;
						break;
					}
					case "dr": {
						if (!this.drToggleFlag) toggleFlag = false;
						break;
					}
					case "maxiter": {
						if (!this.maxiterToggleFlag) toggleFlag = false;
						break;
					}
					case "nticks": {
						if (!this.nticksToggleFlag) toggleFlag = false;
						break;
					}
					case "eps": {
						if (!this.epsilonToggleFlag) toggleFlag = false;
						break;
					}
				}
				toggleFlag &&
					(control.parameters[key] = this.parametersForm[key]);
			}
			control.flags = {};
			Object.assign(control.flags, this.flagsForm);
			this.formData.control = control;
		},
		preUploadCheck(callback) {
			this.fullscreenLoading = true;
			this.$refs.allocationsForm.validate(valid => {
				if (valid) {
					this.$refs.crystalForm.validate(valid => {
						if (valid) {
							this.$refs.parametersForm.validate(valid => {
								if (valid) {
									this.preUploadCheckHandle();
									console.log(this.formData);
									typeof callback === "function" &&
										callback();
									this.fullscreenLoading = false;
								} else {
									this.$notify.error(
										"Invaid input, please check."
									);
									this.fullscreenLoading = false;
									return false;
								}
							});
						} else {
							this.$message.error("Invaid input, please check.");
							this.fullscreenLoading = false;
							return false;
						}
					});
				} else {
					this.$message.error("Invaid input, please check.");
					this.fullscreenLoading = false;
					return false;
				}
			});
		},
		uploadSubmit(e) {
			this.fullscreenLoading = true;
			if (this.fileList == null || this.fileList.length != 2) {
				this.$message.error("No FORCE_CONSTANTS files.");
				this.fullscreenLoading = false;
				return false;
			}
			try {
				const settings = this.calctasks_settings;
				let failed = false;
				(settings.structures.length == 0 ||
					settings.calctasks_type == "" ||
					settings.computer == "" ||
					settings.label == "" ||
					settings.code == "") &&
					(failed = true);
				if (failed) {
					this.$message.error(
						"Invaid input, please check your calctask settings."
					);
					this.fullscreenLoading = false;
					return false;
				}
				this.formData.calctasks_settings = settings;
			} catch (err) {
				this.$message.error("Invaid calctask settings. Please check.");
				this.fullscreenLoading = false;
				return false;
			}
			this.preUploadCheck(() => {
				this.fileData = new FormData();
				this.$refs.forceConstantsUpload.submit();
				this.fileData.append("form", JSON.stringify(this.formData));
				this.$api
					.AddObj(
						"/calctasks/shengbte/ShengBTEWorkChain/",
						this.fileData
					)
					.then(resp => {
						console.log(resp);
						this.fullscreenLoading = false;
						this.$message.success("Submit successfully!");
					});
			});
		},
		getControlFile() {
			this.fullscreenLoading = true;
			this.$api
				.AddObj(
					`/utils/shengbte/files/control/?structure=${this.calctasks_settings.structures[0].uuid}`,
					this.formData.control
				)
				.then(resp => {
					if (resp.code == 0) {
						this.ControlFile = resp.data;
						this.previewControlFileToggleFlag = true;
					}
					this.fullscreenLoading = false;
				});
		},
		previewControlFile() {
			this.preUploadCheck(this.getControlFile);
		},
		previewControlFileDownload() {
			const element = document.createElement("a");
			element.setAttribute(
				"href",
				"data:text/plain;charset=utf-8," +
					encodeURIComponent(this.ControlFile)
			);
			element.setAttribute("download", "CONTROL");
			element.style.display = "none";
			element.click();
			this.previewControlFileToggleFlag = false;
		},
		uploadError(err) {
			this.$message.error(`Uploading failed! ${err}`);
			this.fullscreenLoading = false;
		},
		uploadSuccess() {
			// this.fullscreenLoading = false;
		},
		validateSettings() {
			try {
				let newVal = this.calctasks_settings.structures[0].attributes
					.elements.length;
				this.massesToggleFlag &&
					(this.crystalForm.masses = new Array(newVal).fill(""));
				this.gfactorsToggleFlag &&
					(this.crystalForm.gfactors = new Array(newVal).fill(""));

				this.nelements = newVal;

				newVal = this.calctasks_settings.structures[0].attributes
					.number_of_atoms;
				const arr = this.getValX3Array(3);
				this.crystalForm.born = this.getValX3Array(newVal, arr);

				this.natoms = newVal;
			} catch (e) {
				this.$notify.info("Please add a structure firstly.");
				this.lockComponentFlag = true;
				return;
			}
			this.lockComponentFlag = false;
		}
	},
	watch: {
		calctasks_settings(newVal) {
			this.validateSettings();
			this.formData.calctasks_settings = newVal;
		},
		"allocationsForm.norientations"(newVal) {
			if (newVal != 0) {
				(this.orientationsToggleFlag = true) &&
					(this.crystalForm.orientations = this.getValX3Array(
						newVal
					));
			} else {
				(this.orientationsToggleFlag = false) &&
					(this.crystalForm.orientations = [[]]);
			}
		},
		massesToggleFlag(newVal) {
			newVal &&
				(this.crystalForm.masses = new Array(this.nelements).fill(""));
		},
		gfactorsToggleFlag(newVal) {
			newVal &&
				(this.crystalForm.gfactors = new Array(this.nelements).fill(
					""
				));
		}
	},
	created() {
		this.validateSettings();
	}
};
</script>

<style lang="scss" scoped>
.form-group {
	.el-form {
		padding: 24px;
	}
	.el-form-item {
		padding: 8px;
		.el-form-item__content > [class*="el-input"] {
			width: 200px;
		}
	}
}
</style>
<style lang="scss">
.form-group {
	.el-form-item {
		.el-form-item__label {
			font-weight: bold;
		}
	}
}
</style>
