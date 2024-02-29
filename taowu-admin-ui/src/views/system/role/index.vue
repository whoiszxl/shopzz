<template>
  <div class="container">
    <Breadcrumb :items="['menu.list', 'menu.system.role.list']" />
    <a-card class="general-card" :title="$t('menu.system.role.list')">

      <!-- 头部区域 -->
      <div class="header">
        <!-- 搜索栏 -->
        <div v-if="showQuery" class="header-query">
          <a-form ref="queryRef" :model="queryParams" layout="inline">
            <a-form-item field="username" hide-label>
              <a-input
                  v-model="queryParams.name"
                  placeholder="输入角色搜索"
                  allow-clear
                  style="width: 150px"
                  @press-enter="handleQuery"
              />
            </a-form-item>
            
            <a-form-item hide-label>
              <a-space>
                <a-button type="primary" @click="handleQuery">
                  <template #icon><icon-search /></template>查询
                </a-button>
                <a-button @click="resetQuery">
                  <template #icon><icon-refresh /></template>重置
                </a-button>
              </a-space>
            </a-form-item>
          </a-form>
        </div>
        <!-- 操作栏 -->
        <div class="header-operation">
          <a-row>
            <a-col :span="16">
              <a-space>
                <a-button v-permission="['system:role:add']" type="primary" @click="toAdd">
                  <template #icon><icon-plus/></template>新增
                </a-button>

                <a-button
                    v-permission="['system:role:update']"
                    type="primary"
                    status="success"
                    :disabled="single"
                    :title="single ? '请选择一条要修改的数据' : ''"
                    @click="toUpdate(checkedIds[0])"
                >
                  <template #icon><icon-edit /></template>修改
                </a-button>
                <a-button
                    v-permission="['system:role:delete']"
                    type="primary"
                    status="danger"
                    :disabled="multiple"
                    :title="multiple ? '请选择要删除的数据' : ''"
                    @click="handleBatchDelete"
                >
                  <template #icon><icon-delete /></template>删除
                </a-button>
                <a-button
                    v-permission="['system:role:export']"
                    :loading="exportLoading"
                    type="primary"
                    status="warning"
                    @click="handleExport"
                >
                  <template #icon><icon-download /></template>导出
                </a-button>
              </a-space>
            </a-col>
            <a-col :span="12">
              <right-toolbar v-model:show-query="showQuery" @refresh="fetchData"/>
            </a-col>
          </a-row>
        </div>
      </div>


      <a-table
          row-key="id"
          :data="roleList"
          :loading="loading"
          :pagination="{
              showTotal: true,
              showPageSize: true,
              total: total,
              current: queryParams.page,
            }"
          :bordered="false"
          :size="size"
          @page-change="handlePageChange"
          @page-size-change="handlePageSizeChange"
          @selection-change="handleSelectionChange"
          :row-selection="{
              type: 'checkbox',
              showCheckedAll: true,
              onlyCurrent: false,
            }"
      >

        <template #columns>
          <a-table-column title="ID" data-index="id" :width="85" />
          <a-table-column title="角色名" :width="150">
            <template #cell="{ record }">
              <a-link @click="toDetail(record.id)">{{record.name}}</a-link>
            </template>
          </a-table-column>

          <a-table-column title="角色编码" :width="150">
            <template #cell="{ record }">{{record.code}}</template>
          </a-table-column>

          <a-table-column title="角色描述" :width="150">
            <template #cell="{ record }">{{record.description}}</template>
          </a-table-column>

          <a-table-column title="状态" align="center" data-index="status" :width="150">
            <template #cell="{ record }">
              <a-switch
                  v-model="record.status"
                  :checked-value="1"
                  :unchecked-value="0"
                  :disabled="record.disabled || !checkPermission(['system:role:update'])"
                  @change="handleSwitchStatus(record)"
              />
            </template>
          </a-table-column>

          <a-table-column title="创建者" data-index="createdBy" :width="150" />
          <a-table-column title="创建时间" data-index="createdAt" :width="150" />

          <a-table-column title="操作" align="center" fixed="right" :width="100">

            <template #cell="{ record }">
              <a-button v-permission="['system:role:update']" type="text" size="small" title="修改" @click="toUpdate(record.id)">
                <template #icon><icon-edit/></template>
              </a-button>

              <a-popconfirm content="确定删除吗？" type="warning" @ok="handleDelete([record.id])">
                <a-button v-permission="['system:role:delete']" type="text" size="small" title="删除" :disabled="record.disabled">
                  <template #icon><icon-delete/></template>
                </a-button>
              </a-popconfirm>
            </template>

          </a-table-column>
        </template>


      </a-table>
    </a-card>



    <!-- 表单区域 -->
    <a-drawer
        :title="title"
        :visible="visible"
        :width="580"
        :mask-closable="false"
        unmount-on-close
        render-to-body
        @ok="handleSaveOk"
        @cancel="handleCancel"
    >
      <a-form ref="formRef" :model="roleSaveForm" :rules="rules" size="large">
        <fieldset>
          <legend>基础信息</legend>
          <a-form-item label="角色名称" field="name">
            <a-input v-model="roleSaveForm.name" placeholder="请输入角色名称" />
          </a-form-item>
          <a-form-item label="角色编码" field="code">
            <a-input v-model="roleSaveForm.code" placeholder="请输入角色编码" />
          </a-form-item>
          <a-form-item label="描述" field="description">
            <a-textarea
                v-model="roleSaveForm.description"
                :max-length="200"
                placeholder="请输入描述"
                :auto-size="{
                  minRows: 3,
                }"
                show-word-limit
            />
          </a-form-item>
        </fieldset>
        <fieldset>
          <legend>功能权限</legend>
          <a-form-item label="功能权限">

            <a-space style="margin-top: 2px">
              <a-checkbox v-model="menuExpandAll" @change="handleExpandAll('menu')">展开/折叠</a-checkbox>
              <a-checkbox v-model="menuCheckAll" @change="handleCheckAll('menu')">全选/全不选</a-checkbox>
              <a-checkbox v-model="menuCheckStrictly">父子联动</a-checkbox>
            </a-space>

            <template #extra>
              <a-spin v-if="menuLoading" />
              <a-tree
                  v-if="!menuLoading"
                  ref="menuRef"
                  :data="menuOptions"
                  :default-checked-keys="roleSaveForm.menuIds"
                  :check-strictly="!menuCheckStrictly"
                  :default-expand-all="menuExpandAll"
                  checkable
              />
            </template>
          </a-form-item>
        </fieldset>
      </a-form>
    </a-drawer>




    <a-drawer
        title="角色详情"
        :visible="detailVisible"
        :width="700"
        :footer="false"
        unmount-on-close
        render-to-body
        @cancel="handleDetailCancel"
    >
      <a-descriptions :column="2" bordered size="large">
        <a-descriptions-item label="角色名称">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ role.name }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="角色编码">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ role.code }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="角色详情">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ role.description }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="状态">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>
              <a-tag v-if="role.status === 1" color="green">启用</a-tag>
              <a-tag v-else color="red">禁用</a-tag>
            </span>
        </a-descriptions-item>

        <a-descriptions-item label="创建者">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ role.createdBy }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="创建时间">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ role.createdAt }}</span>
        </a-descriptions-item>


        <a-descriptions-item label="更新者">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ role.updatedBy }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="更新时间">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ role.updatedAt }}</span>
        </a-descriptions-item>
      </a-descriptions>

      <a-card :loading="menuLoading" title="功能权限" :bordered="false">
        <a-tree
            :data="menuOptions"
            :checked-keys="role.menuIds"
            :default-expand-all="false"
            check-strictly
            checkable
        />
      </a-card>
    </a-drawer>


  </div>



</template>

<script lang="ts" setup>
import {getCurrentInstance, reactive, ref, toRefs} from 'vue';
import {useI18n} from 'vue-i18n';
import useLoading from '@/hooks/loading';
import {
  addRole,
  getRole,
  updateRole,
  deleteRole,
  switchRoleStatus,
  RoleParam,
  RoleResponse,
  pageRole, RoleSaveCommand
} from "@/api/system/role";
import checkPermission from '@/utils/check-permission';
import {listRoleDict} from "@/api/system/role";
import {DictResponseState} from "@/store/modules/dict/types";
import {TreeNodeData} from "@arco-design/web-vue";
import {treeMenu} from "@/api/system/menu";

const showQuery = ref(true);

type SizeProps = 'mini' | 'small' | 'medium' | 'large';
const { proxy } = getCurrentInstance() as any;
const { loading, setLoading } = useLoading(true);
const { t } = useI18n();

const size = ref<SizeProps>('medium');
const checkedIds = ref<Array<number>>([]);
const title = ref('');
const single = ref(true);
const multiple = ref(true);

const data = reactive({
  queryParams: {
    name: undefined,
    status: 1,
    page: 1,
    size: 10,
    sort: ['createdAt,desc'],
  },
  roleSaveForm: {} as RoleSaveCommand,
  rules: {
    name: [{ required: true, message: '请输入角色名称' }],
    code: [{ required: true, message: '请输入角色编码' }],
    menuIds: [{ required: true, message: '请选择所属菜单' }],
  },

});
const { queryParams,roleSaveForm,rules } = toRefs(data);

const roleList = ref<RoleResponse[]>([]);
const total = ref(0);

const role = ref<RoleResponse>({
  id: undefined,
  name: '',
  code: '',
  description: '',
  menuIds: undefined,
  status: 1,
  createdBy: '',
  updatedBy: '',
  createdAt: '',
  updatedAt: '',
});

const fetchData = (params: RoleParam = {...queryParams.value}) => {
  setLoading(true);
  pageRole(params).then(res => {
    roleList.value = res.data.list;
    total.value = Number(res.data.total);
  }).finally(() => {
    setLoading(false);
  });

};

fetchData();
const reset = () => {
  menuExpandAll.value = false;
  menuCheckAll.value = false;
  menuCheckStrictly.value = true;

  proxy.$refs.menuRef?.expandAll(menuExpandAll.value);
  roleSaveForm.value = {
    id: undefined,
    name: '',
    code: '',
    description: '',
    menuIds: [] as Array<number>
  };
  proxy.$refs.formRef?.reserFields();
};

const visible = ref(false);
const toAdd = () => {
  reset();
  getRoleOptions();
  getMenuTree();
  title.value = '新增角色';
  visible.value = true;
};

const roleLoading = ref(false);
const roleOptions = ref<DictResponseState[]>([]);

const getRoleOptions = () => {
  roleLoading.value = true;
  listRoleDict({})
      .then((res) => {
        roleOptions.value = res.data;
      })
      .finally(() => {
        roleLoading.value = false;
      });
};

const toUpdate = (id: number) => {
  reset();
  menuCheckStrictly.value = false;
  getMenuTree();
  getRole(id).then((res) => {
    roleSaveForm.value = res.data as unknown as RoleSaveCommand;
    title.value = '修改角色';
    visible.value = true;
  });
};

const handleSwitchStatus = (record: RoleResponse) => {
  const tip = record.status === 1 ? '启用' : '禁用';
  const id = record.id || -1;
  switchRoleStatus(id)
      .then(() => {
        proxy.$message.success(`${tip}成功`);
      })
      .catch(() => {
        record.status = record.status === 1 ? 0 : 1;
      });
};

const menuOptions = ref<TreeNodeData[]>([]);
const menuLoading = ref(false);
const getMenuTree = () => {
  if (menuOptions.value.length <= 0) {
    menuLoading.value = true;
  }
  treeMenu({})
      .then((res) => {
        menuOptions.value = res.data;
      })
      .finally(() => {
        menuLoading.value = false;
      });
};

const handleQuery = () => {
  fetchData();
};

const resetQuery = () => {
  proxy.$refs.queryRef.resetFields();
  handleQuery();
};

const handlePageChange = (current: number) => {
  queryParams.value.page = current;
  fetchData();
};

const handlePageSizeChange = (pageSize: number) => {
  queryParams.value.size = pageSize;
  fetchData();
};

const handleSelectionChange = (rowKeys: Array<any>) => {
  checkedIds.value = rowKeys;
  single.value = rowKeys.length !== 1;
  multiple.value = !rowKeys.length;
};




const handleCancel = () => {
  visible.value = false;
  adminRoleVisible.value =false;
  proxy.$refs.formRef?.resetFields();
  proxy.$refs.userRoleFormRef?.resetFields();
};

const handleSaveOk = () => {
  proxy.$refs.formRef.validate((valid: any) => {
    if (!valid) {
      if (roleSaveForm.value.id !== undefined) {
        roleSaveForm.value.menuIds = getMenuAllCheckedKeys();
        updateRole(roleSaveForm.value).then((res:any) => {
          handleCancel();
          fetchData();
          proxy.$message.success(res.message);
        });
      } else {
        roleSaveForm.value.menuIds = getMenuAllCheckedKeys();
        addRole(roleSaveForm.value).then((res:any) => {
          handleCancel();
          fetchData();
          proxy.$message.success(res.message);
        });
      }
    }
  });
};

const getMenuAllCheckedKeys = () => {
  const checkedNodes = proxy.$refs.menuRef.getCheckedNodes();
  console.log(checkedNodes);
  const checkedKeys = checkedNodes.map((item: TreeNodeData) => item.key);

  const halfCheckedNodes = proxy.$refs.menuRef.getHalfCheckedNodes();
  const halfCheckedKeys = halfCheckedNodes.map(
      (item: TreeNodeData) => item.key
  );

  // eslint-disable-next-line prefer-spread
  checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
  return checkedKeys;
};

const handleDelete = (ids: Array<number>) => {
  deleteRole(ids).then((res:any) => {
    proxy.$message.success(res.message);
    fetchData();
  });
};


const adminRoleVisible = ref(false);
const toUpdateRole = (id: number) => {
  reset();
  getRoleOptions();
  menuCheckStrictly.value = false;
  getRole(id).then((res) => {
    roleSaveForm.value = res.data as unknown as RoleSaveCommand;
    adminRoleVisible.value = true;
  });
};


const detailLoading = ref(false);
const detailVisible = ref(false);
const toDetail = async (id: number) => {
  if (detailLoading.value) return;
  detailLoading.value = true;
  detailVisible.value = true;
  getMenuTree();
  getRole(id)
      .then((res) => {
        role.value = res.data;
      })
      .finally(() => {
        detailLoading.value = false;
      });
};

const handleDetailCancel = () => {
  detailVisible.value = false;
};

const handleBatchDelete = () => {
  if(checkedIds.value.length === 0) {
    proxy.$message.info('没有选中数据');
  }else {
    proxy.$modal.warning({
      title: '警告',
      titleAlign: "start",
      content: '确定删除吗？',
      hideCancel: false,
      onOk: () => {
        handleDelete(checkedIds.value);
      }
    });
  }
}

const exportLoading = ref(false);
const handleExport = () => {
  if(exportLoading.value) return;

  exportLoading.value = true;
  proxy
      .download('/admin/system/role/export', { ...queryParams.value }, '角色数据')
      .finally(() => {
        exportLoading.value = false;
      });

}


const menuExpandAll = ref(false);
const menuCheckAll = ref(false);
const menuCheckStrictly = ref(true);
const handleExpandAll = (type: string) => {
  proxy.$refs.menuRef.expandAll(menuExpandAll.value);
};
const handleCheckAll = (type: string) => {
    proxy.$refs.menuRef.checkAll(menuCheckAll.value);
};


</script>

<script lang="ts">
export default {
  name: 'SearchTable',
};
</script>

<style scoped lang="less">
.container {
  padding: 0 20px 20px 20px;
}

.header {
  padding-bottom: 10px;
}
</style>
