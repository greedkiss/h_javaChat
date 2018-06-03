package client;
import java.awt.*;
import javax.swing.border.*;


import java.net.*;
import javax.swing.*;
import java.awt.event.*;
	public class ConnectConf extends JDialog{
		JPanel panelUserConf=new JPanel();
		JButton save=new JButton();
		JButton cancel=new JButton();
		JLabel DLGINFO=new JLabel("默认连接为  127.0.0.1:8888");
		JPanel panelSave=new JPanel();
		JLabel message = new JLabel();
		String userInputIp;
		int userInputPort;
		JTextField inputIp;
		JTextField inputPort;
		public ConnectConf (JFrame frame,String ip,int port) {
			super(frame ,true);
			this.userInputIp=ip;
			this.userInputPort=port;
			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((int) (screenSize.width-400)/2+50,(int)(screenSize.height-600)/2+150 );
			this.setResizable(false);
		}
		private void jbInit() throws Exception {
			this.setSize(new Dimension(300,130));
			this.setTitle("连接设置");
			message.setText("请输入服务器的ip地址");
			inputIp=new JTextField(10);
			inputIp.setText(userInputIp);
			inputPort=new JTextField(4);
			inputPort.setText(""+userInputPort);
			save.setText("保存");
			cancel.setText("取消");
			panelUserConf.setLayout(new GridLayout(2,2,1,1));
			panelUserConf.add(message);
			panelUserConf.add(inputIp);
			panelUserConf.add(new Label("请输入服务端口号"));
			panelUserConf.add(inputPort);
			panelUserConf.add(new Label("     "));
			panelUserConf.add(save);
			panelUserConf.add(cancel);
			panelUserConf.add(new Label("     "));
			
			Container contentPane=getContentPane();
			contentPane.setLayout(new BorderLayout());
			contentPane.add(panelUserConf,BorderLayout.NORTH);
			contentPane.add(DLGINFO,BorderLayout.CENTER);
			contentPane.add(panelSave,BorderLayout.SOUTH);
			save.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						int savePort;
						String inputIP;
						try{
							userInputIp=""+InetAddress.getByName(inputIp.getText());
							userInputIp=userInputIp.substring(1);
						}catch(UnknownHostException e) {
							DLGINFO.setText("wrong IP");
							return;
						}
						try {
							savePort=Integer.parseInt(inputPort.getText());
							if(savePort<1||savePort>65535) {
								DLGINFO.setText("监听端口在0~65535之间");
								inputPort.setText("");
								return;
							}
							userInputPort=savePort;
							dispose();
						}catch(NumberFormatException e) {
							DLGINFO.setText("错误的端口号，端口号请填写整数");
							inputPort.setText("");
							return;
						}
						
					}
				}
			);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					DLGINFO.setText("默认连接设置为127.0.0.1:8888");
					
				}
			});
			cancel.addActionListener(
					
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DLGINFO.setText("默认连接设置为 127.0.0.1:8888");
						dispose();
					
				}
			});
		}
}

