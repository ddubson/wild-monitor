export const colorMap = (status: string) => {
  switch (status) {
    case "PENDING":
      return "yellow";
    case "STARTED":
      return "orange";
    case "SUCCEEDED":
      return "lightgreen";
    case "FAILED":
      return "red";
    case "EXPIRED":
      return "white";
    default:
      return "yellow";
  }
};