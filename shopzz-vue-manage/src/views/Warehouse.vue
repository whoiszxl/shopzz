<template>

  <el-card class="container">

    <template #header>

      <div class="header">
        <el-input v-model="state.warehouseName" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入仓库名称"></el-input>
        <el-input v-model="state.warehouseAdminName" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入仓库管理员名称"></el-input>

        <el-button @click="search" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">搜索</el-button>

      </div>

    </template>

    <Table
        action='/wms/warehouse/list'
        ref="table"
    >
      <template #column>
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>

        <el-table-column prop="supplierName" label="供应商"></el-table-column>
        <el-table-column prop="warehouseName" label="仓库名称"></el-table-column>
        <el-table-column prop="warehouseAddress" label="仓库地址"></el-table-column>
        <el-table-column prop="warehouseAdminName" label="仓管名称"></el-table-column>
        <el-table-column prop="warehouseAdminPhone" label="仓管手机"></el-table-column>

        <el-table-column label="仓库类型">
          <template #default="scope">
            {{ scope.row.warehouseType === 1 ? '自营仓' : '其他仓' }}
          </template>
        </el-table-column>


        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="handleToShelf(scope.row.id)" type="primary" size="small">查看货架</el-button>
            <el-button @click="handleEdit(scope.row.id)" type="primary" size="small">编辑</el-button>
          </template>

        </el-table-column>

      </template>
    </Table>

  </el-card>

</template>

<script>
import {reactive, ref} from 'vue'
  import Table from '@/components/Table.vue'
  import { ElMessage } from 'element-plus'
  import axios from '@/utils/axios'
  import {useRouter} from "vue-router";

  export default {
    name: 'Warehouse',
    components: {
      Table
    },
    setup() {
      const table = ref(null);
      const router = useRouter();

      const state = reactive({
        warehouseName: '',
        warehouseAdminName: ''
      })

      const search = () => {
        table.value.getList({"warehouseName": state.warehouseName, "warehouseAdminName": state.warehouseAdminName});
      }

      const handleEdit = (id) => {
        router.push({ path: '/warehouse/add', query: { id } })
      }

      const handleToShelf = (id) => {
        router.push({ path: '/warehouseShelf', query: { id } })
      }

      return {
        search,
        state,
        handleEdit,
        handleToShelf,
        table // 一定要 return 给 template
      }
    }
  }
</script>