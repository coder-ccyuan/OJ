<script lang="ts" setup>
import { defineProps, reactive, watchEffect } from "vue";
import CodeEditor from "@/components/CodeEditor.vue";
import OnlyReadMD from "@/components/OnlyReadMD.vue";
import userStore from "@/store/userStore";
import { QuestionSubmitControllerService } from "../../generated";
import { Message } from "@arco-design/web-vue";

const props = defineProps(["record"]);
const question = reactive({
  id: -1,
  content: "",
  judgeCase: [
    {
      input: "",
      output: "",
    },
  ],
  judgeConfig: {
    memoryLimit: 1000,
    stackLimit: 1000,
    timeLimit: 1000,
  },
  tags: [] as Array<string>,
  title: "",
});
const form = reactive({
  code: "fasdfas",
  language: "java",
  questionId: -1,
  userId: userStore().user.id,
});
watchEffect(() => {
  form.questionId = props.record.id;
  question.title = props.record.title;
  question.content = props.record.content;
  question.judgeCase = props.record.judgeCase;
  question.judgeConfig = props.record.judgeConfig;
  question.tags = props.record.tags;
});

const onSubmit = async () => {
  let res = await QuestionSubmitControllerService.addQuestionSubmitUsingPost(
    form
  );
  if (res.code === 0) {
    Message.success("提交成功");
  } else {
    alert("提交失败" + res.message);
  }
};
const changeCode = (code: string) => {
  form.code = code;
};
</script>

<template>
  <a-row class="grid-demo">
    <a-col :span="12">
      <div>
        <h1>{{ question.title }}</h1>
        <a-space>
          <a-tag
            v-for="(tag, index) of question.tags"
            :key="index"
            color="green"
            >{{ tag }}
          </a-tag>
        </a-space>
        <OnlyReadMD :value="question.content"></OnlyReadMD>
        <h2>示例</h2>
        <a-list>
          <template #header> 示例一</template>
          <a-list-item>输入：{{ question.judgeCase[0].input }}</a-list-item>
          <a-list-item>输出：{{ question.judgeCase[0].output }}</a-list-item>
        </a-list>
        <h2>限制</h2>
        <a-list>
          <a-list-item
            >时间限制：{{ question.judgeConfig.timeLimit }}ms
          </a-list-item>
          <a-list-item
            >内存限制：{{ question.judgeConfig.memoryLimit }}kb
          </a-list-item>
          <a-list-item
            >堆栈限制：{{ question.judgeConfig.stackLimit }}kb
          </a-list-item>
        </a-list>
      </div>
    </a-col>
    <a-col :span="12">
      <div>
        <CodeEditor :changeCode="changeCode" :code="form.code" />
        <a-button style="float: right" type="primary" @click="onSubmit"
          >提交
        </a-button>
      </div>
    </a-col>
  </a-row>
</template>

<style scoped></style>
