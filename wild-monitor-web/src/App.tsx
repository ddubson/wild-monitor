import * as React from "react";
import {PureComponent} from "react";
import * as shortid from "shortid";
import wildMonitorService from "./App.config";
import {AxiosResponse} from "axios";

interface Project {
    id: string
    projectKey: string
    projectName: string
}

interface AppProps {
}

interface AppState {
    projects: Project[]
}

const renderProject = (project: Project): JSX.Element =>
    <div key={shortid.generate()}>{project.id} : {project.projectName} [{project.projectKey}]</div>;

class App extends PureComponent<AppProps, AppState> {
    constructor(props: AppProps) {
        super(props);
        this.state = {
            projects: []
        }
    }

    componentDidMount(): void {
        wildMonitorService.get("/projects")
            .then((response: AxiosResponse) => response.data)
            .then((projectResponseArray: any) => {
                this.setState({
                    projects: projectResponseArray.map((projectResponse: any) => {
                        return {
                            id: projectResponse.id,
                            projectKey: projectResponse.projectKey,
                            projectName: projectResponse.projectName
                        }
                    })
                });
            })
    }

    public render(): JSX.Element {
        return (
            <section>
                <h3>Projects</h3>
                <section>
                    {this.state.projects.map(renderProject)}
                </section>
            </section>
        );
    }
}

export default App;