package com.p2p.chat.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

/**
 * ��������˿ڡ�������Ϣ�Լ�������Ϣ
 * @author Administrator
 *
 */
public class Server {
	
	static Logger log = Logger.getLogger(Server.class);
	
	/**
	 * ���ݱ��׽��֣����͹㲥����Ϣ
	 */
	DatagramSocket sender;
	/**
	 * ���ݱ��׽��֣������˶˿ڲ��������ݱ�
	 */
	DatagramSocket listener;
	/**
	 * �������Ƿ�æ
	 */
	boolean busy = false;
	
	public boolean isBusy() {
		return busy;
	}
	public synchronized void setBusy(boolean busy) {
		this.busy = busy;
	}
	/**
	 * ���������캯��
	 * @param sendport ���Ͷ˿�
	 * @throws IOException 
	 */
	public Server(int sendport,int listenport) throws IOException{
		sender = new DatagramSocket(sendport,InetAddress.getLocalHost());
		listener = new DatagramSocket(listenport,InetAddress.getLocalHost());
		
	}
	/**
	 * �����Ĺ㲥��ȥ
	 * @param dp
	 * @throws IOException
	 */
	public synchronized void broadcast(DatagramPacket dp) throws IOException{
		log.trace("Ϊ���ݰ����ö˿ںţ�" + ServerUtils.listenport);
		dp.setSocketAddress(new InetSocketAddress("255.255.255.255", ServerUtils.listenport));
		sender.setBroadcast(true);
		setBusy(true);
		sender.send(dp);
		setBusy(false);
		sender.setBroadcast(false);
	}
	/**
	 * ���ͱ���
	 * @param dp
	 * @throws IOException
	 */
	public synchronized void send(DatagramPacket dp,String ip) throws IOException{
		dp.setSocketAddress(new InetSocketAddress(ip, ServerUtils.listenport));
		setBusy(true);
		sender.send(dp);
		setBusy(false);
	}
	/**
	 * ���ձ���
	 * @return
	 * @throws IOException 
	 */
	public DatagramPacket receive() throws IOException{
		byte[] buffer = new byte[1024 * 4];
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		listener.receive(dp);
		return dp;
	}
	/**
	 * �ر���Ӧ��UDP�׽���
	 */
	public void close(){
		sender.close();
		listener.close();
	}

}
