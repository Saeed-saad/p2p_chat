package com.p2p.chat.client;

import java.util.Date;

/**
 * ʵ����Ϣ���ĳ�����࣬������չ������Ϣ��
 * @author Administrator
 *
 */
public abstract class AbstractInfoEntry implements InfoEntry {

	private static final long serialVersionUID = 332831479932019719L;
	
	protected InfoEntry.InfoType infoType;
	
	protected InfoEntry.ContentType contentType;
	
	protected Date date;

	@Override
	public InfoType getInfoType() {
		return infoType;
	}

	@Override
	public ContentType getContentType() {
		return contentType;
	}

	@Override
	public Date getGenerateTime() {
		return date;
	}

}
