package com.p2p.chat.processor;

import org.apache.log4j.Logger;

import com.p2p.chat.Server.ServerUtils;
import com.p2p.chat.container.UserContainer;
import com.p2p.chat.entity.User;
import com.p2p.chat.event.LoginEvent;

/**
 * ���д������е�login�¼�
 * @author Administrator
 *
 */
public class LoginProcessor extends AbstractProcessor {

	static Logger log = Logger.getLogger(LoginProcessor.class);
	
	/**
	 * ��������緢�͵�Login֪ͨ
	 * @param event
	 */
	public void doSend(){
		log.trace("��ʼ����㲥������Ϣ��" + event);
		ServerUtils.getSU().broadcast(event);
		log.trace("��������㲥������Ϣ��" + event);
	}
	/**
	 * ����������ܵ���login֪ͨ
	 * @param event
	 */
	public void doReceive(){
		log.trace("��ʼ�����յ���������Ϣ��" + event);
		User user = ((LoginEvent)event).getUser();
		UserContainer uc = UserContainer.getUC();
		if(!uc.contains(user)){
			uc.add(user.getIp(), user);
			log.info("�ɹ������ߵ��û���ӽ���������" + user);
		}else{
			log.info("�û��Ѿ����������У�" + user);
		}
	}

}
