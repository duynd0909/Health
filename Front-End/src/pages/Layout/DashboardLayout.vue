<template>
  <div class="wrapper">
    <!-- <div class="wrapper" :class="{'nav-open': $sidebar.showSidebar}"> -->

    <side-bar class="side-bar-custom" v-if="checkToken">
      <!-- <mobile-menu slot="content"></mobile-menu> -->
      <sidebar-link to="/home-page">
        <i class="tim-icons icon-single-02"></i>
        <template>
          <p>Trang chủ</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/dashboard">
        <i class="tim-icons icon-chart-pie-36"></i>
        <template>
          <p>Dashboard</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/class-management">
        <i class="tim-icons icon-bank"></i>
        <template>
          <p>Quản lý lớp học</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/user-management">
        <i class="tim-icons icon-single-02"></i>
        <template>
          <p>Quản lý học sinh</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/teacher-management">
        <i class="tim-icons icon-single-02"></i>
        <template>
          <p>Quản lý giáo viên</p>
        </template>
      </sidebar-link>
      <sidebar-link to="/health-report">
        <a-icon type="snippets" />
        <template>
          <p>Quản lý khai báo</p>
        </template>
      </sidebar-link>
    </side-bar>
    <sidebar-share :background-color.sync="backgroundColor"></sidebar-share>

    <div class="main-panel" :data="backgroundColor">
      <top-navbar></top-navbar>

      <dashboard-content @click.native="toggleSidebar"></dashboard-content>

      <content-footer></content-footer>
    </div>
  </div>
</template>
<style lang="scss"></style>

<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./DashboardContent.vue";
import SidebarShare from "./SidebarSharePlugin.vue";
import SideBar from "@/components/SidebarPlugin/SideBar.vue";
import SidebarLink from "@/components/SidebarPlugin/SidebarLink.vue";
import { verifyToken } from "../../api/processLogin.js";

export default {
  components: {
    TopNavbar,
    DashboardContent,
    ContentFooter,
    SideBar,
    SidebarLink,
    SidebarShare,
  },
  data() {
    return {
      backgroundColor: "green",
    };
  },
  computed: {
    checkToken() {
      if (this.$cookies.get("accessToken")) {
        return true;
      }
      return false;
    },
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    },
  },
};
</script>

<style>
.sidebar-wrapper {
  background-color: rgb(82 187 100) !important;
}
</style>
