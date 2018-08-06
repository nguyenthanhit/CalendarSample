package sg.vinova.calendardayview.event

import sg.vinova.calendardayview.model.Event

/* *
 *  Created by JAY on 01/08/2018
 */

interface IEvent {
    fun onBeginMoveEvent()
    fun onStopMoveEvent()
}