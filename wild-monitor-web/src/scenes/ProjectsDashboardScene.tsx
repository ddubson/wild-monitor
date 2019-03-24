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
  <li className="list-group-item" key={shortid.generate()}>
    <h5>{project.projectName}</h5>
    <h6>{project.id}</h6>
    <Link to={`/jobs?projectKey=${project.projectKey}`}>
      View Jobs
    </Link>
  </li>;

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
          <ul className="list-group">
            {this.state.projects.map(renderProject)}
          </ul>
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
