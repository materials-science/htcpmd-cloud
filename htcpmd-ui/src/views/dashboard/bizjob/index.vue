<template>
    <d2-container class="dashboard-calctasks-container">
        <el-table v-loading="tableLoading" :data="tableData" :default-sort="{ prop: 'id', order: 'descending' }"
            @row-click="viewDetail" class="data-table" height="100%">
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-tabs type="border-card" @tab-click="
                        handleTabSwitch($event, props.$index, props.row)
                    ">
                        <el-tab-pane label="Attributes">
                            <el-form label-position="top" class="data-table-expand--wrap">
                                <el-form-item label="id">
                                    <el-input disabled :value="props.row.id"></el-input>
                                </el-form-item>
                                <el-form-item prop="name" label="task label">
                                    <el-input disabled :value="props.row.name"></el-input>
                                </el-form-item>
                                <el-form-item label="create time">
                                    <el-date-picker v-model="props.row.createTime" type="datetime"
                                        placeholder="create time" readonly>
                                    </el-date-picker>
                                </el-form-item>
                                <el-form-item prop="description" label="task description">
                                    <el-input disabled type="textarea" autosize
                                        :value="props.row.description"></el-input>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                        <el-tab-pane label="User">
                            <el-form label-position="left" class="data-table-expand--wrap">
                                <el-form-item v-for="(val, key) in props.row.user" :key="key" :label="key">
                                    <el-input disabled :value="val"></el-input>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                        <el-tab-pane label="Logs">
                            <el-card>
                                <d2-highlight :code="props.row.logs" lang="bash" />
                            </el-card>
                        </el-tab-pane>
                    </el-tabs>
                </template>
            </el-table-column>
            <el-table-column prop="id" label="id" align="center">
            </el-table-column>
            <el-table-column prop="name" label="name" sortable align="center">
            </el-table-column>
            <el-table-column prop="createBy" :label="$_t('createBy')" align="center">
            </el-table-column>
            <el-table-column prop="createTime" label="created time" sortable align="center">
                <template slot-scope="props">
                    <el-date-picker v-model="props.row.createTime" type="datetime" placeholder="created time" readonly
                        class="data-date-picker">
                    </el-date-picker>
                </template>
            </el-table-column>
            <!-- TODO: Filter -->
            <el-table-column label="status" align="center">
                <template slot-scope="props">
                    <i class="el-icon-warning-outline" v-if="props.row.status == 'failed'"></i>
                    <i class="el-icon-loading" v-else-if="
                        ['running', 'waiting'].includes(props.row.status)
                    "></i>
                    <i class="el-icon-check" v-else-if="props.row.status == 'completed'"></i>
                    <i class="el-icon-question" v-else></i>
                    <span>{{ props.row.status }}</span>
                </template>
            </el-table-column>
            <el-table-column :label="$_t('operations')" align="center" min-width="150">
                <template slot-scope="scope">
                    <el-button size="mini" type="danger" plain
                        @click="handleDelete(scope.$index, scope.row)">delete</el-button>
                </template>
            </el-table-column>
        </el-table>
        <template slot="footer">
            <el-pagination background @size-change="handlePageSizeChange" @current-change="handleCurrentPageChange"
                :current-page="currentPage" :page-sizes="[2, 10, 20, 30, 40]" :page-size="pageSize"
                layout="total, sizes, prev, pager, next" :total="totalCount"></el-pagination>
        </template>
    </d2-container>
</template>

<script>
import setting from '@/setting';

const apiPrefix = `${setting.api.htcpmd_center}/bizjob/`
export default {
    name: "dashboard-bizjob",
    data() {
        return {
            tableData: [
                {
                    id: "1325121640423690200",
                    celery_id: "e96741e4-f36a-448c-affa-4d7a1bcb70ef",
                    user: { id: "1325121640423690200", name: "ias" },
                    code: {},
                    status: "failed"
                },
                {
                    id: "1325121640423690200",
                    user: "IAS",
                    status: "watting"
                },
                {
                    id: "1325121640423690200",
                    user: "IAS",
                    status: "running"
                }
            ],
            tableLoading: true,
            currentPage: 1,
            totalCount: 0,
            pageSize: 10,
            // queryForm: { status_exclude: "failed,completed" }
            queryForm: {}
        };
    },
    methods: {
        pageRequest(
            page = this.currentPage,
            size = this.pageSize,
            options = this.queryForm
        ) {
            return this.$api.GetList(
                apiPrefix + "list",
                Object.assign({ page, size }, options)
            );
        },
        handleCurrentPageChange(page) {
            this.tableLoading = true;
            this.pageRequest(page).then(data => {
                this.tableData = data.rows;
                this.totalCount = data.total;
                this.currentPage = page;
                this.tableLoading = false;
            });
        },
        handlePageSizeChange(size) {
            this.tableLoading = true;
            this.pageSize = size;
            this.pageRequest().then(data => {
                this.tableData = data.rows;
                this.totalCount = data.total;
                this.tableLoading = false;
            });
        },
        viewDetail(row, column, event) {
            let id = row.id;
            if (id == "") {
                this.$message.error("ID is None!");
            }
            // this.$router.push({
            // 	path: `/data/tasks/${id}`
            // });
        },
        handleDelete(index, row) {
            if (row.id == "") {
                return this.$message.error("ID is None!");
            }
            this.$confirm("This will delete permanently, sure?", "Warning", {
                confirmButtonText: "confirm",
                cancelButtonText: "cancel",
                type: "warning"
            })
                .then(() => {
                    this.tableLoading = true;
                    this.$api.DelObj(apiPrefix, row.id).then(resp => {
                        if (resp.code == 200) {
                            this.$message.success("Delete Success");
                            return this.handleCurrentPageChange(
                                this.currentPage
                            );
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
        handleTabSwitch() { }
    },
    mounted() {
        this.pageRequest().then(data => {
            this.tableData = data.rows;
            this.totalCount = data.total;
            this.tableLoading = false;
        });
    }
};
</script>
<style lang="scss">
.dashboard-tasks-container {}
</style>
