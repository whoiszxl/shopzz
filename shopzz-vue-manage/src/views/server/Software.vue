<template>
  <el-card class="software-container">
    <template #header>
      <div class="header">
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增组件</el-button>
      </div>
    </template>

    <Table
        action='/admin/software/list'
        ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"/>
        <el-table-column width="100" prop="softwareName" label="组件名"/>
        <el-table-column width="200" prop="softwareFilename" label="组件文件名"/>
        <el-table-column width="150" prop="softwarePath" label="组件文件路径"/>
        <el-table-column width="150" prop="installPath" label="安装地址"/>
        <el-table-column prop="envPath" label="环境变量文件"/>
        <el-table-column width="100" label="是否安装">
          <template #default="scope">
            {{ scope.row.installStatus == 1 ? '未安装' : (scope.row.installStatus == 2 ? '部分安装' : '已全部安装') }}
          </template>
        </el-table-column>


        <el-table-column width="500" label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
            <el-popconfirm title="确定安装吗？" :disabled="scope.row.installStatus === 3" confirmButtonText='确定' cancelButtonText='取消' @confirm="handleInstall(scope.row.softwareName)">
              <template #reference>
                 <el-button
                            :disabled="scope.row.installStatus === 3"
                            type="primary" size="small" icon="el-icon-star-off">一键安装</el-button>
              </template>
            </el-popconfirm>


            <el-button @click="handleConfig(scope.row.softwareName)" type="primary" size="small" icon="el-icon-star-on">配置文件管理</el-button>
            <el-button @click="handleEdit(scope.row.id)" type="primary" size="small" icon="el-icon-star-on">编辑</el-button>

            <el-popconfirm title="确定删除吗？" confirmButtonText='确定' cancelButtonText='取消' @confirm="handleDelete(scope.row.id)">
              <template #reference>
                <el-button size="small" type="primary" icon="el-icon-delete">删除</el-button>
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
import {ref} from 'vue'
import Table from '@/components/Table.vue'
import {ElMessage} from 'element-plus'
import axios from '@/utils/axios'
import {useRouter} from 'vue-router'

export default {
  name: 'Software',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter()

    const search = () => {
      table.value.getList();
    }

    const handleAdd = () => {
      router.push({ path: '/software/add'});
    }

    const handleEdit = (id) => {
      router.push({ path: '/software/add', query: { id } })
    }

    const handleConfig = (softwareName) => {
      router.push({ path: '/software/config', query: { softwareName } })
    }


    const handleInstall = (softwareName) => {

      axios.post(`/admin/install`, {
        'softwareName': softwareName,
        'serverIds': [1,2,3]
      }).then(() => {
        ElMessage.success(softwareName + '安装成功')
        table.value.getList()
      })

    }

    const handleDelete = (id) => {

      axios.delete(`/admin/software/` + id, {
      }).then(() => {
        ElMessage.success('删除成功')
        table.value.getList()
      })

    }

    return {
      search,
      handleAdd,
      handleEdit,
      handleConfig,
      handleDelete,
      table,
      handleInstall

    }
  }
}
</script>

<style scoped>
.software-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}
</style>