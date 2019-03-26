import {findOrFail, getTextByClassName} from "../helpers/enzyme-helpers";
import {mount, ReactWrapper} from "enzyme";
import JobsOverviewScene from "../../src/scenes/JobsOverviewScene";
import * as React from "react";
import {MemoryRouter} from "react-router";
import {Job} from "../../src/models/Job";
import moment = require("moment");
import {emptyPromiseOfJobs} from "../helpers/Promises";

describe("Jobs Dashboard Scene", () => {
  let scene: ReactWrapper;

  describe("On page load", () => {
    beforeEach(() => {
      scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
        <JobsOverviewScene location={{search: ""}} getJobsByProjectKey={emptyPromiseOfJobs} />
      </MemoryRouter>);
    });

    it("should display No jobs message", () => {
      const message = getTextByClassName(scene, "[data-test='jobs-list']");
      expect(message).toEqual("No jobs yet.")
    });
  });

  describe("When project has jobs", () => {
    beforeEach(() => {
      const jobs: Job[] = [
        {id: "1", projectKey: "1p", status: "PENDING", createdOn: "2019-01-01"},
        {id: "2", projectKey: "2p", status: "STARTED", createdOn: "2018-12-19"}
      ];

      const promiseOfJobs: (projectKey: string) => Promise<Job[]> =
        () => Promise.resolve(jobs);
      scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
        <JobsOverviewScene location={{search: "?projectKey=1p"}} getJobsByProjectKey={promiseOfJobs} />
      </MemoryRouter>);
    });

    it("should display the jobs", (done) => {
      const relativeTimes = [moment("2019-01-01").fromNow(), moment("2018-12-19").fromNow()];

      setImmediate(() => {
        scene.update();
        expect(findOrFail(scene, "[data-test='job-item-header']")
          .map(job => job.text())).toEqual(["PENDING", "STARTED"]);
        expect(findOrFail(scene, "[data-test='job-item-id']")
          .map(job => job.text())).toEqual(["1", "2"]);
        expect(findOrFail(scene, "[data-test='job-item-created-on']")
          .map(job => job.text())).toEqual([`${relativeTimes[0]} (2019-01-01)`, `${relativeTimes[1]} (2018-12-19)`]);
        done();
      });
    });
  });
});
