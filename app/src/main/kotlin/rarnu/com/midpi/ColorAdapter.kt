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
class ColorAdapter: BaseAdapter {

    data class ColorItem(val color: Int, val name: String?, var sel: Boolean)

    companion object {
        var COLORS = arrayListOf(
                ColorItem(R.color.miku, "MIKU", true),
                ColorItem(R.color.rin, "RIN", false),
                ColorItem(R.color.len, "LEN", false),
                ColorItem(R.color.luka, "LUKA", false),
                ColorItem(R.color.meiko, "MEIKO", false),
                ColorItem(R.color.kaito, "KAITO", false)
        )
    }

    private val ctx: Context

    constructor(ctx: Context) {
        this.ctx = ctx
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var v = convertView
        if (v == null) v = LayoutInflater.from(ctx).inflate(R.layout.item_color, parent, false)
        var holder = v?.tag as ColorHolder?
        if (holder == null) {
            holder = ColorHolder()
            holder.layBg = v?.findViewById(R.id.layBg) as RelativeLayout?
            holder.vColor = v?.findViewById(R.id.vColor)
            holder.tvColorName = v?.findViewById(R.id.tvColorName) as TextView?
            v?.tag = holder
        }
        val item = COLORS[position]
        holder.vColor?.setBackgroundColor(ctx.resources.getColor(item.color, ctx.theme))
        holder.tvColorName?.text = item.name
        if (!item.sel) holder.layBg?.setBackgroundColor(Color.WHITE) else holder.layBg?.setBackgroundColor(ctx.resources.getColor(item.color, ctx.theme))
        return v
    }

    override fun getItem(position: Int): ColorItem = COLORS[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = COLORS.size

    inner class ColorHolder {
        internal var layBg: RelativeLayout? = null
        internal var vColor: View? = null
        internal var tvColorName: TextView? = null
    }

    fun selectColor(position: Int) {
        for (i in 0..COLORS.size - 1) COLORS[i].sel = i == position
        notifyDataSetChanged()
    }
}