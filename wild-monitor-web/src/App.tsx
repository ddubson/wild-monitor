import * as React from "react";
import {PureComponent} from "react";
import ProjectsDashboardScene from "./scenes/ProjectsDashboardScene";
import {BrowserRouter as Router, Route} from "react-router-dom";
import JobsOverviewScene from "./scenes/JobsOverviewScene";
import {getAllProjects} from "./services/WildMonitorServiceAdapter";

class App extends PureComponent {
    public render(): JSX.Element {
        return (
            <Router>
                <div className="container">
                    <Route path={"/"}
                           exact={true}
                           render={() => <ProjectsDashboardScene getAllProjects={getAllProjects}/>}
                    />
                    <Route path={"/jobs"}
                           component={JobsOverviewScene}
                    />
                </div>
            </Router>
        );
    }
}

export default App;
