<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>新闻中心</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">新闻</div>
    </el-row>
    <div class="content">
      <el-row>
        <el-col :span="6" style="border-right: 1px solid #e8e8e8;min-height: 200px">
          <el-tree :data="newsColumns" :props="treeProps" node-key="id"
                   @node-click="nodeClick"
                   default-expand-all></el-tree>
        </el-col>
        <el-col :span="18">
          <el-row class="table-toolbar">
            <el-col :span="3" class="table-tool" style="text-align: left;padding: 10px">
              分类:{{ newsColumnText == null?'全部':newsColumnText }}
            </el-col>
            <el-col :span="5" :offset="16" class="table-tool">
              <el-button title="添加" size="small" type="primary" icon="el-icon-circle-plus" @click="add()">添加</el-button>
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
                {{ formatType(scope.row.type) }}
              </template>
            </el-table-column>
            <el-table-column
              prop="publishTime"
              label="发布时间">
            </el-table-column>
            <el-table-column
              label="操作">
              <template slot-scope="scope">
                <el-button size="small" icon="el-icon-edit-outline" type="primary" @click="update(scope.row)">编辑
                </el-button>
                <el-button size="small" icon="el-icon-delete" type="danger" @click="del(scope.row.id)">删除</el-button>
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
        </el-col>
      </el-row>
    </div>

    <el-dialog title="新闻" :visible.sync="dialogVisible">
      <el-form :model="news">
        <el-form-item label="分类" :label-width="formLabelWidth">
          {{ newsColumnText == null?'全部':newsColumnText }}
        </el-form-item>
        <el-form-item label="预览图" :label-width="formLabelWidth">
          <img :src="news.previewPicUrlForShow?news.previewPicUrlForShow:'../../../static/common/images/dftimg.png'"
               width="120px"/>
          <el-row>
            <el-upload
              :action="uploadUrl"
              :data="uploadData"
              :show-file-list="false"
              :on-success="uploadSuccess"
              :before-upload="beforeUpload">
              <el-button type="primary">上传</el-button>
            </el-upload>
          </el-row>
        </el-form-item>
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="news.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="作者" :label-width="formLabelWidth">
          <el-input v-model="news.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="发布时间" :label-width="formLabelWidth">
          <el-date-picker
            v-model="news.publishTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="点击事件" :label-width="formLabelWidth">
          <el-radio-group v-model="news.type">
            <el-radio :label="1">跳转链接</el-radio>
            <el-radio :label="2">查看文章</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="链接" :label-width="formLabelWidth">
          <el-input v-model="news.link" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="摘要" :label-width="formLabelWidth">
          <el-input v-model="news.shortContent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form :model="news" style="height: 500px;padding-bottom: 10px">
          <editor v-model="news.content"></editor>
        </el-form>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import ElRow from "element-ui/packages/row/src/row";
  import Editor from '../common/Editor';
  import Config from '../../../static/common/js/config';

  const treeUrl = '/sys/news/newsColumn/tree';
  const detailUrl = '/sys/news/news/detail';
  const listUrl = '/sys/news/news/list';
  const addUrl = '/sys/news/news/add';
  const updateUrl = '/sys/news/news/update';
  const deleteUrl = '/sys/news/news/batchDelete';

  export default {
    components: {
      Editor,
      ElRow
    },
    name: "News",
    data() {
      return {
        rows: 10,
        page: 1,
        total: 0,
        level: 1,
        newsColumnText: null,
        newsColumnId: null,
        newsColumns: [],
        newsList: [],
        news: {
          id: null,
          title: null,
          author: null,
          content: null,
          shortContent: null,
          publishTime: null,
          type: null,
          link: null,
          previewPicUrl: null,
          previewPicUrlForShow: null,
          opt: 'add',
          valid: {
            title: 'name:"标题",notEmpty:true,length:[0,50]',
            type: 'name:"点击事件",notEmpty:true',
            publishTime: 'name:"发布时间",notEmpty:true',
            shortContent: 'name:"摘要",notEmpty:true,length:[0,150]',
          }
        },
        formLabelWidth: '80px',
        dialogVisible: false,
        uploadUrl: Config.URL_HEAD + '/base/file/uploader/uploadForMinFile',
        uploadData: {appKey: Config.APP_KEY, basePath: '/banner'},
        treeProps: {
          label: 'text'
        }
      }
    },
    mounted() {
      this.loadTree();
      this.loadNews();
    },
    methods: {
      beforeUpload(file) {
        const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG或PNG 格式!');
          return false;
        }
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
          return false;
        }
        return true;
      },
      uploadSuccess(resp) {
        if (resp.code !== 1) {
          this.$message.error(resp.msg);
          return;
        }
        this.news = {...this.news};
        this.news.previewPicUrl = resp.data[0].path;
        this.news.previewPicUrlForShow = resp.data[0].url;
      },
      formatType(type) {
        if (type == 1) {
          return '链接';
        }
        if (type == 2) {
          return '文章';
        }
        return '未知类别';
      },
      nodeClick(data) {
        this.newsColumnText = data.text;
        this.newsColumnId = data.id;
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
      loadTree() {
        this.gateway.get({
          url: treeUrl,
        }).then(resp => {
          this.newsColumns = resp.data;
        }).catch(err => {
          console.log(err);
        });
      },
      loadNews() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows, newsColumnId: this.newsColumnId}
        }).then(resp => {
          this.total = resp.data.total;
          this.newsList = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      save() {
        if (!this.formValid.check(this.news)) {
          this.$message.error(this.news.e);
          return;
        }
        let url = addUrl;
        if (this.news.opt == 'edit') {
          url = updateUrl;
        }
        this.gateway.post({
          url: url,
          data: this.news
        }).then(resp => {
          this.dialogVisible = false;
          this.$message.success(resp.msg);
          this.loadNews();
        }).catch(err => {
          console.log(err);
        });
      },
      add() {
        this.news = {valid: this.news.valid, newsColumnId: this.newsColumnId};
        this.news.opt = 'add';
        this.dialogVisible = true;
      },
      update(obj) {
        this.dialogVisible = true;
        this.gateway.get({
          url: detailUrl,
          data: {id: obj.id}
        }).then(resp => {
          this.news = {...resp.data, valid: this.news.valid, opt: 'edit'};
          this.news.previewPicUrl = resp.data.previewPicUrlWithOutPrefix;
          this.news.previewPicUrlForShow = resp.data.previewPicUrl;
        }).catch(err => {
          console.log(err);
        });
      },
      del(id) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.gateway.get({
            url: deleteUrl,
            data: {ids: id}
          }).then(resp => {
            this.loadNews();
            this.$message.success(resp.message);
          }).catch(e => {
            console.log(e);
          })
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
