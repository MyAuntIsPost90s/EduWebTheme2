<template>
  <el-row class="nav">
    <div class="item"><span slot="title"><img :src="logo?logo:'../../../static/common/images/logo.png'"></span></div>
    <div class="item" v-for="item in newsColumns" :key="item.id">
      <div class="text">
        <span v-if="item.childs == null || item.childs.length<1" @click="toLink(item)">
          {{ item.name }}
        </span>
        <el-dropdown v-else @command="toLink">
          <span class="el-dropdown-link">
            {{ item.name }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="child in item.childs" :key="child.id" :command="child">{{ child.name }}</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </el-row>
</template>

<script>
  const listUrl = '/app/news/newsColumn/listForApp';

  export default {
    name: 'NavBar',
    props: ['logo'],
    data() {
      return {
        newsColumns: []
      };
    },
    mounted() {
      this.loadData();
    },
    methods: {
      loadData() {
        this.gateway.get({url: listUrl})
          .then(resp => {
            this.newsColumns = resp.data;
          })
          .catch(e => {
            console.log(e);
          })
      },
      toLink(newsColumn) {
        if (newsColumn.type == 0) {
          this.$router.push({name: 'Index'});
        }
        if (newsColumn.type == 1) {
          this.$router.push({
            name: 'NewsList'
            , query: {
              newsColumnId: newsColumn.id
              , newsColumnName: newsColumn.name
            }
          });
        }
        if (newsColumn.type == 2) {
          this.$router.push({name: 'NewsColumn', query: {id: newsColumn.id}});
        }
        if (newsColumn.type == 3) {
          window.open(newsColumn.link);
        }
      }
    }
  }
</script>

<style scoped>
  .nav .item {
    color: #606266;
    float: left;
    text-align: center;
    padding: 0 15px;
    cursor: default;
  }

  .nav .item img {
    margin-top: 5px;
  }

  .nav .item .text {
    padding-top: 20px;
  }

  .sp-user-info .sp-name {
    overflow: hidden;
    display: inline-block;
    height: 24px;
  }

</style>
