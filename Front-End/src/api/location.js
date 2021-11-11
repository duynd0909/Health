import axios from "axios";
import config from "../config/index";
const API_PROVINCE = `${config.apiUrl}/api/province`;
const API_DISTRICT = `${config.apiUrl}/api/district`;
const API_WARD = `${config.apiUrl}/api/ward`;

function getAllProvince() {
  return axios.get(`${API_PROVINCE}/getAll`);
}

function getProvinceByName(provinceName) {
  return axios.get(`${API_PROVINCE}/get-by-name?provinceName=${provinceName}`);
}

function getDistrictInProvince(provinceCode, districtName) {
  return axios.get(
    `${API_DISTRICT}/get-district-by-province?provinceCode=${provinceCode}&districtName=${districtName}`
  );
}

function getWardInDistrict(districtCode, wardName) {
  return axios.get(
    `${API_WARD}/get-ward-by-district?districtCode=${districtCode}&wardName=${wardName}`
  );
}

export default {
  getAllProvince,
  getProvinceByName,
  getDistrictInProvince,
  getWardInDistrict,
};
