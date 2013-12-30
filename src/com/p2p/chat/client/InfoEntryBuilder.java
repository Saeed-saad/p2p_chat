package com.p2p.chat.client;

import com.p2p.chat.client.InfoEntry.InfoType;

/**
 * �����࣬���ڽ�����Ϣ��
 * @author Administrator
 *
 */
public class InfoEntryBuilder {

	/**
	 * �����Լ���������Ϣ��
	 * @param content
	 * @return
	 */
	public static TextInfoEntry buildSelfTextInfoEntry(String content){
		return new TextInfoEntry(content,InfoType.SELF);
	}
	/**
	 * ����Զ���ͻ���������Ϣ��
	 * @param content
	 * @return
	 */
	public static TextInfoEntry buildRemoteTextInfoEntry(String content){
		return new TextInfoEntry(content,InfoType.REMOTE);		
	}
	/**
	 * �����Զ���������Ϣ��
	 * @param content
	 * @return
	 */
	public static TextInfoEntry buildAutoTextInfoEntry(String content){
		return new TextInfoEntry(content,InfoType.AUTO);		
	}
}
