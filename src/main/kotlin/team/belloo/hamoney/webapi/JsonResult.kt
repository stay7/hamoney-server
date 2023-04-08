package team.belloo.hamoney.webapi

open class JsonResult(
    open val status: Status,
    open val message: String? = null
) {
    companion object {
        fun success() = JsonResult(status = Status.SUCCESS)
        fun error() = JsonResult(status = Status.ERROR)
    }

    enum class Status(val code: Int) {
        SUCCESS(0), ERROR(-500)
    }
}