<template>
  <el-card class="container">
    <el-form :model="warehouseForm" :rules="rules" ref="warehouseRef" label-width="80px" label-position="right" class="demo-ruleForm">

      <el-form-item label="供应商：" prop="supplierName">
        <el-input style="width: 200px" v-model="warehouseForm.supplierName"></el-input>
      </el-form-item>

      <el-form-item label="仓库名称：" prop="warehouseName">
        <el-input style="width: 200px" v-model="warehouseForm.warehouseName"></el-input>
      </el-form-item>

      <el-form-item label="仓库地址：" prop="warehouseAddress">
        <el-input style="width: 200px" v-model="warehouseForm.warehouseAddress"></el-input>
      </el-form-item>

      <el-form-item label="仓管名称：" prop="warehouseAdminName">
        <el-input style="width: 200px" v-model="warehouseForm.warehouseAdminName"></el-input>
      </el-form-item>

      <el-form-item label="仓管手机：" prop="warehouseAdminPhone">
        <el-input style="width: 200px" v-model="warehouseForm.warehouseAdminPhone"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="submitAdd()">{{ id ? '立即修改' : '立即创建' }}</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  
</template>


<script>
import { reactive, ref, toRefs, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { localGet } from '@/utils'
export default {
  name: 'WarehouseAdd',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const warehouseRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localGet('token') || '',
      id: id,


      warehouseForm: {
        supplierName: '',
        warehouseName: '',
        warehouseAddress: '',
        warehouseAdminName: '',
        warehouseAdminPhone: ''
      },
      rules: {
        supplierName: [
          { required: 'true', message: '请填写仓管名称', trigger: ['change'] }
        ],
        warehouseName: [
          { required: 'true', message: '请填写仓库名称', trigger: ['change'] }
        ]
      },
    })
    onMounted(() => {

      if (id) {
        console.log(id);
        axios.get(`/wms/warehouse/${id}`).then(res => {
          state.warehouseForm = {
            supplierName: res.data.supplierName,
            warehouseName: res.data.warehouseName,
            warehouseAddress: res.data.warehouseAddress,
            warehouseAdminName: res.data.warehouseAdminName,
            warehouseAdminPhone: res.data.warehouseAdminPhone
          }
        })
      }
    })
    const submitAdd = () => {
      warehouseRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            googleCode: state.warehouseForm.googleCode,
            supplierName: state.warehouseForm.supplierName,
            warehouseName: state.warehouseForm.warehouseName,
            warehouseAddress: state.warehouseForm.warehouseAddress,
            warehouseAdminName: state.warehouseForm.warehouseAdminName,
            warehouseAdminPhone: state.warehouseForm.warehouseAdminPhone
          }
          console.log('params', params)
          if (id) {
            params.id = id
            // 修改商品使用 put 方法
            httpOption = axios.put
          }
          httpOption('/wms/warehouse', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/warehouse' })
          })
        }
      })
    }

    return {
      ...toRefs(state),
      warehouseRef,
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