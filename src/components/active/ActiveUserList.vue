<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">活动管理</div>
    </el-row>
    <div class="content">
      <el-row>
        <span @click="back()">
          <i class="el-icon-arrow-left"></i>
          返回
        </span>
        <el-button type="primary" @click="exportExcel()" size="small" style="float: right" icon="el-icon-download">导出
        </el-button>
      </el-row>
      <el-row>
        <el-table
          :data="activeUsers"
          stripe
          style="width: 100%">
          <el-table-column
            prop="userRealname"
            label="姓名">
          </el-table-column>
          <el-table-column
            prop="studentCode"
            label="学号">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="手机号">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="报名时间">
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button size="small" icon="el-icon-delete" type="danger" @click="del(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row class="table-foot">
          <el-col :span="24">
            <el-pagination
              class="table-page"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="page"
              :page-sizes="[10,50,100]"
              :page-size="rows"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </el-col>
        </el-row>
      </el-row>
    </div>
  </div>
</template>

<script>
  import ElRow from "element-ui/packages/row/src/row";
  import Config from '../../../static/common/js/config';

  const listUrl = '/sys/active/activeUser/list';
  const deleteUrl = '/sys/active/activeUser/delete';
  const exportUrl = '/sys/active/activeUser/exportExcel';

  let activeId = null;

  export default {
    components: {
      ElRow
    },
    name: "ActiveUserList",
    data() {
      return {
        rows: 10,
        page: 1,
        total: 0,
        level: 1,
        activeUsers: []
      }
    },
    mounted() {
      if (this.$route.params == null || this.$route.params.activeId == null) {
        this.$router.push({name: 'ActiveList'});
        return;
      }
      activeId = this.$route.params.activeId;
      this.loadActiveUsers();
    },
    methods: {
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadActiveUsers();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadActiveUsers();
      },
      back() {
        this.$router.go(-1);
      },
      loadActiveUsers() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows, activeId: activeId}
        }).then(resp => {
          this.total = resp.data.total;
          this.activeUsers = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      exportExcel() {
        this.gateway.download(Config.URL_HEAD + exportUrl, {activeId: activeId});
      },
      del(id) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.gateway.get({
            url: deleteUrl,
            data: {ids: id}
          }).then(resp => {
            this.loadActiveUsers();
            this.$message.success(resp.message);
          }).catch(e => {
            console.log(e);
          })
        });
      }
    }
  }
</script>

<style scoped>
  .table-toolbar {
    margin-bottom: 20px;
  }

  .table-tool {
    text-align: right;
  }

  .table-page {
    float: right;
  }

  .table-foot {
    margin-top: 20px;
  }
</style>
