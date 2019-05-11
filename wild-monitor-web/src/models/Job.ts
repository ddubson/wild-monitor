export interface Job {
  jobId: string;
  projectKey: string;
  createdOn: string;
  expiresOn: string;
  stateLog: JobStateLog[];
}

export interface JobStateLog {
  status: string;
  updatedOn: string;
}
