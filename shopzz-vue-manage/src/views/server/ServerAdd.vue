<template>

  <el-card class="server-container">
    <template #header>
      <div class="header">
        <el-button @click="back" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-arrow-left">返回上一页</el-button>
      </div>
    </template>

    <div class="add">
      <el-card class="add-container">
        <el-form :model="serverForm" :rules="rules" ref="serverRef" label-width="100px" class="serverForm">
          <el-form-item label="host名称" prop="serverName">
            <el-input style="width: 300px" v-model="serverForm.serverName" placeholder="请输入服务器host名称"></el-input>
          </el-form-item>
          <el-form-item label="外网地址" prop="serverOuterIp">
            <el-input style="width: 300px" v-model="serverForm.serverOuterIp" placeholder="请输入外网地址"></el-input>
          </el-form-item>
          <el-form-item label="内网地址" prop="serverInnerIp">
            <el-input style="width: 300px" v-model="serverForm.serverInnerIp" placeholder="请输入内网地址"></el-input>
          </el-form-item>
          <el-form-item label="端口" prop="serverPort">
            <el-input style="width: 300px" v-model="serverForm.serverPort" placeholder="请输入端口"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="serverUsername">
            <el-input style="width: 300px" v-model="serverForm.serverUsername" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="serverPassword">
            <el-input style="width: 300px" v-model="serverForm.serverPassword" placeholder="请输入密码"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitAdd()">{{ id ? '立即修改' : '立即创建' }}</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </el-card>
</template>

<script>
import { reactive, ref, toRefs, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
export default {
  name: 'ServerAdd',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const serverRef = ref(null) 
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localStorage.getItem('token') || '',
      id: id,
      serverForm: {
        serverName: '',
        serverOuterIp: '',
        serverInnerIp: '',
        serverPort: '',
        serverUsername: '',
        serverPassword: ''
      },
      rules: {
        serverName: [
          { required: 'true', message: '请填写服务器名称', trigger: ['change'] }
        ]
      },
    })
    onMounted(() => {
      if (id) {
        axios.get(`/server/${id}`).then(res => {
          state.serverForm = {
            serverName: res.data.data.serverName,
            serverOuterIp: res.data.data.serverOuterIp,
            serverInnerIp: res.data.data.serverInnerIp,
            serverPort: res.data.data.serverPort,
            serverUsername: res.data.data.serverUsername,
            serverPassword: res.data.data.serverPassword
          }
        })
      }
    })
    onBeforeUnmount(() => {
    })
    const submitAdd = () => {
      serverRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            serverName: state.serverForm.serverName,
            serverOuterIp: state.serverForm.serverOuterIp,
            serverInnerIp: state.serverForm.serverInnerIp,
            serverPort: state.serverForm.serverPort,
            serverUsername: state.serverForm.serverUsername,
            serverPassword: state.serverForm.serverPassword,
            status: 1
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/server', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/server' })
          })
        }
      })
    }

    const back = () => {
      router.go(-1);
    }

    return {
      ...toRefs(state),
      serverRef: serverRef,
      submitAdd,
      back
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