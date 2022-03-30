<template>
  <el-card class="purchase-container">
    <template #header>
        <span> 专栏名: {{name}}</span>
    </template>


    <Table
      action='/promotion/column/spu/list'
      ref="table"
      :canshu="{columnId:id}"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>

        <el-table-column prop="spuId" label="spuId"> </el-table-column>
        

        <el-table-column width="180" label="操作">
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
  name: 'ColumnSpuList',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter();
    const route = useRoute();


    const state = reactive({

    })

    const { id, name } = route.query;


    const search = () => {
        table.value.getList();
    }

    
    const handleAdd = () => {
        router.push({ path: '/column/spu/add'});
    }

    const handleEdit = (id) => {
        router.push({ path: '/column/spu/add', query: { id } })
    }

    const handleToSku = (id) => {
        router.push({ path: '/skuList', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete(`/promotion/column/spu/` + id, {
        }).then(() => {
            ElMessage.success('删除成功')
            table.value.getList()
        })

    }

    return {
      ...toRefs(state),
      search,
      handleEdit,
      handleAdd,
      handleDelete,
      table,
      handleToSku,
      state,
      name,
      id
    }
  }
}
</script>

<style>

</style>