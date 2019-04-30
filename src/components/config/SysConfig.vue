<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">系统配置</div>
    </el-row>
    <div class="content">
      <el-form ref="form" :model="config" label-width="80px">
        <el-form-item label="logo">
          <img :src="config.logoPicUrlForShow?config.logoPicUrlForShow:'../../../static/common/images/dftimg.png'" width="50px"/>
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
        <el-form-item label="版权信息">
          <el-input v-model="config.copyright"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="config.address"></el-input>
        </el-form-item>
        <el-form-item label="邮编">
          <el-input v-model="config.zipCode"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save()">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import Config from '../../../static/common/js/config';

  const detailUrl = '/sys/config/sysConfig/detail';
  const editUrl = '/sys/config/sysConfig/edit';

  export default {
    name: "SysConfig",
    data() {
      return {
        config: {
          logoPicUrl: null,
          logoPicUrlForShow: null,
          copyright: null,
          address: null,
          zipCode: null,
          valid: {
            logoPicUrl: 'name:"logo",notEmpty:true',
            copyright: 'name:"版权信息",notEmpty:true,length:[1,20]',
            address: 'name:"地址",notEmpty:true,length:[1,20]',
            zipCode: 'name:"邮编",notEmpty:true,length:[1,20]'
          }
        },
        uploadUrl: Config.URL_HEAD + '/base/file/uploader/uploadForMinFile',
        uploadData: {appKey: Config.APP_KEY, basePath: '/banner'}
      }
    },
    mounted() {
      this.loadConfig();
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
        this.config.logoPicUrl = resp.data[0].path;
        this.config.logoPicUrlForShow = resp.data[0].url;
        this.config = {...this.config};
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
          this.config = {valid: this.config.valid, ...resp.data};
          this.config.logoPicUrlForShow = this.config.logoPicUrl;
          this.config.logoPicUrl = this.config.logoPicUrlWithOutPrefix;
        }).catch(e => {
          console.log(e)
        });
      }
    }
  }
</script>

<style scoped>

</style>
