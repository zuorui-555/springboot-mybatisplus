<template>
  <el-config-provider :locale="locale">
    <div>
      <!--    Top-->
      <Header/>

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

export default {
  name: "Layout",
  components: {
    Header,
    Aside,
    [ElConfigProvider.name]: ElConfigProvider,
  },
  data() {
    return {
      locale: zhCn
    }
  }
}
</script>

<style scoped>

</style>