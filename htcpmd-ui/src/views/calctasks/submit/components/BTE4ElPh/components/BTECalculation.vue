<template>
	<div class="task-parameters">
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
				<!-- &system -->
				<el-row type="flex" justify="center">
					<el-col :xl="16">
						<el-form
							:model="systemForm"
							:rules="systemFormRules"
							:label-width="formLabelWidth"
							ref="systemForm"
							label-position="top"
							size="small"
						>
							<h5 style="text-transform: uppercase;">
								system
							</h5>
							<el-form-item label="type_key" prop="type_key">
								<p class="color-text-sub">
									通过分别设置type_N，type_P和type_M来指定针对不同系统，即n型半导体，p型半导体和金属的计算
								</p>
								<el-input
									v-model="systemForm.type_key"
								></el-input>
							</el-form-item>
							<el-form-item label="Nbands" prop="Nbands">
								<p class="color-text-sub">
									计算中要考虑的能带数
								</p>
								<el-input-number
									v-model="systemForm.Nbands"
									:min="1"
								></el-input-number>
							</el-form-item>
							<el-form-item
								label="Nbands_irrelevant"
								prop="Nbands_irrelevant"
							>
								<p class="color-text-sub">
									计算中要跳过的前几个无关能带的数目
								</p>
								<el-input-number
									v-model="systemForm.Nbands_irrelevant"
									:min="0"
								></el-input-number>
							</el-form-item>
							<el-form-item
								label="Nbands_filled"
								prop="Nbands_filled"
							>
								<p class="color-text-sub">
									能带的填充百分数，用于确定金属体系计算中的费米能。
								</p>
								<el-input-number
									v-model="systemForm.Te_min"
									:min="0"
								></el-input-number>
							</el-form-item>
							<el-form-item label="Te_min" prop="Te_min">
								<p class="color-text-sub">
									确定最低温度，最高温度和用于温度梯度计算的增量。
								</p>
								<el-input-number
									v-model="systemForm.Te_min"
									:min="0"
								></el-input-number>
							</el-form-item>
							<el-form-item label="Te_max" prop="Te_max">
								<p class="color-text-sub">
									确定最低温度，最高温度和用于温度梯度计算的增量。
								</p>
								<el-input-number
									v-model="systemForm.Te_min"
									:min="0"
								></el-input-number>
							</el-form-item>
							<el-form-item label="Te_step" prop="Te_step">
								<p class="color-text-sub">
									确定最低温度，最高温度和用于温度梯度计算的增量。
								</p>
								<el-input-number
									v-model="systemForm.Te_step"
									:min="0"
								></el-input-number>
							</el-form-item>
							<el-form-item label="scalebroad" prop="scalebroad">
								<p class="color-text-sub">
									Gaussina
									smearing的比例参数。建议使用默认值以确保收敛。减小该值可以加快计算速度，但会降低精度。
								</p>
								<el-input-number
									v-model="systemForm.scalebroad"
									:min="1"
								></el-input-number>
							</el-form-item>
							<el-form-item label="Ethreshold" prop="Ethreshold">
								<p class="color-text-sub">
									计算中要考虑的电子状态的能量窗口宽度，即相对于金属的费米能级，能量最高为EThreshold且低至-EThreshold的范围；在n型和p型半导体中，导带最小值与比其高EThreshold处的范围和价带最大值与和比其低EThreshold处的范围。
								</p>
								<el-input-number
									v-model="systemForm.Ethreshold"
								></el-input-number>
							</el-form-item>
							<el-form-item label="NKinterp" prop="NKinterp">
								<p class="color-text-sub">
									用于扩大k点的网格，以便通过有限差分法获得更准确的电子速度。
								</p>
								<el-input-number
									v-model="systemForm.NKinterp"
									:min="1"
								></el-input-number>
							</el-form-item>
							<el-form-item label="NQinterp" prop="NQinterp">
								<p class="color-text-sub">
									用于减少短程el-ph耦合矩阵的计算。当值大于1时，Wannier插值法仅应用于
									(nqf1，nqf2，nqf3)/ NQinerp
									的网格，然后使用线性插值法将矩阵扩展到所需的整个网格。这里的nqf1，nqf2，nqf3是在EPW计算中的精细网格。
								</p>
								<el-input-number
									v-model="systemForm.NQinterp"
									:min="1"
								></el-input-number>
							</el-form-item>
							<el-form-item label="Nc_min" prop="Nc_min">
								<p class="color-text-sub">
									最小浓度，最大浓度和用于浓度梯度计算的增量。
								</p>
								<el-input-number
									v-model="systemForm.Nc_min"
								></el-input-number>
							</el-form-item>
							<el-form-item label="Nc_max" prop="Nc_max">
								<p class="color-text-sub">
									最小浓度，最大浓度和用于浓度梯度计算的增量。
								</p>
								<el-input-number
									v-model="systemForm.Nc_max"
								></el-input-number>
							</el-form-item>
							<el-form-item label="Nc_step" prop="Nc_step">
								<p class="color-text-sub">
									最小浓度，最大浓度和用于浓度梯度计算的增量。
								</p>
								<el-input-number
									v-model="systemForm.Nc_step"
								></el-input-number>
							</el-form-item>
							<el-form-item label="repsil" prop="repsil">
								<p class="color-text-sub">
									使用Brooks-Herring模型进行杂质散射计算中的相对介电常数。
								</p>
								<el-input-number
									v-model="systemForm.repsil"
								></el-input-number>
							</el-form-item>
							<el-form-item
								label="delta_shift_EFermi"
								prop="delta_shift_EFermi"
							>
								<p class="color-text-sub">
									费米能级相对于金属原始费米能的偏移。
								</p>
								<el-input-number
									v-model="systemForm.delta_shift_EFermi"
								></el-input-number>
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
							<el-form-item
								label="convergence"
								prop="convergence"
							>
								<p class="color-text-sub">
									控制求解BTE的方法。如果为true，则迭代求解BTE，否则根据RTA求解。
								</p>
								<el-switch
									v-model="flagsForm.convergence"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="tolerance" prop="tolerance">
								<p class="color-text-sub">
									BTE迭代解差值的可接受范围。
								</p>
								<el-input-number
									v-model="flagsForm.tolerance"
								></el-input-number>
							</el-form-item>
							<el-form-item label="tolerance" prop="tolerance">
								<p class="color-text-sub">
									BTE迭代求解的最多迭代次数
								</p>
								<el-input-number
									v-model="flagsForm.maxiter"
								></el-input-number>
							</el-form-item>
							<el-form-item label="phwann" prop="phwann">
								<p class="color-text-sub">
									如果为true，则使用wannier插值法计算声子频率，并通过有限差分法计算声子群速度。
								</p>
								<el-switch
									v-model="flagsForm.phwann"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="elwann" prop="elwann">
								<p class="color-text-sub">
									如果为true，则使用wannier插值法计算电子速度，否则采用有限差分法计算。
								</p>
								<el-switch
									v-model="flagsForm.elwann"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="gwrite" prop="gwrite">
								<p class="color-text-sub">
									写出el-ph耦合矩阵。
								</p>
								<el-switch
									v-model="flagsForm.gwrite"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="gread" prop="gread">
								<p class="color-text-sub">
									读取先前计算写入的el-ph耦合矩阵。
								</p>
								<el-switch
									v-model="flagsForm.gread"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item
								label="electron_ph"
								prop="electron_ph"
							>
								<p class="color-text-sub">
									计算电输运特性。
								</p>
								<el-switch
									v-model="flagsForm.electron_ph"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="phonon_el" prop="phonon_el">
								<p class="color-text-sub">
									计算声子-电子的散射率。
								</p>
								<el-switch
									v-model="flagsForm.phonon_el"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="impurity" prop="impurity">
								<p class="color-text-sub">
									在电传输计算中包括杂质散射。
								</p>
								<el-switch
									v-model="flagsForm.impurity"
									active-text="True"
									inactive-text="False"
								>
								</el-switch>
							</el-form-item>
							<el-form-item label="hallfactor" prop="hallfactor">
								<p class="color-text-sub">
									计算材料的霍尔系数。
								</p>
								<el-switch
									v-model="flagsForm.hallfactor"
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
import NumberTable from "@/components/number-table";
import elementsData from "@/assets/data/periodic.json";
import validate from "@/libs/util.validate";
import util from "@/libs/util";
import setting from "@/setting";
export default {
	name: "ShengBTECalculation",
	components: {
		NumberTable
	},
	props: {
		calctasks_settings: { required: true }
	},
	data() {
		let validateArray = validate.elements_of_array_are_not_null;
		return {
			structures: [],
			systemForm: {
				type_key: "type_N",
				Nbands: 12,
				Nbands_irrelevant: 12,
				Nbands_filled: 0.2,
				Te_min: 200,
				Te_max: 300,
				Te_step: 20,
				scalebroad: 1,
				Ethreshold: 0.2,
				NKinterp: 1,
				NQinterp: 1,
				Nc_min: 12,
				Nc_max: 13,
				Nc_step: 2,
				repsil: 0.0,
				delta_shift_EFermi: 0.0
			},
			flagsForm: {
				convergence: true,
				tolerance: 1e-3,
				maxiter: 50,
				phwann: false,
				elwann: false,
				gwrite: false,
				gread: false,
				electron_ph: true,
				phonon_el: false,
				impurity: false,
				hallfactor: false
			},
			systemFormRules: {},
			formLabelWidth: "200px",
			// file
			fileList: [],
			formData: {},
			uploadApi: "/api/calctasks/shengbte/ShengBTECalculation/",
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
	computed: {
		Elements() {
			return elementsData;
		}
	},
	methods: {
		selectAElement(val, index) {
			// TODO: Allow to fill array by user
			this.crystalForm.elements.fill(
				val,
				index,
				this.crystalForm.elements.length
			);
		},
		preUploadCheckHandle() {
			let control = {};
			control.system = this.systemForm;
			control.flags = {};
			Object.assign(control.flags, this.flagsForm);
			this.formData.control = control;
		},
		preUploadCheck(callback) {
			this.fullscreenLoading = true;
			this.$refs.systemForm.validate(valid => {
				if (valid) {
					this.preUploadCheckHandle();
					console.log(this.formData);
					typeof callback === "function" && callback();
					this.fullscreenLoading = false;
				} else {
					this.$notify.error("Invaid input, please check.");
					this.fullscreenLoading = false;
					return false;
				}
			});
		},
		uploadSubmit(e) {
			this.fullscreenLoading = true;
			try {
				const settings = this.formData.calctasks_settings;
				let failed = false;
				(settings.structures.length == 0 ||
					settings.calctasks_type == "" ||
					settings.computer == "" ||
					settings.label == "" ||
					settings.code == "") &&
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
			this.preUploadCheck(() => {
				this.$api
					.AddObj(
						"/calctasks/BTE4ElPh/BTE4ElPhCalculation/",
						this.formData
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
				.AddObj("/utils/BTE4ElPh/files/control/", this.formData.control)
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
		}
	},
	watch: {
		calctasks_settings(newVal) {
			this.formData.calctasks_settings = this.calctasks_settings;
		}
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
