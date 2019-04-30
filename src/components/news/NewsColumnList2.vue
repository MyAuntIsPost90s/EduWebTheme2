<template>
  <div>
    <el-row class="content-bar">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>新闻中心</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="content-title">二级栏目</div>
    </el-row>
    <div class="content">
      <el-row class="table-toolbar">
        <el-col :span="6">
          <el-select size="small" v-model="newsColumns1Id" placeholder="请选择">
            <el-option
              v-for="item in newsColumns1"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <el-button size="small" icon="el-icon-search" type="primary" @click="loadNewsColumn()">查询</el-button>
        </el-col>
        <el-col :span="5" :offset="13" class="table-tool">
          <el-button title="添加" size="small" type="primary" icon="el-icon-circle-plus" @click="add()">添加</el-button>
        </el-col>
      </el-row>
      <el-table
        :data="newsColumns"
        stripe
        style="width: 100%">
        <el-table-column
          prop="name"
          label="名称">
        </el-table-column>
        <el-table-column
          label="类型">
          <template slot-scope="scope">
            {{ formatType(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column
          prop="sortIndex"
          label="排序权重">
        </el-table-column>
        <el-table-column
          label="操作">
          <template slot-scope="scope">
            <el-button size="small" icon="el-icon-edit-outline" type="primary" @click="update(scope.row)">编辑</el-button>
            <el-button v-if="scope.row.type==2" size="small" icon="el-icon-edit-outline" type="primary"
                       @click="updateContent(scope.row)">编辑内容
            </el-button>
            <el-button v-if="scope.row.type==3" size="small" icon="el-icon-edit-outline" type="primary"
                       @click="updateLink(scope.row)">编辑链接
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
    </div>

    <el-dialog title="新闻栏目" :visible.sync="dialogVisible">
      <el-form :model="newsColumn">
        <el-form-item label="栏目名称" :label-width="formLabelWidth">
          <el-input v-model="newsColumn.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序权重" :label-width="formLabelWidth">
          <el-input type="number" v-model="newsColumn.sortIndex" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="父级栏目" :label-width="formLabelWidth">
          <el-select size="small" v-model="newsColumn.parentId" placeholder="请选择">
            <el-option
              v-for="item in newsColumns1"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="栏目类型" :label-width="formLabelWidth">
          <el-radio-group v-model="newsColumn.type">
            <el-radio :label="1">文章类别</el-radio>
            <el-radio :label="2">文章</el-radio>
            <el-radio :label="3">链接</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="链接" :visible.sync="dialogLinkVisible">
      <el-form :model="newsColumn">
        <el-form-item label="链接" :label-width="formLabelWidth">
          <el-input v-model="newsColumn.link" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogLinkVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="内容" :visible.sync="dialogContentVisible">
      <el-form :model="newsColumn" style="height: 500px;padding-bottom: 10px">
        <editor v-model="newsColumn.content"></editor>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogContentVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ElRow from "element-ui/packages/row/src/row";
  import Editor from '../common/Editor';

  const detailUrl = '/sys/news/newsColumn/detail';
  const listUrl = '/sys/news/newsColumn/list';
  const addUrl = '/sys/news/newsColumn/add';
  const updateUrl = '/sys/news/newsColumn/update';
  const deleteUrl = '/sys/news/newsColumn/batchDelete';

  export default {
    components: {
      Editor,
      ElRow
    },
    name: "NewsColumnList2",
    data() {
      return {
        rows: 10,
        page: 1,
        total: 0,
        level: 2,
        newsColumns1Id: null,
        newsColumns1: [],
        newsColumns: [],
        newsColumn: {
          id: null,
          name: null,
          type: null,
          content: null,
          sortIndex: 0,
          parentId: null,
          opt: 'add',
          valid: {
            name: 'name:"栏目名称",notEmpty:true',
            type: 'name:"栏目类型",notEmpty:true',
          }
        },
        formLabelWidth: '80px',
        dialogVisible: false,
        dialogLinkVisible: false,
        dialogContentVisible: false,
      }
    },
    mounted() {
      this.loadNewsColumn();
      this.loadNewsColumn1();
    },
    methods: {
      formatType(type) {
        if (type == 1) {
          return '文章类别';
        }
        if (type == 2) {
          return '文章';
        }
        if (type == 3) {
          return '链接';
        }
        return '未知类别';
      },
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadNewsColumn();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadNewsColumn();
      },
      loadNewsColumn() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows, level: this.level, parentId: this.newsColumns1Id}
        }).then(resp => {
          this.total = resp.data.total;
          this.newsColumns = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      loadNewsColumn1() {
        this.gateway.get({
          url: listUrl,
          data: {page: this.page, rows: this.rows, level: this.level - 1}
        }).then(resp => {
          this.newsColumns1 = resp.data.rows;
        }).catch(err => {
          console.log(err);
        });
      },
      save() {
        if (!this.formValid.check(this.newsColumn)) {
          this.$message.error(this.newsColumn.e);
          return;
        }
        let url = addUrl;
        if (this.newsColumn.opt == 'edit') {
          url = updateUrl;
        }
        this.gateway.post({
          url: url,
          data: this.newsColumn
        }).then(resp => {
          this.dialogVisible = false;
          this.dialogLinkVisible = false;
          this.dialogContentVisible = false;
          this.$message.success(resp.msg);
          this.loadNewsColumn();
        }).catch(err => {
          console.log(err);
        });
      },
      add() {
        this.newsColumn = {valid: this.newsColumn.valid, level: this.level};
        this.newsColumn.opt = 'add';
        this.dialogVisible = true;
      },
      update(obj) {
        this.newsColumn = {...obj, valid: this.newsColumn.valid, level: this.level};
        this.newsColumn.opt = 'edit';
        this.dialogVisible = true;
      },
      updateLink(obj) {
        this.newsColumn = {...obj, valid: this.newsColumn.valid, level: this.level};
        this.newsColumn.opt = 'edit';
        this.dialogLinkVisible = true;
      },
      updateContent(obj) {
        this.newsColumn = {...obj, valid: this.newsColumn.valid, level: this.level};
        this.newsColumn.opt = 'edit';
        this.dialogContentVisible = true;
        this.gateway.get({
          url: detailUrl,
          data: {id: obj.id}
        }).then(resp => {
          this.newsColumn.content = resp.data.content;
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
            this.loadNewsColumn();
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
