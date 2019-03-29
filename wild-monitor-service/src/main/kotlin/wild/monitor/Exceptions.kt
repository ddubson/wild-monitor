package wild.monitor

class ProjectDoesNotExistException: RuntimeException("Project does not exist.")
class ProjectNameTakenException: RuntimeException("Project name has already been taken.")
class NoProjectNameSuppliedException: RuntimeException("Project name was not supplied.")