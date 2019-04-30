<template>
  <el-row :gutter="30">
    <el-col :span="6">
      <Menu :newsColumnId="newsColumn.id"/>
    </el-col>
    <el-col :span="18">
      <div class="div-content">
        <div class="title">{{ newsColumn.name }}</div>
        <div class="author">作者: 本站</div>
        <div class="content" v-html="newsColumn.content"></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import Menu from '../common/Menu';

  const detailUrl = '/app/news/newsColumn/detail';

  let id = null;

  export default {
    name: "NewsColumn",
    components: {Menu},
    data() {
      return {
        newsColumn: {
          id: null,
          name: null,
          createTime: null,
          content: null
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
        this.loadNewsColumn();
      },
      loadNewsColumn() {
        this.gateway.get({
          url: detailUrl,
          data: {id: id}
        })
          .then(resp => {
            this.newsColumn = resp.data;
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
