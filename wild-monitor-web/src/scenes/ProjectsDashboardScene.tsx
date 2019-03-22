import {PureComponent} from "react";
import * as React from "react";
import {AxiosResponse} from "axios";
import * as shortid from "shortid";
import wildMonitorService from "../App.config";
import {Link} from "react-router-dom";
import CreateProjectScene from "./CreateProjectScene";
import {Project} from "../models/Project";

interface AppProps {
  getAllProjects: () => Promise<Project[]>;
}

interface AppState {
  projects: Project[]
}

const renderProject = (project: Project): JSX.Element =>
  <div className="card" key={shortid.generate()} style={{width: "18rem"}}>
    <div className="card-body">
      <h5 className="card-title">{project.projectName}</h5>
      <h6 className="card-subtitle mb-2 text-muted">{project.id}</h6>
      <Link to={`/jobs?projectKey=${project.projectKey}`} className="card-link">
        View Jobs
      </Link>
    </div>
  </div>;

class ProjectsDashboardScene extends PureComponent<AppProps, AppState> {
  constructor(props: AppProps) {
    super(props);
    this.state = {
      projects: []
    }
  }

  componentDidMount(): void {
    this.props.getAllProjects().then((projects: Project[]) => {
        this.setState({ projects: projects });
    }).catch(error => console.error(error));
  }

  render(): React.ReactNode {
    return (
      <section>
        <div className="scene-title"><h3>Projects</h3></div>
        <section>
          {this.state.projects.map(renderProject)}
        </section>
        <hr />
        <section>
          <CreateProjectScene addProject={(projectName) => {
            console.log("Adding project", projectName);
            wildMonitorService.post("/projects", {projectName})
              .then((response: AxiosResponse) => response.data)
              .then((data) => {
                this.setState({
                  projects: [...this.state.projects, {
                    id: data.id,
                    projectKey: data.projectKey,
                    projectName: data.projectName
                  }]
                })
              });
          }} />
        </section>
      </section>
    )
  }
}

export default ProjectsDashboardScene;
