import {Job, JobStateLog} from "../../models/Job";
import * as shortid from "shortid";
import * as React from "react";
import moment = require("moment");
import {colorMap} from "./JobStatusColorMap";

interface JobDetailsProps {
  job: Job;
}

export const JobDetails = ({job}: JobDetailsProps) =>
  <div className="card mt-3" data-test="job-item" key={shortid.generate()}
       style={{width: "18rem"}}>
    <h5 className="card-header" data-test="job-item-header"
        style={{
          color: colorMap(job.stateLog[0].status),
          backgroundColor: "black"
        }}>{job.stateLog[0].status}</h5>
    <div className="card-body" data-test="job-item-body">
      <h6 data-test="job-item-id" className="card-subtitle mb-2 text-muted">{job.jobId}</h6>
      {job.stateLog.map((jobStateLog: JobStateLog) => <div
        key={shortid.generate()}>{jobStateLog.status} &lt;- {jobStateLog.updatedOn}</div>)}
      <div data-test="job-item-created-on"
           style={{fontSize: "11px", color: "lightgrey"}}>
        {moment(job.createdOn).fromNow()} ({job.createdOn})
      </div>
    </div>
  </div>;