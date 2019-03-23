import {PureComponent} from "react";
import * as React from "react";
import * as shortid from "shortid";
import {Link} from "react-router-dom";
import CreateProjectScene from "./CreateProjectScene";
import {Project} from "../models/Project";

interface AppProps {
  getAllProjects: () => Promise<Project[]>;
  addProject: (projectName: string) => Promise<Project>;
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
    this.addProject = this.addProject.bind(this);
    this.state = {
      projects: []
    }
  }

  componentDidMount(): void {
    this.props.getAllProjects().then((projects: Project[]) => {
      this.setState({projects: projects});
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
          <CreateProjectScene addProject={this.addProject} />
        </section>
      </section>
    )
  }

  addProject(projectName: string): void {
    this.props.addProject(projectName).then((project: Project) => {
      this.setState({
        projects: [...this.state.projects, project]
      });
    });
  }
}

export default ProjectsDashboardScene;
