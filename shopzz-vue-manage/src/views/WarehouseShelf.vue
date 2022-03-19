<template>

  <el-card class="container">

    <template #header>

      <div class="header">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleSolve">返回上一页</el-button>
        <el-button type="danger" size="small" icon="el-icon-delete" @click="handleForbid">禁用账户</el-button>
      </div>

    </template>

    <Table
        action='/wms/warehouse/shelf/list'
        ref="table"
    >
      <template #column>
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>

        <el-table-column prop="id" label="序号"></el-table-column>
        <el-table-column prop="shelfCode" label="货架编号"></el-table-column>
        <el-table-column prop="shelfComment" label="说明备注"></el-table-column>

        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="handleToSku(scope.row.id)" type="primary" size="small">查看货架上的商品</el-button>
            <el-button @click="handleEdit(scope.row.id)" type="primary" size="small">编辑</el-button>
          </template>

        </el-table-column>

      </template>
    </Table>

  </el-card>

</template>

<script>
import { ref } from 'vue'
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

    const handleEdit = (id) => {
      router.push({ path: '/warehouseShelf/add', query: { id } })
    }

    const handleToSku = (id) => {
      router.push({ path: '/warehouseSku', query: { id } })
    }

    return {
      handleEdit,
      handleToSku,
      table // 一定要 return 给 template
    }
  }
}
</script>