import * as React from "react";
import {PureComponent} from "react";
import ProjectsDashboardScene from "./scenes/ProjectsDashboardScene";
import {BrowserRouter as Router, Route} from "react-router-dom";
import JobsOverviewScene from "./scenes/JobsOverviewScene";

class App extends PureComponent {
    public render(): JSX.Element {
        return (
            <Router>
                <section>
                    <Route path={"/"} exact={true} component={ProjectsDashboardScene}/>
                    <Route path={"/jobs"} component={JobsOverviewScene}/>
                </section>
            </Router>
        );
    }
}

export default App;