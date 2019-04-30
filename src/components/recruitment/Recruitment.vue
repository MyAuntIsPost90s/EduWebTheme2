<template>
  <el-row :gutter="30">
    <el-col :span="6">
      <Menu :newsColumnId="'-1'"/>
    </el-col>
    <el-col :span="18">
      <div class="div-content">
        <div class="title">
          {{ recruitment.title }}
          <el-button v-if="recruitment.status==1" size="small" type="primary" style="float: right" @click="showAdd()">
            投递简历
          </el-button>
          <el-button v-else size="small" type="info" style="float: right">已结束</el-button>
        </div>
        <div class="date">发布时间:{{ recruitment.createTime }}</div>
        <div class="content" v-html="recruitment.content"></div>
      </div>
    </el-col>

    <el-dialog title="投递简历" :visible.sync="dialogVisible">
      <el-form :model="resume">
        <el-form-item label="姓名" :label-width="'80px'">
          <el-input v-model="resume.userRealname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学号" :label-width="'80px'">
          <el-input v-model="resume.studentCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" :label-width="'80px'">
          <el-input v-model="resume.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简历" :label-width="'80px'">
          <editor v-model="resume.content" style="height: 300px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>
  </el-row>
</template>

<script>
  import Menu from '../common/Menu';
  import Editor from '../common/Editor';

  let detailUrl = '/app/recruitment/recruitment/detail';
  let addUrl = '/app/recruitment/resume/add';

  export default {
    name: "Recruitment",
    components: {Menu, Editor},
    data() {
      return {
        recruitment: {
          id: null,
          title: null,
          content: null,
          createTime: null,
          status: null,
        },
        resume: {
          userRealname: null,
          studentCode: null,
          phone: null,
          content: null,
          valid: {
            userRealname: 'name:"姓名",notEmpty:true,length:[0,20]',
            phone: 'name:"联系方式",notEmpty:true,length:[0,20]',
            studentCode: 'name:"学号",notEmpty:true,length:[0,20]',
            content: 'name:"简历内容",notEmpty:true',
          }
        },
        dialogVisible: false,
      }
    },
    mounted() {
      this.init();
    },
    watch: {
      $route() {
        this.init();
      }
    },
    methods: {
      init() {
        if (this.$route.query == null || this.$route.query.id == null) {
          this.$router.push({name: 'Index'});
          return;
        }
        this.recruitment.id = this.$route.query.id;
        this.loadRecruitment();
      },
      showAdd() {
        this.resume = {valid: this.resume.valid};
        this.dialogVisible = true;
      },
      save() {
        if (!this.formValid.check(this.resume)) {
          this.$message.error(this.resume.e);
          return;
        }
        this.resume.recruitmentId = this.recruitment.id;
        let url = addUrl;
        this.gateway.post({
          url: url,
          data: this.resume
        }).then(resp => {
          this.dialogVisible = false;
          this.$message.success(resp.msg);
        }).catch(err => {
          console.log(err);
        });
      },
      loadRecruitment() {
        this.gateway.get({
          url: detailUrl,
          data: {id: this.recruitment.id}
        }).then(resp => {
          this.recruitment = resp.data;
        }).then(e => {
          console.log(e);
        })

      }
    }
  }
</script>

<style scoped>
  .div-content {
    background-color: #fff;
    padding: 20px;
    min-height: 300px;
    line-height: 24px;
  }

  .div-content .title {
    color: #000;
    font-size: 16px;
  }

  .div-content .date {
    color: #909399;
    font-size: 14px;
  }

  .div-content .author {
    color: #909399;
    font-size: 14px;
  }

  .div-content .content {
    word-break: break-all;
    color: #606266;
  }
</style>
