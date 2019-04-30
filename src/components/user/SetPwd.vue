<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">修改密码</div>
    </el-row>
    <div class="content">
      <el-form ref="form" :model="setPwdParam" label-width="80px">
        <el-form-item label="原密码">
          <el-input show-password v-model="setPwdParam.oldPwd"></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input show-password v-model="setPwdParam.pwd"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save()">确定</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import crypto from 'crypto';

  const currUserUrl = '/sys/user/user/currUser';
  const updateUserUrl = '/sys/user/user/update';
  let currUser = null;

  export default {
    name: "SetPwd",
    data() {
      return {
        oldPwd: '',
        setPwdParam: {
          oldPwd: '',
          pwd: '',
          valid: {
            oldPwd: 'name:"原密码",notEmpty:true',
            pwd: 'name:"新密码",notEmpty:true'
          }
        }
      }
    },
    mounted() {
      this.loadCurrUser();
    },
    methods: {
      save() {
        if (!this.formValid.check(this.setPwdParam)) {
          this.$message.error(this.setPwdParam.e);
          return;
        }
        //判断原密码
        let md5 = crypto.createHash('md5');
        md5.update(this.setPwdParam.oldPwd)
        if (currUser.password !== md5.digest('hex').toUpperCase()) {
          this.$message.error('原密码错误');
          return;
        }

        md5 = crypto.createHash('md5');
        md5.update(this.setPwdParam.pwd);
        let pwd = md5.digest('hex').toUpperCase();
        this.gateway.post({
          url: updateUserUrl
          , data: {id: currUser.id, password: pwd}
        }).then(resp => {
            this.$message.success(resp.msg);
            currUser.password = pwd;
          },
          e => {
            console.log(e);
          });
      },
      loadCurrUser() {
        this.gateway.get({url: currUserUrl})
          .then(resp => {
              currUser = resp.data;
            },
            e => {
              console.log(e);
            });
      }
    }
  }
</script>

<style scoped>

</style>
