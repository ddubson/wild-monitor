import {ReactWrapper} from "enzyme";

export const getTextByClassName = (wrapper: ReactWrapper, cssSelector: string): string => {
  return findOrFail(wrapper, cssSelector).text();
};

export const findOrFail = (wrapper: ReactWrapper, cssSelector: string): ReactWrapper => {
  const result = wrapper.find(cssSelector);
  if (result && result.length > 0) {
    return result;
  } else {
    fail(`Could not find element by CSS selector '${cssSelector}'`);
  }
};
