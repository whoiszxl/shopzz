<template>
  <div class="add">
    <el-card class="add-container">
      <el-form :model="supplierForm" :rules="rules" ref="goodRef" label-width="100px" class="supplierForm">
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input style="width: 300px" v-model="supplierForm.supplierName" placeholder="请输入供应商名称"></el-input>
        </el-form-item>

        <el-form-item label="公司名称" prop="companyName">
          <el-input style="width: 300px" v-model="supplierForm.companyName" placeholder="请输入公司名称"></el-input>
        </el-form-item>

        <el-form-item label="公司地址" prop="companyAddress">
          <el-input style="width: 300px" type="textarea" v-model="supplierForm.companyAddress" placeholder="请输入公司地址"></el-input>
        </el-form-item>  

        <el-form-item label="联系人" prop="contactor">
          <el-input style="width: 300px" v-model="supplierForm.contactor" placeholder="请输入联系人"></el-input>
        </el-form-item>  

        <el-form-item label="联系电话" prop="contactPhoneNumber">
          <el-input type="number" min="0" style="width: 300px" v-model="supplierForm.contactPhoneNumber" placeholder="请输入联系电话"></el-input>
        </el-form-item>

        <el-form-item label="结算周期" prop="accountPeriod">
          <el-radio-group v-model="supplierForm.accountPeriod">
            <el-radio label="1">周结算</el-radio>
            <el-radio label="2">月结算</el-radio>
            <el-radio label="3">季度结算</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="银行名称" prop="bankName">
          <el-input min="0" style="width: 300px" v-model="supplierForm.bankName" placeholder="请输入银行名称"></el-input>
        </el-form-item>

        <el-form-item label="银行账户" prop="bankAccount">
          <el-input type="number" min="0" style="width: 300px" v-model="supplierForm.bankAccount" placeholder="请输入银行账户"></el-input>
        </el-form-item>

        <el-form-item label="开户人" prop="accountHolder">
          <el-input min="0" style="width: 300px" v-model="supplierForm.accountHolder" placeholder="请输入开户人"></el-input>
        </el-form-item>

        <el-form-item label="发票抬头" prop="invoiceTitle">
          <el-input min="0" style="width: 300px" v-model="supplierForm.invoiceTitle" placeholder="请输入发票抬头"></el-input>
        </el-form-item>

        <el-form-item label="纳税人识别号" prop="taxpayerId">
          <el-input min="0" style="width: 300px" v-model="supplierForm.taxpayerId" placeholder="请输入纳税人识别号"></el-input>
        </el-form-item>                

        <el-form-item label="经营范围" prop="businessScope">
          <el-input min="0" style="width: 300px" v-model="supplierForm.businessScope" placeholder="请输入经营范围"></el-input>
        </el-form-item>   

        <el-form-item label="说明备注" prop="supplierComment">
          <el-input type="textarea" min="0" style="width: 300px" v-model="supplierForm.supplierComment" placeholder="请输入说明备注"></el-input>
        </el-form-item>           

        <el-form-item label="企业资质" prop="enterpriseQualification">
          <el-input min="0" style="width: 300px" v-model="supplierForm.enterpriseQualification" placeholder="请输入企业资质"></el-input>
        </el-form-item>   

        <el-form-item label="合作合同" prop="cooperateContract">
          <el-input min="0" style="width: 300px" v-model="supplierForm.cooperateContract" placeholder="请输入合作合同"></el-input>
        </el-form-item>   

        <el-form-item label="协议价合同" prop="priceContract">
          <el-input min="0" style="width: 300px" v-model="supplierForm.priceContract" placeholder="请输入协议价合同"></el-input>
        </el-form-item>   

        <el-form-item label="采购合同" prop="purchaseContract">
          <el-input min="0" style="width: 300px" v-model="supplierForm.purchaseContract" placeholder="请输入采购合同"></el-input>
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
      supplierForm: {
        id: null,
        supplierName: '',
        companyName: '',
        companyAddress: '',
        contactor: '',
        contactPhoneNumber: '',
        accountPeriod: '',
        bankName: '',
        bankAccount: '',
        accountHolder: '',
        invoiceTitle: '',
        taxpayerId: '',
        businessScope: '',
        supplierComment: '',
        enterpriseQualification: '',
        cooperateContract: '',
        priceContract: '',
        purchaseContract: '',
      },
      rules: {
        supplierName: [
          { required: 'true', message: '请填写供应商名称', trigger: ['change'] }
        ],
        companyName: [
          { required: 'true', message: '请填写公司名称', trigger: ['change'] }
        ],
        companyAddress: [
          { required: 'true', message: '请填写公司地址', trigger: ['change'] }
        ],
        contactor: [
          { required: 'true', message: '请填写联系人姓名', trigger: ['change'] }
        ],
      },
    })
    onMounted(() => {
      if (id) {
        axios.get(`/wms/purchase-supplier/${id}`).then(res => {
          state.supplierForm = res.data;
          state.supplierForm.accountPeriod = res.data.accountPeriod;
          console.log(res.data);
        })
      }
    })
    const submitAdd = () => {
      goodRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post
          if (id) {
            state.supplierForm.id = id;
            // 修改商品使用 put 方法
            httpOption = axios.put
          }
          httpOption('/wms/purchase-supplier', state.supplierForm).then(() => {
            ElMessage.success(id ? '修改成功' : '添加成功')
            router.push({ path: '/supplier' })
          })
        }
      })
    }

    return {
      ...toRefs(state),
      goodRef,
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