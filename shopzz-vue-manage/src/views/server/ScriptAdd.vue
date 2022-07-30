<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="scriptForm" :rules="rules" ref="scriptRef" label-width="100px" class="scriptForm">
        <el-form-item label="脚本名称" prop="scriptName">
          <el-input style="width: 300px" v-model="scriptForm.scriptName" placeholder="请输入脚本名称"/>
        </el-form-item>
        <el-form-item label="脚本路径" prop="scriptPath">
          <el-input style="width: 300px" v-model="scriptForm.scriptPath" placeholder="请输入脚本路径"/>
        </el-form-item>
        <el-form-item label="脚本内容" prop="scriptContent">
          <el-input  style="width: 600px" type="textarea" autosize v-model="scriptForm.scriptContent" placeholder="请输入脚本内容"/>
        </el-form-item>
        <el-form-item label="脚本描述" prop="scriptDesc">
          <el-input style="width: 300px" autosize v-model="scriptForm.scriptDesc" placeholder="请输入脚本描述"/>
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
export default {
  name: 'ScriptAdd',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const scriptRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localStorage.getItem('token') || '',
      id: id,
      scriptForm: {
        scriptName: '',
        scriptPath: '',
        scriptContent: '',
        scriptDesc: ''
      },
      rules: {
        scriptName: [
          { required: 'true', message: '请填写脚本名称', trigger: ['change'] }
        ]
      },
    })
    onMounted(() => {
      if (id) {
        axios.get(`/script/${id}`).then(res => {
          state.scriptForm = {
            scriptName: res.data.data.scriptName,
            scriptPath: res.data.data.scriptPath,
            scriptContent: res.data.data.scriptContent,
            scriptDesc: res.data.data.scriptDesc
          }
        })
      }
    })
    onBeforeUnmount(() => {
    })
    const submitAdd = () => {
      scriptRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            scriptName: state.scriptForm.scriptName,
            scriptPath: state.scriptForm.scriptPath,
            scriptContent: state.scriptForm.scriptContent,
            scriptDesc: state.scriptForm.scriptDesc
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/script', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/script' })
          })
        }
      })
    }

    return {
      ...toRefs(state),
      scriptRef: scriptRef,
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