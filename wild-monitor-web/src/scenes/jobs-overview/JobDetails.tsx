import {Job, JobRecord} from "../../models/Job";
import * as shortid from "shortid";
import * as React from "react";
import moment = require("moment");
import {colorMap} from "./JobStatusColorMap";

interface JobDetailsProps {
  job: Job;
}

export const JobDetails = (props: JobDetailsProps) =>
  <div className="card mt-3" data-test="job-item" key={shortid.generate()}
       style={{width: "18rem"}}>
    <h5 className="card-header" data-test="job-item-header"
        style={{
          color: colorMap(props.job.records[0].status),
          backgroundColor: "black"
        }}>{props.job.records[0].status}</h5>
    <div className="card-body" data-test="job-item-body">
      <h6 data-test="job-item-id" className="card-subtitle mb-2 text-muted">{props.job.jobId}</h6>
      {props.job.records.map((jobRecord: JobRecord) => <div>{jobRecord.status} &lt;- {jobRecord.updatedOn}</div>)}
      <div data-test="job-item-created-on"
           style={{fontSize: "11px", color: "lightgrey"}}>
        {moment(props.job.createdOn).fromNow()} ({props.job.createdOn})
      </div>
    </div>
  </div>;