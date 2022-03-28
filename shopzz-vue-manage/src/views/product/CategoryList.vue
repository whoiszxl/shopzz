<template>
  <el-card class="guest-container">
    <template #header>
      <el-button
        @click="state.dialogVisible = !state.dialogVisible"
        style="margin-left: 3px"
        type="danger"
        size="mini"
        icon="el-icon-search"
        >新增分类</el-button
      >

      <el-dialog v-model="state.dialogVisible" title="新增一级分类">
        <template #footer>
          <el-card class="add-container">
            <el-form
              :model="adminForm"
              :rules="rules"
              ref="adminRef"
              label-width="100px"
              class="adminForm"
            >
              <el-form-item label="分类名称">
                <el-input
                  style="width: 300px"
                  v-model="adminForm.name"
                  placeholder="请输入分类名称"
                ></el-input>
              </el-form-item>

              <el-form-item label="是否显示">
                <el-input
                  style="width: 300px"
                  v-model="adminForm.status"
                  placeholder="[0-不显示,1显示]"
                ></el-input>
              </el-form-item>

              
              <el-form-item label="排序">
                <el-input
                  style="width: 300px"
                  v-model="adminForm.sort"
                  placeholder="请输入是否标准"
                ></el-input>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="submitAdd()">立即创建</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </template>
      </el-dialog>
    </template>
    <Table action="/product/category/list" ref="table" :canshu="params">
      <template #column>
        <el-table-column type="selection" width="55"> </el-table-column>

        <el-table-column prop="name" label="分类名"> </el-table-column>
        <el-table-column prop="level" label="分类等级"> </el-table-column>
        <el-table-column prop="createdAt" label="创建时间"> </el-table-column>

        <el-table-column label="操作">
          <template #default="scope">
            <span style="margin-left: 2px">
              <el-button
                @click="handleNextCategory(scope.row)"
                type="primary"
                size="small"
                icon="el-icon-star-on"
                >下级分类</el-button
              >
            </span>

            <span style="margin-left: 2px">
              <el-button
                @click="handleEdit(scope.row.id)"
                type="primary"
                size="small"
                >修改</el-button
              >
            </span>

            <span style="margin-left: 2px">
              <el-button
                @click="handleDelete(scope.row.id)"
                type="danger"
                size="small"
                >删除</el-button
              >
            </span>
          </template>
        </el-table-column>
      </template>
    </Table>
  </el-card>
</template>

<script>
import { ref, onUnmounted, reactive, toRefs } from "vue";
import Table from "@/components/Table.vue";
import { ElMessage } from "element-plus";
import axios from "@/utils/axios";
import { useRouter, useRoute } from "vue-router";

export default {
  name: "CategoryList",
  components: {
    Table,
  },

  setup() {
    const table = ref(null);
    const router = useRouter();
    const route = useRoute();
    const dialogVisible = ref(false);
    const adminRef = ref(null)


    const state = reactive({
      dialogVisible: dialogVisible,
      adminForm: {
        name: "",
        level: "",
        status: "",
        sort: "",
      },
    });

    const { level = 1, parentId = 0 } = route.query;
    const params = {
      level: level,
      parentId: parentId,
    };

    const unwatch = router.afterEach((to) => {
      console.log("toto" + to.name);
      // 每次路由变化的时候，都会触发监听时间，重新获取列表数据
      if (
        ["CategoryList", "CategoryList2", "CategoryList3"].includes(to.name)
      ) {
        table.value.getList(params);
        router.go(0);
      }
    });

    const submitAdd = (parentId = 0) => {
      let level = 1;
      if(route.name === 'CategoryList') {
        parentId = 0;
      }else if(route.name === 'CategoryList2') {
        level = 2;
        parentId = params.parentId;
      }else if(route.name === 'CategoryList3') {
        level = 3;
        parentId = params.parentId;
      }


      adminRef.value.validate((vaild) => {
        if (vaild) {
          // 默认新增用 post 方法
          let httpOption = axios.post;
          let params = {
            name: state.adminForm.name,
            parentId: parentId,
            level: level,
            status: state.adminForm.status,
            sort: state.adminForm.sort,
          };
          console.log("params", params);

          httpOption("/product/category", params).then(() => {
            ElMessage.success("创建成功");
            state.dialogVisible = false;
            table.value.getList();

            state.adminForm.name = "";
            state.adminForm.level = "";
            state.adminForm.status = "";
            state.adminForm.sort = "";
          });
        }
      });
    };

    onUnmounted(() => {
      unwatch();
    });

    const search = () => {
      alert("搜索一下");
    };

    const handleDelete = (id) => {
      axios.delete(`/product/category/` + id, {}).then(() => {
        ElMessage.success("删除成功");
        table.value.getList();
      });
    };

    const handleNextCategory = (item) => {
      const levelNumber = item.level + 1;
      if (levelNumber == 4) {
        ElMessage.error("最多支持3级");
        return;
      }

      router.push({
        name: `CategoryList${levelNumber}`,
        query: {
          level: levelNumber,
          parentId: item.id,
        },
      });
    };

    return {
      ...toRefs(state),
      search,
      handleNextCategory,
      handleDelete,
      submitAdd,
      table,
      params,
      state,
      adminRef
    };
  },
};
</script>

<style>
</style>