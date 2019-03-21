import ProjectsDashboardScene from "../../src/scenes/ProjectsDashboardScene";
import * as React from "react";
import {shallow, ShallowWrapper} from "enzyme";

describe("Projects Dashboard Scene", () => {
    describe("On page load", () => {
        it("should display title", () => {
            const scene = shallow(<ProjectsDashboardScene/>);
            const pageTitle = findOrFail(scene, ".scene-title");
            expect(pageTitle.text()).toEqual("Projects");
        })
    });

    function findOrFail(wrapper: ShallowWrapper, cssSelector: string) {
        const result = wrapper.find(cssSelector);
        if(result && result.length > 0) {
            return result;
        } else {
            fail(`Could not find element by selector '${cssSelector}'`);
        }
    }
});
