import {findOrFail, getTextByClassName} from "../../helpers/enzyme-helpers";
import {mount, ReactWrapper} from "enzyme";
import JobsOverviewScene from "../../../src/scenes/jobs-overview/JobsOverviewScene";
import * as React from "react";
import {MemoryRouter} from "react-router";
import {Job} from "../../../src/models/Job";
import moment = require("moment");
import {emptyPromiseOfJobs} from "../../helpers/Promises";
import {sampleJobs} from "./JobFixtures";

describe("Jobs Dashboard Scene", () => {
  let scene: ReactWrapper;

  describe("On page load", () => {
    beforeEach(() => {
      scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
        <JobsOverviewScene location={{search: ""}} getJobsByProjectKey={emptyPromiseOfJobs}/>
      </MemoryRouter>);
    });

    it("should display No jobs message", () => {
      const message = getTextByClassName(scene, "[data-test='jobs-list']");
      expect(message).toEqual("No jobs yet.")
    });
  });

  describe("When project has jobs", () => {
    beforeEach(() => {
      const jobs: Job[] = sampleJobs();

      const promiseOfJobs: (projectKey: string) => Promise<Job[]> = () => Promise.resolve(jobs);
      scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
        <JobsOverviewScene location={{search: "?projectKey=1p"}} getJobsByProjectKey={promiseOfJobs}/>
      </MemoryRouter>);
    });

    it("should display the jobs", (done) => {
      const relativeTimes = [moment("2019-01-01").fromNow(), moment("2018-12-19").fromNow()];

      setImmediate(() => {
        scene.update();
        expect(findOrFail(scene, "[data-test='job-item-header']")
          .map(job => job.text())).toEqual(["PENDING", "STARTED"]);
        expect(findOrFail(scene, "[data-test='job-item-created-on']")
          .map(job => job.text())).toEqual([`Created ${relativeTimes[0]} (2019-01-01)`, `Created ${relativeTimes[1]} (2018-12-19)`]);
        done();
      });
    });
  });
});
