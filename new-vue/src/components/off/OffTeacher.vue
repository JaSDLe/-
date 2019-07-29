<template>
  <div>
    <el-container>
      <el-header
        style="padding: 0px;display:flex;justify-content:space-between;align-items: center"
      >
        <div style="display: inline">
          <el-input
            placeholder="请输入课程名或教师姓名"
            clearable
            @change="keywordsChange"
            style="width: 300px;margin: 0px;padding: 0px;"
            size="small"
            @keyup.enter.native="searchTeacherCourse"
            prefix-icon="el-icon-search"
            v-model="keywords"
          ></el-input>

          <el-button
            type="primary"
            size="small"
            style="margin-left: 5px"
            icon="el-icon-search"
            @click="searchTeacherCourse"
          >搜索</el-button>
        </div>

        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button
            type="primary"
            size="small"
            icon="el-icon-plus"
            @click="showTeacherCourseView"
          >分配课程</el-button>
        </div>
      </el-header>

      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <el-table
            :data="teacherCourses"
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
              prop="course.course_id"
              width
              align="left"
              show-overflow-tooltip
              label="课程代码"
            ></el-table-column>
            <el-table-column
              prop="course.name"
              width
              show-overflow-tooltip
              align="left"
              label="课程名称"
            ></el-table-column>
            <el-table-column
              prop="teacher.name"
              width
              show-overflow-tooltip
              align="left"
              label="教师姓名"
            ></el-table-column>
            <el-table-column
              prop="teacher.username"
              width
              align="left"
              show-overflow-tooltip
              label="教师用户名"
            ></el-table-column>

            <el-table-column
              fixed="right"
              align="center"
              width="150"
              show-overflow-tooltip
              label="操作"
            >
              <template slot-scope="scope">
                <el-button
                  type="success"
                  icon="el-icon-search"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  size="mini"
                  @click="loadAllGrade(scope.row)"
                >管理成绩</el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  size="mini"
                  @click="deleteTeacherCourse(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div style="display: flex;justify-content: space-between;margin-top: 10px">
            <el-button
              type="danger"
              size="small"
              icon="el-icon-delete"
              v-if="teacherCourses.length>0"
              :disabled="multipleSelection.length==0"
              @click="deleteManyteacherCourses"
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
      width="740px"
    >
      <el-transfer
        v-model="chosenTeacher"
        :data="teachersData"
        :titles="['教师列表', '分配列表']"
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
        <el-button size="mini" type="primary" @click="addTeacherCourse()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :title="dialogTitle"
      style="padding: 0px;text-align: left;"
      :close-on-click-modal="false"
      :visible.sync="totalGradeDialogVisible"
      width="700px"
    >
    <div style="margin-top:20px;padding-bottom:20px;">

      <div style="float:right;margin-bottom:15px;">
        <el-button
          type="success"
          icon="el-icon-refresh"
          size="small"
          @click="computeAllGrade()"
        >更新总成绩</el-button>
      </div>

      <el-table
        :data="finalGrades"
        v-loading="gradeTableLoading"
        border
        resizable
        stripe
        @selection-change="handleSelectionChange"
        size="medium"
        style="width: 100%;"
      >
        <el-table-column type="index" align="center" label=" " width="38"></el-table-column>

        <el-table-column type="selection" align="center" width="40"></el-table-column>

        <el-table-column
          prop="student.username"
          align="left"
          label="学生用户名"
          show-overflow-tooltip
          width
        ></el-table-column>
        <el-table-column prop="student.name" width align="left" show-overflow-tooltip label="学生姓名"></el-table-column>
        <el-table-column
          prop="finalGrades.regular_grade"
          width
          align="left"
          show-overflow-tooltip
          label="平时成绩"
        >
          <template slot-scope="scope">
            <span
              v-if="scope.row.regular_grade >= 80"
              style="font-weight: bold;color: green"
            >{{scope.row.regular_grade}}</span>
            <span
              v-else-if="scope.row.regular_grade < 60"
              style="font-weight: bold;color: red"
            >{{scope.row.regular_grade}}</span>
            <span v-else style="font-weight: bold;color: yellowgreen;">{{scope.row.regular_grade}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="final_grade" width align="left" show-overflow-tooltip label="期末成绩">
          <template slot-scope="scope">
            <span
              v-if="scope.row.final_grade >= 80"
              style="font-weight: bold;color: green"
            >{{scope.row.final_grade}}</span>
            <span
              v-else-if="scope.row.final_grade < 60"
              style="font-weight: bold;color: red"
            >{{scope.row.final_grade}}</span>
            <span v-else style="font-weight: bold;color: yellowgreen;">{{scope.row.final_grade}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="total_grade" width align="left" show-overflow-tooltip label="总成绩">
          <template slot-scope="scope">
            <span
              v-if="scope.row.total_grade >= 80"
              style="font-weight: bold;color: green"
            >{{scope.row.total_grade}}</span>
            <span
              v-else-if="scope.row.total_grade < 60"
              style="font-weight: bold;color: red"
            >{{scope.row.total_grade}}</span>
            <span v-else style="font-weight: bold;color: yellowgreen;">{{scope.row.total_grade}}</span>
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作" align="center" width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
              type="warning"
              icon="el-icon-edit"
              size="mini"
              style="padding: 3px 4px 3px 4px;margin: 2px"
              @click="showEditTotalGradeDialog(scope.row)"
            >修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    </el-dialog>

    <el-dialog
      :title="dialogTitleEdit"
      style="padding: 0px;text-align: left;"
      :close-on-click-modal="false"
      :visible.sync="editTotalGradeDialogVisible"
      width="360px"
    >
      <el-form :model="finalGrade" style="margin-top: 40px;padding: 0px;text-align: center;">
        <el-form-item>
          <span style="font-size:17px;">平时成绩：</span>
          <el-input-number
            v-model="finalGrade.regular_grade"
            :step="0.5"
            :min="0"
            size="small"
            :max="100"
            step-strictly
          ></el-input-number>
        </el-form-item>
        <el-form-item>
          <span style="font-size:17px;">期末成绩：</span>
          <el-input-number
            v-model="finalGrade.final_grade"
            :step="0.5"
            :min="0"
            size="small"
            :max="100"
            step-strictly
          ></el-input-number>
        </el-form-item>
        <el-form-item>
          <span style="font-size:17px;">总成绩：</span>
          <el-input-number
            v-model="finalGrade.total_grade"
            :step="0.5"
            :min="0"
            size="small"
            :max="100"
            step-strictly
          ></el-input-number>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="updateTotalGrade()">确 定</el-button>
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
      dialogTitleEdit: "",
      dialogVisible: false,
      tableLoading: false,
      gradeTableLoading: false,
      totalGradeDialogVisible: false,
      editTotalGradeDialogVisible: false,

      teacherCourses: [],
      courses: [],
      teachers: [],
      finalGrades: [],

      teachersData: [],
      coursesData: [],
      chosenTeacher: [],
      chosenCourse: [],

      finalGrade: {
        user_course_id: '',
        regular_grade: "",
        final_grade: "",
        total_grade: ""
      },
      myRow: ""
    };
  },
  mounted: function() {
    this.loadteacherCourses();
    this.generateTeachersData();
    this.generateCoursesData();
  },
  methods: {
    loadteacherCourses() {
      var _this = this;
      this.tableLoading = true;
      this.getRequest(
        "/office/teacher/course?page=" +
          this.currentPage +
          "&size=" +
          this.pageSize +
          "&keywords=" +
          this.keywords
      ).then(resp => {
        this.tableLoading = false;
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          _this.teacherCourses = data.teacherCourses;
          _this.totalCount = data.count;
        }
      });
    },
    generateTeachersData() {
      this.loadTeachers();
      var data = [];
      this.teachers.forEach((item, index) => {
        data.push({
          label: item.name + "(" + item.username + ")",
          key: item.id
        });
      });
      this.teachersData = data;
    },
    generateCoursesData() {
      this.loadCourses();
      var data = [];
      this.courses.forEach((item, index) => {
        data.push({
          label: item.name + "(" + item.course_id + ")",
          key: item.id
        });
      });
      this.coursesData = data;
    },
    loadCourses() {
      var _this = this;
      this.getRequest("/config/course").then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.courses = data;
        }
      });
    },
    loadTeachers() {
      var _this = this;
      this.getRequest("/office/user/teacher").then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          _this.teachers = data;
        }
      });
    },
    showTeacherCourseView() {
      this.generateTeachersData();
      this.generateCoursesData();
      this.dialogTitle = "分配课程";
      this.dialogVisible = true;
    },
    addTeacherCourse() {
      var _this = this;
      this.tableLoading = true;
      //添加
      this.postRequest("/office/teacher/ins", {
        user_ids: this.chosenTeacher,
        course_ids: this.chosenCourse
      }).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          _this.dialogVisible = false;
          _this.emptyTeacherCourseData();
          _this.loadteacherCourses();
        }
      });
      _this.tableLoading = false;
    },
    loadAllGrade(row) {
      // alert(JSON.stringify(row));
      this.myRow = row;
      this.dialogTitle =
        "管理 " +
        row.course.name +
        "(" +
        row.teacher.name +
        ")" +
        " 的成绩";
      this.totalGradeDialogVisible = true;
      this.gradeTableLoading = true;
      this.getRequest(
        "/office/teacher/getTotalGrade?teacher_course_id=" +
          row.id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          this.finalGrades = data;
          this.gradeTableLoading = false;
        }
      });
    },
    computeAllGrade() {
      // alert(JSON.stringify(this.myRow));
      // alert(this.teacher_course_id);
      this.putRequest("/office/teacher/computeAllGrade" , {teacher_course_id:this.myRow.id,user_id:this.myRow.user_id}).then(resp => {
            if (resp && resp.status == 200) {
              this.loadAllGrade(this.myRow);
            }
          });
    },
    showEditTotalGradeDialog(row) {
      this.finalGrade.user_course_id = row.user_course_id;
      this.dialogTitleEdit =
        "为 " +
        row.student.name +
        "(" +
        row.student.username +
        ")" +
        " 修改总成绩";
      this.finalGrade.regular_grade = row.regular_grade;
      this.finalGrade.final_grade = row.final_grade;
      this.finalGrade.total_grade = row.total_grade;
      this.editTotalGradeDialogVisible = true;
    },
    updateTotalGrade() {
      //更新
      this.putRequest("/office/teacher/Grade/", this.finalGrade).then(resp => {
        if (resp && resp.status == 200) {
          this.editTotalGradeDialogVisible = false;
          this.loadAllGrade(this.myRow);
        }
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteManyteacherCourses() {
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
    deleteTeacherCourse(row) {
      // alert(JSON.stringify(row));
      this.$confirm(
        "此操作将永久删除[" +
          row.teacher.name +
          "的" +
          row.course.name +
          "], 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.doDelete(row.id);
        })
        .catch(() => {});
    },
    doDelete(ids) {
      this.tableLoading = true;
      var _this = this;
      this.deleteRequest("/office/teacher/course/" + ids).then(resp => {
        _this.tableLoading = false;
        if (resp && resp.status == 200) {
          _this.currentPage = 1;
          _this.loadteacherCourses();
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
        this.loadteacherCourses();
      }
    },
    searchTeacherCourse() {
      this.loadteacherCourses();
    },
    handleCurrentChange(currentChange) {
      this.currentPage = currentChange;
      this.loadteacherCourses();
    },
    handleSizeChange(sizeChange) {
      this.pageSize = sizeChange;
      this.currentPage = 1;
      this.loadteacherCourses();
    },
    cancelEidt() {
      this.dialogVisible = false;
      this.emptyTeacherCourseData();
    },
    handleDialogClose(done) {
      this.emptyTeacherCourseData();
      done();
    },
    emptyTeacherCourseData() {
      this.chosenTeacher = [];
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
  width: 300px;
}
</style>
