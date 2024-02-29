<template>
  <a-range-picker :format="format" :show-time="showTime" :shortcuts="shortcuts"></a-range-picker>


</template>

<script lang="ts" setup>
import { computed, PropType } from 'vue';
import { ShortcutType } from '@arco-design/web-vue';
import dayjs from 'dayjs';

defineProps({
  format: {
    type: String,
    default: 'YYYY-MM-DD HH:mm:ss',
  },
  showTime: {
    type: Boolean,
    default: true,
  }
});

const shortcuts = computed<ShortcutType[]>(() => {
  return [
    {
      label: '今天',
      value: (): Date[] => [
        dayjs().startOf('day').toDate(),
        dayjs().toDate(),
      ],
    },
    {
      label: '昨天',
      value: (): Date[] => [
        dayjs().subtract(1, 'day').startOf('day').toDate(),
        dayjs().subtract(1, 'day').endOf('day').toDate(),
      ],
    },
    {
      label: '本周',
      value: (): Date[] => [
        dayjs().startOf('week').add(1, 'day').toDate(),
        dayjs().toDate(),
      ],
    },
    {
      label: '本月',
      value: (): Date[] => [
        dayjs().startOf('month').toDate(),
        dayjs().toDate(),
      ],
    },
    {
      label: '本季度',
      value: (): Date[] => [
        dayjs().add(-3,'month').toDate(),
        dayjs().toDate(),
      ],
    },
    {
      label: '本年',
      value: (): Date[] => [
        dayjs().startOf('year').toDate(),
        dayjs().toDate(),
      ],
    },
  ];
});
</script>

<script lang="ts">
export default {
  name: 'RangePicker',
};
</script>

<style scoped lang="less"></style>