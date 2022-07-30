<template>
  <el-card class="softwareConfig-container">
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
      </el-form>
    </el-card>
  </div>
  </el-card>
</template>

<script>
import {getCurrentInstance, onBeforeUnmount, onMounted, reactive, ref, toRefs} from 'vue'
import axios from '@/utils/axios'
import {useRoute, useRouter} from 'vue-router'

export default {
  name: 'SoftwareConfigDetail',
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

      tableData: [],
    })

    const back = () => {
      router.go(-1);
    }

    onMounted(() => {
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

    return {
      ...toRefs(state),
      softwareConfigRef: softwareConfigRef,
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