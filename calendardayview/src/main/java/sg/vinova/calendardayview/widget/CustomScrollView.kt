package sg.vinova.calendardayview.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ScrollView

class CustomScrollView : ScrollView {
    // if false => can't scroll => focus event
    var mScrollable : Boolean = false
        set(value) {
            Log.d("mScrollable: ", if(mScrollable) "true"  else "false")
            field = value
        }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        val action = ev?.action
//        return when(action){
//            MotionEvent.ACTION_DOWN -> super.onTouchEvent(ev)
//            MotionEvent.ACTION_UP -> super.onTouchEvent(ev)
//            MotionEvent.ACTION_MOVE -> true
//            MotionEvent.ACTION_CANCEL-> super.onTouchEvent(ev)
//
//            else -> false
//        }
//    }
//
//    override fun onTouchEvent(ev: MotionEvent?): Boolean {
//        super.onTouchEvent(ev)
//        return true
//    }
}