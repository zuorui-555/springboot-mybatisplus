<template>
  <div style = "width: 100%; height: 100vh; overflow: hidden">
    <div style = "width: 400px; margin: 100px auto">
      <div style = "color: dodgerblue; font-size: 30px; text-align: center; padding: 30px 0">欢迎注册</div>
      <el-form :rules = "rules" ref = "form" :model = "form" size = "default">
        <el-form-item prop = "username">
          <el-input prefix-icon = "User" v-model = "form.username" clearable></el-input>
        </el-form-item>
        <el-form-item prop = "password">
          <el-input prefix-icon = "lock" v-model = "form.password" show-password clearable></el-input>
        </el-form-item>
        <el-form-item prop = "confirm">
          <el-input prefix-icon = "lock" v-model = "form.confirm" show-password clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style = "width: 50px" type = "primary" @click = "register">注册</el-button>
          <el-button style = "width: 50px" type = "primary" @click = "this.$router.push('/login')">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Register",
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
        ],
        confirm: [
          {require: true, message: '再次输入密码', trigger: 'blur'},
          { min: 3, max: 7, message: '长度在 3 到 7 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['form'].validate((valid) => {
        if (this.form.password !== this.form.confirm) {
          this.$message({
            type: "error",
            message: '两次输入密码不一致！'
          })
          valid = false;
        }
        if (valid) {
          request.post("/user/register", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功"
              })
              this.$router.push("/login")  //登录成功之后进行页面的，跳转到主页
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