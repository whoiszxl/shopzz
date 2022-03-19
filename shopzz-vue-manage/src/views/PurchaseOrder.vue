<template>
  <el-card class="purchase-container">
    <template #header>
      <div class="header">
        <el-input v-model="state.supplierId" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入供应商ID"></el-input>
        <el-input v-model="state.purchaseOrderStatus" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入采购单状态"></el-input>

        <el-button @click="search" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">搜索</el-button>
      </div>

      <br>
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增采购单</el-button>

    </template>
    <Table
      action='/wms/purchase-order'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>
        <el-table-column prop="supplierId" label="供应商ID"> </el-table-column>
        <el-table-column prop="expectArrivalTime" label="预计到货时间"> </el-table-column>
        <el-table-column prop="contactor" label="联系人"> </el-table-column>
        <el-table-column prop="contactPhoneNumber" label="联系电话"> </el-table-column>
        <el-table-column prop="contactEmail" label="联系邮箱"> </el-table-column>
        <el-table-column prop="comment" label="说明备注"> </el-table-column>
        <el-table-column prop="purchaser" label="采购员"> </el-table-column>
        <el-table-column prop="purchaseOrderStatus" label="采购单状态"> </el-table-column>
        <el-table-column width="180" label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
            <el-button @click="handleEdit(scope.row.id)" type="primary" size="small" icon="el-icon-star-on">编辑</el-button>
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
  name: 'SysUser',
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

    const handleAdd = () => {
        router.push({ path: '/supplier/add'});
    }

    const handleEdit = (id) => {
        router.push({ path: '/supplier/add', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete(`/wms/purchase-supplier/` + id, {
        }).then(() => {
            ElMessage.success('删除成功')
            table.value.getList()
        })

    }

    return {
      search,
      handleAdd,
      handleEdit,
      handleDelete,
      table,
      state
    }
  }
}
</script>

<style>

</style>