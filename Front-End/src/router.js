import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";

import Dashboard from "@/pages/Dashboard.vue";
import Login from "@/pages/Login.vue";
import ClassManagement from "@/pages/ClassManagement.vue";
import UserManagement from "@/pages/UserManagement.vue";
import TeacherManagement from "@/pages/TeacherManagement.vue";
import Icons from "@/pages/Icons.vue";
import Notifications from "@/pages/Notifications.vue";
import UserProfile from "@/pages/UserProfile.vue";
import TableList from "@/pages/TableList.vue";
import Typography from "@/pages/Typography.vue";
import NotFound from "@/pages/404.vue";
import Forbidden from "@/pages/403.vue";
import CONFIG from "./config/index";
import { checkLogin,checkLoginViewReport } from "./api/processLogin";
import Declaration from "@/pages/Declaration.vue";
import MoveDeclaration from "@/pages/MoveDeclaration.vue";
import HealthReport from "@/pages/HealthReportManagement.vue";

const router = [
  {
    path: "/",
    component: DashboardLayout,
    redirect: "/home-page",
    children: [
      {
        path: "home-page",
        name: "Trang chủ",
        component: UserProfile
      },
      {
        path: "declare",
        name: "Declaration",
        component: Declaration
      },
      {
        path: "move-declare",
        name: "MoveDeclaration",
        component: MoveDeclaration
      },
      {
        path: "login",
        name: "Dăng nhập",
        component: Login
      },
      {
        path: "dashboard",
        name: "Dashboard",
        component: Dashboard
      },
      {
        path: "forbidden",
        name: "Forbidden",
        component: Forbidden
      },
      {
        path: "class-management",
        name: "Quản lý lớp học",
        component: ClassManagement,
        beforeEnter: async (to, from, next) => {
          await checkLogin(next, CONFIG.SCREEN_CODE.CLASS_MANAGEMENT);
        },
      },
      {
        path: "user-management",
        name: "Quản lý học sinh",
        component: UserManagement,
        beforeEnter: async (to, from, next) => {
          await checkLogin(next, CONFIG.SCREEN_CODE.USER_MANAGEMENT);
        },
      },
      {
        path: "teacher-management",
        name: "Quản lý giáo viên",
        component: TeacherManagement
      },
      {
        path: "icons",
        name: "Icons",
        component: Icons
      },
      {
        path: "notifications",
        name: "Notifications",
        component: Notifications
      },
      {
        path: "table",
        name: "Table List",
        component: TableList
      },
      {
        path: "typography",
        name: "Typography",
        component: Typography
      },
      {
        path: "health-report",
        name: "HealthReport",
        component: HealthReport,
        beforeEnter: async (to, from, next) => {
          await checkLoginViewReport(next);
        },
      },
      // {
      //   path: ":catchAll(.*)",
      //   name: "NotFound",
      //   component: NotFound
      // },
      {
        path: "*",
        name: "NotFound",
        component: NotFound
      }
    ]
  }
];

export default router;
