<template>
  <el-row :gutter="30" class="div-active">
    <el-col :span="6" v-for="item in data" :key="item.id">
      <router-link :to="{ name:'Active',query:{ id:item.id} }" tag="div">
        <div class="div-active-card">
          <div class="img"
               :style="{ height:imgHeight,backgroundImage: `url(${item.picUrl?item.picUrl:'../../../static/common/images/dftimg.png'})` }">
            <span v-if="item.status == 1" class="tag">报名中</span>
            <span v-else class="tag-end">已结束</span>
          </div>
          <div class="title">{{ item.title }}</div>
        </div>
      </router-link>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "ActiveCards",
    props: ['data'],
    data() {
      return {
        imgHeight: '200px'
      }
    },
    mounted() {
      this.imgHeight = document.getElementsByClassName('div-active')[0].offsetWidth * 0.12 + 'px';
    }
  }
</script>

<style scoped>

  .div-active-card {
    padding: 15px;
    background-color: #fff;
    border-radius: 3px;
    margin-bottom: 15px;
  }

  .div-active-card .img {
    width: 100%;
    height: 200px;
    background-size: 100%;
  }

  .div-active-card .tag {
    float: right;
    margin: 5px;
    background-color: #67C23A;
    color: #fff;
    font-size: 13px;
    padding: 2px 5px;
  }

  .div-active-card .tag-end {
    float: right;
    margin: 5px;
    background-color: #909399;
    color: #fff;
    font-size: 12px;
    padding: 2px 5px;
  }

  .div-active-card .title {
    overflow-x: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>
