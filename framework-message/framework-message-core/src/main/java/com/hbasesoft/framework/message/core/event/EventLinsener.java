/**************************************************************************************** 
 Copyright © 2003-2012 hbasesoft Corporation. All rights reserved. Reproduction or       <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.hbasesoft.framework.message.core.event;

import com.hbasesoft.framework.common.utils.CommonUtil;
import com.hbasesoft.framework.common.utils.bean.SerializationUtil;
import com.hbasesoft.framework.common.utils.logger.LoggerUtil;
import com.hbasesoft.framework.common.utils.logger.TransManager;
import com.hbasesoft.framework.log.core.TransLogUtil;
import com.hbasesoft.framework.message.core.MessageSubscriber;

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
public interface EventLinsener extends MessageSubscriber {

    String[] events();

    void onEmmit(String event, EventData data);

    /**
     * Description: 是否为订阅消息<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @return <br>
     */
    default boolean subscriber() {
        return false;
    }

    /**
     * Description: 接收到消息 <br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param data <br>
     */
    default void onMessage(String channel, byte[] data) {
        EventData eventData = SerializationUtil.unserial(EventData.class, data);
        LoggerUtil.info("[{0}]接收到[event={1},data={2}]事件", Thread.currentThread().getId(), channel, eventData);

        String transId = eventData == null ? CommonUtil.getTransactionID() : eventData.getEventId();
        TransManager manager = TransManager.getInstance();
        manager.push(transId, System.currentTimeMillis());

        String methodName = this.getClass().getName() + "<onMessage>";

        try {
            TransLogUtil.before(methodName, new Object[] {
                channel, eventData
            });
            onEmmit(channel, eventData);
            TransLogUtil.afterReturning(methodName, null);
        }
        catch (Exception e) {
            TransLogUtil.afterThrowing(methodName, e);
        }
        finally {
            manager.pop();
        }
    }

    /**
     * Description: 开始订阅<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param subscribeChannels <br>
     */
    default void onSubscribe(String channel, int subscribeChannels) {
        LoggerUtil.info("开始监听{0}事件，监听者数目{1}", channel, subscribeChannels);
    }

    /**
     * Description: 取消订阅<br>
     * 
     * @author 王伟<br>
     * @taskId <br>
     * @param subscribedChannels <br>
     */
    default void onUnsubscribe(String channel, int subscribedChannels) {
        LoggerUtil.info("取消监听{0}事件，监听者数目{1}", channel, subscribedChannels);
    }
}
