<template>
  <div class="app-container">
    <Breadcrumb :items="['menu.system', 'menu.system.menu.list']" />
    <a-card class="general-card" :title="$t('menu.system.menu.list')">


      <div class="header">

        <!-- 搜索栏 -->
        <div v-if="showQuery" class="header-query">
          <a-form ref="queryRef" :model="queryParams" layout="inline">
            <a-form-item field="name" hide-label>
              <a-input v-model="queryParams.name" placeholder="输入菜单标题搜索" allow-clear @press-enter="handleQuery"
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
            <a-col :span="12">
              <a-space>

                <a-button v-permission="['system:menu:add']" type="primary" @click="toAdd">
                  <template #icon><icon-plus /></template>新增
                </a-button>

                <a-button
                    v-permission="['system:menu:update']"
                    type="primary"
                    status="success"
                    :disabled="single"
                    :title="single ? '请选择一条要修改的数据' : ''"
                    @click="toUpdate(ids[0])"
                >
                  <template #icon><icon-edit /></template>修改
                </a-button>
                <a-button
                    v-permission="['system:menu:delete']"
                    type="primary"
                    status="danger"
                    :disabled="multiple"
                    :title="multiple ? '请选择要删除的数据' : ''"
                    @click="handleBatchDelete"
                >
                  <template #icon><icon-delete /></template>删除
                </a-button>
                <a-button
                    v-permission="['system:menu:export']"
                    :loading="exportLoading"
                    type="primary"
                    status="warning"
                    @click="handleExport"
                >
                  <template #icon><icon-download /></template>导出
                </a-button>
                <a-button type="secondary" @click="handleExpandAll">
                  <template #icon><icon-swap :rotate="90" /></template>展开/折叠
                </a-button>
              </a-space>
            </a-col>
            <a-col :span="12">
              <right-toolbar v-model:show-query="showQuery" @refresh="fetchData"/>
            </a-col>
          </a-row>
        </div>
      </div>

      <!-- 列表区域 -->
      <a-table
          ref="tableRef"
          :data="menuList"
          :row-selection="{
          type: 'checkbox',
          showCheckedAll: true,
          onlyCurrent: false,
        }"
          :pagination="false"
          :default-expand-all-rows="true"
          :hide-expand-button-on-empty="true"
          row-key="id"
          :bordered="false"
          :stripe="true"
          :loading="loading"
          size="large"
          @select="handleSelect"
          @selection-change="handleSelectionChange"
      >
        <template #columns>
          <a-table-column title="菜单名称" >
            <template #cell="{ record }">
              <svg-icon :icon-class="record.icon ? record.icon : ''" />{{record.name}}
            </template>
          </a-table-column>

          <a-table-column title="权限标识" data-index="permission" />

          <a-table-column title="组件路径" data-index="component" />

          <a-table-column title="状态" align="center">
            <template #cell="{ record }">
              <a-switch
                  v-model="record.status"
                  :checked-value="1"
                  :unchecked-value="0"
                  :disabled="!checkPermission(['system:menu:update'])"
                  @change="handleChangeStatus(record)"
              />
            </template>
          </a-table-column>

          <a-table-column title="外链" align="center">
            <template #cell="{ record }">
              <a-tag v-if="record.isFrame" color="green">是</a-tag>
              <a-tag v-else color="red">否</a-tag>
            </template>
          </a-table-column>

          <a-table-column title="缓存" align="center">
            <template #cell="{ record }">
              <a-tag v-if="record.isCache" color="green">是</a-tag>
              <a-tag v-else color="red">否</a-tag>
            </template>
          </a-table-column>

          <a-table-column title="隐藏" align="center">
            <template #cell="{ record }">
              <a-tag v-if="record.visible" color="green">是</a-tag>
              <a-tag v-else color="red">否</a-tag>
            </template>
          </a-table-column>

          <a-table-column title="排序" align="center" data-index="sort" />

          <a-table-column title="创建时间" data-index="createdAt" />
          <a-table-column
              v-if="checkPermission(['system:menu:update', 'system:menu:delete'])"
              title="操作"
              align="center"
          >
            <template #cell="{ record }">
              <a-button
                  v-permission="['system:menu:update']" type="text" size="small"
                  title="修改"
                  @click="toUpdate(record.id)"
              >
                <template #icon><icon-edit /></template>修改
              </a-button>
              <a-popconfirm
                  content="是否要删除当前菜单以及当前菜单之下的记录？"
                  type="warning"
                  @ok="handleDelete([record.id])"
              >
                <a-button v-permission="['system:menu:delete']" type="text" size="small" title="删除">
                  <template #icon><icon-delete /></template>删除
                </a-button>
              </a-popconfirm>
            </template>
          </a-table-column>
        </template>
      </a-table>

      <!-- 表单区域 -->
      <a-modal :title="title" :visible="visible" :width="750"
          :mask-closable="false" unmount-on-close render-to-body
          @ok="handleOk"
          @cancel="handleCancel"
      >
        <a-form ref="formRef" :model="form" :rules="rules" layout="inline"
            :label-col-style="{ width: '85px' }" size="large">

          <a-form-item label="菜单类型" field="type" lab>
            <a-radio-group v-model="form.type" type="button">
              <a-radio :value="1" style="width: 57px">目录</a-radio>
              <a-radio :value="2" style="width: 57px">菜单</a-radio>
              <a-radio :value="3" style="width: 57px">按钮</a-radio>
            </a-radio-group>
          </a-form-item>
          <a-form-item v-if="form.type !== 3" label="菜单图标" field="icon">
            <a-popover v-model:popup-visible="showChooseIcon" position="bottom" trigger="click">

              <a-input v-model="form.icon" placeholder="点击选择菜单图标" readonly allow-clear style="width: 473px">

                <template #prefix>
                  <svg-icon v-if="form.icon" :icon-class="form.icon" style="height: 32px; width: 16px"/>
                  <icon-search v-else />
                </template>

              </a-input>

              <template #content>
                <icon-select ref="iconSelectRef" @selected="selected" />
              </template>

            </a-popover>
          </a-form-item>


          <a-form-item label="菜单名称" field="title">
            <a-input v-model="form.name" placeholder="请输入菜单名称"/>
          </a-form-item>

          <a-form-item label="菜单排序" field="sort">
            <a-input-number v-model="form.sort" placeholder="请输入菜单排序" :min="1" mode="button"/>
          </a-form-item>

          <a-form-item v-if="form.type !== 1" label="权限标识" field="permission">
            <a-input v-model="form.permission" placeholder="请输入权限标识"/>
          </a-form-item>

          <a-form-item v-if="form.type !== 3" label="路由地址" field="path">
            <a-input v-model="form.path" placeholder="请输入路由地址" style="width: 473px"/>
          </a-form-item>

          <a-form-item v-if="!form.isFrame && form.type === 2" label="组件名称" field="name">
            <a-input v-model="form.name" placeholder="请输入组件名称"/>
          </a-form-item>

          <a-form-item v-if="!form.isFrame && form.type === 2" label="组件路径" field="component">
            <a-input v-model="form.component" placeholder="请输入组件路径"/>
          </a-form-item>

          <br />

          <a-form-item v-if="form.type !== 3" label="是否外链" field="isFrame">
            <a-radio-group v-model="form.isFrame" type="button">
              <a-radio :value="1">是</a-radio>
              <a-radio :value="0">否</a-radio>
            </a-radio-group>
          </a-form-item>

          <a-form-item v-if="form.type === 2" label="是否缓存" field="isCache">
            <a-radio-group v-model="form.isCache" type="button">
              <a-radio :value="1">是</a-radio>
              <a-radio :value="0">否</a-radio>
            </a-radio-group>
          </a-form-item>

          <a-form-item v-if="form.type !== 3" label="是否隐藏" field="visible">
            <a-radio-group v-model="form.visible" type="button">
              <a-radio :value="1">是</a-radio>
              <a-radio :value="0">否</a-radio>
            </a-radio-group>
          </a-form-item>

          <a-form-item label="上级菜单" field="parentId">
            <a-tree-select
                v-model="form.parentId"
                :data="treeData"
                placeholder="请选择上级菜单"
                allow-clear
                allow-search
                :filter-tree-node="filterMenuTree"
                :fallback-option="false"
                style="width: 473px"
            />
          </a-form-item>
        </a-form>
      </a-modal>
    </a-card>
  </div>
</template>

<script lang="ts" setup>
import {getCurrentInstance, reactive, ref, toRefs} from 'vue';
import {TableData, TreeNodeData} from '@arco-design/web-vue';
import {
  addMenu,
  deleteMenu,
  getMenu,
  MenuParam,
  MenuResponse,
  MenuSaveCommand,
  treeList,
  treeMenu,
  updateMenu,
} from '@/api/system/menu';
import checkPermission from '@/utils/check-permission';

const { proxy } = getCurrentInstance() as any;

const menuList = ref<MenuResponse[]>([]);
const ids = ref<Array<number>>([]);
const title = ref('');
const single = ref(true);
const multiple = ref(true);
const showQuery = ref(true);
const loading = ref(false);
const exportLoading = ref(false);
const expandAll = ref(false);
const visible = ref(false);
const showChooseIcon = ref(false);
const treeData = ref<TreeNodeData[]>();

const data = reactive({
  // 查询参数
  queryParams: {
    name: undefined,
    status: undefined,
    sort: ['parentId,asc', 'sort,asc', 'createdAt,desc'],
  },
  // 表单数据
  form: {} as MenuSaveCommand,
  // 表单验证规则
  rules: {
    name: [{ required: true, message: '请输入菜单名称' }],
    path: [{ required: true, message: '请输入路由地址' }],
    component: [{ required: true, message: '请输入组件路径' }],
    permission: [{ required: true, message: '请输入权限标识' }],
    sort: [{ required: true, message: '请输入菜单排序' }],
  },
});
const { queryParams, form, rules } = toRefs(data);

const fetchData = (params: MenuParam = { ...queryParams.value }) => {
  loading.value = true;
  treeList(params)
      .then((res) => {
        menuList.value = res.data;
      })
      .finally(() => {
        loading.value = false;
      });
};
fetchData();

const toAdd = () => {
  reset();
  treeMenu({}).then((res) => {
    treeData.value = res.data;
  });
  title.value = '新增菜单';
  visible.value = true;
};

const toUpdate = (id: number) => {
  reset();
  treeMenu({}).then((res) => {
    treeData.value = res.data;
  });

  getMenu(id).then((res) => {
    form.value = res.data as unknown as MenuSaveCommand;
    title.value = '修改菜单';
    visible.value = true;
  });
};

const reset = () => {
  form.value = {
    id: undefined,
    name: '',
    parentId: undefined,
    type: 1,
    path: undefined,
    query: undefined,
    component: undefined,
    icon: undefined,
    isFrame: 1,
    isCache: 1,
    visible: 1,
    permission: undefined,
    sort: 999,
    status: 1
  };
  proxy.$refs.formRef?.resetFields();
};

const handleCancel = () => {
  visible.value = false;
  proxy.$refs.formRef.resetFields();
};

const handleOk = () => {
  proxy.$refs.formRef.validate((valid: any) => {
    if (!valid) {
      if (form.value.id !== undefined) {
        updateMenu(form.value).then((res) => {
          handleCancel();
          fetchData();
          proxy.$message.success(res.data.message);
        });
      } else {
        addMenu(form.value).then((res) => {
          handleCancel();
          fetchData();
          proxy.$message.success(res.data.message);
        });
      }
    }
  });
};

const handleBatchDelete = () => {
  if (ids.value.length === 0) {
    proxy.$message.info('请选择要删除的数据');
  } else {
    proxy.$modal.warning({
      title: '警告',
      titleAlign: 'start',
      content: '是否要删除当前菜单以及当前菜单之下的记录？',
      hideCancel: false,
      onOk: () => {
        handleDelete(ids.value);
      },
    });
  }
};

const handleDelete = (ids: Array<number>) => {
  deleteMenu(ids).then((res) => {
    proxy.$message.success(res.data.message);
    fetchData();
  });
};

const handleSelect = (rowKeys: any, rowKey: any, record: TableData) => {
  if (rowKeys.find((key: any) => key === rowKey)) {
    if (record.children) {
      record.children.forEach((r) => {
        proxy.$refs.tableRef.select(r.id);
        rowKeys.push(r.id);
        if (r.children) {
          handleSelect(rowKeys, rowKey, r);
        }
      });
    }
  } else if (record.children) {
    record.children.forEach((r) => {
      rowKeys.splice(
          rowKeys.findIndex((key: number | undefined) => key === r.id),
          1
      );
      proxy.$refs.tableRef.select(r.id, false);
      if (r.children) {
        handleSelect(rowKeys, rowKey, r);
      }
    });
  }
};

const handleSelectionChange = (rowKeys: Array<any>) => {
  ids.value = rowKeys;
  single.value = rowKeys.length !== 1;
  multiple.value = !rowKeys.length;
};


const handleExport = () => {
  if (exportLoading.value) return;
  exportLoading.value = true;
  proxy
      .download('/system/menu/export', { ...queryParams.value }, '菜单数据')
      .finally(() => {
        exportLoading.value = false;
      });
};

const handleExpandAll = () => {
  expandAll.value = !expandAll.value;
  proxy.$refs.tableRef.expandAll(expandAll.value);
};

const handleChangeStatus = (record: MenuResponse) => {
  const tip = record.status === 1 ? '启用' : '禁用';
  updateMenu(record)
      .then(() => {
        proxy.$message.success(`${tip}成功`);
      })
      .catch(() => {
        record.status = record.status === 1 ? 2 : 1;
      });
};

const filterMenuTree = (searchValue: string, nodeData: TreeNodeData) => {
  if (nodeData.title) {
    return (
        nodeData.title.toLowerCase().indexOf(searchValue.toLowerCase()) > -1
    );
  }
  return false;
};

const selected = (name: string) => {
  form.value.icon = name;
  console.log(form.value.icon);
  showChooseIcon.value = false;
};

const handleQuery = () => {
  fetchData();
};

const resetQuery = () => {
  proxy.$refs.queryRef.resetFields();
  handleQuery();
};
</script>

<script lang="ts">
export default {
  // eslint-disable-next-line vue/no-reserved-component-names
  name: 'Menu',
};
</script>

<style scoped lang="less"></style>
