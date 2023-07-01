package team.belloo.hamoney.webapi

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonValue

@JsonInclude(JsonInclude.Include.NON_NULL)
open class JsonResult(
    open val status: Status,
    open val message: String? = null
) {
    companion object {
        fun success(message: String? = null) = JsonResult(status = Status.SUCCESS, message = message)
        fun error(message: String? = null) = JsonResult(status = Status.ERROR, message = message)
    }

    data class Status(@JsonValue val code: Int) {
        companion object {
            val SUCCESS = Status(0)
            val ERROR = Status(-500)
            val REQUIRED_REFRESH_TOKEN = Status(1000)
        }
    }
}