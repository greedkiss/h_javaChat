package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;


public class ChatClient extends JFrame implements ActionListener{
	String ip="127.0.0.1";
	int port=8888;
	String UserName="宸拐子";
	int type=0;// 连接状态
	
	JComboBox combobox;
	JTextArea messageShow;
	JScrollPane messageScrollPane;
	JLabel sendToLabel,messageLabel;
	
	JTextField clientMessage;
	JCheckBox checkbox;
	JButton clientMessageButton;
	JTextField showStatus;
	Socket socket;
	ObjectOutputStream output;
	ObjectInputStream input;
	
	ClientReceive recvThread;
	
	JMenuBar jMenuBar=new JMenuBar();
	JMenu operateMenu=new JMenu("操作");
	JMenuItem loginItem=new JMenuItem("用户登陆");
	JMenuItem logoffItem=new JMenuItem("用户注销");
	JMenuItem exitItem=new JMenuItem("退出");
	JMenu conMenu=new JMenu("设置");
	JMenuItem userItem=new JMenuItem("用户设置");
	JMenuItem connectItem=new JMenuItem("连接设置");
	JMenu helpMenu=new JMenu("帮助");
	JMenuItem helpItem=new JMenuItem("帮助");
	JToolBar toolBar=new JToolBar();
	JButton loginButton;
	JButton logoffButton;
	JButton userButton;
	JButton connectButton;
	JButton exitButton;
	Dimension faceSize=new Dimension(400,600);
	JPanel downpanel;
	GridBagLayout gridBag;
	GridBagConstraints gridBagCon;
	
	public ChatClient() {
		init();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(faceSize);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width-faceSize.getWidth())/2, (int)(screenSize.height-faceSize.getWidth())/2);
		this.setResizable(false);
		this.setTitle("聊天器客户端");
		show();
	}
	public void init() {
		Container contentPane= getContentPane();
		contentPane.setLayout(new BorderLayout());
		operateMenu.add(loginItem);
		operateMenu.add(logoffItem);
		operateMenu.add(exitItem);
		jMenuBar.add(operateMenu);
		conMenu.add(userItem);
		conMenu.add(connectItem);
		jMenuBar.add(conMenu);
		helpMenu.add(helpItem);
		jMenuBar.add(helpMenu);
		setJMenuBar(jMenuBar);
		
		loginButton=new JButton("登录");
		logoffButton =new JButton("注销");
		userButton =new JButton("用户设置");
		connectButton = new JButton("连接设置");
		exitButton= new JButton("退出");
		loginButton.setToolTipText("连接到指定服务器");
		logoffButton.setToolTipText("与服务器断开连接");
		userButton.setToolTipText("设置用户信息");
		connectButton.setToolTipText("设置所要来连接到服务器的信息");
		
		toolBar.add(userButton);
		toolBar.add(connectButton);
		toolBar.addSeparator();
		toolBar.add(loginButton);
		toolBar.add(logoffButton);
		toolBar.addSeparator();
		toolBar.add(exitButton);
		contentPane.add(toolBar,BorderLayout.NORTH);
		
		loginButton.setEnabled(true);
		logoffButton.setEnabled(false);
		
		loginItem.addActionListener(this);
		logoffItem.addActionListener(this);
		exitItem.addActionListener(this);
		userItem.addActionListener(this);
		connectItem.addActionListener(this);
		helpItem.addActionListener(this);
		
		loginButton.addActionListener(this);
		logoffButton.addActionListener(this);
		userButton.addActionListener(this);
		connectButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		combobox=new JComboBox();
		combobox.insertItemAt("所有人", 0);
		
//		messageShow =new JTextArea();
//		messageShow.setEnabled(false);
		messageScrollPane=new JScrollPane(messageShow,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScrollPane.setPreferredSize(new Dimension(400,400));
		messageScrollPane.revalidate();
		
	
		
		clientMessage=new JTextField(23);
		clientMessage.setEnabled(false);
		clientMessageButton=new JButton();
		clientMessageButton.setText("发送");
		
		clientMessage.addActionListener(this);
		clientMessageButton.addActionListener(this);
		
		sendToLabel=new JLabel("发送至");
		messageLabel=new JLabel("发送消息");
		downpanel = new JPanel();
		gridBag= new GridBagLayout();
		downpanel.setLayout(gridBag);
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=0;
		gridBagCon.gridwidth=5;
		gridBagCon.gridheight=2;
		gridBagCon.ipadx=5;
		gridBagCon.ipady=2;
		JLabel none=new JLabel("  ");
		gridBag.setConstraints(none, gridBagCon);
		downpanel.add(none);
		
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=2;
		gridBagCon.insets=new Insets(1,0,0,0);
		gridBag.setConstraints(sendToLabel, gridBagCon);
		downpanel.add(sendToLabel);
		
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=1;
		gridBagCon.gridy=2;
		gridBagCon.anchor=GridBagConstraints.LAST_LINE_START;
		gridBag.setConstraints(combobox, gridBagCon);
		downpanel.add(combobox);
		
		
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=3;
		gridBag.setConstraints(messageLabel, gridBagCon);
		downpanel.add(messageLabel);
		
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=1;
		gridBagCon.gridy=3;
		gridBagCon.gridwidth=3;
		gridBagCon.gridheight=1;
		gridBag.setConstraints(clientMessage, gridBagCon);
		downpanel.add(clientMessage);
		
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=4;
		gridBagCon.gridy=3;
		gridBag.setConstraints(clientMessageButton, gridBagCon);
		downpanel.add(clientMessageButton);
		
		showStatus = new JTextField(35);
		showStatus.setEditable(false);
		gridBagCon=new GridBagConstraints();
		gridBagCon.gridx=0;
		gridBagCon.gridy=5;
		gridBagCon.gridwidth=5;
		gridBag.setConstraints(showStatus, gridBagCon);
		downpanel.add(showStatus);
		contentPane.add(messageScrollPane,BorderLayout.CENTER);
		contentPane.add(downpanel,BorderLayout.SOUTH);
		
		this.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					if(type==1) {DisConnect();}
					System.exit(0);
				}
			}
		);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj=e.getSource();
		if(obj==userItem||obj==userButton) {
			UserConf userconf=new UserConf(this,UserName);
			userconf.show();
			UserName=userconf.userInputName;
		}
		else if(obj==connectItem|| obj==connectButton) {
			ConnectConf conConf=new ConnectConf(this,ip,port);
			conConf.show();
			ip=conConf.userInputIp;
			port=conConf.userInputPort;
		}
		else if(obj==loginItem||obj==connectButton) {
			Connect();
		}
		else if(obj==logoffItem|| obj==logoffButton) {
			DisConnect();
			showStatus.setText("");
		}
		else if (obj==clientMessage||obj==clientMessageButton) 
		{
			SendMessage();
			clientMessage.setText("");
		}
		else if(obj==exitButton||obj==exitButton) {
			int j=JOptionPane.showConfirmDialog(this,"真的要退出吗？","退出",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(j==JOptionPane.YES_OPTION) {
				if(type==1) {
					DisConnect();
				}
				System.exit(0);
			}
		}
		else if(obj==helpItem) {
//			Help helpDialog = new Help(this);
//			helpDialog.show();
		}
		
	}
	public void Connect() {
		try {
			socket =new Socket(ip,port);
		}
		catch(Exception e){
			JOptionPane.showConfirmDialog(this,"不能连接到服务器","提示",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE);
			return;
		}
		try 
		{
			output= new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input=new ObjectInputStream(socket.getInputStream());
			output.writeObject(UserName);
			output.flush();
			recvThread= new ClientReceive(socket,output,input,combobox,messageShow,showStatus);
			recvThread.start();
			loginButton.setEnabled(false);
			loginItem.setEnabled(false);
			userButton.setEnabled(false);
			userItem.setEnabled(false);
			connectButton.setEnabled(false);
			logoffButton.setEnabled(true);
			logoffItem.setEnabled(true);
			clientMessage.setEnabled(true);
			messageShow.append("连接服务器"+ip+":"+port+"成功...\n");
			type=1;
			
			
		}catch(Exception e) 
		{
			System.out.println(e);
			return;
		}
	}
	public void DisConnect() {
		loginButton.setEnabled(true);
		loginItem.setEnabled(true);
		userButton.setEnabled(true);
		userItem.setEnabled(true);
		connectButton.setEnabled(true);
		logoffButton.setEnabled(false);
		logoffItem.setEnabled(false);
		clientMessage.setEnabled(false);
		if(socket.isClosed()) {
			return;
		}
		try {
			output.writeObject("用户下线");
			output.flush();
			input.close();
			output.close();
			socket.close();
			messageShow.append("服务器断开连接...\n");
			type=0;
		}
		catch(Exception e) {}
	}
	
	public void SendMessage() {
		String toSomeBody=combobox.getSelectedItem().toString();
		String message=clientMessage.getText();
		if(socket.isClosed()) {
			return;
		}
		try {
			output.writeObject("聊天信息");
			output.flush();
			output.writeObject(toSomeBody);
			output.flush();
			output.writeObject(message);
			output.flush();
			}
		catch(Exception e) {
			
		}
		}
	public static void main(String args[] )throws Exception {
		ChatClient app=new ChatClient();
	}
	}
	

