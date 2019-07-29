<template>
  <div style="margin-top: 10px" v-loading="fullloading">
    <div style="display: flex;justify-content: space-around;flex-wrap: wrap;text-align: left">
      <el-card
        style="width: 350px;margin-top: 20px"
        v-for="(item,index) in courses"
        :key="item.id"
        v-loading="cardLoading[index]"
      >
        <div slot="header" class="clearfix">
          <span class="course-header">{{item.teacherCourse.course.name}}</span>
          <div
            style="font-weight: bold;float:right;margin-top:15px;"
          >{{item.teacherCourse.teacher.name}}</div>
        </div>

        <div>
          <div style="margin-bottom: 5px;">
            <span class="course-info">课程代码:</span>
            <span style="font-weight: bold;">{{item.teacherCourse.course.course_id}}</span>
          </div>
          <div style="margin-bottom: 5px;">
            <span class="course-info">考核方式:</span>
            <span style="font-weight: bold;">
              <span v-if="item.teacherCourse.course.isExam=='1'">考试</span>
              <span v-else-if="item.teacherCourse.course.isExam=='0'">考查</span>
            </span>
          </div>
          <div>
            <span class="course-info">平时成绩占比:</span>
            <span style="font-weight: bold;">{{item.teacherCourse.course.proportion*100}}%</span>
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
      width="50%"
    >
      <el-tabs v-model="activeTabsNames" style="font-size: 20px;" @tab-click="handleClick">
        <el-tab-pane name="info">
          <span slot="label" class="tab-title">
            <i class="el-icon-info"></i> 课程信息
          </span>

          <el-collapse v-model="activeNames">
            <el-collapse-item name="basic">
              <template slot="title">
                <span class="el-icon-document" style="font-size: 18px;">基本信息</span>
              </template>
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
            </el-collapse-item>

            <el-collapse-item name="allo">
              <template slot="title">
                <span class="el-icon-s-operation" style="font-size: 18px;">平时成绩分配</span>
              </template>
              <div v-loading="alloLoading">
                <div
                  v-for="(value,key) in allocations"
                  :key="key"
                  style="margin:30px;font-size: 18px;"
                >
                  {{ types[value.type_id - 1].name }} {{ value.allocation * 100}}%
                  <el-slider
                    style="margin-top: 20px;"
                    v-model="value.allocation"
                    :step="0.05"
                    disabled="true"
                    max="1"
                    show-input
                    show-stops
                  ></el-slider>
                </div>
              </div>
            </el-collapse-item>
          </el-collapse>
        </el-tab-pane>

        <el-tab-pane name="regular_grade">
          <span slot="label" class="tab-title">
            <i class="el-icon-s-data"></i> 平时成绩
          </span>

          <div style="float:right;margin-bottom:15px;">
            <el-button
              type="success"
              icon="el-icon-data-line"
              size="small"
              @click="showGradeDialog()"
            >分析成绩</el-button>
          </div>

          <el-table
            :data="regular_grades"
            v-loading="tableLoading"
            style="width: 100%;margin-bottom:20px;"
            border
            resizable
            stripe
            @selection-change="handleSelectionChange"
            size="medium"
          >
            <el-table-column type="index" align="center" label=" " width="38"></el-table-column>

            <el-table-column type="selection" align="center" width="40"></el-table-column>
            <el-table-column
              prop="gradeType.name"
              width
              align="center"
              show-overflow-tooltip
              label="成绩类型"
            >
              <template slot-scope="scope">第{{scope.row.number}}次{{scope.row.gradeType.name}}</template>
            </el-table-column>
            <el-table-column prop="grade" width align="center" show-overflow-tooltip label="成绩">
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
          </el-table>
        </el-tab-pane>

        <el-tab-pane name="all_grade">
          <span slot="label" class="tab-title">
            <i class="el-icon-data-analysis"></i>总成绩
          </span>

          <el-table
            :data="all_grades"
            v-loading="tableLoading"
            border
            resizable
            stripe
            @selection-change="handleSelectionChange"
            size="medium"
            style="width: 100%;margin-bottom:20px;"
          >
            <el-table-column type="index" align="center" label=" " width="38"></el-table-column>

            <el-table-column type="selection" align="center" width="40"></el-table-column>

            <el-table-column
              prop="regular_grade"
              width
              align="center"
              show-overflow-tooltip
              label="总平时成绩"
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
                <span
                  v-else
                  style="font-weight: bold;color: yellowgreen;"
                >{{scope.row.regular_grade}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="final_grade"
              width
              align="center"
              show-overflow-tooltip
              label="期末成绩"
            >
              <template slot-scope="scope">
                <span
                  v-if="scope.row.final_grade >= 80"
                  style="font-weight: bold;color: green"
                >{{scope.row.final_grade}}</span>
                <span
                  v-else-if="scope.row.final_grade < 60"
                  style="font-weight: bold;color: red"
                >{{scope.row.final_grade}}</span>
                <span
                  v-else
                  style="font-weight: bold;color: yellowgreen;"
                >{{scope.row.grafinal_gradede}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="total_grade"
              width
              align="center"
              show-overflow-tooltip
              label="总成绩"
            >
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
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <el-dialog
      style="padding: 0px;text-align: left;margin-top:-50px;"
      :close-on-click-modal="false"
      :visible.sync="gradeDialogVisible"
      width="1200px"
    >
      <div id="myChart" style="width:1200px;height:700px"></div>
    </el-dialog>
  </div>
</template>

<script>
let echarts = require("echarts/lib/echarts");
require("echarts/lib/chart/line");
require("echarts/lib/component/tooltip");
// require("echarts/lib/component/toolbox");
require("echarts/lib/component/title");
require("echarts/lib/component/legend");
export default {
  data() {
    return {
      fullloading: false,
      tableLoading: false,
      cardLoading: [],
      alloLoading: false,
      dialogVisible: false,
      dialogTitle: "",
      gradeDialogVisible: false,

      user_id: "",
      user_course_id: "",
      teacher_course_id: "",
      allocations: [],
      types: [],
      courses: [],
      regular_grades: [],
      average_regular_grades: [],
      all_grades: [],
      grade: [],
      course: {
        course_id: "",
        name: "",
        isExam: "",
        proportion: "",
        introduction: ""
      },
      activeTabsNames: "info",
      activeNames: ["basic"]
    };
  },
  computed: {
    user() {
      return this.$store.state.user;
    }
  },
  mounted: function() {
    this.initCards();
    this.loadTypes();
  },
  methods: {
    initCards() {
      this.fullloading = true;
      // alert(JSON.stringify(this.user));
      this.user_id = this.user.id;
      // alert("card"+this.user_id);
      this.getRequest("/student/course/course?id=" + this.user_id).then(
        resp => {
          if (resp && resp.status == 200) {
            var data = resp.data;
            this.courses = data;
            // alert(JSON.stringify(data));
            this.fullloading = false;
          }
        }
      );
    },
    showDialog(item) {
      // alert(JSON.stringify(item));
      this.activeTabsNames = "info";
      this.user_course_id = item.id;
      this.teacher_course_id = item.teacher_course_id;
      this.course = item.teacherCourse.course;
      this.loadGrade();
      this.loadAllocations();
      this.loadAllGrade();
      this.loadAvgGrade();
      this.dialogVisible = true;
    },
    showGradeDialog() {
      this.loadAvgGrade();
      this.gradeDialogVisible = true;
      this.$nextTick(() => {
        this.drawLine();
      });
    },
    drawLine() {
      // alert(JSON.stringify(this.average_regular_grades));
      var chartGrade = [];
      var chartName = [];
      var chartAvgGrade = [];
      for (let index = 0; index < this.regular_grades.length; index++) {
        chartGrade[index] = this.regular_grades[index].grade;
        chartName[index] =
          "第" +
          this.regular_grades[index].number +
          "次" +
          this.regular_grades[index].gradeType.name;
        // alert(chartGrade[index]);
        // alert(chartName[index]);
        chartAvgGrade[index] = this.average_regular_grades[index].grade;
      }
      let myChart = echarts.init(document.getElementById("myChart"));
      // this.myChart.showLoading();
      myChart.setOption({
        // textStyle: {
        //   fontSize: 18
        // },
        title: {
          text: "成绩分析"
        },
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["我的成绩", "平均成绩"]
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          axisLabel: {
            textStyle: {
              show: true,
              fontSize: 18
            }
          },
          data: chartName
        },
        yAxis: {
          axisLabel: {
            textStyle: {
              show: true,
              fontSize: 18
            }
          }
        },
        series: [
          {
            name: "我的成绩",
            type: "line",
            color: "#00B5FF",
            symbolSize: 10,
            lineStyle: {
              width: 5
            },
            data: chartGrade
          },
          {
            name: "平均成绩",
            type: "line",
            color: "#7ED321",
            symbolSize: 10,
            lineStyle: {
              width: 5
            },
            data: chartAvgGrade
          }
        ]
      });
      // this.myChart.hideLoading();
    },
    handleClick(tab, event) {
      // console.log(tab,event);
      if (tab.name == "regular_grade") {
        this.loadGrade();
      } else if (tab.name == "all_grade") {
        this.loadAllGrade();
      }
    },
    loadAllocations() {
      this.alloLoading = true;
      this.getRequest(
        "/student/course/getAllocation?teacher_course_id=" +
          this.teacher_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          // alert(JSON.stringify(data));
          this.allocations = data;
          this.alloLoading = false;
        }
      });
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
    loadAllGrade() {
      this.tableLoading = true;
      this.getRequest(
        "/student/course/allgrade?user_course_id=" + this.user_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          //  alert(JSON.stringify(data));
          this.all_grades = data;
          this.tableLoading = false;
        }
      });
    },
    loadGrade() {
      this.tableLoading = true;
      this.getRequest(
        "/student/course/grade?user_course_id=" + this.user_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          //  alert(JSON.stringify(data));
          this.regular_grades = data;
          this.tableLoading = false;
        }
      });
    },
    loadAvgGrade() {
      this.getRequest(
        "/student/course/getAverageGrade?user_course_id=" + this.user_course_id
      ).then(resp => {
        if (resp && resp.status == 200) {
          var data = resp.data;
          //  alert(JSON.stringify(data));
          this.average_regular_grades = data;
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
