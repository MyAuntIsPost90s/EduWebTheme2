<template>
  <el-row :gutter="30">
    <el-col :span="6">
      <Menu :newsColumnId="'-1'"/>
    </el-col>
    <el-col :span="18">
      <div class="div-content">
        <div class="title">
          {{ active.title }}
          <el-button v-if="active.status==1" size="small" type="primary" style="float: right" @click="showAdd()">我要报名
          </el-button>
          <el-button v-else size="small" type="info" style="float: right">已结束</el-button>
        </div>
        <div class="date">发布时间:{{ active.createTime }}</div>
        <div class="join-count">报名人数:{{ active.joinCount }}</div>
        <div class="content" v-html="active.content"></div>
      </div>
    </el-col>

    <el-dialog title="申请加入" :visible.sync="dialogVisible" :label-width="'80px'">
      <el-form :model="activeUser">
        <el-form-item label="姓名">
          <el-input v-model="activeUser.userRealname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="activeUser.studentCode" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="activeUser.phone" autocomplete="off"></el-input>
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

  let detailUrl = '/app/active/active/detail';
  let addUrl = '/app/active/activeUser/add';

  export default {
    name: "Active",
    components: {Menu},
    data() {
      return {
        active: {
          id: null,
          title: null,
          createTime: null,
          content: null,
          status: null,
          joinCount: null
        },
        activeUser: {
          userRealname: null,
          studentCode: null,
          phone: null,
          valid: {
            userRealname: 'name:"姓名",notEmpty:true,length:[0,20]',
            phone: 'name:"联系方式",notEmpty:true,length:[0,20]',
            studentCode: 'name:"学号",notEmpty:true,length:[0,20]',
          }
        },
        dialogVisible: false,
      }
    },
    mounted() {
      this.init();
    },
    watch: {
      $router() {
        this.init();
      }
    },
    methods: {
      init() {
        if (this.$route.query == null || this.$route.query.id == null) {
          this.$router.push({name: 'Index'});
          return;
        }
        this.active.id = this.$route.query.id;
        this.loadActive();
      },
      showAdd() {
        this.activeUser = {valid: this.activeUser.valid};
        this.dialogVisible = true;
      },
      save() {
        if (!this.formValid.check(this.activeUser)) {
          this.$message.error(this.activeUser.e);
          return;
        }
        this.activeUser.activeId = this.active.id;
        let url = addUrl;
        this.gateway.post({
          url: url,
          data: this.activeUser
        }).then(resp => {
          this.dialogVisible = false;
          this.$message.success(resp.msg);
        }).catch(err => {
          console.log(err);
        });
      },
      loadActive() {
        this.gateway.get({
          url: detailUrl,
          data: {id: this.active.id}
        }).then(resp => {
          this.active = resp.data;
        }).catch(e => {
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

  .div-content .date, .div-content .join-count{
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
