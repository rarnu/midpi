package rarnu.com.midpi

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by rarnu on 4/9/17.
 */
object Config {

    private val KEY_THEME = "key_theme"
    var themeId = 0

    fun getColorTheme(ctx: Context): Int = PreferenceManager.getDefaultSharedPreferences(ctx).getInt(KEY_THEME, 0)

    fun setColorTheme(ctx: Context, value: Int) = PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt(KEY_THEME, value).apply()

}