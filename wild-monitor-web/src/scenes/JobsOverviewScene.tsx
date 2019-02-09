import {PureComponent} from "react";
import * as React from "react";
import {Link} from "react-router-dom";
import wildMonitorService from "../App.config";
import {AxiosResponse} from "axios";
import * as shortid from "shortid";

interface Job {
    id: string,
    projectKey: string,
    status: string,
}

interface JobsOverviewSceneProps {
    location: any,
}
interface JobsOverviewSceneState {
    jobs: Job[]
}


class JobsOverviewScene extends PureComponent<JobsOverviewSceneProps, JobsOverviewSceneState> {
    constructor(props: JobsOverviewSceneProps) {
        super(props);
        this.state = {
            jobs: []
        }
    }

    componentDidMount(): void {
        const params = new URLSearchParams(this.props.location.search);
        wildMonitorService.get(`/jobs?projectKey=${params.get('projectKey')}`)
            .then((response: AxiosResponse) => response.data)
            .then((jobResponseArray: any) => {
                this.setState({
                    jobs: jobResponseArray.map((jobResponse: any) => {
                        return {
                            id: jobResponse.id,
                            projectKey: jobResponse.projectKey,
                            status: jobResponse.status
                        }
                    })
                });
            })
    }

    render(): React.ReactNode {
        return (
            <div>
                <div><Link to={"/"}>Back to Projects</Link></div>
                {this.state.jobs.map(job => <div key={shortid.generate()}>{job.id}: {job.status}: {job.projectKey}</div>)}
            </div>
        )
    }
}

export default JobsOverviewScene;