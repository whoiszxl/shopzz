<template>
  <div class="layout">
    <el-container v-if="showMenu" class="container">
      <el-aside class="aside">
        <div class="head">
          <div>
            <img src="//user-images.githubusercontent.com/5679180/79618120-0daffb80-80be-11ea-819e-d2b0fa904d07.gif" alt="logo">
            <span>shopzz admin</span>
          </div>
        </div>
        <div class="line" />
        <el-menu
          background-color="#222832"
          text-color="#fff"
          :router="true"
        >
          <el-submenu index="1">
            <template #title>
              <span>仪表盘</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/"><i class="el-icon-odometer" />首页</el-menu-item>
            </el-menu-item-group>
          </el-submenu>


          <el-submenu index="2">
            <template #title>
              <span>管理员管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/sysUser"><i class="el-icon-star-on" />管理员列表</el-menu-item>
            </el-menu-item-group>
          </el-submenu>


          <el-submenu index="3">
            <template #title>
              <span>商品管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/attributeList"><i class="el-icon-star-on" />属性管理</el-menu-item>
              <el-menu-item index="/categoryList"><i class="el-icon-star-on" />分类管理</el-menu-item>
              <el-menu-item index="/spuList"><i class="el-icon-star-on" />SPU管理</el-menu-item>
            </el-menu-item-group>
            
          </el-submenu>

          <el-submenu index="4">
            <template #title>
              <span>营销管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/seckillList"><i class="el-icon-star-on" />秒杀管理</el-menu-item>
              <el-menu-item index="/bannerList"><i class="el-icon-star-on" />轮播图管理</el-menu-item>
              <el-menu-item index="/columnList"><i class="el-icon-star-on" />专栏管理</el-menu-item>
              <el-menu-item index="/activityList"><i class="el-icon-star-on" />活动管理</el-menu-item>
              <el-menu-item index="/couponList"><i class="el-icon-star-on" />优惠券管理</el-menu-item>
            </el-menu-item-group>
            
          </el-submenu>

          <el-submenu index="5">
            <template #title>
              <span>订单管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/bannerList"><i class="el-icon-star-on" />订单管理</el-menu-item>
            </el-menu-item-group>
            
          </el-submenu>


          <el-submenu index="6">
            <template #title>
              <span>WMS管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/warehouse"><i class="el-icon-star-on" />仓储管理</el-menu-item>
              <el-menu-item index="/supplier"><i class="el-icon-star-on" />供应商管理</el-menu-item>
              <el-menu-item index="/purchaseOrder"><i class="el-icon-star-on" />采购单管理</el-menu-item>
              <el-menu-item index="/outboundSellOrder"><i class="el-icon-star-on" />销售出库管理</el-menu-item>
              <el-menu-item index="/inboundReturnOrder"><i class="el-icon-star-on" />退货入库管理</el-menu-item>
            </el-menu-item-group>
            
          </el-submenu>

          <el-submenu index="7">
            <template #title>
              <span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/fileList"><i class="el-icon-star-on" />文件管理</el-menu-item>
            </el-menu-item-group>
            
          </el-submenu>


          <el-submenu index="8">
            <template #title>
              <span>服务器管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/init"><i class="el-icon-star-off" />初始化</el-menu-item>
              <el-menu-item index="/software/install"><i class="el-icon-star-off" />组件安装</el-menu-item>
              <el-menu-item index="/server/list"><i class="el-icon-star-off" />服务器管理</el-menu-item>
              <el-menu-item index="/software/list"><i class="el-icon-star-off" />组件管理</el-menu-item>
              <el-menu-item index="/server/script"><i class="el-icon-star-off" />脚本管理</el-menu-item>
            </el-menu-item-group>
          </el-submenu>


        </el-menu>
      </el-aside>
      <el-container class="content">
        <Header />
        <div class="main">
          <router-view />
        </div>
        <Footer />
      </el-container>
    </el-container>
    <el-container v-else class="container">
      <router-view />
    </el-container>
  </div>
</template>

<script>
import { reactive, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'
import { localGet, pathMap } from '@/utils'
export default {
  name: 'App',
  components: {
    Header,
    Footer
  },
  setup() {
    const noMenu = ['/login']
    const router = useRouter()
    const state = reactive({
      showMenu: false,
      defaultOpen: ['1', '2', '3'],
      currentPath: '/#/',
    })

    router.beforeEach((to, from, next) => {
      if (to.path == '/login') {
        // 如果路径是 /login 则正常执行
        next()
      } else {
        // 如果不是 /login，判断是否有 token
        if (!localGet('token')) {
          // 如果没有，则跳至登录页面
          next({ path: '/login' })
        } else {
          // 否则继续执行
          next()
        }
      }
      state.showMenu = !noMenu.includes(to.path)
      state.currentPath = to.path
      document.title = pathMap[to.name]
    })

    return {
      ...toRefs(state)
    }
  }
}
</script>

<style scoped>

.el-menu-item.is-active {
  color: orange;
}

.layout {
  min-height: 100vh;
  background-color: #ffffff;
}
.container {
  height: 100vh;
}
.aside {
  width: 200px!important;
  background-color: #222832;
}
.head {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
}
.head > div {
  display: flex;
  align-items: center;
}

.head img {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}
.head span {
  font-size: 20px;
  color: #ffffff;
}
.line {
  border-top: 1px solid hsla(0,0%,100%,.05);
  border-bottom: 1px solid rgba(0,0,0,.2);
}
.content {
  display: flex;
  flex-direction: column;
  max-height: 100vh;
  overflow: hidden;
}
.main {
  height: calc(100vh - 100px);
  overflow: auto;
  padding: 10px;
}
</style>

<style>
  body {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
  }
  .el-menu {
    border-right: none!important;
  }
  .el-submenu {
    border-top: 1px solid hsla(0, 0%, 100%, .05);
    border-bottom: 1px solid rgba(0, 0, 0, .2);
  }
  .el-submenu:first-child {
    border-top: none;
  }
  .el-submenu [class^="el-icon-"] {
    vertical-align: -1px!important;
  }
  a {
    color: #409eff;
    text-decoration: none;
  }
  .el-pagination {
    text-align: center;
    margin-top: 20px;
  }
  .el-popper__arrow {
    display: none;
  }
</style>