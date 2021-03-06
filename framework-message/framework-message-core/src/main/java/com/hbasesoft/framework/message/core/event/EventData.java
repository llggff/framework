/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.framework.message.core.event;

import java.util.HashMap;

/**
 * <Description> <br>
 * 
 * @author 王伟<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年2月21日 <br>
 * @since V1.0<br>
 * @see com.hbasesoft.framework.message.core.event <br>
 */
public class EventData extends HashMap<String, Object> {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 2323769185641461392L;

    private String eventId;

    public String getParameter(String key) {
        return (String) this.get(key);
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return eventId <br>
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Description: <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param eventId <br>
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
