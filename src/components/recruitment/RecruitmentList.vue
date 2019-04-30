<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">部门招聘</div>
    </el-row>
    <div class="content">
      <el-row>
        <el-row class="table-toolbar">
          <el-col :span="5">
            <el-input v-model="queryTitle">
              <el-button slot="append" icon="el-icon-search" @click="loadRecruitmentList()">查询</el-button>
            </el-input>
          </el-col>
          <el-col :span="5" :offset="14" class="table-tool">
            <el-button title="添加" size="small" type="primary" icon="el-icon-circle-plus" @click="add()">添加</el-button>
          </el-col>
        </el-row>
        <el-table
          :data="recruitmentList"
          stripe
          style="width: 100%">
          <el-table-column
            prop="title"
            label="标题">
          </el-table-column>
          <el-table-column
            prop="createTime"
            label="发布时间">
          </el-table-column>
          <el-table-column
            label="招聘状态">
            <template slot-scope="scope">
              <span v-if="scope.row.status==1">进行中</span>
              <span v-else>已下架</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button size="small" icon="el-icon-edit-outline" type="primary" @click="update(scope.row)">编辑
              </el-button>
              <el-button size="small" icon="el-icon-tickets" type="primary" @click="toResume(scope.row.id)">投递情况
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

    <el-dialog title="招聘" :visible.sync="dialogVisible">
      <el-form :model="recruitment">
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="recruitment.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-radio-group v-model="recruitment.status">
            <el-radio :label="1">进行中</el-radio>
            <el-radio :label="2">下架</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="招聘内容" :label-width="formLabelWidth">
          <editor v-model="recruitment.content" style="height: 500px"></editor>
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

  const detailUrl = '/sys/recruitment/recruitment/detail';
  const listUrl = '/sys/recruitment/recruitment/list';
  const addUrl = '/sys/recruitment/recruitment/add';
  const updateUrl = '/sys/recruitment/recruitment/update';
  const deleteUrl = '/sys/recruitment/recruitment/delete';

  export default {
    components: {
      Editor,
      ElRow
    },
    name: "RecruitmentList",
    data() {
      return {
        rows: 10,
        page: 1,
        total: 0,
        level: 1,
        queryTitle: null,
        recruitmentList: [],
        recruitment: {
          id: null,
          title: null,
          content: null,
          createTime: null,
          status: null,
          opt: 'add',
          valid: {
            title: 'name:"标题",notEmpty:true,length:[0,50]',
            status: 'name:"招聘状态",notEmpty:true',
            content: 'name:"内容",notEmpty:true',
          }
        },
        formLabelWidth: '80px',
        dialogVisible: false,
      }
    },
    mounted() {
      this.loadRecruitmentList();
    },
    methods: {
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadRecruitmentList();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadRecruitmentList();
      },
      toResume(recruitmentId) {
        this.$router.push({name: 'ResumeList', params: {recruitmentId: recruitmentId}});
      },
      loadRecruitmentList() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows, title: this.queryTitle}
        }).then(resp => {
          this.total = resp.data.total;
          this.recruitmentList = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      save() {
        if (!this.formValid.check(this.recruitment)) {
          this.$message.error(this.recruitment.e);
          return;
        }
        let url = addUrl;
        if (this.recruitment.opt == 'edit') {
          url = updateUrl;
        }
        this.gateway.post({
          url: url,
          data: this.recruitment
        }).then(resp => {
          this.dialogVisible = false;
          this.$message.success(resp.msg);
          this.loadRecruitmentList();
        }).catch(err => {
          console.log(err);
        });
      },
      add() {
        this.recruitment = {valid: this.recruitment.valid, newsColumnId: this.newsColumnId};
        this.recruitment.opt = 'add';
        this.dialogVisible = true;
      },
      update(obj) {
        this.recruitment.opt = 'edit';
        this.dialogVisible = true;
        this.gateway.get({
          url: detailUrl,
          data: {id: obj.id}
        }).then(resp => {
          this.recruitment = {...this.recruitment, ...resp.data};
          this.recruitment.picUrl = resp.data.picUrlNoPrefix;
          this.recruitment.picUrlForShow = resp.data.picUrl;
          this.recruitment.createTime = null;
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
            this.loadRecruitmentList();
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
