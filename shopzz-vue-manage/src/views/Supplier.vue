<template>
  <el-card class="supplier-container">
    <template #header>
      <div class="header">
        <el-input v-model="state.supplierName" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入供应商名称"></el-input>
        <el-input v-model="state.accountPeriod" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入结算周期"></el-input>

        <el-button @click="search" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">搜索</el-button>
      </div>

      <br>
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增供应商</el-button>

    </template>
    <Table
      action='/wms/purchase/supplier/list'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>
        <el-table-column prop="supplierName" label="供应商名称"> </el-table-column>
        <el-table-column prop="companyName" label="公司名称"> </el-table-column>
        <el-table-column prop="companyAddress" label="公司地址"> </el-table-column>
        <el-table-column prop="contactor" label="联系人"> </el-table-column>
        <el-table-column prop="contactPhoneNumber" label="联系电话"> </el-table-column>

        <el-table-column label="结算周期">
          <template #default="scope">
              {{ scope.row.accountPeriod == 1 ? '周结算' : (scope.row.accountPeriod == 2 ? "月结算" : "季度结算") }}
          </template>
        </el-table-column>


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
        table.value.getList({"supplierName": state.supplierName, "accountPeriod": state.accountPeriod});
    }

    const handleAdd = () => {
        router.push({ path: '/supplier/add'});
    }

    const handleEdit = (id) => {
        router.push({ path: '/supplier/add', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete(`/wms/purchase/supplier/` + id, {
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