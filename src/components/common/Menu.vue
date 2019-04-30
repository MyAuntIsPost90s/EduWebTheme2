<template>
  <div>
    <el-menu :default-active="'1'" :default-openeds="['1']">
      <el-submenu index="1" v-if="newsColumn.childs != null && newsColumn.childs.length > 0">
        <template slot="title">
          <span>{{ newsColumn.name }}</span>
        </template>
        <el-menu-item v-for="item in newsColumn.childs" :key="item.id" index="item.id" @click="toLink(item)">
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item v-else index="1" @click="toLink(newsColumn)">
        <span slot="title">{{ newsColumn.name }}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
  let detailUrl = '/app/news/newsColumn/detailForApp'

  export default {
    name: "Menu",
    props: ['newsColumnId'],
    data() {
      return {
        newsColumn: {
          id: null,
          name: null,
          childs: null
        }
      }
    },
    mounted(){
      if (this.newsColumnId) {
        this.loadData();
      }
    },
    methods: {
      loadData() {
        this.gateway.get({
          url: detailUrl
          , data: {id: this.newsColumnId}
        }).then(resp => {
          this.newsColumn = resp.data;
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
    },
    watch: {
      newsColumnId() {
        if (this.newsColumnId) {
          this.loadData();
        }
      }
    }
  }
</script>

<style scoped>

</style>
