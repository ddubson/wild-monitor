import {PureComponent} from "react";
import * as React from "react";
import {Link} from "react-router-dom";
import wildMonitorService from "../App.config";
import * as shortid from "shortid";
import {Job} from "../models/Job";

interface JobsOverviewSceneProps {
  location: any,
  getJobsByProjectKey: (projectKey: string) => Promise<Job[]>
}

interface JobsOverviewSceneState {
  jobs: Job[],
  projectKey: string
}

const colorMap = (status: string) => {
  switch (status) {
    case "PENDING":
      return "yellow";
    case "STARTED":
      return "orange";
    case "SUCCEEDED":
      return "lightgreen";
    case "FAILED":
      return "red";
    case "EXPIRED":
      return "white";
    default:
      return "yellow";
  }
};

const renderJob = (job: Job) =>
  <div className="card" data-test="job-item" key={shortid.generate()} style={{marginTop: "5px", width: "18rem"}}>
    <h5 className="card-header" data-test="job-item-header" style={{color: colorMap(job.status), backgroundColor: "black"}}>{job.status}</h5>
    <div className="card-body" data-test="job-item-body">
      <h6 className="card-subtitle mb-2 text-muted">{job.id}</h6>
    </div>
  </div>;

class JobsOverviewScene extends PureComponent<JobsOverviewSceneProps, JobsOverviewSceneState> {
  eventSource: EventSource;

  constructor(props: JobsOverviewSceneProps) {
    super(props);
    const params = new URLSearchParams(this.props.location.search);
    this.state = {
      projectKey: params.get('projectKey'),
      jobs: []
    };
  }

  componentDidMount(): void {
    this.props.getJobsByProjectKey(this.state.projectKey).then((jobs: Job[]) => {
      this.setState({jobs: [...this.state.jobs, ...jobs]})
    });
  }

  render(): React.ReactNode {
    return (
      <div>
        <section>
          <h3>Jobs</h3>
          <Link to={"/"}> &gt;&gt; Projects</Link>
        </section>
        <section style={{marginTop: "15px"}} data-test="jobs-list">
          {this.state.jobs.length !== 0 ? this.state.jobs.map(renderJob) : "No jobs yet."}
        </section>
        <section style={{marginTop: "15px"}}>
          <p>To create a job, execute the following:</p>
          <code>
            http POST {wildMonitorService.defaults.baseURL}/jobs projectKey={this.state.projectKey}
          </code>
        </section>
      </div>
    )
  }
}

export default JobsOverviewScene;
