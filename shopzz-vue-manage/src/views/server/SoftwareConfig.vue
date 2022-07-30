<template>
  <el-card class="software-config-container">
    <template #header>
      <div class="header">
        <el-button @click="back" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-arrow-left">返回上一页</el-button>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleAdd">新增组件配置</el-button>
      </div>
    </template>
    <el-table
        :load="loading"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
    >
      <el-table-column prop="softwareName" label="组件名称"/>
      <el-table-column prop="configName" label="配置名称"/>
      <el-table-column prop="configPath" label="配置文件路径"/>

      <el-table-column
          label="操作"
          width="300"
      >
        <template #default="scope">
          <el-button @click="handleDetail(scope.row.id, scope.row.softwareId)" type="primary" size="small" icon="el-icon-star-on">详情</el-button>
          <el-button @click="handleEdit(scope.row.id, scope.row.softwareId)" type="primary" size="small" icon="el-icon-star-on">编辑</el-button>
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
    const query = route.query
    console.log(query.softwareName)
    const state = reactive({
      loading: false,
      tableData: [], // 数据列表
      total: 0, // 总条数
      page: 1, // 当前页
      size: 10 // 分页大小
    })
    onMounted(() => {
      getSoftwareConfigList()
    })
    // 获取组件配置列表
    const getSoftwareConfigList = () => {
      state.loading = true
      axios.post('/admin/software-config/list', {
        params: {
          page: state.page,
          size: state.size,
          softwareName: query.softwareName
        }
      }).then(res => {
        state.tableData = res.data.records
        state.total = res.data.data.total
        state.currentPage = res.data.data.page
        state.loading = false
        app.ctx.goTop() // 回到顶部
      })
    }
    const handleAdd = () => {
      router.push({ path: '/softwareConfig/add' })
    }

    const handleDetail = (id, mySoftwareId) => {
      router.push({ path: '/softwareConfig/detail', query: { 'id':id, 'softwareId': mySoftwareId } })
    }


    const handleDelete = (id) => {

      axios.delete(`/admin/software-config/` + id, {
      }).then(() => {
        ElMessage.success('删除成功')
        getSoftwareConfigList()
      })
    }

    const back = () => {
      router.go(-1);
    }

    const handleEdit = (id, mySoftwareId) => {
      router.push({ path: '/softwareConfig/add', query: { 'id':id, 'softwareId': mySoftwareId } })
    }
    const changePage = (val) => {
      state.currentPage = val
      state.page = val
      getSoftwareConfigList()
    }

    return {
      ...toRefs(state),
      handleAdd,
      handleEdit,
      handleDetail,
      changePage,
      handleDelete,
      back
    }
  }
}
</script>

<style scoped>
.software-config-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}
</style>
