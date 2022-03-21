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
        action='/wms/warehouse/sku/list'
        ref="table"
    >
      <template #column>
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>

        <el-table-column prop="skuCode" label="SKU编号"></el-table-column>
        <el-table-column prop="skuName" label="SKU名称"></el-table-column>
        <el-table-column prop="purchasePrice" label="采购价"></el-table-column>
        <el-table-column prop="length" label="长度"></el-table-column>
        <el-table-column prop="width" label="宽度"></el-table-column>
        <el-table-column prop="height" label="高度"></el-table-column>
        <el-table-column prop="grossWeight" label="毛重"></el-table-column>
        <el-table-column prop="shelfId" label="货架ID"></el-table-column>

        <el-table-column width="200" label="操作">
          <template #default="scope">
            <el-button @click="handleToShelf(scope.row.id)" type="primary" size="small">查看SKU详情</el-button>
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
import {useRoute, useRouter} from "vue-router";

  export default {
    name: 'Warehouse',
    components: {
      Table
    },
    setup() {
      const table = ref(null);
      const router = useRouter();
      const route = useRoute()
      const { id } = route.query

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