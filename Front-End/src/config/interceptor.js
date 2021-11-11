import axios from "axios";
import Vue from "vue";
import CONFIG from "./index";

(function() {
  let token = Vue.$cookies.get("accessToken");
  Vue.mixin({
    data: () => {
      return {
        loginUsername: Vue.$cookies.get("account") || "",
        token
      };
    },
  });
  if (token) {
    axios.defaults.headers.common["Authorization"] = "Bearer " + token;
  } else {
    axios.defaults.headers.common["Authorization"] = null;
  }
  // set a default response handler
  axios.interceptors.response.use(
    (res) => {
      return res;
    },
    (err) => {
      if (typeof err.response === "undefined" || err.response.status === 401) {
        //   window.location.href = `${CONFIG.LOGIN_URL}${encodeURIComponent(window.location.href)}`;
        window.location.href = `${CONFIG.CLIENT_URL}/#/login`;
      } else if (err.response.status === 403) {
        window.location.href = `${CONFIG.CLIENT_URL}/#/forbidden`;
      }
      return Promise.reject(err);
    }
  );
})();
