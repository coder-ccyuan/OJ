<script lang="ts" setup>
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>

<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="head">
        <global-header></global-header>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="foot">
        <a-divider></a-divider>
        <icon-github style="font-size: 20px" />
        <a-link href="https://github.com/coder-ccyuan/OJ" icon>
          OJ by cpy
        </a-link>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<style scoped>
#basicLayout .head {
  height: 56px;
  margin-bottom: 16px;
  //background-color: coral;
}

#basicLayout .content {
  height: auto;
  //background: linear-gradient(to right, white, #e8edef);
}

#basicLayout .foot {
  //background: linear-gradient(to right, white, #e8edef); padding: 10px; position: static; bottom: 0; right: 0; left: 0; text-align: center;
}
</style>
