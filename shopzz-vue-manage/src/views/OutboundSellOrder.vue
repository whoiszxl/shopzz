<template>
  <el-card class="container">
    <template #header>
      <div class="header">
        <el-input v-model="state.orderNo" size="mini" style="width:150px; margin-left:3px;" placeholder="订单号查询"></el-input>
        <el-input v-model="state.receiveName" size="mini" style="width:150px; margin-left:3px;" placeholder="收件人查询"></el-input>

        <el-button @click="search" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">搜索</el-button>
      </div>

    </template>
    <Table
      action='/wms/outbound/sell/list'
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


        <el-table-column prop="receiveName" label="收货人姓名"> </el-table-column>
        <el-table-column prop="receiveAddress" label="收货人地址"> </el-table-column>
        <el-table-column prop="receivePhone" label="收货人手机号"> </el-table-column>

        <el-table-column prop="expressNo" label="快递单号"> </el-table-column>
        <el-table-column prop="expressCode" label="快递公司"> </el-table-column>
        <el-table-column prop="orderComment" label="订单备注"> </el-table-column>



        <el-table-column width="180" label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
            <el-button @click="handleToDetail(scope.row.id)" type="primary" size="small" icon="el-icon-delete">查询详情</el-button>
            <el-button @click="handleDelete(scope.row.id)" type="primary" size="small" icon="el-icon-delete">删除</el-button>
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
  name: 'OutboundSellOrder',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter()

    const state = reactive({
      orderNo: '',
      receiveName: ''
    })

    const search = () => {
        table.value.getList({"orderNo": state.orderNo, "receiveName": state.receiveName});
    }


    const handleDelete = (id) => {

      axios.delete(`/wms/outbound/sell/` + id, {
        }).then(() => {
            ElMessage.success('删除成功')
            table.value.getList()
        })

    }

    const handleToDetail = (id) => {
      router.push({ path: '/outboundSellOrder/detail', query: { id } })
    }

    return {
      search,
      handleDelete,
      handleToDetail,
      table,
      state
    }
  }
}
</script>

<style>

</style>