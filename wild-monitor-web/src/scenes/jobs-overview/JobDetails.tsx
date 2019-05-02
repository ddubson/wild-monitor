import {Job, JobStateLog} from "../../models/Job";
import * as shortid from "shortid";
import * as React from "react";
import moment = require("moment");
import {colorMap} from "./JobStatusColorMap";

interface JobDetailsProps {
  job: Job;
}

export const JobDetails = ({job}: JobDetailsProps) =>
  <div className="card mt-3" data-test="job-item" key={shortid.generate()}>
    <h5 className="card-header" data-test="job-item-header"
        style={{
          color: colorMap(job.stateLog[0].status),
          backgroundColor: "black"
        }}>{job.stateLog[0].status}</h5>
    <div className="card-body" data-test="job-item-body">
      {job.stateLog.map((jobStateLog: JobStateLog) => <p
        key={shortid.generate()} style={{color: "grey"}}>{jobStateLog.status} ({moment(jobStateLog.updatedOn).fromNow()})</p>)}
      <div data-test="job-item-created-on"
           style={{fontSize: "11px", color: "grey"}}>
        Created {moment(job.createdOn).fromNow()} ({job.createdOn})
      </div>
    </div>
  </div>;