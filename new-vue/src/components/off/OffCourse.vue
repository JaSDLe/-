<template>
  <div>
    <el-container>
      <el-header
        style="padding: 0px;display:flex;justify-content:space-between;align-items: center"
      >
        <div style="display: inline">
          <el-input
            placeholder="请输入课程名"
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="small"
            @keyup.enter.native="searchCourse"
            prefix-icon="el-icon-search"
            v-model="keywords"
          ></el-input>

          <el-button
            type="primary"
            size="small"
            style="margin-left: 5px"
            icon="el-icon-search"
            @click="searchCourse"
          >搜索</el-button>
        </div>

        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="small" icon="el-icon-plus" @click="showAddCourseView">添加课程</el-button>
        </div>
      </el-header>

      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <el-table
            :data="courses"
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

            <el-table-column
              prop="course_id"
              width="120px"
              align="left"
              show-overflow-tooltip
              label="课程代码"
            ></el-table-column>
            <el-table-column
              prop="name"
              width="160px"
              show-overflow-tooltip
              align="left"
              label="课程名称"
            ></el-table-column>
            <el-table-column
              prop="isExam"
              width="80px"
              show-overflow-tooltip
              align="left"
              label="考核方式"
            >
            <template slot-scope="scope">                   
                    <p v-if="scope.row.isExam=='1'">
                        考试
                    </p>
                    <p v-else-if="scope.row.isExam=='0'">
                        考查
                    </p>                    
            </template>
            </el-table-column>
            <el-table-column
              prop="proportion"
              width="110px"
              align="left"
              show-overflow-tooltip
              label="平时成绩占比"
            >
            <template slot-scope="scope">                   
                    <p>
                        {{scope.row.proportion*100}}%
                    </p>                   
            </template>
            </el-table-column>
            <el-table-column prop="introduction" align="left" show-overflow-tooltip label="课程介绍"></el-table-column>

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
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  size="mini"
                  @click="showEditCourseView(scope.row)"
                >修改</el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  size="mini"
                  @click="deleteCourse(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div style="display: flex;justify-content: space-between;margin-top: 10px">
            <el-button
              type="danger"
              size="small"
              icon="el-icon-delete"
              v-if="courses.length>0"
              :disabled="multipleSelection.length==0"
              @click="deleteManyCourses"
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
      <el-form :model="course" :rules="rules" ref="addCourseForm" style="margin: 0px;padding: 0px;">
        <el-form-item label="课程代码:" prop="course_id">
          <el-input
            prefix-icon="el-icon-edit"
            v-model.trim="course.course_id"
            type="text"
            clearable
            placeholder="请输入课程代码"
          ></el-input>
        </el-form-item>

        <el-form-item label="课程名称:" prop="name">
          <el-input
            prefix-icon="el-icon-edit"
            v-model.trim="course.name"
            clearable
            type="text"
            placeholder="请输入课程名称"
          ></el-input>
        </el-form-item>

        <el-form-item label="考核方式:" prop="isExam">
          <el-switch
            style="margin-top:50px;margin-left:-72px;"
            v-model="course.isExam"
            active-text="考试"
            inactive-text="考查"
            :active-value="1"
            :inactive-value="0"
          ></el-switch>
        </el-form-item>

        <el-form-item label="平时成绩占比:" prop="proportion">
          <el-slider
            style="margin-top:50px;"
            v-model="course.proportion"
            :step="0.1"
            max="1"
            show-input
            show-stops
          ></el-slider>
        </el-form-item>

        <el-form-item label="课程简介:" prop="introduction">
          <el-input
            v-model="course.introduction"
            clearable
            autosize="true"
            type="textarea"
            placeholder="请输入课程简介"
          ></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="cancelEidt('addCourseForm')">取 消</el-button>
        <el-button size="mini" type="primary" @click="addCourse('addCourseForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    var validateCourse_id = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入课程代码"));
      } else {
        callback();
      }
    };
    var validateName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请输入课程名称"));
      } else {
        callback();
      }
    };
    // var validateisExam = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error("请输入考核方式"));
    //   } else {
    //     callback();
    //   }
    // };
    // var validateProportion = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error("请输入平时成绩占比"));
    //   } else {
    //     callback();
    //   }
    // };
    // var validateIntroduction = (rule, value, callback) => {
    //   if (!value) {
    //     return callback(new Error("请输入课程简介"));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      courses: [],
      keywords: "",
      dialogTitle: "",
      multipleSelection: [],
      pageSize: 10,
      totalCount: -1,
      currentPage: 1,
      dialogVisible: false,
      tableLoading: false,

      course: {
        course_id: "",
        name: "",
        isExam: 1,
        proportion: 0.5,
        introduction: ""
      },
      rules: {
        course_id: [{ validator: validateCourse_id, trigger: "blur" }],
        name: [{ validator: validateName, trigger: "blur" }],
        // isExam: [{ validator: validateisExam, trigger: "blur" }],
        // proportion: [{ validator: validateProportion, trigger: "blur" }],
        // introduction: [{ validator: validateIntroduction, trigger: "blur" }]
      }
    };
  },
  mounted: function() {
    this.loadCourses();
  },
  methods: {
    loadCourses() {
      var _this = this;
      this.tableLoading = true;
      this.getRequest(
        "/office/course/course?page=" +
          this.currentPage +
          "&size=" +
          this.pageSize +
          "&keywords=" +
          this.keywords
      ).then(resp => {
        this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.courses = data.courses;
          _this.totalCount = data.count;
        }
      });
    },
    showEditCourseView(row) {
      this.dialogTitle = "编辑课程";
      this.course = row;
      this.dialogVisible = true;
    },
    showAddCourseView() {
      this.dialogTitle = "添加课程";
      this.course.isExam = 1;
      this.dialogVisible = true;
    },
    addCourse(formName) {
      var _this = this;
      this.tableLoading = true;
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.course.id) {
            //更新
            this.putRequest("/office/course/", this.course).then(resp => {
              _this.tableLoading = false;
              if (resp && resp.status == 200) {
                _this.dialogVisible = false;
                _this.emptyCourseData();
                _this.loadCourses();
              }
            });
          } else {
            //添加
            this.postRequest("/office/course/course/ins", this.course).then(
              resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  _this.dialogVisible = false;
                  _this.emptyCourseData();
                  _this.loadCourses();
                }
              }
            );
          }
        }
      });
      _this.tableLoading = false;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteManyCourses() {
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
    deleteCourse(row) {
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
      this.deleteRequest("/office/course/course/" + ids).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          _this.currentPage = 1;
          _this.loadCourses();
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
        this.loadCourses();
      }
    },
    searchCourse() {
      this.loadCourses();
    },
    handleCurrentChange(currentChange) {
      this.currentPage = currentChange;
      this.loadCourses();
    },
    handleSizeChange(sizeChange) {
      this.pageSize = sizeChange;
      this.currentPage = 1;
      this.loadCourses();
    },
    cancelEidt(formName) {
      this.dialogVisible = false;
      this.emptyCourseData();
      this.$refs.addCourseForm.clearValidate();
    },
    handleDialogClose(done) {
      this.emptyCourseData();
      this.$refs.addCourseForm.clearValidate();
      done();
    },
    emptyCourseData() {
      this.course = {
        course_id: "",
        name: "",
        isExam: 1,
        proportion: 0.5,
        introduction: ""
      };
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
