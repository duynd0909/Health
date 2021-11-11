import axios from "axios";
import config from "../config/index";
const API_VEHICLE = `${config.apiUrl}/api/vehicle`;

function getVehicleByName(vehicleName) {
  return axios.get(
    `${API_VEHICLE}/get-vehicle-by-name?vehicleName=${vehicleName}`
  );
}

export default {
  getVehicleByName,
};