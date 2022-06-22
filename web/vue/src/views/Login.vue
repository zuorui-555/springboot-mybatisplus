<template>
  <div style = "width: 100%; height: 100vh; ">
    <div style = "width: 400px; margin: 100px auto">
      <div style = "color: dodgerblue; font-size: 30px; text-align: center; padding: 30px 0">欢迎登录</div>
      <el-form :rules = "rules" ref = "form" :model = "form" size = "default">
        <el-form-item prop = "username">
          <el-input prefix-icon = "User" v-model = "form.username" clearable></el-input>
        </el-form-item>
        <el-form-item prop = "password">
          <el-input prefix-icon = "Key" v-model = "form.password" show-password clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style = "width: 50px" type = "primary" @click = "login">登录</el-button>
          <el-button style = "width: 50px" type = "primary" @click = "this.$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  data() {
    return {
      form: {},
      rules: {
        username: [
          {require: true, message: '请输入用户名', trigger: 'blur'},
          { min: 3, max: 7, message: '长度在 3 到 7 个字符', trigger: 'blur' }
        ],
        password: [
          {require: true, message: '请输入密码', trigger: 'blur'},
          { min: 3, max: 7, message: '长度在 3 到 7 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              debugger
              sessionStorage.clear();//登录之前清空sessionStorage
              sessionStorage.setItem("user", JSON.stringify(res.data))  //缓存用户信息
              console.log(JSON.stringify((res.data)));
              this.$router.push("/")  //登录成功之后进行页面的，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      });
      return false
    }
  }
}
</script>

<style scoped>

</style>