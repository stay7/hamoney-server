package team.belloo.hamoney.webapi

open class JsonResult(
    open val status: Status,
    open val message: String? = null
) {
    companion object {
        fun success(message: String? = null) = JsonResult(status = Status.SUCCESS, message = message)
        fun error(message: String? = null) = JsonResult(status = Status.ERROR, message = message)
    }

    enum class Status(val code: Int) {
        SUCCESS(0), ERROR(-500)
    }
}