<template>
  <div class="content">
    <card>
      <template slot="header">
        <h4 class="card-title">Khai báo di chuyển nội địa</h4>
      </template>
      <div class="table-responsive text-left declard-form-cus">
        <a-form :form="form" @submit="handleSubmit">
          <a-card title="Thông tin cá nhân:">
            <a-row type="flex">
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Họ và tên" has-feedback>
                  <a-input
                    :disabled="true"
                    v-decorator="[
                      'fullName',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa nhập họ và tên',
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập..."
                  >
                  </a-input>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Giới tính" has-feedback>
                  <a-select
                    v-decorator="[
                      'gender',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn giới tính!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn giới tính"
                  >
                    <a-select-option key="true" :value="true">
                      Nam
                    </a-select-option>
                    <a-select-option key="false" :value="false">
                      Nữ
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Ngày sinh" has-feedback>
                  <a-date-picker
                    v-decorator="[
                      'dateOfBirth',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn ngày sinh!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn ngày sinh"
                  >
                  </a-date-picker>
                </a-form-item>
              </a-col>
            </a-row>
            <a-row type="flex">
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Điện thoại" has-feedback>
                  <a-input
                    v-decorator="[
                      'phoneNumber',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa nhập số điện thoại',
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập số điện thoại..."
                  >
                  </a-input>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Số thẻ bảo hiểm y tế" has-feedback>
                  <a-input
                    v-decorator="[
                      'healthInsuranceId',
                      {
                        rules: [
                          {
                            required: false,
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập số thẻ"
                  >
                  </a-input>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Email" has-feedback>
                  <a-input
                    v-decorator="[
                      'gmail',
                      {
                        rules: [
                          {
                            required: false,
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập email"
                  >
                  </a-input>
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>

          <a-card title="Địa chỉ lưu trú:" :style="{ marginTop: '16px' }">
            <a-row type="flex">
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Tỉnh thành" has-feedback>
                  <a-select
                    @change="onChangeProvince"
                    v-decorator="[
                      'provinceCode',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn nơi ở!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn tỉnh, thành phố"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listProvince"
                      :key="item.id"
                      :value="item.code"
                    >
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Quận huyện" has-feedback>
                  <a-select
                    @change="onChangeDistrict"
                    v-decorator="[
                      'districtCode',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn nơi ở(Quận huyện)!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn quận huyện"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listDistrict"
                      :key="item.id"
                      :value="item.districtCode"
                    >
                      {{ item.districtName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Phường xã" has-feedback>
                  <a-select
                    v-decorator="[
                      'wardCode',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn nơi ở(Phường xã)!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn phường xã"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listWard"
                      :key="item.id"
                      :value="item.wardCode"
                    >
                      {{ item.wardName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item
                  label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                  has-feedback
                >
                  <a-input
                    v-decorator="[
                      'addressDetail',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa nhập địa chỉ nơi ở!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập..."
                  >
                  </a-input>
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>

          <a-card title="Phương tiện - Biển số:" :style="{ marginTop: '16px' }">
            <a-row type="flex">
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Loại phương tiện" has-feedback>
                  <a-select
                    v-decorator="[
                      'vehicleType',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn loại phương tiện!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn phương tiện"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listVehicle"
                      :key="item.id"
                      :value="item.code"
                    >
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Biển số/ Số hiệu phương tiện" has-feedback>
                  <a-input
                    v-decorator="[
                      'controlPlateNumber',
                      {
                        rules: [
                          {
                            required: true,
                            message:
                              'Bạn chưa nhập biển số/ số hiệu phương tiện!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập biển số/ số hiệu phương tiện"
                  >
                  </a-input>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Ngày di chuyển" has-feedback>
                  <a-date-picker
                    v-decorator="[
                      'movingDate',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn ngày di chuyển!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn ngày di chuyển"
                  >
                  </a-date-picker>
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>

          <a-card title="Thông tin nơi đi:" :style="{ marginTop: '16px' }">
            <a-row type="flex" label="1. Nơi đi" title="1. Nơi đi">
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Tỉnh thành" has-feedback>
                  <a-select
                    @change="onChangeProvinceFrom"
                    v-decorator="[
                      'provinceCodeFrom',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn nơi đi!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn tỉnh, thành phố"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listProvince"
                      :key="item.id"
                      :value="item.code"
                    >
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Quận huyện" has-feedback>
                  <a-select
                    @change="onChangeDistrictFrom"
                    v-decorator="[
                      'districtCodeFrom',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn điểm đi(Quận huyện)!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn quận huyện"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listDistrictFrom"
                      :key="item.id"
                      :value="item.districtCode"
                    >
                      {{ item.districtName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Phường xã" has-feedback>
                  <a-select
                    v-decorator="[
                      'wardCodeFrom',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn điểm đi(Phường xã)!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn phường xã"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listWardFrom"
                      :key="item.id"
                      :value="item.wardCode"
                    >
                      {{ item.wardName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item
                  label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                  has-feedback
                >
                  <a-input
                    v-decorator="[
                      'addressDetailFrom',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa nhập địa chỉ đi điểm đi!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập..."
                  >
                  </a-input>
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>

          <a-card title="Thông tin nơi đến:" :style="{ marginTop: '16px' }">
            <a-row type="flex" label="1. Nơi đi" title="1. Nơi đi">
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Tỉnh thành" has-feedback>
                  <a-select
                    @change="onChangeProvinceTo"
                    v-decorator="[
                      'provinceCodeTo',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn nơi đến!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn tỉnh, thành phố"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listProvince"
                      :key="item.id"
                      :value="item.code"
                    >
                      {{ item.name }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Quận huyện" has-feedback>
                  <a-select
                    @change="onChangeDistrictTo"
                    v-decorator="[
                      'districtCodeTo',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn điểm đến(Quận huyện)!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn quận huyện"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listDistrictTo"
                      :key="item.id"
                      :value="item.districtCode"
                    >
                      {{ item.districtName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item label="Phường xã" has-feedback>
                  <a-select
                    v-decorator="[
                      'wardCodeTo',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa chọn điểm đến(Phường xã)!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Chọn phường xã"
                  >
                    <a-select-option
                      v-for="item in fetchedData.listWardTo"
                      :key="item.id"
                      :value="item.wardCode"
                    >
                      {{ item.wardName }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col class="gutter-box" flex="1 1 200px">
                <a-form-item
                  label="Số nhà,phố tổ,tổ dân phố/thôn/đội"
                  has-feedback
                >
                  <a-input
                    v-decorator="[
                      'addressDetailTo',
                      {
                        rules: [
                          {
                            required: true,
                            message: 'Bạn chưa nhập địa chỉ điểm đến!',
                          },
                        ],
                      },
                    ]"
                    placeholder="Nhập..."
                  >
                  </a-input>
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>

          <a-card
            title=""
            class="last-select-cus"
            :style="{ marginTop: '16px' }"
          >
            <!-- <a slot="extra" href="#">More</a> -->
            <a-form-item
              label="Trong vòng 14 ngày qua, Anh/Chị có đến khu vực tỉnh thành phố, quốc gia/vùng lãnh thổ nào (Có thể đi nhiều nơi)"
            >
              <a-radio-group
                v-decorator="[
                  'contactToPlace',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Bạn chưa chọn',
                      },
                    ],
                  },
                ]"
              >
                <a-radio :value="true"> Có </a-radio>
                <a-radio :value="false"> Không </a-radio>
              </a-radio-group>
            </a-form-item>
            <a-form-item
              label="Trong vòng 14 ngày qua, Anh/Chị có thấy xuất hiện ít nhất  trong các dấu hiệu: sốt, ho, khó thở, viêm phổi, đau họng, mệt mỏi, thay đổi vị giác không?"
            >
              <a-radio-group
                v-decorator="[
                  'sicking',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Bạn chưa chọn',
                      },
                    ],
                  },
                ]"
              >
                <a-radio :value="true"> Có </a-radio>
                <a-radio :value="false"> Không </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-card>
          <a-card
            class="last-select-cus"
            title="Trong vòng 14 ngày qua, Anh/Chị có tiếp xúc với:"
            :style="{ marginTop: '16px' }"
          >
            <a-form-item label="Người bệnh hoặc nghi ngờ mắc bệnh Covid-19">
              <a-radio-group
                v-decorator="[
                  'closeToRiskingPeople',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Bạn chưa chọn',
                      },
                    ],
                  },
                ]"
              >
                <a-radio :value="true"> Có </a-radio>
                <a-radio :value="false"> Không </a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item label="Người từ nước có bệnh Covid-19">
              <a-radio-group
                v-decorator="[
                  'closeToCountry',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Bạn chưa chọn',
                      },
                    ],
                  },
                ]"
              >
                <a-radio :value="true"> Có </a-radio>
                <a-radio :value="false"> Không </a-radio>
              </a-radio-group>
            </a-form-item>

            <a-form-item
              label="Người bệnh có biểu hiện sốt, ho, khó, thở, viêm phổi"
            >
              <a-radio-group
                v-decorator="[
                  'closeToSicking',
                  {
                    rules: [
                      {
                        required: true,
                        message: 'Bạn chưa chọn',
                      },
                    ],
                  },
                ]"
              >
                <a-radio :value="true"> Có </a-radio>
                <a-radio :value="false"> Không </a-radio>
              </a-radio-group>
            </a-form-item>
          </a-card>
          <br />
          <div class="confirm-and-submit">
            <div>
              <h4 class="number-header-rule">
                Cam kết thực hiện biện pháp phòng chống dịch
                <span class="red">*</span>
              </h4>
            </div>
            <div>
              <span class="number-header-rule">1. </span
              ><span class="content-rule "
                >Di chuyển thẳng từ sân bay về nơi lưu trú và thông báo ngay cho
                y tế địa phương nơi cư trú.</span
              >
              <br />
              <span class="number-header-rule">2. </span
              ><span class="content-rule "
                >Tuân thủ tuyệt đối các hướng dẫn, quy định và trả mọi chi phí
                liên quan đến biện pháp phòng chống dịch Covid-19 theo quy định
                của địa phương nơi đến.</span
              >
            </div>
            <br />
            <a-checkbox @change="onAcceptRule">
              Tôi đã đoc và cam kết thực hiện các nội dung trên
            </a-checkbox>
            <br />
            <a-form-item v-bind="tailFormItemLayout">
              <a-button
                type="primary"
                html-type="submit"
                :disabled="!showButtonSubmit"
              >
                Gửi tờ khai
              </a-button>
            </a-form-item>
          </div>
        </a-form>
      </div>
    </card>
  </div>
</template>
<script>
import { Card } from "@/components/index";
import LocationRepository from "../api/location.js";
import VehicleRepository from "../api/vehicle.js";
import UserRepository from "../api/user.js";
import TrackingRepository from "../api/trackingReport.js";
export default {
  components: {
    Card,
  },
  data() {
    return {
      fetchedData: {
        listProvince: [],
        listProvinceFrom: [],
        listProvinceTo: [],
        listDistrict: [],
        listDistrictFrom: [],
        listDistrictTo: [],
        listWard: [],
        listWardFrom: [],
        listWardTo: [],
        listVehicle: [],
      },
      confirmDirty: false,
      autoCompleteResult: [],
      userDetail: {},
      // formItemLayout: {
      //   labelCol: {
      //     xs: { span: 24 },
      //     sm: { span: 8 },
      //   },
      //   wrapperCol: {
      //     xs: { span: 24 },
      //     sm: { span: 16 },
      //   },
      // },
      tailFormItemLayout: {
        wrapperCol: {
          xs: {
            span: 24,
            offset: 8,
          },
          sm: {
            span: 24,
            offset: 11,
          },
        },
      },
      showButtonSubmit: false,
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "register" });
  },
  created() {
    this.fetchProvince();
    this.getUserDetail();
    this.fetchVehicle("");
  },
  mounted: {},
  methods: {
    onAcceptRule(e) {
      this.showButtonSubmit = e.target.checked;
    },
    getUserDetail() {
      UserRepository.getUserByUsername(this.$cookies.get("username")).then(
        (res) => {
          this.userDetail = res.data.data;
          this.form.setFieldsValue({
            fullName: this.userDetail.fullName,
            gender: this.userDetail.gender,
            dateOfBirth: this.userDetail.dob,
            phoneNumber: this.userDetail.phoneNumber,
            gmail: this.userDetail.gmail,
            healthInsuranceId: this.userDetail.healthInsuranceId,
            provinceCode: this.userDetail.provinceCode,
            districtCode: this.userDetail.districtCode,
            wardCode: this.userDetail.wardCode,
            addressDetail: this.userDetail.addressDetail,
          });
          this.fetchDistrict(this.userDetail.provinceCode);
          this.fetchWard(this.userDetail.districtCode);
        }
      );
    },
    onChangeProvince(value) {
      this.fetchedData.listDistrict = [];
      this.fetchedData.listWard = [];
      this.form.resetFields("districtCode");
      this.form.resetFields("wardCode");
      this.fetchDistrict(value);
    },
    onChangeProvinceFrom(value) {
      this.fetchedData.listDistrictFrom = [];
      this.fetchedData.listWardFrom = [];
      this.form.resetFields("districtCodeFrom");
      this.form.resetFields("wardCodeFrom");
      this.fetchDistrictFrom(value);
    },
    onChangeProvinceTo(value) {
      this.fetchedData.listDistrictTo = [];
      this.fetchedData.listWardTo = [];
      this.form.resetFields("districtCodeTo");
      this.form.resetFields("wardCodeTo");
      this.fetchDistrictTo(value);
    },
    onChangeDistrict(value) {
      this.fetchedData.listWard = [];
      this.form.resetFields("wardCode");
      this.fetchWard(value);
    },
    onChangeDistrictFrom(value) {
      this.fetchedData.listWardFrom = [];
      this.form.resetFields("wardCodeFrom");
      this.fetchWardFrom(value);
    },
    onChangeDistrictTo(value) {
      this.fetchedData.listWardTo = [];
      this.form.resetFields("wardCodeTo");
      this.fetchWardTo(value);
    },
    fetchProvince() {
      LocationRepository.getAllProvince().then((res) => {
        this.fetchedData.listProvince = res.data.data;
      });
    },
    fetchDistrict(value) {
      LocationRepository.getDistrictInProvince(value, "").then((res) => {
        this.fetchedData.listDistrict = res.data.data;
      });
    },
    fetchDistrictFrom(value) {
      LocationRepository.getDistrictInProvince(value, "").then((res) => {
        this.fetchedData.listDistrictFrom = res.data.data;
      });
    },
    fetchDistrictTo(value) {
      LocationRepository.getDistrictInProvince(value, "").then((res) => {
        this.fetchedData.listDistrictTo = res.data.data;
      });
    },
    fetchWard(value) {
      LocationRepository.getWardInDistrict(value, "").then((res) => {
        this.fetchedData.listWard = res.data.data;
      });
    },
    fetchWardFrom(value) {
      LocationRepository.getWardInDistrict(value, "").then((res) => {
        this.fetchedData.listWardFrom = res.data.data;
      });
    },
    fetchWardTo(value) {
      LocationRepository.getWardInDistrict(value, "").then((res) => {
        this.fetchedData.listWardTo = res.data.data;
      });
    },
    fetchVehicle(value) {
      VehicleRepository.getVehicleByName(value).then((res) => {
        this.fetchedData.listVehicle = res.data.data.items;
      });
    },
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFieldsAndScroll((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.submitHealthReport(values);
        }
      });
    },
    submitHealthReport(form) {
      TrackingRepository.add(form)
        .then((res) => {
          this.$notification.success({
            message: "Khai báo di chuyển thành công!",
          });
        })
        .catch((err) => {
          this.$notification.console.error();
          ({
            message: "Khai báo di chuyển thất bại!",
          });
        });
    },
    handleConfirmBlur(e) {
      const value = e.target.value;
      this.confirmDirty = this.confirmDirty || !!value;
    },
    compareToFirstPassword(rule, value, callback) {
      const form = this.form;
      if (value && value !== form.getFieldValue("password")) {
        callback("Two passwords that you enter is inconsistent!");
      } else {
        callback();
      }
    },
    validateToNextPassword(rule, value, callback) {
      const form = this.form;
      if (value && this.confirmDirty) {
        form.validateFields(["confirm"], { force: true });
      }
      callback();
    },
    handleWebsiteChange(value) {
      let autoCompleteResult;
      if (!value) {
        autoCompleteResult = [];
      } else {
        autoCompleteResult = [".com", ".org", ".net"].map(
          (domain) => `${value}${domain}`
        );
      }
      this.autoCompleteResult = autoCompleteResult;
    },
  },
};
</script>
<style scoped>
#components-form-demo-validate-other .dropbox {
  height: 180px;
  line-height: 1.5;
}
.gutter-box {
  padding-right: 10px;
}
.card-title {
  text-align: center;
  font-weight: normal !important;
  font-size: 18px;
}
.number-header-rule {
  font-weight: normal;
  font-size: 14px;
}
.content-rule {
  font-size: 0.75rem;
}
</style>
<style>
.ant-card-bordered {
  border: 1px solid #52c41a !important;
  border-radius: 25px !important;
}
.declard-form-cus .ant-card-head-wrapper .ant-card-head-title {
  white-space: initial !important;
  font-size: 14px;
}
.declard-form-cus .ant-form-item-label {
  white-space: initial !important;
  text-align: left !important;
}

.last-select-cus .ant-form-item {
  background-color: aliceblue !important;
  padding: 15px !important;
}

.table-responsive {
  overflow: hidden !important;
}

.confirm-and-submit .ant-checkbox-wrapper:hover .ant-checkbox-inner,
.ant-checkbox:hover .ant-checkbox-inner,
.ant-checkbox-input:focus + .ant-checkbox-inner {
  border-color: #52bb64 !important;
}

.confirm-and-submit .ant-checkbox-checked .ant-checkbox-inner {
  border-color: #52bb64 !important;
  background-color: #52bb64 !important;
}
</style>
