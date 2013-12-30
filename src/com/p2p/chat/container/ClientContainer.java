package com.p2p.chat.container;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.p2p.chat.client.Client;
import com.p2p.chat.entity.User;

/**
 * �������Client���������
 * @author Administrator
 *
 */
public class ClientContainer implements Serializable,Iterable<Client> {

	private static final long serialVersionUID = -5267703862906425534L;
	
	/**
	 * ʵ�ֵ���ģʽ
	 */
	private static ClientContainer cc = new ClientContainer();
	
	private ClientContainer(){
		
	}
	
	public static ClientContainer getCC(){
		return cc;
	}

	/**
	 * ά�����е�client����֤������������������
	 */
	Map<User, Client> map = new ConcurrentHashMap<>();
	
	/**
	 * ��map������û��ͻ���
	 * @param user
	 * @param client
	 */
	public void addPair(User user,Client client){
		if(!map.containsKey(user)){
			map.put(user, client);
		}
	}
	/**
	 * ��map���Ƴ��û��ͻ���
	 * @param user
	 */
	public void removePair(User user){
		map.remove(user);
	}

	/**
	 * �鿴map���Ƿ����user��client
	 * @param user
	 * @return
	 */
	public boolean contains(User user){
		return map.containsKey(user);
	}
	/**
	 * ʵ��Iterable�ӿڣ����ڱ�������ֵ
	 */
	@Override
	public Iterator<Client> iterator() {
		return map.values().iterator();
	}

	
}
