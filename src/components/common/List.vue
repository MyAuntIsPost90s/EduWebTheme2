<template>
  <el-row>
    <el-row class="div-item" v-for="item in data" :key="item.id">
      <div @click="toLink(item)">
        <el-col class="title" :span="19">{{ item.title }}</el-col>
        <el-col class="date" :span="5" style="text-align: right">{{ item.publishTime.split(' ')[0] }}</el-col>
      </div>
    </el-row>
  </el-row>
</template>

<script>
  export default {
    name: "List",
    props: ['data', 'linkType'],
    methods: {
      toLink(item) {
        if (this.linkType == 'recruitment') {
          this.$router.push({name: 'Recruitment', query: {id: item.id}})
        } else {
          if (item.type == 1) {
            if (item.link == null) {
              this.$message.error("该文章未设置链接，无法跳转");
              return;
            }
            window.open(item.link);
          } else {
            this.$router.push({name: 'News', query: {id: item.id}});
          }
        }
      }
    }
  }
</script>

<style scoped>
  .div-item {
    padding: 10px 0;
    border-bottom: 1px solid #E4E7ED;
    color: #606266;
    margin: 0;
    cursor: default;
  }

  .div-item .date {
    color: #909399;
  }
</style>
