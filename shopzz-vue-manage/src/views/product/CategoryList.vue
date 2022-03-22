<template>
  <el-card class="guest-container">
    <template #header>
    </template>
    <Table
      action='/product/category/list'
      ref="table"
      :canshu="params"
    >
      <template #column>
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>

        <el-table-column prop="name" label="分类名"> </el-table-column>
        <el-table-column prop="level" label="分类等级"> </el-table-column>
        <el-table-column prop="createdAt" label="创建时间"> </el-table-column>

        <el-table-column label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
                <el-button @click="handleNextCategory(scope.row)" type="primary" size="small" icon="el-icon-star-on">下级分类</el-button>
            </span>

            <span style="margin-left:2px;">
                <el-button @click="handleEdit(scope.row.id)" type="primary" size="small">修改</el-button>
            </span>

            <span style="margin-left:2px;">
                <el-button @click="handleEdit(scope.row.id)" type="danger" size="small">删除</el-button>
            </span>

          </template>
        </el-table-column>
      </template>
    </Table>
  </el-card>
</template>

<script>
import { ref, onUnmounted, reactive, toRefs } from 'vue'
import Table from '@/components/Table.vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/axios'
import { useRouter, useRoute } from 'vue-router'

export default {
  name: 'CategoryList',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter()
    const route = useRoute()

    const { level = 1, parent_id = 0 } = route.query
    const params = {
        level: level, 
        parentId: parent_id
    }

    console.log("pppp" + JSON.stringify(params));

    // 身份状态锁定切换接口调用
    const lockSwitch = (username) => {
        axios.post(`/admin/admin/lock/switch/` + username, {
        }).then(() => {
            ElMessage.success('切换成功')
            table.value.getList()
        })
    }

    const unwatch = router.afterEach((to) => {
      // 每次路由变化的时候，都会触发监听时间，重新获取列表数据
      if (['CategoryList', 'CategoryList2', 'CategoryList3'].includes(to.name)) {
        table.value.getList()
      }
    })

    onUnmounted(() => {
        unwatch()
    })

    const search = () => {
        alert("搜索一下");
    }

    const handleAdd = () => {
        router.push({ path: '/sysuser/add'});
    }

    const handleEdit = () => {
        router.push({ path: '/sysuser/add'});
    }

    const handleNextCategory = (item) => {
        const levelNumber = item.level + 1
        if (levelNumber == 4) {
            ElMessage.error('最多支持3级')
            return
        }

        router.push({
            name: `CategoryList${levelNumber}`,
            query: {
                level: levelNumber,
                parent_id: item.id
            }
        })
    }


    return {
      lockSwitch,
      search,
      handleAdd,
      handleEdit,
      handleNextCategory,
      table,
      params
    }
  }
}
</script>

<style>

</style>