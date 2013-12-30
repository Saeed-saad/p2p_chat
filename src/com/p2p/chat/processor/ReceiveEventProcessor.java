package com.p2p.chat.processor;

import java.io.IOException;
import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.EventContainer;
import com.p2p.chat.event.Event;

public class ReceiveEventProcessor implements Runnable {
	
	static Logger log = Logger.getLogger(ReceiveEventProcessor.class);

	ServerUtils su = ServerUtils.getSU();
	EventContainer ec = EventContainer.getEC();
	
	@Override
	public void run() {
		log.info("�˿��¼�������������ʼִ��");
		while(!Thread.interrupted()){
			try {
				Event event = su.receive();
				ec.push(event);
				log.info("�ɹ��������ݱ���ѹ���¼����У�" + event);
			} catch (ClassNotFoundException e) {
				log.warn("�������ݱ���Ϣ����",e);
				//e.printStackTrace();
			} catch (IOException e) {
				log.warn("�������ݱ����� �� �������ر�",e);
				//e.printStackTrace();
			}
		}
		log.info("�˿��¼���������������ִ��");
	}

}
