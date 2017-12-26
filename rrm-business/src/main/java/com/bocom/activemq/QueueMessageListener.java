package com.bocom.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QueueMessageListener implements MessageListener {
	
    private static final Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);

//    @Resource
//    private AppDataService appDataService; 
    
	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                logger.debug("接受MQ的Queue消息:{}",textMessage.getText());
//            	appDataService.handleMessage(textMessage.getText());
            } catch (JMSException e) {
    	    	logger.error("接收MQ的Queue消息出错:{}", e);
            }
        }
	}

}
