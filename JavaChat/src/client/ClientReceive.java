package client;

import java.net.*;
import java.io.*;
import javax.swing.*;
public class ClientReceive extends Thread{
	private JComboBox combobox;
	private JTextArea textarea;
	Socket socket;
	ObjectOutputStream output;
	ObjectInputStream input;
	JTextField showStatus;
	public ClientReceive(
			Socket socket,ObjectOutputStream output,ObjectInputStream input,
			JComboBox combobox,JTextArea textarea,JTextField showStatus
			) {
		this.socket=socket;
		this.output=output;
		this.combobox=combobox;
		this.textarea=textarea;
		this.showStatus=showStatus;
		this.input=input;
	}
	public void run() {
		while(!socket.isClosed()) {
			try {
				String type=(String)input.readObject();
				if(type.equalsIgnoreCase("系统信息")) {
					String sysmsg=(String)input.readObject();
					textarea.append("系统消息:"+sysmsg);
				}
				
				else if(type.equalsIgnoreCase("服务关闭")) {
					output.close();
					input.close();
					socket.close();
					textarea.append("服务器已关闭\n");
					break;
				}
				else if(type.equalsIgnoreCase("聊天信息")) {
					String message=(String)input.readObject();
					textarea.append(message);
					
				}
				else if(type.equalsIgnoreCase("用户列表")) {
					String userList=(String)input.readObject();
					String usernames[]=userList.split("\n");
					combobox.removeAllItems();
					int i=0;
					combobox.addItem("所有人");
					while(i<usernames.length) {
						combobox.addItem(usernames[i]);
						i++;
					}
					combobox.setSelectedIndex(0);
					showStatus.setText("在线用户"+usernames.length+"人");
				}
			}catch(Exception e) {
				System.out.println("e");
		}
		}
	}
}

