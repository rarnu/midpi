package rarnu.com.midpi

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView

/**
 * Created by rarnu on 4/9/17.
 */
class MessageDialog : Dialog, View.OnClickListener {

    class Builder {
        var dialog: MessageDialog? = null
        constructor(ctx: Context) {
            dialog = MessageDialog(ctx)
        }
        fun setTitle(txt: String?): Builder {
            dialog?.tvTitle?.text = txt
            return this
        }
        fun setTitle(res: Int): Builder {
            dialog?.tvTitle?.setText(res)
            return this
        }
        fun setMessage(txt: String?): Builder {
            dialog?.tvMsg?.text = txt
            return this
        }
        fun setMessage(res: Int): Builder {
            dialog?.tvMsg?.setText(res)
            return this
        }
        fun setMikuClickListener(listener: OnMikuClickListener?): Builder {
            dialog?.listener = listener
            return this
        }
        fun show() {
            dialog?.show()
        }
    }

    interface OnMikuClickListener {
        fun onMikuOk(sender: MessageDialog)
    }

    private var tvTitle: TextView? = null
    private var tvMsg: TextView? = null
    private var listener: OnMikuClickListener? = null
    private var btnOK: Button? = null


    constructor(ctx: Context): super(ctx) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_message)
        setCanceledOnTouchOutside(false)
        tvTitle = findViewById(R.id.tvTitle) as TextView?
        tvMsg = findViewById(R.id.tvMsg) as TextView?
        btnOK = findViewById(R.id.btnOK) as Button?
        btnOK?.setOnClickListener(this)
        val color = ctx.resources.getColor(ColorAdapter.COLORS[Config.themeId].color, ctx.theme)
        tvTitle?.setBackgroundColor(color)
        btnOK?.setBackgroundColor(color)
    }

    fun setMikuClickListener(listener: OnMikuClickListener) {
        this.listener = listener
    }

    override fun onClick(v: View?) {
        dismiss()
        listener?.onMikuOk(this)
    }

}