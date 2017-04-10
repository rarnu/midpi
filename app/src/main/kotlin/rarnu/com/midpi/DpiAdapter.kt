package rarnu.com.midpi

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView

/**
 * Created by rarnu on 4/9/17.
 */
class DpiAdapter: BaseAdapter {

    private var ctx: Context
    private var list: MutableList<DpiItem>? = null

    constructor(ctx: Context, list: MutableList<DpiItem>?) {
        this.ctx = ctx
        this.list = list
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var v = convertView
        if (v ==  null) v = LayoutInflater.from(ctx).inflate(R.layout.item_dpi, parent, false)
        var holder = v?.tag as DpiHolder?
        if (holder == null) {
            holder = DpiHolder()
            holder.laybg = v?.findViewById(R.id.layBg) as RelativeLayout?
            holder.tvDpi = v?.findViewById(R.id.tvDpi) as TextView?
            v?.tag = holder
        }
        val item = list!![position]
        holder.tvDpi?.text = "${item.dpi}"
        holder.laybg?.setBackgroundColor(if (item.sel) ctx.resources.getColor(ColorAdapter.COLORS[Config.themeId].color, ctx.theme) else Color.WHITE)
        return v
    }

    override fun getItem(position: Int): DpiItem = list!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list!!.size

    inner class DpiHolder {
        internal var laybg: RelativeLayout? = null
        internal var tvDpi: TextView? = null
    }
}