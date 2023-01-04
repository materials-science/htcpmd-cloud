<template>
  <d2-container type="full" v-loading.fullscreen.lock="fullscreenLoading" class="structure-detail-container">
    <el-tabs tab-position="top" class="tab-container" type="border-card" @tab-click="handleTabSwitch($event)">
      <el-tab-pane label="Material Detail" class="panel material-panel">
        <el-row type="flex" :gutter="20" v-loading.fullscreen.lock="update_tag_loading">
          <el-col :xl="12" class="material-detail">
            <!-- Title -->
            <el-row>
              <el-col :span="DetailSpan">
                <p class="material-detail-title">
                  Material Detail
                </p>
              </el-col>
            </el-row>
            <!-- UUID -->
            <el-row :gutter="20" type="flex" align="middle">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain"> UUID </el-tag>
                </div>
              </el-col>
              <el-col :span="(DetailSpan * 2) / 3">
                <div class="content">
                  {{ structure.uuid }}
                </div>
              </el-col>
            </el-row>
            <!-- formula -->
            <el-row :gutter="20" type="flex" align="middle">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain"> Formula </el-tag>
                </div>
              </el-col>
              <el-col :span="(DetailSpan * 2) / 3">
                <div class="content" v-html="structure.formula"></div>
              </el-col>
            </el-row>
            <!-- volume -->
            <el-row :gutter="20" type="flex" align="middle">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain"> volume </el-tag>
                </div>
              </el-col>
              <el-col :span="(DetailSpan * 2) / 3">
                <div class="content">
                  {{ structure.volume }}
                </div>
              </el-col>
            </el-row>
            <!-- spacegroup -->
            <el-row :gutter="20" type="flex" align="middle">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain"> spacegroup </el-tag>
                </div>
              </el-col>
              <el-col :span="(DetailSpan * 2) / 3">
                <div class="content">
                  {{ structure.spacegroup }}
                </div>
              </el-col>
            </el-row>
            <!-- cell -->
            <el-row :gutter="20">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain"> cell </el-tag>
                </div>
              </el-col>
              <!-- <el-col :span="(DetailSpan * 2) / 3"> -->
              <div class="content">
                <number-table :span="(DetailSpan * 2) / 3" :data="structure.cell" :precision="6"
                  disabled></number-table>
              </div>
            </el-row>
            <!-- reciprocal_cell -->
            <el-row :gutter="20">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain">
                    reciprocal cell
                  </el-tag>
                </div>
              </el-col>
              <number-table :span="(DetailSpan * 2) / 3" :data="structure.reciprocal_cell" :precision="6"
                disabled></number-table>
            </el-row>
            <!-- position -->
            <el-row :gutter="20" type="flex" align="middle">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain">
                    positions
                  </el-tag>
                </div>
              </el-col>
              <number-table :span="(DetailSpan * 2) / 3" :data="structure.positions" :precision="6"
                disabled></number-table>
            </el-row>
            <!-- createTime -->
            <el-row :gutter="20" type="flex" align="middle">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain">
                    created time
                  </el-tag>
                </div>
              </el-col>
              <el-col :span="(DetailSpan * 2) / 3">
                <div class="content">
                  <el-date-picker v-model="structure.createTime" type="datetime" readonly class="detail-date-picker">
                  </el-date-picker>
                </div>
              </el-col>
            </el-row>
            <!-- updateTime -->
            <el-row :gutter="20" type="flex" align="middle">
              <el-col :span="DetailSpan / 3">
                <div class="label">
                  <el-tag effect="plain">
                    last modified
                  </el-tag>
                </div>
              </el-col>
              <el-col :span="(DetailSpan * 2) / 3">
                <div class="content">
                  <el-date-picker v-model="structure.updateTime" type="datetime" readonly class="detail-date-picker">
                  </el-date-picker>
                </div>
              </el-col>
            </el-row>
            <!-- Tags -->
            <structure-tags :tags="structure.tags"></structure-tags>
          </el-col>
          <el-col :xl="12" class="material-viewer-container hidden-sm-and-down" v-loading="viewerLoading">
            <el-row>
              <el-col :span="DetailSpan">
                <p class="viewer-title">Crystal Structure</p>
              </el-col>
            </el-row>
            <div class="structure-viewer flex-center-col" :id="`detail-viewer-${uuid}`">
              <div class="structure-viewer-cover" v-if="viewerCoverTip" :style="{
  'background-image': structure.coverImg
    ? `url('${structure.coverImg}')`
    : '',
  'background-position': 'center',
  'background-blend-mode': 'darken'
}"></div>
              <p class="cover-tip" v-if="viewerCoverTip" @dblclick="displayViewer">
                Double click to show
              </p>
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="Band Structure" class="panel band-panel">
        <el-row :gutter="20" justify="space-around" v-loading="loadingBandData">
          <el-col class="chart-box" :xl="16" :md="16">
            <v-chart v-if="
  bandChartOption &&
  currentTab == 'Band Structure'
" class="chart" :option="bandChartOption" ref="bandChart" />
            <el-row v-if="!bandChartOption" class="cover flex-center-col">
              <img src="@/assets/images/phonon_bg.png" />
              <p class="cover-tip">
                No Data
              </p>
            </el-row>
          </el-col>
          <el-col class="button-box" :xl="8" :md="8">
            <el-row class="d2-mb-20 color-text-normal">
              <h3>Data Info</h3>
            </el-row>
            <el-row class="data-info" v-if="diagrams.band.data">
              <!-- Update Date -->
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      Update Date
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    {{ diagrams.band.date }}
                  </div>
                </el-col>
              </el-row>
              <!-- Update By -->
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      Update By
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    <router-link to="/user">
                      <el-link type="primary">UUID
                        {{ diagrams.band.createBy }}
                      </el-link>
                    </router-link>
                  </div>
                </el-col>
              </el-row>
            </el-row>
            <el-row>
              <el-button type="primary" plain @click="exportChartData('band', 'image')">Export
                Image</el-button>
              <el-button type="primary" plain @click="exportChartData('band', 'file')">Download
                Data</el-button>
            </el-row>
            <el-row class="cover">
              <el-upload class="upload-box" action="" drag ref="uploadBand" :auto-upload="false"
                :file-list="bandFileList" :on-preview="bandPreview" :on-change="bandFileChange" :multiple="false"
                :limit="1">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  Drag here, or <em>click to upload</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                  Mind the format of your file, tap file name
                  to preview.
                </div>
              </el-upload>
            </el-row>
            <el-row>
              <el-button type="success" plain @click="submitBandUpload">Upload</el-button>
            </el-row>
            <!-- supplement -->
            <el-row class="d2-mb-20 color-text-normal">
              <h3>Data Supplement</h3>
            </el-row>
            <el-row class="data-info" v-if="diagrams.band.supplement">
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      File
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    <el-link type="primary" :href="
  diagrams.band.supplement.filepath
">
                      {{ diagrams.band.supplement.name }}
                    </el-link>
                  </div>
                </el-col>
              </el-row>
            </el-row>
            <el-row>
              <el-row class="cover">
                <el-upload class="upload-box" action="" drag ref="uploadBandSupplement" :auto-upload="false"
                  :file-list="bandFileSupllement" :http-request="bandFileSupllementUpload" :multiple="false" :limit="1">
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">
                    Drag here, or <em>click to upload</em>
                  </div>
                  <div class="el-upload__tip" slot="tip">
                    Mind the format of your file, tap file
                    name to preview.
                  </div>
                </el-upload>
              </el-row>
              <el-row>
                <el-button type="success" plain @click="submitBandSupplementUpload">Upload
                  supplement</el-button>
              </el-row>
            </el-row>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="Phonon Dispersion" class="panel phonon-panel">
        <el-row :gutter="20" justify="space-around" v-loading="loadingPhononData">
          <el-col class="chart-box" :xl="16" :md="16">
            <v-chart v-if="
  phononChartOption &&
  currentTab == 'Phonon Dispersion'
" class="chart" :option="phononChartOption" ref="phononChart" />
            <el-row v-if="!phononChartOption" class="cover flex-center-col">
              <img src="@/assets/images/phonon_bg.png" />
              <p class="cover-tip">
                No Data
              </p>
            </el-row>
          </el-col>
          <el-col class="button-box" :xl="8" :md="8">
            <el-row class="d2-mb-20 color-text-normal">
              <h3>Data Info</h3>
            </el-row>
            <el-row class="data-info" v-if="diagrams.phonon.data">
              <!-- Update Date -->
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      Update Date
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    {{ diagrams.phonon.createTime }}
                  </div>
                </el-col>
              </el-row>
              <!-- Update By -->
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      Update By
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    <router-link to="/user>
                                            <el-link type=" primary">UUID
                      {{ diagrams.phonon.createBy }}
                      </el-link>
                    </router-link>
                  </div>
                </el-col>
              </el-row>
            </el-row>
            <el-row>
              <el-button type="primary" plain @click="exportChartData('phonon', 'image')">Export
                Image</el-button>
              <el-button type="primary" plain @click="exportChartData('phonon', 'file')">Download
                Data</el-button>
            </el-row>
            <el-row class="cover">
              <el-upload class="upload-box" action="" drag ref="uploadPhonon" :auto-upload="false"
                :file-list="phononFileList" :on-preview="phononPreview" :on-change="phononFileChange" :multiple="false"
                :limit="1">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  Drag here, or <em>click to upload</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                  Mind the format of your file, tap file name
                  to preview.
                </div>
              </el-upload>
            </el-row>
            <el-row>
              <el-button type="success" plain @click="submitPhononUpload">Upload</el-button>
            </el-row>
            <!-- supplement -->
            <el-row class="d2-mb-20 color-text-normal">
              <h3>Data Supplement</h3>
            </el-row>
            <el-row class="data-info" v-if="diagrams.phonon.supplement">
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      File
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    <el-link type="primary" :href="diagrams.phonon.supplement.filepath">
                      {{ diagrams.phonon.supplement.name }}
                    </el-link>
                  </div>
                </el-col>
              </el-row>
            </el-row>
            <el-row>
              <el-row class="cover">
                <el-upload class="upload-box" action="" drag ref="uploadPhononSupplement" :auto-upload="false"
                  :file-list="phononFileSupllement" :http-request="phononFileSupllementUpload" :multiple="false"
                  :limit="1">
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">
                    Drag here, or <em>click to upload</em>
                  </div>
                  <div class="el-upload__tip" slot="tip">
                    Mind the format of your file, tap file
                    name to preview.
                  </div>
                </el-upload>
              </el-row>
              <el-row>
                <el-button type="success" plain @click="submitPhononSupplementUpload">Upload
                  supplement</el-button>
              </el-row>
            </el-row>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="Conductivity">
        <!-- <d2-container-frame
					:src="diagrams.phonon.supplement.filepath"
				/> -->
        <el-row :gutter="20" justify="space-around">
          <el-col class="chart-box" :xl="16" :md="16" v-if="diagrams.mobility.supplement">
            <iframe v-for="sup in diagrams.mobility.supplement.files" :key="sup.id" class="pdf-container-frame"
              :src="sup.filepath" frameborder="0" seamless />
          </el-col>
          <el-col class="button-box" :xl="8" :md="8">
            <el-row class="d2-mb-20 color-text-normal">
              <h3>Data Supllement</h3>
            </el-row>
            <el-row class="data-info" v-if="diagrams.mobility">
              <!-- Update Date -->
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      Update Date
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    {{ diagrams.mobility.createTime }}
                  </div>
                </el-col>
              </el-row>
              <!-- Update By -->
              <el-row :gutter="20" type="flex" align="middle">
                <el-col :span="DetailSpan / 3">
                  <div class="label">
                    <el-tag effect="plain">
                      Update By
                    </el-tag>
                  </div>
                </el-col>
                <el-col :span="(DetailSpan * 2) / 3">
                  <div class="content">
                    <router-link to="/user">
                      <el-link type="primary">
                        UUID {{ diagrams.mobility.createBy }}
                      </el-link>
                    </router-link>
                  </div>
                </el-col>
              </el-row>
            </el-row>
            <el-row class="cover">
              <el-upload class="upload-box" action="" drag ref="uploadMobilitySupplement" :auto-upload="false"
                :file-list="mobilityFileSupllement" :http-request="mobilityFileSupllementUpload" :multiple="true">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  Drag here, or <em>click to upload</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                  Mind the format of your file, tap file name
                  to preview.
                </div>
              </el-upload>
            </el-row>
            <el-row>
              <el-button type="success" plain @click="submitMobilitySupplementUpload">Upload
                Supplement</el-button>
            </el-row>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </d2-container>
</template>

<script>
import $ from "jquery";
import $3Dmol from "@/libs/3Dmol-nojquery.js";
import * as api from "./api";
import NumberTable from "@/components/number-table";
import util from "@/libs/util";
import Axios from "axios";
import { errorCreate } from "@/api/tools";
// echart
import { use } from "echarts/core";
import { CanvasRenderer } from "echarts/renderers";
import { LineChart } from "echarts/charts";
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  DataZoomComponent
} from "echarts/components";
import { default as VChart, THEME_KEY } from "vue-echarts";
import d2ContainerFrame from "@/components/d2-container-frame";
// tags
import structureTags from "./components/tags";
use([
  CanvasRenderer,
  LineChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  DataZoomComponent
]);
// TODO: hidden charts that don't need to show
let viewer = null;
let viewer_id = null;
const viewer_config = { backgroundColor: "white" };
export default {
  name: "structure-detail",
  components: {
    VChart,
    NumberTable,
    d2ContainerFrame,
    structureTags
  },
  provide: {
    // [THEME_KEY]: "dark"
  },
  data() {
    return {
      uuid: "",
      structure: {},
      fullscreenLoading: true,
      currentTab: "Material Detail",
      numberPrecision: 6,
      DetailSpan: 24,
      viewerLoading: false,
      viewerCoverTip: true,
      phononFileList: [],
      phononFileSupllement: [],
      bandFileList: [],
      bandFileSupllement: [],
      mobilityFileList: [],
      mobilityFileSupllement: [],
      phononChartOption: null,
      bandChartOption: null,
      loadingPhononData: false,
      loadingBandData: false,
      tags_dialog_visible: false,
      tags_dialog_inner_visible: false,
      new_tag_form: {
        name: "",
        description: ""
      },
      new_tag_form_rules: {
        name: [{ required: true, trigger: "blur" }],
        description: [{ required: true, trigger: "blur" }]
      },
      update_tag_loading: false,
      /* diagram data */
      diagramField: ["band", "phonon", "mobility"],
      diagrams: {
        phonon: {
          data: null,
          createBy: null,
          createTime: null,
          type: "",
          supplement: { filepath: "" }
        },
        band: {
          data: null,
          createBy: null,
          createTime: null,
          type: "",
          supplement: { filepath: "" }
        },
        mobility: {
          data: null,
          createBy: null,
          createTime: null,
          type: "",
          supplement: {
            filepath: ""
          }
        }
      }
    };
  },
  computed: {

  },
  methods: {
    handleTabSwitch(event) {
      if (this.currentTab == event.label) {
        return;
      } else {
        this.currentTab = event.label;
      }
      this.loadTabData();
    },
    loadTabData() {
      if (this.currentTab == "Phonon Dispersion") {
        if (!this.diagrams.phonon) {
          return;
        }
        this.loadingPhononData = true;
        if (this.diagrams.phonon.type.toLowerCase() == "json") {
          this.loadPhononJsonData(
            this.diagrams.phonon,
            (fileString, fileOption) => {
              this.phononFileString = fileString;
              this.phononFileOption = fileOption;
              !!this.$refs.phononChart &&
                this.$refs.phononChart.clear();
              this.phononChartOption = fileOption;
              this.loadingPhononData = false;
            }
          );
        } else {
          if (this.diagrams.phonon.data) {
            let [
              phononFileString,
              phononFileOption
            ] = this.loadPhononData(this.diagrams.phonon.data);
            this.phononFileString = phononFileString;
            this.phononFileOption = phononFileOption;
            !!this.$refs.phononChart &&
              this.$refs.phononChart.clear();
            this.phononChartOption = phononFileOption;
          }
          this.loadingPhononData = false;
        }
      } else if (this.currentTab == "Band Structure") {
        if (!this.diagrams.band) {
          return;
        }
        this.loadingBandData = true;
        if (this.diagrams.band.type.toLowerCase() == "json") {
          this.loadBandJsonData(
            this.diagrams.band,
            (fileString, fileOption) => {
              this.bandFileString = fileString;
              this.bandFileOption = fileOption;
              !!this.$refs.bandChart &&
                this.$refs.bandChart.clear();
              this.bandChartOption = fileOption;
              this.loadingBandData = false;
            }
          );
        } else {
          if (this.diagrams.band.data) {
            let [fileString, fileOption] = this.loadBandData(
              this.diagrams.band.data
            );
            this.bandFileString = fileString;
            this.bandFileOption = fileOption;
            !!this.$refs.bandChart && this.$refs.bandChart.clear();
            this.bandChartOption = fileOption;
          }
          this.loadingBandData = false;
        }
      }
    },
    displayViewer() {
      this.viewerLoading = true;
      api.GetFileStream(this.structure.filepath).then(data => {
        this.structureFileData = data;
        this.showStructureViewer(this.structureFileData);
        this.viewerLoading = false;
        this.viewerCoverTip = false;
      });
    },
    showStructureViewer(fileString, ext_name = "cif") {
      if (viewer) {
        viewer.clear();
      }
      viewer_id = $(`#detail-viewer-${this.uuid}`);
      viewer_id.children("canvas").remove();
      viewer = $3Dmol.createViewer(viewer_id, viewer_config);
      viewer.setBackgroundColor(0xffffffff, 0);
      let m = viewer.addModel(fileString, ext_name, {
        duplicateAssemblyAtoms: true,
        doAssembly: true
      });
      viewer.setHoverable(
        {},
        true,
        (atom, viewer, event, container) => {
          if (!atom.label) {
            atom.label = viewer.addLabel(atom.elem, {
              position: atom,
              backgroundColor: "darkgreen",
              backgroundOpacity: 0.5,
              fontColor: "white"
            });
          }
        },
        (atom) => {
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
    },
    clearPageContent(to, from, next) {
      if (viewer) {
        viewer.clear();
      }
      if (viewer_id) viewer_id.children("canvas").remove();
      this.viewerCoverTip = true;
      next();
    },
    uploadPhononPlotData() { },
    newTagFormSubmit(formName) {
      this.update_tag_loading = true;
      let formData = Object.assign(
        {
          user: util.cookies.get("uuid")
        },
        this.new_tag_form
      );
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$api
            .PatchObj(
              `/structure/${this.structure.uuid}/add_tags/`,
              {
                tags: [formData]
              }
            )
            .then(resp => {
              if (resp.code == 200) {
                this.$message.success("Upload Success!");
              }
              this.structure = resp.data;
              this.$refs[formName].resetFields();
              this.update_tag_loading = false;
              this.tags_dialog_inner_visible = false;
            });
        } else {
          this.$notify.error("Invaid Input");
          this.update_tag_loading = false;
          return false;
        }
      });
    },
    /* handleDeleteTag(index, row) {
      this.update_tag_loading = true;
      this.$api
        .PartialDelObj(
          `/structure/${this.structure.uuid}/remove_tags/`,
          {
            tags: [{ name: row.name }]
          }
        )
        .then(resp => {
          if (resp.code == 200) {
            this.$message.success("Remove Success!");
          }
          this.structure = resp.data;
          this.update_tag_loading = false;
        });
    }, */
    phononFileChange(file, fileList) {
      this.fullscreenLoading = true;
      const reader = new FileReader();
      reader.readAsText(file.raw, "UTF-8");
      reader.onload = event => {
        try {
          if (file.name.split(".").pop() == "json") {
            this.phononFileType = "json";
            this.loadPhononJsonData(
              { data: event.target.result },
              (fileString, fileOption) => {
                this.phononFileString = fileString;
                this.phononFileOption = fileOption;
                !!this.$refs.phononChart &&
                  this.$refs.phononChart.clear();
                this.phononChartOption = fileOption;
              }
            );
          } else {
            let [
              phononFileString,
              phononFileOption
            ] = this.loadPhononData(event.target.result.trim());
            this.phononFileString = phononFileString;
            this.phononFileOption = phononFileOption;
            this.phononFileList = fileList;
          }
        } catch (err) {
          return (this.fullscreenLoading =
            false &&
            this.$message.error(`Unsupport file format. ${err}`));
        }
        this.fullscreenLoading = false;
      };
    },
    phononFileSupllementUpload(event) {
      const file = event.file;
      const ext = file.name.substring(file.name.lastIndexOf("."));
      if ([".pdf"].includes(ext)) {
        return this.phononFileSupllementFileData.append("file", file);
      }
      this.fullscreenLoading = false;
      errorCreate("ERROR: File type Not support");
    },
    bandFileChange(file, fileList) {
      this.fullscreenLoading = true;
      const reader = new FileReader();
      reader.readAsText(file.raw, "UTF-8");
      reader.onload = event => {
        try {
          if (file.name.split(".").pop() == "json") {
            this.bandFileType = "json";
            this.loadBandJsonData(
              { data: event.target.result },
              (fileString, fileOption) => {
                this.bandFileString = fileString;
                this.bandFileOption = fileOption;
                !!this.$refs.bandChart &&
                  this.$refs.bandChart.clear();
                this.bandChartOption = fileOption;
              }
            );
          } else {
            let [
              bandFileString,
              bandFileOption
            ] = this.loadBandData(event.target.result.trim());
            this.bandFileString = bandFileString;
            this.bandFileOption = bandFileOption;
            this.bandFileList = fileList;
          }
        } catch (err) {
          return (this.fullscreenLoading =
            false &&
            this.$message.error(`Unsupport file format. ${err}`));
        }
        this.fullscreenLoading = false;
      };
    },
    bandFileSupllementUpload(event) {
      const file = event.file;
      const ext = file.name.substring(file.name.lastIndexOf("."));
      if ([".pdf"].includes(ext)) {
        return this.bandFileSupllementFileData.append("file", file);
      }
      this.fullscreenLoading = false;
      errorCreate("ERROR: File type Not support");
    },
    mobilityFileSupllementUpload(event) {
      const file = event.file;
      const ext = file.name.substring(file.name.lastIndexOf("."));
      if ([".pdf"].includes(ext)) {
        return this.mobilityFileSupllementFileData.append("file", file);
      }
      this.fullscreenLoading = false;
      errorCreate("ERROR: File type Not support");
    },
    loadPhononData(fileString) {
      let option = {
        grid: {
          show: true,
          borderWidth: 0.5,
          shadowColor: "rgba(0, 0, 0, 0.5)",
          shadowBlur: 10
        },
        title: {
          text: `Phonon Dispersion`,
          left: "center"
        },
        tooltip: {
          trigger: "axis"
        },
        xAxis: {
          name: "Wave Vector",
          nameLocation: "center",
          nameTextStyle: {
            lineHeight: 56,
            fontWeight: "bold",
            fontSize: 16
          },
          splitLine: {
            show: true,
            lineStyle: {
              width: 0.5,
              opacity: 0.5
            }
          }
        },
        yAxis: {
          name: "Frequency",
          nameLocation: "center",
          nameTextStyle: {
            lineHeight: 56,
            fontWeight: "bold",
            fontSize: 16
          },
          type: "value",
          splitLine: {
            show: true,
            lineStyle: {
              width: 0.5,
              opacity: 0.5
            }
          }
        },
        series: []
      };

      let phononFileString = "";
      let bands = fileString.split(/\n\s*\n/g);
      bands.forEach(bandstr => {
        let band_y = {
          type: "line",
          smooth: true,
          showSymbol: false
        };
        let band = bandstr.split(/\n/g);
        let xdata = [];
        let ydata = [];
        band.forEach(pos => {
          let [x, y] = pos.trim().split(/\s+/g);
          xdata.push(parseFloat(x));
          ydata.push(parseFloat(y));
          phononFileString += pos.trim() + "\n";
        });
        if (!option.xAxis.hasOwnProperty("data")) {
          option.xAxis.data = xdata;
        }
        band_y.data = ydata;
        option.series.push(band_y);
        phononFileString += "\n";
      });
      return [phononFileString, option];
    },
    loadPhononJsonData(obj, callback) {
      /* data obj:
      data: ""
      createTime: "2021-05-24T12:51:23.576"
      filepath: "/static/media/files/structure/b9d13437-d13c-4fd0-b221-2591be4f4144/bands/d5e376df-e0f4-4372-a0f6-419beb3d1516.json"
      type: "json"
      createBy: null
      uuid: "d5e376df-e0f4-4372-a0f6-419beb3d1516" */
      let setPlotData = (data, callback) => {
        let tick_labels = data.tick_labels.map(o => util.renderUnicodeString(o));
        let tick_pos = data.tick_pos;
        let xLabelIndex = [0];
        let option = {
          animation: false,
          grid: {
            show: true,
            borderWidth: 0.5,
            shadowColor: "rgba(0, 0, 0, 0.5)",
            shadowBlur: 10
          },
          title: {
            text: `Phonon Dispersion`,
            left: "center"
          },
          tooltip: {
            trigger: "axis"
          },
          xAxis: {
            name: "Wave Vector",
            nameLocation: "center",
            nameTextStyle: {
              lineHeight: 56,
              fontWeight: "bold",
              fontSize: 16
            },
            boundaryGap: false,
            min: "dataMin",
            max: "dataMax",
            axisLabel: {
            },
            axisTick: {
            },
            splitLine: {}
          },
          yAxis: {
            name: data.yaxis_label || "Dispersion (THz)",
            nameLocation: "center",
            nameTextStyle: {
              lineHeight: 56,
              fontWeight: "bold",
              fontSize: 16
            },
            type: "value"
          },
          series: []
        };

        let bandFileString = JSON.stringify(data);
        let x = [];
        let paths = data.paths;
        paths.forEach((item, index) => {
          x = x.concat(item.x);
          xLabelIndex.push(x.length > 0 ? x.length - 1 : x.length)
          item.values.forEach((band, bn) => {
            if (index == 0) {
              option.series.push({
                type: "line",
                smooth: true,
                showSymbol: false,
                data: band
              });
            } else {
              Array.prototype.push.apply(
                option.series[bn].data,
                band
              );
            }
          });
        });
        option.xAxis.data = x;
        option.xAxis.axisLabel = {
          show: true,
          interval: (index, value) => xLabelIndex.includes(index),
          formatter: (value, index) => tick_labels[xLabelIndex.lastIndexOf(index)]
        }
        option.xAxis.axisTick = {
          show: true,
          interval: (index, value) => xLabelIndex.includes(index)
        }
        option.xAxis.splitLine = {
          show: true,
          interval: (index, value) => xLabelIndex.includes(index)
        }
        callback(bandFileString, option);
      };
      if (!obj.data && !!obj.filepath) {
        // get file
        api.GetFileStream(obj.filepath)
          .then(data => setPlotData(data, callback));
      } else if (obj.data) {
        setPlotData(JSON.parse(obj.data), callback);
      }
    },
    loadBandData(fileString) {
      let option = {
        animation: false,
        grid: {
          show: true,
          borderWidth: 0.5,
          shadowColor: "rgba(0, 0, 0, 0.5)",
          shadowBlur: 10
        },
        title: {
          text: `Band Structure`,
          left: "center"
        },
        tooltip: {},
        dataZoom: [
          {
            id: "dataZoomX",
            show: true,
            type: "slider",
            filterMode: "none",
            xAxisIndex: [0]
          },
          {
            id: "dataZoomY",
            show: true,
            type: "inside",
            filterMode: "none",
            yAxisIndex: [0],
            startValue: -10,
            endValue: 10
          }
        ],
        xAxis: {
          splitLine: {
            show: true,
            lineStyle: {
              width: 0.5,
              opacity: 0.5
            }
          },
          minorTick: {
            show: true
          },
          min: "dataMin",
          max: "dataMax"
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: true,
            lineStyle: {
              width: 0.5,
              opacity: 0.5
            }
          },
          minorTick: {
            show: true
          },
          min: "dataMin",
          max: "dataMax"
        },
        series: []
      };

      let bandFileString = "";
      let bands = fileString.split(/\n\s*\n/g);
      bands.forEach(bandstr => {
        let band_y = {
          type: "line",
          smooth: true,
          showSymbol: false
        };
        let band = bandstr.split(/\n/g);
        let xdata = [];
        let ydata = [];
        band.forEach(pos => {
          let [x, y] = pos.trim().split(/\s+/g);
          xdata.push(parseFloat(x));
          ydata.push(parseFloat(y));
          bandFileString += pos.trim() + "\n";
        });
        if (!option.xAxis.hasOwnProperty("data"))
          option.xAxis.data = xdata;
        band_y.data = ydata;
        option.series.push(band_y);
        bandFileString += "\n";
      });
      return [bandFileString, option];
    },
    loadBandJsonData(obj, callback) {
      /* data obj:
      data: ""
      createTime: "2021-05-24T12:51:23.576"
      filepath: "/static/media/files/structure/b9d13437-d13c-4fd0-b221-2591be4f4144/bands/d5e376df-e0f4-4372-a0f6-419beb3d1516.json"
      type: "json"
      createBy: null
      uuid: "d5e376df-e0f4-4372-a0f6-419beb3d1516" */
      let setPlotData = (data, callback) => {
        let bandFileString = JSON.stringify(data);
        let x = [];
        let paths = data.paths;
        let tick_labels = data.tick_labels.map(o => util.renderUnicodeString(o));
        let tick_pos = data.tick_pos;
        let xLabelIndex = [0];
        let option = {
          animation: false,
          grid: {
            show: true,
            borderWidth: 0.5,
            shadowColor: "rgba(0, 0, 0, 0.5)",
            shadowBlur: 10
          },
          title: {
            text: `Band Structure`,
            left: "center"
          },
          dataZoom: [
            {
              id: "dataZoomX",
              show: true,
              type: "slider",
              filterMode: "none",
              xAxisIndex: [0]
            },
            {
              id: "dataZoomY",
              show: true,
              type: "inside",
              filterMode: "none",
              yAxisIndex: [0],
              startValue: data.y_min_lim || -10,
              endValue: data.y_max_lim || 10
            }
          ],
          xAxis: {
            boundaryGap: false,
            min: "dataMin",
            max: "dataMax",
            axisLabel: {
            },
            axisTick: {
            }
          },
          yAxis: {
            name: data.yaxis_label || "Dispersion (ev)",
            type: "value"
          },
          series: []
        };
        paths.forEach((item, index) => {
          x = x.concat(item.x);
          xLabelIndex.push(x.length > 0 ? x.length - 1 : x.length)
          item.values.forEach((band, bn) => {
            if (index == 0) {
              option.series.push({
                type: "line",
                smooth: true,
                showSymbol: false,
                data: band
              });
            } else {
              Array.prototype.push.apply(
                option.series[bn].data,
                band
              );
            }
          });
        });
        option.xAxis.data = x;
        option.xAxis.axisLabel = {
          show: true,
          interval: (index, value) => xLabelIndex.includes(index),
          formatter: (value, index) => tick_labels[xLabelIndex.lastIndexOf(index)]
        }
        option.xAxis.axisTick = {
          show: true,
          interval: (index, value) => xLabelIndex.includes(index)
        }
        option.xAxis.splitLine = {
          show: true,
          interval: (index, value) => xLabelIndex.includes(index)
        }
        callback(bandFileString, option);
      };
      if (!obj.data && !!obj.filepath) {
        // get file
        api.GetFileStream(obj.filepath).then(data => {
          setPlotData(data, callback);
        });
      } else if (obj.data) {
        setPlotData(JSON.parse(obj.data), callback);
      }
    },
    phononPreview(file) {
      this.phononChartOption = this.phononFileOption;
    },
    bandPreview(file) {
      this.bandChartOption = this.bandFileOption;
    },
    submitPhononUpload() {
      if (this.phononFileList.length == 0) {
        return this.$notify.info("You have to choose a file.");
      }
      this.fullscreenLoading = true;
      let callback = () => {
        this.$api
          .AddObj("/structure/upload/phonon", {
            structureId: this.structure.uuid,
            data: this.phononFileString,
            type: this.phononFileType || null,
            field: "phonon",
            description: "Phonon Dispersion Diagram"
          })
          .then(resp => {
            if (resp.code == 200) {
              this.structure = resp.data;
              this.loadTabData();
              this.phononFileList = [];
              this.fullscreenLoading = false;
              this.$message.success("Submit successfully!");
            } else {
              this.$message.error(resp.code);
            }
          });
      };
      if (this.diagrams.phonon && this.diagrams.phonon.data) {
        this.$confirm(
          "This operation will overide current phonon dispersion data, continue?",
          "Attention",
          {
            confirmButtonText: "sure",
            cancelButtonText: "cancel",
            type: "warning"
          }
        )
          .then(() => {
            callback();
          })
          .catch(() => {
            this.fullscreenLoading = false;
          });
      } else {
        callback();
      }
    },
    submitPhononSupplementUpload() {
      this.fullscreenLoading = true;
      let callback = () => {
        this.phononFileSupllementFileData = new FormData();
        this.$refs.uploadPhononSupplement.submit();
        this.phononFileSupllementFileData.append(
          "form",
          JSON.stringify({
            structureId: this.structure.uuid,
            type: "pdf"
          })
        );
        this.$api
          .AddObj(
            "/structure/phonon/supplement/",
            this.phononFileSupllementFileData
          )
          .then(resp => {
            if (resp.code == 200) {
              this.structure = resp.data;
              this.phononFileSupllement = [];
              this.fullscreenLoading = false;
              this.$message.success("Submit successfully!");
            } else {
              this.$message.error(resp.code);
            }
          });
      };
      callback();
    },
    submitBandUpload() {
      if (this.bandFileList.length == 0) {
        return this.$notify.info("You have to choose a file.");
      }
      this.fullscreenLoading = true;
      let callback = () => {
        this.$api
          .AddObj("/structure/upload/band/", {
            structureId: this.structure.uuid,
            data: this.bandFileString,
            type: this.bandFileType || ""
          })
          .then(resp => {
            if (resp.code == 200) {
              this.structure = resp.data;
              this.loadTabData();
              this.bandFileList = [];
              this.fullscreenLoading = false;
              this.$message.success("Submit successfully!");
            } else {
              this.$message.error(resp.code);
            }
          });
      };
      if (this.diagrams.band && this.diagrams.band.data) {
        this.$confirm(
          "This operation will overide current band structure data, continue?",
          "Attention",
          {
            confirmButtonText: "sure",
            cancelButtonText: "cancel",
            type: "warning"
          }
        )
          .then(() => {
            callback();
          })
          .catch(() => {
            this.fullscreenLoading = false;
          });
      } else {
        callback();
      }
    },
    submitBandSupplementUpload() {
      this.fullscreenLoading = true;
      let callback = () => {
        this.bandFileSupllementFileData = new FormData();
        this.$refs.uploadBandSupplement.submit();
        this.bandFileSupllementFileData.append(
          "form",
          JSON.stringify({
            uuid: this.structure.uuid,
            user: util.cookies.get("uuid"),
            type: "pdf"
          })
        );
        this.$api
          .AddObj(
            "/structure/bands/supplement/",
            this.bandFileSupllementFileData
          )
          .then(resp => {
            if (resp.code == 200) {
              this.structure = resp.data;
              this.bandFileSupllement = [];
              this.fullscreenLoading = false;
              this.$message.success("Submit successfully!");
            } else {
              this.$message.error(resp.code);
            }
          });
      };
      callback();
    },
    submitMobilitySupplementUpload() {
      this.fullscreenLoading = true;
      let callback = () => {
        this.mobilityFileSupllementFileData = new FormData();
        this.$refs.uploadMobilitySupplement.submit();
        this.mobilityFileSupllementFileData.append(
          "form",
          JSON.stringify({
            uuid: this.structure.uuid,
            user: util.cookies.get("uuid"),
            type: "pdf"
          })
        );
        this.$api
          .AddObj(
            "/structure/mobility/supplement/",
            this.mobilityFileSupllementFileData
          )
          .then(resp => {
            if (resp.code == 200) {
              this.structure = resp.data;
              this.mobilityFileSupllement = [];
              this.fullscreenLoading = false;
              this.$message.success("Submit successfully!");
            } else {
              this.$message.error(resp.code);
            }
          });
      };
      callback();
    },
    exportChartData(chart, type) {
      let data = null;
      if (type == "image") {
        if (chart == "phonon") {
          data = this.$refs.phononChart;
        } else if (chart == "band") {
          data = this.$refs.bandChart;
        }
        if (!data) return this.$notify.info("No chart data currently.");
        let img = {
          src: data.getDataURL({
            pixelRatio: window.devicePixelRatio || 2
          }),
          width: data.getWidth(),
          height: data.getHeight()
        };
        const element = document.createElement("a");
        element.setAttribute("href", img.src);
        element.setAttribute("download", chart + ".png");
        element.style.display = "none";
        element.click();
      } else if (type == "file") {
        if (chart == "phonon") {
          data = this.phononFileString;
        } else if (chart == "band") {
          data = this.bandFileString;
        }
        if (!data) return this.$notify.info("No chart data currently.");
        const element = document.createElement("a");
        element.setAttribute(
          "href",
          "data:text/plain;charset=utf-8," + encodeURIComponent(data)
        );
        element.setAttribute("download", chart + ".data");
        element.style.display = "none";
        element.click();
      }
    },
    assignDiagramData(key) {
      let listKey = key + "List";
      if (this.structure[listKey] && this.structure[listKey].length > 0) {
        let diagram = Object.assign({}, this.structure[listKey].slice(-1).pop());
        if (diagram.supplements && diagram.supplement.length > 0) {
          diagram.supplement = diagram.supplements.slice(-1).pop();
          delete diagram.supplements;
        }
        return diagram;
      }
      return {};
    },
    loadStructureData() {
      this.diagramField.forEach(key => {
        this.diagrams[key] = this.assignDiagramData(key);
      })
    }
  },
  mounted() {
    this.uuid = this.$route.params.uuid;
    if (this.uuid == "") {
      this.$message.error("Whoops! This uuid has expired.");
      setTimeout(() => {
        this.$router.back();
      }, 1500);
    }
    api.GetObj(this.uuid).then(resp => {
      this.structure = resp.data;
      this.loadStructureData();
      this.fullscreenLoading = false;

      this.$store.dispatch("d2admin/page/update", {
        tagName: this.$route.fullPath,
        title: `${this.structure.formula.replace(/<[^<>]+>/g, "")}`
      });
    });
    viewer_config.id = `detail-viewer-canvas-${this.uuid}`;
  },
  beforeRouteUpdate(to, from, next) {
    this.clearPageContent(to, from, next);
  },
  beforeRouteLeave(to, from, next) {
    this.clearPageContent(to, from, next);
  }
};
</script>

<style lang="scss" scoped>
@import "@/assets/style/public.scss";

.panel {
  padding: 24px 40px;
  width: 100%;

  .cover {
    width: 100%;
    height: 100%;
    @extend %flex-col;
  }

  >.el-row {
    width: 100%;
    height: 100%;
  }

  .phonon-panel {
    text-align: center;
  }
}

.chart-box {
  padding: 16px;
  height: 75vh;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  position: relative;

  .cover {
    @extend %unable-select;

    img {
      position: absolute;
      width: 100%;
      height: 100%;
      opacity: 0.1;
      filter: blur(1px);
    }

    &::after {
      content: "";
      width: 100%;
      height: 100%;
      position: absolute;
      opacity: 0;
      top: 0;
      left: 0;
    }
  }

  .chart {
    // max-width: 720px;
    // max-height: 600px;
    margin: 16px auto;
  }
}

.button-box {
  .el-row+.el-row {
    margin-top: 16px;
  }
}

@mixin detail-title() {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  border-bottom: 1px solid #99a9bf;
  padding-bottom: 16px;
}

.material-detail {
  .material-detail-title {
    @include detail-title();
  }

  .label {
    // color: #99a9bf;
    // font-size: 16px;
    // font-weight: bold;
    text-transform: uppercase;
  }

  >.el-row {
    padding: 8px 0;
  }
}

.material-viewer-container {

  // height: 100%;
  >.el-row {
    padding: 8px 0;
  }

  .viewer-title {
    @include detail-title();
  }

  .structure-viewer {
    background-color: rgba($color: #fff, $alpha: 0.1);
    color: #99a9bf;
    padding: 12px;
    border: 1px dashed #d9d9d9;
    width: 100%;
    height: 100%;
    max-height: 720px;
    position: relative;

    .structure-viewer-cover {
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      opacity: 0.2;
    }

    .cover-tip {
      z-index: 99;
      background: rgba($color: #000000, $alpha: 0.1);
      padding: 24px;
      border-radius: 5px;
      @extend %unable-select;
      transition: background-color 0.3s;
    }

    .cover-tip:hover {
      background: rgba($color: #868686, $alpha: 0.5);
      color: #fff;
    }
  }
}
</style>
<style lang="scss">
.structure-detail-container {
  .tab-container {
    width: 100%;
    height: 100%;
    overflow: hidden;

    &.el-tabs--border-card {
      background-color: transparent;
    }

    .el-tabs__content {
      overflow-y: auto;
      @include scrollBar();
    }

    .el-tabs__header {
      background: rgba($color: #fff, $alpha: 0.8);
      border-radius: 5px;
    }

    .el-tabs__active-bar.is-bottom {
      transform: translateX(5px);
    }

    .el-tabs__content {
      height: 100%;
      width: 100%;
      overflow-y: auto;
    }
  }

  .detail-date-picker input {
    border: none !important;
  }

  .material-viewer-container {
    canvas {
      padding: 8px !important;
    }
  }

  .pdf-container-frame {
    text-align: center;
    height: 50%;
    width: 100%;
  }
}
</style>
