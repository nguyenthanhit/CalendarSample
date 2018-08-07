package sg.vinova.calendarsample

import android.app.Application
import com.facebook.stetho.Stetho

class CalendarApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}