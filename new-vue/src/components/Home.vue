<template>
  <div>
    <el-container class="home-container">
      <el-header class="home-header">
        <span class="home_title">课程过程化考核管理系统</span>

        <div style="display: flex;align-items: center;margin-right: 7px;">
          <el-dropdown @command="handleCommand">
            <span
              class="el-dropdown-link home_userinfo el-icon-user-solid"
              style="display: flex;align-items: center;font-size: 20px;margin-right:20px;"
            >{{user.name}}</span>

            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-setting" command="me" style="font-size: 20px;">个人中心</el-dropdown-item>
              <el-dropdown-item
                icon="el-icon-switch-button"
                command="logout"
                divided
                style="font-size: 20px;"
              >注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <!-- #ececec -->
      <!-- background-color="#40a0ff50" 
      text-color="#fff"-->
      <el-container>
        <el-aside width="180px" class="home-aside">
          <div style="display: flex;justify-content: flex-start;width: 180px;text-align: left;">
            <el-menu style="width: 180px;" unique-opened router>
              <template v-for="(item,index) in this.routes">
                <el-submenu :key="index" :index="index+''">
                  <template slot="title">
                    <span slot="title" style="font-size: 20px;">{{item.name}}</span>
                  </template>

                  <el-menu-item
                    width="180px"
                    style="padding-left: 30px;padding-right:0px;margin-left: 0px;width: 170px;text-align: left;font-size:20px;"
                    v-for="child in item.children"
                    :index="child.path"
                    :key="child.path"
                  >
                    <i class="el-icon-caret-right"></i>
                    {{child.name}}
                  </el-menu-item>
                </el-submenu>
              </template>
            </el-menu>
          </div>
        </el-aside>

        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="font-size: 20px;">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <keep-alive>
            <router-view v-if="this.$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!this.$route.meta.keepAlive"></router-view>
          <welcome v-if="this.$route.path == '/home'"></welcome>
        </el-main>
      </el-container>
    </el-container>

    <el-dialog
      :title="dialogTitle"
      style="padding: 0;"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      :before-close="handleDialogClose"
      top="8vh"
      width="500px"
    >
      <el-form
        :model="myuser"
        :rules="rules"
        ref="updateUserForm"
        style="margin: 0px;padding: 0px;"
      >
        <el-form-item label="用户名:" prop="username">
          <el-input v-model.trim="myuser.username" disabled></el-input>
        </el-form-item>

        <el-form-item label="姓名:" prop="name">
          <el-input v-model.trim="myuser.name" disabled></el-input>
        </el-form-item>

        <el-form-item label="身份:" prop="role">
          <el-input v-model.trim="myuser.role" disabled></el-input>
        </el-form-item>

        <el-form-item label="原密码" prop="oldpassword">
          <el-input
            type="password"
            v-model.trim="myuser.oldpassword"
            placeholder="请输入原密码"
            clearable
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="新密码" prop="newpassword">
          <el-input
            type="password"
            v-model.trim="myuser.newpassword"
            placeholder="请输入新密码"
            clearable
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model.trim="myuser.checkPass"
            placeholder="请再次输入新密码"
            clearable
            show-password
          ></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="cancelEidt()">取 消</el-button>
        <el-button size="mini" type="primary" @click="editUser('updateUserForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import welcome from "./Welcome.vue";
export default {
  components: { welcome },

  data() {
    var validatePass0 = (rule, value, callback) => {
      if (value === "" || value == null) {
        callback(new Error("请输入原密码"));
      } else {
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "" || value == null) {
        callback(new Error("请输入新密码"));
      } else {
        this.$refs.updateUserForm.validateField("checkPass");
      }
      callback();
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "" || value == null) {
        callback(new Error("请再次输入新密码"));
      } else if (value !== this.myuser.newpassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
      // callback();
    };
    return {
      dialogTitle: "",
      dialogVisible: false,

      myuser: {
        username: "",
        name: "",
        role: "",
        oldpassword: "",
        newpassword: "",
        checkPass: ""
      },
      rules: {
        oldpassword: [{ validator: validatePass0, trigger: "blur" }],
        newpassword: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }]
      }
    };
  },
  computed: {
    user() {
      // alert(JSON.stringify(this.routes));
      return this.$store.state.user;
    },
    routes() {
      return this.$store.state.routes;
    }
  },
  methods: {
    handleCommand(cmd) {
      var _this = this;
      if (cmd == "logout") {
        this.$confirm("注销登录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            _this.getRequest("/logout");
            _this.$store.commit("logout");
            _this.$router.replace({ path: "/" });
          })
          .catch(() => {
            _this.$message({
              type: "info",
              message: "取消"
            });
          });
      } else if (cmd == "me") {
        // alert(JSON.stringify(this.user));
        this.myuser.username = this.user.username;
        this.myuser.name = this.user.name;
        this.myuser.role = this.user.roles[0].nameZh;
        this.dialogTitle = "个人中心";
        this.dialogVisible = true;
      }
    },
    editUser(formName) {
      var _this = this;
      //更新
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.putRequest(
            "/config/change/password?id=" +
              this.user.id +
              "&oldPass=" +
              this.myuser.oldpassword +
              "&newPass=" +
              this.myuser.newpassword
          ).then(resp => {
            if (resp && resp.status == 200) {
              _this.dialogVisible = false;
              _this.cancelEidt();
            }
          });
        }
      });
    },
    handleDialogClose(done) {
      this.cancelEidt();
      // this.$refs.addUserForm.clearValidate();
      done();
    },
    cancelEidt() {
      this.dialogVisible = false;
      // this.emptyUserData();
      this.$refs.updateUserForm.resetFields();
      // this.$refs.addUserForm.clearValidate();
    }
  }
};
</script>

<style scoped>
.home-container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.home-header {
  /* background-color: #20a0ff; */
  background-color: #40a0ffa6;
  background-color: #409eff;
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: content-box;
  padding: 0px;
}

.home-aside {
  /* background-color: #ececec; */
  background-color: #00000007;
}

.home-main {
  background-color: #fff;
  color: #000;
  text-align: center;
  margin: 0px;
  padding: 0px;
}

.home_title {
  color: #fff;
  font-size: 24px;
  display: inline;
  margin-left: 15px;
}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  display: inline;
  margin-right: 20px;
}

.el-submenu .el-menu-item {
  width: 180px;
  min-width: 175px;
}
</style>
