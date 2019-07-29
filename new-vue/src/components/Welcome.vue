<template>
  <div>
    <div style="margin-top:50px;">
      <!-- <span class="demonstration"></span> -->
      <el-carousel 
        arrow="always" 
        trigger="click"
        interval="1500"
        indicator-position="outside"
        width="960px"
        height="320px">
        <el-carousel-item v-for="item in imagesbox" :key="item.id">
          <img :src="item.idView" fit="contain">
        </el-carousel-item>
      </el-carousel>
    </div>
    <!-- <span class="home_title_1">2019年精品在线开放课程</span>
          <el-row>
            <el-col :span="4" v-for="(o, index) in 4" :key="o" :offset="index > 0 ? 2 : 0">
              <el-card :body-style="{ padding: '0px' }">
                <img src="" class="image">
                <div style="padding: 14px;">
                  <span>精品课程</span>
                  <div class="bottom clearfix">
                    <time class="time">{{ currentDate }}</time>
                    <el-button type="text" class="button">进入课程</el-button>
                  </div>
                </div>
              </el-card>
            </el-col>
    </el-row>-->
  </div>
</template>

<script>
export default {
  data() {
    return {
      imagesbox: [
        { id: 0, idView: require("../static/p1.jpg") },
        { id: 1, idView: require("../static/p2.jpg") },
        { id: 2, idView: require("../static/p3.jpg") },
      ]
    };
  }
};
</script>

<style>
</style>
