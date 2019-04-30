<template>
  <div style="height: 100%">
    <el-upload
      style="display: none"
      :action="uploadUrl"
      :data="uploadData"
      :show-file-list="false"
      :on-success="uploadSuccess"
      :before-upload="beforeUpload">
      <el-button id="btn-upload" type="primary">上传</el-button>
    </el-upload>
    <quill-editor ref="Editor"
                  v-model="content"
                  :options="editorOption"
                  style="height: 100%"></quill-editor>
  </div>
</template>

<script>
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';

  import Config from '../../../static/common/js/config';
  import {quillEditor} from "vue-quill-editor";

  export default {
    name: "Editor",
    props: {value: {type: String}},
    components: {
      quillEditor
    },
    data() {
      return {
        content: this.value,
        uploadUrl: Config.URL_HEAD + '/base/file/uploader/uploadForMinFile',
        uploadData: {appKey: Config.APP_KEY, basePath: '/banner'},
        editorOption: {
          placeholder: '请输入内容',
          modules: {
            toolbar: {
              container: [
                ['bold', 'italic', 'underline', 'strike', {'align': []}],
                ['blockquote', 'code-block'],
                [{'color': []}, {'size': []}, {'background': []}],
                ['link', 'image']
              ],
              handlers: {
                'image': function (value) {
                  if (value) {
                    document.querySelector('#btn-upload').click();
                  } else {
                    this.quill.format('image', false);
                  }
                }
              }
            }
          }
        }
      }
    },
    watch: {
      value(val) {
        this.content = val;
      },
      content(val) {
        this.$emit('input', val);
      }
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
        let quill = this.$refs.Editor.quill;
        // 获取光标所在位置
        let length = quill.getSelection().index;
        //插入图片
        quill.insertEmbed(length, 'image', resp.data[0].url);
        // 调整光标到最后
        quill.setSelection(length + 1);
      },
    }
  }
</script>

<style scoped>

</style>
