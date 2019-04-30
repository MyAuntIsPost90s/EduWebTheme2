<template>
  <el-row :gutter="30">
    <el-col :span="6">
      <Menu :newsColumnId="'-1'"/>
    </el-col>
    <el-col :span="18" class="div-list">
      <el-row class="div-title">招聘信息</el-row>
      <List :data="recruitmentList" :linkType="'recruitment'"/>
      <el-row class="div-page">
        <el-pagination
          :current-page="page"
          :page-sizes="[rows, rows+20, rows+40, rows+60]"
          :page-size="rows"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :total="total">
        </el-pagination>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
  import Menu from '../common/Menu';
  import List from '../common/List';

  let listUrl = '/app/recruitment/recruitment/list';

  export default {
    name: "RecruitmentList",
    components: {Menu, List},
    data() {
      return {
        page: 1,
        rows: 15,
        total: 0,
        recruitmentList: []
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
      loadRecruitmentList() {
        this.gateway.get({
          url: listUrl
          , data: {
            page: this.page,
            rows: this.rows,
            status: 1,
          }
        }).then(resp => {
          let recruitmentList = [];
          for (let i = 0; i < resp.data.rows.length; i++) {
            recruitmentList.push({
              title: resp.data.rows[i].title,
              publishTime: resp.data.rows[i].createTime,
              id: resp.data.rows[i].id
            });
          }
          this.recruitmentList = recruitmentList;
          this.total = resp.data.total;
        }).catch(e => {
          console.log(e);
        });
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
