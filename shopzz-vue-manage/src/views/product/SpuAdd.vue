<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="skuForm" :rules="rules" ref="skuRef" label-width="100px" class="skuForm">
        <el-form-item required label="SPU分类">
          <el-cascader :placeholder="defaultCate" style="width: 300px" :props="category" @change="handleChangeCate"></el-cascader>
        </el-form-item>
        <el-form-item label="SPU名称" prop="name">
          <el-input style="width: 300px" v-model="skuForm.name" placeholder="请输入SPU名称"></el-input>
        </el-form-item>
        <el-form-item label="SPU副名称" prop="subName">
          <el-input style="width: 300px" type="textarea" v-model="skuForm.subName" placeholder="请输入SPU副名称"></el-input>
        </el-form-item>

        <el-form-item label="SPU品牌ID" prop="brandId">
          <el-input style="width: 300px" v-model="skuForm.brandId" placeholder="请输入SPU品牌ID"></el-input>
        </el-form-item>

        <el-form-item label="默认价格" prop="defaultPrice">
          <el-input type="number" min="0" style="width: 300px" v-model="skuForm.defaultPrice" placeholder="请输入SPU默认价格"></el-input>
        </el-form-item>

        <el-form-item label="包装列表" prop="packageList">
          <el-input style="width: 300px" type="textarea" v-model="skuForm.packageList" placeholder="请输入SPU包装列表"></el-input>
        </el-form-item>
        
        <el-form-item label="删除状态" prop="deleteStatus">
          <el-radio-group v-model="skuForm.deleteStatus">
            <el-radio label="0">未删除</el-radio>
            <el-radio label="1">已删除</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上架状态" prop="publishStatus">
          <el-radio-group v-model="skuForm.publishStatus">
            <el-radio label="0">下架</el-radio>
            <el-radio label="1">上架</el-radio>
          </el-radio-group>
        </el-form-item>       

        <el-form-item label="审核状态" prop="verifyStatus">
          <el-radio-group v-model="skuForm.verifyStatus">
            <el-radio label="0">未审核</el-radio>
            <el-radio label="1">审核通过</el-radio>
          </el-radio-group>
        </el-form-item>


        <el-form-item required label="SPU主图" prop="defaultPic">
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
            <img style="width: 100px; height: 100px; border: 1px solid #e9e9e9;" v-if="skuForm.defaultPic" :src="skuForm.defaultPic" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>


        <el-form-item  style="padding-top:30px;" label="详情内容">
          
          <el-input style="width: 300px" type="textarea" v-model="skuForm.detailHtml" placeholder="请输入SPU详情"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAdd()">{{ id ? '立即修改' : '立即创建' }}</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { reactive, ref, toRefs, onBeforeUnmount } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { localGet, uploadImgServer } from '@/utils'
export default {

  name: 'SpuAdd',
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
        brandId: '',
        defaultPrice: '',
        packageList: '',
        deleteStatus: '0',
        publishStatus: '',
        verifyStatus: '',
        defaultPic: '',
        detailHtml: ''
      },
      rules: { // 规则
        name: [
          { required: 'true', message: '请填写SPU名称', trigger: ['change'] }
        ],
        defaultPrice: [
          { required: 'true', message: '请填写SPU价格', trigger: ['change'] }
        ]
      },
      categoryId: '', // 分类 id
      category: { // 联动组件 props 属性
        lazy: true,
        lazyLoad(node, resolve) { // 懒加载分类方法
          const { level = 0, value } = node
          axios.post('/product/category/list', {
              page: 1,
              size: 10000,
              level: level + 1,
              parentId: value || 0
          }).then(res => {
            const list = res.data.records
            
            const nodes = list.map(item => ({
              value: item.id,
              label: item.name,
              leaf: level > 1
            }))
            
            resolve(nodes)
          })
        }
      }
    })

    onBeforeUnmount(() => {
    })

    // 添加SPU方法
    const submitAdd = () => {
      skuRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            categoryId: state.categoryId,
            name: state.skuForm.name,
            subName: state.skuForm.subName,
            brandId: state.skuForm.brandId,
            defaultPrice: state.skuForm.defaultPrice,
            packageList: state.skuForm.packageList,
            deleteStatus: state.skuForm.deleteStatus,
            publishStatus: state.skuForm.publishStatus,
            verifyStatus: state.skuForm.verifyStatus,
            defaultPic: state.skuForm.defaultPic,
            detailHtml: state.skuForm.detailHtml,
          }
          console.log('params', params)
          if (id) {
            params.id = id
            httpOption = axios.put
          }
          httpOption('/product/spu', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/spuList' })
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
      state.skuForm.goodsCoverImg = val.data || ''
    }
    // 联动变化后的回调
    const handleChangeCate = (val) => {
      state.categoryId = val[2] || 0
    }
    return {
      ...toRefs(state),
      skuRef,
      submitAdd,
      handleBeforeUpload,
      handleUrlSuccess,
      handleChangeCate
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