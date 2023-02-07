<template>
  <d2-container type="full" class="cluster-computers-container">
    <el-row class="d2-mb-8">
      <el-col :span="6">
        <!-- <el-button class="add-new-button" plain type="primary" @click="addNewComputers">Add New Computers</el-button> -->
        <select-server-env @update="updateSelectEnv"></select-server-env>
      </el-col>
      <el-col :span="18" style="text-align: end">
        <el-button class="add-new-button" plain type="primary" @click="addNewComputers">{{
          $_t("Add New Computers", true)
        }}</el-button>
      </el-col>
    </el-row>
    <el-table v-loading="tableLoading" :data="tableData" style="width: 100%"
      :default-sort="{ prop: 'uuid', order: 'descending' }" class="data-table"
      header-row-class-name="data-table-header-light-gray">
      <el-table-column type="expand" fixed>
        <template slot-scope="props">
          <el-tabs type="border-card" @tab-click="
            handleTabSwitch($event, props.$index, props.row)
          ">
            <el-tab-pane label="Attributes">
              <computer-attr-form :object="props.row"></computer-attr-form>
            </el-tab-pane>
            <el-tab-pane label="Test" @tab-click="handleTest(props.$index, props.row)">
              <el-card>
                <transition-group name="log-list" tag="div">
                  <d2-highlight v-for="item in testLogs[props.row.uuid]" :key="item.id" :code="item.msg" lang="bash"
                    :class="`log--${item.type}`" />
                </transition-group>
              </el-card>
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
      <el-table-column :label="$_t('operations')" align="center" min-width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" plain @click="handleNewCode(scope.$index, scope.row)">new
            code</el-button>
          <!-- TODO: editable -->
          <!-- <el-button
						size="mini"
						type="warning"
						plain
						@click="handleEdit(scope.$index, scope.row)"
						>Edit</el-button
					> -->
          <el-button size="mini" type="danger" plain @click="handleDelete(scope.$index, scope.row)">delete</el-button>
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
    <el-dialog title="Add A New Computer" :center="true" :visible.sync="dialogFormVisible">
      <el-form :model="form" :rules="formRules" ref="form" label-position="left">
        <el-form-item label="ssh configure" :label-width="formLabelWidth">
          <el-popover placement="bottom" :title="$t(languagePath('rsa_pub_tip.desc'))" width="300">
            <el-link type="primary" href="https://aiida.readthedocs.io/projects/aiida-core/en/latest/howto/ssh.html"
              target="_blank">help: click here for details</el-link>
            <d2-highlight :code="rsa_pub" />
            <el-button slot="reference" @click="showRSAPub" type="primary" plain>{{
              $t(languagePath("rsa_pub_tip.title"))
            }}</el-button>
          </el-popover>
        </el-form-item>
        <el-form-item :label="$_t('label')" prop="label" :label-width="formLabelWidth">
          <el-input v-model="form.label" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item :label="$_t('description')" prop="description" :label-width="formLabelWidth">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="hostname" prop="hostname" :label-width="formLabelWidth">
          <el-input v-model="form.hostname" autocomplete="off" placeholder="127.0.0.1"></el-input>
        </el-form-item>
        <el-form-item label="port" prop="port" :label-width="formLabelWidth">
          <el-input v-model="form.port" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="remote username" prop="username" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="transport type" prop="transport_type" :label-width="formLabelWidth">
          <el-select v-model="form.transport_type" placeholder="select transport type">
            <el-option label="remote ssh" value="ssh"></el-option>
            <el-option label="local computer" value="local" disabled></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="scheduler type" prop="scheduler_type" :label-width="formLabelWidth">
          <el-select v-model="form.scheduler_type" placeholder="select scheduler type">
            <el-option label="Direct" value="direct"></el-option>
            <el-option label="PBSPro" value="pbspro"></el-option>
            <el-option label="SLURM" value="slurm"></el-option>
            <el-option label="Oracle Grid Engine" value="sge"></el-option>
            <el-option label="LSF" value="lsf" disabled></el-option>
            <el-option label="Torque" value="torque"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="workdir" prop="workdir" :label-width="formLabelWidth">
          <el-input v-model="form.workdir" autocomplete="off"
            placeholder="make sure you have permission to access. (/home/user/workdir)"></el-input>
        </el-form-item>
        <el-form-item label="shebang" prop="shebang" :label-width="formLabelWidth">
          <el-input v-model="form.shebang" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="mpirun command" prop="mpirun_command" :label-width="formLabelWidth">
          <el-input v-model="form.mpirun_command" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="prepend text" prop="prepend_text" :label-width="formLabelWidth">
          <el-input v-model="form.prepend_text" autocomplete="off" placeholder="command before"></el-input>
        </el-form-item>
        <el-form-item label="append text" prop="append_text" :label-width="formLabelWidth">
          <el-input v-model="form.append_text" autocomplete="off" placeholder="command after"></el-input>
        </el-form-item>
        <el-form-item label="default mpi procs per machine" prop="default_mpiprocs_per_machine"
          :label-width="formLabelWidth">
          <el-input type="number" v-model.number="form.default_mpiprocs_per_machine" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="minimum job poll interval" prop="minimum_job_poll_interval" :label-width="formLabelWidth">
          <el-input v-model.number="form.minimum_job_poll_interval" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="key policy" prop="key_policy" :label-width="formLabelWidth">
          <el-select v-model="form.key_policy" placeholder="select key policy">
            <el-option label="RejectPolicy" value="RejectPolicy"></el-option>
            <el-option label="WarningPolicy" value="WarningPolicy"></el-option>
            <el-option label="AutoAddPolicy" value="AutoAddPolicy"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="newComputersSubmit('form')"
          v-loading.fullscreen.lock="fullscreenLoading">Submit</el-button>
      </span>
    </el-dialog>
    <el-dialog title="add a new code" :center="true" :visible.sync="newCodeDialogVisible">
      <code-add :computer="selectedComputer" v-on:cancel="newCodeDialogVisible = false"></code-add>
    </el-dialog>
  </d2-container>
</template>

<script>
import * as clipboard from "clipboard-polyfill";
// import {
// 	openWebsocket,
// 	sendWebsocket,
// 	closeWebsocket
// } from "@/libs/websocket.js";
import util from "@/libs/util";
import CodeAdd from "../codes/code-add";
import userLink from "@/components/user-link";
import computerAttrForm from "./components/computer-attr-form.vue";
import selectServerEnv from "../components/select-server-env.vue";
import setting from "@/setting";
const htcpmd_center = setting.api.htcpmd_center;
const apiPrefix = `${htcpmd_center}/computer/`;
const wsPrefix = "/computer/";
export default {
  name: "cluster-computers",
  components: {
    CodeAdd,
    computerAttrForm,
    userLink,
    selectServerEnv
  },
  data() {
    return {
      totalCount: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null
      },
      tableData: [
        {
          uuid: "9676960c-8547-4fad-8614-954e0760715c",
          createBy: "",
          label: "computer.label",
          hostname: "computer.hostname",
          description: "computer.description",
          transport_type: "computer.transport_type",
          scheduler_type: "computer.scheduler_type",
          workdir: "computer.get_workdir()",
          shebang: "computer.get_shebang()",
          mpirun_command: "computer.get_mpirun_command()",
          prepend_text: "computer.get_prepend_text()",
          append_text: "computer.get_append_text()"
        }
      ],
      tableLoading: true,
      formLabelWidth: "200px",
      form: {
        label: "",
        username: "root",
        password: null,
        hostname: "localhost",
        description: "localhost computer only for test",
        transport_type: "ssh",
        scheduler_type: "direct",
        workdir: "",
        shebang: "#!/bin/bash",
        mpirun_command: "mpirun -np {tot_num_mpiprocs}",
        prepend_text: "",
        append_text: "",
        minimum_job_poll_interval: 10,
        default_mpiprocs_per_machine: 4,
        port: 22,
        key_policy: "AutoAddPolicy"
      },
      formRules: {
        label: [{ required: true, trigger: "blur" }],
        username: [{ required: true, trigger: "blur" }],
        hostname: [{ required: true, trigger: "blur" }],
        workdir: [{ required: true, trigger: "blur" }],
        shebang: [{ required: true, trigger: "blur" }],
        mpirun_command: [{ required: true, trigger: "blur" }],
        minimum_job_poll_interval: [
          { required: true, type: "integer", min: 0, trigger: "blur" }
        ],
        default_mpiprocs_per_machine: [
          { required: true, type: "integer", min: 1, trigger: "blur" }
        ],
        port: [{ required: true, trigger: "blur" }]
      },
      dialogFormVisible: false,
      rsa_pub: "",
      RSAPubVisible: false,
      fullscreenLoading: false,
      socket: null,
      testLogs: {},
      selectedComputer: "",
      newCodeDialogVisible: false,
      // server env
      serverEnv: null
    };
  },
  methods: {
    handleTabSwitch(event, index, row) {
      if (event.label == "Test") {
        this.handleTest(index, row);
      }
    },
    languagePath(key) {
      return `page.${this.$route.name.split("-").join(".")}.${key}`;
    },
    getRSAPub() {
      this.$api.GetObj("/security/", "rsa_pub").then(resp => {
        this.rsa_pub = resp.data;
      });
    },
    searchData() {
      this.tableLoading = true;
      this.$api.pageRequest(apiPrefix, this.queryParams).then(data => {
        this.tableData = data.rows;
        this.totalCount = data.total;
        this.tableLoading = false;
      });
    },
    addNewComputers() {
      this.dialogFormVisible = true;
    },
    newComputersSubmit(formName) {
      this.fullscreenLoading = true;
      this.$refs[formName].validate(valid => {
        if (valid) {
          let data = Object.assign({}, this.form);
          data.mpirun_command = data.mpirun_command.split(" ");
          data.user = util.cookies.get("uuid");
          this.$api.AddObj(apiPrefix, data).then(resp => {
            if (resp.code == 200) {
              this.$message.success("Upload Success!");
            }
            this.searchData();
            this.fullscreenLoading = false;
            this.dialogFormVisible = false;
          });
        } else {
          this.$notify.error("Invaid Input");
          this.fullscreenLoading = false;
          return false;
        }
      });
    },
    showRSAPub() {
      this.RSAPubVisible = true;
      clipboard.writeText(this.rsa_pub);
      this.$message.success("copied id_rsa.pub to clipboard");
    },
    handleDelete(index, row) {
      if (row.uuid == "") {
        return this.$message.error("UUID is None!");
      }
      this.$confirm("This will delete permanently, sure?", "Warning", {
        confirmButtonText: "confirm",
        cancelButtonText: "cancel",
        type: "warning"
      })
        .then(() => {
          this.tableLoading = true;
          this.$api.DelObj(apiPrefix, row.uuid).then(resp => {
            if (resp.code == 200) {
              this.$message.success("Delete Success");
              return this.searchData();
            }
            this.tableLoading = false;
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "canceled"
          });
        });
    },
    handleTest(index, row) {
      if (row.uuid == "") {
        return this.$message.error("UUID is None!");
      }
      if (!(row.uuid in this.testLogs)) {
        this.$confirm(
          "This will make a series of tests to check if connection is available",
          "Notice",
          {
            confirmButtonText: "confirm",
            cancelButtonText: "cancel",
            type: "info"
          }
        )
          .then(() => {
            this.tableLoading = true;
            this.$set(this.testLogs, row.uuid, []);
            this.currentTestComputer = row.uuid;
            // this.socket = openWebsocket(
            // 	`${wsPrefix}${row.uuid}/test/`,
            // 	this.onWSMessage,
            // 	this.onWSError
            // );
            this.$connect(
              util.getWebsocketUrl(`${wsPrefix}${row.uuid}/test/`)
            );
            this.socket = this.$socket;
            this.socket.onmessage = data => this.onWSMessage(data);
          })
          .catch(e => {
            this.$log.push({
              message: e.toString(),
              type: "danger"
            });
            console.error(e);
            this.$message({
              type: "info",
              message: "canceled"
            });
          });
      }
    },
    closeComputerTest() {
      // closeWebsocket();
      this.socket && this.socket.close();
      this.tableLoading = false;
    },
    onWSMessage(data) {
      data = JSON.parse(data.data);
      this.tableLoading = false;
      console.log(data);
      data.id = new Date().getTime();
      let logs = this.testLogs[this.currentTestComputer];
      logs.push(data);
      this.$set(this.testLogs, this.currentTestComputer, logs);
    },
    onWSError() { },
    handleNewCode(index, row) {
      if (row.uuid == "") {
        return this.$message.error("UUID is None!");
      }
      this.newCodeDialogVisible = true;
      this.selectedComputer = row.uuid;
    },
    updateSelectEnv(selected) {
      this.serverEnv = selected;
    }
  },
  mounted() {
    this.searchData();
    // this.getRSAPub();
  },
  beforeDestroy() {
    this.tableLoading = false;
    // closeWebsocket();
    this.socket && this.socket.close();
  }
};
</script>

<style lang="scss">
.cluster-computers-container {
  .data-table {
    .el-table__body-wrapper {
      @include scrollBar();
    }
  }

  .data-table-header-light-gray {
    background-color: #f8f8f9;
    color: #515a6e;

    th,
    tr {
      background-color: inherit;
    }
  }

  .d2-highlight {
    border-radius: initial !important;
  }

  .log-list-enter-active,
  .list-leave-active {
    transition: all 1s;
  }

  .log-list-enter,
  .list-leave-to {
    opacity: 0;
    transform: translateY(30px);
  }
}
</style>
