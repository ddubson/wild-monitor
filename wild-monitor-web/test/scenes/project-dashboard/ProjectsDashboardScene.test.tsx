import ProjectsDashboardScene from "../../../src/scenes/projects-dashboard/ProjectsDashboardScene";
import * as React from "react";
import {mount, ReactWrapper} from "enzyme";
import {Project} from "../../../src/models/Project";
import {MemoryRouter} from "react-router";
import {findOrFail, getTextByClassName} from "../../helpers/enzyme-helpers";
import {emptyAddProjectPromise, emptyPromiseOfProjects} from "../../helpers/Promises";

describe("Projects Dashboard Scene", () => {
  let scene: ReactWrapper;

  describe("On page load", () => {
    beforeEach(() => {
      const expectedProjects: Project[] = [
        {id: "1", projectKey: "p1", projectName: "name1", createdOn: "2019-01-01"},
        {id: "2", projectKey: "p2", projectName: "name2", createdOn: "2018-01-01"},
      ];
      const fakeGetAllProjects: () => Promise<Project[]> =
        () => Promise.resolve(expectedProjects);

      scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
        <ProjectsDashboardScene getAllProjects={fakeGetAllProjects}
                                addProject={emptyAddProjectPromise} /></MemoryRouter>
      );
    });

    it("should display title", () => {
      expect(getTextByClassName(scene, ".scene-title")).toEqual("Projects");
    });

    it("should load projects that exist in the system", (done) => {
      setImmediate(() => {
        scene.update();
        const projectCard = scene.find(".list-group-item");
        const projectTitles = projectCard.find("h5").map(title => title.text());
        expect(["name1", "name2"]).toEqual(projectTitles);

        const projectIds = projectCard.find("h6").map(title => title.text());
        expect(["1", "2"]).toEqual(projectIds);

        const projectCreatedOn = projectCard.find("[data-test='project-created-on']").map(p => p.text());
        expect(projectCreatedOn).toEqual(["2019-01-01", "2018-01-01"]);
        done();
      });
    });
  });

  describe("Adding a project", () => {
    describe("given the projects service is operational", () => {
      describe("when I type in a project name", () => {
        beforeEach(() => {
          const expectedProject: Project = {
            id: "1",
            projectKey: "p1",
            projectName: "Test Project",
            createdOn: "2017-01-01"
          };

          const addProjectSpy: (projectName: string) => Promise<Project> =
            () => Promise.resolve(expectedProject);
          scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
            <ProjectsDashboardScene getAllProjects={emptyPromiseOfProjects}
                                    addProject={addProjectSpy} /></MemoryRouter>
          );

          findOrFail(scene, "input[name='projectName']")
            .simulate("change", {target: {value: "Test Project"}});
        });

        describe("and I click 'Add Project'", () => {
          beforeEach(() => {
            findOrFail(scene, "[type='submit']").simulate('submit');
          });

          it("should add the project to the dashboard", (done) => {
            setImmediate(() => {
              scene.update();
              const projectTitles = scene.find(".list-group-item h5").map(title => title.text());
              expect(projectTitles).toEqual(["Test Project"]);

              const projectIds = scene.find(".list-group-item h6").map(title => title.text());
              expect(projectIds).toEqual(["1"]);

              expect(getTextByClassName(scene, "[data-test='project-created-on']")).toEqual("2017-01-01");
              done();
            });

          });

          it("should not display an error message", (done) => {
            setImmediate(() => {
              expect(scene.find("[data-test='add-project-error']")).toHaveLength(0);
              done();
            })
          });

          describe("when I attempt to add the project with same name", () => {
            const errorMessage = "Error message";

            beforeEach(() => {
              const addProjectSpy: (projectName: string) => Promise<Project> =
                () => Promise.reject(errorMessage);
              scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
                <ProjectsDashboardScene getAllProjects={emptyPromiseOfProjects}
                                        addProject={addProjectSpy} /></MemoryRouter>
              );

              findOrFail(scene, "input[name='projectName']")
                .simulate("change", {target: {value: "Test Project"}});
              findOrFail(scene, "[type='submit']").simulate('submit');
            });

            it("should display error message", (done) => {
              setImmediate(() => {
                scene.update();
                expect(getTextByClassName(scene, "[data-test='add-project-error']")).toEqual(errorMessage);
                done();
              })
            });
          });
        });
      });

      describe("when I supply no project name", () => {
        beforeEach(() => {
          scene = mount(<MemoryRouter initialEntries={["/"]} initialIndex={1}>
            <ProjectsDashboardScene getAllProjects={emptyPromiseOfProjects}
                                    addProject={emptyAddProjectPromise} /></MemoryRouter>
          );

          findOrFail(scene, "input[name='projectName']")
            .simulate("change", {target: {value: ""}});
        });

        describe("and I click 'Add Project'", () => {
          beforeEach(() => {
            findOrFail(scene, "[type='submit']").simulate('submit');
          });

          it("should display an error message", (done) => {
            setImmediate(() => {
              scene.update();
              expect(getTextByClassName(scene, "[data-test='add-project-error']")).toEqual("Please provide a unique project name");
              done();
            })
          });
        });
      });
    });
  });
});
