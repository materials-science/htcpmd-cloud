<template>
	<d2-container class="structures-import-container">
		<el-tabs type="border-card" class="tabs-container">
			<el-tab-pane label="Upload Structure Files">
				<!-- <template slot="header">区域划分</template> -->
				<el-row type="flex" :gutter="20" class="upload-wrap">
					<el-col :xl="8" :lg="12">
						<!-- upload box -->
						<el-upload
							class="upload-structure-box"
							drag
							multiple
							ref="structureUpload"
							:action="uploadUrl"
							:headers="uploadHeaders"
							:on-preview="handleFilePreview"
							:on-change="handleFileChange"
							:on-remove="handleFileRemove"
							:file-list="fileList"
							:auto-upload="false"
							:on-error="uploadError"
							:on-exceed="replaceCurrentFile"
						>
							<i class="el-icon-upload"></i>
							<div class="el-upload__text">
								Drag crystal structure file to here or<em>
									Click mouse</em
								>
							</div>
							<div class="el-upload__tip" slot="tip">
								Support <strong>POSCAR</strong>,
								<strong>CIF</strong> etc.
							</div>
						</el-upload>
						<el-row class="d2-pl-30">
							<el-button
								type="warning"
								plain
								@click="clearFileList"
								>Clear Current Files List</el-button
							>
							<el-button
								type="primary"
								plain
								@click="uploadSubmit"
								>Submit To Server</el-button
							>
						</el-row>
					</el-col>
					<el-col :xl="16" :lg="12" hidden-sm-and-down>
						<div class="upload-structure-box">
							<div class="structure-viewer"></div>
							<div class="tip"></div>
						</div>
						<!-- <div
								style="
									width: 100%;
									height: 100%;
									display: flex;
									align-items: center;
									justify-content: space-around;
									padding: 0 20px;
								"
							>
								<el-button
									type="warning"
									plain
									style="width: 100%"
									@click="clearFileList"
									>Clear Current Files List</el-button
								>
								<el-button
									type="primary"
									plain
									style="width: 100%"
									@click="uploadSubmit"
									>Submit To Server</el-button
								>
							</div> -->
					</el-col>
				</el-row>
			</el-tab-pane>
			<el-tab-pane label="Import Structure Data">
				<el-row :gutter="20" v-loading="import_structures_loading">
					<el-col :xl="6" :lg="8" :md="12">
						<h5 class="d2-pb-8">Import</h5>
						<el-form
							:model="import_structures_form"
							:rules="import_structures_form_rules"
							ref="import_structures_form"
							label-position="left"
						>
							<el-form-item
								label="tag name"
								prop="name"
								:label-width="formLabelWidth"
							>
								<el-input
									v-model="import_structures_form.name"
									autocomplete="off"
								></el-input>
							</el-form-item>
							<el-form-item
								label="tag description"
								prop="description"
								:label-width="formLabelWidth"
							>
								<el-input
									v-model="import_structures_form.description"
									autocomplete="off"
								></el-input>
							</el-form-item>
							<el-form-item>
								<el-upload
									class="upload-box"
									ref="import_structures_files"
									action=""
									:auto-upload="false"
									list-type="text"
									:file-list="import_structures_files_list"
									:http-request="uploadImportFiles"
									:accept="accept_import_exts.join()"
									:limit="1"
									:on-change="handleImportFileChange"
								>
									<el-button
										slot="trigger"
										size="small"
										type="primary"
										plain
										>choose files</el-button
									>
									<div slot="tip" class="el-upload__tip">
										File(zip, tar.gz)
									</div>
								</el-upload>
							</el-form-item>
							<el-form-item>
								<el-button
									type="primary"
									@click="
										importStructuresFormSubmit(
											'import_structures_form'
										)
									"
									plain
									>Submit</el-button
								>
							</el-form-item>
						</el-form>
					</el-col>
					<el-col :xl="8" :lg="10" :md="12">
						<h5 class="d2-pb-8">Tips</h5>
						<el-collapse :value="['1', '2']">
							<el-collapse-item title="文件格式" name="1">
								<p>接受zip和tar.gz格式的压缩包</p>
							</el-collapse-item>
							<el-collapse-item
								title="压缩文件夹结构示例"
								name="2"
							>
								<el-row
									v-for="(item, index) in directory_structure"
									:key="item.name"
								>
									<p v-if="item.type == 'file'">
										<el-link
											type="primary"
											@click="showExampleFile(item.name)"
										>
											<d2-icon name="file-code-o" />
											{{ item.name }}
										</el-link>
									</p>
									<p v-else>
										<d2-icon name="folder-o" />
										{{ item.name }}
									</p>
									<p
										v-for="child in item.children"
										:key="child.name"
										class="d2-ml-16"
									>
										<el-link
											type="primary"
											@click="showExampleFile(child.name)"
										>
											<d2-icon name="file-code-o" />
											{{ child.name }}
										</el-link>
									</p>
								</el-row>
							</el-collapse-item>
						</el-collapse>
					</el-col>
				</el-row>
			</el-tab-pane>
			<el-dialog
				:title="showed_example_name"
				:visible.sync="example_dialog_visible"
			>
				<d2-highlight :code="showed_example_content" lang="json" />
				<span slot="footer" class="dialog-footer">
					<el-button
						type="primary"
						@click="example_dialog_visible = false"
						>close</el-button
					>
				</span>
			</el-dialog>
		</el-tabs>
	</d2-container>
</template>

<script>
import $ from "jquery";
import $3Dmol from "@/libs/3Dmol-nojquery.js";
import util from "@/libs/util";
import ExampleData from "@/assets/data/structuresImportExamples.json";
// import "@/libs/3Dmol-nojquery.js";
let viewer = null;
let viewer_id = ".upload-structure-box .structure-viewer";
const viewer_config = {
	backgroundColor: "white",
	id: "upload-viewer-canvas"
};
export default {
	name: "structures-import",
	data() {
		return {
			formLabelWidth: "200px",
			fileList: [],
			uploadUrl: "/api/structures/files/",
			uploadHeaders: {
				Authorization: `Token ${util.cookies.get("token")}`
			},
			accept_import_exts: [".zip", ".gz"],
			import_structures_files_list: [],
			import_structures_form: {
				name: "",
				description: ""
			},
			import_structures_form_rules: {
				name: [{ required: true, trigger: "blur" }],
				description: [{ required: true, trigger: "blur" }]
			},
			directory_structure: [
				{ name: "structures.json", type: "file" },
				{
					name: "structures",
					type: "folder",
					children: [
						{ name: "Ag2Br2-8d1e37cd.json", type: "file" },
						{ name: "Ag2Br2-8d1e37cd.xsf", type: "file" }
					]
				},
				{
					name: "bands",
					type: "folder",
					children: [
						{ name: "Ag2Br2-7fbb633d_data.json", type: "file" }
					]
				},
				{
					name: "phonons",
					type: "folder",
					children: [
						{ name: "Ag2Br2-c0931d70_data.json", type: "file" }
					]
				}
			],
			showed_example_name: null,
			example_dialog_visible: false,
			import_structures_files_list: [],
			import_structures_loading: false
		};
	},
	computed: {
		imported_examples_data() {
			return ExampleData;
		},
		showed_example_content() {
			if (this.showed_example_name == null) {
				return "";
			}
			let ext = this.showed_example_name.split(".").pop();
			try {
				let content = this.imported_examples_data[
					this.showed_example_name
				];
				if (ext.toLowerCase() == "json") {
					return JSON.stringify(content, null, "\b");
				} else {
					return content;
				}
			} catch (err) {
				this.$message.error("errors in loading examples.");
				this.$log.push(err);
				return "";
			}
		}
	},
	mounted() {
		viewer_id = $(viewer_id);
		// 加载完成后显示提示
		if ($3Dmol) {
			this.showInfo("Tip", "3Dmol loaded!");
			viewer = $3Dmol.createViewer(viewer_id, viewer_config);
			viewer.setBackgroundColor(0xffffffff, 0);
		}
	},
	methods: {
		// 显示提示
		showInfo(title, message) {
			this.$notify({
				title: title,
				message: message
			});
		},
		replaceCurrentFile(files, fileList) {},
		handleFileChange(file, fileList) {
			// this.showStructureViewer(file);
		},
		showStructureViewer(file) {
			viewer.clear();
			const reader = new FileReader();
			// const viewer = this.viewer;
			reader.readAsText(file.raw, "UTF-8");
			reader.onload = event => {
				// There is a plugin to guess type of file
				let name = file.name.toLowerCase();
				let ext_name = "";
				if (name.indexOf(".") != -1) {
					ext_name = name.split(".").pop();
				} else {
					switch (name) {
						case "poscar": {
							ext_name = "vasp";
							break;
						}
					}
				}
				let fileString = event.target.result;
				let m = viewer.addModel(fileString, ext_name, {
					duplicateAssemblyAtoms: true,
					doAssembly: true
				});
				viewer.setHoverable(
					{},
					true,
					function(atom, viewer, event, container) {
						if (!atom.label) {
							atom.label = viewer.addLabel(atom.elem, {
								position: atom,
								backgroundColor: "darkgreen",
								backgroundOpacity: 0.5,
								fontColor: "white"
							});
						}
					},
					function(atom) {
						if (atom.label) {
							viewer.removeLabel(atom.label);
							delete atom.label;
						}
					}
				);
				viewer.setStyle({
					stick: {
						radius: 0.1,
						colorscheme: "Jmol",
						opacity: 0.9,
						singleBonds: true
					},
					sphere: { radius: 0.6, colorscheme: "Jmol", opacity: 1 }
				});
				viewer.addUnitCell(m, {
					box: { color: "purple" }
				});
				viewer.replicateUnitCell(2, 2, 2);
				viewer.zoomTo();
				viewer.render();
			};
		},
		handleFileRemove(file, fileList) {
			// viewer.clear();
		},
		handleFilePreview(file) {
			this.showStructureViewer(file);
		},
		triggerSubmit(e) {
			$("#btn-submit").trigger("click");
		},
		uploadSubmit(e) {
			this.$refs.structureUpload.submit();
			viewer.clear();
		},
		clearFileList() {
			this.$refs.structureUpload.clearFiles();
			viewer.clear();
		},
		uploadError(err) {
			this.$message.error(`Uploading failed! ${err}`);
		},
		showExampleFile(name) {
			this.showed_example_name = name;
			this.example_dialog_visible = true;
		},
		handleImportFileChange(file, fileList) {
			if (
				![
					"application/x-gzip",
					"application/x-zip-compressed"
				].includes(file.raw.type)
			) {
				this.import_structures_files_list = [];
				this.$message.error("Only zip and tar.gz are accepted.");
			}
		},
		uploadImportFiles(event) {
			const file = event.file;
			return this.fileData.append("file", file);
		},
		importStructuresFormSubmit(formName) {
			this.import_structures_loading = true;
			this.$refs[formName].validate(valid => {
				if (valid) {
					this.fileData = new FormData();
					this.$refs.import_structures_files.submit();
					this.fileData.append(
						"form",
						JSON.stringify(
							Object.assign(
								{ user: util.cookies.get("uuid") },
								this.import_structures_form
							)
						)
					);

					this.$api
						.AddObj("/structures/import/", this.fileData)
						.then(resp => {
							if (resp.code == 0) {
								this.$message.success("Upload Success!");
							}
							this.$refs[formName].resetFields();
							this.import_structures_files_list = [];
							this.import_structures_loading = false;
						});
				} else {
					this.$notify.error("Invaid Input");
					this.import_structures_loading = false;
					return false;
				}
			});
		}
	}
};
</script>

<style lang="scss" scoped>
.upload-structure-box {
	height: 80%;
	max-height: 720px;
	margin: 8px;
	padding: 20px;
	text-align: center;
	.structure-viewer {
		width: 100%;
		height: 100%;
		position: relative;
		border: 1px dashed #d9d9d9;
	}
}
.tabs-container {
	height: 100%;
	overflow: hidden;
}
.upload-wrap {
	height: 100%;
}
</style>
<style lang="scss">
.structures-import-container {
	.tabs-container {
		.el-tabs__content {
			@include scrollBar(4px);
			overflow-y: auto;
			height: calc(100% - 40px);
			padding: 16px;
			#pane-0 {
				height: 100%;
			}
		}
	}
	.upload-structure-box {
		.el-upload.el-upload--text {
			width: 100%;
			height: 60%;
			max-height: 400px;
			flex: 1;
			.el-upload-dragger {
				width: initial;
				height: 100%;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
			}
		}
		.el-upload-list.el-upload-list--text {
			max-height: 40%;
			overflow: auto;
			text-align: left;
			li {
				background-color: rgba(33, 33, 33, 0.1);
				cursor: pointer;
				transition: background-color 0.3s;
			}
			li:hover {
				background-color: rgba(33, 33, 33, 0.3);
			}
		}
	}
	.material-viewer-container {
		canvas {
			padding: 8px !important;
		}
	}
}
</style>
