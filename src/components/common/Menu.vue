<template>
  <div>
    <el-row class="logo">
      <i class="logo-icon"></i>系统管理中心
    </el-row>
    <el-row>
      <el-menu
        class="el-menu-vertical-demo"
        :router="true"
        @open="handleOpen"
        @close="handleClose">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-tickets"></i>
            <span>新闻中心</span>
          </template>
          <el-menu-item index="/News/NewsColumnList1">
            <span slot="title">一级栏目</span>
          </el-menu-item>
          <el-menu-item index="/News/NewsColumnList2">
            <span slot="title">二级栏目</span>
          </el-menu-item>
          <el-menu-item index="/News/NewsList">
            <span slot="title">新闻</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item index="/Active/ActiveList">
          <i class="el-icon-menu"></i>
          <span slot="title">活动管理</span>
        </el-menu-item>
        <el-menu-item index="/Recruitment/RecruitmentList">
          <i class="el-icon-star-off"></i>
          <span slot="title">部门招聘</span>
        </el-menu-item>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-setting"></i>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/Config/BannerList">
            <span slot="title">轮播图配置</span>
          </el-menu-item>
          <el-menu-item index="/Config/IndexConfig">
            <span slot="title">首页配置</span>
          </el-menu-item>
          <el-menu-item index="/Config/SysConfig">
            <span slot="title">系统配置</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "Menu",
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>

<style scoped>
  .logo {
    padding-left: 20px;
    line-height: 60px;
    font-weight: 600;
    font-size: 20px;
    font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
    vertical-align: middle;
    box-shadow: 1px 1px 0 0 #e8e8e8;
    z-index: 1;
  }

  .logo-icon {
    width: 40px;
    height: 40px;
    float: left;
    background-image: url("../../../static/common/images/logo.png");
    background-size: 100%;
    margin-right: 10px;
    margin-top: 8px;
  }
</style>
