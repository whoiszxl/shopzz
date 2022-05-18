<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="skuForm" :rules="rules" ref="skuRef" label-width="100px" class="skuForm">


        <el-form-item label="秒杀活动名称" prop="name">
          <el-input style="width: 300px" v-model="skuForm.name" placeholder="请输入秒杀活动名称"></el-input>
        </el-form-item>

        <el-form-item label="秒杀活动描述" prop="descs">
          <el-input style="width: 300px" v-model="skuForm.descs" placeholder="请输入秒杀活动描述"></el-input>
        </el-form-item>

        <el-form-item label="有效期开始时间" prop="startTime">
          <el-input style="width: 300px" v-model="skuForm.startTime" placeholder="请输入有效期开始时间"></el-input>
        </el-form-item>

        <el-form-item label="有效期结束时间" prop="endTime">
          <el-input style="width: 300px" v-model="skuForm.endTime" placeholder="请输入有效期结束时间"></el-input>
        </el-form-item>


        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="skuForm.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="2">禁用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item required label="秒杀活动" prop="img">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgServer"
            accept="jpg,jpeg,png"
            :headers="{
              token: token
            }"
            :show-file-list="false"
            :before-upload="handleBeforeUpload"
            :on-success="handleUrlSuccess"
          >
            <img style="height: 100px; border: 1px solid #e9e9e9;" v-if="skuForm.img" :src="skuForm.img" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item style="padding-top:40px;">
          <el-button type="primary" @click="submitAdd()">{{ id ? '立即修改' : '立即创建' }}</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { reactive, ref, toRefs, onBeforeUnmount, onMounted } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { localGet, uploadImgServer } from '@/utils'
export default {

  name: 'seckillAdd',
  setup() {
    const skuRef = ref(null) // 表单 ref
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query // 编辑时传入的SPU id
    const state = reactive({
      uploadImgServer, // 上传图片的接口地址，单图上传
      token: localGet('token') || '', // 存在本地的 token
      id: id,
      defaultCate: '', // 默认分类值
      skuForm: { // SPU表单内容
        name: '',
        descs: '',
        startTime: '',
        endTime: '',
        status: '',
        img: ''
      },
      rules: { // 规则
        name: [
          { required: 'true', message: '请填写秒杀活动名称', trigger: ['change'] }
        ]
      },
      
    })


    onMounted(() => {
      if (id) {
        axios.get(`/promotion/seckill/${id}`).then(res => {
            state.skuForm = res.data;
        })
      }
    })

    // 添加SPU方法
    const submitAdd = () => {
      skuRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            name: state.skuForm.name,
            descs: state.skuForm.descs,
            startTime: state.skuForm.startTime,
            endTime: state.skuForm.endTime,
            status: state.skuForm.status,
            img: state.skuForm.img
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/promotion/seckill', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/seckillList' })
          })
        }
      })
    }
    // 上传之前，判断一下文件格式
    const handleBeforeUpload = (file) => {
      const sufix = file.name.split('.')[1] || ''
      if (!['jpg', 'jpeg', 'png'].includes(sufix)) {
        ElMessage.error('请上传 jpg、jpeg、png 格式的图片')
        return false
      }
    }
    // 图片上传成功后的回调
    const handleUrlSuccess = (val) => {
      state.skuForm.pic = val.data || ''
    }

    return {
      ...toRefs(state),
      skuRef,
      submitAdd,
      handleBeforeUpload,
      handleUrlSuccess,
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