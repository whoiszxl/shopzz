<template>
  <div class="container">
    <Breadcrumb :items="['menu.list', 'menu.system.admin.list']" />
    <a-card class="general-card" :title="$t('menu.system.admin.list')">

      <!-- 头部区域 -->
      <div class="header">
        <!-- 搜索栏 -->
        <div v-if="showQuery" class="header-query">
          <a-form ref="queryRef" :model="queryParams" layout="inline">
            <a-form-item field="username" hide-label>
              <a-input
                  v-model="queryParams.username"
                  placeholder="输入用户名搜索"
                  allow-clear
                  style="width: 150px"
                  @press-enter="handleQuery"
              />
            </a-form-item>

            <a-form-item field="createdAt" hide-label>
              <range-picker v-model="queryParams.createdAt" />
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
                <a-button v-permission="['system:admin:add']" type="primary" @click="toAdd">
                  <template #icon><icon-plus/></template>新增
                </a-button>

                <a-button
                    v-permission="['system:admin:update']"
                    type="primary"
                    status="success"
                    :disabled="single"
                    :title="single ? '请选择一条要修改的数据' : ''"
                    @click="toUpdate(checkedIds[0])"
                >
                  <template #icon><icon-edit /></template>修改
                </a-button>
                <a-button
                    v-permission="['system:admin:delete']"
                    type="primary"
                    status="danger"
                    :disabled="multiple"
                    :title="multiple ? '请选择要删除的数据' : ''"
                    @click="handleBatchDelete"
                >
                  <template #icon><icon-delete /></template>删除
                </a-button>
                <a-button
                    v-permission="['system:admin:export']"
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
          :data="adminList"
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
          <a-table-column title="用户名" :width="150">
            <template #cell="{ record }">
              <a-link @click="toDetail(record.id)">{{
                  record.username
                }}</a-link>
            </template>
          </a-table-column>

          <a-table-column title="头像" align="center" :width="50">
            <template #cell="{ record }">
              <a-avatar>
                <img :src="record.avatar" alt="头像" />
              </a-avatar>
            </template>
          </a-table-column>

          <a-table-column title="真实姓名" data-index="realName" :width="120" />
          <a-table-column title="性别" :width="100">
            <template #cell="{ record }">
              <span v-if="record.gender === 1">男</span>
              <span v-else-if="record.gender === 2">女</span>
              <span v-else>未知</span>
            </template>
          </a-table-column>

          <a-table-column title="联系方式" :width="180">
            <template #cell="{ record }">
              {{ '邮箱: ' + record.email }}
              <br v-if="record.email && record.mobile">
              {{ '电话: ' + record.mobile }}
            </template>
          </a-table-column>

          <a-table-column title="最后登录时间" :width="130">
            <template #cell="{ record }">
              {{ record.lastLoginTime }}
            </template>
          </a-table-column>

          <a-table-column title="谷歌验证码" align="center" :width="70">
            <template #cell="{ record }">
              <a-switch
                  v-model="record.googleStatus"
                  :checked-value="1"
                  :unchecked-value="0"
                  :disabled="record.disabled || !checkPermission(['system:admin:update-google-status'])"
                  @change="handleGoogleStatus(record)"
              />
            </template>
          </a-table-column>

          <a-table-column title="管理员状态" align="center" :width="70">
            <template #cell="{ record }">
              <a-switch
                  v-model="record.status"
                  :checked-value="1"
                  :unchecked-value="0"
                  :disabled="record.disabled || !checkPermission(['system:admin:update'])"
                  @change="handleSwitchStatus(record)"
              />
            </template>
          </a-table-column>

          <a-table-column
              title="操作"
              align="center"
              fixed="right"
              :width="50"
          >
            <template #cell="{ record }">
              <a-button
                  v-permission="['system:admin:update']"
                  type="text"
                  size="small"
                  title="修改"
                  @click="toUpdate(record.id)"
              >
                <template #icon><icon-edit /></template>
              </a-button>
              <a-popconfirm
                  content="确定删除吗？"
                  type="warning"
                  @ok="handleDelete([record.id])"
              >
                <a-button
                    v-permission="['system:admin:delete']"
                    type="text"
                    size="small"
                    title="删除"
                    :disabled="record.disabled"
                >
                  <template #icon><icon-delete /></template>
                </a-button>
              </a-popconfirm>
              <a-popconfirm
                  content="是否要重置密码？"
                  type="warning"
                  @ok="handleResetPassword(record.id)"
              >
                <a-button
                    v-permission="['system:admin:reset-password']"
                    type="text"
                    size="small"
                    title="重置密码"
                >
                  <template #icon><icon-safe /></template>
                </a-button>
              </a-popconfirm>
              <a-button
                  v-permission="['system:admin:role:update']"
                  type="text"
                  size="small"
                  title="分配角色"
                  @click="toUpdateRole(record.id)"
              >
                <template #icon><icon-user /></template>
              </a-button>
            </template>
          </a-table-column>
        </template>


      </a-table>
    </a-card>


    <!-- 表单区域 -->
    <a-modal
        :title="title"
        :visible="visible"
        :width="580"
        :mask-closable="false"
        unmount-on-close
        render-to-body
        @ok="handleOk"
        @cancel="handleCancel"
    >
      <a-form
          ref="formRef"
          :model="adminAddForm"
          :rules="rules"
          :label-col-style="{ width: '84px' }"
          size="large"
          layout="inline"
      >
        <a-form-item label="用户名" field="username">
          <a-input
              v-model="adminAddForm.username"
              placeholder="请输入用户名"
              style="width: 162px"
          />
        </a-form-item>
        <a-form-item label="密码" field="username">
          <a-input
              v-model="adminAddForm.password"
              placeholder="请输入密码"
              style="width: 162px"
          />
        </a-form-item>
        <a-form-item label="真实姓名" field="nickname">
          <a-input
              v-model="adminAddForm.realName"
              placeholder="请输入真实姓名"
              style="width: 162px"
          />
        </a-form-item>
        <a-form-item label="邮箱" field="email">
          <a-input
              v-model="adminAddForm.email"
              placeholder="请输入邮箱"
              style="width: 162px"
          />
        </a-form-item>
        <a-form-item label="手机号码" field="phone">
          <a-input
              v-model="adminAddForm.mobile"
              placeholder="请输入手机号码"
              style="width: 162px"
          />
        </a-form-item>
        <a-form-item label="性别" field="gender">
          <a-radio-group v-model="adminAddForm.gender">
            <a-radio :value="1">男</a-radio>
            <a-radio :value="2">女</a-radio>
            <a-radio :value="0" disabled>未知</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="所属角色" field="roleIds">
          <a-select
              v-model="adminAddForm.roleIds"
              :options="roleOptions"
              placeholder="请选择所属角色"
              :loading="roleLoading"
              multiple
              allow-clear
              :allow-search="{ retainInputValue: true }"
              style="width: 431px"
          />
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal
        title="分配角色"
        :visible="adminRoleVisible"
        :mask-closable="false"
        unmount-on-close
        render-to-body
        @ok="handleUpdateRole"
        @cancel="handleCancel"
    >
      <a-form ref="userRoleFormRef" :model="adminAddForm" :rules="rules" size="large">
        <a-form-item label="所属角色" field="roleIds">
          <a-select
              v-model="adminAddForm.roleIds"
              :options="roleOptions"
              placeholder="请选择所属角色"
              :loading="roleLoading"
              multiple
              allow-clear
              :allow-search="{ retainInputValue: true }"
              style="width: 416px"
          />
        </a-form-item>
      </a-form>
    </a-modal>

    <a-drawer
        title="用户详情"
        :visible="detailVisible"
        :width="700"
        :footer="false"
        unmount-on-close
        render-to-body
        @cancel="handleDetailCancel"
    >
      <a-descriptions :column="2" bordered size="large">
        <a-descriptions-item label="用户名">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.username }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="昵称">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.realName }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="状态">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>
              <a-tag v-if="admin.status === 1" color="green">启用</a-tag>
              <a-tag v-else color="red">禁用</a-tag>
            </span>
        </a-descriptions-item>

        <a-descriptions-item label="谷歌验证码">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>
              <a-tag v-if="admin.googleStatus === 1" color="green">启用</a-tag>
              <a-tag v-else color="red">禁用</a-tag>
            </span>
        </a-descriptions-item>

        <a-descriptions-item label="性别">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>
              <span v-if="admin.gender === 1">男</span>
              <span v-else>女</span>
            </span>
        </a-descriptions-item>

        <a-descriptions-item label="邮箱">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.email || '无' }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="手机号码">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.mobile || '无' }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="所属角色">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.roles }}</span>
        </a-descriptions-item>
        <a-descriptions-item label="创建者">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.createdBy }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="创建时间">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.createdAt }}</span>
        </a-descriptions-item>


        <a-descriptions-item label="更新者">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.updatedBy }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="更新时间">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.updatedAt }}</span>
        </a-descriptions-item>

        <a-descriptions-item label="最后登录时间">
          <a-skeleton v-if="detailLoading" :animation="true">
            <a-skeleton-line :rows="1" />
          </a-skeleton>
          <span v-else>{{ admin.lastLoginTime }}</span>
        </a-descriptions-item>
      </a-descriptions>
    </a-drawer>

  </div>



</template>

<script lang="ts" setup>
  import {getCurrentInstance, reactive, ref, toRefs} from 'vue';
  import {useI18n} from 'vue-i18n';
  import useLoading from '@/hooks/loading';
  import {
    addAdmin,
    getAdmin,
    updateAdmin,
    deleteAdmin,
    resetPassword,
    AdminSaveCommand,
    AdminParam,
    AdminResponse,
    pageAdmin,
    switchAdminStatus,
    updateAdminRole
  } from "@/api/system/admin";
  import checkPermission from '@/utils/check-permission';
  import {listRoleDict} from "@/api/system/role";
  import {DictResponseState} from "@/store/modules/dict/types";
  import {AxiosResponse} from "axios";
  import {HttpResponse} from "@/api/interceptor";

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
      username: undefined,
      status: 1,
      createdAt: undefined,
      page: 1,
      size: 10,
      sort: ['createdAt,desc'],
    },
    adminAddForm: {} as AdminSaveCommand,
    rules: {
      username: [{ required: true, message: '请输入管理员用户名' }],
      password: [{ required: true, message: '请输入管理员密码' }],
      roleIds: [{ required: true, message: '请选择所属角色' }],
    },

  });
  const { queryParams,adminAddForm,rules } = toRefs(data);

  const adminList = ref<AdminResponse[]>([]);
  const total = ref(0);

  const admin = ref<AdminResponse>({
    id: undefined,
    username: '',
    avatar: '',
    realName: '',
    gender: undefined,
    mobile: '',
    email: '',
    googleStatus: undefined,
    lastLoginTime: undefined,
    ip: undefined,
    status: undefined,
    location: '',
    browser: '',
    permissions: undefined,
    roles: undefined,
    roleIds: undefined,
    createdBy: '',
    updatedBy: '',
    createdAt: '',
    updatedAt: '',
  });

  const fetchData = (params: AdminParam = {...queryParams.value}) => {
    setLoading(true);
    pageAdmin(params).then(res => {
      adminList.value = res.data.list;
      total.value = Number(res.data.total);
    }).finally(() => {
      setLoading(false);
    });

  };

  fetchData();
  const reset = () => {
    adminAddForm.value = {
      id: undefined,
      username: '',
      password: '',
      realName: '',
      gender: undefined,
      mobile: '',
      email: '',
      roleIds: [] as Array<number>,
      roles: [] as Array<string>
    };
    proxy.$refs.formRef?.reserFields();
  };

  const visible = ref(false);
  const toAdd = () => {
    reset();
    getRoleOptions();
    title.value = '新增用户';
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
    getRoleOptions();
    getAdmin(id).then((res) => {
      adminAddForm.value = res.data as unknown as AdminSaveCommand;
      title.value = '修改用户';
      visible.value = true;
      adminAddForm.value.password = '';
    });
  };

  const handleSwitchStatus = (record: AdminResponse) => {
    const tip = record.status === 1 ? '启用' : '禁用';
    const id = record.id || -1;
    switchAdminStatus(id)
        .then(() => {
          proxy.$message.success(`${tip}成功`);
        })
        .catch(() => {
          record.status = record.status === 1 ? 0 : 1;
        });
  };

  const handleQuery = () => {
    console.log("handleQuery", queryParams);
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

  const handleOk = () => {
    proxy.$refs.formRef.validate((valid: any) => {
      if (!valid) {
        if (adminAddForm.value.id !== undefined) {
          updateAdmin(adminAddForm.value).then((res:any) => {
            handleCancel();
            fetchData();
            proxy.$message.success(res.message);
          });
        } else {
          addAdmin(adminAddForm.value).then((res:any) => {
            handleCancel();
            fetchData();
            proxy.$message.success(res.message);
          });
        }
      }
    });
  };

  const handleDelete = (ids: Array<number>) => {
    deleteAdmin(ids).then((res:any) => {
      proxy.$message.success(res.message);
      fetchData();
    });
  };

  const handleResetPassword = (id: number) => {
    resetPassword(id).then((res:any) => {
      proxy.$message.success(res.message);
    });
  };

  const adminRoleVisible = ref(false);
  const toUpdateRole = (id: number) => {
    reset();
    getRoleOptions();
    getAdmin(id).then((res) => {
      adminAddForm.value = res.data as unknown as AdminSaveCommand;
      adminRoleVisible.value = true;
    });
  };

  const handleUpdateRole = () => {
    proxy.$refs.userRoleFormRef.validate((valid: any) => {
      if (!valid && adminAddForm.value.id !== undefined) {
        updateAdminRole({ roleIds: adminAddForm.value.roleIds, id: adminAddForm.value.id }).then(
            (res) => {
              handleCancel();
              fetchData();
              proxy.$message.success(res.data.message);
            }
        );
      }
    });
  };

  const detailLoading = ref(false);
  const detailVisible = ref(false);
  const toDetail = async (id: number) => {
    if (detailLoading.value) return;
    detailLoading.value = true;
    detailVisible.value = true;
    getAdmin(id)
        .then((res) => {
          admin.value = res.data;
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
        .download('/admin/system/admin/export', { ...queryParams.value }, '用户数据')
        .finally(() => {
          exportLoading.value = false;
        });

  }


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
