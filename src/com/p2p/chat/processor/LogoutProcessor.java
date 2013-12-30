package com.p2p.chat.processor;

import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.UserContainer;
import com.p2p.chat.entity.User;
import com.p2p.chat.event.LogoutEvent;

public class LogoutProcessor extends AbstractProcessor {

	static Logger log = Logger.getLogger(LogoutProcessor.class);

	@Override
	protected void doSend() {
		log.trace("��ʼ�㲥������Ϣ��" + event);
		UserContainer.getUC().remove(((LogoutEvent)event).getUser().getIp());
		log.info("���û��������Ƴ�" + ((LogoutEvent)event).getUser());
		ServerUtils.getSU().broadcast(event);
		log.trace("�����㲥������Ϣ��" + event);		
	}

	@Override
	protected void doReceive() {
		log.trace("�����յ���������Ϣ��" + event);
		LogoutEvent event = (LogoutEvent) getEvent();
		User user = event.getUser();
		UserContainer uc = UserContainer.getUC();
		if(uc.contains(user)){
			uc.remove(user.getIp());
			log.info("�û����������Ƴ���" + user);
		}else{
			log.info("�û��������в����ڣ�" + user);			
		}
	}

}
