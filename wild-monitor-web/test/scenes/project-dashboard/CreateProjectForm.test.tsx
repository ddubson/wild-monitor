import {mount, ReactWrapper} from "enzyme";
import {getTextByClassName} from "../../helpers/enzyme-helpers";
import * as React from "react";
import CreateProjectForm from "../../../src/scenes/projects-dashboard/CreateProjectForm";
import {emptyAddProjectPromise} from "../../helpers/Promises";

describe("Create Project Form", () => {
  let scene: ReactWrapper;

  describe("when an error message is not present", () => {
    beforeEach(() => {
      scene = mount(<CreateProjectForm addProject={emptyAddProjectPromise} />);
    });

    it("should not display the error message", () => {
      expect(scene.find("[data-test='add-project-error']")).toHaveLength(0);
    });
  });

  describe("when an error message is present", () => {
    const errorMessage = "Project name has already been taken.";
    beforeEach(() => {
      scene = mount(<CreateProjectForm errorMessage={errorMessage} addProject={emptyAddProjectPromise} />);
    });

    it("should display the error message", () => {
      expect(getTextByClassName(scene, "[data-test='add-project-error']")).toEqual(errorMessage);
    });
  });
});
