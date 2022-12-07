<template>
	<div v-loading="loading">
		<el-row :gutter="20">
			<el-col :xl="12" :lg="12" :md="12">
				<el-form
					:model="form"
					:rules="formRules"
					ref="form"
					label-position="left"
				>
					<el-form-item
						label="label"
						prop="label"
						:label-width="formLabelWidth"
					>
						<el-input
							v-model="form.label"
							autocomplete="off"
						></el-input>
					</el-form-item>
					<el-form-item
						label="description"
						prop="description"
						:label-width="formLabelWidth"
					>
						<el-input
							v-model="form.description"
							autocomplete="off"
						></el-input>
					</el-form-item>
					<el-form-item
						label="type"
						prop="type"
						:label-width="formLabelWidth"
					>
						<el-select
							v-model="form.type"
							placeholder="Choose A Type"
							filterable
							autocomplete
							default-first-option
						>
							<el-option
								v-for="item in potentials_types"
								:key="item.value"
								:label="item.label"
								:value="item.value"
							>
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-upload
							class="upload-box"
							ref="potentialsUpload"
							action=""
							:auto-upload="false"
							list-type="text"
							:file-list="fileList"
							:http-request="uploadFile"
							:accept="acceptExts.join()"
						>
							<el-button
								slot="trigger"
								size="small"
								type="primary"
								plain
								>choose files</el-button
							>
							<div slot="tip" class="el-upload__tip">
								Potentials File(zip)
							</div>
						</el-upload>
					</el-form-item>
				</el-form>
			</el-col>
			<el-col :xl="12" :lg="12" :md="12">
				<el-card class="box-card" shadow="never">
					<p slot="header">Directory Structure</p>
					<el-row :gutter="20">
						<el-col :span="12">
							<p>
								<d2-icon name="file-archive-o" />
								potpaw_PBE.54.tar.gz
							</p>
							<p class="d2-ml-16 d2-mt-8">
								<d2-icon name="folder-o" /> C
							</p>
							<p class="d2-ml-32 d2-mt-8">
								<d2-icon name="file-code-o" />
								POTCAR
							</p>
							<p class="d2-ml-32 d2-mt-8">
								<d2-icon name="file-code-o" />
								PSCTR
							</p>
						</el-col>
						<el-col :span="12">
							<p>
								<d2-icon name="file-archive-o" />
								oncv_1.2.tar.gz
							</p>
							<p class="d2-ml-16 d2-mt-8">
								<d2-icon name="file-code-o" />
								C_ONCV_PBE-1.2.upf
							</p>
							<p class="d2-ml-16 d2-mt-8">
								<d2-icon name="file-code-o" />
								Ca_ONCV_PBE-1.2.upf
							</p>
						</el-col>
					</el-row>
				</el-card>
			</el-col>
		</el-row>
		<span slot="footer" class="dialog-footer">
			<el-button @click="canceled">Cancel</el-button>
			<el-button
				type="primary"
				@click="newPotentialsSubmit('form')"
				v-loading.fullscreen.lock="fullscreenLoading"
				plain
				>Submit</el-button
			>
		</span>
	</div>
</template>

<script>
// TODO: Add same codes for mutiple computers at once
import util from "@/libs/util";
import validate from "@/libs/util.validate";
import { errorCreate } from "@/api/tools";
const apiPrefix = "/potentials/";
export default {
	name: "PotentialsAdd",
	props: {},
	data() {
		let validateLabel = validate.is_clean_string_2;
		return {
			formLabelWidth: "200px",
			form: {
				label: "",
				description: "",
				type: ""
			},
			formRules: {
				label: [
					{
						required: true,
						validator: validateLabel,
						trigger: "blur"
					}
				],
				description: [{ required: true, trigger: "blur" }],
				type: [
					{
						required: true,
						validator: validateLabel,
						trigger: "blur"
					}
				]
			},
			potentials_types: [
				{
					label: "upf",
					value: "upf"
				},
				{
					label: "vaps-potcar",
					value: "potcar"
				}
			],
			fileList: [],
			fileData: [],
			acceptExts: [".zip", ".gz"],
			loading: true,
			fullscreenLoading: false
		};
	},
	methods: {
		canceled() {
			this.$emit("cancel");
		},
		uploadFile(event) {
			const file = event.file;
			const ext = file.name.substring(file.name.lastIndexOf("."));
			if (this.acceptExts.includes(ext)) {
				return this.fileData.append("file", file);
			}
			this.fullscreenLoading = false;
			errorCreate("ERROR: File type Not support");
		},
		newPotentialsSubmit(formName) {
			this.fullscreenLoading = true;
			this.$refs[formName].validate(valid => {
				if (valid) {
					this.fileData = new FormData();
					this.$refs.potentialsUpload.submit();
					this.fileData.append(
						"form",
						JSON.stringify(
							Object.assign(
								{ user: util.cookies.get("uuid") },
								this.form
							)
						)
					);

					this.$api.AddObj(apiPrefix, this.fileData).then(resp => {
						if (resp.code == 0) {
							this.$message.success("Upload Success!");
							this.$emit("success");
						}
						this.fullscreenLoading = false;
						this.canceled();
					});
				} else {
					this.$notify.error("Invaid Input");
					this.fullscreenLoading = false;
					return false;
				}
			});
		}
	},
	mounted() {
		this.loading = false;
	}
};
</script>
