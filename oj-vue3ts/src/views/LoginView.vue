<script setup lang="ts">
import { reactive } from "vue";
import { UserControllerService, UserLoginRequest } from "../../generated";
import { useRouter } from "vue-router";

const form = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginRequest);
const router = useRouter();
const onHandleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form);
  if (res.code === 0) {
    await router.push("/");
    router.go(0);
  } else {
    alert("账号或密码错误");
  }
};
</script>

<template>
  <div id="loginView">
    <h1>用户登录</h1>
    <a-form :model="form" :style="{ width: '600px' }" @submit="onHandleSubmit">
      <a-form-item field="userAccount" label="账户">
        <a-input v-model="form.userAccount" placeholder="请输入用户名" />
      </a-form-item>
      <a-form-item field="userPassword" label="密码">
        <a-input
          type="password"
          v-model="form.userPassword"
          placeholder="请输入密码"
        />
      </a-form-item>
      <div style="float: right; margin-left: 90%">
        <router-link to="/register">注册</router-link>
      </div>

      <a-form-item>
        <a-button html-type="submit">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped>
#loginView {
  padding: 30px;
  max-width: 600px;
  margin: 30px auto;
  text-align: center;
}
</style>
