package sg.vinova.calendardayview.model

import java.util.*

/* *
 *  Created by JAY on 01/08/2018
 */

data class Event(var id: Int = 0, var startTime: Calendar? = null,
                 var endTime: Calendar? = null, var title: String = "", var description: String = "",
                 var importId: String = "", var flags: Int = 0, var repeatLimit: Int = 0, var repeatRule: Int = 0,
                 var offset: String = "", var isDstIncluded: Boolean = false, var parentId: Int = 0, var lastUpdated: Long = 0L,
                 var color: Int = 0, var location: String = "", var isPastEvent: Boolean = false)