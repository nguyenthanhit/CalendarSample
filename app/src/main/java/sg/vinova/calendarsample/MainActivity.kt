package sg.vinova.calendarsample

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sg.vinova.calendardayview.model.Event
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        scrollView?.requestDisallowInterceptTouchEvent(false)

        val events = arrayListOf<Event>()
        val timeStart = Calendar.getInstance()
        timeStart.set(Calendar.HOUR_OF_DAY, 2)
        timeStart.set(Calendar.MINUTE, 0)
        val timeEnd = timeStart.clone() as Calendar
        timeEnd.set(Calendar.HOUR_OF_DAY, 3)
        timeEnd.set(Calendar.MINUTE, 30)
        val event = Event(1, timeStart, timeEnd, "Event", "Hockaido",
                color = ContextCompat.getColor(this, R.color.md_deep_orange_300_dark))

        events.add(event)


//        val a = Calendar.getInstance()
//        a.set(Calendar.HOUR_OF_DAY, 16)
//        a.set(Calendar.MINUTE, 0)
//        val b = timeStart.clone() as Calendar
//        b.set(Calendar.HOUR_OF_DAY, 17)
//        b.set(Calendar.MINUTE, 0)
//        val event1 = Event(1, a, b, "Event", "Hockaido",
//                color = ContextCompat.getColor(this, R.color.blue_55))
//
//        events.add(event1)

        calendarView?.setEvent(events)
    }
}
