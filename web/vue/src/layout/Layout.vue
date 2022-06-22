<template>
  <el-config-provider :locale="locale">
    <div>
      <!--    Top-->
      <Header :user="user"/>

      <!--    body-->
      <div style ="display: flex">

        <!--      aside-->
        <Aside/>

        <!--      content-->
        <router-view style = "flex: 1"/>
      </div>
    </div>
  </el-config-provider>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
import { ElConfigProvider } from 'element-plus'

import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import request from "@/utils/request";

export default {
  name: "Layout",
  components: {
    Header,
    Aside,
    [ElConfigProvider.name]: ElConfigProvider,
  },
  data() {
    return {
      locale: zhCn,
      user:{}
    }
  },
  created() {
    this.refreshUser()
  },
  methods: {
    refreshUser() {
      debugger
      let userJson = sessionStorage.getItem("user");
      if (!userJson) {
        return
      }
      let userId = JSON.parse(userJson).id
      // 从后台取出更新后的最新用户信息
      request.get("/user/" + userId).then(res => {
        this.user = res.data
      })
    }
  }
}
</script>

<style scoped>

</style>