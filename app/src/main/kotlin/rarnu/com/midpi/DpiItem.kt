package rarnu.com.midpi

/**
 * Created by rarnu on 4/9/17.
 */
data class DpiItem(val dpi: Int, var sel: Boolean) {
    companion object {
        var DPIS = arrayListOf(
                DpiItem(640, false),
                DpiItem(620, false),
                DpiItem(600, false),
                DpiItem(580, false),
                DpiItem(560, false),
                DpiItem(540, false),
                DpiItem(520, false),
                DpiItem(500, false),
                DpiItem(480, false),
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
                DpiItem(260, false),
                DpiItem(240, false)
        )
    }
}