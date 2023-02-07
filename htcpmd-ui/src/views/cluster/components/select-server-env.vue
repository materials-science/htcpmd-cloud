<template>
  <div class="cluster-select-server-env">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item :label="$_t('Current Environment', true)" prop="label">
        <el-select v-model="selected.label" :placeholder="$_t('No Data')" @focus="dialog_visible = !dialog_visible"
          popper-class="hidden">
          <el-option :label="selected.label" :value="selected.label"></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-dialog :title="$_t('server list', true)" :visible.sync="dialog_visible" destroy-on-close>
      <el-table :data="tableData" @row-click="onRowClick" :row-class-name="tableRowClassName">
        <el-table-column property="label" :label="$_t('label')"></el-table-column>
        <el-table-column property="description" :label="$_t('description')" width="200"></el-table-column>
        <el-table-column :label="$_t('createTime')" width="200">
          <template slot-scope="props">
            <el-date-picker v-model="props.row.createTime" type="datetime" placeholder="created time" readonly
              class="data-date-picker">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column :label="$_t('createBy')">
          <template slot-scope="props">
            <user-link :uuid="props.row.createBy"></user-link>
          </template>
        </el-table-column>
        <el-table-column align="right" width="200">
          <template slot="header" slot-scope="scope">
            <el-input v-model="queryParams.label" size="small" placeholder="Type Label to Search" clearable
              @keyup.enter.native="searchData" />
          </template>
        </el-table-column>
      </el-table>

      <el-row class="d2-pt-8 d2-pb-8">
        <el-col style="text-align: end">
          <el-pagination background @size-change="searchData" @current-change="searchData"
            :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40]" :page-size="queryParams.pageSize"
            layout="total, sizes, prev, pager, next" :total="totalCount"></el-pagination>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import userLink from "@/components/user-link";
import { errorCreate } from "@/api/tools";
import setting from "@/setting";
const htcpmd_center = setting.api.htcpmd_center;
const apiPrefix = `${htcpmd_center}/server/`;
export default {
  name: "select-server-env",
  components: {
    userLink
  },
  props: {
    serverEnv: {
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      loading: true,
      DetailSpan: 24,
      dialog_visible: false,
      /* search */
      tableData: [],
      totalCount: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        label: null
      },
      selected: this.serverEnv ? this.serverEnv : {}
    }
  },
  methods: {
    searchData() {
      this.loading = true;
      let promise = this.queryParams.label
        ? this.$api.GetList(`${apiPrefix}search`, this.queryParams)
        : this.$api.pageRequest(apiPrefix, this.queryParams)
      promise
        .then(resp => {
          this.tableData = resp.rows;
          this.totalCount = resp.total;
          this.loading = false;
        })
        .catch(err => {
          this.loading = false;
          errorCreate(err);
        })
    },
    onRowClick(row) {
      if (row.disabled) {
        return
      }
      this.selected = row
    },
    tableRowClassName({ row, rowIndex }) {
      console.log(row, rowIndex, this.selected.uuid);
      if (row.uuid == this.selected.uuid) {
        return "clickable row-success-bg"
      }
      if (row.disabled) {
        return "is-disabled"
      }
      return "clickable"
    }
  },
  watch: {
    selected(newVal) {
      this.$emit("update", this.selected);
    }
  },
  mounted() {
    this.searchData();
  }
}
</script>

<style lang="scss" scoped>
.server {
  &-list {
    margin: 1rem 0;
  }
}
</style>

<style lang="scss">
.row-success-bg {
  background-color: #e1f3d8 !important;
}

.hidden {
  display: none;
}
</style>
