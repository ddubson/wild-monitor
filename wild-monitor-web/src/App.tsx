import * as React from "react";
import {PureComponent} from "react";
import ProjectsDashboardScene from "./scenes/ProjectsDashboardScene";
import {BrowserRouter as Router, Link, Route} from "react-router-dom";
import JobsOverviewScene from "./scenes/JobsOverviewScene";
import {AddProject, getAllProjects, getJobsByProjectKey} from "./services/WildMonitorServiceAdapter";

class App extends PureComponent {
  public render(): JSX.Element {
    return (
      <Router>
        <section>
          <nav className="navbar navbar-light bg-light">
            <a className={"navbar-brand font-weight-bold"}>WildMonitor</a>
          </nav>
          <div className="container mt-2">
            <Route path={"/"}
                   exact={true}
                   render={() =>
                     <ProjectsDashboardScene
                       getAllProjects={getAllProjects}
                       addProject={AddProject}
                     />
                   }
            />
            <Route path={"/jobs"}
                   render={({location}) =>
                     <JobsOverviewScene
                       location={location}
                       getJobsByProjectKey={getJobsByProjectKey}
                     />
                   }
            />
          </div>
        </section>
      </Router>
    );
  }
}

export default App;
