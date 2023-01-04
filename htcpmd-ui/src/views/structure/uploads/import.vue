<template>
  <d2-container class="structure-import-container" type="card">
    <template slot="header">Import AiiDA Exported Structure Data</template>
    <el-row :gutter="20" v-loading="import_structures_loading">
      <el-col :xl="6" :lg="8" :md="12">
        <h5 class="d2-pb-8">Import</h5>
        <structure-tags @update="onTagsUpdate"></structure-tags>
        <el-form :model="import_structures_form" :rules="import_structures_form_rules" ref="import_structures_form"
          label-position="left">
          <!-- upload box -->
          <el-form-item>
            <el-upload class="upload-box" ref="import_structures_files" action="" :auto-upload="false" list-type="text"
              :file-list="import_structures_files_list" :http-request="uploadImportFiles"
              :accept="accept_import_exts.join()" :limit="1" :on-change="handleImportFileChange">
              <el-button slot="trigger" size="small" type="primary" plain>choose files</el-button>
              <div slot="tip" class="el-upload__tip">
                File(zip, tar.gz)
              </div>
            </el-upload>
          </el-form-item>
          <!-- operations -->
          <el-form-item>
            <el-button type="primary" @click="importStructuresFormSubmit('import_structures_form')"
              plain>Submit</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <!-- Example -->
      <el-col :xl="8" :lg="10" :md="12">
        <h5 class="d2-pb-8">Tips</h5>
        <el-collapse :value="['1', '2']">
          <el-collapse-item title="文件格式" name="1">
            <p>接受zip和tar.gz格式的压缩包</p>
          </el-collapse-item>
          <el-collapse-item title="压缩文件夹结构示例" name="2">
            <el-row v-for="(item, index) in directory_structure" :key="item.name">
              <p v-if="item.type == 'file'">
                <el-link type="primary" @click="showExampleFile(item.name)">
                  <d2-icon name="file-code-o" />
                  {{ item.name }}
                </el-link>
              </p>
              <p v-else>
                <d2-icon name="folder-o" />
                {{ item.name }}
              </p>
              <p v-for="child in item.children" :key="child.name" class="d2-ml-16">
                <el-link type="primary" @click="showExampleFile(child.name)">
                  <d2-icon name="file-code-o" />
                  {{ child.name }}
                </el-link>
              </p>
            </el-row>
          </el-collapse-item>
        </el-collapse>
      </el-col>
    </el-row>
    <!-- Example dialog -->
    <el-dialog :title="showed_example_name" :visible.sync="example_dialog_visible">
      <d2-highlight :code="showed_example_content" lang="json" />
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="example_dialog_visible = false">close</el-button>
      </span>
    </el-dialog>
  </d2-container>
</template>

<script>
import structureTags from "../components/tags";
import util from "@/libs/util";
import ExampleData from "@/assets/data/structuresImportExamples.json";
import setting from "@/setting";
import * as api from "../api";
const htcpmd_center = setting.api.htcpmd_center;
export default {
  name: "structure-import-from-aiida",
  components: {
    "structure-tags": structureTags
  },
  data() {
    return {
      formLabelWidth: "200px",
      fileList: [],
      uploadUrl: "/api/structure/files/",
      uploadHeaders: {
        Authorization: `Token ${util.cookies.get("token")}`
      },
      accept_import_exts: [".zip", ".gz"],
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
          children: [{ name: "Ag2Br2-7fbb633d_data.json", type: "file" }]
        },
        {
          name: "phonons",
          type: "folder",
          children: [{ name: "Ag2Br2-c0931d70_data.json", type: "file" }]
        }
      ],
      showed_example_name: null,
      example_dialog_visible: false,
      import_structures_files_list: [],
      import_structures_loading: false,
      tags: []
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
        let content = this.imported_examples_data[this.showed_example_name];
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
  methods: {
    // 显示提示
    showInfo(title, message) {
      this.$notify({
        title: title,
        message: message
      });
    },
    showExampleFile(name) {
      this.showed_example_name = name;
      this.example_dialog_visible = true;
    },
    handleImportFileChange(file, fileList) {
      if (
        !["application/x-gzip", "application/x-zip-compressed"].includes(
          file.raw.type
        )
      ) {
        this.import_structures_files_list = [];
        this.$message.error("Only zip and tar.gz are accepted.");
      }
      if (file.size / 1024 / 1024 >= setting.file.max) {
        this.import_structures_files_list = [];
        this.$message.error("This file has exceed max file limit.");
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
          // append tags
          if (this.tags && this.tags.length > 0) {
            let tagNameList = this.tags.map(tag => {
              return tag.name;
            })
            this.fileData.append("tagNameList", tagNameList);
          }

          api.Post("import/aiida", this.fileData).then(resp => {
            if (resp.code == 200) {
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
    },
    onTagsUpdate(tags) {
      this.tags = tags;
    }
  }
};
</script>

<style lang="scss" scoped>

</style>
