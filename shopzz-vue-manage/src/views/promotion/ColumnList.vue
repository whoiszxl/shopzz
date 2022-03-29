<template>
  <el-card class="purchase-container">
    <template #header>
        
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增专栏</el-button>
    </template>


    <Table
      action='/promotion/column/list'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>

        <el-table-column prop="name" label="专栏名称"> </el-table-column>

        <el-table-column prop="descs" label="专栏描述"> </el-table-column>

        <el-table-column label="入口图片">
        <template #default="scope">
          <img style="height: 60px;" :key="scope.row.pic" :src="$filters.prefix(scope.row.enterImg)" alt="轮播图">
        </template>
        </el-table-column>

        <el-table-column label="内部Banner">
        <template #default="scope">
          <img style="height: 60px;" :key="scope.row.pic" :src="$filters.prefix(scope.row.bannerImg)" alt="轮播图">
        </template>
        </el-table-column>

        <el-table-column prop="status" label="状态">
            <template #default="scope">
            <span style="color: red;" v-if="scope.row.status == 0">下线</span>
            <span style="color: green;" v-else-if="scope.row.status == 1">上线</span>
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
  name: 'ColumnList',
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
    const params = {
      spuId: id
    };

    const search = () => {
        table.value.getList();
    }

    
    const handleAdd = () => {
        router.push({ path: '/column/add'});
    }

    const handleEdit = (id) => {
        router.push({ path: '/column/add', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete(`/promotion/column/` + id, {
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
      params
    }
  }
}
</script>

<style>

</style>