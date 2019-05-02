import {Job} from "../../../src/models/Job";

export const sampleJobs: () => Job[] = () => [
  {
    jobId: "1",
    projectKey: "1p",
    createdOn: "2019-01-01",
    expiresOn: "2020-01-01",
    stateLog: [
      {
        status: "PENDING",
        updatedOn: "2019-01-01"
      }
    ]
  },
  {
    jobId: "2",
    projectKey: "2p",
    createdOn: "2018-12-19",
    expiresOn: "2019-01-01",
    stateLog: [
      {
        status: "STARTED",
        updatedOn: "2018-12-19"
      }
    ]
  }
];