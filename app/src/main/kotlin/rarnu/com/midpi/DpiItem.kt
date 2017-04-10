package rarnu.com.midpi

/**
 * Created by rarnu on 4/9/17.
 */
data class DpiItem(val dpi: Int, var sel: Boolean) {
    companion object {
        var DPIS = arrayListOf(
                DpiItem(480, true),
                DpiItem(460, false),
                DpiItem(440, false),
                DpiItem(420, false),
                DpiItem(400, false),
                DpiItem(380, false),
                DpiItem(360, false),
                DpiItem(340, false),
                DpiItem(320, false),
                DpiItem(300, false),
                DpiItem(280, false),
                DpiItem(260, false)
        )
    }
}