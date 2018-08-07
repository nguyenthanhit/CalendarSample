package sg.vinova.calendardayview.event

import android.view.View
import sg.vinova.calendardayview.model.Event

/* *
 *  Created by JAY on 01/08/2018
 */

interface IEvent {
    fun onBeginMoveEvent()
    fun onStopMoveEvent()
    fun onFocus(view: View)
    fun onLongClickEvent()
    fun onFocusOutSide()
}