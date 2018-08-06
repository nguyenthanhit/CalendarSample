package sg.vinova.calendardayview.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_day_calendar.view.*
import org.joda.time.DateTime
import sg.vinova.calendardayview.R
import sg.vinova.calendardayview.event.IEvent
import sg.vinova.calendardayview.helper.Formatter
import sg.vinova.calendardayview.helper.ScreenUtils
import sg.vinova.calendardayview.model.Event
import java.util.*

/* *
 *  Created by JAY on 01/08/2018
 */

class CalendarDayView : FrameLayout, IEvent {

    private var mDayHeight = 0

    private var mEventMarginLeft = 0

    private var mHourWidth = 120

    private var mTimeHeight = 120

    private var mSeparateHourHeight = 0

    private var mStartHour = 0

    private var mEndHour = 23

    private lateinit var events: ArrayList<Event>

    lateinit var inflater: LayoutInflater


    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        events = arrayListOf()
        mDayHeight = resources.getDimensionPixelSize(R.dimen.dayHeight)
        inflater = LayoutInflater.from(context)
        LayoutInflater.from(context).inflate(R.layout.view_day_calendar, this, true)
        if (attrs != null) {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarDayView)
            mEventMarginLeft = typeArray.getDimensionPixelSize(R.styleable.CalendarDayView_eventMarginLeft,
                    mEventMarginLeft)
            mDayHeight = typeArray.getDimensionPixelSize(R.styleable.CalendarDayView_dayHeight, mDayHeight)
            mStartHour = typeArray.getInt(R.styleable.CalendarDayView_startHour, mStartHour)
            mEndHour = typeArray.getInt(R.styleable.CalendarDayView_endHour, mEndHour)
            typeArray.recycle()
        }

        refresh()
    }

    private fun refresh() {
        drawDayViews()

        drawEvents()
    }

    private fun drawDayViews() {
        dayViewContainer.removeAllViews()
        var dayView: DayView? = null
        val hourDateTime = DateTime().withDate(2000, 1, 1)
                .withTime(0, 0, 0, 0)
        for (i in mStartHour..mEndHour) {
            dayView = DayView(context)
            val formattedHours = Formatter.convertHours(context!!, hourDateTime.withHourOfDay(i))

            dayView.setTextHour(formattedHours)
            dayViewContainer.addView(dayView)
        }
        mHourWidth = dayView!!.getHourTextWidth().toInt()
        mTimeHeight = dayView.getHourTextHeight().toInt()
        mSeparateHourHeight = dayView.getSeparateHeight().toInt()
    }


    override fun onBeginMoveEvent() {
        eventContainer.setOnClickListener {
            return@setOnClickListener
        }
    }

    override fun onStopMoveEvent() {
        eventContainer.isClickable = false
    }


    fun setLimitTime(startHour: Int, endHour: Int) {
        mStartHour = startHour
        mEndHour = endHour
        refresh()
    }

    fun setEventClick(event: IEvent) {

    }

    fun setEvent(events: ArrayList<Event>) {
        this.events = events
        refresh()
    }

    private fun drawEvents() {
        eventContainer.removeAllViews()
        for (event in events) {

            val rect = getTimeBound(event)

            // add event view
            val eventView = EventView(context, this)
            eventView.setEvent(event, rect)
            eventView.setPosition(rect)
            eventContainer.addView(eventView, eventView.layoutParams)

        }
    }

    private fun getTimeBound(event: Event): Rect {
        val rect = Rect()
        rect.top = getPositionOfTime(event.startTime!!) + mTimeHeight / 2 + mSeparateHourHeight
        rect.bottom = getPositionOfTime(event.endTime!!) + mTimeHeight / 2 + mSeparateHourHeight
        rect.left = mHourWidth + ScreenUtils.dpToPx(16)
        rect.right = width
        return rect
    }

    private fun getPositionOfTime(calendar: Calendar): Int {
        val hour = calendar.get(Calendar.HOUR_OF_DAY) - mStartHour
        val minute = calendar.get(Calendar.MINUTE)
        return hour * mDayHeight + minute * mDayHeight / 60
    }
}