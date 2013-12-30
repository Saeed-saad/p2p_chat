package com.p2p.chat.client;

import java.io.Serializable;
import java.util.List;

import com.p2p.chat.entity.ClientState;

/**
 * Client����������Э��
 * @author Administrator
 *
 */
public interface Client extends Serializable {

	/**
	 * �������ڵĵ�һ�׶Σ���ʼ��
	 * ����client����ͨ��ǰ��׼������
	 */
	public void doInit();
	
	/**
	 * �������ڵ��м�׶Σ���������
	 * ��client�˵��û�������д���ͨ����ͨ�����罫���ݷ��ͳ�ȥ
	 */
	public void doInput(InfoEntry infoEntry);
	
	/**
	 * �������ڵ��м�׶Σ��������
	 * ��Զ�̿ͻ��˵Ĵ�����������ݽ��д���ͨ������ʾ����Ӧ�Ķ˿�
	 */
	public void doOutput(InfoEntry infoEntry);
	
	/**
	 * �������ڵ����׶Σ������׶�
	 * ��һЩ����ǰ��������
	 */
	public void doEnd();
	
	/**
	 * ������������ȡ��Ϣ�б�
	 * @return
	 */
	public List<InfoEntry> getInfoEntryList();
	/**
	 * ��ȡ�û��Լ��Ŀͻ���״̬
	 * @return
	 */
	public ClientState getSelfState();
	/**
	 * �����û��Լ��ͻ��˵�״̬
	 * @param selfState
	 */
	public void setSelfState(ClientState selfState);
	/**
	 * ���Զ�˿ͻ��˵�ʵ��״̬
	 * @return
	 */
	public ClientState getRemoteState();
	/**
	 * ����Զ�˿ͻ��˵�ʵ��״̬
	 * @param remoteState
	 */
	public void setRemoteState(ClientState remoteState);
	
}
