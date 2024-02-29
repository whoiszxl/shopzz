<template>
  <div class="login-form-wrapper">
    <div class="login-form-title">{{ $t('login.form.title') }}</div>
    <div class="login-form-sub-title">{{ $t('login.form.title') }}</div>
    <div class="login-form-error-msg">{{ errorMessage }}</div>
    <a-form
      ref="loginForm"
      :model="userInfo"
      class="login-form"
      layout="vertical"
      @submit="handleSubmit"
    >
      <!-- 登录用户名组件 -->
      <a-form-item field="username"
        :rules="[{ required: true, message: $t('login.form.userName.errMsg') }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input v-model="userInfo.username" :placeholder="$t('login.form.userName.placeholder')">
          <template #prefix> <icon-user /></template>
        </a-input>
      </a-form-item>

      <!-- 登录密码组件 -->
      <a-form-item field="password"
        :rules="[{ required: true, message: $t('login.form.password.errMsg') }]"
        :validate-trigger="['change', 'blur']"
        hide-label
      >
        <a-input-password v-model="userInfo.password" :placeholder="$t('login.form.password.placeholder')" allow-clear>
          <template #prefix> <icon-lock /> </template>
        </a-input-password>
      </a-form-item>

      <!-- 登录验证码组件 -->
      <a-form-item class="login-form-captcha" field="captcha" hide-label>
        <a-input
            v-model="userInfo.captcha"
            :placeholder="$t('login.form.captcha.placeholder')"
            allow-clear
            style="width: 63%"
        >
          <template #prefix><icon-check-circle /></template>
        </a-input>
        <img
            :src="captchaImgBase64"
            @click="getCaptcha"
        />
      </a-form-item>

      <a-space :size="16" direction="vertical">
        <div class="login-form-remember-me">
          <a-checkbox
              checked="rememberMe"
              :model-value="loginConfig.rememberPassword"
              @change="setRememberPassword"
          >
            {{ $t('login.form.rememberPassword') }}
          </a-checkbox>
        </div>
        <a-button
            :loading="loading"
            type="primary"
            long
            html-type="submit"
        >
          {{ $t('login.form.login') }}
        </a-button>
      </a-space>



    </a-form>
  </div>
</template>

<script lang="ts" setup>

  import { ValidatedError } from '@arco-design/web-vue';
  import { getCurrentInstance, ref, reactive } from 'vue';
  import { useRouter } from 'vue-router';
  import { useI18n } from 'vue-i18n';
  import { useStorage } from '@vueuse/core';
  import {useLoginStore} from '@/store';
  import useLoading from '@/hooks/loading';
  import {encryptByPublicKey} from "@/utils/password";

  const router = useRouter();
  const { t } = useI18n();
  const errorMessage = ref('');
  const { loading, setLoading } = useLoading();
  const captchaImgBase64 = ref('');
  const loginStore = useLoginStore();
  const { proxy } = getCurrentInstance() as any;

  const loginConfig = useStorage('login-config', {
    rememberPassword: true,
    username: '',
    password: '',
    captcha: '',
    uuid: '',
  });
  const userInfo = reactive({
    username: loginConfig.value.username,
    password: loginConfig.value.password,
    captcha: loginConfig.value.captcha,
    uuid: loginConfig.value.captcha
  });

  const getCaptcha = () => {
    loginStore.getImgCaptcha().then((res) => {
      userInfo.uuid = res.data.uuid;
      captchaImgBase64.value = res.data.captcha;
    });
  };
  getCaptcha();

  const handleSubmit = async ({errors, values}: {
    errors: Record<string, ValidatedError> | undefined;
    values: Record<string, any>;
  }) => {
    if (loading.value) return;
    if (!errors) {
      setLoading(true);
      loginStore.login({
        username: values.username,
        password: encryptByPublicKey(values.password) || '',
        captcha: values.captcha,
        uuid: values.uuid
      }).then(() => {

        const { redirect, ...othersQuery } = router.currentRoute.value.query;
        router.push({
          name: (redirect as string) || 'Workplace',
          query: {
            ...othersQuery,
          },
        });

        const { rememberPassword } = loginConfig.value;
        const { username, password } = values;

        loginConfig.value.username = rememberPassword ? username : '';
        loginConfig.value.password = rememberPassword ? password : '';

        proxy.$message.success(t('login.form.login.success'));

      }).catch(() => {
        getCaptcha();
      }).finally(() => {
        loading.value = false;
      });
    }
  };

  const setRememberPassword = (value: boolean) => {
    loginConfig.value.rememberPassword = value;
  };
</script>

<style lang="less" scoped>
  .login-form {
    &-wrapper {
      width: 320px;
    }

    &-title {
      color: var(--color-text-1);
      font-weight: 500;
      font-size: 24px;
      line-height: 32px;
    }

    &-sub-title {
      color: var(--color-text-3);
      font-size: 16px;
      line-height: 24px;
    }

    &-error-msg {
      height: 32px;
      color: rgb(var(--red-6));
      line-height: 32px;
    }

    &-password-actions {
      display: flex;
      justify-content: space-between;
    }

    &-register-btn {
      color: var(--color-text-3) !important;
    }
  }
</style>
