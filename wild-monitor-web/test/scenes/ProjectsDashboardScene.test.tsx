import ProjectsDashboardScene from "../../src/scenes/ProjectsDashboardScene";
import * as React from "react";
import {mount, shallow, ShallowWrapper} from "enzyme";
import {Project} from "../../src/models/Project";
import {MemoryRouter} from "react-router";

const emptyPromiseOfProjects: () => Promise<Project[]> = () => Promise.resolve([]);

describe("Projects Dashboard Scene", () => {
  describe("On page load", () => {
    it("should display title", () => {
      const scene = shallow(<ProjectsDashboardScene getAllProjects={emptyPromiseOfProjects} />);
      const pageTitle = findOrFail(scene, ".scene-title");
      expect(pageTitle.text()).toEqual("Projects");
    });

    it("should load projects that exist in the system", (done) => {
      const expectedProjects: Project[] = [
        {id: "1", projectKey: "p1", projectName: "name1"},
        {id: "2", projectKey: "p2", projectName: "name2"},
      ];

      const fakeGetAllProjects: () => Promise<Project[]> =
        () => Promise.resolve(expectedProjects);
      const scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
        <ProjectsDashboardScene getAllProjects={fakeGetAllProjects} /></MemoryRouter>
      );

      setImmediate(() => {
        scene.update();
        const projectTitles = scene.find(".card .card-title").map(title => title.text());
        expect(["name1", "name2"]).toEqual(projectTitles);

        const projectIds = scene.find(".card .card-subtitle").map(title => title.text());
        expect(["1", "2"]).toEqual(projectIds);
        done();
      })
    });
  });

  function findOrFail(wrapper: ShallowWrapper, cssSelector: string) {
    const result = wrapper.find(cssSelector);
    if (result && result.length > 0) {
      return result;
    } else {
      fail(`Could not find element by selector '${cssSelector}'`);
    }
  }
});
