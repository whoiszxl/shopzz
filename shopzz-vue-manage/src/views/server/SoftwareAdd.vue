<template>
  <el-card class="server-container">
  <template #header>
    <div class="header">
      <el-button @click="back" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-arrow-left">返回上一页</el-button>
    </div>
  </template>

  <div class="add">
    <el-card class="add-container">
      <el-form :model="softwareForm" :rules="rules" ref="softwareRef" label-width="100px" class="softwareForm">
        <el-form-item label="组件名称">
          <el-input style="width: 300px" v-model="softwareForm.softwareName" placeholder="请输入组件名称"></el-input>
        </el-form-item>
        <el-form-item label="组件文件名" prop="softwareFilename">
          <el-input style="width: 300px" v-model="softwareForm.softwareFilename" placeholder="请输入组件文件名"></el-input>
        </el-form-item>
        <el-form-item label="组件文件路径" prop="softwarePath">
          <el-input style="width: 300px" type="textarea" v-model="softwareForm.softwarePath" placeholder="请输入组件文件路径"></el-input>
        </el-form-item>
        <el-form-item label="组件安装路径" prop="installPath">
          <el-input style="width: 300px" type="textarea" v-model="softwareForm.installPath" placeholder="请输入组件安装路径"></el-input>
        </el-form-item>

        <el-form-item label="修改安装状态" prop="installStatus">
          <el-radio-group v-model="softwareForm.installStatus">
            <el-radio :label="1">未安装</el-radio>
            <el-radio :label="2">部分安装</el-radio>
            <el-radio :label="3">全安装</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="环境变量路径" prop="envPath">
        </el-form-item>
        <el-form-item label="环境变量内容" prop="envContent">
          <el-input style="width: 600px" autosize type="textarea" v-model="softwareForm.envContent" placeholder="请输入环境变量内容"></el-input>
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
  name: 'SoftwareAdd',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const softwareRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localStorage.getItem('token') || '',
      id: id,
      softwareForm: {
        softwareName: '',
        softwareFilename: '',
        softwarePath: '',
        installPath: '',
        installStatus: '',
        envPath: '',
        envContent: ''
      },
      rules: {
        softwareName: [
          { required: 'true', message: '请填写组件名称', trigger: ['change'] }
        ]
      },
    })
    onMounted(() => {
      if (id) {
        axios.get(`/software/${id}`).then(res => {
          state.softwareForm = {
            softwareName: res.data.data.softwareName,
            softwareFilename: res.data.data.softwareFilename,
            softwarePath: res.data.data.softwarePath,
            installPath: res.data.data.installPath,
            installStatus: res.data.data.installStatus,
            envPath: res.data.data.envPath,
            envContent: res.data.data.envContent,
          }
        })
      }
    })
    onBeforeUnmount(() => {
    })
    const submitAdd = () => {
      softwareRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            softwareName: state.softwareForm.softwareName,
            softwareFilename: state.softwareForm.softwareFilename,
            softwarePath: state.softwareForm.softwarePath,
            installPath: state.softwareForm.installPath,
            installStatus: state.softwareForm.installStatus,
            envPath: state.softwareForm.envPath,
            envContent: state.softwareForm.envContent,
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/software', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/software' })
          })
        }
      })
    }

    const back = () => {
      router.go(-1);
    }

    return {
      ...toRefs(state),
      softwareRef,
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