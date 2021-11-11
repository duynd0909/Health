<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <template slot="header">
          <h4 class="card-title">Student Management</h4>
        </template>

        <div class="header-filter-custom text-left">
          <a-form>
            <a-row type="flex" justify="space-between">
              <a-col class="gutter-box" :span="4">
                <a-form-item label="Lớp" has-feedback>
                  <a-select
                    v-model="formDataSearch.classID"
                    class="filter-select"
                    :disabled="checkShowClass"
                    placeholder="~Chọn lớp học~"
                    style="width: 100%"
                    @search="fetchClass"
                    @change="searchUser"
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
                  <span v-if="teacherInfo && teacherInfo.fullName">
                    {{ teacherInfo.fullName }}
                  </span>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" :span="4">
                <a-form-item label="Sĩ số" has-feedback>
                  <span v-if="totals">
                    {{ totals }}
                  </span>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box custom-button-header" :span="4">
                <base-button type="primary" @click="openAddForm()"
                  >Thêm học sinh</base-button
                >
              </a-col>
            </a-row>
          </a-form>
        </div>
        <div class="text-left">
          <a-spin :spinning="loading">
            <a-icon
              type="loading"
              slot="indicator"
              style="font-size: 24px"
              spin
            />
            <a-table
              :data-source="data"
              :columns="columns"
              :scroll="{ x: 1500 }"
              :pagination="false"
            >
              <div
                slot="filterDropdown"
                slot-scope="{
                  setSelectedKeys,
                  selectedKeys,
                  confirm,
                  clearFilters,
                  column,
                }"
                style="padding: 8px"
              >
                <a-input
                  v-ant-ref="(c) => (searchInput = c)"
                  :value="selectedKeys[0]"
                  style="width: 188px; margin-bottom: 8px; display: block;"
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
              <template #action="item">
                <a-dropdown>
                  <a-menu slot="overlay">
                    <a-menu-item key="1" @click="handleEditItemBtnClick(item)">
                      Sửa
                    </a-menu-item>

                    <a-menu-item key="2">
                      <a-popconfirm
                        placement="leftBottom"
                        ok-text="Có"
                        cancel-text="Không"
                        @confirm="deleteSubItemBtnClick(item)"
                      >
                        <template slot="title">
                          <span
                            >Ban có chắc chắn muốn xóa học sinh này không?</span
                          ><br />
                        </template>
                        Xóa
                      </a-popconfirm>
                    </a-menu-item>

                    <a-menu-item
                      key="3"
                      @click="handleResetPasswordItemBtnClick(item)"
                    >
                      Đặt lại mât khấu
                    </a-menu-item>
                  </a-menu>
                  <a-button> <a-icon type="down" /> </a-button>
                </a-dropdown>
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
                  <span v-if="text">
                    Nam
                  </span>
                  <span v-else>
                    Nữ
                  </span>
                </template>
              </template>
              <template #dobCustom="item">
                <span>{{ generateTime(item.dob) }}</span>
              </template>
            </a-table>
          </a-spin>
        </div>
        <div class="gutter-example pt-md pagnigation-custom">
          <a-pagination
            v-model="current"
            show-quick-jumper
            :default-current="1"
            :total="totals"
            @change="paginate"
          />
        </div>

        <!-- edit modal -->
        <a-modal
          title="Chỉnh sửa thông tin học sinh"
          v-model="showModal.edit"
          :maskClosable="false"
          :destroyOnClose="true"
          :closable="false"
        >
          <template slot="footer">
            <a-button
              key="submit"
              type="primary"
              :loading="loadingModal"
              @click="saveEditUser"
            >
              Cập nhật
            </a-button>
            <a-button
              key="cancel"
              type="secondary"
              :disabled="loadingModal"
              @click="closeEditForm()"
            >
              Hủy
            </a-button>
          </template>

          <a-spin :spinning="loadingModal">
            <a-icon
              type="loading"
              slot="indicator"
              style="font-size: 24px"
              spin
            />
            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Họ và Tên
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input v-model="editForm.fullName" />
                <span v-if="errors.fullName" class="red">
                  {{ errors.fullName }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Giới tính
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-radio-group
                  v-model="editForm.gender"
                  name="radioGroup"
                  :default-value="1"
                >
                  <a-radio :value="1">
                    Male
                  </a-radio>
                  <a-radio :value="0">
                    Female
                  </a-radio>
                </a-radio-group>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Ngày sinh
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-date-picker v-model="editForm.dob">
                  <template slot="renderExtraFooter"> </template>
                </a-date-picker>
                <span v-if="errors.dob" class="red">
                  {{ errors.dob }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Số điện thoại
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input v-model="editForm.phoneNumber" />
                <span v-if="errors.phoneNumber" class="red">
                  {{ errors.phoneNumber }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Sđt người thân
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input v-model="editForm.parentPhoneNumber" />
                <span v-if="errors.parentPhoneNumber" class="red">
                  {{ errors.parentPhoneNumber }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Tỉnh
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="editForm.provinceCode"
                  class="filter-select"
                  style="width: 100%"
                  allow-clear
                  @search="fetchProvince"
                  @change="fetchDistrictForModal"
                >
                  <a-select-option
                    v-for="item in provinceList"
                    :key="item.code"
                    :value="item.code"
                  >
                    {{ item.name }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.provinceCode" class="red">
                  {{ errors.provinceCode }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Huyện
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="editForm.districtCode"
                  class="filter-select"
                  :disabled="districtList.length < 1"
                  style="width: 100%"
                  allow-clear
                  @search="fetchDistrictAfter"
                  @change="fetchWardForModal"
                >
                  <a-select-option
                    v-for="item in districtList"
                    :key="item.districtCode"
                    :value="item.districtCode"
                  >
                    {{ item.districtName }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.districtCode" class="red">
                  {{ errors.districtCode }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Xã
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="editForm.wardCode"
                  :disabled="wardList.length < 1"
                  allow-clear
                  class="filter-select"
                  style="width: 100%"
                  @search="fetchWardAfter"
                >
                  <a-select-option
                    v-for="item in wardList"
                    :key="item.wardCode"
                    :value="item.wardCode"
                  >
                    {{ item.wardName }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.wardCode" class="red">
                  {{ errors.wardCode }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Địa chỉ cụ thể
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-textarea
                  v-model="editForm.addressDetail"
                  :auto-size="{ minRows: 1, maxRows: 5 }"
                  :min="0"
                  class="full-width--i"
                />
                <span v-if="errors.addressDetail" class="red">
                  {{ errors.addressDetail }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Lớp
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="editForm.classID"
                  class="filter-select"
                  :disabled="checkShowClass"
                  style="width: 100%"
                  @search="fetchClass"
                >
                  <a-select-option
                    v-for="item in classList"
                    :key="item.id"
                    :value="item.id"
                  >
                    {{ item.className }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.classID" class="red">
                  {{ errors.classID }}
                </span>
              </a-col>
            </a-row>
          </a-spin>
        </a-modal>

        <!-- edit modal -->
        <a-modal
          title="Đổi mật khẩu"
          v-model="showModal.changePassword"
          :maskClosable="false"
          :destroyOnClose="true"
          :closable="false"
        >
          <template slot="footer">
            <a-button
              key="submit"
              type="primary"
              :loading="loadingModal"
              @click="changePasswordWithUsername"
            >
              Thay đổi
            </a-button>
            <a-button
              key="cancel"
              type="secondary"
              :disabled="loadingModal"
              @click="closeEditForm()"
            >
              Hủy
            </a-button>
          </template>

          <a-spin :spinning="loadingModal">
            <a-icon
              type="loading"
              slot="indicator"
              style="font-size: 24px"
              spin
            />
            <a-row :gutter="[24, 16]">
              <a-col :span="8">Tài khoản </a-col>
              <a-col :span="16">
                <a-input v-model="changePassForm.username" :disabled="true" />
              </a-col>
            </a-row>
            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Mật khẩu mới
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input-password v-model="changePassForm.passwordChanged" />
                <span v-if="errors.passwordChanged" class="red">
                  {{ errors.passwordChanged }}
                </span>
              </a-col>
            </a-row>
            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Nhập lại
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input-password v-model="changePassForm.rePasswordChanged" />
                <span v-if="errors.rePasswordChanged" class="red">
                  {{ errors.rePasswordChanged }}
                </span>
              </a-col>
            </a-row>
          </a-spin>
        </a-modal>

        <!-- Add modal -->
        <a-modal
          title="Thêm mới học sinh"
          v-model="showModal.add"
          :maskClosable="false"
          :destroyOnClose="true"
          :closable="false"
        >
          <template slot="footer">
            <a-button
              key="submit"
              type="primary"
              :loading="loadingModal"
              @click="addNewUser"
            >
              Thêm
            </a-button>
            <a-button
              key="cancel"
              type="secondary"
              :disabled="loadingModal"
              @click="closeModal()"
            >
              Hủy
            </a-button>
          </template>

          <a-spin :spinning="loadingModal">
            <a-icon
              type="loading"
              slot="indicator"
              style="font-size: 24px"
              spin
            />

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Họ và Tên
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input v-model="addForm.fullName" />
                <span v-if="errors.fullName" class="red">
                  {{ errors.fullName }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Giới tính
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-radio-group
                  v-model="addForm.gender"
                  name="radioGroup"
                  :default-value="1"
                >
                  <a-radio :value="1">
                    Male
                  </a-radio>
                  <a-radio :value="0">
                    Female
                  </a-radio>
                </a-radio-group>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Ngày sinh
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-date-picker v-model="addForm.dob">
                  <template slot="renderExtraFooter"> </template>
                </a-date-picker>
                <span v-if="errors.dob" class="red">
                  {{ errors.dob }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Số điện thoại
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input v-model="addForm.phoneNumber" />
                <span v-if="errors.phoneNumber" class="red">
                  {{ errors.phoneNumber }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Sđt người thân
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-input v-model="addForm.parentPhoneNumber" />
                <span v-if="errors.parentPhoneNumber" class="red">
                  {{ errors.parentPhoneNumber }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Tỉnh
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="addForm.provinceCode"
                  class="filter-select"
                  style="width: 100%"
                  @search="fetchProvince"
                  @change="fetchDistrict"
                >
                  <a-select-option
                    v-for="item in provinceList"
                    :key="item.code"
                    :value="item.code"
                  >
                    {{ item.name }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.provinceCode" class="red">
                  {{ errors.provinceCode }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Huyện
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="addForm.districtCode"
                  :disabled="districtList.length < 1"
                  class="filter-select"
                  style="width: 100%"
                  @search="fetchDistrictAfter"
                  @change="fetchWard"
                >
                  <a-select-option
                    v-for="item in districtList"
                    :key="item.districtCode"
                    :value="item.districtCode"
                  >
                    {{ item.districtName }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.districtCode" class="red">
                  {{ errors.districtCode }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Xã
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="addForm.wardCode"
                  :disabled="wardList.length < 1"
                  class="filter-select"
                  style="width: 100%"
                  @search="fetchWardAfter"
                >
                  <a-select-option
                    v-for="item in wardList"
                    :key="item.wardCode"
                    :value="item.wardCode"
                  >
                    {{ item.wardName }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.wardCode" class="red">
                  {{ errors.wardCode }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8"
                >Địa chỉ cụ thể
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-textarea
                  v-model="addForm.addressDetail"
                  :auto-size="{ minRows: 1, maxRows: 5 }"
                  :min="0"
                  class="full-width--i"
                />
                <span v-if="errors.addressDetail" class="red">
                  {{ errors.addressDetail }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Lớp
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="addForm.classID"
                  class="filter-select"
                  :disabled="checkShowClass"
                  style="width: 100%"
                  @search="fetchClass"
                >
                  <a-select-option
                    v-for="item in classList"
                    :key="item.id"
                    :value="item.id"
                  >
                    {{ item.className }}
                  </a-select-option>
                </a-select>
                <span v-if="errors.classID" class="red">
                  {{ errors.classID }}
                </span>
              </a-col>
            </a-row>
          </a-spin>
        </a-modal>
      </div>
    </div>
  </div>
</template>
<script>
import UserRepository from "../api/user.js";
import ClassRepository from "../api/class.js";
import LocationRepository from "../api/location.js";
import moment from "moment";

const defaultModalState = {
  add: false,
  edit: false,
  changePassword: false,
};

const defaultForm = {
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

const requiredError = "Không được để trống thông tin này!";

const defaultInputErrors = {
  fullName: "",
  gender: "",
  phoneNumber: "",
  parentPhoneNumber: "",
  passwordChanged: "",
  rePasswordChanged: "",
};

export default {
  data() {
    return {
      userInfor: {
        username: "",
        fullName: "",
        roleCode: "",
        classID: undefined,
      },
      teacherInfo: "",
      checkShowClass: false,
      data: [],
      loadingModal: false,
      classList: [],
      provinceList: [],
      districtList: [],
      wardList: [],
      studentList: [],
      current: 1,
      totals: 0,
      changePassForm: {
        username: "",
        passwordChanged: "",
        rePasswordChanged: "",
      },
      loading: false,
      formDataSearch: {
        fullName: "",
        genderSearch: "",
        wardName: "",
        districtName: "",
        provinceName: "",
        userName: "",
        classID: undefined,
      },
      provinceCodeSearch: undefined,
      districtCodeSearch: undefined,
      showModal: { ...defaultModalState },
      editForm: { ...defaultForm },
      addForm: { ...defaultForm },
      errors: { ...defaultInputErrors },
      selectedItem: null,
      searchText: "",
      searchInput: null,
      searchedColumn: "",
      columns: [
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
            record.fullName
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
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
            record.gender
              .toString()
              .toLowerCase()
              .includes(value.toLowerCase()),
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
            record.userName
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
            record.wardName
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
      ],
    };
  },
  created() {
    this.searchUser();
    this.fetchProvince("");
    this.fetchClass("");
    this.getUserInfor();
  },
  methods: {
    cleaderLocation() {
      this.districtList = [];
      this.wardList = [];
    },
    getUserInfor() {
      var username = this.$cookies.get("username");
      var roleCode = this.$cookies.get("role");
      UserRepository.getUserByUsername(username).then((res) => {
        if (res.data.data) {
          var classOfTeacher =
            res.data.data.classID == null || res.data.data.classID == ""
              ? undefined
              : res.data.data.classID;
          this.userInfor = {
            username: res.data.data.username,
            fullName: res.data.data.fullName,
            roleCode: res.data.data.roleCode,
            classID: classOfTeacher,
          };
          this.checkShowClassMethod(this.userInfor);
        }
      });
    },
    checkShowClassMethod(userInfor) {
      var parsedobj = JSON.parse(JSON.stringify(userInfor));
      if (
        parsedobj.roleCode === "HIEU_TRUONG" ||
        parsedobj.roleCode === "HIEU_PHO"
      ) {
        this.checkShowClass = false;
      } else {
        this.checkShowClass = true;
      }
    },
    checkContainSearchKey(fragment, searchText) {
      if (
        this.removeAccents(fragment).toLowerCase() ===
        this.removeAccents(searchText).toLowerCase()
      ) {
        return true;
      }
      return false;
    },
    removeAccents(str) {
      var AccentsMap = [
        "aàảãáạăằẳẵắặâầẩẫấậ",
        "AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬ",
        "dđ",
        "DĐ",
        "eèẻẽéẹêềểễếệ",
        "EÈẺẼÉẸÊỀỂỄẾỆ",
        "iìỉĩíị",
        "IÌỈĨÍỊ",
        "oòỏõóọôồổỗốộơờởỡớợ",
        "OÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢ",
        "uùủũúụưừửữứự",
        "UÙỦŨÚỤƯỪỬỮỨỰ",
        "yỳỷỹýỵ",
        "YỲỶỸÝỴ",
      ];
      for (var i = 0; i < AccentsMap.length; i++) {
        var re = new RegExp("[" + AccentsMap[i].substr(1) + "]", "g");
        var char = AccentsMap[i][0];
        str = str.replace(re, char);
      }
      return str;
    },
    generateTime(dateNumber) {
      const date = new Date(dateNumber);
      const m = moment(date);
      return m.isValid() ? m.format("DD-MM-YYYY") : "";
    },
    paginate(current = 1) {
      this.loading = true;
      this.current = current;

      UserRepository.searchUser(this.formDataSearch, this.current).then(
        (res) => {
          this.data = res.data.data.items;
          this.totals = res.data.data.total;
          this.loading = false;
        }
      );
    },
    searchUser() {
      this.loading = true;
      if (this.formDataSearch.genderSearch === null) {
        this.data = [];
        this.totals = 0;
        this.loading = false;
        return;
      }
      if (
        this.userInfor &&
        this.userInfor.roleCode === "GIAO_VIEN_CHU_NHIEM" &&
        this.userInfor.classID
      ) {
        this.formDataSearch.classID = this.userInfor.classID;
      }
      if (this.formDataSearch.classID) {
        UserRepository.searchUser(this.formDataSearch, 1)
          .then((res) => {
            if (res.data.success) {
              this.data = res.data.data.items;
              this.totals = res.data.data.total;
              this.teacherInfo = res.data.data.teacher;
              this.current = 1;
              this.loading = false;
            } else {
              this.loading = false;
            }
          })
          .catch(() => {
            this.loading = false;
          });
      } else {
        this.loading = false;
      }
    },
    handleSearch(selectedKeys, dataIndex) {
      this.searchText = selectedKeys[0];
      this.searchedColumn = dataIndex;
      if (dataIndex === "fullName") {
        this.formDataSearch.fullName = selectedKeys[0];
      } else if (dataIndex === "gender") {
        var genderSearchKey = this.removeAccents(selectedKeys[0]);
        if (selectedKeys[0] == "Nam" || selectedKeys[0] == "nam") {
          this.formDataSearch.genderSearch = true;
        } else if (genderSearchKey === "Nu" || genderSearchKey === "nu") {
          this.formDataSearch.genderSearch = false;
        } else {
          this.formDataSearch.genderSearch = null;
        }
      } else if (dataIndex === "wardName") {
        this.formDataSearch.wardName = selectedKeys[0];
      } else if (dataIndex === "districtName") {
        this.formDataSearch.districtName = selectedKeys[0];
      } else if (dataIndex === "provinceName") {
        this.formDataSearch.provinceName = selectedKeys[0];
      } else if (dataIndex === "userName") {
        this.formDataSearch.userName = selectedKeys[0];
      }
      this.searchUser();
    },
    handleReset(dataIndex, clearFilters) {
      if (dataIndex === "fullName") {
        this.formDataSearch.fullName = "";
      } else if (dataIndex === "gender") {
        this.formDataSearch.genderSearch = "";
      } else if (dataIndex === "wardName") {
        this.formDataSearch.wardName = "";
      } else if (dataIndex === "districtName") {
        this.formDataSearch.districtName = "";
      } else if (dataIndex === "provinceName") {
        this.formDataSearch.provinceName = "";
      } else if (dataIndex === "userName") {
        this.formDataSearch.userName = "";
      }
      clearFilters();
      this.searchUser();
      this.searchText = "";
    },
    async handleEditItemBtnClick(item) {
      // this.fetchTeacher("");
      this.selectedItem = item;
      this.editForm = item;
      this.editForm.gender = item.gender === true ? 1 : 0;
      if (item.provinceCode) {
        this.fetchDistrict(item.provinceCode);
        if (item.districtCode) {
          this.fetchWard(item.districtCode);
        }
      }
      this.showModal = {
        edit: true,
      };
    },
    handleResetPasswordItemBtnClick(item) {
      // this.fetchTeacher("");
      this.selectedItem = item;
      console.log("item.username ==>", item);
      this.changePassForm.username = item.userName;
      this.showModal = {
        changePassword: true,
      };
    },
    validateChangePassword() {
      let isValid = true;
      this.errors = { ...defaultInputErrors };
      if (
        this.changePassForm.passwordChanged == "" ||
        this.changePassForm.passwordChanged == null ||
        this.changePassForm.passwordChanged == undefined
      ) {
        this.errors.passwordChanged = requiredError;
        isValid = false;
      }
      if (
        this.changePassForm.rePasswordChanged == "" ||
        this.changePassForm.rePasswordChanged == null ||
        this.changePassForm.rePasswordChanged == undefined
      ) {
        this.errors.rePasswordChanged = requiredError;
        isValid = false;
      }
      return isValid;
    },
    changePasswordWithUsername() {
      this.loadingModal = true;
      const validation = this.validateChangePassword();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      if (
        this.changePassForm.passwordChanged !==
        this.changePassForm.rePasswordChanged
      ) {
        this.errors.rePasswordChanged = "Mât khẩu không trùng khớp";
        this.loadingModal = false;
        return;
      }
      let changePasswordForm = {
        username: this.changePassForm.username,
        newPassword: this.changePassForm.passwordChanged,
        confirmPassword: this.changePassForm.rePasswordChanged,
      };
      console.log("changePasswordForm ===>", changePasswordForm);
      UserRepository.changePasswordByUsername(changePasswordForm).then(res => {
        if (res.data.success) {
          this.loadingModal = false;
          this.$notification.success({
            message: "Đặt lại mật khẩu thành công!"
          });
          this.closeModal();
          this.loadingModal = false;
        }
      });
    },
    closeEditForm() {
      this.selectedItem = null;
      this.closeModal();
      this.cleaderLocation();
    },
    closeModal() {
      this.showModal = { ...defaultModalState };
      this.errors = { ...defaultInputErrors };
      this.editForm = { ...defaultForm };
      this.addForm = { ...defaultForm };
      this.changePassForm = {
        username: "",
        passwordChanged: "",
        rePasswordChanged: ""
      };
      this.cleaderLocation();
    },
    openAddForm() {
      if (this.userInfor && this.userInfor.classID) {
        this.addForm.classID = this.formDataSearch.classID;
      }
      this.showModal = {
        add: true,
      };
    },
    validate() {
      let isValid = true;
      this.errors = { ...defaultInputErrors };
      if (this.editForm.fullName == "" || this.editForm.fullName == null) {
        this.errors.fullName = requiredError;
        isValid = false;
      }
      if (
        this.editForm.phoneNumber == "" ||
        this.editForm.phoneNumber == null
      ) {
        this.errors.phoneNumber = requiredError;
        isValid = false;
      } else {
        if (this.editForm.phoneNumber.match(/\d/g).length === 10) {
          // Phone number okela
        } else {
          this.errors.phoneNumber =
            "Số điện thoại phải có 10 số và bao gồm các số từ 0-9!";
          isValid = false;
        }
      }
      if (
        this.editForm.parentPhoneNumber == "" ||
        this.editForm.parentPhoneNumber == null
      ) {
        this.errors.parentPhoneNumber = requiredError;
        isValid = false;
      } else {
        if (this.editForm.parentPhoneNumber.match(/\d/g).length === 10) {
          // Phone number okela
        } else {
          this.errors.parentPhoneNumber =
            "Số điện thoại phải có 10 số và bao gồm các số từ 0-9!";
          isValid = false;
        }
      }
      if (
        this.editForm.provinceCode == "" ||
        this.editForm.provinceCode == null ||
        this.editForm.provinceCode == undefined
      ) {
        this.errors.provinceCode = requiredError;
        isValid = false;
      }
      if (
        this.editForm.districtCode == "" ||
        this.editForm.districtCode == null ||
        this.editForm.districtCode == undefined
      ) {
        this.errors.districtCode = requiredError;
        isValid = false;
      }
      if (
        this.editForm.wardCode == "" ||
        this.editForm.wardCode == null ||
        this.editForm.wardCode == undefined
      ) {
        this.errors.wardCode = requiredError;
        isValid = false;
      }
      if (
        this.editForm.addressDetail == "" ||
        this.editForm.addressDetail == null ||
        this.editForm.addressDetail == undefined
      ) {
        this.errors.addressDetail = requiredError;
        isValid = false;
      }
      if (
        this.editForm.classID == "" ||
        this.editForm.classID == null ||
        this.editForm.classID == undefined
      ) {
        this.errors.classID = requiredError;
        isValid = false;
      }
      return isValid;
    },
    validateAddNewStudent() {
      let isValid = true;
      this.errors = { ...defaultInputErrors };
      if (this.addForm.fullName == "" || this.addForm.fullName == null) {
        this.errors.fullName = requiredError;
        isValid = false;
      }
      if (this.addForm.phoneNumber == "" || this.addForm.phoneNumber == null) {
        this.errors.phoneNumber = requiredError;
        isValid = false;
      } else {
        if (this.addForm.phoneNumber.match(/\d/g).length === 10) {
          // Phone number okela
        } else {
          this.errors.phoneNumber =
            "Số điện thoại phải có 10 số và bao gồm các số từ 0-9!";
          isValid = false;
        }
      }
      if (
        this.addForm.parentPhoneNumber == "" ||
        this.addForm.parentPhoneNumber == null
      ) {
        this.errors.parentPhoneNumber = requiredError;
        isValid = false;
      } else {
        if (this.addForm.parentPhoneNumber.match(/\d/g).length === 10) {
          // Phone number okela
        } else {
          this.errors.parentPhoneNumber =
            "Số điện thoại phải có 10 số và bao gồm các số từ 0-9!";
          isValid = false;
        }
      }
      if (
        this.addForm.provinceCode == "" ||
        this.addForm.provinceCode == null ||
        this.addForm.provinceCode == "undefined" ||
        typeof this.addForm.provinceCode == undefined
      ) {
        this.errors.provinceCode = requiredError;
        isValid = false;
      }
      if (
        this.addForm.districtCode == "" ||
        this.addForm.districtCode == null ||
        this.addForm.districtCode == undefined
      ) {
        this.errors.districtCode = requiredError;
        isValid = false;
      }
      if (
        this.addForm.wardCode == "" ||
        this.addForm.wardCode == null ||
        this.addForm.wardCode == undefined
      ) {
        this.errors.wardCode = requiredError;
        isValid = false;
      }
      if (
        this.addForm.addressDetail == "" ||
        this.addForm.addressDetail == null ||
        this.addForm.addressDetail == undefined
      ) {
        this.errors.addressDetail = requiredError;
        isValid = false;
      }
      if (
        this.addForm.classID == "" ||
        this.addForm.classID == null ||
        this.addForm.classID == undefined
      ) {
        this.errors.classID = requiredError;
        isValid = false;
      }
      return isValid;
    },
    async fetchProvince(provinceName) {
      this.loadingModal = true;
      LocationRepository.getProvinceByName(provinceName).then((res) => {
        this.provinceList = res.data.data;
        this.loadingModal = false;
      });
    },
    fetchDistrict(value) {
      this.loadingModal = true;
      this.provinceCodeSearch = value;
      LocationRepository.getDistrictInProvince(
        this.provinceCodeSearch,
        ""
      ).then((res) => {
        this.districtList = res.data.data;
        this.loadingModal = false;
      });
    },
    fetchDistrictForModal(value) {
      this.loadingModal = true;
      this.provinceCodeSearch = value;
      LocationRepository.getDistrictInProvince(
        this.provinceCodeSearch,
        ""
      ).then((res) => {
        this.districtList = res.data.data;
        this.editForm.districtCode = undefined;
        this.addForm.districtCode = undefined;
        this.editForm.wardCode = undefined;
        this.addForm.wardCode = undefined;
        this.loadingModal = false;
      });
    },
    fetchDistrictAfter(districtName) {
      this.loadingModal = true;
      LocationRepository.getDistrictInProvince(
        this.provinceCodeSearch,
        districtName
      ).then((res) => {
        this.districtList = res.data.data;
        this.loadingModal = false;
      });
    },
    fetchWard(value) {
      this.loadingModal = true;
      this.districtCodeSearch = value;
      LocationRepository.getWardInDistrict(this.districtCodeSearch, "").then(
        (res) => {
          this.wardList = res.data.data;
          this.loadingModal = false;
        }
      );
    },
    fetchWardForModal(value) {
      this.loadingModal = true;
      this.districtCodeSearch = value;
      LocationRepository.getWardInDistrict(this.districtCodeSearch, "").then(
        (res) => {
          this.wardList = res.data.data;
          this.loadingModal = false;
          this.editForm.wardCode = undefined;
          this.addForm.wardCode = undefined;
        }
      );
    },
    fetchWardAfter(wardName) {
      this.loadingModal = true;
      LocationRepository.getWardInDistrict(
        this.districtCodeSearch,
        wardName
      ).then((res) => {
        this.wardList = res.data.data;
        this.loadingModal = false;
      });
    },
    fetchClass(className) {
      this.loadingModal = true;
      ClassRepository.searchClassByName(className).then((res) => {
        this.classList = res.data.data.items;
        this.loadingModal = false;
      });
    },
    addNewUser() {
      this.loadingModal = true;
      const validation = this.validateAddNewStudent();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      var formAddData = {
        fullName: this.addForm.fullName,
        dob: this.addForm.dob,
        gender: this.addForm.gender,
        phoneNumber: this.addForm.phoneNumber,
        parentPhoneNumber: this.addForm.parentPhoneNumber,
        provinceCode: this.addForm.provinceCode,
        districtCode: this.addForm.districtCode,
        wardCode: this.addForm.wardCode,
        addressDetail: this.addForm.addressDetail,
        classID: this.addForm.classID,
      };
      UserRepository.addNewStudent(formAddData)
        .then((response) => {
          if (response.data.success === true) {
            this.$notification.success({
              message: "Thêm mới học sinh thành công!",
            });
            this.paginate();
            this.closeModal();
            this.loadingModal = false;
          } else {
            this.$notification.error({
              message: "Thêm mới học sinh thất bại!",
            });
            this.loadingModal = false;
          }
        })
        .catch(() => {
          this.$notification.error({
            message: "Thêm mới học sinh thất bại!",
          });
          this.loadingModal = false;
        });
    },
    deleteSubItemBtnClick(item) {
      this.loading = true;
      this.selectedItem = item;
      UserRepository.deleteUser(this.selectedItem.id)
        .then((res) => {
          if (res.data.success === true) {
            this.$notification.success({
              message: "Xóa học sinh thành công!",
            });
            this.paginate();
          } else {
            this.$notification.error({
              message: "Xóa học sinh thất bại!",
            });
            this.loading = false;
          }
        })
        .catch((e) => {
          this.$notification.error({
            message: e.response.data.message,
          });
          this.loading = false;
        });
    },
    saveEditUser() {
      this.loadingModal = true;
      const validation = this.validate();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      const editForm = {
        username: this.editForm.userName,
        fullName: this.editForm.fullName,
        gender: this.editForm.gender,
        dob: this.editForm.dob,
        phoneNumber: this.editForm.phoneNumber,
        parentPhoneNumber: this.editForm.parentPhoneNumber,
        provinceCode: this.editForm.provinceCode,
        districtCode: this.editForm.districtCode,
        wardCode: this.editForm.wardCode,
        addressDetail: this.editForm.addressDetail,
        classID: this.editForm.classID,
      };
      UserRepository.updateUser(editForm)
        .then((response) => {
          if (response.data.success === true) {
            this.$notification.success({
              message: "Chỉnh sửa thành công!",
            });
            this.paginate();
            this.closeModal();
            this.loadingModal = false;
          } else {
            this.$notification.error({
              message: "Chỉnh sửa thất bại!",
            });
            this.loadingModal = false;
          }
        })
        .catch(() => {
          this.$notification.error({
            message: "Chỉnh sửa thất bại!",
          });
          this.loadingModal = false;
        });
    },
  },
};
</script>
<style scoped>
.highlight {
  background-color: none !important;
  padding: 0px;
}

.gutter-example {
  padding-bottom: 40px;
}

/* .pagnigation-custom {
  float: right;
} */
.button-add-custom {
  float: left;
}
.table-responsive-class {
  padding-top: 10px;
}
.ant-pagination {
  float: right;
}
/* .header-cus {
  float: left;
} */
.cus-header-class {
  margin: none !important;
  padding: none !important;
}
.header-filter-custom {
  width: 70%;
}
.custom-button-header {
  margin-top: 20px;
}
</style>
