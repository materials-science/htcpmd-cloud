<template>
	<d2-container
		class="calctasks-submit-index-container"
		v-loading.fullscreen.lock="loading_counter > 0"
	>
		<template slot="header">
			<el-row :gutter="20">
				<el-col :span="23">
					<el-menu
						mode="horizontal"
						unique-opened
						@select="selectPlugin"
						:default-active="default_active_menu"
					>
						<el-submenu
							v-for="(plugin, index) in plugins"
							:key="plugin.name"
							:index="`${index}#${plugin.name}`"
						>
							<template slot="title">
								{{ plugin.name }}
							</template>
							<el-menu-item-group>
								<span slot="title">Calculations</span>
								<el-menu-item
									v-for="(item, key) in plugin.calculations"
									:key="item.class_name"
									:index="`calculations#${key}`"
									:disabled="!item.available"
									>{{ key }}
									<span class="font-sup">{{
										item.class_name
									}}</span>
								</el-menu-item>
							</el-menu-item-group>
							<el-menu-item-group>
								<span slot="title">Workflows</span>
								<el-menu-item
									v-for="(item, key) in plugin.workflows"
									:key="item.class_name"
									:index="`workflows#${key}`"
									:disabled="!item.available"
									>{{ key }}
									<span class="font-sup">{{
										item.class_name
									}}</span>
								</el-menu-item>
							</el-menu-item-group>
						</el-submenu>
					</el-menu>
				</el-col>
				<el-col :span="1" class="d2-pr-12" style="line-height: 60px;">
					<router-link to="/cluster/plugins">
						<el-button
							plain
							icon="el-icon-info"
							style="border: none;"
						></el-button>
					</router-link>
				</el-col>
			</el-row>
		</template>
		<el-row class="content-container">
			<el-col :lg="12" :xl="12" class="d2-pr-16">
				<el-row class="flex-between-col">
					<!-- General Settings -->
					<el-col class="d2-mb-16" style="flex: 1;">
						<el-tabs
							type="border-card"
							class="no-box-shadow no-bg tab-container"
						>
							<el-tab-pane label="General">
								<el-row class="form-box">
									<h5>
										<span>CalcTask Label</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="Set a label for your task"
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-input
										v-model="calctask.label"
									></el-input>
								</el-row>
								<el-row class="form-box">
									<h5>
										<span>CalcTask Description</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="Description of your task"
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-input
										type="textarea"
										:autosize="{ minRows: 2 }"
										placeholder=""
										v-model="calctask.description"
										show-word-limit
									>
									</el-input>
								</el-row>
								<el-row class="form-box">
									<h5>
										<span>CalcTask Tag</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="Set a tag for your task"
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-input v-model="calctask.tag"></el-input>
								</el-row>
								<el-row class="form-box">
									<h5>
										<span>CalcTask Private</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="Set calctasks to private or public."
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-switch
										v-model="calctask.access_public"
										active-text="Public"
										inactive-text="Private"
									>
									</el-switch>
								</el-row>
							</el-tab-pane>
							<el-tab-pane label="Structures">
								<h5>
									<span>Structures</span>
									<el-tooltip
										class="d2-pl-16"
										effect="dark"
										content="Set Structures for your calculations"
										placement="bottom-start"
									>
										<el-button
											circle
											class="no-bg no-border"
										>
											<d2-icon
												name="question-circle-o"
											></d2-icon>
										</el-button>
									</el-tooltip>
								</h5>
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
											<el-table-column
												label="formula"
												align="center"
											>
												<template slot-scope="props">
													<span
														v-html="
															props.row.attributes
																.formula
														"
													></span>
												</template>
											</el-table-column>
											<el-table-column align="right">
												<template
													slot="header"
													slot-scope="scope"
												>
													<el-row
														type="flex"
														justify="space-around"
														style="align-items: center"
													>
														<el-col :span="20">
															<el-input
																v-model="
																	new_structure_id
																"
																placeholder="structure uuid"
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
																viewStructureDetail(
																	scope.row
																)
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
							</el-tab-pane>
							<el-tab-pane label="HPC">
								<el-row class="form-box">
									<h5>
										<span>Computer</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="Set a computer where your calculations submit to"
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-select
										v-model="calctask.computer"
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
								</el-row>
								<el-row class="form-box">
									<h5>
										<span>num_machines</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="The maximum number of machines (nodes) to use for the calculations."
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-input-number
										v-model="calctask.num_machines"
										:min="1"
										:precision="0"
									></el-input-number>
								</el-row>
								<el-row class="form-box">
									<h5>
										<span>num_mpiprocs_per_machine</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="The number of process per machine (node) to use for the calculations."
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-input-number
										v-model="
											calctask.num_mpiprocs_per_machine
										"
										:min="1"
										:precision="0"
									></el-input-number>
								</el-row>
								<el-row class="form-box">
									<h5>
										<span>max_wallclock_seconds</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="The maximum wallclock time in seconds to set for the calculations."
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-input-number
										v-model="calctask.max_wallclock_seconds"
										:min="1800"
										:precision="0"
									></el-input-number>
								</el-row>
								<el-row class="form-box">
									<h5>
										<span>PBS queue</span>
										<el-tooltip
											class="d2-pl-16"
											effect="dark"
											content="Set the queue if using PBS system."
											placement="bottom-end"
										>
											<el-button
												circle
												class="no-bg no-border"
											>
												<d2-icon
													name="question-circle-o"
												></d2-icon>
											</el-button>
										</el-tooltip>
									</h5>
									<el-input
										v-model="calctask.queue"
									></el-input>
								</el-row>
							</el-tab-pane>
						</el-tabs>
					</el-col>
					<!-- Plugin collapse -->
					<!-- <el-col class="border-card-like">
						<el-row
							@click.native="is_menu_collapse = !is_menu_collapse"
							type="flex"
							align="middle"
							:justify="
								is_menu_collapse ? 'center' : 'space-between'
							"
							class="d2-p-8 border-radius-2"
							style="cursor: pointer;"
						>
							<span
								v-show="!is_menu_collapse"
								class="font-small-title"
								>aiida plugins</span
							>
							<d2-icon
								:name="is_menu_collapse ? 'cogs' : 'caret-up'"
							></d2-icon>
						</el-row>
						<el-row v-show="!is_menu_collapse">
							<el-menu
								:collapse="is_menu_collapse"
								unique-opened
								@select="selectPlugin"
							>
								<el-submenu
									v-for="(plugin, index) in plugins"
									:key="plugin.name"
									:index="plugin.name"
								>
									<template slot="title">
										{{ plugin.name }}
									</template>
									<el-menu-item-group>
										<span slot="title">Calculations</span>
										<el-menu-item
											v-for="(item,
											key) in plugin.calculations"
											:key="item.class_name"
											:index="'calculations#' + key"
											:disabled="!item.available"
											>{{ key }}
											<span class="font-sup">{{
												item.class_name
											}}</span>
										</el-menu-item>
									</el-menu-item-group>
									<el-menu-item-group>
										<span slot="title">Workflows</span>
										<el-menu-item
											v-for="(item,
											key) in plugin.workflows"
											:key="item.class_name"
											:index="'workflows#' + key"
											:disabled="!item.available"
											>{{ key }}
											<span class="font-sup">{{
												item.class_name
											}}</span>
										</el-menu-item>
									</el-menu-item-group>
								</el-submenu>
							</el-menu>
						</el-row>
						<router-link
							v-show="!is_menu_collapse"
							to="/cluster/plugins"
						>
							<el-button
								plain
								icon="el-icon-info"
								style="border: none;"
								>About plugins</el-button
							>
						</router-link>
					</el-col> -->
				</el-row>
			</el-col>
			<el-col :lg="12" :xl="12" class="d2-pl-16">
				<component
					:is="plugin_selected_component"
					:calctask="calctask"
					:structures="structures"
					:type_selected="type_selected"
					:hash_name="plugin_hash_name"
				></component>
			</el-col>
		</el-row>
	</d2-container>
</template>

<script>
import util from "@/libs/util";
export default {
	name: "calctasks-submit-index",
	data() {
		return {
			loading_counter: 2,
			is_menu_collapse: false,
			default_active_menu: null,
			loading_plugins: true,
			plugins: [],
			plugin_selected_component: null,
			plugin_selected: {},
			plugin_hash_name: null,
			type_selected: {},
			calctask: {
				label: null,
				description: null,
				tag: null,
				computer: null,
				num_machines: 1,
				num_mpiprocs_per_machine: 1,
				queue: null,
				max_wallclock_seconds: 3600 * 24,
				user: util.cookies.get("uuid"),
				access_public: false
			},
			// computers
			loading_computers_list: false,
			computers_list: [],
			computers_selected: "",
			// structures
			loading_structures: true,
			structures: [],
			new_structure_id: ""
		};
	},
	methods: {
		// Change selected plugin according to url hash
		updatePluginRoute(hash) {
			if (hash !== "" && hash !== "/") {
				try {
					let [plugin, entry, type] = hash.slice(1).split("/");
					console.log(hash, plugin, entry, type);
					if (
						plugin !== "" &&
						["workflows", "calculations"].includes(entry) &&
						type !== ""
					) {
						let index = this.plugins.findIndex(item => {
							return item.name === plugin;
						});
						this.default_active_menu = `${entry}#${type}`;
						this.updateSelectedPlugin(this.default_active_menu, [
							index + "#" + plugin,
							this.default_active_menu
						]);
					}
				} catch (err) {
					this.$router.replace("/calctasks/submit");
				}
			} else {
				this.plugin_selected_component = null;
				this.plugin_selected = {};
				this.type_selected = {};
			}
		},
		// update current selected plugin information
		updateSelectedPlugin(key, key_path) {
			this.plugin_selected = this.plugins[key_path[0].split("#")[0]];
			this.type_selected = this.plugin_selected[key.split("#")[0]][
				key.split("#")[1]
			];
			this.plugin_selected_component = require(`./components/${this.plugin_selected.name}/${this.type_selected.class_name}.vue`).default;
			this.plugin_hash_name = `#/${this.plugin_selected.name}/${
				key.split("#")[0]
			}/${key.split("#")[1]}`;

			this.$store.dispatch("d2admin/page/update", {
				tagName: this.$route.fullPath,
				title: `Calctask-${this.plugin_selected.name}`
			});
		},
		/* el-menu method
		 * update current selected plugin and url after user selecting
		 */
		selectPlugin(key, key_path) {
			this.updateSelectedPlugin(key, key_path);
			this.$router.push(this.plugin_hash_name);
		},
		deleteRow(index, rows) {
			rows.splice(index, 1);
		},
		viewStructureDetail(row, column, event) {
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
		async loadStructures() {
			const db = await this.$store.dispatch("d2admin/db/database", {
				user: true
			});
			// TODO: Remove selected structures stored in local database
			this.structures = db.get("selectedStructures").value() || [];
			this.loading_structures = false;
			this.loading_counter -= 1;
		},
		async saveStructures(structures) {
			const db = await this.$store.dispatch("d2admin/db/database", {
				user: true
			});
			db.set("selectedStructures", structures).write();
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
		}
	},
	mounted() {
		let hash = this.$route.hash.slice(1);
		this.$api.GetList(`/aiida_plugins/`).then(resp => {
			if (resp.code == 0) {
				this.plugins = resp.data;
				this.updatePluginRoute(hash);
			}
			this.loading_plugins = false;
			this.loading_counter -= 1;
		});
		this.loadStructures();
	},
	watch: {
		$route(to, from) {
			if (this.$route.name == "calctasks-submit-index") {
				let hash = this.$route.hash;
				if (hash !== "") {
					this.updatePluginRoute(hash.slice(1));
				} else {
					this.$router.replace(this.plugin_hash_name);
					this.loading_counter += 1;
					this.loadStructures();
				}
			}
		}
	}
	// // This will check the hash and redirect to `/#/` if no hash tag
	// beforeRouteEnter(to, from, next) {
	// 	to.hash.slice(1) === ""
	// 		? next(instance =>
	// 				instance.$router.replace({ path: `${to.fullPath}/#/` })
	// 		  )
	// 		: next();
	// },
	// // This will call `updatePluginRoute` when url updates
	// beforeRouteUpdate(to, from, next) {
	// 	this.updatePluginRoute(to.hash.slice(1));
	// 	next();
	// }
};
</script>

<style lang="scss">
.calctasks-submit-index-container {
	.d2-container-full__header {
		padding: initial !important;
	}
	.el-menu-item-group__title {
		padding-left: 0 !important;
	}
	.content-container.el-row {
		height: 100%;
		> .el-col {
			height: 100%;
			overflow: auto;
			@include scrollBar();
		}
		.el-menu {
			border: none;
		}
		.form-box {
			> h5,
			> label > h5 {
				padding: 4px 0;
				@extend %flex-row;
				align-items: center;
				justify-content: space-between;
			}
			> label {
				width: 100%;
				padding: initial;
			}
		}
	}
	.tab-container {
		.el-tabs__header {
			background: rgba($color: #fff, $alpha: 0.8);
		}
	}
}
</style>
