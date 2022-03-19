<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="goodForm" :rules="rules" ref="goodRef" label-width="100px" class="goodForm">
        <el-form-item label="账号" prop="username">
          <el-input style="width: 300px" v-model="goodForm.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="fullname">
          <el-input style="width: 300px" type="textarea" v-model="goodForm.fullname" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input type="number" min="0" style="width: 300px" v-model="goodForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input type="textarea" min="0" style="width: 300px" v-model="goodForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="谷歌验证码" prop="google_code">
          <el-input type="textarea" min="0" style="width: 300px" v-model="goodForm.googleCode" placeholder="请输入谷歌验证码"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="goodForm.status">
            <el-radio label="0">上架</el-radio>
            <el-radio label="1">下架</el-radio>
          </el-radio-group>
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
import { localGet } from '@/utils'
export default {
  name: 'AddSysUser',
  setup() {
    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const goodRef = ref(null)
    const route = useRoute()
    const router = useRouter()
    const { id } = route.query
    const state = reactive({
      token: localGet('token') || '',
      id: id,
      goodForm: {
        goodsName: '',
        goodsIntro: '',
        originalPrice: '',
        sellingPrice: '',
        stockNum: '',
        goodsSellStatus: '0',
        goodsCoverImg: '',
        tag: ''
      },
      rules: {
        goodsName: [
          { required: 'true', message: '请填写商品名称', trigger: ['change'] }
        ],
        originalPrice: [
          { required: 'true', message: '请填写商品价格', trigger: ['change'] }
        ],
        sellingPrice: [
          { required: 'true', message: '请填写商品售价', trigger: ['change'] }
        ],
        stockNum: [
          { required: 'true', message: '请填写商品库存', trigger: ['change'] }
        ],
      },
      categoryId: '',
      category: {
        lazy: true,
        lazyLoad(node, resolve) {
          const { level = 0, value } = node
          axios.get('/categories', {
            params: {
              pageNumber: 1,
              pageSize: 1000,
              categoryLevel: level + 1,
              parentId: value || 0
            }
          }).then(res => {
            const list = res.list
            const nodes = list.map(item => ({
              value: item.categoryId,
              label: item.categoryName,
              leaf: level > 1
            }))
            resolve(nodes)
          })
        }
      }
    })
    onMounted(() => {

      if (id) {
        axios.get(`/goods/${id}`).then(res => {
          const { goods, firstCategory, secondCategory, thirdCategory } = res
          state.goodForm = {
            goodsName: goods.goodsName,
            goodsIntro: goods.goodsIntro,
            originalPrice: goods.originalPrice,
            sellingPrice: goods.sellingPrice,
            stockNum: goods.stockNum,
            goodsSellStatus: String(goods.goodsSellStatus),
            goodsCoverImg: proxy.$filters.prefix(goods.goodsCoverImg),
            tag: goods.tag
          }
          state.categoryId = goods.goodsCategoryId
          state.defaultCate = `${firstCategory.categoryName}/${secondCategory.categoryName}/${thirdCategory.categoryName}`
        })
      }
    })
    const submitAdd = () => {
      goodRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          let params = {
            goodsCategoryId: state.categoryId,
            goodsCoverImg: state.goodForm.goodsCoverImg,
            goodsDetailContent: instance.txt.html(),
            goodsIntro: state.goodForm.goodsIntro,
            goodsName: state.goodForm.goodsName,
            goodsSellStatus: state.goodForm.goodsSellStatus,
            originalPrice: state.goodForm.originalPrice,
            sellingPrice: state.goodForm.sellingPrice,
            stockNum: state.goodForm.stockNum,
            tag: state.goodForm.tag
          }
          console.log('params', params)
          if (id) {
            params.goodsId = id
            // 修改商品使用 put 方法
            httpOption = axios.put
          }
          httpOption('/goods', params).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/good' })
          })
        }
      })
    }

    return {
      ...toRefs(state),
      goodRef,
      submitAdd,
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