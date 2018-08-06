package sg.vinova.calendardayview.helper

import android.content.res.Resources

/* *
 *  Created by JAY on 02/08/2018
 */
 
object ScreenUtils {
    fun dpToPx(dp: Int) : Int{
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}