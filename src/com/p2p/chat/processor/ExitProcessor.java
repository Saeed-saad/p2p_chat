package com.p2p.chat.processor;

import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.EventContainer;

/**
 * �˳�����������رշ�����
 * @author Administrator
 *
 */
public class ExitProcessor extends AbstractProcessor {
	
	static Logger log = Logger.getLogger(ExitProcessor.class);

	@Override
	protected void doSend() {
		log.trace("��ʼ�رշ�����,�������¼�����:" + event);
		EventContainer.getEC().setLocked(true);
		do{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				log.warn("�˳��������ȴ�����ϣ�", e);
			}
		}while(ServerUtils.getSU().isServerBusy());
		ServerUtils.getSU().closeServer();
		log.info("�������Ѿ��رգ�" + event);
	}

	@Override
	protected void doReceive() {
		doSend();
	}

}
