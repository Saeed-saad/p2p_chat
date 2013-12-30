package com.p2p.chat.event;

import com.p2p.chat.entity.User;
import com.p2p.chat.event.Event.Direction;

/**
 * �¼�������
 * @author Administrator
 *
 */
public class EventBuilder {
	
	/**
	 * ����һ����¼�¼����¼���������������
	 * @param user ��½���û�
	 * @return ����õĵ�¼�¼�
	 */
	public static LoginEvent buildLoginEvent(User user){
		LoginEvent event = null;
		event = new LoginEvent(user);
		event.setDirection(Direction.SERVER);
		return event;
	}
	/**
	 * ����һ�������¼����¼���������������
	 * @param user
	 * @return
	 */
	public static LogoutEvent buildLogoutEvent(User user){
		LogoutEvent event = null;
		event = new LogoutEvent(user);
		event.setDirection(Direction.SERVER);
		return event;
	}

	/**
	 * ����һ���˳��¼����¼���������������
	 * @return
	 */
	public static ExitEvent buildExitEvent(){
		ExitEvent event = new ExitEvent();
		event.setDirection(Direction.SERVER);
		return event;
	}
}
