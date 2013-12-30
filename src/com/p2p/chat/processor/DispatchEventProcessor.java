package com.p2p.chat.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.p2p.chat.container.EventContainer;
import com.p2p.chat.event.Event;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

/**
 * �¼��ַ�������������Ӧ���¼�������Ӧ�Ĵ�����
 * @author Administrator
 *
 */
public class DispatchEventProcessor implements Runnable {
	
	static Logger log = Logger.getLogger(DispatchEventProcessor.class);

	/**
	 * �����¼���������Ӧ��������ӳ���ϵ
	 */
	@SuppressWarnings("rawtypes")
	static Map<Class,AbstractProcessor> map = new HashMap<>();
	/**
	 * ���д��������̳߳�
	 */
	ExecutorService exe = Executors.newFixedThreadPool(5);
	/**
	 * ����¼�������
	 */
	EventContainer ec = EventContainer.getEC();
	/**
	 * ���¼��봦����ӳ�������ӳ��
	 * @param clazz
	 * @param run
	 */
	public static void register(Class<? extends Event> clazz,AbstractProcessor run){
		if(!map.containsKey(clazz)){
			map.put(clazz, run);
			log.trace("�ɹ����¼��ַ���������ע���ϵ��" + clazz.getSimpleName() + "<===>" + run.getClass().getSimpleName());
		}
	}
	
	/**
	 * ������������ж����е��¼�
	 */
	public void clearEC(){
		log.info("��ʼ����¼������е������¼�");
		ec.setLocked(true);
		while(!ec.isEmpty()){
			Event event = ec.pop();
			AbstractProcessor pro = map.get(event.getClass());
			pro.setEvent(event);
			exe.execute(pro);
		}
		log.info("�����е��¼��Ѿ����");
	}
	
	/**
	 * �ȴ����ر��̳߳�
	 * @param pool
	 */
	void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					log.warn("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public void run() {
		log.info("�¼��ַ���������ʼִ��");
		while(!Thread.interrupted()){
			if(ec.isEmpty()) continue;
			Event event = ec.pop();
			AbstractProcessor pro = map.get(event.getClass());
			pro.setEvent(event);
			exe.execute(pro);
		}
		if(Thread.interrupted()){
			clearEC();
		}
		List<Runnable> list = exe.shutdownNow();
		log.info("����ִ�еĺ�̨�̣߳�" + list);
		shutdownAndAwaitTermination(exe);
		log.info("�¼��ַ�����������ִ��");
	}

}
