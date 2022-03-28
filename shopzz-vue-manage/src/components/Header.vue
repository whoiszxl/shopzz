<template>
  <div class="header">
    <div class="left">
      <i class="el-icon-back" @click="back"></i>
      <span style="font-size: 18px; padding-left:20px;">{{ name }}</span>
    </div>
    <div class="right">
      
        

      <i class="icon el-icon-s-custom" />
      {{ userInfo && userInfo.username || '' }}
      <i class="el-icon-caret-bottom" />
       
      <el-tag size="mini" effect="dark" class="logout" @click="logout">退出</el-tag>

    </div>
  </div>
</template>

<script>
import { onMounted, reactive, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import axios from '@/utils/axios'
import { localRemove, pathMap } from '@/utils'
export default {
  name: 'Header',
  setup() {
    const router = useRouter()
    const state = reactive({
      name: 'shopzz',
      userInfo: null, // 用户信息变量
    })
    // 初始化执行方法
     onMounted(() => {
      const pathname = window.location.hash.split('/')[1] || ''
      if (!['login'].includes(pathname)) {
        getUserInfo()
      }
    })
    // 获取用户信息
    const getUserInfo = async () => {
      const userInfo = await axios.post('/admin/admin/info')
      state.userInfo = userInfo.data
    }
    // 退出登录
    const logout = () => {

      axios.delete('/admin/admin/logout').then(() => {
        // 退出之后，将本地保存的 token  清理掉
        localRemove('token')
        // 回到登录页
        router.push({ path: '/login' })
      })
    }

    router.afterEach((to) => {
      const { id } = to.query
      state.name = pathMap[to.name]
    })

    const back = () => {
      router.back()
    }

    return {
      ...toRefs(state),
      logout,
      back
    }
  }
}
</script>

<style scoped>
  .header {
    height: 50px;
    border-bottom: 1px solid #e9e9e9;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
  }
  .el-icon-back {
    border: 1px solid #e9e9e9;
    padding: 4px;
    border-radius: 50px;
    margin-right: 10px;
  }
  .right > div > .icon{
    font-size: 18px;
    margin-right: 6px;
  }
  .author {
    margin-left: 10px;
    cursor: pointer;
  }
</style>

<style>
  .popper-user-box {
    background: url('https://s.yezgea02.com/lingling-h5/static/account-banner-bg.png') 50% 50% no-repeat!important;
    background-size: cover!important;
    border-radius: 0!important;
  }
   .popper-user-box .nickname {
    position: relative;
    color: #ffffff;
  }
  .popper-user-box .nickname .logout {
    position: absolute;
    right: 2px;
    top: 0;
    cursor: pointer;
  }
</style>