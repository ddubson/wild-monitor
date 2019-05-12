package wild.monitor.projects

class ProjectNameTakenException: RuntimeException("Project name has already been taken.")
class NoProjectNameSuppliedException: RuntimeException("Project name was not supplied.")
class ProjectNotFoundException: RuntimeException("Project does not exist.")