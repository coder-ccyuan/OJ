<script lang="ts" setup>
import MDEditor from "@/components/MDEditor.vue";
import { defineProps, nextTick, reactive, ref, watchEffect } from "vue";
import { QuestionControllerService } from "../../generated";

const props = defineProps(["record"]);
const form = reactive({
  id: -1,
  answer: "",
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
watchEffect(() => {
  form.id = props.record.id;
  form.answer = props.record.answer;
  form.title = props.record.title;
  form.content = props.record.content;
  form.judgeCase = props.record.judgeCase;
  form.judgeConfig = props.record.judgeConfig;
  form.tags = props.record.tags;
});
//tags相关变量
const inputRef = ref(null);
const showInput = ref(false);
const inputVal = ref("");
const colors = [
  "gold",
  "lime",
  "green",
  "cyan",
  "blue",
  "arcoblue",
  "purple",
  "pinkpurple",
  "magenta",
  "gray",
];
/**
 * 判例删除
 * @param index
 */
const handleDelete = (index: number) => {
  form.judgeCase?.splice(index, 1);
};
/**
 * 判例添加
 */
const handleAddCase = () => {
  form.judgeCase?.push({
    input: "",
    output: "",
  });
};
/**
 * 子传父内容
 * @param v
 */
const onChangeContent = (v: string) => {
  form.content = v;
};
/**
 * 子传父答案
 * @param v
 */
const onChangeAnswer = (v: string) => {
  form.answer = v;
};
//tags相关方法
const handleEdit = () => {
  showInput.value = true;

  nextTick(() => {
    if (inputRef.value) {
      inputRef.value.focus();
    }
  });
};

const handleAddTag = () => {
  if (inputVal.value) {
    form.tags?.push(inputVal.value);
    inputVal.value = "";
  }
  showInput.value = false;
};

const handleRemove = (key: string) => {
  form.tags = form.tags?.filter((tag: string) => tag !== key);
};
/**
 * 提交
 */
const onsubmit = async () => {
  let res = await QuestionControllerService.updateQuestionUsingPost(form);
  if (res.code === 0) {
    alert("成功");
  } else {
    alert("添加失败");
  }
};
</script>

<template>
  <div id="createQuestion">
    <h1>更新题目</h1>
    <a-form :style="{ width: '600px' }" model="form">
      <h3>标题</h3>
      <a-form-item>
        <a-input v-model="form.title" placeholder="输入标题" />
      </a-form-item>
      <h3>标签</h3>
      <a-form-item>
        <a-space wrap>
          <a-tag
            v-for="(tag, index) of form.tags"
            :key="tag"
            :closable="index !== -1"
            :color="colors[index]"
            @close="handleRemove(tag)"
          >
            {{ tag }}
          </a-tag>
          <a-input
            v-if="showInput"
            ref="inputRef"
            v-model.trim="inputVal"
            :style="{ width: '90px' }"
            size="mini"
            @blur="handleAddTag"
            @keyup.enter="handleAddTag"
          />
          <a-tag
            v-else
            :style="{
              width: '90px',
              backgroundColor: 'var(--color-fill-2)',
              border: '1px dashed var(--color-fill-3)',
              cursor: 'pointer',
            }"
            @click="handleEdit"
          >
            <template #icon>
              <icon-plus />
            </template>
            Add Tag
          </a-tag>
        </a-space>
      </a-form-item>
      <h3>题目限制</h3>
      时间限制：
      <a-input-number v-model="form.judgeConfig.timeLimit"></a-input-number>
      <br />
      内存限制：
      <a-input-number v-model="form.judgeConfig.memoryLimit"></a-input-number>
      <br />
      堆栈限制：
      <a-input-number v-model="form.judgeConfig.stackLimit"></a-input-number>
      <h3>内容</h3>
      <MDEditor :onChange="onChangeContent" :value="form.content"></MDEditor>
      <h3>答案</h3>
      <MDEditor :onChange="onChangeAnswer" :value="form.answer"></MDEditor>
      <h3>判例</h3>
      <a-form-item
        v-for="(item, index) in form.judgeCase"
        :key="index"
        :field="`judgeCase[${index}]`"
        :label="'判例' + index"
      >
        输入
        <a-input v-model="item.input" placeholder="输入判例" />
        输出
        <a-input v-model="item.output" placeholder="输入判例" />
        <a-button status="danger" @click="handleDelete(index)">删除</a-button>
      </a-form-item>
      <a-button status="normal" type="text" @click="handleAddCase"
        >添加判例
      </a-button>
      <br />
      <a-button type="primary" @click="onsubmit">提交</a-button>
    </a-form>
  </div>
</template>

<style scoped>
#createQuestion {
  margin-left: 30%;
}
</style>
