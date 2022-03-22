<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="500px">

        <el-card class="add-container">
            <el-form :model="adminForm" :rules="rules" ref="adminRef" label-width="100px" class="adminForm">
            
                <el-form-item label="属性键" prop="name">
                <el-input placeholder="" v-model="state.name"></el-input>
                </el-form-item>

                <el-form-item label="单位" prop="unit">
                <el-input placeholder="" v-model="state.unit"></el-input>
                </el-form-item>

                <el-form-item label="类型" prop="type">
                <el-input placeholder="" v-model="state.type"></el-input>
                </el-form-item>
 
                <el-form-item label="是否标准" prop="standard">
                <el-input placeholder="" v-model="state.standard"></el-input>
                </el-form-item>
            
                <el-form-item>
                <el-button type="primary" @click="submitAdd()">立即修改</el-button>
                </el-form-item>

            </el-form>
        </el-card>


      </el-aside>

      <el-main>
        <Table action="/product/attribute/value/list" :id="state.id" ref="table">
          <template #column>
            <el-table-column type="selection" width="50"></el-table-column>

            <el-table-column width="50" prop="id" label="ID"> </el-table-column>
            <el-table-column prop="value" label="属性值"> </el-table-column>
            <el-table-column width="200" prop="createdAt" label="创建时间">
            </el-table-column>
            <el-table-column width="180" label="操作">
              <template #default="scope">
                <span style="margin-left: 2px">
                  <el-button
                    @click="handleEdit(scope.row.id)"
                    type="primary"
                    size="small"
                    icon="el-icon-star-on"
                    >详情</el-button
                  >
                  <el-button
                    @click="handleDelete(scope.row.id)"
                    type="primary"
                    size="small"
                    icon="el-icon-delete"
                    >删除</el-button
                  >
                </span>
              </template>
            </el-table-column>
          </template>
        </Table>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import Table from "@/components/Table.vue";
import { reactive, ref, toRefs, onMounted, onBeforeUnmount, getCurrentInstance } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { localGet } from '@/utils'

export default {
  name: "AttributeDetail",
  components: {
    Table,
  },
  setup() {
    const table = ref(null);
    const router = useRouter();


    const { proxy } = getCurrentInstance()
    console.log('proxy', proxy)
    const adminRef = ref(null)
    const route = useRoute()
    const { id, name, unit, standard, type } = route.query

    console.log(name);

    const state = reactive({
        token: localGet('token') || '',
        id: id,
        name: name,
        unit: unit,
        type: type,
        standard: standard,
      rules: {
        name: [
          { required: 'true', message: '请填写属性名称', trigger: ['change'] }
        ],
        unit: [
          { required: 'true', message: '请填写属性单位', trigger: ['change'] }
        ]
      },
    });

    onMounted(() => {

      if (id) {
        state.name = name;
        state.unit = unit;
      }
    })

    const search = () => {
      table.value.getList();
    };

    const handleAdd = () => {
      router.push({ path: "/purchase/order/add" });
    };

    const handleEdit = (id) => {
      router.push({ path: "/purchase/order/add", query: { id } });
    };

    const handleDelete = (id) => {
      axios.delete(`/product/attribute/value/` + id, {}).then(() => {
        ElMessage.success("删除成功");
        table.value.getList();
      });
    };

    return {
      search,
      handleAdd,
      handleEdit,
      handleDelete,
      table,
      state,
    };
  },
};
</script>

<style>
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 100px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>