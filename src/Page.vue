<template>
  <el-container v-bind:style="{ height:height }">
    <el-container v-bind:style="{ height:'100%' }">
      <el-header class="main-header">
        <NavBar class="nav-bar" :logo="sysConfig.logoPicUrl"/>
      </el-header>
      <el-main class="main-contain">
        <div style="width: 1280px;margin: auto">
          <router-view/>
        </div>
        <el-row class="main-footer">
          <div>{{ sysConfig.copyright }}</div>
          <div>地址：{{ sysConfig.address }}</div>
          <div>邮编：{{ sysConfig.zipCode }}</div>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import NavBar from './components/common/Navbar'

  const configUrl = '/app/config/sysConfig/detail';

  export default {
    name: "Page",
    components: {NavBar},
    data() {
      return {
        height: (innerHeight) + 'px',
        sysConfig: {
          copyright: null,
          address: null,
          zipCode: null,
          logoPicUrl: null,
        }
      }
    },
    mounted() {
      this.loadSysConfig();
    },
    methods: {
      loadSysConfig() {
        this.gateway.get({url: configUrl})
          .then(resp => {
            this.sysConfig = resp.data;
          })
          .catch(e => {
            console.log(e);
          })
      }
    }
  }
</script>

<style>

</style>
