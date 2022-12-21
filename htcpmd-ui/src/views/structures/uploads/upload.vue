<template>
  <d2-container class="structures-import-container">
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
            Drag crystal structure file to here or<em> Click mouse</em>
          </div>
          <div class="el-upload__tip" slot="tip">
            Support <strong>POSCAR</strong>, <strong>CIF</strong> etc.
          </div>
        </el-upload>
        <el-row class="d2-pl-30">
          <el-button type="warning" plain @click="clearFileList"
            >Clear Current Files List</el-button
          >
          <el-button type="primary" plain @click="uploadSubmit"
            >Submit To Server</el-button
          >
        </el-row>
      </el-col>
      <el-col :xl="16" :lg="12" hidden-sm-and-down>
        <div class="upload-structure-box">
          <div class="structure-viewer"></div>
          <div class="tip"></div>
        </div>
      </el-col>
    </el-row>
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
      }
    };
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
        message: message,
        duration: 1500
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
.upload-wrap {
  height: 100%;
}
</style>
<style lang="scss">
.structures-import-container {
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
