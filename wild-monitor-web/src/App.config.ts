import axios, {AxiosInstance} from "axios";

const wildMonitorServiceUrl = process.env.WILD_MONITOR_SVC_URL || "http://localhost:8080";

const wildMonitorService: AxiosInstance = axios.create({
    baseURL: wildMonitorServiceUrl,
});

export default wildMonitorService;