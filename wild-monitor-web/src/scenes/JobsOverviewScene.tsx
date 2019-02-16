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

interface Event {
    data: string,
}

interface JobsOverviewSceneProps {
    location: any,
}

interface JobsOverviewSceneState {
    jobs: Job[],
    projectKey: string,
    events: Event[],
}

const colorMap = (status: string) => {
    switch(status) {
        case "PENDING": return "yellow";
        case "STARTED": return "orange";
        case "SUCCEEDED": return "lightgreen";
        case "FAILED": return "red";
        default:
            return "yellow";
    }
};

const renderJob = (job: Job) =>
    <div className="card" key={shortid.generate()} style={{marginTop: "5px", borderColor: "lightgreen", backgroundColor: "black",width: "18rem"}}>
        <div className="card-body">
            <h5 className="card-title" style={{color: colorMap(job.status)}}>{job.status}</h5>
            <h6 className="card-subtitle mb-2 text-muted">{job.id}</h6>
        </div>
    </div>;

class JobsOverviewScene extends PureComponent<JobsOverviewSceneProps, JobsOverviewSceneState> {
    eventSource: EventSource;

    constructor(props: JobsOverviewSceneProps) {
        super(props);
        this.eventSource = new EventSource(`${wildMonitorService.defaults.baseURL}/events`);
        const params = new URLSearchParams(this.props.location.search);
        this.state = {
            projectKey: params.get('projectKey'),
            jobs: [],
            events: []
        }
    }

    componentDidMount(): void {
        this.eventSource.onmessage = (event) => {
            this.setState({
                events: [...this.state.events, {data: event.data}]
            })
        };

        wildMonitorService.get(`/jobs?projectKey=${this.state.projectKey}`)
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

    componentWillUnmount(): void {
        this.eventSource.close();
    }

    render(): React.ReactNode {
        return (
            <div>
                <nav><Link to={"/"}>Back to Projects</Link></nav>
                <section style={{marginTop: "15px"}}>
                    {this.state.jobs.length !== 0 ? this.state.jobs.map(renderJob) : "No jobs yet."}
                </section>
                <section style={{marginTop: "15px"}}>
                    <p>To create a job, execute the following:</p>
                    <code>
                        http POST {wildMonitorService.defaults.baseURL}/jobs projectKey={this.state.projectKey}
                    </code>
                </section>
                <section style={{marginTop: "15px"}}>
                    <h4>Events</h4>
                    {this.state.events.map((event: Event) => <div>{event.data}</div>)}
                </section>
            </div>
        )
    }
}

export default JobsOverviewScene;