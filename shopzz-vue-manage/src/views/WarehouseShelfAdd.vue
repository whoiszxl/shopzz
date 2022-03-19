<template>
  <el-card class="container">
    <el-form :model="warehouseShelfForm" :rules="rules" ref="warehouseShelfRef" label-width="80px" label-position="right" class="demo-ruleForm">

      <el-form-item label="货架编号：" prop="shelfCode" label-width="200">
        <el-input style="width: 200px" v-model="warehouseShelfForm.shelfCode"></el-input>
      </el-form-item>

      <el-form-item label="说明备注：" prop="shelfComment" label-width="200">
        <el-input style="width: 200px" v-model="warehouseShelfForm.shelfComment"></el-input>
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
    const warehouseShelfRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localGet('token') || '',
      id: id,


      warehouseShelfForm: {
        shelfCode: '',
        shelfComment: ''
      },
      rules: {
        shelfCode: [
          { required: 'true', message: '请填写货架编号', trigger: ['change'] }
        ],
        shelfComment: [
          { required: 'true', message: '请填写说明描述', trigger: ['change'] }
        ]
      },
    })
    onMounted(() => {

      if (id) {
        console.log(id);
        axios.get(`/wms/warehouse/shelf/${id}`).then(res => {
          state.warehouseShelfForm = {
            shelfCode: res.data.shelfCode,
            shelfComment: res.data.shelfComment
          }
        })
      }
    })
    const submitAdd = () => {
      warehouseShelfRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            shelfCode: state.warehouseShelfForm.shelfCode,
            shelfComment: state.warehouseShelfForm.shelfComment
          }
          console.log('params', params)
          if (id) {
            params.id = id
            // 修改商品使用 put 方法
            httpOption = axios.put
          }
          httpOption('/wms/warehouse/shelf', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/warehouseShelf' })
          })
        }
      })
    }

    return {
      ...toRefs(state),
      warehouseShelfRef,
      submitAdd
    }
  }
}
</script>