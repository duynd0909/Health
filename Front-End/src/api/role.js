import axios from "axios";
import config from "../config/index";
const API_ROLES = `${config.apiUrl}/api/role`;
const pageSize = 10;

function getALLRole() {
  return axios.get(`${API_ROLES}/get-all`);
}

function getRoleNotStudent() {
  return axios.get(`${API_ROLES}/get-role-not-student`);
}
export default {
  getALLRole,
  getRoleNotStudent,
};
