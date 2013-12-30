package com.p2p.chat.processor;

import com.p2p.chat.event.Event;

/**
 * �������ĳ���
 * @author Administrator
 *
 */
public abstract class AbstractProcessor implements Runnable {

	/**
	 * ������Ҫ������¼�
	 */
	protected Event event;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Override
	public void run() {
		switch(event.getDirection()){
		case CLIENT:
			doReceive();
			break;
		case SERVER:
			doSend();
			break;
		default:
			break;
		}
	}

	/**
	 * ����Ҫ��������Ϣ
	 */
	protected abstract void doSend();
	
	/**
	 * �����յ�����Ϣ
	 */
	protected abstract void doReceive();

}
