<template>
  <el-card class="purchase-container">
    <template #header>
        
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增SPU</el-button>
    </template>


    <Table
      action='/product/spu/list'
      ref="table"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>

        <el-table-column prop="name" label="名称"></el-table-column>
        <el-table-column prop="subName" label="副名称"> </el-table-column>
        <el-table-column prop="defaultPrice" label="默认价格"> </el-table-column>

        <el-table-column label="商品图片" width="150px">
        <template #default="scope">
          <img style="width: 80px; height: 80px;" :key="scope.row.defaultPic" :src="$filters.prefix(scope.row.defaultPic)" alt="商品默认图片">
        </template>
        </el-table-column>

        <el-table-column prop="brandName" label="品牌名称"> </el-table-column>
        <el-table-column prop="categoryId" label="分类ID"> </el-table-column>
        
        <el-table-column prop="createdAt" label="创建时间"> </el-table-column>
        <el-table-column width="240" label="操作">
          <template #default="scope">
            <span style="margin-left:2px;">
            <el-button @click="handleToSku(scope.row.id)" type="primary" size="small" icon="el-icon-star-on">查看SKU</el-button>
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
  name: 'SpuList',
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

    
    const handleAdd = () => {
        router.push({ path: '/spu/add'});
    }

    const handleEdit = (id, name, unit, standard, type) => {
        router.push({ path: '/attributeDetail', query: { id, name, unit, standard, type } })
    }

    const handleToSku = (id) => {
        router.push({ path: '/skuList', query: { id } })
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
      handleAdd,
      handleDelete,
      submitAdd,
      table,
      handleToSku,
      state
    }
  }
}
</script>

<style>

</style>