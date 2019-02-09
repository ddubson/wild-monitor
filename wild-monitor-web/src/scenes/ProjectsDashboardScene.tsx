import {PureComponent} from "react";
import * as React from "react";
import {AxiosResponse} from "axios";
import * as shortid from "shortid";
import wildMonitorService from "../App.config";
import {Link} from "react-router-dom";

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
    <div className="card" key={shortid.generate()} style={{width: "18rem"}}>
        <div className="card-body">
            <h5 className="card-title">{project.projectName}</h5>
            <h6 className="card-subtitle mb-2 text-muted">{project.id}</h6>
            <Link to={`/jobs?projectKey=${project.projectKey}`}>
                <a href="#" className="card-link">View Jobs</a>
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

    render(): React.ReactNode {
        return (
            <section>
                <h3>Projects</h3>
                <section>
                    {this.state.projects.map(renderProject)}
                </section>
            </section>
        )
    }
}

export default ProjectsDashboardScene;