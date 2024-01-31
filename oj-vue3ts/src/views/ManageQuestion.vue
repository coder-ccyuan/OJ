<template>
  <a-table :columns="columns" :data="data" :pagination="false">
    <template #optional="{ record }">
      <a-button type="primary" @click="onUpdate(record)">更新</a-button>
      <span>|</span>
      <a-button type="primary" status="danger" @click="onDelete(record)"
        >删除
      </a-button>
    </template>
    <template #tag="{ record }">
      <a-space>
        <a-tag :key="index" v-for="(tag, index) in record.tags"
          >{{ tag }}
        </a-tag>
      </a-space>
    </template>
  </a-table>
  <a-modal
    v-model:visible="visible"
    @ok="handleOk"
    @cancel="handleCancel"
    fullscreen
  >
    <template #title> 修改题目</template>
    <div>
      <UpdateQuestion :record="recoredKey"></UpdateQuestion>
    </div>
  </a-modal>
  <a-pagination
    :show-total="true"
    v-model:total="pageParameters.total"
    v-model:current="pageParameters.current"
    :page-size="pageParameters.pageSize"
    @change="onPageChange()"
  />
</template>

<script setup>
import { onMounted, reactive, ref } from "vue";
import { QuestionControllerService } from "../../generated";
import UpdateQuestion from "@/components/UpdateQuestion.vue";

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
    alert("删除成功");
  } else {
    // this.$message.info("删除失败");
    alert("删除失败");
  }
};
const onPageChange = () => {
  SearchParameters.current = pageParameters.current;
  loadDate();
};
</script>
