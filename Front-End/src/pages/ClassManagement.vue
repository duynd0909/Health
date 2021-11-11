<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <template slot="header">
          <h4 class="card-title">Class Management</h4>
        </template>

        <!-- <div class="container cus-header-class">
          <div class="row justify-content-between">
            <div class="col-2 button-add-custom">
              <base-button type="primary" @click="openAddForm()"
                >Thêm lớp học</base-button
              >
            </div>
            <div class="col-6"></div>
          </div>
        </div> -->
        <div class="header-filter-custom text-left">
          <a-form>
            <a-row type="flex" justify="space-between">
              <a-col class="gutter-box custom-button-header" :span="4">
                <base-button type="primary" @click="openAddForm()"
                  >Thêm lớp học</base-button
                >
              </a-col>
            </a-row>
          </a-form>
        </div>
        <div class="table-responsive-class text-left">
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
              :scroll="{ x: 500 }"
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
                    <mark
                      v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                      :key="i"
                      class="highlight"
                      >{{ fragment }}</mark
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
                        ok-text="Yes"
                        cancel-text="No"
                        @confirm="deleteSubItemBtnClick(item)"
                      >
                        <template slot="title">
                          <span
                            >Ban có chắc chắn muốn xóa lớp học này không?</span
                          ><br />
                        </template>
                        Xóa
                      </a-popconfirm>
                    </a-menu-item>
                  </a-menu>
                  <a-button> <a-icon type="down" /> </a-button>
                </a-dropdown>
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
          title="Chỉnh sửa lớp học"
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
              @click="saveEditClass"
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
                >Tên lớp
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-textarea
                  v-model="editForm.className"
                  :auto-size="{ minRows: 1, maxRows: 5 }"
                  :min="0"
                  class="full-width--i"
                />
                <span v-if="errors.className" class="red">
                  {{ errors.className }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Giáo viên chủ nhiệm
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="editForm.teacherUsername"
                  class="filter-select"
                  show-search
                  placeholder="~Chọn~"
                  allow-clear
                  style="width: 100%"
                  @search="fetchTeacherFree"
                >
                  <a-select-option
                    v-for="item in teacherList"
                    :key="item.userName"
                    :value="item.userName"
                  >
                    {{ item.fullName }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-spin>
        </a-modal>

        <!-- Add modal -->
        <a-modal
          title="Thêm mới lớp học"
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
              @click="addNewClass"
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
                >Tên lớp
                <span class="red">*</span>
              </a-col>
              <a-col :span="16">
                <a-textarea
                  v-model="addForm.className"
                  :auto-size="{ minRows: 1, maxRows: 5 }"
                  :min="0"
                  class="full-width--i"
                />
                <span v-if="errors.className" class="red">
                  {{ errors.className }}
                </span>
              </a-col>
            </a-row>

            <a-row :gutter="[24, 16]">
              <a-col :span="8">
                Giáo viên chủ nhiệm
              </a-col>
              <a-col :span="16">
                <a-select
                  v-model="addForm.teacherUsername"
                  class="filter-select"
                  style="width: 100%"
                  show-search
                  allow-clear
                  placeholder="~Chọn~"
                  @search="fetchTeacherFree"
                >
                  <a-select-option
                    v-for="item in teacherList"
                    :key="item.userName"
                    :value="item.userName"
                  >
                    {{ item.fullName }}
                  </a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-spin>
        </a-modal>
      </div>
    </div>
  </div>
</template>
<script>
import ClassRepository from "../api/class.js";
import UserRepository from "../api/user.js";

const defaultModalState = {
  add: false,
  edit: false,
};

const defaultForm = {
  id: undefined,
  className: "",
  teacherUsername: undefined,
};

const requiredError = "This field can't blank";

const defaultInputErrors = {
  className: "",
};

export default {
  data() {
    return {
      data: [],
      teacherList: [],
      current: 1,
      totals: 0,
      loading: false,
      loadingModal: false,
      formDataSearch: {
        className: "",
        teacherName: "",
      },
      showModal: { ...defaultModalState },
      editForm: { ...defaultForm },
      addForm: { ...defaultForm },
      errors: { ...defaultInputErrors },
      selectedItem: null,
      searchText: "",
      searchInput: null,
      teacherOfClassSelected: null,
      searchedColumn: "",
      statusList: [
        {
          key: "Hoạt động",
          value: false,
        },
        {
          key: "Ngừng hoạt động",
          value: true,
        },
      ],
      columns: [
        {
          title: "Stt",
          dataIndex: "index",
          width: 50,
          key: "index",
        },
        {
          title: "Tên lớp",
          dataIndex: "className",
          width: 100,
          key: "className",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.name
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
          title: "Tài khoản GVCN",
          dataIndex: "teacherAccount",
          width: 100,
          key: "teacherAccount",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.address
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
          title: "Tên GVCN",
          dataIndex: "teacherName",
          width: 100,
          key: "teacherName",
          scopedSlots: {
            filterDropdown: "filterDropdown",
            filterIcon: "filterIcon",
            customRender: "customRender",
          },
          onFilter: (value, record) =>
            record.address
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
    this.searchClass();
    this.fetchTeacherFree("");
  },
  methods: {
    paginate(current = 1) {
      this.loading = true;
      this.current = current;
      ClassRepository.searchClass(this.formDataSearch, this.current).then(
        (res) => {
          this.data = res.data.data.items;
          this.totals = res.data.data.total;
          this.loading = false;
        }
      );
    },
    searchClass() {
      this.loading = true;
      ClassRepository.searchClass(this.formDataSearch, 1).then((res) => {
        this.data = res.data.data.items;
        this.totals = res.data.data.total;
        this.current = 1;
        this.loading = false;
      });
    },
    handleSearch(selectedKeys, dataIndex) {
      this.searchText = selectedKeys[0];
      this.searchedColumn = dataIndex;
      if (dataIndex === "className") {
        this.formDataSearch.className = selectedKeys[0];
      } else if (dataIndex === "teacherName") {
        this.formDataSearch.teacherName = selectedKeys[0];
      }
      this.searchClass();
    },

    handleReset(dataIndex, clearFilters) {
      if (dataIndex === "className") {
        this.formDataSearch.className = "";
      } else if (dataIndex === "teacherName") {
        this.formDataSearch.teacherName = "";
      }
      clearFilters();
      this.searchClass();
      this.searchText = "";
    },
    async handleEditItemBtnClick(item) {
      this.selectedItem = item;
      this.editForm.id = item.id;
      this.editForm.className = item.className;
      // this.editForm.teacherUsername = item.teacherAccount;
      if (item.teacherAccount) {
        this.teacherOfClassSelected = {
          userName: item.teacherAccount,
          fullName: item.teacherName
        };
        this.editForm.teacherUsername = item.teacherAccount;
      }
      this.fetchTeacherFree("");
      this.showModal = {
        edit: true,
      };
    },
    closeEditForm() {
      this.selectedItem = null;
      this.closeModal();
      this.teacherOfClassSelected = null;
    },
    closeModal() {
      this.showModal = { ...defaultModalState };
      this.errors = { ...defaultInputErrors };
      this.editForm = { ...defaultForm };
      this.addForm = { ...defaultForm };
      this.teacherOfClassSelected = null;
    },
    saveEditClass() {
      this.loadingModal = true;
      const validation = this.validate();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      var formEditData = {
        id: this.editForm.id,
        className: this.editForm.className,
        teacherUsername: this.editForm.teacherUsername,
      };
      ClassRepository.editClass(formEditData)
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
    openAddForm() {
      this.fetchTeacherFree("");
      this.showModal = {
        add: true,
      };
    },
    addNewClass() {
      this.loadingModal = true;
      const validation = this.validateAddNew();
      if (!validation) {
        this.loadingModal = false;
        return;
      }
      var formEditData = {
        className: this.addForm.className,
        teacherUsername: this.addForm.teacherUsername,
      };
      ClassRepository.addNewClass(formEditData)
        .then((response) => {
          console.log('response', response);
          if (response.data.success === true) {
            this.$notification.success({
              message: "Thêm mới thành công!",
            });
            this.paginate();
            this.closeModal();
            this.loadingModal = false;
          } else {
            this.$notification.error({
              message: "Tên lớp học đã tồn tại!",
            });
            this.loadingModal = false;
          }
        })
        .catch((e) => {
          console.log('e ===>', e);
          this.$notification.error({
            message: "Tên lớp học đã tồn tại!",
          });
          this.loadingModal = false;
        });
    },
    validate() {
      let isValid = true;
      if (this.editForm.className == "" || this.editForm.className == null) {
        this.errors.className = requiredError;
        isValid = false;
      }
      return isValid;
    },
    validateAddNew() {
      let isValid = true;
      if (this.addForm.className == "" || this.addForm.className == null) {
        this.errors.className = requiredError;
        isValid = false;
      }
      return isValid;
    },
    async fetchTeacherFree(teacherName) {
      this.loadingModal = true;
      await UserRepository.searchTeacherFreeByName(teacherName, 1).then((res) => {
        this.teacherList = res.data.data.items;
        if (this.teacherOfClassSelected) {
          this.teacherList.push(this.teacherOfClassSelected);
        }
        this.loadingModal = false;
      });
    },
    deleteSubItemBtnClick(item) {
      this.loading = true;
      this.selectedItem = item;
      ClassRepository.deleteClass(this.selectedItem.id)
        .then((res) => {
          if (res.data.success === true) {
            this.$notification.success({
              message: "Xóa lớp học thành công!",
            });
            this.paginate();
          } else {
            this.$notification.error({
              message: "Xóa lớp học thất bại!",
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
  },
};
</script>
<style scoped>
.highlight {
  background-color: rgb(255, 192, 105);
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

.cus-header-class {
  margin: none !important;
  padding: none !important;
}
</style>
