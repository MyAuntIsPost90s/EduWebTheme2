<template>
  <el-row :gutter="30">
    <el-col :span="6">
      <Menu :newsColumnId="newsColumnId"/>
    </el-col>
    <el-col :span="18" class="div-list">
      <el-row class="div-title">{{ newsColumnName }}</el-row>
      <List :data="newsList"/>
      <el-row class="div-page">
        <el-pagination
          :current-page="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="[rows, rows+20, rows+40, rows+60]"
          :page-size="rows"
          layout="total, sizes, prev, pager, next, jumper"
          :total="this.total">
        </el-pagination>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
  import Menu from '../common/Menu';
  import List from '../common/List';

  const listUrl = '/app/news/news/list';

  export default {
    name: "NewsList",
    components: {Menu, List},
    data() {
      return {
        newsColumnId: null,
        newsColumnName: null,
        page: 1,
        rows: 15,
        total: 0,
        newsList: []
      }
    },
    watch: {
      $route() {
        this.init();
      }
    },
    mounted() {
      this.init();
    },
    methods: {
      init() {
        if (this.$route.query == null || this.$route.query.newsColumnId == null) {
          this.$router.push({name: 'Index'});
          return;
        }
        this.newsColumnId = this.$route.query.newsColumnId;
        this.newsColumnName = this.$route.query.newsColumnName;
        this.loadNews();
      },
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadNews();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadNews();
      },
      loadNews() {
        this.gateway.get({
          url: listUrl,
          data: {
            page: this.page,
            rows: this.rows,
            newsColumnId: this.newsColumnId
          }
        }).then(resp => {
          this.newsList = resp.data.rows;
          this.total = resp.data.total;
        }).catch(e => {
          console.log(e);
        })
      }
    }
  }
</script>

<style scoped>
  .div-list {
    background-color: #fff;
    min-height: 300px;
    padding: 20px;
  }

  .div-title {
    font-size: 16px;
    font-weight: 600;
    padding: 10px 0;
  }

  .div-page {
    margin-top: 20px;
    text-align: right;
  }
</style>
