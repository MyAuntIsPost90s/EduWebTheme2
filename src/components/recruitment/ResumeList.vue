<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">部门招聘</div>
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
          :data="resumes"
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
              <el-button size="small" icon="el-icon-search" type="primary" @click="show(scope.row)">查看</el-button>
              <el-button v-if="scope.row.status==1" size="small" icon="el-icon-star-off" type="primary"
                         @click="toggleStatus(scope.row.id,2)">标记处理
              </el-button>
              <el-button v-if="scope.row.status==2" size="small" icon="el-icon-star-on" type="primary"
                         @click="toggleStatus(scope.row.id,1)">取消标记
              </el-button>
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

    <el-dialog title="简历" :visible.sync="dialogVisible">
      <el-form :model="resume">
        <el-form-item label="标题" :label-width="'80px'">
          <el-input v-model="resume.title" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="'80px'">
          <el-input v-model="resume.userRealname" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="学号" :label-width="'80px'">
          <el-input v-model="resume.studentCode" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="联系方式" :label-width="'80px'">
          <el-input v-model="resume.phone" autocomplete="off" readonly></el-input>
        </el-form-item>
        <el-form-item label="活动状态" :label-width="'80px'">
          <el-radio-group v-model="resume.status" readonly>
            <el-radio :label="1">进行中</el-radio>
            <el-radio :label="2">关闭</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="活动内容" :label-width="'80px'">
          <editor v-model="resume.content" style="height: 500px" readonly></editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ElRow from "element-ui/packages/row/src/row";
  import Editor from '../common/Editor';
  import Config from '../../../static/common/js/config';

  const listUrl = '/sys/recruitment/resume/list';
  const deleteUrl = '/sys/recruitment/resume/delete';
  const updateUrl = '/sys/recruitment/resume/update';
  const exportUrl = '/sys/recruitment/resume/exportExcel';

  let recruitmentId = null;

  export default {
    components: {
      ElRow,
      Editor,
    },
    name: "ResumeList",
    data() {
      return {
        rows: 10,
        page: 1,
        total: 0,
        level: 1,
        resumes: [],
        resume: {
          userRealname: null,
          phone: null,
          studentCode: null,
          content: null
        },
        dialogVisible: false,
      }
    },
    mounted() {
      if (this.$route.params == null || this.$route.params.recruitmentId == null) {
        this.$router.push({name: 'RecruitmentList'});
        return;
      }
      recruitmentId = this.$route.params.recruitmentId;
      this.loadResumes();
    },
    methods: {
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadResumes();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadResumes();
      },
      back() {
        this.$router.go(-1);
      },
      loadResumes() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows, recruitmentId: recruitmentId}
        }).then(resp => {
          this.total = resp.data.total;
          this.resumes = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      show(resume) {
        this.resume = resume;
        this.dialogVisible = true;
      },
      toggleStatus(id, status) {
        this.gateway.get({
          url: updateUrl,
          data: {id: id, status: status}
        }).then(resp => {
          this.loadResumes();
        }).catch(e => {
          console.log(e);
        });
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
            this.loadResumes();
            this.$message.success(resp.message);
          }).catch(e => {
            console.log(e);
          })
        });
      },
      exportExcel() {
        this.gateway.download(Config.URL_HEAD + exportUrl, {recruitmentId: recruitmentId});
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
