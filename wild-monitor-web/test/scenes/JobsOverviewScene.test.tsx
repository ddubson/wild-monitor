import {findOrFail, getTextByClassName} from "../helpers/enzyme-helpers";
import {mount, ReactWrapper} from "enzyme";
import JobsOverviewScene from "../../src/scenes/JobsOverviewScene";
import * as React from "react";
import {MemoryRouter} from "react-router";
import {Job} from "../../src/models/Job";

const emptyPromiseOfJobs: (projectKey: string) => Promise<Job[]> =
  () => Promise.resolve([]);

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
        {id: "1", projectKey: "1p", status: "PENDING"},
        {id: "2", projectKey: "2p", status: "STARTED"}
      ];

      const promiseOfJobs: (projectKey: string) => Promise<Job[]> =
        () => Promise.resolve(jobs);
      scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
        <JobsOverviewScene location={{search: "?projectKey=1p"}} getJobsByProjectKey={promiseOfJobs} />
      </MemoryRouter>);
    });

    it("should display the jobs", (done) => {
      setImmediate(() => {
        scene.update();
        expect(findOrFail(scene, "[data-test='job-item-header']")
          .map(job => job.text())).toEqual(["PENDING", "STARTED"]);
        expect(findOrFail(scene, "[data-test='job-item-body']")
          .map(job => job.text())).toEqual(["1", "2"]);
        done();
      });
    });
  });
});
