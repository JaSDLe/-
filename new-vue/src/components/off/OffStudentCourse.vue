<template>
  <div>
    <el-container>
      <el-header
        style="padding: 0px;display:flex;justify-content:space-between;align-items: center"
      >
        <div style="display: inline">
          <el-input
            placeholder="请输入课程名或学生姓名或教师姓名"
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="small"
            @keyup.enter.native="searchStudentCourse"
            prefix-icon="el-icon-search"
            v-model="keywords"
          ></el-input>

          <el-button
            type="primary"
            size="small"
            style="margin-left: 5px"
            icon="el-icon-search"
            @click="searchStudentCourse"
          >搜索</el-button>
        </div>

        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="showStudentCourseView"
          >分配课程</el-button>
        </div>
      </el-header>

      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <el-table
            :data="studentCourses"
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
              prop="teacherCourse.course.course_id"
              align="left"
              label="课程代码"
              show-overflow-tooltip
              width
            ></el-table-column>
            <el-table-column
              prop="teacherCourse.course.name"
              width
              align="left"
              label="课程名称"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              prop="student.name"
              width
              align="left"
              show-overflow-tooltip
              label="学生姓名"
            ></el-table-column>
            <el-table-column
              prop="student.username"
              align="left"
              label="学生用户名"
              show-overflow-tooltip
              width
            ></el-table-column>
            <el-table-column
              prop="teacherCourse.teacher.name"
              width
              align="left"
              show-overflow-tooltip
              label="老师姓名"
            ></el-table-column>
            <el-table-column
              prop="teacherCourse.teacher.username"
              width
              align="left"
              show-overflow-tooltip
              label="老师用户名"
            ></el-table-column>

            <el-table-column
              fixed="right"
              label="操作"
              align="center"
              width="150"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  size="mini"
                  @click="deleteStudentCourse(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button
              type="danger"
              size="small"
              icon="el-icon-delete"
              v-if="studentCourses.length>0"
              :disabled="multipleSelection.length==0"
              @click="deleteManyStudentCourses"
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
      width="1000px"
    >
      <el-transfer
        v-model="chosenStudent"
        :data="studentsData"
        :titles="['学生列表', '分配列表']"
        target-order="push"
        style="margin-bottom:20px"
      ></el-transfer>

      <el-transfer
        v-model="chosenCourse"
        :data="coursesData"
        :titles="['课程列表', '分配列表']"
        target-order="push"
        style="margin-bottom:20px"
      ></el-transfer>

      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="cancelEidt()">取 消</el-button>
        <el-button size="mini" type="primary" @click="addStudentCourse()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      multipleSelection: [],
      keywords: "",
      currentPage: 1,
      pageSize: 10,
      totalCount: -1,
      dialogTitle: "",
      dialogVisible: false,
      tableLoading: false,

      studentCourses: [],
      courses: [],
      students: [],

      studentsData: [],
      coursesData: [],
      chosenStudent: [],
      chosenCourse: []
    };
  },
  mounted: function() {
    this.loadStudentCourses();
    this.generateStudentsData();
    this.generateCoursesData();
  },
  methods: {
    loadStudentCourses() {
      var _this = this;
      this.tableLoading = true;
      this.getRequest(
        "/studentcourse/sc?page=" +
          this.currentPage +
          "&size=" +
          this.pageSize +
          "&keywords=" +
          this.keywords
      ).then(resp => {
        this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.studentCourses = data.studentcourses;
          _this.totalCount = data.count;
        }
      });
    },
    generateStudentsData() {
      this.loadStudents();
      var data = [];
      this.students.forEach((item, index) => {
        data.push({
          label: item.name + "(" + item.username + ")",
          key: item.id
        });
      });
      this.studentsData = data;
    },
    generateCoursesData() {
      this.loadCourses();
      var data = [];
      this.courses.forEach((item, index) => {
        data.push({
          label: '《' + item.course.name + "(" + item.course.course_id + ")" + '》' + item.teacher.name + "(" + item.teacher.username + ")",
          key: item.id
        });
      });
      this.coursesData = data;
    },
    loadCourses() {
      var _this = this;
      this.getRequest("/office/teacher/teacherCourse").then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.courses = data;
        }
      });
    },
    loadStudents() {
      var _this = this;
      this.getRequest("/office/user/student").then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.students = data;
        }
      });
    },
    showStudentCourseView() {
      this.generateStudentsData();
      this.generateCoursesData();
      this.dialogTitle = "分配课程";
      this.dialogVisible = true;
    },
    addStudentCourse() {
      var _this = this;
      this.tableLoading = true;
      //添加
      this.postRequest("/studentcourse/ins", {
        user_ids: this.chosenStudent,
        teacher_course_ids: this.chosenCourse
      }).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          _this.dialogVisible = false;
          _this.emptyStudentCourseData();
          _this.loadStudentCourses();
        }
      });
      _this.tableLoading = false;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteManyStudentCourses() {
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
    deleteStudentCourse(row) {
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
      this.deleteRequest("/studentcourse/sc/" + ids).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          _this.currentPage = 1;
          _this.loadStudentCourses();
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
        this.loadStudentCourses();
      }
    },
    searchStudentCourse() {
      this.loadStudentCourses();
    },
    handleCurrentChange(currentChange) {
      this.currentPage = currentChange;
      this.loadStudentCourses();
    },
    handleSizeChange(sizeChange) {
      this.pageSize = sizeChange;
      this.currentPage = 1;
      this.loadStudentCourses();
    },
    cancelEidt() {
      this.dialogVisible = false;
      this.emptyStudentCourseData();
    },
    handleDialogClose(done) {
      this.emptyStudentCourseData();
      done();
    },
    emptyStudentCourseData() {
      this.chosenStudent = [];
      this.chosenCourse = [];
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

.el-transfer-panel {
  width: 430px;
}
</style>