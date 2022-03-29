<template>
  <el-card class="container">
    <template #header>
      <div class="header">
        <el-input v-model="state.supplierId" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入供应商ID"></el-input>
        <el-input v-model="state.purchaseOrderStatus" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入采购单状态"></el-input>

        <el-button @click="search" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">搜索</el-button>
      </div>

    </template>
    <Table
      action='/wms/inbound/return/list'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>
        <el-table-column prop="orderNo" label="订单编号"> </el-table-column>
        <el-table-column prop="payType" label="支付方式"> </el-table-column>
        <el-table-column prop="totalAmount" label="订单总额"> </el-table-column>
        <el-table-column prop="freightAmount" label="运费金额"> </el-table-column>
        <el-table-column prop="promotionAmount" label="促销折扣金额"> </el-table-column>
        <el-table-column prop="pointAmount" label="积分抵扣金额"> </el-table-column>
        <el-table-column prop="couponAmount" label="优惠券抵扣金额"> </el-table-column>
        <el-table-column prop="adminDiscountAmount" label="管理后台折扣金额"> </el-table-column>
        <el-table-column prop="payAmount" label="应付金额"> </el-table-column>
        <el-table-column prop="returnReason" label="退货理由"> </el-table-column>
        <el-table-column prop="returnComment" label="退货备注"> </el-table-column>
        <el-table-column prop="arrivalTime" label="到货时间"> </el-table-column>
        <el-table-column width="180" label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
            <el-popconfirm title="确认删除?" confirm-button-text="确认" cancel-button-text="取消" @confirm="handleDelete(scope.row.id)">
                <template #reference>
                    <el-button type="primary" size="small" icon="el-icon-delete">删除</el-button>
                </template>
            </el-popconfirm>
            </span>
          </template>
        </el-table-column>
      </template>
    </Table>
  </el-card>
</template>

<script>
import { ref, reactive } from 'vue'
import Table from '@/components/Table.vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/axios'
import { useRouter } from 'vue-router'

export default {
  name: 'InboundReturnOrder',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter()

    const state = reactive({
      supplierName: '',
      accountPeriod: ''
    })

    const search = () => {
        table.value.getList();
    }


    const handleDelete = (id) => {

      axios.delete(`/wms/inbound/return/` + id, {
        }).then(() => {
            ElMessage.success('删除成功')
            table.value.getList()
        })

    }

    return {
      search,
      handleDelete,
      table,
      state
    }
  }
}
</script>

<style>

</style>