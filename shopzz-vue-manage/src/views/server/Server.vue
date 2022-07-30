<template>
  <el-card class="server-container">
    <template #header>
      <div class="header">
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增服务器</el-button>
        

        <el-button
        @click="state.dialogVisible = !state.dialogVisible"
        style="margin-left: 3px"
        type="danger"
        size="mini"
        icon="el-icon-search"
        >生成服务器</el-button
      >

        <el-dialog v-model="state.dialogVisible" title="生成服务器">
        <template #footer>
          <el-card class="add-container">
            <el-form
              :model="state.adminForm"
              :rules="rules"
              ref="adminRef"
              label-width="100px"
              class="adminForm"
            >
              <el-form-item label="accessKeyId">
                <el-input
                  style="width: 300px"
                  v-model="state.adminForm.accessKeyId"
                  placeholder="请输入accessKeyId"
                ></el-input>
              </el-form-item>

              <el-form-item label="accessSecret">
                <el-input
                  style="width: 300px"
                  v-model="state.adminForm.accessSecret"
                  placeholder="请输入accessSecret"
                ></el-input>
              </el-form-item>

              <el-form-item label="主机名称">
                <el-input
                  style="width: 300px"
                  v-model="state.adminForm.hostName"
                  placeholder="请输入主机名称"
                ></el-input>
              </el-form-item>

              <el-form-item label="实例名称">
                <el-input
                  style="width: 300px"
                  v-model="state.adminForm.instanceName"
                  placeholder="请输入实例名称"
                ></el-input>
              </el-form-item>

              <el-form-item label="机器数量">
                <el-input
                  style="width: 300px"
                  v-model="state.adminForm.quantity"
                  placeholder="请输入机器数量"
                ></el-input>
              </el-form-item>

              <el-form-item label="服务器密码">
                <el-input
                  style="width: 300px"
                  v-model="state.adminForm.password"
                  placeholder="请输入服务器密码"
                ></el-input>
              </el-form-item>

              <el-form-item label="平台，默认阿里云">
                <el-input
                  style="width: 300px"
                  v-model="state.adminForm.platform"
                  placeholder="请输入平台"
                ></el-input>
              </el-form-item>


              <el-form-item>
                <el-button type="primary" @click="serverGenerate()">立即生成</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </template>
      </el-dialog>

      </div>
    </template>

    <Table
        action='/admin/server/list'
        ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"/>
        <el-table-column width="100" prop="serverName" label="服务器名"/>
        <el-table-column width="200" prop="serverOuterIp" label="外网地址"/>
        <el-table-column width="150" prop="serverInnerIp" label="内网地址"/>
        <el-table-column width="150" prop="serverPort" label="连接端口"/>
        <el-table-column width="150" prop="serverUsername" label="用户名"/>
        <el-table-column width="200" prop="createdAt" label="创建时间"/>

        <el-table-column label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
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
import {ref, reactive} from 'vue'
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
    const dialogVisible = ref(false);

    const state = reactive({
      dialogVisible: dialogVisible,
      adminForm: {
        accessKeyId: "",
        accessSecret: "",
        hostName: "",
        instanceName: "",
        password: "",
        platform: "",
        quantity: 0,
      },
    });
    const search = () => {
      table.value.getList();
    }

    const handleAdd = () => {
      router.push({ path: '/server/add'});
    }

    const handleEdit = (id) => {
      router.push({ path: '/server/add', query: { id } })
    }

    const handleDelete = (id) => {

      axios.delete('/admin/server/' + id, {
      }).then(() => {
        ElMessage.success('删除成功')
        table.value.getList()
      })

    }

    
    const serverGenerate = (id) => {

      axios.post('/admin/install/ecsGenerate', state.adminForm).then(() => {
        ElMessage.success('删除成功')
        table.value.getList()
      })

    }

    return {
      search,
      handleAdd,
      handleEdit,
      handleDelete,
      table,
      serverGenerate,
      state
    }
  }
}
</script>

<style scoped>
.server-container {
  min-height: 100%;
}
.el-card.is-always-shadow {
  min-height: 100%!important;
}
</style>