<template>
  <el-card class="server-container">
    <template #header>
      <div class="header">
        <el-button @click="back" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-arrow-left">返回上一页</el-button>
      </div>
    </template>

  <div class="add">
    <el-card class="add-container">
      <el-form :model="softwareConfigForm" :rules="rules" ref="softwareConfigRef" label-width="100px" class="softwareConfigForm">
        <el-form-item label="组件名称">
          <el-input style="width: 300px" v-model="softwareConfigForm.softwareName"/>
          <el-select v-model="softwareConfigForm.softwareName" placeholder="请选择">
            <el-option
                v-for="item in softwareList"
                :key="item.softwareName"
                :label="item.softwareName"
                :value="item.softwareName"
            >
            </el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="配置名称">
          <el-input style="width: 300px" v-model="softwareConfigForm.configName"/>
        </el-form-item>
        <el-form-item label="配置路径">
          <el-input style="width: 300px" v-model="softwareConfigForm.configPath"/>
        </el-form-item>
        <el-form-item label="配置模板">
          <el-input style="width: 600px" type="textarea" autosize v-model="softwareConfigForm.configTemplate"/>
        </el-form-item>
        <el-form-item label="配置模板参数">
          <el-input style="width: 600px" type="textarea" autosize v-model="softwareConfigForm.configTemplateParams"/>
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
  name: 'SoftwareConfigAdd',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const softwareConfigRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localStorage.getItem('token') || '',
      id: id,
      softwareConfigForm: {
        softwareId: '',
        softwareName: '',
        configName: '',
        configPath: '',
        configTemplate: '',
        configTemplateParams: ''
      },

      softwareList: [],

      rules: {
        softwareName: [
          { required: 'true', message: '请填写组件名称', trigger: ['change'] }
        ]
      },
    })

    const back = () => {
      router.go(-1);
    }

    onMounted(() => {
      axios.post(`/admin/software/list`, {
        params: {
          page: 1,
          size: 100000
        }
      }).then(res => {
        state.softwareList = res.data.records;
      })

      if (id) {
        axios.get(`/admin/software-config/${id}`).then(res => {
          state.softwareConfigForm = {
            softwareId: res.data.softwareId,
            softwareName: res.data.softwareName,
            configName: res.data.configName,
            configPath: res.data.configPath,
            configTemplate: res.data.configTemplate,
            configTemplateParams: res.data.configTemplateParams
          }
        })
      }
    })
    onBeforeUnmount(() => {
    })
    const submitAdd = () => {
      softwareConfigRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            softwareId: state.softwareConfigForm.softwareId,
            softwareName: state.softwareConfigForm.softwareName,
            configName: state.softwareConfigForm.configName,
            configPath: state.softwareConfigForm.configPath,
            configTemplate: state.softwareConfigForm.configTemplate,
            configTemplateParams: state.softwareConfigForm.configTemplateParams
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/software-config', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/software/config' })
          })
        }
      })
    }

    return {
      ...toRefs(state),
      softwareConfigRef,
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