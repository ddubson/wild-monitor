import wildMonitorService from "../App.config";
import {AxiosResponse} from "axios";
import {Project} from "../models/Project";
import {Job} from "../models/Job";

export const getAllProjects = (): Promise<Project[]> => {
  return wildMonitorService.get("/projects")
    .then((response: AxiosResponse) => response.data)
    .catch(error => console.error("Catch me!!"));
};

export const AddProject = (projectName: string): Promise<Project> => {
  return wildMonitorService.post("/projects", {projectName})
    .then((response: AxiosResponse) => response.data);
};

export const getJobsByProjectKey = (projectKey: string): Promise<Job[]> => {
  return wildMonitorService.get(`/jobs?projectKey=${projectKey}`)
    .then((response: AxiosResponse) => response.data);
};
