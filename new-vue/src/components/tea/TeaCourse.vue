<template>
  <div style="margin-top: 10px" v-loading="fullloading">
    <!-- <div style="margin-bottom: 10px;display: flex;justify-content: center;align-items: center">
      <el-input
        placeholder="默认展示部分用户，可以通过用户名搜索更多用户..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px"
        v-model="keywords"
      ></el-input>

      <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
    </div>-->

    <div style="display: flex;justify-content: space-around;flex-wrap: wrap;text-align: left">
      <el-card
        style="width: 350px;margin-top: 20px"
        v-for="(item,index) in courses"
        :key="item.id"
        v-loading="cardLoading[index]"
      >
        <div slot="header" class="clearfix">
          <span class="course-header">《{{item.course.name}}》</span>
        </div>

        <div>
          <div style="margin-bottom: 5px;">
            <span class="course-info">课程代码:</span>
            <span style="font-weight: bold;">{{item.course.course_id}}</span>
          </div>
          <div style="margin-bottom: 5px;">
            <span class="course-info">考核方式:</span>
            <span style="font-weight: bold;">
              <span v-if="item.course.isExam=='1'">考试</span>
              <span v-else-if="item.course.isExam=='0'">考查</span>
            </span>
          </div>
          <div>
            <span class="course-info">平时成绩占比:</span>
            <span style="font-weight: bold;">{{item.course.proportion*100}}%</span>
          </div>
        </div>
        <div style="float:right;margin-bottom: 20px;">
          <el-button type="primary" @click="showDialog(item)">进入课程</el-button>
        </div>
      </el-card>
    </div>

    <el-dialog
      style="padding: 0px;text-align: left;"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible"
      width="80%"
    >
      <el-tabs v-model="activeTabsNames" style="font-size: 20px;" @tab-click="handleClick">
        <el-tab-pane name="info">
          <span slot="label" class="tab-title">
            <i class="el-icon-info"></i> 课程信息
          </span>
          <el-form :model="course" style="margin: 0px;padding: 0px;">
            <el-form-item label="课程代码:" prop="course_id">
              <el-input readonly="true" v-model.trim="course.course_id" type="text"></el-input>
            </el-form-item>

            <el-form-item label="课程名称:" prop="name">
              <el-input readonly="true" v-model.trim="course.name" type="text"></el-input>
            </el-form-item>

            <el-form-item label="考核方式:" prop="isExam">
              <el-switch
                style="margin-top:50px;margin-left:-72px;"
                v-model="course.isExam"
                disabled="true"
                active-text="考试"
                inactive-text="考查"
                :active-value="1"
                :inactive-value="0"
              ></el-switch>
            </el-form-item>

            <el-form-item label="平时成绩占比:" prop="proportion">
              <el-slider
                style="margin-top:50px;"
                disabled="true"
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
                readonly="true"
                autosize="true"
                type="textarea"
                placeholder="请输入课程简介"
              ></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>

        <el-tab-pane name="setting">
          <span slot="label" class="tab-title">
            <i class="el-icon-s-tools"></i> 课程设置
          </span>
          <div style="margin-bottom:80px;">
            <div v-loading="alloLoading">
              <span style="font-size:25px;margin-top:10px;font-weight: bold;">平时成绩分配</span>
              <div v-for="(value,key) in types" :key="key" style="margin:30px;">
                {{ value.name }} {{allocations[key] * 100}}%
                <el-slider
                  style="margin-top: 20px;"
                  v-model="allocations[key]"
                  :step="0.05"
                  max="1"
                  show-input
                  show-stops
                ></el-slider>
              </div>
            </div>

            <el-button
              style="float:right;margin-right:30px;"
              size="small"
              type="primary"
              @click="changeAllocation()"
            >确 定</el-button>
          </div>
        </el-tab-pane>

        <el-tab-pane name="student">
          <span slot="label" class="tab-title">
            <i class="el-icon-user-solid"></i> 学生管理
          </span>

          <el-table
            :data="students"
            v-loading="tableLoading"
            border
            resizable
            stripe
            @selection-change="handleSelectionChange"
            size="medium"
            style="width: 100%;margin-bottom:15px;"
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
            <el-table-column
              prop="student.name"
              width
              align="left"
              show-overflow-tooltip
              label="学生姓名"
            ></el-table-column>

            <el-table-column
              fixed="right"
              label="操作"
              align="center"
              width="240"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  style="padding: 5px 5px 5px 5px;margin: 1px"
                  @click="showAddGradeDialog(scope.row)"
                >添加平时成绩</el-button>
                <el-button
                  type="success"
                  icon="el-icon-plus"
                  size="mini"
                  style="padding: 5px 5px 5px 5px;margin: 1px"
                  :disabled="isExamDisabled"
                  @click="showAddFinalGradeDialog(scope.row)"
                >添加期末成绩</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane name="grade">
          <span slot="label" class="tab-title">
            <i class="el-icon-s-data"></i>平时成绩
          </span>
          <el-table
            :data="grades"
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
              prop="student.username"
              align="left"
              label="学生用户名"
              show-overflow-tooltip
              width
            ></el-table-column>
            <el-table-column
              prop="student.name"
              width
              align="left"
              show-overflow-tooltip
              label="学生姓名"
            ></el-table-column>
            <el-table-column
              prop="gradeType.name"
              width
              align="left"
              show-overflow-tooltip
              label="成绩类型"
            >
              <template slot-scope="scope">第{{scope.row.number}}次{{scope.row.gradeType.name}}</template>
            </el-table-column>
            <el-table-column prop="grade" width align="left" show-overflow-tooltip label="成绩">
              <template slot-scope="scope">
                <span
                  v-if="scope.row.grade >= 80"
                  style="font-weight: bold;color: green"
                >{{scope.row.grade}}</span>
                <span
                  v-else-if="scope.row.grade < 60"
                  style="font-weight: bold;color: red"
                >{{scope.row.grade}}</span>
                <span v-else style="font-weight: bold;color: yellowgreen;">{{scope.row.grade}}</span>
              </template>
            </el-table-column>

            <el-table-column
              fixed="right"
              label="操作"
              align="center"
              width="150"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-button
                  type="warning"
                  icon="el-icon-edit"
                  size="mini"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  @click="showEditGradeDialog(scope.row)"
                >修改</el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  @click="deleteGrade(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <el-button
            type="danger"
            size="small"
            icon="el-icon-delete"
            style="margin-top: 12px;margin-bottom:12px;"
            v-if="grades.length>0"
            :disabled="multipleSelection.length==0"
            @click="deleteManyGrades"
          >批量删除</el-button>
        </el-tab-pane>

        <el-tab-pane name="all_grade">
          <span slot="label" class="tab-title">
            <i class="el-icon-data-analysis"></i>总成绩
          </span>

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
            v-loading="tableLoading"
            border
            resizable
            stripe
            @selection-change="handleSelectionChange"
            size="medium"
            style="width: 100%;margin-bottom:15px;"
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
            <el-table-column
              prop="student.name"
              width
              align="left"
              show-overflow-tooltip
              label="学生姓名"
            ></el-table-column>
            <el-table-column 
              prop="finalGrades.regular_grade" 
              width 
              align="left" 
              show-overflow-tooltip 
              label="平时成绩">
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
            <el-table-column 
              prop="final_grade" 
              width 
              align="left" 
              show-overflow-tooltip 
              label="期末成绩">
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
            <el-table-column 
              prop="total_grade" 
              width 
              align="left" 
              show-overflow-tooltip 
              label="总成绩">
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

            <el-table-column
              fixed="right"
              label="操作"
              align="center"
              width="100"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-button
                  type="warning"
                  icon="el-icon-edit"
                  size="mini"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  @click="showEditTotalGradeDialog(scope.row)"
                >修改</el-button>
                <!-- <el-button
                  type="danger"
                  icon="el-icon-delete"
                  size="mini"
                  style="padding: 3px 4px 3px 4px;margin: 2px"
                  @click="deleteTotalGrade(scope.row)"
                >删除</el-button> -->
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <el-dialog
      :title="dialogTitle"
      style="padding: 0px;text-align: left;"
      :close-on-click-modal="false"
      :visible.sync="gradeDialogVisible"
      width="360px"
    >
      <el-form :model="grade" style="margin-top: 20px;padding: 0px;text-align: center;">
        <el-form-item>
          <el-select v-model="grade.type_id" placeholder="请选择成绩类型">
            <el-option
              v-for="(item,index) in types"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="isTypeDisabled[index]"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <span style="font-size:17px;">序号：</span>
          <el-input-number v-model="grade.number" :step="1" :min="1" size="small" step-strictly></el-input-number>
        </el-form-item>

        <el-form-item>
          <span style="font-size:17px;">成绩：</span>
          <el-input-number
            v-model="grade.grade"
            :step="0.5"
            :min="0"
            size="small"
            :max="100"
            step-strictly
          ></el-input-number>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="addGrade()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :title="dialogTitle"
      style="padding: 0px;text-align: left;"
      :close-on-click-modal="false"
      :visible.sync="finalGradeDialogVisible"
      width="360px"
    >
      <el-form :model="finalGrade" style="margin-top: 40px;padding: 0px;text-align: center;">
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
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="addFinalGrade()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :title="dialogTitle"
      style="padding: 0px;text-align: left;"
      :close-on-click-modal="false"
      :visible.sync="totalGradeDialogVisible"
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
      fullloading: false,
      cardLoading: [],
      tableLoading: false,
      alloLoading: false,

      dialogVisible: false,
      gradeDialogVisible: false,
      finalGradeDialogVisible: false,
      totalGradeDialogVisible: false,

      dialogTitle: "",
      multipleSelection: [],

      user_id: "",
      teacher_course_id: "",
      student_course_id: "",
      courses: [],
      students: [],
      grades: [],
      finalGrades: [],
      allocations: [],
      types: [],
      isTypeDisabled: [],
      isExamDisabled: true,

      course: {
        course_id: "",
        name: "",
        isExam: "",
        proportion: "",
        introduction: ""
      },
      grade: {
        id: "",
        number: "",
        type_id: "",
        grade: ""
      },
      finalGrade: {
        user_course_id: '',
        regular_grade: "",
        final_grade: "",
        total_grade: ""
      },
      activeTabsNames: "info"
    };
  },
  computed: {
    user() {
      return this.$store.state.user;
    }
  },
  mounted: function() {
    this.initCards();
  },
  methods: {
    showDialog(item) {
      this.activeTabsNames = "info";
      this.teacher_course_id = item.id;
      this.course = item.course;
      if (this.course.isExam == 1) {
        this.isExamDisabled = false;
      } else if (this.course.isExam == 0) {
        this.isExamDisabled = true;
      }
      this.loadTypes();
      this.loadAllocations();
      this.loadStudent();
      this.loadGrade();
      this.loadAllGrade();
      this.dialogVisible = true;
    },
    initCards() {
      this.fullloading = true;
      var _this = this;
      // alert(JSON.stringify(this.user));
      this.user_id = this.user.id;
      // alert("card"+this.user_id);
      this.getRequest("/teacher/course/course?id=" + this.user_id).then(
        resp => {
          if (resp && resp.status == 200) {
            var data = resp.data;
            // alert(JSON.stringify(data));
            _this.courses = data;
            this.fullloading = false;
          }
        }
      );
    },
    loadTypes() {
      this.alloLoading = true;
      this.getRequest("/config/gradeType").then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          this.types = data;
          this.alloLoading = false;
        }
      });
    },
    loadAllocations() {
      this.alloLoading = true;
      this.getRequest(
        "/teacher/course/getAllocation?teacher_course_id=" +
          this.teacher_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          for (let i = 0; i < this.types.length; i++) {
            this.allocations[i] = 0;
            this.isTypeDisabled[i] = true;
            for (let j = 0; j < data.length; j++) {
              if (data[j].type_id - 1 == i) {
                this.allocations[i] = data[j].allocation;
                this.isTypeDisabled[i] = false;
                break;
              }
            }
          }
          this.alloLoading = false;
        }
      });
    },
    loadStudent() {
      this.tableLoading = true;
      this.getRequest(
        "/teacher/course/student?id=" + this.teacher_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          this.students = data;
          this.tableLoading = false;
        }
      });
    },
    loadGrade() {
      this.tableLoading = true;
      this.getRequest(
        "/teacher/course/getRegularGrade?teacher_course_id=" +
          this.teacher_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          this.grades = data;
          this.tableLoading = false;
        }
      });
    },
    loadAllGrade() {
      this.tableLoading = true;
      this.getRequest(
        "/teacher/course/getTotalGrade?teacher_course_id=" +
          this.teacher_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          this.finalGrades = data;
          this.tableLoading = false;
        }
      });
    },
    changeAllocation() {
      var sum = 0;
      for (let index = 0; index < this.allocations.length; index++) {
        sum = sum + Number(this.allocations[index]);
      }
      if (sum == 1) {
        // alert(this.allocations + this.teacher_course_id);
        this.putRequest("/teacher/course/allocation", {
          teacher_course_id: this.teacher_course_id,
          allocations: this.allocations
        }).then(resp => {
          if (resp && resp.status == 200) {
            this.loadAllocations();
          }
        });
      } else {
        this.$message({
          type: "error",
          message: "比例之和不为1，分配失败！"
        });
      }
    },
    showAddGradeDialog(row) {
      // alert(JSON.stringify(row));
      this.student_course_id = row.id;
      this.dialogTitle =
        "为 " +
        row.student.name +
        "(" +
        row.student.username +
        ")" +
        " 添加平时成绩";
      this.grade = {
        id: "",
        number: "",
        type_id: "",
        grade: ""
      };
      this.gradeDialogVisible = true;
    },
    showAddFinalGradeDialog(row) {
      // alert(JSON.stringify(row));
      this.student_course_id = row.id;
      this.dialogTitle =
        "为 " +
        row.student.name +
        "(" +
        row.student.username +
        ")" +
        " 添加期末成绩";
      this.finalGrade.final_grade = "";
      this.finalGradeDialogVisible = true;
    },
    showEditTotalGradeDialog(row) {
      // alert(JSON.stringify(row));
      this.finalGrade.user_course_id = row.user_course_id;
      this.dialogTitle =
        "为 " +
        row.student.name +
        "(" +
        row.student.username +
        ")" +
        " 修改总成绩";
      this.finalGrade.regular_grade = row.regular_grade;
      this.finalGrade.final_grade = row.final_grade;
      this.finalGrade.total_grade = row.total_grade;
      this.totalGradeDialogVisible = true;
    },
    showEditGradeDialog(row) {
      // alert(JSON.stringify(row));
      this.dialogTitle =
        "修改 " +
        row.student.name +
        "(" +
        row.student.username +
        ")" +
        " 的成绩";
      this.grade = {
        id: row.id,
        number: row.number,
        type_id: row.type_id,
        grade: row.grade
      };
      this.gradeDialogVisible = true;
    },
    addGrade() {
      // alert(JSON.stringify(this.grade));
      if (this.grade.type_id == "") {
        this.$message({
          type: "error",
          message: "清选择成绩类型！"
        });
      } else {
        if (this.grade.id) {
          //更新
          this.putRequest("/teacher/course/RegularGrade/", this.grade).then(
            resp => {
              if (resp && resp.status == 200) {
                this.gradeDialogVisible = false;
                this.loadGrade();
              }
            }
          );
        } else {
          //添加
          this.postRequest("/teacher/course/addRegularGrade", {
            user_course_id: this.student_course_id,
            number: this.grade.number,
            grade: this.grade.grade,
            type_id: this.grade.type_id
          }).then(resp => {
            if (resp && resp.status == 200) {
              this.gradeDialogVisible = false;
            }
          });
        }
      }
    },
    addFinalGrade() {
      //添加
      this.postRequest("/teacher/course/addFinalGrade", {
        user_course_id: this.student_course_id,
        final_grade: this.finalGrade.final_grade
      }).then(resp => {
        if (resp && resp.status == 200) {
          this.finalGradeDialogVisible = false;
        }
      });
    },
    updateTotalGrade() {
      //更新
      this.putRequest("/teacher/course/Grade/", this.finalGrade).then(resp => {
        if (resp && resp.status == 200) {
          this.totalGradeDialogVisible = false;
          this.loadAllGrade();
        }
      });
    },
    computeAllGrade() {
      // alert(this.teacher_course_id);
      this.putRequest("/teacher/course/computeAllGrade" , {teacher_course_id:this.teacher_course_id,user_id:this.user_id}).then(resp => {
            if (resp && resp.status == 200) {
              this.loadAllGrade();
            }
          });
    },
    handleClick(tab, event) {
      // console.log(tab,event);
      if (tab.name == "setting") {
        // this.loadTypes();
        this.loadAllocations();
      } else if (tab.name == "student") {
        this.loadStudent();
      } else if (tab.name == "grade") {
        this.loadGrade();
      } else if (tab.name == "all_grade") {
        this.loadAllGrade();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteManyGrades() {
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
    deleteGrade(row) {
      this.$confirm(
        "此操作将永久删除[" +
          row.student.name +
          " 的 第" +
          row.number +
          "次" +
          row.gradeType.name +
          " 的成绩" +
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
      this.deleteRequest("/teacher/course/RegularGrade/" + ids).then(resp => {
        if (resp && resp.status == 200) {
          this.loadGrade();
        } else {
          this.$message({
            type: "error",
            message: "删除的数据与别的表有关联，删除失败！"
          });
        }
      });
    }
  }
};
</script>

<style>
.course-header {
  font-size: 20px;
  font-weight: bold;
}
.course-info {
  font-size: 18px;
  color: #09c0f6;
}
.tab-title {
  font-size: 20px;
}
</style>
