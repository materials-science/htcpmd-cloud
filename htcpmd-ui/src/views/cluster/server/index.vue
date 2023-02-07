<template>
  <d2-container type="full" class="cluster-server-container">
    <el-table v-loading="tableLoading" :data="tableData" style="width: 100%"
      :default-sort="{ prop: 'uuid', order: 'descending' }" row-key="uuid" :expand-row-keys="expandRowKeys"
      @row-click="onRowClick" class="data-table" height="100%">
      <el-table-column type="expand" fixed>
        <template slot-scope="props">
          <el-tabs type="border-card" @tab-click="
            handleTabSwitch($event, props.$index, props.row)
          ">
            <el-tab-pane label="Attributes">
              <object-attr-form :object="props.row" :keys="attrKeys"></object-attr-form>
            </el-tab-pane>
          </el-tabs>
        </template>
      </el-table-column>
      <el-table-column prop="uuid" label="uuid" sortable align="center"></el-table-column>
      <el-table-column prop="label" :label="$_t('label')" align="center">
      </el-table-column>
      <el-table-column prop="hostname" label="hostname" align="center">
      </el-table-column>
      <el-table-column :label="$_t('user')" align="center">
        <template slot-scope="scope">
          <user-link :uuid="scope.row.createBy"></user-link>
        </template>
      </el-table-column>
      <!-- <el-table-column :label="$_t('operations')" align="center" min-width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" plain @click="handleNewCode(scope.$index, scope.row)">new
            code</el-button>
        </template>
      </el-table-column> -->
    </el-table>
    <template slot="footer">
      <el-row type="flex" align="middle" :gutter="20">
        <el-col :span="18">
          <el-pagination background @size-change="searchData" @current-change="searchData"
            :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40]" :page-size="queryParams.pageSize"
            layout="total, sizes, prev, pager, next" :total="totalCount"></el-pagination>
        </el-col>
      </el-row>
    </template>
  </d2-container>
</template>

<script>
import setting from "@/setting";
import userLink from "@/components/user-link";
import objectAttrForm from "@/components/object-attr-form";
const htcpmd_center = setting.api.htcpmd_center;
const apiPrefix = `${htcpmd_center}/server/`;
export default {
  name: "cluster-server-index",
  components: {
    objectAttrForm,
    userLink
  },
  data() {
    return {
      totalCount: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null
      },
      attrKeys: {
        uuid: "20629461ac32465da7c37c418912a499",
        label: "public-default-1",
        hostname: null,
        description: "text",
        createBy: "user-link",
        updateBy: "user-link",
        createTime: "datetime",
        updateTime: "datetime"
      },
      tableData: [
        {
          uuid: "uuid",
          label: "label",
          hostname: "hostname",
          description: "description",
          createBy: ""
        }
      ],
      expandRowKeys: [],
      tableLoading: true,
      formLabelWidth: "200px",
      dialogFormVisible: false,
      fullscreenLoading: false,
      socket: null
    };
  },
  methods: {
    handleTabSwitch(event, index, row) { },
    languagePath(key) {
      return `page.${this.$route.name.split("-").join(".")}.${key}`;
    },
    searchData() {
      this.tableLoading = true;
      this.$api.pageRequest(apiPrefix, this.queryParams).then(data => {
        this.tableData = data.rows;
        this.totalCount = data.total;
        this.tableLoading = false;
      });
    },
    onRowClick(row) {
      let idx = this.expandRowKeys.lastIndexOf(row.uuid);
      if (idx !== -1) {
        this.expandRowKeys.splice(idx, 1);
      } else {
        this.expandRowKeys = this.expandRowKeys.length > 5 ? this.expandRowKeys.slice(1).concat(row.uuid) : this.expandRowKeys.concat(row.uuid);
      }
    }
  },
  mounted() {
    this.searchData();
  },
  beforeDestroy() {
    this.tableLoading = false;
  }
};
</script>

<style lang="scss">
.cluster-server-container {
  .data-table {
    .el-table__body-wrapper {
      @include scrollBar();
    }
  }
}
</style>
