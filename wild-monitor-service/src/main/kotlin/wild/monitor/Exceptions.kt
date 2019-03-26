package wild.monitor

class ProjectDoesNotExistException: RuntimeException("Project does not exist.")
class ProjectNameTakenException: java.lang.RuntimeException("Project name has already been taken.")