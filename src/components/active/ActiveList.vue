<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">活动管理</div>
    </el-row>
    <div class="content">
      <el-row>
        <el-row class="table-toolbar">
          <el-col :span="5">
            <el-input v-model="queryTitle">
              <el-button slot="append" icon="el-icon-search" @click="loadActives()">查询</el-button>
            </el-input>
          </el-col>
          <el-col :span="5" :offset="14" class="table-tool">
            <el-button title="添加" size="small" type="primary" icon="el-icon-circle-plus" @click="add()">添加</el-button>
          </el-col>
        </el-row>
        <el-table
          :data="actives"
          stripe
          style="width: 100%">
          <el-table-column
            prop="title"
            width="200"
            label="标题">
          </el-table-column>
          <el-table-column
            label="封面"
            width="200">
            <template slot-scope="scope">
              <img :src="scope.row.picUrl?scope.row.picUrl:'../../../static/common/images/dftimg.png'" width="100px"/>
            </template>
          </el-table-column>
          <el-table-column
            prop="createTime"
            width="200"
            label="发布时间">
          </el-table-column>
          <el-table-column
            prop="joinCount"
            width="150"
            label="报名人数">
          </el-table-column>
          <el-table-column
            label="活动状态">
            <template slot-scope="scope">
              <span v-if="scope.row.status==1">进行中</span>
              <span v-else>已结束</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button size="small" icon="el-icon-edit-outline" type="primary" @click="update(scope.row)">编辑
              </el-button>
              <el-button size="small" icon="el-icon-tickets" type="primary"
                         @click="toActiveUserList(scope.row.id)">报名情况
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
      </el-row>
    </div>

    <el-dialog title="活动" :visible.sync="dialogVisible">
      <el-form :model="active">
        <el-form-item label="预览图" :label-width="formLabelWidth">
          <img :src="active.picUrlForShow?active.picUrlForShow:'../../../static/common/images/dftimg.png'"
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
          <el-input v-model="active.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="活动状态" :label-width="formLabelWidth">
          <el-radio-group v-model="active.status">
            <el-radio :label="1">进行中</el-radio>
            <el-radio :label="2">关闭</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="活动内容" :label-width="formLabelWidth">
          <editor v-model="active.content" style="height: 500px"></editor>
        </el-form-item>
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

  const detailUrl = '/sys/active/active/detail';
  const listUrl = '/sys/active/active/list';
  const addUrl = '/sys/active/active/add';
  const updateUrl = '/sys/active/active/update';
  const deleteUrl = '/sys/active/active/delete';

  export default {
    components: {
      Editor,
      ElRow
    },
    name: "ActiveList",
    data() {
      return {
        rows: 10,
        page: 1,
        total: 0,
        level: 1,
        queryTitle: null,
        actives: [],
        active: {
          id: null,
          title: null,
          content: null,
          picUrl: null,
          picUrlForShow: null,
          createTime: null,
          status: null,
          opt: 'add',
          valid: {
            title: 'name:"标题",notEmpty:true,length:[0,50]',
            picUrl: 'name:"封面图片",notEmpty:true',
            content: 'name:"内容",notEmpty:true',
          }
        },
        formLabelWidth: '80px',
        dialogVisible: false,
        uploadUrl: Config.URL_HEAD + '/base/file/uploader/uploadForMinFile',
        uploadData: {appKey: Config.APP_KEY, basePath: '/banner'},
      }
    },
    mounted() {
      this.loadActives();
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
        this.active = {...this.active};
        this.active.picUrl = resp.data[0].path;
        this.active.picUrlForShow = resp.data[0].url;
      },
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadActives();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadActives();
      },
      toActiveUserList(activeId) {
        this.$router.push({name: 'ActiveUserList', params: {activeId: activeId}});
      },
      loadActives() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows, title: this.queryTitle}
        }).then(resp => {
          this.total = resp.data.total;
          this.actives = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      save() {
        if (!this.formValid.check(this.active)) {
          this.$message.error(this.active.e);
          return;
        }
        let url = addUrl;
        if (this.active.opt == 'edit') {
          url = updateUrl;
        }
        this.gateway.post({
          url: url,
          data: this.active
        }).then(resp => {
          this.dialogVisible = false;
          this.$message.success(resp.msg);
          this.loadActives();
        }).catch(err => {
          console.log(err);
        });
      },
      add() {
        this.active = {valid: this.active.valid, newsColumnId: this.newsColumnId};
        this.active.opt = 'add';
        this.dialogVisible = true;
      },
      update(obj) {
        this.active.opt = 'edit';
        this.dialogVisible = true;
        this.gateway.get({
          url: detailUrl,
          data: {id: obj.id}
        }).then(resp => {
          this.active = {...this.active, ...resp.data};
          this.active.picUrl = resp.data.picUrlNoPrefix;
          this.active.picUrlForShow = resp.data.picUrl;
          this.active.createTime = null;
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
            this.loadActives();
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
