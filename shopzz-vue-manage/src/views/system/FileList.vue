<template>
  <el-card class="purchase-container">
    <template #header>
      <div class="header">
        <el-input v-model="state.supplierId" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入业务类型"></el-input>
        <el-input v-model="state.purchaseOrderStatus" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入数据类型"></el-input>

        <el-button @click="search" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">搜索</el-button>
      </div>

    </template>
    <Table
      action='/file/file/list'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>
        <el-table-column prop="bizType" label="业务类型"> </el-table-column>
        <el-table-column prop="dataType" label="数据类型"> </el-table-column>
        <el-table-column prop="relativePath" label="文件路径"> </el-table-column>
        <el-table-column prop="finalFileName" label="文件名"> </el-table-column>
        <el-table-column prop="originalFileName" label="原始文件名"> </el-table-column>
        <el-table-column prop="size" label="文件大小"> </el-table-column>
        <el-table-column prop="url" label="访问地址"> </el-table-column>
        
        <el-table-column width="180" label="操作">

          <template #default="scope">
            <span style="margin-left:2px;">
            <el-button @click="handleEdit(scope.row.id)" type="primary" size="small" icon="el-icon-star-on">查看文件</el-button>
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
  name: 'FileList',
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
        router.push({ path: '/purchase/order/add'});
    }

    const handleEdit = (id) => {
        router.push({ path: '/purchase/order/add', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete(`/wms/purchase/order` + id, {
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