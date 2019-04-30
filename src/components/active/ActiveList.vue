<template>
  <el-row :gutter="30">
    <el-col :span="6">
      <Menu :newsColumnId="'-1'"/>
    </el-col>
    <el-col :span="18" class="div-list">
      <el-row class="div-title">部门活动</el-row>
      <ActiveCards :data="actives"/>
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
  import ActiveCards from './ActiveCards';

  const listUrl = '/app/active/active/list'

  export default {
    name: "ActiveList",
    components: {Menu, ActiveCards},
    data() {
      return {
        page: 1,
        rows: 16,
        total: 0,
        actives: []
      }
    },
    mounted() {
      this.loadActives();
    },
    methods: {
      handleSizeChange(rows) {
        this.rows = rows;
        this.loadActives();
      },
      handleCurrentChange(page) {
        this.page = page;
        this.loadActives();
      },
      loadActives() {
        this.gateway.get({
          url: listUrl
          , data: {
            page: this.page,
            rows: this.rows
          }
        }).then(resp => {
          this.actives = resp.data.rows;
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
