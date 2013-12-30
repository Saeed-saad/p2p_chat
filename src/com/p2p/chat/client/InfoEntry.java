package com.p2p.chat.client;

import java.io.Serializable;
import java.util.Date;

/**
 * ÿ��ͨ�ŵ���Ϣ����ʵ�ִ˽ӿڵ���Ϣ������Client���ӵ�
 * @author Administrator
 *
 */
public interface InfoEntry extends Serializable {
	/**
	 * ʶ����Ϣ���Ĳ�������
	 * @author Administrator
	 *
	 */
	public static enum InfoType{
		SELF,REMOTE,AUTO
	}
	/**
	 * ʶ����Ϣ���ݵ�����
	 * @author Administrator
	 *
	 */
	public static enum ContentType{
		TEXT
	}
	/**
	 * ��ȡ��Ϣ����������
	 * @return
	 */
	public InfoType getInfoType();
	/**
	 * ��ȡ��Ϣ���ݵ�����
	 * @return
	 */
	public ContentType getContentType();
	/**
	 * ��ȡ��Ϣ���е�����
	 * @return
	 */
	public Object getContent();
	/**
	 * ��ȡ��Ϣ������ʱ��
	 * @return
	 */
	public Date getGenerateTime();
}
