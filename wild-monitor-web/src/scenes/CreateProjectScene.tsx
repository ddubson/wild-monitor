import {ChangeEvent, FormEvent, PureComponent} from "react";
import * as React from "react";

interface CreateProjectSceneProps {
  addProject: (projectName: string) => void;
  errorMessage?: string;
}

interface CreateProjectSceneState {
  projectName: string;
}

class CreateProjectScene extends PureComponent<CreateProjectSceneProps, CreateProjectSceneState> {
  constructor(props: CreateProjectSceneProps) {
    super(props);
    this.onSubmit = this.onSubmit.bind(this);
    this.setProjectName = this.setProjectName.bind(this);
    this.state = {
      projectName: null,
    };
  }

  public render(): React.ReactNode {
    return (
      <React.Fragment>
        <div>Create Project</div>
        {this.props.errorMessage && <div data-test="add-project-error" className="alert alert-danger" role="alert">
          {this.props.errorMessage}
        </div>}
        <form onSubmit={this.onSubmit}>
          <label htmlFor={"projectName"}>Project Name</label>
          <input type={"text"} name="projectName" onChange={this.setProjectName} placeholder={"Project name"} />
          <input type={"submit"} value={"Add Project"} />
        </form>
      </React.Fragment>
    );
  }

  private setProjectName(e: ChangeEvent<HTMLInputElement>): void {
    this.setState({projectName: e.target.value});
  }

  private onSubmit(event: FormEvent<HTMLFormElement>): void {
    event.preventDefault();
    this.props.addProject(this.state.projectName);
  }
}

export default CreateProjectScene;
