<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">首页配置</div>
    </el-row>
    <div class="content">
      <el-form :model="config" label-width="80px" style="width: 300px">
        <el-form-item label="首页新闻">
          <el-input :value="config.firstNewsTitle" readonly placeholder="请选择首页新闻">
            <el-button slot="append" @click="save()">保存</el-button>
          </el-input>
        </el-form-item>
      </el-form>
      <el-row>
        <el-row class="table-toolbar">
          <el-col :span="5" class="table-tool" style="text-align: left;padding: 10px">
            <el-input v-model="queryTitle">
              <el-button slot="append" icon="el-icon-search" @click="loadNews()">查询</el-button>
            </el-input>
          </el-col>
        </el-row>
        <el-table
          :data="newsList"
          stripe
          style="width: 100%">
          <el-table-column
            prop="title"
            label="标题">
          </el-table-column>
          <el-table-column
            prop="author"
            label="作者">
          </el-table-column>
          <el-table-column
            label="类型">
            <template slot-scope="scope">
              <span v-if="scope.row.type === 1">链接</span>
              <span v-else-if="scope.row.type === 2">文章</span>
              <span v-else>未知类别</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="publishTime"
            label="发布时间">
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button v-if="scope.row.previewPicUrl!=null" size="small" type="primary" @click="select(scope.row)">
                选择
              </el-button>
              <el-button v-else size="small" type="info" disable>需要设置预览图</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row class="table-foot">
          <el-col :span="24">
            <el-pagination
              class="table-page"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="page"
              :page-sizes="[10,50,100]"
              :page-size="rows"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </el-col>
        </el-row>
      </el-row>
    </div>
  </div>
</template>

<script>

  const newsUrl = '/sys/news/news/list';
  const detailUrl = '/sys/config/indexConfig/detail';
  const editUrl = '/sys/config/indexConfig/edit';

  export default {
    name: "IndexConfig",
    data() {
      return {
        page: 1,
        rows: 10,
        total: 0,
        queryTitle: null,
        newsList: [],
        config: {
          firstNewsId: null,
          firstNewsTitle: null,
          valid: {
            firstNewsId: 'name:"首页新闻",notEmpty:true',
          }
        }
      }
    },
    mounted() {
      this.loadConfig();
      this.loadNews();
    },
    methods: {
      select(news) {
        this.config.firstNewsId = news.id;
        this.config.firstNewsTitle = news.title;
      },
      save() {
        if (!this.formValid.check(this.config)) {
          this.$message.error(this.config.e);
          return;
        }
        this.gateway.post({
          url: editUrl
          , data: this.config
        }).then(resp => {
          this.$message.success(resp.msg);
        }).catch(e => {
          console.log(e);
        })
      },
      loadConfig() {
        this.gateway.get({
          url: detailUrl
        }).then(resp => {
          this.config = {...this.config.valid, ...resp.data};
        }).catch(e => {
          console.log(e)
        });
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
          url: newsUrl,
          data: {page: this.page, rows: this.rows, title: this.queryTitle}
        }).then(resp => {
          this.total = resp.data.total;
          this.newsList = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      }
    }
  }
</script>

<style scoped>
  .table-toolbar {
    margin-bottom: 20px;
  }

  .table-tool {
    text-align: right;
  }

  .table-page {
    float: right;
  }

  .table-foot {
    margin-top: 20px;
  }
</style>
