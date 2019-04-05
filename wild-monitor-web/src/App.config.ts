import axios, {AxiosInstance} from "axios";

require("dotenv").config();

const wildMonitorServiceUrl = process.env.WILDMONITOR_API_URI || "http://localhost:8080";

const wildMonitorService: AxiosInstance = axios.create({
  baseURL: wildMonitorServiceUrl,
});

export default wildMonitorService;
