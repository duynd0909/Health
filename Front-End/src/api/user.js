import axios from "axios";
import config from "../config/index";
const API_USER = `${config.apiUrl}/api/user`;
const pageSize = 10;

function getAllUser() {
  return axios.get(`${API_USER}/getAll`);
}

function searchTeacherByName(teacherName, pageIndex) {
  return axios.get(
    `${API_USER}/search-teacher-by-name?teacherName=${teacherName}&pageIndex=${pageIndex}&pageSize=${pageSize}`
  );
}

function searchTeacherFreeByName(teacherName) {
  return axios.get(
    `${API_USER}/search-teacher-free-by-name?teacherName=${teacherName}`
  );
}

function searchUser(formSearch, pageIndex) {
  return axios.post(
    `${API_USER}/search-user-to-management?pageIndex=${pageIndex}&pageSize=${pageSize}`,
    formSearch
  );
}

function searchTeacher(formSearch, pageIndex) {
  return axios.post(
    `${API_USER}/search-teacher-to-management?pageIndex=${pageIndex}&pageSize=${pageSize}`,
    formSearch
  );
}

function updateUser(updateForm) {
  return axios.put(`${API_USER}/update`, updateForm);
}
function deleteUser(id) {
  return axios.delete(`${API_USER}/delete?id=${id}`);
}

function getUserByUsername(username) {
  return axios.get(`${API_USER}/get-user-by-username?username=${username}`);
}
function addNewUser(formAddNew) {
  return axios.post(`${API_USER}/add-new-user`, formAddNew);
}
function addNewStudent(formAddNew) {
  return axios.post(`${API_USER}/add-new-student`, formAddNew);
}
function addNewTeacher(formAddNew) {
  return axios.post(`${API_USER}/add-new-teacher`, formAddNew);
}

function changePasswordByUsername(changePasswordForm) {
  return axios.post(
    `${API_USER}/change-password-by-username`,
    changePasswordForm
  );
}

export default {
  getAllUser,
  searchTeacherByName,
  searchTeacherFreeByName,
  searchUser,
  searchTeacher,
  addNewUser,
  updateUser,
  deleteUser,
  getUserByUsername,
  addNewStudent,
  addNewTeacher,
  changePasswordByUsername,
};
