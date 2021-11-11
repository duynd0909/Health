<template>
  <section>
    <div class="container pt-lg-md">
      <div class="row justify-content-center">
        <div class="col-lg-5">
          <card
            type="secondary"
            shadow
            header-classes="bg-white pb-5"
            body-classes="px-lg-5 py-lg-5"
            class="border-0"
          >
            <a-spin :spinning="loading" tip="Đang đăng nhập...">
              <a-icon
                type="loading"
                slot="indicator"
                style="font-size: 24px"
                spin
              />
              <template class="spin-content">
                <form role="form" class="form-login">
                  <base-input
                    v-model="username"
                    class="mb-3"
                    placeholder="Email"
                    addon-left-icon="ni ni-email-83"
                  >
                  </base-input>
                  <base-input
                    v-model="password"
                    type="password"
                    placeholder="Password"
                    addon-left-icon="ni ni-lock-circle-open"
                  >
                  </base-input>
                  <base-checkbox> Remember me </base-checkbox>
                  <span v-if="errorLogin" class="red"> {{ errorLogin }} </span>
                  <div class="text-center">
                    <base-button
                      @loading="loading"
                      type="primary"
                      class="my-4"
                      @click="basicLogin"
                    >
                      Sign In
                    </base-button>
                  </div>
                </form>
              </template>
            </a-spin>
          </card>
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import { BaseInput, Card } from "@/components/index";
import { basicProcessLogins } from "../api/processLogin";
import axios from "axios";
import Vue from "vue";

const defaultFormAddUser = {
  account: "",
  firstName: "",
  lastName: "",
  avatar: "",
  address: "",
  gender: 0,
  phoneNumber: "",
  dateOfBirth: "1970-01-01",
};

export default {
  components: {
    Card,
    BaseInput,
  },
  data() {
    return {
      username: "",
      password: "",
      loading: false,
      visible: false,
      listRoles: [],
      userData: {},
      formLayout: "horizontal",
      showModalAddUser: false,
      errors: {
        account: "",
        category: "",
        firstName: "",
        lastName: "",
        address: "",
        gender: "",
        phoneNumber: "",
        dateOfBirth: "",
      },
      formAddUser: { ...defaultFormAddUser },
      fileList: [],
      previewImage: "",
      headers: "",
      previewVisible: false,
      introductionImageUpload: "",
      shareId: null,
      userLogin: null,
      errorLogin: "",
    };
  },
  methods: {
    async basicLogin() {
      this.loading = true;
      await basicProcessLogins(this.username, this.password)
        .then((res) => {
          if (res) {
            console.log(res, "resss");
            this.userData = res;
            let userInfo = this.userData.userInfor;
            const expiredTime = this.userData.expiredTime;
            console.log("this.userData ==>", this.userData);
            axios.defaults.headers.common["Authorization"] =
              "Bearer " + this.userData.token;
            Vue.$cookies.set("accessToken", this.userData.token, expiredTime);
            Vue.$cookies.set("username", this.userData.username, expiredTime);
            Vue.$cookies.set("role", userInfo.roleCode, expiredTime);
            Vue.$cookies.set("class", userInfo.classID, expiredTime);

            this.$router.push("home-page");
            this.loading = false;
          } else {
            this.errorLogin = "Username or password is incorrect!";
            this.loading = false;
          }
        })
        .catch(() => {
          this.errorLogin = "Username or password is incorrect!";
          this.loading = false;
        });
    },
  },
};
</script>
<style>
.form-login {
  margin-top: 150px;
}
.red {
  color: red;
}
.spin-content {
  border: 1px solid #91d5ff;
  background-color: #e6f7ff;
  padding: 30px;
}
</style>
