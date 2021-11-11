<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <h4 class="card-title">Quản lý khai báo</h4>
          </template>

          <div class="table-responsive text-left">
            <div class="header-filter-custom text-left">
              <a-form>
                <a-row type="flex" justify="space-between">
                  <a-col class="gutter-box" :span="4">
                    <a-form-item label="Lớp" has-feedback>
                      <a-select
                        :disabled="userRole !== 'HIEU_TRUONG'"
                        v-model="searchForm.classID"
                        class="filter-select"
                        placeholder="~Chọn lớp học~"
                        style="width: 100%"
                        @search="fetchClass"
                        @change="submitForm"
                      >
                        <a-select-option
                          v-for="item in classList"
                          :key="item.id"
                          :value="item.id"
                        >
                          {{ item.className }}
                        </a-select-option>
                      </a-select>
                    </a-form-item>
                  </a-col>
                  <a-col class="gutter-box" :span="4">
                    <a-form-item label="Giáo viên chủ nhiệm" has-feedback>
                      <span v-if="teacherInfor && teacherInfor.teacherName">
                        {{ teacherInfor.teacherName }}
                      </span>
                    </a-form-item>
                  </a-col>
                  <a-col class="gutter-box" :span="4">
                    <a-form-item label="Tổng số học sinh" has-feedback>
                      <span v-if="total">
                        {{ total }}
                      </span>
                    </a-form-item>
                  </a-col>
                  <a-col
                    class="gutter-box custom-button-header"
                    :span="6"
                    v-if="userRole === 'HIEU_TRUONG'"
                  >
                    <div :style="{ borderBottom: '1px solid #E9E9E9' }">
                      <p>Chọn đối tượng được phép xem khai báo:</p>
                      <!-- <a-checkbox
                        :indeterminate="indeterminate"
                        :checked="checkAll"
                        @change="onCheckAllChange"
                      >
                        Check all
                      </a-checkbox> -->
                    </div>
                    <div class="allow-report">
                      <div class="row">
                        <div class="col-12">
                          <a-checkbox
                            :disabled="!teacherInfor.id"
                            v-model="allowViewForm.teacher"
                            @change="onChangeAllow('teacher')"
                          >
                            Giáo viên chủ nhiệm
                          </a-checkbox>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-12">
                          <a-checkbox
                            :disabled="!teacherInfor.id"
                            v-model="allowViewForm.student"
                            @change="
                              onChangeAllow('student'),
                                (allowViewForm.studentChecked = true)
                            "
                          >
                            Học sinh
                          </a-checkbox>
                        </div>
                      </div>
                    </div>
                  </a-col>
                </a-row>
              </a-form>
            </div>
            <a-table
              :columns="columns"
              :data-source="listUser"
              :pagination="false"
              :scroll="{ x: 1500, y: 800 }"
            >
              <div
                slot="filterDropdown"
                slot-scope="{
                  setSelectedKeys,
                  selectedKeys,
                  clearFilters,
                  column,
                }"
                style="padding: 8px"
              >
                <a-input
                  v-ant-ref="(c) => (searchInput = c)"
                  :value="selectedKeys[0]"
                  style="width: 188px; margin-bottom: 8px; display: block"
                  @change="
                    (e) =>
                      setSelectedKeys(e.target.value ? [e.target.value] : [])
                  "
                  @pressEnter="
                    () => handleSearch(selectedKeys, column.dataIndex)
                  "
                />
                <a-button
                  type="primary"
                  icon="search"
                  size="small"
                  style="width: 90px; margin-right: 8px"
                  @click="() => handleSearch(selectedKeys, column.dataIndex)"
                >
                  Tìm
                </a-button>
                <a-button
                  size="small"
                  style="width: 90px"
                  @click="() => handleReset(column.dataIndex, clearFilters)"
                >
                  Xóa
                </a-button>
              </div>
              <a-icon
                slot="filterIcon"
                slot-scope="filtered"
                type="search"
                :style="{ color: filtered ? '#108ee9' : undefined }"
              />
              <template
                slot="customRender"
                slot-scope="text, record, index, column"
              >
                <span v-if="searchText && searchedColumn === column.dataIndex">
                  <template
                    v-for="(fragment, i) in text
                      .toString()
                      .split(
                        new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i')
                      )"
                  >
                    <span
                      v-if="checkContainSearchKey(fragment, searchText)"
                      :key="i"
                      class="highlight"
                      >{{ fragment }}</span
                    >
                    <template v-else>{{ fragment }}</template>
                  </template>
                </span>
                <template v-else>
                  {{ text }}
                </template>
              </template>
              <template slot="gender" slot-scope="text, record, index, column">
                <span v-if="searchText && searchedColumn === column.dataIndex">
                  <template
                    v-for="(fragment, i) in text
                      .toString()
                      .split(
                        new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i')
                      )"
                  >
                    <span
                      v-if="'nam' === searchText.toLowerCase()"
                      :key="i"
                      class="highlight"
                    >
                      Nam
                    </span>
                    <!-- <template v-else>
                      Nam
                    </template> -->
                    <span
                      v-if="
                        'nu' === removeAccents(searchText).toLowerCase() &&
                        searchText.toLowerCase() !== 'nam'
                      "
                      :key="i"
                      class="highlight"
                    >
                      Nữ
                    </span>
                    <!-- <template v-else-if="searchText.toLowerCase() !== 'nam'">
                      Nữ
                    </template> -->
                  </template>
                </span>
                <template v-else>
                  <span v-if="text"> Nam </span>
                  <span v-else> Nữ </span>
                </template>
              </template>
              <template #dobCustom="item">
                <span>{{ generateTime(item.dob) }}</span>
              </template>
              <template #action="item">
                <a-dropdown>
                  <a>Tùy chọn</a>
                  <a-menu slot="overlay">
                    <a-menu-item @click="showModal(item)">
                      <a href="javascript:;">Lịch sử khai báo</a>
                    </a-menu-item>
                  </a-menu>
                </a-dropdown>
              </template>
            </a-table>
            <div class="gutter-example pt-md pagnigation-custom">
              <a-pagination
                v-model="current"
                show-quick-jumper
                :default-current="1"
                :total="total"
                @change="paginate"
              />
            </div>
          </div>
        </card>
      </div>
    </div>
    <a-modal
      v-model="visibleHealthReport"
      :title="`Danh sách khai báo : ${currentUser.fullName}`"
      :width="1080"
      :closable="false"
      on-ok="handleOk"
    >
      <template slot="footer">
        <a-button key="back" @click="handleCancelHealthReport">
          Trở lại
        </a-button>
      </template>
      <a-layout id="components-layout-demo-fixed-sider">
        <a-layout-sider
          :style="{
            overflow: 'auto',
            left: 0,
            background: '#fff',
            color: '000',
          }"
        >
          <div class="logo" />
          <a-menu :default-selected-keys="[0]" theme="light" mode="inline">
            <a-menu-item
              v-for="(item, index) in listHealthReport"
              :key="index"
              @click="onChangeSelectReport(item)"
            >
              <a-icon type="safety-certificate" />
              <span class="nav-text">{{
                formatDateTime(item.createdTime, "DD-MM-YYYY hh:mm:ss")
              }}</span>
            </a-menu-item>
          </a-menu>
        </a-layout-sider>
        <a-layout>
          <a-layout-content
            :style="{ margin: '24px 16px 0', overflow: 'initial' }"
          >
            <div
            v-if="currentHealthReport"
              :style="{
                padding: '24px',
                background: '#fff',
                textAlign: 'center',
              }"
            >
              <a-descriptions
                title="Chi tiết khai báo"
                layout="vertical"
                bordered
              >
                <a-descriptions-item label="Họ và tên">
                  {{ currentUser.fullName }}
                </a-descriptions-item>
                <a-descriptions-item label="Giới tính">
                  {{ formatGender(currentHealthReport.gender) }}
                </a-descriptions-item>
                <a-descriptions-item label="Ngày sinh">
                  {{
                    formatDateTime(
                      currentHealthReport.dateOfBirth,
                      "DD-MM-YYYY"
                    )
                  }}
                </a-descriptions-item>
                <a-descriptions-item label="Điện thoại">
                  {{ currentHealthReport.phoneNumber }}
                </a-descriptions-item>
                <a-descriptions-item label="Số thẻ bảo hiểm y tế">
                  {{ currentHealthReport.healthInsuranceId }}
                </a-descriptions-item>
                <a-descriptions-item label="Email">
                  {{ currentHealthReport.email }}
                  <!-- <a-badge status="processing" text="Running" /> -->
                </a-descriptions-item>
                <a-descriptions-item label="Tỉnh thành">
                  {{ currentHealthReport.provinceName }}
                </a-descriptions-item>
                <a-descriptions-item label="Quận huyện">
                  {{ currentHealthReport.districtName }}
                </a-descriptions-item>
                <a-descriptions-item label="Phường xã">
                  {{ currentHealthReport.wardName }}
                </a-descriptions-item>
                <a-descriptions-item
                  label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                  :span="3"
                >
                  {{ currentHealthReport.addressDetail }}
                </a-descriptions-item>
                <a-descriptions-item
                  label="Trong vòng 14 ngày qua, Anh/Chị có đến khu vực tỉnh thành phố, quốc gia/vùng lãnh thổ nào (Có thể đi nhiều nơi)"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.contactToPlace) }}
                </a-descriptions-item>
                <a-descriptions-item
                  label="Trong vòng 14 ngày qua, Anh/Chị có thấy xuất hiện ít nhất  trong các dấu hiệu: sốt, ho, khó thở, viêm phổi, đau họng, mệt mỏi, thay đổi vị giác không?"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.sicking) }}
                </a-descriptions-item>
                <a-descriptions-item
                  label="Trong vòng 14 ngày qua, Anh/Chị có tiếp xúc với người bệnh hoặc nghi ngờ mắc bệnh Covid-19"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.closeToRiskingPeople) }}
                </a-descriptions-item>
                <a-descriptions-item
                  label="Trong vòng 14 ngày qua, Anh/Chị có tiếp xúc với người từ nước có bệnh Covid-19"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.closeToCountry) }}
                </a-descriptions-item>
                <a-descriptions-item
                  label="Trong vòng 14 ngày qua, Anh/Chị có tiếp xúc với người bệnh có biểu hiện sốt, ho, khó, thở, viêm phổi"
                  :span="3"
                >
                  {{ formatYesNo(currentHealthReport.contactToPlace) }}
                </a-descriptions-item>
              </a-descriptions>
            </div>
          </a-layout-content>
          <a-layout-footer :style="{ textAlign: 'center' }">
            Ant Design ©2018 Created by Ant UED
          </a-layout-footer>
        </a-layout>
      </a-layout>
    </a-modal>
  </div>
</template>
<script>
import { Card } from "@/components/index";
import HealthReportRepository from "../api/healthReport";
import ClassRepository from "../api/class.js";

import moment from "moment";
const columns = [
  {
    title: "Stt",
    dataIndex: "index",
    width: 80,
    key: "index",
  },
  {
    title: "Chức vụ",
    dataIndex: "roleName",
    width: 100,
    key: "roleName",
  },
  {
    title: "Họ và Tên",
    dataIndex: "fullName",
    width: 200,
    key: "fullName",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.fullName.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        }, 0);
      }
    },
  },
  {
    title: "Lớp",
    dataIndex: "className",
    width: 100,
    key: "className",
  },
  {
    title: "Giới tính",
    dataIndex: "gender",
    width: 140,
    key: "gender",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "gender",
    },
    onFilter: (value, record) =>
      record.gender.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        }, 0);
      }
    },
  },
  {
    title: "Ngày sinh",
    width: 150,
    key: "dob",
    scopedSlots: {
      customRender: "dobCustom",
    },
  },
  {
    title: "Tài khoản",
    dataIndex: "userName",
    width: 150,
    key: "userName",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.userName.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "Địa chỉ chi tiết",
    dataIndex: "addressDetail",
    width: 200,
    key: "addressDetail",
  },
  {
    title: "Phường / Xã",
    dataIndex: "wardName",
    width: 150,
    key: "wardName",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.wardName.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "Quận / Huyện",
    dataIndex: "districtName",
    width: 150,
    key: "districtName",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.districtName
        .toString()
        .toLowerCase()
        .includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "Tỉnh thành",
    dataIndex: "provinceName",
    width: 150,
    key: "provinceName",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.provinceName
        .toString()
        .toLowerCase()
        .includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "Số điện thoại",
    dataIndex: "phoneNumber",
    width: 150,
    key: "phoneNumber",
  },
  {
    title: "Sđt nguời thân",
    dataIndex: "parentPhoneNumber",
    width: 150,
    key: "parentPhoneNumber",
  },
  {
    title: "Tùy chọn",
    key: "action",
    fixed: "right",
    width: 100,
    scopedSlots: { customRender: "action" },
  },
];
const columnsHealthReport = [];
const defaultSearchForm = {
  id: undefined,
  fullName: "",
  dob: "2000-01-01",
  gender: 1,
  phoneNumber: "",
  parentPhoneNumber: "",
  provinceCode: undefined,
  districtCode: undefined,
  wardCode: undefined,
  addressDetail: "",
  classID: undefined,
};
const defaultUpdateAllowViewReportForm = {
  userId: undefined,
  teacherChecked: false,
  studentChecked: false,
  teacher: false,
  student: false,
  classId: undefined,
};
export default {
  components: {
    Card,
  },
  data() {
    return {
      columns: columns,
      searchForm: { ...defaultSearchForm },
      allowViewForm: { ...defaultUpdateAllowViewReportForm },
      listHealthReport: [],
      listUser: [],
      classList: [],
      current: 1,
      total: 0,
      totalHealthReport: 0,
      loading: false,
      visibleHealthReport: false,
      currentUser: {},
      currentHealthReport: null,
      columnsHealthReport: columnsHealthReport,
      searchText: "",
      teacherInfor: {},
      userRole: this.$cookies.get("role"),
    };
  },
  created() {
    if (this.userRole !== "HIEU_TRUONG") {
      this.searchForm.classID = Number(this.$cookies.get("class"));
      this.getListUser()
    }
    // this.getListUser();
    this.fetchClass("");
  },
  methods: {
    onChangeAllow(type) {
      if (type === "student") {
        this.allowViewForm.studentChecked = true;
      }
      if (type === "teacher") {
        this.allowViewForm.teacherChecked = true;
      }
      this.allowViewForm.userId = this.teacherInfor.teacherID;
      this.allowViewForm.classId = this.teacherInfor.id;
      this.updateAllowViewReport();
    },
    updateAllowViewReport() {
      HealthReportRepository.updateAllowViewReport(this.allowViewForm)
        .then((res) => {
          this.$notifications.error({
            message: "Cấp quyền thành công !",
          });
          this.allowViewForm = { ...defaultUpdateAllowViewReportForm };
          this.getListUser();
        })
        .catch((err) => {
          this.$notifications.error({
            message: "Cấp quyền thất bại !",
          });
        });
    },
    submitForm(e) {
      this.teacherInfor = this.classList.filter((cl) => cl.id === e)[0];
      this.allowViewForm.teacher = this.teacherInfor.allowViewReport;
      this.searchForm.classID = e;
      this.getListUser();
      console.log(this.teacherInfor);
    },
    generateTime(dateNumber) {
      const date = new Date(dateNumber);
      const m = moment(date);
      return m.isValid() ? m.format("DD-MM-YYYY") : "";
    },
    fetchClass(className) {
      ClassRepository.searchClassByName(className).then((res) => {
        this.classList = res.data.data.items;
      });
    },
    onChangeSelectReport(item) {
      this.currentHealthReport = item;
    },
    formatYesNo(item) {
      if (item) {
        return "Có";
      }
      return "Không";
    },
    formatGender(gender) {
      if (gender) {
        return "Nam";
      }
      return "Nữ";
    },
    formatDateTime(date, formatter) {
      return moment(date).format(formatter);
    },
    paginate(current = 1) {
      this.loading = true;
      this.current = current;
      this.getListUser();
    },
    getListUser() {
      HealthReportRepository.searchUserReport(
        this.searchForm,
        this.current
      ).then((res) => {
        console.log(res, "res");
        this.listUser = res.data.items;
        this.total = res.data.total;
        this.allowViewForm.student = this.listUser[0].allowViewReport;
      });
    },
    getListReportByUsername(username) {
      HealthReportRepository.getReportsByUsername(username).then((res) => {
        this.listHealthReport = res.data.data.items;
        if (this.listHealthReport && this.listHealthReport.length > 0) {
          this.currentHealthReport = this.listHealthReport[0];
        }
      });
    },
    showModal(item) {
      this.visibleHealthReport = true;
      this.currentUser = { ...item };
      this.getListReportByUsername(item.userName);
    },
    handleOk(e) {
      this.loading = true;
      setTimeout(() => {
        this.visibleHealthReport = false;
        this.loading = false;
      }, 3000);
    },
    handleCancelHealthReport(e) {
      this.visibleHealthReport = false;
      this.currentHealthReport = {};
      this.listHealthReport = [];
    },
  },
};
</script>
<style lang="scss" scoped>
.allow-report {
  padding-top: 3px;
  padding-left: 10px;
}
</style>
