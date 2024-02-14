import { RouteRecordRaw } from "vue-router";
import QuestionSet from "@/views/QuestionSet.vue";
import UserCenter from "@/views/user/UserCenter.vue";
import ManageQuestion from "@/views/ManageQuestion.vue";
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import AddQuestion from "@/views/AddQuestion.vue";
import SubmintRecord from "@/views/SubmintRecord.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    name: "题集",
    path: "/",
    component: QuestionSet,
    meta: {
      isLogin: false,
      isAdmin: false,
    },
  },
  {
    name: "用户中心",
    path: "/user",
    component: UserCenter,
    meta: {
      isLogin: true,
      isAdmin: false,
    },
  },
  {
    name: "登录",
    path: "/login",
    component: LoginView,
    meta: {
      isLogin: false,
      isAdmin: false,
      isHide: true,
    },
  },
  {
    name: "注册",
    path: "/register",
    component: RegisterView,
    meta: {
      isLogin: false,
      isAdmin: false,
      isHide: true,
    },
  },
  {
    name: "添加题目",
    path: "/aq",
    component: AddQuestion,
    meta: {
      isLogin: true,
      isAdmin: true,
    },
  },
  {
    name: "管理题目",
    path: "/mq",
    component: ManageQuestion,
    meta: {
      isLogin: true,
      isAdmin: true,
    },
  },
  {
    name: "提交记录",
    path: "/subRecord",
    component: SubmintRecord,
    meta: {
      isLogin: false,
      isAdmin: false,
    },
  },
];
