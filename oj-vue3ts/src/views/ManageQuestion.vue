<template>
  <a-table :columns="columns" :data="data" :pagination="false">
    <template #optional="{ record }">
      <a-button type="primary" @click="onUpdate(record)">更新</a-button>
      <span>|</span>
      <a-button status="danger" type="primary" @click="onDelete(record)"
        >删除
      </a-button>
    </template>
    <template #tag="{ record }">
      <a-space>
        <a-tag v-for="(tag, index) in record.tags" :key="index" color="green"
          >{{ tag }}
        </a-tag>
      </a-space>
    </template>
  </a-table>
  <a-modal
    v-model:visible="visible"
    fullscreen
    @cancel="handleCancel"
    @ok="handleOk"
  >
    <template #title> 修改题目</template>
    <div>
      <UpdateQuestion :record="recoredKey"></UpdateQuestion>
    </div>
  </a-modal>
  <a-pagination
    v-model:current="pageParameters.current"
    v-model:total="pageParameters.total"
    :page-size="pageParameters.pageSize"
    :show-total="true"
    @change="onPageChange()"
  />
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { QuestionControllerService } from "../../generated";
import UpdateQuestion from "@/components/UpdateQuestion.vue";
import { Message } from "@arco-design/web-vue";

const columns = [
  {
    title: "标题",
    dataIndex: "title",
  },
  {
    title: "标签",
    slotName: "tag",
  },
  {
    title: "通过数",
    dataIndex: "acceptedNum",
  },
  {
    title: "提交数",
    dataIndex: "submitNum",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
  },
  {
    title: "操作",
    slotName: "optional",
  },
];

const data = ref([]);
const visible = ref(false);
const pageParameters = reactive({
  showTotal: true,
  total: 0,
  pageSize: 10,
  current: 0,
});
const SearchParameters = reactive({
  current: pageParameters.current,
  pageSize: pageParameters.pageSize,
});
const loadDate = async () => {
  const res = await QuestionControllerService.listQuestionVoByPageUsingPost(
    SearchParameters
  );
  data.value = res.data.records;
  pageParameters.total = res.data.total;
  pageParameters.pageSize = res.data.pageSize;
  pageParameters.current = Number.parseInt(res.data.current);
};
onMounted(() => {
  loadDate();
});
const recoredKey = ref({
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
  tags: [],
  title: "",
});
const onUpdate = (record) => {
  recoredKey.value = record;
  visible.value = true;
};
const handleOk = () => {
  visible.value = false;
};
const handleCancel = () => {
  visible.value = false;
};
const onDelete = async (record) => {
  const res = await QuestionControllerService.deleteQuestionUsingPost({
    id: record.id,
  });
  if (res.code === 0) {
    data.value = data.value.filter((item) => {
      return item.id !== record.id;
    });
    pageParameters.total--;
    Message.success("删除成功");
  } else {
    alert("删除失败" + res.message);
  }
};
const onPageChange = () => {
  SearchParameters.current = pageParameters.current;
  loadDate();
};
</script>
