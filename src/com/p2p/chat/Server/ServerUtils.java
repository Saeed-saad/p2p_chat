package com.p2p.chat.Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.p2p.chat.event.Event;

/**
 * ��Server���а�װ�Ĺ�����
 * @author Administrator
 *
 */
public class ServerUtils {

	public static final Integer sendport = 8888;
	
	public static final Integer listenport = 8889;
	
	static Logger log = Logger.getLogger(ServerUtils.class);
	
	private Server server;

	/**
	 * ʵ�ֵ���ģʽ
	 */
	private static ServerUtils serverUtils = new ServerUtils();
	
	private ServerUtils(){
		try {
			this.server = new Server(sendport,listenport);
		} catch (IOException e) {
			log.warn("����������ʧ��",e);
			throw new RuntimeException(e);
		}
		log.info("�����������ɹ�");
	}
	
	public static ServerUtils getSU(){
		return serverUtils;
	}
	/**
	 * �㲥�¼�
	 * @param event
	 */
	public void broadcast(Event event){
		server.setBusy(true);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			event.setDirection(Event.Direction.CLIENT);
			oos.writeObject(event);
			byte[] buffer = bos.toByteArray();
			log.debug("�㲥����д�뻺��ɹ���" + event);
			server.broadcast(new DatagramPacket(buffer, buffer.length));
		} catch (IOException e) {
			log.warn("���������͹㲥ʧ��",e);
			e.printStackTrace();
		}
		server.setBusy(false);
		log.info("���������͹㲥�ɹ���" + event);
	}
	
	/**
	 * ���Ͱ���ָ���û�
	 * @param event �¼���Ϣ
	 * @param ip Ŀ��ip��ַ
	 */
	public void send(Event event,String ip){
		server.setBusy(true);
		event.setDirection(Event.Direction.CLIENT);
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(event);
			byte[] buffer = bos.toByteArray();
			log.debug("�������ݳɹ�д�뻺�棺" + event);
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
			server.send(dp,ip);
		} catch (IOException e) {
			log.warn("��������ʧ��", e);
			e.printStackTrace();
		}
		server.setBusy(false);
		log.info("�������ݳɹ���" + event);
	}
	/**
	 * �����¼�
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Event receive() throws IOException, ClassNotFoundException{
		DatagramPacket dp = null;
		dp = server.receive();
		if(dp == null) return null;
		log.trace("�յ����ݰ���length=" + dp.getLength() + " from=" + dp.getAddress().getHostAddress());
		ByteArrayInputStream bis = new ByteArrayInputStream(dp.getData());
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object o = ois.readObject();
		log.info("�������յ��¼���" + o);
		return (Event) o;
	}
	/**
	 * �رշ�����
	 */
	public void closeServer(){
		log.info("��ʼ�رշ�����");
		this.server.close();
		log.info("�����رշ�����");
	}
	/**
	 * �������Ƿ�æ
	 * @return
	 */
	public boolean isServerBusy(){
		return this.server.isBusy();
	}
}
