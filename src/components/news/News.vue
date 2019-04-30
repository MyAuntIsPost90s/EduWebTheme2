<template>
  <el-row :gutter="30">
    <el-col :span="6">
      <Menu :newsColumnId="news.newsColumnId"/>
    </el-col>
    <el-col :span="18">
      <div class="div-content">
        <div class="title">{{ news.title }}</div>
        <div class="author">作者:{{ news.author }}</div>
        <div class="date">发布时间:{{ news.publishTime }}</div>
        <div class="date">浏览量:{{ news.scanCount }}</div>
        <div class="content" v-html="news.content"></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import Menu from '../common/Menu';

  const newsUrl = '/app/news/news/readDetail';

  let id = null;

  export default {
    name: "News",
    components: {Menu},
    data() {
      return {
        news: {
          id: null,
          title: null,
          author: null,
          publishTime: null,
          scanCount: 0,
          content: null,
          newsColumnId: null,
        }
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
        id = this.$route.query.id;
        this.loadNews();
      },
      loadNews() {
        this.gateway.get({
          url: newsUrl,
          data: {id: id}
        })
          .then(resp => {
            this.news = resp.data;
          })
          .catch(e => {
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
    overflow-x: hidden;
  }
</style>
