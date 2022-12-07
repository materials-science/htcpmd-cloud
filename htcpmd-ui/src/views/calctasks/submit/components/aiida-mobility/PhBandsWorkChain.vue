<template>
	<el-container
		class="full-container"
		v-loading.fullscreen.lock="loading_counter > 0"
	>
		<el-main>
			<el-tabs
				type="border-card"
				class="no-box-shadow no-bg tab-container"
			>
				<el-tab-pane
					:label="type_selected.class_name"
					style="height:100%; overflow: hidden;"
				>
					<el-row class="form-box">
						<h5>Description</h5>
						<p class="pre-wrap d2-p-8">
							{{ type_selected.description || "No Description." }}
						</p>
					</el-row>
					<el-row class="form-box">
						<h5>Helps</h5>
						<p class="pre-wrap d2-p-8">
							{{ type_selected.help || "No Help Docs." }}
						</p>
					</el-row>
					<el-row class="form-box">
						<h5>
							<span>Load Template</span>
							<el-tooltip
								class="d2-pl-16"
								effect="dark"
								content="Load parameters from a template"
								placement="bottom-end"
							>
								<el-button circle class="no-bg no-border">
									<d2-icon name="question-circle-o"></d2-icon>
								</el-button>
							</el-tooltip>
						</h5>
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
					</el-row>
					<el-row class="form-box">
						<h5>
							<span>PW Code</span>
							<el-tooltip
								class="d2-pl-16"
								effect="dark"
								content="PW code."
								placement="bottom-end"
							>
								<el-button circle class="no-bg no-border">
									<d2-icon name="question-circle-o"></d2-icon>
								</el-button>
							</el-tooltip>
						</h5>
						<el-select
							v-model="pw_settings.code"
							placeholder="pw"
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
					</el-row>
					<el-row class="form-box">
						<h5>
							<span>PH Code</span>
							<el-tooltip
								class="d2-pl-16"
								effect="dark"
								content="PH code."
								placement="bottom-end"
							>
								<el-button circle class="no-bg no-border">
									<d2-icon name="question-circle-o"></d2-icon>
								</el-button>
							</el-tooltip>
						</h5>
						<el-select
							v-model="ph_settings.code"
							placeholder="ph"
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
					</el-row>
					<el-row class="form-box">
						<h5>
							<span>Q2R Code</span>
							<el-tooltip
								class="d2-pl-16"
								effect="dark"
								content="q2r code."
								placement="bottom-end"
							>
								<el-button circle class="no-bg no-border">
									<d2-icon name="question-circle-o"></d2-icon>
								</el-button>
							</el-tooltip>
						</h5>
						<el-select
							v-model="q2r_settings.code"
							placeholder="q2r"
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
					</el-row>
					<el-row class="form-box">
						<h5>
							<span>Matdyn Code</span>
							<el-tooltip
								class="d2-pl-16"
								effect="dark"
								content="matdyn code."
								placement="bottom-end"
							>
								<el-button circle class="no-bg no-border">
									<d2-icon name="question-circle-o"></d2-icon>
								</el-button>
							</el-tooltip>
						</h5>
						<el-select
							v-model="matdyn_settings.code"
							placeholder="q2r"
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
					</el-row>
				</el-tab-pane>
				<el-tab-pane label="Control Flags">
					<el-form label-position="top">
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>system_2d</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Set mesh to 2D mesh according to cell lengths."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-switch
								v-model="control_settings.system_2d"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>run_relax</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Whether to run relax before scf."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-switch
								v-model="control_settings.run_relax"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>vc_relax mode</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Whether to run relax in `vc-relax` mode, or in `relax` mode."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-switch
								v-model="control_settings.vc_relax"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>spin orbit coupling</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Whether to run for spin orbit coupling."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-switch
								v-model="control_settings.soc"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>max_restart_iterations</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="The maximum restart times from scratch relax."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input-number
								v-model="
									control_settings.max_restart_iterations
								"
								:min="1"
							></el-input-number>
						</el-form-item>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="Parameters Protocol">
					<el-form
						label-position="top"
						:rules="protocol_settings_rules"
						:model="protocol_settings"
					>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>pseudo_family</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Set the pseudo_family for your
									calculations. This will search from pseudo family data that you've uploaded before."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-select
								v-model="protocol_settings.pseudo_family"
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
										`${item.label} | ${item.type} | ${item.count}`
									"
									:value="item.uuid"
								>
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>Protocol</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Stored default parameters set for calculations."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-cascader
								:options="protocols_list"
								v-model="protocol_settings.protocol"
								@change="selectProtocol"
							>
								<template slot-scope="{ node, data }">
									<span>{{ data.label }}</span>
									<span v-if="!node.isLeaf">
										({{ data.children.length }})
									</span>
								</template>
							</el-cascader>
							<el-row
								v-show="!!protocol_settings.protocol"
								class="border-card-like d2-mt-8"
							>
								<el-button
									class="no-bg no-border d2-fr"
									icon="el-icon-view"
									@click="
										show_protocol_json = !show_protocol_json
									"
									>view protocol data</el-button
								>
								<tree-view
									v-show="show_protocol_json"
									:data="selected_protocol"
									:options="json_preview_options"
								/>
							</el-row>
						</el-form-item>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="PW Settings">
					<el-form label-position="top">
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>kpoints_mesh</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="The number of points in the kpoint mesh along each basis vector. If not set, `kpoints_distance` in protocol will be used."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<number-table
								:span="6"
								:data="pw_settings.kpoints_mesh"
								:precision="0"
							></number-table>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>ecutwfc and dual</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="[ecutrho] is dual * ecutwfc. If not set, recommended ecutwfc will get from protocol pseudo data."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<number-table
								:span="4"
								:data="pw_settings.cutoffs"
								:precision="0"
							></number-table>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span
									>Extra Parameters for pw program
									<span class="color-text-sub"
										>(json format)</span
									></span
								>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Override parameters in protocol by specifying the key and value of parameter. Give in json format."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input
								type="textarea"
								:autosize="{ minRows: 2 }"
								placeholder='{"name": "jack"}'
								v-model="pw_settings.parameters"
								show-word-limit
								@blur="previewPatametersInJson('pw')"
								v-show="!json_preview_config.show_pw_json"
							>
							</el-input>
							<el-row
								@click.native="
									json_preview_config.show_pw_json = false
								"
								v-show="json_preview_config.show_pw_json"
								class="border-card-like"
							>
								<tree-view
									:data="json_preview_config.pw_json"
									:options="json_preview_options"
								/>
							</el-row>
						</el-form-item>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="PH Settings">
					<el-form label-position="top">
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>epsil</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="whether to set calculation mode of the first qpoint to epsil."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-switch
								v-model="ph_settings.epsil"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>tr2_ph</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="tr2_ph, default is 1.0e-15"
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input v-model="ph_settings.tr2_ph"></el-input>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>qpoints_mesh</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="The number of points in the kpoint mesh along each basis vector. If not set, `qpoints_distance` will be used."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<number-table
								:span="6"
								:data="ph_settings.qpoints_mesh"
								:precision="0"
							></number-table>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>qpoints_distance</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="The minimal distance between k-points in reciprocal space in inverse Ångström."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input-number
								v-model="ph_settings.qpoints_distance"
								:min="0.001"
							></el-input-number>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>check_imaginary_frequencies</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Whether to check imaginary frequencies, if it is True, the Calculation will throw an error when imaginary frequencies are found."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-switch
								v-model="
									ph_settings.check_imaginary_frequencies
								"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item
							class="form-box"
							v-show="ph_settings.check_imaginary_frequencies"
						>
							<h5 slot="label">
								<span>frequency_threshold</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="tolerable frequency threshold for checking imaginray frequencies."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input-number
								v-model="ph_settings.frequency_threshold"
							></el-input-number>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>separated_qpoints</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Set true if you want to calculate each qpoint separately."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-switch
								v-model="ph_settings.separated_qpoints"
								active-text="True"
								inactive-text="False"
							>
							</el-switch>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span
									>Extra Parameters for ph program
									<span class="color-text-sub"
										>(json format)</span
									></span
								>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Extra parameters specifying the key and value of parameter. Give in json format."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input
								type="textarea"
								:autosize="{ minRows: 2 }"
								placeholder='{"name": "jack"}'
								v-model="ph_settings.parameters"
								show-word-limit
								@blur="previewPatametersInJson('ph')"
								v-show="!json_preview_config.show_ph_json"
							>
							</el-input>
							<el-row
								@click.native="
									json_preview_config.show_ph_json = false
								"
								v-show="json_preview_config.show_ph_json"
								class="border-card-like"
							>
								<tree-view
									:data="json_preview_config.ph_json"
									:options="json_preview_options"
								/>
							</el-row>
						</el-form-item>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="Q2R Settings">
					<el-form label-position="top">
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>zasr</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Indicates the type of Acoustic Sum Rule imposed."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-select
								v-model="q2r_settings.zasr"
								placeholder="Select"
							>
								<el-option
									v-for="value in [
										'crystal',
										'no',
										'simple',
										'one-dim',
										'zero-dim'
									]"
									:key="value"
									:label="value"
									:value="value"
								>
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span
									>Extra Parameters for q2r program
									<span class="color-text-sub"
										>(json format)</span
									></span
								>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Extra parameters by specifying the key and value of parameter. Give in json format."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input
								type="textarea"
								:autosize="{ minRows: 2 }"
								placeholder='{"age": 20}'
								v-model="q2r_settings.parameters"
								show-word-limit
								@blur="previewPatametersInJson('q2r')"
								v-show="!json_preview_config.show_q2r_json"
							>
							</el-input>
							<el-row
								@click.native="
									json_preview_config.show_q2r_json = false
								"
								v-show="json_preview_config.show_q2r_json"
								class="border-card-like"
							>
								<tree-view
									:data="json_preview_config.q2r_json"
									:options="json_preview_options"
								/>
							</el-row>
						</el-form-item>
					</el-form>
				</el-tab-pane>
				<el-tab-pane label="Dynmat Settings">
					<el-form label-position="top">
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>asr</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									placement="bottom-end"
								>
									<p slot="content" class="pre-wrap">
										{{
											`Indicates the type of Acoustic Sum Rule
								imposed.\n\n Note that in certain cases, not all
								the rotational asr can be applied (e.g. if there
								are only 2 atoms in a molecule or if all the
								atoms are aligned, etc.). \n\n In these cases the
								supplementary asr are canceled during the
								orthonormalization procedure. \n\n Finally, in all
								cases except 'no' a simple correction on the
								effective charges is performed (same as in the
								previous implementation).`
										}}
									</p>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-select
								v-model="matdyn_settings.asr"
								placeholder="Select"
							>
								<el-option
									v-for="value in [
										'crystal',
										'no',
										'simple',
										'one-dim',
										'zero-dim'
									]"
									:key="value"
									:label="value"
									:value="value"
								>
								</el-option>
							</el-select>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span
									>Extra Parameters for matdyn program
									<span class="color-text-sub"
										>(json format)</span
									></span
								>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="Extra parameters by specifying the key and value of parameter. Give in json format."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input
								type="textarea"
								:autosize="{ minRows: 2 }"
								placeholder='{"age": 20}'
								v-model="matdyn_settings.parameters"
								show-word-limit
								@blur="previewPatametersInJson('matdyn')"
								v-show="!json_preview_config.show_matdyn_json"
							>
							</el-input>
							<el-row
								@click.native="
									json_preview_config.show_matdyn_json = false
								"
								v-show="json_preview_config.show_matdyn_json"
								class="border-card-like"
							>
								<tree-view
									:data="json_preview_config.matdyn_json"
									:options="json_preview_options"
								/>
							</el-row>
						</el-form-item>
						<el-form-item class="form-box">
							<h5 slot="label">
								<span>matdyn_distance</span>
								<el-tooltip
									class="d2-pl-16"
									effect="dark"
									content="kpoint distance to get kpoints, if not set, `kpoints_distance_for_bands` in protocol will be used."
									placement="bottom-end"
								>
									<el-button circle class="no-bg no-border">
										<d2-icon
											name="question-circle-o"
										></d2-icon>
									</el-button>
								</el-tooltip>
							</h5>
							<el-input-number
								v-model="matdyn_settings.matdyn_distance"
							></el-input-number>
						</el-form-item>
					</el-form>
				</el-tab-pane>

				<el-dialog
					title="Template"
					:visible.sync="template_dialog_visible"
				>
					<el-form label-position="left">
						<el-form-item label="Template Label">
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
			</el-tabs>
		</el-main>
		<el-footer class="d2-text-center">
			<el-row type="flex" align="center" justify="space-around">
				<el-col class="form-box">
					<el-button
						plain
						type="primary"
						@click="template_dialog_visible = true"
						>Save as a Template</el-button
					>
				</el-col>
				<el-col class="form-box">
					<el-button plain type="primary" @click="taskSubmit"
						>Submit</el-button
					>
				</el-col>
			</el-row>
		</el-footer>
	</el-container>
</template>

<script>
import NumberTable from "@/components/number-table";
export default {
	name: "PhBandsWorkChain",
	components: {
		NumberTable
	},
	props: {
		type_selected: { require: true },
		calctask: { required: true },
		structures: { required: true },
		hash_name: { required: true }
	},
	data() {
		return {
			loading_counter: 2,
			loading_protocol: true,
			protocols_list: [],
			protocols_data: {},
			show_protocol_json: false,
			selected_protocol: {},
			loading_pseudo_family_list: false,
			pseudo_family_list: [],
			loading_codes_list: false,
			codes_list: [],
			protocol_settings: {
				protocol: null,
				pseudo_family: null
			},
			protocol_settings_rules: {
				protocol: [{ required: true, trigger: "blur" }],
				pseudo_family: [{ required: true, trigger: "blur" }]
			},
			pw_settings: {
				parameters: null,
				code: null,
				kpoints_mesh: [null, null, null],
				cutoffs: [null, null]
			},
			ph_settings: {
				parameters: null,
				code: null,
				epsil: false,
				tr2_ph: 1e-15,
				qpoints_mesh: [null, null, null],
				qpoints_distance: 0.5,
				check_imaginary_frequencies: false,
				frequency_threshold: -20,
				separated_qpoints: false
			},
			q2r_settings: {
				parameters: null,
				code: null,
				zasr: "crystal"
			},
			matdyn_settings: {
				parameters: null,
				code: null,
				asr: "crystal",
				matdyn_distance: null
			},
			control_settings: {
				system_2d: false,
				run_relax: false,
				vc_relax: false,
				max_restart_iterations: 1,
				soc: false
			},
			json_preview_options: {
				maxDepth: 10,
				rootObjectKey: "parameters preview",
				modifiable: false
			},
			json_preview_config: {},
			params_templates_list: [],
			params_templates_selected: null,
			template_dialog_visible: false,
			new_template_name: null
		};
	},
	methods: {
		previewPatametersInJson(type) {
			try {
				let data = JSON.parse(this[`${type}_settings`].parameters);
				this.$set(this.json_preview_config, `${type}_json`, data);
				Object.keys(data).length > 0 &&
					this.$set(
						this.json_preview_config,
						`show_${type}_json`,
						true
					);
			} catch (err) {
				console.error(err);
			}
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
		selectProtocol(value) {
			try {
				let protocol = this.protocols_data[value[0]]["parameters"][
					value[1]
				];
				this.selected_protocol = protocol;
			} catch (err) {
				this.$message.error(
					`No available protocl data found. [Error: ${err}]`
				);
			}
		},
		getCodesList(query) {
			if (query == "") {
				return (this.codes_list = []);
			}
			if (
				this.calctask.computer == "" ||
				this.calctask.computer == null
			) {
				return this.$message.info("Please select a computer first!");
			}
			this.loading_codes_list = true;
			this.$api
				.GetList("/codes/", {
					label: query,
					uuid: query,
					computer: this.calctask.computer
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
		// template
		async loadTemplates() {
			// load from local storage;
			// TODO: `computers`, `codes` only have uuid but no name.
			const db = await this.$store.dispatch("d2admin/db/database", {
				// user: true
			});
			let templates =
				// db.get(`templates${this.hash_name}`).value() || null;
				db.get(`templates`).value() || null;
			console.log(templates);
			if (templates != null) {
				this.params_templates_list = templates;
			}
			this.loading_counter -= 1;
		},
		paramsTemplatesChange(val) {
			let template = this.params_templates_list[val].template;
			// TODO: assign won't overide all properties
			// Object.assign(this.inputsForm, util.deepcopy(template.inputs));
			for (let key in template) {
				this[key] = template[key];
			}
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
			let template = {
				protocols_list: this.protocols_list,
				protocols_data: this.protocols_data,
				selected_protocol: this.selected_protocol,
				protocol_settings: this.protocol_settings,
				pw_settings: this.pw_settings,
				ph_settings: this.ph_settings,
				q2r_settings: this.q2r_settings,
				matdyn_settings: this.matdyn_settings,
				control_settings: this.control_settings
			};
			if (index == -1) {
				this.params_templates_list.push({
					label: this.new_template_name,
					template: template
				});
			} else {
				this.params_templates_list[index].template = template;
			}
			const db = await this.$store.dispatch("d2admin/db/database", {
				// user: true
			});
			// db.set(
			// 	`templates${this.hash_name}`,
			// 	this.params_templates_list
			// ).write();
			db.set("templates", this.params_templates_list).write();
			this.template_dialog_visible = false;
		},
		// submit
		paramsCheck() {
			let form = {};
			let code_types = ["pw", "ph", "q2r", "matdyn"];

			// structures check
			if (this.structures.length === 0) {
				return this.$message.error("Please specify structures.");
			}

			// codes check
			if (
				!code_types.every(type => {
					return this[`${type}_settings`].code !== null;
				})
			) {
				return this.$message.error("Please specify codes.");
			}

			// protocol check
			if (
				Object.keys(this.protocol_settings).every(key => {
					return this.protocol_settings[key] !== null;
				})
			) {
				form.protocol_settings = Object.assign(
					{},
					this.protocol_settings
				);
			} else {
				return this.$message.error("Please check Protocol settings.");
			}

			// pw_settings check
			form.pw_settings = Object.assign({}, this.pw_settings);
			if (
				form.pw_settings.cutoffs.some(item => {
					return item === 0 || item === null;
				})
			) {
				delete form.pw_settings.cutoffs;
			}
			if (
				form.pw_settings.kpoints_mesh.some(item => {
					return item === 0 || item === null;
				})
			) {
				delete form.pw_settings.kpoints_mesh;
			}

			// ph_settings check
			form.ph_settings = Object.assign({}, this.ph_settings);
			if (
				form.ph_settings.qpoints_mesh.some(item => {
					return item === 0 || item === null;
				})
			) {
				delete form.ph_settings.qpoints_mesh;
			}
			if (
				form.ph_settings.qpoints_distance <= 0 &&
				form.ph_settings.qpoints_mesh == undefined
			) {
				return this.$message.error(
					"Please set correct qpoints_distance or qpoints_mesh in PH settings."
				);
			}

			form.q2r_settings = Object.assign({}, this.q2r_settings);
			form.matdyn_settings = Object.assign({}, this.matdyn_settings);

			if (form.matdyn_settings.matdyn_distance <= 0) {
				delete form.matdyn_settings.matdyn_distance;
			}

			// extra parameters  check
			try {
				code_types.forEach(type => {
					let parameters = JSON.parse(
						form[`${type}_settings`].parameters
					);
					if (parameters === null) {
						delete form[`${type}_settings`].parameters;
					}
				});
			} catch (err) {
				return this.$message.error(
					"Extra parameters are not given in json format."
				);
			}

			form.control_settings = Object.assign({}, this.control_settings);
			return form;
		},
		taskSubmit() {
			// this.loading_counter += 1;
			let form = this.paramsCheck();
			Object.assign(form, this.calctask);
			form.structures = this.structures.map(s => {
				return s.uuid;
			});
			this.$api
				.AddObj(`/calctasks${this.hash_name.slice(1)}`, form)
				.then(resp => {
					console.log(resp);
					// this.loading_counter -= 1;
					this.$message.success("Submit successfully!");
				});
		}
	},
	mounted() {
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
					this.protocols_data = protocols_data;
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
		this.loadTemplates();
	}
};
</script>

<style></style>
