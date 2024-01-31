import { createRouter, createWebHistory } from "vue-router";
import { routes } from "@/router/routes";
import userStore from "@/store/userStore";

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});
/**
 * 权限校验
 */
router.beforeEach(async (to, from, next) => {
  //自动登录
  await userStore().getLoginUser();
  //判断是否需要登录
  if (to.meta.isLogin && !userStore().isLogin) {
    //未登录
    next("/login");
    return;
  }
  //已登录||该页面不需要登录
  //判断是否需要admin权限
  if (to.meta.isAdmin && userStore().user.userRole !== "admin") {
    //没有管理员权限
    alert("没有权限");
    return;
  }
  next();
});
export default router;
