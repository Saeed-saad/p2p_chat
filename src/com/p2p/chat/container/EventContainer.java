package com.p2p.chat.container;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.p2p.chat.event.Event;

/**
 * ������пͻ���server�˲������¼�
 * @author Administrator
 *
 */
public class EventContainer {
	
	static Logger log = Logger.getLogger(EventContainer.class);
	
	/**
	 * ʵ�ֵ���ģʽ
	 */
	private static EventContainer eventContainer = new EventContainer();
	
	private EventContainer(){
		log.trace("�����¼�����");
	}
	
	public static EventContainer getEC(){
		return eventContainer;
	}

	/**
	 * ����¼��Ķ���
	 */
	Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();
	
	/**
	 * �����Ƿ��������������������������¼�
	 */
	boolean isLocked = false;
	
	/**
	 * ���¼�ѹ�����
	 * @param event
	 */
	public synchronized void push(Event event){
		if(event != null && !isLocked()){
			eventQueue.add(event);
			log.trace("���¼�������ѹ���¼���" + event);
		}else{
			log.info("�¼������ѱ�����");
		}
	}
	/**
	 * ���¼���������
	 * @return
	 */
	public Event pop(){
		log.trace("���¼������е����¼���" + eventQueue.peek());
		return eventQueue.poll();
	}
	/**
	 * �¼������Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty(){
		return eventQueue.isEmpty();
	}
	/**
	 * ����¼������е������¼�
	 */
	public void clear(){
		log.trace("����¼�����");
		eventQueue.clear();
	}

	public boolean isLocked() {
		return isLocked;
	}

	public synchronized void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
}
