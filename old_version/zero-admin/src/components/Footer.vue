<template>
  <div class="footer">
    <div class="left">zero mall</div>
    <div class="right">
      <a target="_blank" href="https://github.com/whoiszxl/zero-mall">zero-mall 1.0.0 Vue 3.0</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
  .footer {
    height: 50px;
    border-top: 2px solid #e9e9e9;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
  }
</style>