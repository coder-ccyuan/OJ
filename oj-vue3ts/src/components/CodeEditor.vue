<template>
  <codemirror
    v-model="code"
    :autofocus="true"
    :extensions="extensions"
    :indent-with-tab="true"
    :style="{ height: '500px' }"
    :tabSize="2"
    placeholder="输入代码"
    @change="codeChange"
  />
</template>

<script setup>
import { Codemirror } from "vue-codemirror";
import { java } from "@codemirror/lang-java";
// import { oneDark } from "@codemirror/theme-one-dark";
import { defineProps, ref } from "vue";

const props = defineProps(["code", "changeCode"]);
const code = ref(
  "class Main{\n" +
    "        public static void main(String[] strs){\n" +
    '              System.out.println("hello world")\n' +
    "        }\n" +
    "}"
);
const extensions = [java()];
const codeChange = () => {
  props.changeCode(code);
};
</script>
