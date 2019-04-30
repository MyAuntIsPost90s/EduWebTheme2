<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">轮播图配置</div>
    </el-row>
    <div class="content">
      <el-row class="table-toolbar">
        <el-col :span="5" :offset="19" class="table-tool">
          <el-button title="添加" size="small" type="primary" icon="el-icon-circle-plus" @click="add()">添加</el-button>
        </el-col>
      </el-row>
      <el-table
        :data="banner"
        stripe
        style="width: 100%">
        <el-table-column
          prop="name"
          label="名称">
        </el-table-column>
        <el-table-column
          prop="picUrl"
          label="轮播图">
          <template slot-scope="scope">
            <img :src="scope.row.picUrl?scope.row.picUrl:'../../../static/common/images/dftimg.png'" width="100px"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="link"
          label="链接">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button size="small" icon="el-icon-edit-outline" type="primary" @click="update(scope.row)">编辑</el-button>
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
    </div>

    <el-dialog title="轮播图编辑" :visible.sync="dialogVisible">
      <el-form :model="bannerParam">
        <el-form-item label="轮播图" :label-width="formLabelWidth">
          <img :src="bannerParam.picUrlForShow?bannerParam.picUrlForShow:'../../../static/common/images/dftimg.png'"
               width="120px"/>
          <el-row>
            <el-upload
              class="avatar-uploader"
              :action="uploadUrl"
              :data="uploadData"
              :show-file-list="false"
              :on-success="uploadSuccess"
              :before-upload="beforeUpload">
              <el-button type="primary">上传</el-button>
            </el-upload>
          </el-row>
        </el-form-item>
        <el-form-item label="名称" :label-width="formLabelWidth">
          <el-input v-model="bannerParam.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="链接" :label-width="formLabelWidth">
          <el-input v-model="bannerParam.link" autocomplete="off"></el-input>
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
  import Config from '../../../static/common/js/config';

  const listUrl = '/sys/config/banner/list';
  const addUrl = '/sys/config/banner/add';
  const updateUrl = '/sys/config/banner/update';
  const deleteUrl = '/sys/config/banner/batchDelete';

  export default {
    components: {ElRow},
    name: "BannerList",
    data() {
      return {
        rows: 10,
        page: 1,
        total: 0,
        banner: [],
        bannerParam: {
          id: null,
          name: null,
          picUrl: null,
          picUrlForShow: null,
          link: null,
          opt: 'add',
          valid: {
            name: 'name:"名称",notEmpty:true',
            picUrl: 'name:"轮播图",notEmpty:true',
          }
        },
        formLabelWidth: '80px',
        dialogVisible: false,
        uploadUrl: Config.URL_HEAD + '/base/file/uploader/uploadForMinFile',
        uploadData: {appKey: Config.APP_KEY, basePath: '/banner'}
      }
    },
    mounted() {
      this.loadBanner()
    },
    methods: {
      beforeUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG 格式!');
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
        this.bannerParam = {...this.bannerParam};
        this.bannerParam.picUrl = resp.data[0].path;
        this.bannerParam.picUrlForShow = resp.data[0].url;
      },
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadBanner();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadBanner();
      },
      loadBanner() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows}
        }).then(resp => {
          this.total = resp.data.total;
          this.banner = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      save() {
        if (!this.formValid.check(this.bannerParam)) {
          this.$message.error(this.bannerParam.e);
          return;
        }
        let url = addUrl;
        if (this.bannerParam.opt == 'edit') {
          url = updateUrl;
        }
        this.gateway.post({
          url: url,
          data: this.bannerParam
        }).then(resp => {
          this.dialogVisible = false;
          this.$message.success(resp.msg);
          this.loadBanner();
        }).catch(err => {
          console.log(err);
        });
      },
      add() {
        this.bannerParam = {valid: this.bannerParam.valid};
        this.bannerParam.opt = 'add';
        this.dialogVisible = true;
      },
      update(obj) {
        this.bannerParam = {...obj, valid: this.bannerParam.valid};
        this.bannerParam.picUrlForShow = this.bannerParam.picUrl;
        this.bannerParam.picUrl = this.bannerParam.picUrlWithOutPrefix;
        this.bannerParam.opt = 'edit';
        this.dialogVisible = true;
      },
      del(id) {
        this.$confirm('此操作将永久删除, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.gateway.get({
            url:deleteUrl,
            data:{ids:id}
          }).then(resp=>{
            this.loadBanner();
            this.$message.success(resp.message);
          }).catch(e=>{
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
