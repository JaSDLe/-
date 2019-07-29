<template>
  <div class="page" style="
  margin:-8px;
  position:absolute;
  width: 100%;
  height:100%;
  ">
    <div style="margin: 50px auto;
  width: 90%;
  padding: 35px 35px 15px 35px;">
      <el-container>
        <el-header>
          <div style="display: flex;justify-content: space-around;flex-wrap: wrap;text-align: left">
            <!-- <el-card :body-style="{ padding: '0px' }" style="height:100px;width:100px;">
            <img src="../static/logo_icon.png" class="image" style="height:100px;width:100px;">
          </el-card>

          <el-card :body-style="{ padding: '0px' }" style="height:100px;width:430px;">
            <img src="../static/logo_name.png" class="image" style="height:100px;width:430px;">
            </el-card>-->

            <!-- <el-card
            :body-style="{ padding: '0px' }"
            style="height:150px;
                    width:700px;
                    display: flex;
                    align-items:Center;
                    background: rgba(255, 255, 255, 0.1);
                  "
            >-->
            <span
              style="font-family:'方正姚体 Bold', '方正姚体';
                      font-size:60px;
                      color:#023A64;
                      padding:20px;
                      font-weight: bold;
                      "
            >课程过程化考核管理系统</span>
            <!-- </el-card> -->
          </div>
        </el-header>

        <el-main style="margin-top:150px;">
          <div style="display: flex;justify-content: space-around;flex-wrap: wrap;">
            <!-- <el-card :body-style="{ padding: '0px' }" style="width:800px;">
            <img src="../static/gate.jpg" class="image" style="width:100%;">
            </el-card>-->

            <div style="margin-top:7px;width:800px;">
              <el-carousel arrow="always" trigger="click" interval="1500" height="400px">
                <el-carousel-item v-for="item in imagesbox" :key="item.id">
                  <img :src="item.idView" fit="contain">
                </el-carousel-item>
              </el-carousel>
            </div>

            <el-form
              :model="ruleForm"
              :rules="rules"
              ref="ruleForm"
              v-loading="loading"
              class="login-container"
            >
              <h3 style="color:#023A64;" class="login_title">登录</h3>

              <el-form-item prop="username">
                <el-input
                  type="text"
                  autocomplete="on"
                  placeholder="用户名"
                  prefix-icon="el-icon-user"
                  @keyup.enter.native="submitForm('ruleForm')"
                  v-model.trim="ruleForm.username"
                  clearable
                ></el-input>
              </el-form-item>

              <el-form-item prop="password">
                <el-input
                  type="password"
                  placeholder="密码"
                  prefix-icon="el-icon-key"
                  @keyup.enter.native="submitForm('ruleForm')"
                  v-model.trim="ruleForm.password"
                  clearable
                  show-password
                ></el-input>
              </el-form-item>

              <el-form-item prop="verifycode">
                <el-input
                  type="text"
                  placeholder="请输入验证码"
                  prefix-icon="el-icon-s-claim"
                  @keyup.enter.native="submitForm('ruleForm')"
                  v-model.trim="ruleForm.verifycode"
                  style="width:260px;float:left;"
                  clearable
                ></el-input>
                <span @click="refreshCode" style="float:right;margin-right:5px;">
                  <s-identify :identifyCode="identifyCode"></s-identify>
                </span>
              </el-form-item>

              <el-form-item>
                <div class="identifybox">
                  <div @click="refreshCode">
                    <s-identify :identifyCode="identifyCode"></s-identify>
                  </div>
                  <el-button
                    style="color:#023A64;margin-top:-60px;"
                    @click="refreshCode"
                    type="text"
                    class="textbtn"
                  >看不清,换一张</el-button>
                </div>
              </el-form-item>

              <el-form-item style="margin: -40px auto 0px auto;">
                <el-button
                  style="margin: -60px auto 0px auto;"
                  type="primary"
                  @click="submitForm('ruleForm')"
                >登录</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-main>
      </el-container>
    </div>
  </div>
</template>

<script>
import SIdentify from "./identifyCode.vue";
export default {
  components: {
    SIdentify
  },
  data() {
    var checkUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入用户名"));
      } else {
        callback();
      }
    };
    var checkPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };
    var validateVerifycode = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入验证码"));
      } else if (value !== this.identifyCode) {
        callback(new Error("验证码错误"));
      } else {
        callback();
      }
    };
    return {
      imagesbox: [
        { id: 0, idView: require("../static/p11.jpg") },
        { id: 1, idView: require("../static/p22.jpg") },
        { id: 2, idView: require("../static/p33.jpg") }
      ],
      ruleForm: {
        username: "",
        password: "",
        verifycode: ""
      },
      rules: {
        username: [{ validator: checkUsername, trigger: "blur" }],
        password: [{ validator: checkPassword, trigger: "blur" }],
        verifycode: [{ validator: validateVerifycode, trigger: "blur" }]
      },
      loading: false,

      identifyCodes: "1234567890",
      identifyCode: ""
    };
  },
  mounted() {
    this.identifyCode = "";
    this.makeCode(this.identifyCode, 4);
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true;
          this.postRequest("/login", {
            username: this.ruleForm.username,
            password: this.ruleForm.password
          }).then(resp => {
            this.loading = false;
            if (resp && resp.status == 200) {
              var data = resp.data;
              this.$store.commit("login", data.obj);
              var path = this.$route.query.redirect;
              this.$router.replace({
                path: path == "/" || path == undefined ? "/home" : path
              });
            }
          });
        }
      });
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
          this.randomNum(0, this.identifyCodes.length)
        ];
      }
      console.log(this.identifyCode);
    }
  }
};
</script>

<style scoped>
* {
  font-size: 20px;
}
.page {
  /* font-size: 20px; */
  background-image: url(../static/background2.jpg);
  background-color: white;
  /* filter:alpha(Opacity=85);
    -moz-opacity:0.85;
    opacity: 0.85; */
}
/* .page { */
/* background-color: aquamarine; */
/* content: ""; */
/* position: relative; */
/* overflow: hidden; */
/* z-index: 0; */
/* background-position: center;
    background-size: contain;
    background-repeat: no-repeat; */
/* width: 100%;
    height: 100%; */
/* filter: blur(10px); */
/* background: no-repeat center center fixed; */
/* } */
/* .inpage { */
/* background-color: aquamarine; */
/* position: relative; */
/* overflow: hidden;
  z-index: -1; */
/* background: no-repeat center center fixed; */
/* } */
/* .inpage ::before { */
/* background-color: aquamarine; */
/* content: "";
  position: absolute; */
/* filter: blur(5px); */
/* z-index: -2; */
/* background: no-repeat center center fixed; */
/* } */
.login-container {
  /* border-radius: 4px; */
  /* background-clip: padding-box; */
  /* margin: 180px auto; */
  width: 400px;
  padding: 35px 35px 15px 35px;
  /* background: #fff; */
  background: rgba(23, 110, 110, 0.1);
  /* border: 1px solid #eaeaea; */
  /* border: 1px solid #ffffff; */
  /* box-shadow: 0 0 20px #cac6c6; */
  /* box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04); */
}
.login_title {
  margin: 0px auto 40px auto;
  font-size: 40px;
  text-align: center;
  /* color: #505458; */
}
.identifybox {
  display: flex;
  justify-content: space-between;
}
</style>
