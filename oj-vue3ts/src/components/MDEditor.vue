<template>
  <Editor :value="props.value" :plugins="plugins" @change="handleChange" />
</template>

<script setup>
import gfm from "@bytemd/plugin-gfm";
import highlight from "@bytemd/plugin-highlight";
import { Editor } from "@bytemd/vue-next";
import { ref, defineProps, watchEffect } from "vue";

const plugins = [
  gfm(),
  highlight(),
  // Add more plugins here
];
let props = defineProps(["value", "onChange"]);
const value = ref();
watchEffect(() => {
  value.value = props.value;
});
const handleChange = (v) => {
  value.value = v;
  props.onChange(v);
};
</script>
