package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.net.*;

public class UserConf extends JDialog {
	JPanel panelUserConf=new JPanel();
	JButton save=new JButton();
	JButton cancel=new JButton();
	JLabel DLGINFO=new JLabel("默认用户名：宸拐子");
	JPanel panelSave=new JPanel();
	JLabel message=new JLabel();
	String userInputName;
	JTextField userName;
	public UserConf(JFrame frame,String str) {
		super(frame,true);
		this.userInputName=str;
		try {
			jbInit();
		}catch(Exception e) {
			e.printStackTrace();
		}
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int ) (screenSize.width-400)/2+50,(int) (screenSize.height-600)/2+150);
		this.setResizable(false);
	}
	private void jbInit() throws Exception{
		this.setSize(new Dimension(300,120));
		this.setTitle("用户设置");
		message.setText("请输入用户名");
		userName=new JTextField(10);
		userName.setText(userInputName);
		save.setText("保存");
		cancel.setText("取消");
		
		panelUserConf.setLayout(new FlowLayout());
		panelUserConf.add(message);
		panelUserConf.add(userName);
		panelSave.add(new Label("       "));
		panelSave.add(save);
		panelSave.add(cancel);
		panelSave.add(new Label("       "));
		
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(panelUserConf,BorderLayout.NORTH);
		contentPane.add(DLGINFO,BorderLayout.CENTER);
		contentPane.add(panelSave,BorderLayout.SOUTH);
				
	
	save.addActionListener
	(
			new ActionListener() {
				public void actionPerformed(ActionEvent a) {
						if(userName.getText().equals("")) {
						 DLGINFO.setText("用户名不能为空");
						 userName.setText(userInputName);
						 return;
						}
						else if(userName.getText().length()>15) {
							DLGINFO.setText("用户名长度不能超过25个");
							userName.setText(userInputName);
							return;
						}
						userInputName=userName.getText();
						dispose();
						}
			}
			);
	this.addWindowListener(
			new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
					DLGINFO.setText("默认用户：宸拐子");
					
				}
			}
			);
	cancel.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DLGINFO.setText("默认用户：宸拐子");
					dispose();
				}
			}
			
			);
}
}

