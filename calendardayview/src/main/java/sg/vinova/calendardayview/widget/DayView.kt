package sg.vinova.calendardayview.widget

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_day.view.*
import sg.vinova.calendardayview.R
import sg.vinova.calendardayview.activity.DetailEventActivity

/* *
 *  Created by JAY on 01/08/2018
 */
 
class DayView: FrameLayout {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_day, this, true)

        dayParentView?.setOnClickListener{
            tvAddNewEvent?.visibility = if (tvAddNewEvent.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        tvAddNewEvent?.setOnClickListener {
            context.startActivity(Intent(context, DetailEventActivity::class.java))
        }
    }

    fun setTextHour(hour: String) {
        tvHour?.text = hour
    }

    fun getHourTextWidth(): Float {
        val param = tvHour.layoutParams as ConstraintLayout.LayoutParams
        val measureTextWidth = tvHour.paint.measureText("12:00 PM")
        return (Math.max(measureTextWidth, param.width.toFloat())
                + param.marginEnd.toFloat()
                + param.marginStart.toFloat())
    }

    fun getHourTextHeight(): Float {
        return StaticLayout("12:00", tvHour.paint, getHourTextWidth().toInt(),
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true).height.toFloat()
    }

    fun getSeparateHeight(): Float = vwDivider.layoutParams.height.toFloat()

}
