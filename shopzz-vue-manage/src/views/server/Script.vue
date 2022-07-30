<template>
  <el-card class="script-container">
    <template #header>
      <div class="header">
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleAdd">新增脚本配置</el-button>
      </div>
    </template>
    <el-table
        :load="loading"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
    >
      <el-table-column prop="scriptName" label="脚本名称"/>
      <el-table-column prop="scriptDesc" label="脚本描述"/>
      <el-table-column width="100" label="脚本状态">
        <template #default="scope">
          {{ scope.row.status == 1 ? '未同步' : '已同步' }}
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间"/>


      <el-table-column
          label="操作"
          width="300"
      >
        <template #default="scope">
          <el-button @click="handleEdit(scope.row.id)" type="primary" size="small" icon="el-icon-star-on">编辑</el-button>
          <el-popconfirm title="确定删除吗？" confirmButtonText='确定' cancelButtonText='取消' @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="small" type="primary" icon="el-icon-delete">删除</el-button>
            </template>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
    <!--总数超过一页，再展示分页器-->
    <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="size"
        :current-page="page"
        @current-change="changePage"
    />
  </el-card>
</template>

<script>
import { onMounted, reactive, ref, toRefs, getCurrentInstance } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import {useRoute, useRouter} from 'vue-router'
export default {
  name: 'Good',
  setup() {
    const app = getCurrentInstance()
    const route = useRoute()
    const router = useRouter()
    const state = reactive({
      loading: false,
      tableData: [], // 数据列表
      total: 0, // 总条数
      page: 1, // 当前页
      size: 10 // 分页大小
    })
    onMounted(() => {
      getScriptList()
    })
    // 获取脚本配置列表
    const getScriptList = () => {
      state.loading = true
      axios.get('/script', {
        params: {
          page: state.page,
          size: state.size
        }
      }).then(res => {
        state.tableData = res.data.records
        state.total = res.data.data.total
        state.currentPage = res.data.data.page
        state.size = res.data.data.size
        state.page = res.data.data.page
        state.loading = false
        app.ctx.goTop() // 回到顶部
      })
    }
    const handleAdd = () => {
      router.push({ path: '/script/add' })
    }

    const handleDelete = (id) => {

      axios.delete(`/script/` + id, {
      }).then(() => {
        ElMessage.success('删除成功')
        getScriptList()
      })

    }

    const handleDetail = (id) => {
      router.push({ path: '/script/detail', query: { id } })
    }

    const handleEdit = (id) => {
      router.push({ path: '/script/add', query: { id } })
    }
    const changePage = (val) => {
      console.log(val);
      state.currentPage = val
      state.page = val
      getScriptList()
    }

    return {
      ...toRefs(state),
      handleAdd,
      handleEdit,
      handleDetail,
      getGoodList: getScriptList,
      changePage,
      handleDelete
    }
  }
}
</script>

<style scoped>
.script-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}
</style>
