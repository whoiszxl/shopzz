<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="skuForm" :rules="rules" ref="skuRef" label-width="100px" class="skuForm">


        <el-form-item label="轮播图名称" prop="name">
          <el-input style="width: 300px" v-model="skuForm.name" placeholder="请输入轮播图名称"></el-input>
        </el-form-item>


        <el-form-item label="轮播图类型" prop="type">
          <el-radio-group v-model="skuForm.type">
            <el-radio :label="0">PC首页轮播</el-radio>
            <el-radio :label="1">app首页轮播</el-radio>
            <el-radio :label="2">app导航小组件</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="业务跳转ID" prop="bizId">
          <el-input style="width: 300px" v-model="skuForm.bizId" placeholder="请输入业务跳转ID"></el-input>
        </el-form-item>


        <el-form-item required label="轮播图" prop="pic">
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
            <img style="height: 100px; border: 1px solid #e9e9e9;" v-if="skuForm.pic" :src="skuForm.pic" class="avatar">
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

  name: 'BannerAdd',
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
        type: '',
        bizId: '',
        pic: ''
      },
      rules: { // 规则
        name: [
          { required: 'true', message: '请填写轮播图名称', trigger: ['change'] }
        ]
      },
      
    })


    onMounted(() => {
      if (id) {
        axios.get(`/promotion/banner/${id}`).then(res => {
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
            type: state.skuForm.type,
            bizId: state.skuForm.bizId,
            pic: state.skuForm.pic
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/promotion/banner', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/bannerList' })
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