import {PureComponent} from "react";
import * as React from "react";
import {Link} from "react-router-dom";
import shortid = require("shortid");
import wildMonitorService from "../../App.config";
import {Job} from "../../models/Job";
import {JobDetails} from "./JobDetails";

interface JobsOverviewSceneProps {
  location: any;
  getJobsByProjectKey: (projectKey: string) => Promise<Job[]>;
}

interface JobsOverviewSceneState {
  jobs: Job[];
  projectKey: string;
}

class JobsOverviewScene extends PureComponent<JobsOverviewSceneProps, JobsOverviewSceneState> {
  constructor(props: JobsOverviewSceneProps) {
    super(props);
    const params = new URLSearchParams(this.props.location.search);
    this.state = {
      jobs: [],
      projectKey: params.get("projectKey"),
    };
  }

  public componentDidMount(): void {
    this.props.getJobsByProjectKey(this.state.projectKey).then((jobs: Job[]) => {
      this.setState({jobs: [...this.state.jobs, ...jobs]});
    });
  }

  public render(): React.ReactNode {
    const { jobs, projectKey } = this.state;

    return (
      <div>
        <section>
          <h3>Jobs</h3>
          <Link to={"/"}> &gt;&gt; Projects</Link>
        </section>
        <section style={{marginTop: "15px"}} data-test="jobs-list">
          {jobs.length !== 0 ? jobs.map((job) => <JobDetails key={shortid.generate()} job={job}/>) : "No jobs yet."}
        </section>
        <section style={{marginTop: "15px"}}>
          <p>To create a job, execute the following:</p>
          <code>
            http POST {wildMonitorService.defaults.baseURL}/jobs projectKey={projectKey}
          </code>
        </section>
      </div>
    );
  }
}

export default JobsOverviewScene;
