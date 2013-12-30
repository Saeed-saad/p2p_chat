package com.p2p.chat.container;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.p2p.chat.entity.User;

/**
 * ����û�������
 * @author Administrator
 *
 */
public class UserContainer implements Iterable<User> {
	
	/**
	 * ʵ�ֵ���ģʽ
	 */
	private static UserContainer uc = new UserContainer();
	
	private UserContainer(){
		
	}
	
	public static UserContainer getUC(){
		return uc;
	}

	/**
	 * ά�����е��û��б�
	 */
	Map<String,User> map = new ConcurrentHashMap<>();
	
	/**
	 * �������������Ӧ���û�����
	 * @param key �û���ip��ַ
	 * @return
	 */
	public User get(String key){
		return map.get(key);
	}

	/**
	 * ʵ��Iterable�ӿ��еķ����Ա���������û�
	 */
	@Override
	public Iterator<User> iterator() {
		return map.values().iterator();
	}
	
	/**
	 * ����û����б���
	 * @param user
	 */
	public void add(String ip,User user){
		if(!map.containsValue(user)){
			map.put(ip, user);
		}
	}
	
	/**
	 * ���û��б����Ƴ���Ӧ���û�
	 * @param user
	 */
	public void remove(String ip){
		map.remove(ip);
	}
	
	/**
	 * �������Ƿ����user
	 * @param user
	 * @return
	 */
	public boolean contains(User user){
		return map.containsValue(user);
	}
}
