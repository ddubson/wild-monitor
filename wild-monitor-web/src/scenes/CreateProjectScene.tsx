import {ChangeEvent, FormEvent, PureComponent} from "react";
import * as React from "react";

interface CreateProjectSceneProps {
    addProject: (projectName: string) => void;
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
        }
    }

    render(): React.ReactNode {
        return (
            <React.Fragment>
                <div>Create Project</div>
                <form onSubmit={this.onSubmit}>
                    <label htmlFor={"projectName"}>Project Name</label>
                    <input type={"text"} name="projectName" onChange={this.setProjectName} placeholder={"Project name"}/>
                    <input type={"submit"} value={"Add Project"}/>
                </form>
            </React.Fragment>
        )
    }

    private setProjectName(e: ChangeEvent<HTMLInputElement>): void {
        this.setState({projectName: e.target.value})
    }

    private onSubmit(event: FormEvent<HTMLFormElement>): void {
        event.preventDefault();
        this.props.addProject(this.state.projectName)
    }
}

export default CreateProjectScene;