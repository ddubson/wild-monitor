package wild.monitor.web_support

data class ErrorResponse(val message: String = "An error has occurred.",
                         val howToRectify: String? = "No specific instructions specified.")