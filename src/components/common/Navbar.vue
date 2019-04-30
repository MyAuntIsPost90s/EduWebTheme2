<template>
  <el-menu
    :router="true"
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal">
    <el-menu-item index="Welcome">
       <span slot="title">首页</span>
    </el-menu-item>
    <el-dropdown @command="toCommand" style="float: right;margin-top: 17px;">
      <span class="sp-user-info">
        <img class="img-head"
             :src="user.headPicUrl?user.headPicUrl:'https://fs.591iq.cn/group1/M00/0C/4A/rBKYP1wtZySAVOwJAADbQioY8Y4860.png'"/>
        <span class="sp-name">{{ user.nickname }} <i class="i-icon el-icon-arrow-down el-icon--right"></i></span>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="SetPwd"><i class="el-icon-info"></i><span>个人中心</span></el-dropdown-item>
        <el-dropdown-item command="Login"><i class="el-icon-error"></i><span>退出</span></el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-menu>
</template>

<script>
  const currUserUrl = '/sys/user/user/currUser';

  export default {
    name: 'NavBar',
    data() {
      return {
        user: {
          nickname: '小明'
          , headPicUrl: null
        },
        activeIndex: 'Welcome'
      };
    },
    mounted() {
      this.loadData();
    },
    methods: {
      loadData() {
        this.gateway.get({url: currUserUrl})
          .then(resp => {
              this.user = resp.data;
            },
            e => {
              console.log(e);
            });
      },
      toCommand(name) {
        this.$router.push({name: name});
      }
    }
  }
</script>

<style scoped>
  .sp-user-info {
    display: inline-block;
    padding-right: 20px;
  }

  .img-head {
    height: 25px;
    width: 25px;
    border-radius: 50%;
    margin-right: 5px;
    display: inline-block;
  }

  .sp-user-info .sp-name {
    overflow: hidden;
    display: inline-block;
    height: 24px;
  }

</style>
