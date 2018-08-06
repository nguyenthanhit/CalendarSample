package sg.vinova.calendardayview.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_event.view.*
import sg.vinova.calendardayview.R
import sg.vinova.calendardayview.event.IEvent
import sg.vinova.calendardayview.helper.ScreenUtils
import sg.vinova.calendardayview.model.Event


/* *
 *  Created by JAY on 01/08/2018
 */

class EventView : FrameLayout {
    private lateinit var mEvent: Event
    private lateinit var params: FrameLayout.LayoutParams
    private lateinit var eventListener: IEvent
    private var dy = 0f

    constructor(context: Context?, eventListener: IEvent) : super(context) {
        this.eventListener = eventListener
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        LayoutInflater.from(context).inflate(R.layout.view_event, this, true)

        eventContainer.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    eventListener.onBeginMoveEvent()
                    dy = view.y - motionEvent.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                            .y(motionEvent.rawY + dy)
                            .setDuration(0)
                            .start()
                }
                MotionEvent.ACTION_UP -> {
                    eventListener.onStopMoveEvent()
                }
            }
            true
        }

        ivDownArrow.setOnTouchListener { view, motionEvent ->

            val y = motionEvent.rawY
            val location = IntArray(2)
            tvEvent.getLocationOnScreen(location)
            val totalEventY = tvEvent.height + location[1]

            if ((totalEventY - y in 1..50) || (
                            y - totalEventY >= 50 && y - totalEventY > 0)) {
                when (motionEvent.action) {
                    MotionEvent.ACTION_MOVE -> {
                       // update height event
                        tvEvent.layoutParams.height += (y - totalEventY).toInt()
                        tvEvent.requestLayout()
                    }
                    MotionEvent.ACTION_UP -> {
                        eventListener.onStopMoveEvent()
                    }
                }
            }
            true
        }

        ivTopArrow.setOnTouchListener { view, motionEvent ->
            Log.d("Thanh", "Up up up up ")
            true
        }
    }

    fun setPosition(rect: Rect) {
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        params.leftMargin = rect.left
        layoutParams = params
    }

    fun setEvent(event: Event, rect: Rect) {
        mEvent = event
        tvEvent.apply {
            height = rect.height()
            text = event.title
            setBackgroundColor(event.color)
        }

        eventContainer.apply {
            (layoutParams as FrameLayout.LayoutParams).apply {
                this.topMargin = rect.top - ScreenUtils.dpToPx(24)
            }
        }
    }
}