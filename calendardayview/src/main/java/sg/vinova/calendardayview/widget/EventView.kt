package sg.vinova.calendardayview.widget

import android.content.Context
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
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
    private var mDetector: GestureDetectorCompat? = null
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
        mDetector = GestureDetectorCompat(context, EventGestureListener())
        LayoutInflater.from(context).inflate(R.layout.view_event, this, true)

        setOnClickListener {
            showArrowButton(false)
            isClickable = false
        }

        eventContainer.setOnTouchListener { _, motionEvent ->
            // set parent's click to prevent show "add event view"
            this@EventView.isClickable = true
            mDetector!!.onTouchEvent(motionEvent)
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    eventListener.onBeginMoveEvent()
                    dy = eventContainer.y - motionEvent.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    eventContainer.animate()
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

        ivDownArrow.setOnTouchListener { _, motionEvent ->
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
            true
        }
    }

    fun setPosition(rect: Rect) {
        // set position parent view
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

    private fun showArrowButton(isShowView: Boolean) {
        if (isShowView) {
            ivTopArrow.visibility = View.VISIBLE
            ivDownArrow.visibility = View.VISIBLE
        } else {
            ivTopArrow.visibility = View.INVISIBLE
            ivDownArrow.visibility = View.INVISIBLE
        }
    }

    inner class EventGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(motionEvent: MotionEvent?) {
            showArrowButton(true)
        }
    }
}