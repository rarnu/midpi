package rarnu.com.midpi

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import rarnu.com.midpi.root.MiscUtils
import rarnu.com.midpi.root.RootAPI

class MainActivity : Activity(), AdapterView.OnItemClickListener, MessageDialog.OnMikuClickListener {

    private var tvCurrent: TextView? = null
    private var gvDpis: GridView? = null
    private var adapter: DpiAdapter? = null
    private var miSave: MenuItem? = null
    private var miAbout: MenuItem? = null
    private var gvColor: GridView? = null
    private var adapterColor: ColorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Config.themeId = Config.getColorTheme(this)
        val color = resources.getColor(ColorAdapter.COLORS[Config.themeId].color, theme)
        actionBar.setBackgroundDrawable(ColorDrawable(color))
        window.statusBarColor = color
        setContentView(R.layout.activity_main)
        tvCurrent = findViewById(R.id.tvCurrent) as TextView?
        gvDpis = findViewById(R.id.gvDpis) as GridView?
        gvColor = findViewById(R.id.gvColor) as GridView?

        adapter = DpiAdapter(this, DpiItem.DPIS)
        gvDpis?.adapter = adapter
        gvDpis?.onItemClickListener = this
        adapterColor = ColorAdapter(this)
        gvColor?.adapter = adapterColor
        gvColor?.onItemClickListener = this

        if (!checkMIUI()) return
        if (!rootAndMount()) return

        getCurrentDensity()
        getCurrentTheme()
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        if (grantResults != null) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                MessageDialog.Builder(this).setTitle(R.string.msg_hint).setMessage(R.string.msg_grant_permission).setMikuClickListener(this).show()
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        when(parent.id) {
            R.id.gvDpis -> {
                for (i in 0..DpiItem.DPIS.size - 1) DpiItem.DPIS[i].sel = i == position
                adapter?.notifyDataSetChanged()
            }
            R.id.gvColor -> {
                Config.themeId = position
                Config.setColorTheme(this, Config.themeId)
                adapterColor?.selectColor(position)
                getCurrentTheme()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        miSave = menu?.add(0, 1, 0, R.string.menu_save)
        miSave?.setIcon(android.R.drawable.ic_menu_save)
        miSave?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        miAbout = menu?.add(0, 2, 1, R.string.menu_about)
        miAbout?.setIcon(android.R.drawable.ic_menu_info_details)
        miAbout?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                val dpi = DpiItem.DPIS.filter { it.sel }[0].dpi
                RootAPI.setDensity(dpi)
            }
            2 -> {
                // TODO: about
                MessageDialog.Builder(this).setTitle(R.string.msg_about).setMessage(R.string.msg_github).show()
            }
        }
        return true
    }

    private fun checkMIUI(): Boolean {
        if (!MiscUtils.isMIUI(this)) {
            MessageDialog.Builder(this).setTitle(R.string.msg_hint).setMessage(R.string.msg_not_miui).setMikuClickListener(this).show()
            return false
        }
        return true
    }

    override fun onMikuOk(sender: MessageDialog) {
        finish()
    }

    private fun rootAndMount(): Boolean {
        val bRoot = RootAPI.mount()
        if (!bRoot) {
            MessageDialog.Builder(this).setTitle(R.string.msg_hint).setMessage(R.string.msg_no_root).setMikuClickListener(this).show()
            return false
        }
        val bRW = RootAPI.isSystemRW()
        if (!bRW) {
            MessageDialog.Builder(this).setTitle(R.string.msg_hint).setMessage(R.string.msg_system_verity).setMikuClickListener(this).show()
            return false
        }
        return true
    }

    private fun getCurrentDensity() {
        val density = RootAPI.getDensity()
        tvCurrent?.text = getString(R.string.view_current_density, density)
        DpiItem.DPIS.forEach { it.sel = it.dpi == density }
        adapter?.notifyDataSetChanged()
    }

    private fun getCurrentTheme() {
        val color = resources.getColor(ColorAdapter.COLORS[Config.themeId].color, theme)
        actionBar.setBackgroundDrawable(ColorDrawable(color))
        window.statusBarColor = color
        (0..ColorAdapter.COLORS.size - 1).forEach { ColorAdapter.COLORS[it].sel = it == Config.themeId }
        adapter?.notifyDataSetChanged()
    }

}
