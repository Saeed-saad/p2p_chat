package com.p2p.chat.client;

import java.io.IOException;

import com.p2p.chat.entity.User;

/**
 * ����ͻ��˽�����
 * @author Administrator
 *
 */
public class ClientBuilder {

	/**
	 * ����һ���ͻ���ʵ��
	 * @param remoter �Է����û�ʵ��
	 * @return
	 * @throws IOException
	 */
	public static Client buildClient(User remoter) throws IOException{
		
		return new ClientImpl(remoter);
	}
	
}
