<template>
  <a-divider />
  <a-table :columns="columns" :data="data" :pagination="false">
    <template #tag="{ record }">
      <a-space>
        <a-tag
          v-for="(tag, index) in record.questionVO.tags"
          :key="index"
          color="green"
          >{{ tag }}
        </a-tag>
      </a-space>
    </template>
  </a-table>
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
import { QuestionSubmitControllerService } from "../../generated";

const columns = [
  {
    title: "题目名称",
    dataIndex: "questionVO.title",
  },
  {
    title: "标签",
    slotName: "tag",
  },
  {
    title: "提交用户",
    dataIndex: "userVO.userName",
  },
  {
    title: "提交时间",
    dataIndex: "createTime",
  },
];

const data = ref([]);
const pageParameters = reactive({
  showTotal: true,
  total: 0,
  pageSize: 10,
  current: 0,
});
const SearchParameters = reactive({
  current: pageParameters.current,
  pageSize: pageParameters.pageSize,
  title: "",
});
const loadDate = async () => {
  const res =
    await QuestionSubmitControllerService.listQuestionSubmitVoByPageUsingGet(
      SearchParameters.current,
      SearchParameters.pageSize
    );
  data.value = res.data.records;
  pageParameters.total = res.data.total;
  pageParameters.current = Number.parseInt(res.data.current);
};
onMounted(() => {
  loadDate();
});
const onPageChange = () => {
  SearchParameters.current = pageParameters.current;
  loadDate();
};
// const onSearch = () => {
//   loadDate();
// };
</script>
<style scoped>
.search {
  align-items: center;
  display: inline-flex;
}
</style>
