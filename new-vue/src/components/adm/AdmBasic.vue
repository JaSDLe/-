<template>
  <div>
    <el-container>
      <el-header
        style="padding: 0px;display:flex;justify-content:space-between;align-items: center"
      >
        <div style="display: inline">
          <el-input
            placeholder="请输入姓名"
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="small"
            @keyup.enter.native="searchUser"
            prefix-icon="el-icon-search"
            v-model="keywords"
          ></el-input>

          <el-button
            type="primary"
            size="small"
            style="margin-left: 5px"
            icon="el-icon-search"
            @click="searchUser"
          >搜索</el-button>
        </div>

        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="small" icon="el-icon-plus" @click="showAddUserView">添加用户</el-button>
        </div>
      </el-header>

      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <el-table
            :data="users"
            v-loading="tableLoading"
            border
            resizable
            stripe
            @selection-change="handleSelectionChange"
            size="medium"
            style="width: 100%"
          >
            <el-table-column type="index" align="center" label=" " width="38"></el-table-column>

            <el-table-column type="selection" align="center" width="40"></el-table-column>

            <el-table-column prop="name" align="left" show-overflow-tooltip label="姓名"></el-table-column>
            <el-table-column prop="username" align="left" show-overflow-tooltip label="用户名"></el-table-column>
            <el-table-column prop="roles[0].nameZh" align="left" show-overflow-tooltip label="角色"></el-table-column>

            <el-table-column
              fixed="right"
              align="center"
              width="150"
              show-overflow-tooltip
              label="操作"
            >
              <template slot-scope="scope">
                <el-button
                  type="warning"
                  icon="el-icon-edit"
                  @click="showEditUserView(scope.row)"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  size="mini"
                >修改</el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  size="mini"
                  @click="deleteUser(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div style="display: flex;justify-content: space-between;margin-top: 10px">
            <el-button
              type="danger"
              size="small"
              icon="el-icon-delete"
              v-if="users.length>0"
              :disabled="multipleSelection.length==0"
              @click="deleteManyUsers"
            >批量删除</el-button>

            <el-pagination
              background
              :page-size="pageSize"
              :page-sizes="[10, 5, 20, 30]"
              :current-page="currentPage"
              @current-change="handleCurrentChange"
              @size-change="handleSizeChange"
              layout="total, prev, pager, next, sizes, jumper"
              :total="totalCount"
            ></el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>

    <el-dialog
      :title="dialogTitle"
      style="padding: 0px;text-align: left"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible"
      :before-close="handleDialogClose"
      width="500px"
    >
      <el-form :model="user" :rules="rules" ref="addUserForm" style="margin: 0px;padding: 0px;">
        <el-form-item label="用户名:" prop="username">
          <el-input
            prefix-icon="el-icon-edit"
            type="text"
            v-model.trim="user.username"
            clearable
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>

        <el-form-item label="姓名:" prop="name">
          <el-input
            prefix-icon="el-icon-edit"
            v-model.trim="user.name"
            clearable
            placeholder="请输入姓名"
          ></el-input>
        </el-form-item>

        <el-form-item prop="role" style="padding-top:20px">
          <el-select v-model="value" placeholder="请选择角色">
            <el-option v-for="item in options" :key="item.id" :label="item.nameZh" :value="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            type="password"
            v-model.trim="user.password"
            v-bind:disabled="isDisabled"
            placeholder="请输入密码"
            clearable
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="checkPass">
          <el-input
            type="password"
            v-model.trim="user.checkPass"
            v-bind:disabled="isDisabled"
            placeholder="请再次输入密码"
            clearable
            show-password
          ></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="cancelEidt('addUserForm')">取 消</el-button>
        <el-button size="mini" type="primary" @click="addUser('addUserForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (this.isDisabled == false) {
        if (value === "" || value == null) {
          callback(new Error("请输入密码"));
        } else {
          this.$refs.addUserForm.validateField("checkPass");
        }
      }
      callback();
    };
    var validatePass2 = (rule, value, callback) => {
      if (this.isDisabled == false) {
        if (value === "" || value == null) {
          callback(new Error("请再次输入密码"));
        } else if (value !== this.user.password) {
          callback(new Error("两次输入密码不一致!"));
        } else {
          callback();
        }
      }
      callback();
    };
    var validateUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入用户名"));
      } else {
        callback();
      }
    };
    var validateName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入姓名"));
      } else {
        callback();
      }
    };
    return {
      users: [],
      keywords: "",
      dialogTitle: "",
      multipleSelection: [],
      pageSize: 10,
      totalCount: -1,
      currentPage: 1,
      dialogVisible: false,
      tableLoading: false,
      isDisabled: true,

      user: {
        username: "",
        name: "",
        password: "",
        checkPass: ""
      },
      rules: {
        username: [{ validator: validateUsername, trigger: "blur" }],
        name: [{ validator: validateName, trigger: "blur" }],
        password: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }]
      },
      options: [],
      value: ""
    };
  },
  mounted: function() {
    this.loadUsers();
    this.loadRoles();
  },
  methods: {
    loadRoles() {
      var _this = this;
      this.getRequest("/system/basic/roles").then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.options = data;
        }
      });
    },
    loadUsers() {
      var _this = this;
      this.tableLoading = true;
      this.getRequest(
        "/admin/basic/user?page=" +
          this.currentPage +
          "&size=" +
          this.pageSize +
          "&keywords=" +
          this.keywords
      ).then(resp => {
        this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.users = data.users;
          _this.totalCount = data.count;
        }
      });
    },
    showEditUserView(row) {
      this.isDisabled = false;
      this.dialogTitle = "编辑用户";
      this.user.id = row.id;
      this.user.username = row.username;
      this.user.name = row.name;
      this.value = row.roles[0].id;
      this.dialogVisible = true;
    },
    showAddUserView() {
      this.isDisabled = true;
      this.dialogTitle = "添加用户";
      this.dialogVisible = true;
    },
    addUser(formName) {
      var _this = this;
      this.tableLoading = true;
      //更新
      if (this.user.id) {
        if (
          (this.user.password == "" && this.user.checkPass == "") ||
          (this.user.password == null && this.user.checkPass == null)
        ) {
          this.putRequest(
            "/admin/basic/?role_id=" + this.value,
            this.user
          ).then(resp => {
            _this.tableLoading = false;
            if (resp && resp.status == 200) {
              _this.dialogVisible = false;
              _this.emptyUserData();
              _this.loadUsers();
            }
          });
        } else {
          this.$refs[formName].validate(valid => {
            if (valid) {
              this.putRequest(
                "/admin/basic/?role_id=" + this.value,
                this.user
              ).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  _this.dialogVisible = false;
                  _this.emptyUserData();
                  _this.loadUsers();
                }
              });
            }
          });
        }
      } else {
        //添加
        this.$refs[formName].validate(valid => {
          if (valid) {
            this.postRequest(
              "/admin/basic/user/reg?role_id=" + this.value,
              this.user
            ).then(resp => {
              _this.tableLoading = false;
              if (resp && resp.status == 200) {
                _this.dialogVisible = false;
                _this.emptyUserData();
                _this.loadUsers();
              }
            });
          }
        });
      }
      _this.tableLoading = false;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteManyUsers() {
      this.$confirm(
        "此操作将删除[" + this.multipleSelection.length + "]条数据, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          var ids = "";
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
          }
          this.doDelete(ids);
        })
        .catch(() => {});
    },
    deleteUser(row) {
      this.$confirm("此操作将永久删除[" + row.name + "], 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.doDelete(row.id);
        })
        .catch(() => {});
    },
    doDelete(ids) {
      this.tableLoading = true;
      var _this = this;
      this.deleteRequest("/admin/basic/user/" + ids).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          _this.currentPage = 1;
          _this.loadUsers();
        } else {
          _this.$message({
            type: "error",
            message: "删除的数据与别的表有关联，删除失败！"
          });
        }
      });
    },
    keywordsChange(val) {
      if (val == "") {
        this.currentPage = 1;
        this.loadUsers();
      }
    },
    searchUser() {
      this.loadUsers();
    },
    handleCurrentChange(currentChange) {
      this.currentPage = currentChange;
      this.loadUsers();
    },
    handleSizeChange(sizeChange) {
      this.pageSize = sizeChange;
      this.currentPage = 1;
      this.loadUsers();
    },
    cancelEidt(formName) {
      this.dialogVisible = false;
      this.emptyUserData();
      this.$refs.addUserForm.clearValidate();
    },
    handleDialogClose(done) {
      this.emptyUserData();
      this.$refs.addUserForm.clearValidate();
      done();
    },
    emptyUserData() {
      this.user = {
        username: "",
        name: "",
        password: "",
        checkPass: ""
      };
      this.value = "";
    }
  }
};
</script>

<style>
.el-dialog__body {
  padding-top: 0px;
  padding-bottom: 0px;
}

.slide-fade-enter-active {
  transition: all 0.8s ease;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter,
.slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}
</style>
