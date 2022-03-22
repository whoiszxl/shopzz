<template>
  <el-card class="purchase-container">
    <template #header>
        
        <el-button @click="state.dialogVisible=!state.dialogVisible" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增属性键</el-button>
    
        
        <el-dialog
            v-model="state.dialogVisible"
            title="新增属性键"
            width="40%"
            :before-close="handleClose"
        >
            <template #footer>
                    <el-card class="add-container">
            <el-form :model="adminForm" :rules="rules" ref="adminRef" label-width="100px" class="adminForm">
                <el-form-item label="属性键">
                <el-input style="width: 300px" v-model="adminForm.name" placeholder="请输入属性键"></el-input>
                </el-form-item>

                <el-form-item label="单位">
                <el-input style="width: 300px" v-model="adminForm.unit" placeholder="请输入单位"></el-input>
                </el-form-item>

                <el-form-item label="类型">
                <el-input style="width: 300px" v-model="adminForm.type" placeholder="请输入类型"></el-input>
                </el-form-item>

                <el-form-item label="是否标准">
                <el-input style="width: 300px" v-model="adminForm.standard" placeholder="请输入是否标准"></el-input>
                </el-form-item>
                

                <el-form-item>
                <el-button type="primary" @click="submitAdd()">{{ id ? '立即修改' : '立即创建' }}</el-button>
                </el-form-item>

      </el-form>
    </el-card>
            </template>
        </el-dialog>
    </template>


    <Table
      action='/product/attribute/key/list'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>

        <el-table-column prop="name" label="属性键"></el-table-column>
        <el-table-column prop="unit" label="单位"> </el-table-column>
        <el-table-column prop="type" label="类型">
            <template #default="scope">
                <span style="color: red;" v-if="scope.row.type == 0">销售属性</span>
                <span style="color: green;" v-else>基本属性</span>
            </template>
        </el-table-column>

        <el-table-column prop="standard" label="是否标准">
            <template #default="scope">
                <span style="color: red;" v-if="scope.row.standard == 0">非标准</span>
                <span style="color: green;" v-else>标准</span>
            </template>
        </el-table-column>
        
        <el-table-column prop="createdAt" label="创建时间"> </el-table-column>
        <el-table-column width="180" label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
            <el-button @click="handleEdit(scope.row.id, scope.row.name, scope.row.unit, scope.row.standard, scope.row.type)" type="primary" size="small" icon="el-icon-star-on">详情</el-button>
            <el-button @click="handleDelete(scope.row.id)" type="primary" size="small" icon="el-icon-delete">删除</el-button>
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
import { useRouter } from 'vue-router'

export default {
  name: 'AttributeList',
  components: {
    Table
  },
  setup() {
    const table = ref(null)
    const router = useRouter()
    const dialogVisible = ref(false)
    const adminRef = ref(null)


    const state = reactive({
      supplierName: '',
      accountPeriod: '',
      dialogVisible: dialogVisible,

      adminForm: {
        name: '',
        unit: '',
        type: '',
        standard: ''
      },
    })

const submitAdd = () => {
            adminRef.value.validate((vaild) => {
                if (vaild) {
                // 默认新增用 post 方法
                let httpOption = axios.post
                let params = {
                    name: state.adminForm.name,
                    unit: state.adminForm.unit,
                    type: state.adminForm.type,
                    standard: state.adminForm.standard
                }
                console.log('params', params)

                httpOption('/product/attribute/key', params).then(() => {
                    ElMessage.success('创建成功')
                    state.dialogVisible = false;
                    table.value.getList();

                    state.adminForm.name = "";
                    state.adminForm.unit = "";
                    state.adminForm.type = "";
                    state.adminForm.standard = "";
                })
                }
            })
        }

    const search = () => {
        table.value.getList();
    }

    const handleEdit = (id, name, unit, standard, type) => {
        router.push({ path: '/attributeDetail', query: { id, name, unit, standard, type } })
    }

    const handleDelete = (id) => {

      axios.delete(`/product/attribute/key/` + id, {
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
      handleDelete,
      submitAdd,
      table,
      state
    }
  }
}
</script>

<style>

</style>