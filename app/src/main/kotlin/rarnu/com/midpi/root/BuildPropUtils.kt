package rarnu.com.midpi.root

import java.io.Serializable

object BuildPropUtils {

    data class BuildPropInfo(
            var buildName: String?,
            var buildValue: String?
    ) : Serializable {
        companion object {
            fun parse(str: String?): BuildPropInfo =
                    BuildPropInfo(
                            str?.substring(0, str.indexOf("="))?.trim { it <= ' ' },
                            str?.substring(str.indexOf("=") + 1)?.trim { it <= ' ' }
                    )
        }
    }

    private val PATH_BUILD_PROP = "/system/build.prop"

    val buildProp: MutableList<BuildPropInfo>?
        get() {
            var list: MutableList<BuildPropInfo>? = null
            try {
                val file = FileUtils.readFile(PATH_BUILD_PROP)
                if (file != null && file.size != 0) {
                    list = arrayListOf<BuildPropInfo>()
                    file.filter { f -> f.trim { it <= ' ' } != "" && !f.trim { it <= ' ' }.startsWith("#") && f.trim { it <= ' ' }.contains("=") }.mapTo(list) { BuildPropInfo.parse(it) }
                }
            } catch (e: Exception) {

            }
            return list
        }

    fun setBuildProp(list: List<BuildPropInfo>?): Boolean {
        var ret = false
        var str = ""
        for ((buildName, buildValue) in list!!) {
            str += "$buildName=$buildValue\n"
        }
        try {
            RootAPI.mount()
            ret = RootAPI.writeFile(PATH_BUILD_PROP, str, 755)
        } catch (e: Exception) {

        }

        return ret
    }
}
