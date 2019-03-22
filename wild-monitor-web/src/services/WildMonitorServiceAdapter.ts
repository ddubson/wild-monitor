import wildMonitorService from "../App.config";
import {AxiosResponse} from "axios";
import {Project} from "../models/Project";

export const getAllProjects = (): Promise<Project[]> => {
  return wildMonitorService.get("/projects")
    .then((response: AxiosResponse) => response.data)
    .catch(error => console.error("Catch me!!"));
};
