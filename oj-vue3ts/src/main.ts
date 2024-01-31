import { createApp } from "vue";
import ArcoVue from "@arco-design/web-vue";
import ArcoVueIcon from "@arco-design/web-vue/es/icon";
import App from "./App.vue";
import "@arco-design/web-vue/dist/arco.css";
import { createPinia } from "pinia";
import "bytemd/dist/index.css";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import router from "@/router";

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
createApp(App)
  .use(pinia)
  .use(router)
  .use(ArcoVue)
  .use(ArcoVueIcon)
  .mount("#app");
