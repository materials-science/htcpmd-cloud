<template>
  <div class="structure-tags">
    <el-row class="structure-tags-label">
      <el-col :span="DetailSpan / 2">
        <el-tag effect="plain"> Tags </el-tag>
      </el-col>
      <el-col :span="DetailSpan / 2">
        <el-link type="primary" @click.stop="tags_dialog_visible = true" class="d2-mr-10"><d2-icon
            name="edit"></d2-icon></el-link>
      </el-col>
    </el-row>
    <el-row :span="DetailSpan" class="structure-tags-list">
      <el-col :span="DetailSpan / 3">
        <el-link type="primary" v-for="(tag, index) in tags" :key="index" @click.stop="tags_dialog_visible = true"
          class="d2-mr-10">{{ tag.name }}</el-link>
      </el-col>
    </el-row>
    <el-dialog title="Tags" :visible.sync="tags_dialog_visible" destroy-on-close>
      <el-table :data="tags">
        <el-table-column property="name" label="name"></el-table-column>
        <el-table-column property="description" label="description" width="200"></el-table-column>
        <el-table-column label="createTime" width="200">
          <template slot-scope="props">
            <el-date-picker v-model="props.row.createTime" type="datetime" placeholder="created time" readonly
              class="data-date-picker">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="createBy">
          <template slot-scope="props">
            <router-link :to="`/user/${props.row.createBy}`">
              <el-link type="primary">UUID
                {{ props.row.createBy }}
              </el-link>
            </router-link>
          </template>
        </el-table-column>
        <el-table-column align="right" width="200">
          <template slot="header" slot-scope="scope">
            <el-row :gutter="20">
              <el-col :span="DetailSpan / 2">
                <el-button size="small" @click.stop="tags_search_dialog_visible = true">add</el-button>
              </el-col>
              <el-col :span="DetailSpan / 2">
                <el-button size="small" @click.stop="tags_dialog_inner_visible = true">create</el-button>
              </el-col>
            </el-row>
          </template>
          <template slot-scope="scope">
            <el-button size="mini" type="danger" plain
              @click="removeThisTag(scope.$index, scope.row)">remove</el-button>
            <el-button size="small" icon="el-icon-edit"></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- add a tag -->
      <el-dialog title="Add Tag" :visible.sync="tags_search_dialog_visible" append-to-body v-loading="loading">
        <el-table :data="searchTagList">
          <el-table-column property="name" label="name"></el-table-column>
          <el-table-column property="description" label="description" width="200"></el-table-column>
          <el-table-column label="createTime" width="200">
            <template slot-scope="props">
              <el-date-picker v-model="props.row.createTime" type="datetime" placeholder="created time" readonly
                class="data-date-picker">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="createBy">
            <template slot-scope="props">
              <router-link :to="`/user/${props.row.createBy}`">
                <el-link type="primary">UUID
                  {{ props.row.createBy }}
                </el-link>
              </router-link>
            </template>
          </el-table-column>
          <el-table-column align="right" width="200">
            <template slot="header" slot-scope="scope">
              <el-input v-model="queryParams.name" size="small" placeholder="Type to search" clearable
                @keyup.enter.native="searchTags" />
            </template>
            <template slot-scope="scope">
              <el-button size="mini" plain @click="addThisTag(scope.$index, scope.row)">add</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination background @size-change="searchTags" @current-change="searchTags"
          :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40]" :page-size="queryParams.pageSize"
          layout="total, sizes, prev, pager, next" :total="totalTags"></el-pagination>
      </el-dialog>

      <!-- create a new tag -->
      <el-dialog title="New Tag" :visible.sync="tags_dialog_inner_visible" append-to-body>
        <el-form :model="new_tag_form" :rules="new_tag_form_rules" ref="new_tag_form" label-position="left">
          <el-form-item label="tag name" prop="name">
            <el-input v-model="new_tag_form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="tag description" prop="description">
            <el-input v-model="new_tag_form.description" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="newTagFormSubmit('new_tag_form')" plain>Submit</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-dialog>
  </div>
</template>

<script>
import { errorCreate } from "@/api/tools";
import setting from "@/setting";
const htcpmd_center = setting.api.htcpmd_center;
export default {
  name: "structure-tags",
  props: {
    tags: {
      default() {
        return [];
      }
    }
  },
  data() {
    return {
      loading: true,
      DetailSpan: 24,
      /* tags */
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
      /* tag search */
      tags_search_dialog_visible: false,
      searchTagList: [],
      totalTags: 0,
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        name: null
      }
    }
  },
  methods: {
    newTagFormSubmit(formName) {
      let formData = this.new_tag_form;
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.$api
            .AddObj(
              `${htcpmd_center}/structure_tag/`,
              formData
            )
            .then(resp => {
              if (resp.code == 200) {
                this.$message.success("Upload Success!");
              }
              this.$refs[formName].resetFields();
              this.tags_dialog_inner_visible = false;
              this.tags.push(resp.data)
            });
        } else {
          this.$notify.error("Invaid Input");
          return false;
        }
      });
    },
    /* add existed tag */
    searchTags() {
      this.loading = true;
      this.$api.GetList(`${htcpmd_center}/structure_tag/search`, this.queryParams)
        .then(resp => {
          this.searchTagList = resp.rows;
          this.totalTags = resp.total;
          this.loading = false;
        })
        .catch(err => {
          this.loading = false;
          errorCreate(err);
        })
    },
    addThisTag(index, row) {
      let existed = this.tags.some(tag => {
        return tag.uuid == row.uuid;
      })
      if (existed) {
        this.$notify.info("This tag has already existed.")
      } else {
        this.tags.push(row);
        this.$notify.success("Success.")
      }
    },
    removeThisTag(index, row) {
      this.tags.splice(index, 1);
    }
  },
  watch: {
    tags(newVal) {
      this.$emit("update", this.tags);
    }
  }
}
</script>

<style lang="scss" scoped>
.structure-tags {
  &-list {
    margin: 1rem 0;
  }
}
</style>
