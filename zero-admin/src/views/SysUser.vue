<template>
  <el-card class="guest-container">
    <template #header>
      <div class="header">
        <el-input v-model="input" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入用户名"></el-input>
        <el-input v-model="input" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入手机号"></el-input>
        <el-input v-model="input" size="mini" style="width:150px; margin-left:3px;" placeholder="请输入邮箱"></el-input>

        <el-button @click="search" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">搜索</el-button>
      </div>

      <br>
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增管理员</el-button>

    </template>
    <Table
      action='/authentication/admin/list'
      ref="table"
    >
      <template #column>
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>

        <el-table-column prop="username" label="账号"> </el-table-column>
        <el-table-column prop="fullname" label="姓名"> </el-table-column>
        <el-table-column prop="mobile" label="手机号"> </el-table-column>
        <el-table-column prop="email" width="200" label="邮箱"> </el-table-column>
        <el-table-column prop="createdAt" label="创建时间"> </el-table-column>

        <el-table-column label="身份状态">
          <template #default="scope">
            <span :style="scope.row.status == 1 ? 'color: green;' : 'color: red;'">
              {{ scope.row.status == 1 ? '正常' : '禁用' }}
            </span>
          </template>
        </el-table-column>


        <el-table-column label="操作">
          <template #default="scope">
            <span @click="lockSwitch(scope.row.username)" :style="scope.row.status == 1 ? 'color: green;' : 'color: red;'">
                <el-button v-if="scope.row.status == 1" type="danger" size="small" icon="el-icon-star-on">禁用</el-button>
                <el-button v-if="scope.row.status == 0" type="primary" size="small" icon="el-icon-star-on">启用</el-button>
            </span>

            <span style="margin-left:2px;">
            <el-button type="primary" size="small" icon="el-icon-star-on">编辑</el-button>
            </span>
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
import { useRouter } from 'vue-router'

export default {
  name: 'SysUser',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter()

    // 身份状态锁定切换接口调用
    const lockSwitch = (username) => {
        axios.post(`/authentication/admin/lock/switch/` + username, {
        }).then(() => {
            ElMessage.success('切换成功')
            table.value.getList()
        })
    }

    const search = () => {
        alert("搜索一下");
    }

    const handleAdd = () => {
        router.push({ path: '/sysuser/add'});
    }

    return {
      lockSwitch,
      search,
      handleAdd,
      table
    }
  }
}
</script>

<style>

</style>