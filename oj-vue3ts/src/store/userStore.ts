import { defineStore } from "pinia";
import { LoginUserVO, UserControllerService } from "../../generated";

const userStore = defineStore("user", {
  state: () => {
    return {
      user: {
        id: -1,
        userName: "登录",
        userRole: "user",
        userAvatar: "http://1.12.46.177/images/images.jpg",
      } as LoginUserVO,
      isLogin: false,
    };
  },
  actions: {
    async getLoginUser() {
      const res = await UserControllerService.getLoginUserUsingGet();
      if (res.code === 0) {
        this.user = { ...res.data };
        console.log(this.user);
        this.isLogin = true;
      } else {
        this.isLogin = false;
        this.user = {
          id: -1,
          userName: "登录",
          userRole: "user",
          userAvatar: "http://1.12.46.177/images/images.jpg",
        };
      }
    },
  },
  getters: {},
  persist: {
    key: "userStore", //存储名称
    storage: sessionStorage, // 存储方式
    paths: ["user", "isLogin"], //指定 state 中哪些数据需要被持久化。[] 表示不持久化任何状态，undefined 或 null 表示持久化整个 state
  },
});
export default userStore;
