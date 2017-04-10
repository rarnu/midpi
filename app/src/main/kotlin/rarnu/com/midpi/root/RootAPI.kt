package rarnu.com.midpi.root

import android.content.Context
import android.util.Log
import java.io.File

/**
 * Created by rarnu on 11/21/16.
 */
object RootAPI {

    fun mount(): Boolean {
        val cmd = "mount -o remount,rw /system"
        var b = true
        val ret = RootUtils.runCommand(cmd, true)
        Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
        if (ret.error != "" && ret.error.toLowerCase().contains("denied")) {
            b = false
        }
        return b
    }

    fun isSystemRW(): Boolean {
        val cmd = "mount"
        val ret = RootUtils.runCommand(cmd, true)
        Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
        val sl = ret.result.split("\n".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
        var b = sl.any { it.contains(" /system") && it.contains("ext4") && it.contains("rw") }
        if (!b) {
            val ret2 = RootUtils.runCommand(cmd, false)
            val sl2 = ret2.result.split("\n".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
            b = sl2.any { it.contains(" /system") && it.contains("ext4") && it.contains("rw") }
        }
        return b
    }

    fun writeFile(filePath: String?, text: String?, perm: Int): Boolean {
        // writeFile
        val CACHE = "/sdcard/.tmp/"
        var b = false
        val fCache = File(CACHE)
        if (!fCache.exists()) {
            fCache.mkdirs()
        }
        val tmpPath = CACHE + filePath?.substring(filePath.lastIndexOf("/") + 1)
        val tmpPathEx = filePath + ".tmp"
        try {
            FileUtils.rewriteFile(tmpPath, text)
        } catch (e: Exception) {

        }

        var modStr = perm.toString()
        while (modStr.length < 3) {
            modStr = "0" + modStr
        }
        val fTmp = File(tmpPath)
        if (fTmp.exists()) {
            var ret: RootUtils.CommandResult = RootUtils.runCommand("cp $tmpPath $tmpPathEx", true)
            Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
            if (ret.error == "") {
                ret = RootUtils.runCommand("chmod $modStr $tmpPathEx", true)
                Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
            }
            if (ret.error == "") {
                ret = RootUtils.runCommand("cp $tmpPathEx $filePath", true)
                Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
            }
            if (ret.error == "") {
                ret = RootUtils.runCommand("chmod $modStr $filePath", true)
                Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
            }
            b = ret.error == ""
        }
        return b
    }

    fun getDensity(): Int {
        val cmd = "wm density"
        val ret = RootUtils.runCommand(cmd, false)
        Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
        val str = ret.result.replace("Physical density:", "").trim {  it <= ' ' }
        return str.toInt()
    }

    fun setDensity(value: Int): Boolean {
        val list = BuildPropUtils.buildProp
        list!!.forEach { if (it.buildName == "ro.sf.lcd_density") { it.buildValue = "$value" } }
        val bProp = BuildPropUtils.setBuildProp(list)
        if (bProp) {
            var ret: RootUtils.CommandResult = RootUtils.runCommand("wm density $value", true)
            Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
            if (ret.error == "") {
                ret = RootUtils.runCommand("reboot", true)
                Log.e("RootAPI", "result: ${ret.result}, error: ${ret.error}")
                return true
            }
        }
        return false
    }
}
