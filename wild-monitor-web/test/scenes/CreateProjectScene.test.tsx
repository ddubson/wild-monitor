import {mount, ReactWrapper} from "enzyme";
import {getTextByClassName} from "../helpers/enzyme-helpers";
import * as React from "react";
import CreateProjectScene from "../../src/scenes/CreateProjectScene";
import {emptyAddProjectPromise} from "../helpers/Promises";

describe("Create Project Scene", () => {
  let scene: ReactWrapper;

  describe("when an error message is not present", () => {
    beforeEach(() => {
      scene = mount(<CreateProjectScene addProject={emptyAddProjectPromise} />);
    });

    it("should not display the error message", () => {
      expect(scene.find("[data-test='add-project-error']")).toHaveLength(0);
    });
  });

  describe("when an error message is present", () => {
    const errorMessage = "Project name has already been taken.";
    beforeEach(() => {
      scene = mount(<CreateProjectScene errorMessage={errorMessage} addProject={emptyAddProjectPromise} />);
    });

    it("should display the error message", () => {
      expect(getTextByClassName(scene, "[data-test='add-project-error']")).toEqual(errorMessage);
    });
  });
});
