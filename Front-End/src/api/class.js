import axios from "axios";
import config from "../config/index";
const API_CLASS = `${config.apiUrl}/api/class`;
const pageSize = 10;

function getALlClass() {
  return axios.get(`${API_CLASS}/getAll`);
}

function searchClass(classFormSearch, pageIndex) {
  return axios.post(
    `${API_CLASS}/search-class?pageIndex=${pageIndex}&pageSize=${pageSize}`,
    classFormSearch
  );
}

function searchClassByName(className) {
  return axios.get(`${API_CLASS}/search-class-by-name?className=${className}`);
}

function editClass(classFormEdit) {
  return axios.put(`${API_CLASS}/update?`, classFormEdit);
}

function addNewClass(classFormAddNew) {
  return axios.post(`${API_CLASS}/add?`, classFormAddNew);
}

function deleteClass(id) {
  return axios.delete(`${API_CLASS}/delete?id=${id}`);
}

export default {
  getALlClass,
  searchClass,
  searchClassByName,
  editClass,
  addNewClass,
  deleteClass,
};
