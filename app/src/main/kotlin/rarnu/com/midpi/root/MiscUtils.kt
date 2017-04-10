package rarnu.com.midpi.root

import android.content.Context
import android.content.pm.PackageInfo

/**
 * Created by rarnu on 4/9/17.
 */
object MiscUtils {

    fun isMIUI(ctx: Context?): Boolean {
        val pm = ctx?.packageManager
        val pkgs = arrayOf("com.miui.core", "com.miui.system")
        var isMIUI = true
        var pi: PackageInfo?
        for (s in pkgs) {
            try {
                pi = pm?.getPackageInfo(s, 0)
                isMIUI = pi != null
            } catch (e: Exception) {
                isMIUI = false
            }
            if (!isMIUI) {
                break
            }
        }
        return isMIUI
    }

}