<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="skuForm" :rules="rules" ref="skuRef" label-width="100px" class="skuForm">


        <el-form-item label="优惠券名称" prop="name">
          <el-input style="width: 300px" v-model="skuForm.name" placeholder="请输入优惠券名称"></el-input>
        </el-form-item>

        <el-form-item label="优惠券副名称" prop="subName">
          <el-input style="width: 300px" v-model="skuForm.subName" placeholder="请输入优惠券副名称"></el-input>
        </el-form-item>

        <el-form-item label="有效期起始时间" prop="startTime">
          <el-input style="width: 300px" v-model="skuForm.startTime" placeholder="请输入有效期起始时间"></el-input>

          <!-- <el-date-picker
            v-model="value1"
            type="datetimerange"
            range-separator="To"
            start-placeholder="Start date"
            end-placeholder="End date"
          /> -->

        </el-form-item>

        <el-form-item label="有效期结束时间" prop="endTime">
          <el-input style="width: 300px" v-model="skuForm.endTime" placeholder="请输入有效期结束时间"></el-input>
        </el-form-item>

        <el-form-item label="使用阈值" prop="useThreshold">
          <el-input style="width: 300px" v-model="skuForm.useThreshold" placeholder="请输入优惠券使用阈值"></el-input>
        </el-form-item>

        <el-form-item label="折扣金额" prop="discountAmount">
          <el-input style="width: 300px" v-model="skuForm.discountAmount" placeholder="请输入优惠券折扣金额"></el-input>
        </el-form-item>        


        <el-form-item label="折扣比率" prop="discountRate">
          <el-input style="width: 300px" v-model="skuForm.discountRate" placeholder="请输入优惠券折扣比率"></el-input>
        </el-form-item>   

        <el-form-item label="优惠券类型" prop="type">
          <el-radio-group v-model="skuForm.type">
            <el-radio :label="1">满减券</el-radio>
            <el-radio :label="2">满减折扣券</el-radio>
            <el-radio :label="3">无门槛立减券</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="全场限制" prop="fullLimited">
          <el-radio-group v-model="skuForm.fullLimited">
            <el-radio :label="1">全场通用</el-radio>
            <el-radio :label="2">分类限制</el-radio>
          </el-radio-group>
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

  name: 'CouponAdd',
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
        subName: '',
        startTime: '',
        endTime: '',
        useThreshold: '',
        discountAmount: '',
        discountRate: '',
        type: '',
        fullLimited: ''
      },
      rules: { // 规则
        name: [
          { required: 'true', message: '请填写优惠券名称', trigger: ['change'] }
        ]
      },
      
    })


    onMounted(() => {
      if (id) {
        axios.get(`/promotion/coupon/${id}`).then(res => {
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
            pic: state.skuForm.pic,

            name: state.skuForm.name,
            subName: state.skuForm.subName,
            startTime: state.skuForm.startTime,
            endTime: state.skuForm.endTime,
            useThreshold: state.skuForm.useThreshold,
            discountAmount: state.skuForm.discountAmount,
            discountRate: state.skuForm.discountRate,
            type: state.skuForm.type,
            fullLimited: state.skuForm.fullLimited
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/promotion/coupon', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/couponList' })
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