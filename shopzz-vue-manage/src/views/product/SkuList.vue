<template>
  <el-card class="purchase-container">
    <template #header>
        
        <el-button @click="handleAdd" style="margin-left:3px;" type="danger" size="mini" icon="el-icon-search">新增SKU</el-button>
    </template>


    <Table
      action='/product/sku/list'
      ref="table"
      :canshu="params"
    >
      <template #column>
        <el-table-column type="selection" width="50"></el-table-column>

        <el-table-column width="50" prop="id" label="ID"> </el-table-column>

        <el-table-column prop="spuId" label="SPUID"></el-table-column>
        <el-table-column prop="skuName" label="sku名称"> </el-table-column>

        <el-table-column label="SKU缩略图" width="150px">
        <template #default="scope">
          <img style="width: 80px; height: 80px;" :key="scope.row.skuImg" :src="$filters.prefix(scope.row.skuImg)" alt="SKU缩略图">
        </template>
        </el-table-column>

        <el-table-column prop="salePrice" label="销售价格"> </el-table-column>
        <el-table-column prop="promotionPrice" label="促销价格"> </el-table-column>
        <el-table-column prop="skuCode" label="SKU编码"> </el-table-column>
        
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
    const params = {
      spuId: id
    };

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
      table,
      handleToSku,
      state,
      params
    }
  }
}
</script>

<style>

</style>