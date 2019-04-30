<template>
  <div>
    <el-carousel v-if="banners!=null&&banners.length>0" indicator-position="outside">
      <el-carousel-item v-for="item in banners" :key="item.id" arrow="always">
        <div class="div-banner" :style="{backgroundImage:`url(${item.picUrl})` }" @click="bannerLink(item)"></div>
      </el-carousel-item>
    </el-carousel>
    <el-row>
      <el-row class="div-news-title">
        <el-col :span="21">新闻动态</el-col>
        <el-col class="more" :span="3">
          <router-link :to="{name:'NewsList',query:{ newsColumnId:'0X00000001',newsColumnName:'新闻动态' }}" tag="div">更多
          </router-link>
        </el-col>
      </el-row>
      <el-row :gutter="40">
        <el-col :span="6" v-if="firstNews">
          <img :src="firstNews.previewPicUrl?firstNews.previewPicUrl: '../../../static/common/images/dftimg.png'"
               width="100%"
               height="180px" @click="firstNewsLink()"/>
          <div class="div-content">
            {{ firstNews.shortContent }}
          </div>
        </el-col>
        <el-col :span="18">
          <List :data="news"/>
        </el-col>
      </el-row>

      <el-row style="margin-top: 38px">
        <el-row class="div-news-title">
          <el-col :span="21">部门活动</el-col>
          <el-col class="more" :span="3">
            <router-link :to="{name:'ActiveList'}" tag="div">更多</router-link>
          </el-col>
        </el-row>
        <el-row>
          <ActiveCards :data="actives"/>
        </el-row>
      </el-row>

      <el-row :gutter="40" style="margin-top: 38px">
        <el-col :span="12">
          <el-row>
            <el-row class="div-news-title">
              <el-col :span="21">通知公告</el-col>
              <el-col class="more" :span="3">
                <router-link :to="{name:'NewsList',query:{ newsColumnId:'0X00000000',newsColumnName:'通知公告' }}"
                             tag="div">更多
                </router-link>
              </el-col>
            </el-row>
            <el-row>
              <List :data="notices"/>
            </el-row>

          </el-row>
        </el-col>
        <el-col :span="12">
          <el-row>
            <el-row class="div-news-title">
              <el-col :span="21">部门招聘</el-col>
              <el-col class="more" :span="3">
                <router-link :to="{name:'RecruitmentList'}"
                             tag="div">
                  更多
                </router-link>
              </el-col>
            </el-row>
            <el-row>
              <List :data="recruitmentList" :linkType="'recruitment'"/>
            </el-row>

          </el-row>
        </el-col>
      </el-row>

    </el-row>
  </div>
</template>

<script>

  import List from '../common/List';
  import ActiveCards from '../active/ActiveCards';

  const indexUrl = '/app/config/indexConfig/detailForApp';

  export default {
    name: "Index",
    components: {List, ActiveCards},
    data() {
      return {
        banners: [],
        news: [],
        actives: [],
        notices: [],
        recruitmentList: [],
        firstNews: {
          picUrl: null,
          shortContent: null,
        },
      }
    },
    mounted() {
      this.loadIndex();
    },
    methods: {
      loadIndex() {
        this.gateway.get({url: indexUrl})
          .then(resp => {
            if (resp.data.firstNews != null) {
              this.firstNews = resp.data.firstNews;
              if (this.firstNews.shortContent.length > 50) {
                this.firstNews.shortContent = this.firstNews.shortContent.substring(0, 50) + '....';
              }
            }
            if (resp.data.banners != null) {
              this.banners = resp.data.banners;
            }
            if (resp.data.news != null) {
              let news = [];
              for (let i = 0; i < resp.data.news.length; i++) {
                news.push({
                  title: resp.data.news[i].title,
                  publishTime: resp.data.news[i].publishTime,
                  id: resp.data.news[i].id,
                  type: resp.data.news[i].type,
                  link: resp.data.news[i].link
                });
              }
              this.news = news;
            }
            if (resp.data.actives != null) {
              this.actives = resp.data.actives;
            }
            if (resp.data.notices != null) {
              let notices = [];
              for (let i = 0; i < resp.data.notices.length; i++) {
                notices.push({
                  title: resp.data.notices[i].title,
                  publishTime: resp.data.notices[i].publishTime,
                  id: resp.data.notices[i].id,
                  type: resp.data.news[i].type,
                  link: resp.data.news[i].link
                });
              }
              this.notices = notices;
            }
            if (resp.data.recruitments != null) {
              let recruitmentList = [];
              for (let i = 0; i < resp.data.recruitments.length; i++) {
                recruitmentList.push({
                  title: resp.data.recruitments[i].title,
                  publishTime: resp.data.recruitments[i].createTime,
                  id: resp.data.recruitments[i].id
                });
              }
              this.recruitmentList = recruitmentList;
            }
          })
          .catch(e => {
            console.log(e);
          });
      },
      bannerLink(banner) {
        if (banner.link == null) {
          return;
        }
        window.open(banner.link);
      },
      firstNewsLink() {
        if (this.firstNews.type == 1) {
          window.open(this.firstNews.link);
        } else {
          this.$router.push({name: 'News', query: {id: this.firstNews.id}});
        }
      }
    }
  }
</script>

<style>
  .div-banner {
    height: 100%;
    background-size: 100%;
  }

  .div-news-title {
    border-bottom: 1px solid #DCDFE6;
    margin-bottom: 10px;
    font-size: 16px;
    font-weight: 600;
  }

  .div-news-title .more {
    font-size: 15px;
    color: #606266;
    font-weight: 500;
    text-align: right;
    cursor: default;
  }

  .div-content {
    word-break: break-all;
    line-height: 24px;
    color: #606266;
  }
</style>
