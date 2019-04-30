import Vue from 'vue'
import Router from 'vue-router'
import Authority from '../../static/common/js/authority'

import Page from '../Page'
import Error from '../components/common/Error'

import Index from '../components/index/Index'

import News from '../components/news/News'
import NewsList from '../components/news/NewsList'

import ActiveList from '../components/active/ActiveList'
import Active from '../components/active/Active'

import Recruitment from '../components/recruitment/Recruitment'
import RecruitmentList from '../components/recruitment/RecruitmentList'

import NewsColumn from '../components/newsColumn/NewsColumn';

Vue.use(Router)

//路由
const routers = [
  {
    path: '/',
    name: 'Page',
    redirect: '/Index',
    component: Page,
    children: [
      {
        path: '/Index',
        name: 'Index',
        component: Index,
      },
      {
        path: '/News',
        name: 'News',
        component: News,
      },
      {
        path: '/NewsList',
        name: 'NewsList',
        component: NewsList,
      },
      {
        path: '/ActiveList',
        name: 'ActiveList',
        component: ActiveList,
      },
      {
        path: '/Active',
        name: 'Active',
        component: Active,
      },
      {
        path: '/Recruitment',
        name: 'Recruitment',
        component: Recruitment,
      },
      {
        path: '/RecruitmentList',
        name: 'RecruitmentList',
        component: RecruitmentList,
      },
      {
        path: '/NewsColumn',
        name: 'NewsColumn',
        component: NewsColumn
      },
      {
        path: '/Error',
        name: 'Error',
        component: Error,
      }
    ]
  }
]

const router = new Router({
  routes: routers
})

router.beforeEach((to, from, next) => {
  let currAuthority = Authority.getAuthority();
  let routerItem = getRouter(routers, to);
  if (routerItem) {
    if (!checkAuthority(routerItem, currAuthority)) {
      next({name: 'Error'});
    } else {
      next();
    }
    return;
  }
  next({name: 'Error'});
})

//查询路由
function getRouter(routers, to) {
  if (routers.length > 0) {
    for (let item of routers) {
      //广度优先便利
      let queue = [];
      queue.push(item);
      while (queue.length > 0) {
        let routerItem = queue[0];
        if (routerItem.name == to.name) {
          return routerItem;
        }
        if (routerItem.children) {
          for (let i = 0; i < routerItem.children.length; i++) {
            queue.push(routerItem.children[i]);
          }
        }
        queue.splice(0, 1);
      }
    }
  }
  return null;
}

//判断权限
function checkAuthority(router, currAuthority) {
  if (router.authority) {
    if (typeof  router.authority == 'string') {
      return currAuthority == router.authority;
    }
    if (router.authority instanceof Array) {
      for (let i = 0; i < router.authority.length; i++) {
        if (currAuthority == router.authority[i]) {
          return true;
        }
      }
      return false;
    }
  }
  return true;
}

export default router;
