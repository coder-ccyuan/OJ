<script setup lang="ts">
import { ref } from "vue";
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import userStore from "../store/userStore";
import { UserControllerService } from "../../generated";

const router = useRouter();
const selectedKeys = ref(["/"]);
const visibleRoutes = routes.filter((item, index) => {
  if (item.meta?.isHide) {
    return false;
  }
  if (item.meta?.isAdmin && userStore().user.userRole !== "admin") {
    return false;
  } else {
    return true;
  }
});
const menuItemClick = (key: string) => {
  router.push({
    path: key,
  });
};
const onHandleSelect = async (v: any) => {
  console.log(v);
  if (userStore().isLogin) {
    const res = await UserControllerService.userLogoutUsingPost();
    if (res.code === 0) {
      router.go(0);
    }
  } else {
    await router.push("/login");
    router.go(0);
  }
};
//router 是全局性的
//route是局部的，路由发生改变时，只在对应路由的<router-view>的组件里生效，其父组件的route不发生改变

/* 浏览器同步到页面 方法一
router.beforeEach((to, from, next) => {
  selectedKeys.value = [to.path];
  //一定要放行
  next();
});*/
/*
浏览器同步到页面 方法二
 */
router.afterEach((to, from, next) => {
  selectedKeys.value = [to.path];
});
</script>

<template>
  <!--  align 样式居中 wrap 关闭换行-->
  <a-row
    id="globalHeader"
    style="margin-bottom: 16px"
    align="center"
    :wrap="false"
  >
    <a-col flex="auto">
      <div>
        <a-menu
          mode="horizontal"
          v-model:selected-keys="selectedKeys"
          @menu-item-click="menuItemClick"
        >
          <a-menu-item
            :key="0"
            :style="{ padding: 0, marginRight: '30px' }"
            disabled
          >
            <div class="title-bar">
              <img
                class="logo"
                src="http://1.12.46.177/images/images.jpg"
                alt="logo"
              />
              <div class="title">鲲OJ</div>
            </div>
          </a-menu-item>
          <a-menu-item :key="item.path" v-for="item in visibleRoutes">
            {{ item.name }}
          </a-menu-item>
        </a-menu>
      </div>
    </a-col>
    <a-col flex="100px">
      <a-dropdown @select="onHandleSelect">
        <a-avatar>
          <img :src="userStore().user.userAvatar" alt="头像"
        /></a-avatar>
        {{ userStore().user.userName }}
        <template #content>
          <a-doption v-if="userStore().isLogin">登出</a-doption>
          <a-doption v-else>登录</a-doption>
        </template>
      </a-dropdown>
    </a-col>
  </a-row>
</template>

<style scoped>
.title-bar .logo {
  height: 44px;
}

.title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: #444;
  margin-left: 10px;
}
</style>
