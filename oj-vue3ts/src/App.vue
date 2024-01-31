<template>
  <div id="app">
    <basic-layout />
  </div>
</template>

<style>
#app {
}
</style>
<script setup lang="ts">
import BasicLayout from "@/layouts/BasicLayout.vue";
import { onMounted } from "vue";
import userStore from "@/store/userStore";

/**ort { onMounted } from "vue
 * 全局初始化入口
 */
const doInit = () => {
  console.log("欢迎来到OJ");
  userStore().getLoginUser();
};

onMounted(() => {
  doInit();
});
</script>
