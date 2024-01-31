<script setup lang="ts">
import { useRoute } from "vue-router";

const route = useRoute();
console.log(route.path);
</script>

<template>
  <h1>用户中心</h1>
</template>

<style scoped></style>
