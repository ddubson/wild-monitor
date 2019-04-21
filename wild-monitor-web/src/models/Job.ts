export interface Job {
  jobId: string;
  projectKey: string;
  createdOn: string;
  records: JobRecord[];
}

export interface JobRecord {
  status: string;
  expiresOn: string;
  updatedOn: string;
}
