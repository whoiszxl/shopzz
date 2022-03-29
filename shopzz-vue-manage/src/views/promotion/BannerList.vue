<template>
  <el-card class="purchase-container">
    <template #header>
        
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增轮播图</el-button>
    </template>


    <Table
      action='/promotion/banner/list'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>

        <el-table-column prop="name" label="轮播图名称"> </el-table-column>
        
        <el-table-column prop="type" label="轮播图类型">
            <template #default="scope">
            <span style="color: green;" v-if="scope.row.type == 0">PC首页轮播</span>
            <span style="color: green;" v-else-if="scope.row.type == 1">app首页轮播</span>
            <span style="color: green;" v-else-if="scope.row.type == 2">app导航小组件</span>
            </template>
        </el-table-column>

        <el-table-column prop="bizId" label="业务跳转ID"> </el-table-column>

        <el-table-column label="轮播图">
        <template #default="scope">
          <img style="height: 60px;" :key="scope.row.pic" :src="$filters.prefix(scope.row.pic)" alt="轮播图">
        </template>
        </el-table-column>
        
        <el-table-column prop="createdAt" label="创建时间"> </el-table-column>
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
  name: 'SkuList',
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
        router.push({ path: '/banner/add'});
    }

    const handleEdit = (id) => {
        router.push({ path: '/banner/add', query: { id } })
    }

    const handleToSku = (id) => {
        router.push({ path: '/skuList', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete(`/promotion/banner/` + id, {
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
      handleToSku,
      state
    }
  }
}
</script>

<style>

</style>