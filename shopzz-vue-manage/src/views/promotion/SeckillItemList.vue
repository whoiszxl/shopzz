<template>
  <el-card class="purchase-container">
    <template #header>
        
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增秒杀商品</el-button>
    </template>


    <Table
      action='/promotion/seckill/item/list'
      :canshu="{seckillId:id}"
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>

        <el-table-column prop="skuName" label="秒杀名称"> </el-table-column>
        <el-table-column prop="skuDescs" label="秒杀描述"> </el-table-column>

        <el-table-column prop="initStockQuantity" label="秒杀初始库存"> </el-table-column>
        <el-table-column prop="availableStockQuantity" label="秒杀可用库存"> </el-table-column>

        <el-table-column prop="warmUpStatus" label="状态">
            <template #default="scope">
            <span style="color: red;" v-if="scope.row.warmUpStatus == 0">未预热</span>
            <span style="color: green;" v-else-if="scope.row.warmUpStatus == 1">已预热</span>
            </template>
        </el-table-column>

        <el-table-column prop="skuPrice" label="SKU价格"> </el-table-column>
        <el-table-column prop="seckillPrice" label="秒杀价格"> </el-table-column>

        <el-table-column prop="status" label="状态">
            <template #default="scope">
            <span style="color: red;" v-if="scope.row.status == 0">未启用</span>
            <span style="color: green;" v-else-if="scope.row.status == 1">已启用</span>
            </template>
        </el-table-column>
        
        <el-table-column width="220" label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
            <el-button @click="handleEdit(scope.row.id)" type="primary" size="small" icon="el-icon-star-on">编辑</el-button>
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
import { ref, reactive, toRefs } from 'vue'
import Table from '@/components/Table.vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/axios'
import { useRouter, useRoute } from 'vue-router'

export default {
  name: 'SeckillItemList',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter();
    const route = useRoute();
    const adminRef = ref(null)

    const state = reactive({
    })

    const { id = 0 } = route.query;

    const search = () => {
        table.value.getList();
    }

    
    const handleAdd = () => {
        router.push({ path: '/seckill/item/add'});
    }

    const handleEdit = (id) => {
        router.push({ path: '/seckill/item/add', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete(`/promotion/seckill/item/` + id, {
        }).then(() => {
            ElMessage.success('删除成功')
            table.value.getList()
        })

    }

    return {
      ...toRefs(state),
      adminRef,
      search,
      handleEdit,
      handleAdd,
      handleDelete,
      table,
      state,
      id
    }
  }
}
</script>

<style>

</style>