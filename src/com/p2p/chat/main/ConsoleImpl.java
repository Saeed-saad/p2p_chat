package com.p2p.chat.main;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.p2p.chat.container.ClientContainer;
import com.p2p.chat.container.EventContainer;
import com.p2p.chat.container.UserContainer;
import com.p2p.chat.entity.User;
import com.p2p.chat.event.EventBuilder;
import com.p2p.chat.event.ExitEvent;
import com.p2p.chat.event.LoginEvent;
import com.p2p.chat.event.LogoutEvent;
import com.p2p.chat.processor.DispatchEventProcessor;
import com.p2p.chat.processor.ExitProcessor;
import com.p2p.chat.processor.LoginProcessor;
import com.p2p.chat.processor.LogoutProcessor;
import com.p2p.chat.processor.ReceiveEventProcessor;

import java.util.concurrent.ExecutorService;

/**
 * ��ʾ�ͻ��ˣ��ڴ��ڰ�
 * @author Administrator
 *
 */
public class ConsoleImpl {
	
	static enum Menu{
		LOGIN("����������Ϣ"),
		LOGOUT("����������Ϣ"),
		SHOW_ALL_USER("��ʾ���������û�"),
		EXIT("�˳�p2p��Chat");
		
		String description;

		public String getDescription() {
			return description;
		}

		private Menu(String description) {
			this.description = description;
		}
		
	}
	
	/**
	 * ִ���¼��������¼�������̳߳�
	 */
	ExecutorService exe = Executors.newFixedThreadPool(2);

	/**
	 * �¼����������а������еĴ������¼�
	 */
	EventContainer eventContainer = EventContainer.getEC();
	/**
	 * �û����������а������е������û�
	 */
	UserContainer userContainer = UserContainer.getUC();
	/**
	 * ����ͻ������������а�����������ͨ�ŵĿͻ�
	 */
	ClientContainer clientContainer = ClientContainer.getCC();
	
	PrintStream out = System.out;
	
	Scanner in = new Scanner(System.in);
	
	/**
	 * �Ƿ��ѵ�¼
	 */
	boolean isLogin = false;
	
	/**
	 * ��ǰ��¼�û�
	 */
	User currentUser = null;
	/**
	 * ��ѭ���Ƿ�����
	 */
	boolean isRunning = false;
	
	/**
	 * ��ʼ��ִ�л���
	 */
	public void init(){
		exe.execute(new DispatchEventProcessor());
		exe.execute(new ReceiveEventProcessor());
	}
	/**
	 * ��ӡ��ִ�в˵�
	 */
	public void displayMenu(){
		out.println(">>>>>>>>>>>>>>>>��ִ�в˵�<<<<<<<<<<<<<<<<");
		for(Menu menu : Menu.values()){
			out.println((menu.ordinal() + 1) + ", " + menu.getDescription());
		}
		out.println(">>>>>>>>>>>>>>>>********<<<<<<<<<<<<<<<<");
		out.print("��ѡ����Ҫִ�еĲ�����");
	}
	/**
	 * ��¼����Ĵ���
	 */
	public void login(){
		if(isLogin){
			out.println("���Ѿ���¼��");
			return;
		}
		User user = null;
		out.print("���������ĵ�¼�ǳƣ�");
		String nickname = in.next();
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		user = new User(nickname, ip);
		LoginEvent event = EventBuilder.buildLoginEvent(user);
		eventContainer.push(event);
		this.currentUser = user;
		this.isLogin = true;
	}
	
	/**
	 * ��ʾ���������û�����
	 */
	public void showAllUser(){
		out.println((this.currentUser != null ? this.currentUser.getNickname() : "����û�е�¼") + " O(��_��)O");
		out.println("------------------------");
		for(User user : userContainer){
			out.println(user);
		}
	}
	/**
	 * �˳������
	 */
	public void exit(){
		out.println("��ȷ��Ҫ�˳���(Y/N)");
		String input = in.next();
		if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")){
			if(isLogin){
				logout();
			}
			ExitEvent event = EventBuilder.buildExitEvent();
			eventContainer.push(event);
			exe.shutdownNow();
			isRunning = false;
			try {
				exe.awaitTermination(60, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡ����Ĳ˵�ѡ��
	 * @return
	 */
	public int readMenu(){
		int menu = 0;
		while(true){
			if(in.hasNextInt()){
				menu = in.nextInt();
				if(menu >= 1 && menu <= Menu.values().length){
					break;
				}
			}
			out.println("������ȷ��������������룺");
			in.next();
		}
		return menu - 1;
	}
	/**
	 * ����ִ�к���
	 */
	public void run(){
		isRunning = true;
		while(isRunning){
			displayMenu();
			int menuOrder = readMenu();
			switch(Menu.values()[menuOrder]){
			case LOGIN:
				login();
				break;
			case SHOW_ALL_USER:
				showAllUser();
				break;
			case LOGOUT:
				logout();
				break;
			case EXIT:
				exit();
				break;
			default:
			}
			
		}
	}
	/**
	 * ���������
	 */
	private void logout() {
		if(isLogin){
			LogoutEvent event = EventBuilder.buildLogoutEvent(currentUser);
			eventContainer.push(event);
			out.println("���߳ɹ�");
		}else{
			out.println("���Ѿ����ߣ����ȵ�¼��");
		}
		isLogin = false;
		this.currentUser = null;
	}
	public static void main(String[] args){
		//ע���¼����ͺ���Ӧ��ִ����
		DispatchEventProcessor.register(LoginEvent.class, new LoginProcessor());
		DispatchEventProcessor.register(LogoutEvent.class, new LogoutProcessor());
		DispatchEventProcessor.register(ExitEvent.class, new ExitProcessor());
		
		ConsoleImpl conImpl = new ConsoleImpl();
		conImpl.init();
		conImpl.run();
	}
}
