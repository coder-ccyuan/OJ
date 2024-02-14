<template>
  <Viewer :plugins="plugins" :value="props.value" aria-readonly="true" />
</template>

<script setup>
import gfm from "@bytemd/plugin-gfm";
import highlight from "@bytemd/plugin-highlight";
import { Viewer } from "@bytemd/vue-next";
import { defineProps } from "vue";

const plugins = [
  gfm(),
  highlight(),
  // Add more plugins here
];
let props = defineProps(["value"]);
</script>
