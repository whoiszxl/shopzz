<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="adminForm" :rules="rules" ref="adminRef" label-width="100px" class="adminForm">
        <el-form-item label="账号" prop="username">
          <el-input style="width: 300px" v-model="adminForm.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input style="width: 300px" v-model="adminForm.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="fullname">
          <el-input style="width: 300px" type="textarea" v-model="adminForm.fullname" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input type="number" min="0" style="width: 300px" v-model="adminForm.mobile" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input type="textarea" min="0" style="width: 300px" v-model="adminForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="谷歌验证码" prop="googleCode">
          <el-input type="textarea" min="0" style="width: 300px" v-model="adminForm.googleCode" placeholder="请输入谷歌验证码"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="adminForm.status">
            <el-radio label="0">无效</el-radio>
            <el-radio label="1">有效</el-radio>
          </el-radio-group>
        </el-form-item>


        <el-form-item>
          <el-button type="primary" @click="submitAdd()">{{ id ? '立即修改' : '立即创建' }}</el-button>
        </el-form-item>

      </el-form>
    </el-card>
  </div>
</template>

<script>
import { reactive, ref, toRefs, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { localGet } from '@/utils'
export default {
  name: 'AddSysUser',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const adminRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localGet('token') || '',
      id: id,
      adminForm: {
        username: '',
        password: '',
        fullname: '',
        mobile: '',
        email: '',
        googleCode: ''
      },
      rules: {
        username: [
          { required: 'true', message: '请填写管理员名称', trigger: ['change'] }
        ],
        password: [
          { required: 'true', message: '请填写管理员密码', trigger: ['change'] }
        ]
      },
    })
    onMounted(() => {

      if (id) {
        console.log(id);
        axios.get(`/admin/admin/${id}`).then(res => {
          state.adminForm = {
            username: res.data.username,
            password: res.data.password,
            fullname: res.data.fullname,
            mobile: res.data.mobile,
            email: res.data.email,
            googleCode: res.data.googleCode
          }
        })
      }
    })
    const submitAdd = () => {
      adminRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            username: state.adminForm.username,
            password: state.adminForm.password,
            fullname: state.adminForm.fullname,
            mobile: state.adminForm.mobile,
            email: state.adminForm.email,
            googleCode: state.adminForm.googleCode
          }
          console.log('params', params)
          if (id) {
            params.id = id
            // 修改商品使用 put 方法
            httpOption = axios.put
          }
          httpOption('/admin/admin', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/sysUser' })
          })
        }
      })
    }

    return {
      ...toRefs(state),
      adminRef,
      submitAdd
    }
  }
}
</script>

<style scoped>
  .add {
    display: flex;
  }
  .add-container {
    flex: 1;
    height: 100%;
  }
  .avatar-uploader {
    width: 100px;
    height: 100px;
    color: #ddd;
    font-size: 30px;
  }
  .avatar-uploader-icon {
    display: block;
    width: 100%;
    height: 100%;
    border: 1px solid #e9e9e9;
    padding: 32px 17px;
  }
</style>