import {Project} from "../../src/models/Project";
import {Job} from "../../src/models/Job";

export const emptyPromiseOfProjects: () => Promise<Project[]> =
  () => Promise.resolve([]);

export const emptyAddProjectPromise: (projectName: string) => Promise<Project> =
  () => Promise.resolve(null);

export const emptyPromiseOfJobs: (projectKey: string) => Promise<Job[]> =
  () => Promise.resolve([]);
