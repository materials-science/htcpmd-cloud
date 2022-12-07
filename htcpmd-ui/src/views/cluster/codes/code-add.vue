<template>
	<div v-loading="loading">
		<el-form
			:model="form"
			:rules="formRules"
			ref="form"
			label-position="left"
		>
			<el-form-item
				label="computer"
				prop="computer"
				:label-width="formLabelWidth"
			>
				<el-input v-model="form.computer" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item
				label="label"
				prop="label"
				:label-width="formLabelWidth"
			>
				<el-input v-model="form.label" autocomplete="off"></el-input>
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
				label="version"
				prop="version"
				:label-width="formLabelWidth"
			>
				<el-input v-model="form.version" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item
				label="input_plugin"
				prop="input_plugin"
				:label-width="formLabelWidth"
			>
				<el-cascader
					:options="plugins_list"
					clearable
					v-model="form.input_plugin"
					separator="."
					filterable
					@change="inputPluginChange"
				></el-cascader>
			</el-form-item>
			<el-form-item
				label="remote_exec_path"
				prop="remote_exec_path"
				:label-width="formLabelWidth"
			>
				<el-input
					v-model="form.remote_exec_path"
					autocomplete="off"
					placeholder="/bin/bash"
				></el-input>
			</el-form-item>
			<el-form-item
				label="prepend text"
				prop="prepend_text"
				:label-width="formLabelWidth"
			>
				<el-input
					v-model="form.prepend_text"
					autocomplete="off"
					placeholder="command before"
				></el-input>
			</el-form-item>
			<el-form-item
				label="append text"
				prop="append_text"
				:label-width="formLabelWidth"
			>
				<el-input
					v-model="form.append_text"
					autocomplete="off"
					placeholder="command after"
				></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="canceled">Cancel</el-button>
			<el-button
				type="primary"
				@click="newCodesSubmit('form')"
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
import { defaults } from "js-cookie";
const apiPrefix = "/codes/";
export default {
	name: "CodeAdd",
	props: {
		computer: {
			type: String,
			default() {
				return "";
			}
		}
	},
	data() {
		let validateLabel = validate.is_clean_string_2;
		return {
			formLabelWidth: "200px",
			form: {
				computer: "",
				label: "",
				description: "",
				remote_exec_path: "/bin/bash",
				input_plugin: "",
				prepend_text: "",
				append_text: "",
				version: "1.0"
			},
			formRules: {
				computer: [{ required: true, trigger: "blur" }],
				label: [
					{
						required: true,
						validator: validateLabel,
						trigger: "blur"
					}
				],
				remote_exec_path: [{ required: true, trigger: "blur" }],
				input_plugin: [{ required: true, trigger: "blur" }]
			},
			plugins_list: [],
			loading: true,
			fullscreenLoading: false
		};
	},
	methods: {
		canceled() {
			this.$emit("cancel");
		},
		inputPluginChange(val) {
			// TODO: Get code template from server end
			let plugin = val.join(".");
			switch (plugin) {
				case "phonopy.phonopy":
					this.form = Object.assign(this.form, {
						remote_exec_path: "/usr/local/bin/phonopy",
						prepend_text: "",
						append_text: ""
					});
					break;
				case "shengbte.shengbte":
					this.form = Object.assign(this.form, {
						remote_exec_path: "/workdir/shengbte/ShengBTE",
						prepend_text: "",
						append_text: ""
					});
					break;
				case "shengbte.thirdorder_reap":
					this.form = Object.assign(this.form, {
						remote_exec_path:
							"/workdir/thirdorder/thirdorder_vasp.py",
						prepend_text: "",
						append_text: ""
					});
					break;
				case "shengbte.thirdorder_sow":
					this.form = Object.assign(this.form, {
						remote_exec_path:
							"/workdir/thirdorder/thirdorder_vasp.py",
						prepend_text: "find job.* -name vasprun.xml|sort -n|"
					});
					break;
				default:
					this.form = Object.assign(this.form, {
						remote_exec_path: "",
						prepend_text: "",
						append_text: ""
					});
			}
		},
		newCodesSubmit(formName) {
			this.fullscreenLoading = true;
			this.$refs[formName].validate(valid => {
				if (valid) {
					let data = Object.assign({}, this.form);
					data.input_plugin = data.input_plugin.join(".");
					data.user = util.cookies.get("uuid");
					this.$api.AddObj(apiPrefix, data).then(resp => {
						if (resp.code == 0) {
							this.$message.success("Upload Success!");
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
		this.$api.GetObj("/entrypoints/", "calculations").then(resp => {
			let data = resp.data;
			let root = {};
			data.forEach(element => {
				let sp = element.split(".");
				let parent = root;
				sp.forEach((item, index) => {
					if (!parent.hasOwnProperty(item)) {
						parent[item] = {};
					}
					parent = parent[item];
				});
			});
			function iterator(dict, parent) {
				let children = [];
				for (let key in dict) {
					let current = {
						value: key,
						label: key
					};
					iterator(dict[key], current);
					children.push(current);
				}
				if (children.length > 0) {
					parent.children = children;
				}
			}
			let pack = {};
			iterator(root, pack);
			this.plugins_list = pack.children;
			this.loading = false;
		});
	},
	created() {
		this.form.computer = this.computer;
	}
};
</script>

<style></style>
