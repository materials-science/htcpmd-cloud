<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="任务名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入任务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联ID" prop="rid">
        <el-input
          v-model="queryParams.rid"
          placeholder="请输入关联ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联名称" prop="rname">
        <el-input
          v-model="queryParams.rname"
          placeholder="请输入关联名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="群组ID" prop="groupId">
        <el-input
          v-model="queryParams.groupId"
          placeholder="请输入群组ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['htcpmd-center:job:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['htcpmd-center:job:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['htcpmd-center:job:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['htcpmd-center:job:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jobList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="任务名称" align="center" prop="name" />
      <el-table-column label="任务描述" align="center" prop="description" />
      <el-table-column label="任务类型" align="center" prop="type" />
      <el-table-column label="关联ID" align="center" prop="rid" />
      <el-table-column label="关联名称" align="center" prop="rname" />
      <el-table-column label="任务状态" align="center" prop="status" />
      <el-table-column label="任务状态信息" align="center" prop="statusMsg" />
      <!-- <el-table-column
        label="任务日志"
        align="center"
        prop="logs"
        show-overflow-tooltip
      /> -->
      <el-table-column label="详细属性JSON" align="center" prop="attributes" />
      <el-table-column label="群组ID" align="center" prop="groupId" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleShowLogs(scope.row)"
            >Logs</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['htcpmd-center:job:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['htcpmd-center:job:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改Htcpmd业务任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入任务描述" />
        </el-form-item>
        <el-form-item label="关联ID" prop="rid">
          <el-input v-model="form.rid" placeholder="请输入关联ID" />
        </el-form-item>
        <el-form-item label="关联名称" prop="rname">
          <el-input v-model="form.rname" placeholder="请输入关联名称" />
        </el-form-item>
        <el-form-item label="任务状态信息" prop="statusMsg">
          <el-input v-model="form.statusMsg" placeholder="请输入任务状态信息" />
        </el-form-item>
        <el-form-item label="任务日志" prop="logs">
          <el-input
            v-model="form.logs"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="详细属性JSON" prop="attributes">
          <el-input
            v-model="form.attributes"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="群组ID" prop="groupId">
          <el-input v-model="form.groupId" placeholder="请输入群组ID" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="showLogs" append-to-body>
      <pre v-for="item in logDetail">
        <code>{{ item }}</code>
      </pre>
    </el-dialog>
  </div>
</template>

<script>
import {
  listJob,
  getJob,
  delJob,
  addJob,
  updateJob,
} from "@/api/htcpmd-center/job";

export default {
  name: "Job",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // Htcpmd业务任务表格数据
      jobList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        type: null,
        rid: null,
        rname: null,
        status: null,
        groupId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      showLogs: false,
      logDetail: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询Htcpmd业务任务列表 */
    getList() {
      this.loading = true;
      listJob(this.queryParams).then((response) => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        description: null,
        type: null,
        rid: null,
        rname: null,
        status: null,
        statusMsg: null,
        logs: null,
        attributes: null,
        groupId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delSts: 0,
        verNo: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加Htcpmd业务任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJob(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改Htcpmd业务任务";
      });
    },
    handleShowLogs(row) {
      this.showLogs = true;
      this.logDetail = row.logs.split("\n");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJob(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addJob(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除Htcpmd业务任务编号为"' + ids + '"的数据项？')
        .then(function () {
          return delJob(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "htcpmd-center/job/export",
        {
          ...this.queryParams,
        },
        `job_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
