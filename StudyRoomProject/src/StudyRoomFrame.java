import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.util.function.*;
import org.knowm.xchart.*;


public class StudyRoomFrame   {
	
	public static void main(String[] args) throws Exception  {
		//������ ����
		StartPage startFrame=new StartPage(0);	
	}
}


//����ȭ��
class StartPage extends JFrame implements ActionListener{
	JPanel contentPane = new JPanel();// ��ư�̳� ���� �� ȭ���� ����
	JLabel title=new JLabel("Welcome to STUDYROOM");
	JButton userBt = new JButton("User"); // ����� ȭ������ ��ȯ�ϴ� ��ư
	JButton managerBt = new JButton("Manager"); // ������ ȭ������ ��ȯ�ϴ� ��ư
	JButton ExitBt = new JButton("Exit"); // ���� ��ư
	static StudyRoomManagement SRM;
	File file = new File("RoomInfo.dat"); 
	UserMenu1 user1;
	Manager_AddPage manageradd;
	static ObjectInputStream inputFile;
	
	
	StartPage(int active) throws Exception{
		
		setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
		contentPane.setBounds(0, 0, 900, 560); //â ũ�� ���� �� ��ġ ����
		setTitle("StudyRoom"); // â �̸�
		contentPane.setLayout(null);
		
		switch(active) {
		//user_menu ȭ�鿡�� ����ȭ������ ���ƿ� ��� SRM �ʱ�ȭ
		case 1:
			this.SRM=user1.SRM;
			break;
		//manager menu ȭ�鿡�� ����ȭ������ ���ƿ� ��� SRM �ʱ�ȭ
		case 2:
			this.SRM=manageradd.SRM;
			break;
			
		default:
			inputFile = new ObjectInputStream(new FileInputStream(file));
			this.SRM = new StudyRoomManagement("manager123", inputFile);
			break;
		}
		
			 title.setForeground(Color.BLACK); //�۾� ����
	         title.setFont(new Font("����", Font.PLAIN, 35)); // �۾� ũ�� �� ����
	         title.setBounds(150, 120, 491, 80); //�۾� ��ġ
	         contentPane.add(title);
	         
	         userBt.setBounds(300, 220, 100, 50);
	         contentPane.add(userBt);
	         
	         managerBt.setBounds(300, 285, 100, 50);
	         contentPane.add(managerBt);
	         
	         ExitBt.setBounds(300, 350, 100, 50);
	         contentPane.add(ExitBt);
	         
	         add(contentPane);

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				
				userBt.addActionListener(this);
				managerBt.addActionListener(this);	
				ExitBt.addActionListener(this);
	         
	        
	     
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == userBt) {
			new PhonePage(); //����� ��ȭ��ȣ â���� ��ȯ
            setVisible(false); // â �Ⱥ��̰� �ϱ�   
            
            try {
            inputFile.close();
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			SRM.writeRoomInfos(output);
			output.close();
            }
            catch(Exception e1) {
            	e1.printStackTrace();
            }
			}
		
		if(e.getSource() == managerBt) {
			new ManagerLoginPage(); //������ �α��� â���� ��ȯ 
            setVisible(false); // â �Ⱥ��̰� �ϱ� 
			}
		if(e.getSource()==ExitBt) {
			try {
				
				inputFile.close();
				System.exit(0);
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
}


//user_�α��� ������
class PhonePage extends JFrame implements ActionListener {
	
	JPanel contentPane = new JPanel();
	JTextField phoneText= new JTextField(); //��ȭ��ȣ �޴� �ؽ�Ʈ�ʵ�
	JButton enterBt = new JButton("Login"); // �α��� ���� ��ư
	JButton cancelBt = new JButton("Cancel"); // �α��� ��� ��ư
	JLabel label =new JLabel("PhoneNumber");
	static User userPhone;
    
	PhonePage(){


		setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
		setTitle("StudyRoom"); // â �̸�
		contentPane.setLayout(null);
		
       
       label.setForeground(Color.BLACK);
       label.setFont(new Font("����", Font.PLAIN, 20));
       label.setBounds(150, 150, 491, 80);
       contentPane.add(label);
       
       phoneText.setBounds(330, 170, 200, 40);
       contentPane.add(phoneText);
       
       enterBt.setBounds(200, 300, 100, 50);
       contentPane.add(enterBt);
       cancelBt.setBounds(350, 300, 100, 50);
       contentPane.add(cancelBt);
       
		 add(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		enterBt.addActionListener(this);
		cancelBt.addActionListener(this);	
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == enterBt) {
			
			if(phoneText.getText().isEmpty())  //���â
				JOptionPane.showMessageDialog(null, "Enter your PhoneNumber", "Message", JOptionPane.ERROR_MESSAGE);
			else {
				userPhone = new User(phoneText.getText());
				try {
					new UserMenu1();
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
                setVisible(false); // â �Ⱥ��̰� �ϱ�  
			}
			
		}
		
		if(e.getSource() == cancelBt) {
			try {
				new StartPage(0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
          setVisible(false);
			}
		
		
	}	
}


//user_menu �⺻ ����Ʈ ȭ���� <���డ���� ��� �� ��ȸ�ϱ�>
class UserMenu1 extends JFrame implements ActionListener{
	
	JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));// ��ư�̳� ���� �� ȭ���� ����
	JPanel p2 = new JPanel(new GridLayout(3,1));// use, check out, exit to main ��ư�� �� ȭ��
	JButton AllRoomBt = new JButton("View All The Rooms"); // ���� ������ ��� �� ��ȸ ��ư
	JButton MyRoomBt = new JButton("View My Rooms"); //����ڰ� ������ �� ��ȸ ��ư
	JButton UseBt = new JButton("Check In"); //üũ�� ��ư
	JButton CheckOutBt = new JButton("Check Out"); //üũ�ƿ� ��ư
	JButton ExitBt = new JButton("Exit to main"); //����ȭ������ ���ư��� ��ư
	static StudyRoomManagement SRM;
	Vector <String> vector;
	DefaultTableModel model; //�� �߰�,������ �����ϰ� �ϱ� ���� DefaultTableModel ���
    JTable table;
    MyMouseListener mouse=new MyMouseListener();
    StartPage start;
    PhonePage user;
    static boolean active=false;
    static ObjectInputStream inputFile;
    File file = new File("RoomInfo.dat"); 
    
	UserMenu1() throws Exception  {
		
		inputFile = new ObjectInputStream(new FileInputStream(file));
		this.SRM = new StudyRoomManagement("manager123", inputFile);
	
		
		setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
		setTitle("StudyRoom"); // â �̸�
	
		AllRooms();	
			
		p.add(AllRoomBt);
		p.add(MyRoomBt);
		
		p2.add(UseBt);
		p2.add(CheckOutBt);
		p2.add(ExitBt);
		
		//üũ�ƿ� ��ư ��Ȱ��ȭ
		CheckOutBt.setEnabled(false);
		AllRoomBt.addActionListener(this);
		MyRoomBt.addActionListener(this);
		UseBt.addActionListener(this);
		ExitBt.addActionListener(this);
		
		table.addMouseListener(new MyMouseListener());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	//���� ������ �� ���� ���
	void AllRooms()  {
	
		SRM=start.SRM;
		 //Ÿ��Ʋ �����
		vector = new Vector<String>();
		vector.addElement("no");
		vector.addElement("RoomName");
		vector.add("Capacity");
		vector.add("Bill per Hour");
		//vector.add("Bill per Day");
		
		try {
		
			model = new DefaultTableModel(vector,0) ;
			
			   // �� ����
				ArrayList<Room> Room = SRM.searchEmptyRoom();
				int i=0;
				for(Room data:Room){	
					Vector<String> v= new Vector<String>();
					v.add(String.valueOf(i+1));
					v.add(data.getRoomName());
					v.add(String.valueOf(data.getCapacity()));
					v.add(String.valueOf(data.getBill()));
					model.addRow(v);
					i++;
				}
				
		}catch(Exception e1) {
			
		}
		
	
	    table = new JTable(model);
	    JScrollPane scroll = new JScrollPane(table);

		Container c = getContentPane();
		c.add("Center",scroll);
		c.add("North", p);
		c.add("East", p2);
		
	}
	
	//�� üũ��
	void CheckIn() throws Exception {
		//���콺�� Ŭ���� ���� �̸��� ���� ���
		if(mouse.roomName==null) {
			JOptionPane.showMessageDialog(null, "Please check the room again", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		SRM.checkIn( mouse.roomName,user.userPhone);
		
		try {
			inputFile.close();
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			SRM.writeRoomInfos(output);
			output.close();
		}
		catch(Exception e1) {
			
		}
	}
	
	//��ư �̺�Ʈ
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == MyRoomBt) {
			try {
				new UserMenu2();
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            setVisible(false);	    
			}
		//check in ��ư
		if(e.getSource() == UseBt) {
			try {
				CheckIn();
				new UserMenu1();
				setVisible(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}	    
			}
        //����ȭ������ ���ư���
		if(e.getSource()==ExitBt) {
			try {
				active=true;
				new StartPage(1);
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				
			} catch (Exception e1) {
			}
		}
	}
	
}


//user_menu ȭ�� �� �ϳ��� <����ڰ� ������ �� ��ȸ�ϱ�>
class UserMenu2 extends JFrame implements ActionListener{

	JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));// ��ư�̳� ���� �� ȭ���� ����
	JPanel p2 = new JPanel(new GridLayout(3,1));// use, check out, exit to main ��ư�� �� ȭ��
	JButton AllRoomBt = new JButton("View All The Rooms"); // ��ư �ʱ�ȭ
	JButton MyRoomBt = new JButton("View My Rooms");
	JButton UseBt = new JButton("Check In");
	JButton CheckOutBt = new JButton("Check Out");
	JButton ExitBt = new JButton("Exit to main");
	StudyRoomManagement SRM;
	Vector <String> vector;
	DefaultTableModel model; //�� �߰�,������ �����ϰ� �ϱ� ���� DefaultTableModel ���
    JTable table;
    PhonePage user;
    StartPage start;
    MyMouseListener mouse=new MyMouseListener();
    static boolean active=false;
    static ObjectInputStream inputFile;
    File file = new File("RoomInfo.dat"); 
    
     UserMenu2() throws Exception  {
    		inputFile = new ObjectInputStream(new FileInputStream(file));
    		this.SRM = new StudyRoomManagement("manager123", inputFile);
    		
		
		setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
		setTitle("StudyRoom"); // â �̸�
		      
		MyRooms();
			
		p.add(AllRoomBt);
		p.add(MyRoomBt);
		
		p2.add(UseBt);
		p2.add(CheckOutBt);
		p2.add(ExitBt);
		
		//üũ�� ��ư ��Ȱ��ȭ
		UseBt.setEnabled(false);
		
		AllRoomBt.addActionListener(this);
		MyRoomBt.addActionListener(this);
		CheckOutBt.addActionListener(this);
		ExitBt.addActionListener(this);
		table.addMouseListener(new MyMouseListener());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
     
   //����ڰ� ���� ����ϰ� �ִ� �� ���� ���
 	void MyRooms() {
 		
 		vector = new Vector<String>();
 		vector.addElement("no");
 		vector.addElement("RoomName");
 		
 		try {
 
 			SRM=start.SRM;
			model = new DefaultTableModel(vector,0) ;
 			
 			   // �� ����
 				ArrayList<Room> Room = SRM.searchRoom();
 				int i=0;
 				
 				for(Room data:Room){
 					Vector<String> v= new Vector<String>();
 					if(data.isRoomUsed()) {
 						if(data.getUser().getPhoneNumber().equals(user.userPhone.getPhoneNumber())) {
 							v.add(String.valueOf(i+1));
 		 					v.add(data.getRoomName());
 		 					model.addRow(v);
 		 					i++;
 						}
 		 					
 						
 					}		
 					
 				}		
 				
 		}catch(Exception e1) {
 			
 		}
 	    table = new JTable(model);
 	    JScrollPane scroll = new JScrollPane(table);
 		Container c = getContentPane();
 		c.add("Center",scroll);
 		c.add("North", p);
 		c.add("East", p2);
 		
 	}
 	
 	//�� üũ�ƿ�
 		void CheckOut() throws Exception {
 			if(mouse.roomName==null) {
 				JOptionPane.showMessageDialog(null, "Please check the room again", "Error", JOptionPane.ERROR_MESSAGE);
 				return;
 			}
 			//���� ����
 			JOptionPane.showMessageDialog(null, "You have to pay "+SRM.checkOut( mouse.roomName));
 		}
 	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == AllRoomBt) {
			try {
				new UserMenu1();
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	        setVisible(false);	 
		}
		if(e.getSource() == CheckOutBt) {
			try {
				CheckOut();
				new UserMenu2();
			
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			setVisible(false);
		}
		if(e.getSource()==ExitBt) {
			try {
				active=true;
				new StartPage(1);
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
			} catch (Exception e1) {
			}
		}
		
	}
	
}


//���콺 �̺�Ʈ
//���콺�� ������ ���̸� ã��
 class MyMouseListener extends MouseAdapter{
	    JTable table;
	    DefaultTableModel model;
	    static int nRow,nColumn; //���̺� ��,��
	    Object value;
	    static String roomName;
	    
	 public void mouseClicked(MouseEvent e) {
		 if (e.getButton() == 1) {
				table = (JTable)e.getComponent();
				model = (DefaultTableModel)table.getModel();

				//������ �� �˾Ƴ�
				nRow = table.getSelectedRow();
				//������ ���� �� �̸� �� ���
				value=model.getValueAt(nRow, 1);
				roomName=value.toString();
				System.out.println(roomName);
				
				}
	 }
}
 
 
 //������ �α��� â
 class ManagerLoginPage extends JFrame{
	  
	   JPanel contentPane = new JPanel();// ��ư�̳� ���� �� ȭ���� ����
		JPasswordField passwordText= new JPasswordField(); //��ȭ��ȣ �޴� �ؽ�Ʈ�ʵ�
		JButton enterBt = new JButton("Login"); // �α��� ��ư
		JButton cancelBt = new JButton("Cancel"); // �α��� ��� ��ư
		JLabel label =new JLabel("ManagerID");
		String managerID;
		StartPage start;
	 
	 ManagerLoginPage(){
		 
		    setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
			setTitle("Manager"); // â �̸�
			contentPane.setLayout(null);
			
	       
	       label.setForeground(Color.BLACK);
	       label.setFont(new Font("����", Font.PLAIN, 20));
	       label.setBounds(150, 150, 491, 80);
	       contentPane.add(label);
	       
	       passwordText.setBounds(330, 170, 200, 40);
	       contentPane.add( passwordText);
	       
	       enterBt.setBounds(200, 300, 100, 50);
	       contentPane.add(enterBt);
	       cancelBt.setBounds(350, 300, 100, 50);
	       contentPane.add(cancelBt);
			 
			 add(contentPane);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			
			//Login ��ư ������ �̺�Ʈ ó��
			enterBt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//�α���â�� �н����带 ������ ���ϰ� ���� ���
					if( passwordText.getText().isEmpty()) 
						JOptionPane.showMessageDialog(null, "Please Enter ManagerID", "Message", JOptionPane.ERROR_MESSAGE);
					else {
						if(start.SRM.matchMangerID(passwordText.getText())) {
							setVisible(false); // â �Ⱥ��̰� �ϱ� 
							try {
								new Manager_AddPage();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						//�н����尡 �ٸ��� ������ ���
						else
							JOptionPane.showMessageDialog(null, "Wrong ManagerID", "Message", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			});
			
			cancelBt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						new StartPage(0);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	              setVisible(false);
				}
			});
	 }
	 
 }
 
 
 
//������ �⺻â �� ���߰� â
class Manager_AddPage extends JFrame implements ActionListener{
	 
	    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));// ��ư�̳� ���� �� ȭ���� ����
		JPanel p2 = new JPanel(new GridLayout(5,1));// add, delete, all, income, exit to main ��ư�� �� ȭ��
		JButton AddBt = new JButton("ADD"); // �� �߰� ��ư
		JButton DeleteBt = new JButton("DELETE"); // �� ���� ��ư
		JButton AllBt = new JButton("ALL"); // �� ��ü ��ȸ ��ư
		JButton IncomeBt = new JButton("INCOME"); // ���� ��ȸ��ư
		JButton ExitBt = new JButton("Exit to main"); // ����ȭ������ ���ư��� ��ư
		JButton UpdateBt = new JButton("UPDATE"); // ������Ʈ ��ư
		
		JLabel namelabel =new JLabel("RoomName:");
		JLabel capacitylabel =new JLabel("Capacity:");
		JLabel hourlabel =new JLabel("Bill Per Hour:");
		//JLabel daylabel =new JLabel("Bill Per Day:");
		
		JTextField nameText= new JTextField(10); // ���̸�
		JTextField capacityText= new JTextField(5); //�� ��밡�� �ο�
		JTextField hourText= new JTextField(5); //�ð��� �ݾ�
		//JTextField dayText= new JTextField(5); //�ϴ� �ݾ�
		
		static StudyRoomManagement SRM;
		Vector <String> vector;
		DefaultTableModel model; //�� �߰�,������ �����ϰ� �ϱ� ���� DefaultTableModel ���
	    JTable table;
	    PhonePage user;
	    StartPage start;
	    MyMouseListener mouse=new MyMouseListener();
	    
	    static ObjectInputStream inputFile;
	    File file = new File("RoomInfo.dat"); 
	   
	    Manager_AddPage() throws Exception  {
	    	
	    	inputFile = new ObjectInputStream(new FileInputStream(file));
    		this.SRM = new StudyRoomManagement("manager123", inputFile);
    		
			setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
			setTitle("Manager"); // â �̸�
			
			AllRooms();
			
			p.add(namelabel);
			p.add(nameText);
			p.add(capacitylabel);
			p.add(capacityText);
			p.add(hourlabel);
			p.add(hourText);
			//p.add(daylabel);
			//p.add(dayText);
			p.add(UpdateBt);
			
			p2.add(AddBt);
			p2.add(DeleteBt);
			p2.add(AllBt);
			p2.add(IncomeBt);
			p2.add(ExitBt);
			
			DeleteBt.addActionListener(this);
			AllBt.addActionListener(this);
			IncomeBt.addActionListener(this);
			ExitBt.addActionListener(this);
			UpdateBt.addActionListener(this);
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
	     
	   //�� ����
	 	void AddRooms() throws Exception {
	 		
	 		String roomName;
	 		int cap, bph, bpd;
	 		
	 			roomName=nameText.getText();
	 			cap= Integer.parseInt(capacityText.getText());
	 			bph= Integer.parseInt(hourText.getText());
	 			SRM=start.SRM;
	 			
	 			
	 			if(!this.SRM.searchRoom(roomName)) {
					Room tmpRoom = new Room(roomName, cap, bph);
					this.SRM.makeRoom(tmpRoom);		
				} else {
					JOptionPane.showMessageDialog(null, "There is already room "+roomName, "Message", JOptionPane.ERROR_MESSAGE);
				}	
	 	}
	 	
	 	//�� ���� ��ȸ
		void AllRooms()  {
			SRM=start.SRM;
			 //Ÿ��Ʋ �����
			vector = new Vector<String>();
			vector.addElement("no");
			vector.addElement("RoomName");
			vector.add("Capacity");
			vector.add("Bill per Hour");
			//vector.add("Bill per Day");
			
			try {
			
				model = new DefaultTableModel(vector,0) ;
				
				   // �� ����
					ArrayList<Room> Room = SRM.searchRoom();
					int i=0;
					for(Room data:Room){	
						
						Vector<String> v= new Vector<String>();
						v.add(String.valueOf(i+1));
						v.add(data.getRoomName());
						v.add(String.valueOf(data.getCapacity()));
						v.add(String.valueOf(data.getBill()));
						model.addRow(v);
						i++;
					}
					
			}catch(Exception e1) {
				e1.printStackTrace();
			}
	
		    table = new JTable(model);
		    JScrollPane scroll = new JScrollPane(table);
			Container c = getContentPane();
			c.add("Center",scroll);
			c.add("North", p);
			c.add("East", p2);
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == DeleteBt) {
				try {
				new Manager_DeletePage();
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				} 
				catch(Exception e1) {
					
				}
			}
			if(e.getSource() == AllBt) {
				try {
				new Manager_AllPage();
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				}
				catch(Exception e1) {
					
				}
			
			}
			if(e.getSource()==IncomeBt) {
				try {
				new Manager_IncomePage();
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				}
				catch(Exception e1) {
					
				}
			}
           if(e.getSource()==ExitBt) {
           	
           	try {
					new StartPage(2);
					setVisible(false);
					
					inputFile.close();
					ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
					SRM.writeRoomInfos(output);
					output.close();
				} catch (Exception e1) {
					System.out.println("��");
					e1.printStackTrace();
				}	
			}
          if(e.getSource()==UpdateBt) {
				try {
					AddRooms();
					new Manager_AddPage();
					setVisible(false);
				} catch (Exception e1) {
					
				}
			}
			
		}
		
	}
 
 
//������ �����â
class Manager_DeletePage extends JFrame implements ActionListener{
	 
	    JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));// ��ư�̳� ���� �� ȭ���� ����
		JPanel p2 = new JPanel(new GridLayout(5,1));// add, delete, all, income, exit to main ��ư�� �� ȭ��
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));// ��ư�̳� ���� �� ȭ���� ����
		JButton AddBt = new JButton("ADD"); 
		JButton DeleteBt = new JButton("DELETE");
		JButton AllBt = new JButton("ALL");
		JButton IncomeBt = new JButton("INCOME");
		JButton ExitBt = new JButton("Exit to main");
		JButton UpdateBt = new JButton("UPDATE");
		
		JLabel message =new JLabel("Click the room you want to delete");
		
		static StudyRoomManagement SRM;
		Vector <String> vector;
		DefaultTableModel model; //�� �߰�,������ �����ϰ� �ϱ� ���� DefaultTableModel ���
	    JTable table;
	    PhonePage user;
	    StartPage start;
	    MyMouseListener mouse=new MyMouseListener();
	    
	    static ObjectInputStream inputFile;
	    File file = new File("RoomInfo.dat"); 
	   
	    Manager_DeletePage() throws Exception {
	    	
	    	inputFile = new ObjectInputStream(new FileInputStream(file));
    		this.SRM = new StudyRoomManagement("manager123", inputFile);
	    	
			setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
			setTitle("Manager"); // â �̸�
			 
			AllRooms();
			
			p.add(UpdateBt);
			
			p2.add(AddBt);
			p2.add(DeleteBt);
			p2.add(AllBt);
			p2.add(IncomeBt);
			p2.add(ExitBt);
			
			p3.add(message);
			
			AddBt.addActionListener(this);
			AllBt.addActionListener(this);
			IncomeBt.addActionListener(this);
			ExitBt.addActionListener(this);
			UpdateBt.addActionListener(this);
			table.addMouseListener(new MyMouseListener());
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
	     
	   // �� ����
	 	void DeleteRooms() throws Exception {
	 		if(mouse.roomName==null) {
	 			JOptionPane.showMessageDialog(null, "Please check it again ", "ERROR", JOptionPane.ERROR_MESSAGE);
	 			return;
	 		}
	 		SRM.deleteRoom(mouse.roomName);	
	 	}
	 	
	 	//�� ���� ��ȸ
		void AllRooms()  {
			SRM=start.SRM;
			 //Ÿ��Ʋ �����
			vector = new Vector<String>();
			vector.addElement("no");
			vector.addElement("RoomName");
			vector.add("Capacity");
			vector.add("Bill per Hour");
			//vector.add("Bill per Day");
			
			try {
			
				model = new DefaultTableModel(vector,0) ;
				
				   // �� ����
					ArrayList<Room> Room = SRM.searchRoom();
					int i=0;
					for(Room data:Room){	
						
						Vector<String> v= new Vector<String>();
						v.add(String.valueOf(i+1));
						v.add(data.getRoomName());
						v.add(String.valueOf(data.getCapacity()));
						v.add(String.valueOf(data.getBill()));
						model.addRow(v);
						i++;
					}
					
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			
		
		    table = new JTable(model);
		    JScrollPane scroll = new JScrollPane(table);
			Container c = getContentPane();
			c.add("Center",scroll);
			c.add("South", p);
			c.add("East", p2);
			c.add("North", p3);
			
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == AddBt) {
					 try {
						new Manager_AddPage();
						
						inputFile.close();
						ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
						SRM.writeRoomInfos(output);
						output.close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 setVisible(false);
				
			}
			if(e.getSource() == AllBt) {
				try {
				new Manager_AllPage();
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				}catch(Exception e1) {
					
				}
			}
			if(e.getSource()==IncomeBt) {
				try {
				new Manager_IncomePage();
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
				}
				catch(Exception e1) {
					
				}
			}
           if(e.getSource()==ExitBt) {
           	
           	try {
					new StartPage(2);
					setVisible(false);
					
					inputFile.close();
					ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
					SRM.writeRoomInfos(output);
					output.close();
				} catch (Exception e1) {
					System.out.println("error exit");
					e1.printStackTrace();
				}	
			}
          if(e.getSource()==UpdateBt) {
				try {
					DeleteRooms();
					new Manager_DeletePage();
					setVisible(false);
				} catch (Exception e1) {
					System.out.println("error delete update");
					e1.printStackTrace();
				}
			}	
		}
	}


//������ �� ��ü ��ȸâ
class Manager_AllPage extends JFrame implements ActionListener{
	 
	    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));// ��ư�̳� ���� �� ȭ���� ����
		JPanel p2 = new JPanel(new GridLayout(5,1));// add, delete, all, income, exit to main ��ư�� �� ȭ��
		JButton AddBt = new JButton("ADD"); // ��ư �ʱ�ȭ
		JButton DeleteBt = new JButton("DELETE");
		JButton AllBt = new JButton("ALL");
		JButton IncomeBt = new JButton("INCOME");
		JButton ExitBt = new JButton("Exit to main");
		JButton SearchBt = new JButton("SEARCH"); //�� �̸����� �� ã�� ��ư
		JButton Search2Bt = new JButton("SEARCH"); // ����� ��ȭ��ȣ�� �� ã�� ��ư
		
		JLabel roomName =new JLabel("RoomName:");
		JLabel phoneNumber =new JLabel("     PhoneNumber:");
		
		static JTextField nameText= new JTextField(10);
		static JTextField phoneText= new JTextField(10);
		
		static StudyRoomManagement SRM;
		Vector <String> vector;
		DefaultTableModel model; //�� �߰�,������ �����ϰ� �ϱ� ���� DefaultTableModel ���
	    JTable table;
	    PhonePage user;
	    StartPage start;
	    MyMouseListener mouse=new MyMouseListener();
	    static boolean search,search2;
	    static ArrayList<Room> Room;
	    
	    static ObjectInputStream inputFile;
	    File file = new File("RoomInfo.dat"); 
	    
	    Manager_AllPage() throws Exception  {
	    	
	    	inputFile = new ObjectInputStream(new FileInputStream(file));
    		this.SRM = new StudyRoomManagement("manager123", inputFile);
   	
			setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
			setTitle("Manager"); // â �̸�
			 
			AllRooms();
			
			p.add(roomName);
			p.add(nameText);
			p.add(SearchBt);
			p.add(phoneNumber);
			p.add(phoneText);
			p.add(Search2Bt);
			
			p2.add(AddBt);
			p2.add(DeleteBt);
			p2.add(AllBt);
			p2.add(IncomeBt);
			p2.add(ExitBt);
			
			AddBt.addActionListener(this);
			AllBt.addActionListener(this);
			DeleteBt.addActionListener(this);
			IncomeBt.addActionListener(this);
			ExitBt.addActionListener(this);
			SearchBt.addActionListener(this);
			Search2Bt.addActionListener(this);
			table.addMouseListener(new MyMouseListener());
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
	     
	 	
	 	//���� ������ �� ���� ���
		void AllRooms()  {
			//SRM=start.SRM;
			 //Ÿ��Ʋ �����
			vector = new Vector<String>();
			vector.addElement("no");
			vector.addElement("RoomName");
			vector.add("Capacity");
			vector.add("Bill per Hour");
			//vector.add("Bill per Day");
			vector.add("Using"); //��뿩��
			vector.add("PhoneNumber"); //��ȭ��ȣ
			
			try {
			
				model = new DefaultTableModel(vector,0) ;
				//�� ����
				Room= SRM.searchRoom();
				
				//�� �̸��� search ��ư�� Ŭ���� ���
				if(search==true) {
					Room = SRM.SearchRoomName(nameText.getText());
					search=false;
				}
				//����� ��ȭ��ȣ�� search ��ư�� Ŭ���� ���
				else if(search2==true) {
					Room = SRM.SearchRoomPhone(phoneText.getText());
					search=false;
				}
					int i=0;
					for(Room data:Room){			
						Vector<String> v= new Vector<String>();
						v.add(String.valueOf(i+1));
						v.add(data.getRoomName());
						v.add(String.valueOf(data.getCapacity()));
						v.add(String.valueOf(data.getBill()));
						//v.add("");
						if(data.isRoomUsed()) {
							v.add("O");
							v.add(String.valueOf(data.getUser().getPhoneNumber()));
						}
						else
							v.add("X");
							v.add("X");
						model.addRow(v);
						i++;
						
					}
					
			}catch(Exception e1) {
				JOptionPane.showMessageDialog(null, "There is no room ", "ERROR", JOptionPane.ERROR_MESSAGE);
				//e1.printStackTrace();
			}
			
		
		    table = new JTable(model);
		    JScrollPane scroll = new JScrollPane(table);
			Container c = getContentPane();
			c.add("Center",scroll);
			c.add("North", p);
			c.add("East", p2);
			
		}
		
		
		
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == AddBt) {
				try {
					new Manager_AddPage();
					 setVisible(false);
					 
					 inputFile.close();
						ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
						SRM.writeRoomInfos(output);
						output.close();
				}catch(Exception e1) {
					
				}
				
			}
			if(e.getSource() == DeleteBt) {
				try {
				new Manager_DeletePage();
				 setVisible(false);
				 
				 inputFile.close();
					ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
					SRM.writeRoomInfos(output);
					output.close();
				}
				catch(Exception e1) {
					
				}
			}
			if(e.getSource()==IncomeBt) {
				try {
				new Manager_IncomePage();
				 setVisible(false);
				 
				 inputFile.close();
					ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
					SRM.writeRoomInfos(output);
					output.close();
				}
				catch(Exception e1) {
					
				}
			}
           if(e.getSource()==ExitBt) {
           	try {
        	   new StartPage(2);
				setVisible(false);
				
				inputFile.close();
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
				SRM.writeRoomInfos(output);
				output.close();
           	}
           	catch(Exception e1) {
           		
           	}
		}
           if(e.getSource()==AllBt) {
              	try {
           	   new Manager_AllPage();
   				setVisible(false);
   				
   				inputFile.close();
   				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
   				SRM.writeRoomInfos(output);
   				output.close();
              	}
              	catch(Exception e1) {
              		
              	}
   		}
           if(e.getSource()==SearchBt) {
          	 try {
          		 search=true;
          		new Manager_AllPage();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
					setVisible(false);
			}
           if(e.getSource()==Search2Bt) {
          	 try {
					search2=true;
					new Manager_AllPage();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
					setVisible(false);
			}
	}
}


		//���� ��ȸâ
		class Manager_IncomePage extends JFrame implements ActionListener {
			
			    JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));// ��ư�̳� ���� �� ȭ���� ����
				JPanel p2 = new JPanel(new GridLayout(5,1));// add, delete, all, income, exit to main ��ư�� �� ȭ��
				JButton AddBt = new JButton("ADD"); 
				JButton DeleteBt = new JButton("DELETE");
				JButton AllBt = new JButton("ALL");
				JButton IncomeBt = new JButton("INCOME");
				JButton EBt = new JButton("Update");
				JButton ExitBt = new JButton("Exit to main");
				JLabel tlabel =new JLabel("Total Income: ");
				JLabel totallabel ;
				static StudyRoomManagement SRM;
				StartPage start;
			    static ArrayList<Room> Room;
			    static int total;
			    DrawingPanel drawingPanel = new DrawingPanel();
				
				 static ObjectInputStream inputFile;
				    File file = new File("RoomInfo.dat"); 
				
			Manager_IncomePage() throws Exception {

				inputFile = new ObjectInputStream(new FileInputStream(file));
	    		this.SRM = new StudyRoomManagement("manager123", inputFile);
	    		
		        SRM=start.SRM;
				setBounds(280, 100, 700, 530); //â ũ�� ���� �� ��ġ ����
				setTitle("Manager"); // â �̸�
			
		     totalIncome();
		     
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				
				 p2.add(AddBt);
				 p2.add(AllBt);
				 p2.add(DeleteBt);
				 p2.add(IncomeBt);
				 p2.add(ExitBt);
				 
				 
				 
				AddBt.addActionListener(this);
				AllBt.addActionListener(this);
				DeleteBt.addActionListener(this);
				ExitBt.addActionListener(this);
			    
				Container c = getContentPane();
				
				c.add(drawingPanel, BorderLayout.CENTER);
				c.add("South",p);
				c.add("East",p2);
					
			}

			void totalIncome() throws Exception {
				
				Room= SRM.searchRoom();
				try {
				for(Room data:Room) {
					total=data.getPay();
				}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				 totallabel=new JLabel("Total Income: "+total+" won         ");
			     totallabel.setForeground(Color.BLACK);
			     totallabel.setFont(new Font("����", Font.PLAIN, 20));
			     totallabel.setBounds(250, 150, 491, 80);
			     p.add(totallabel);
			     p.add(EBt);
			     
			     EBt.addActionListener(new DrawActionListener(drawingPanel));
					
				
			}
		
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == AddBt) {
						 try {
							new Manager_AddPage();
							
							inputFile.close();
							ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
							SRM.writeRoomInfos(output);
							output.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						 setVisible(false);
					
				}
				if (e.getSource() == AllBt) {
					 try {
						new Manager_AllPage();
						
						inputFile.close();
						ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
						SRM.writeRoomInfos(output);
						output.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					 setVisible(false);
				
			}
				if(e.getSource() == DeleteBt) {
					 try {
						new Manager_DeletePage();
						
						inputFile.close();
						ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
						SRM.writeRoomInfos(output);
						output.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					 setVisible(false);
					
				}

		     if(e.getSource()==ExitBt) {
		     	
		     	try {
						new StartPage(2);
						setVisible(false);
						
						inputFile.close();
						ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
						SRM.writeRoomInfos(output);
						output.close();
						
					} catch (Exception e1) {
						System.out.println("error exit");
						e1.printStackTrace();
					}	
				}
				
			}	
		}
		


 class DrawActionListener implements ActionListener{
	 Manager_IncomePage total;

JTextField text1, text2, text3;
DrawingPanel drawingPanel;
 DrawActionListener (DrawingPanel drawingPanel) {

    this.drawingPanel = drawingPanel;
   

}

    public void actionPerformed(ActionEvent e) {

    try{

       int b2 = 0;
       int b1 = 0;
       int now = total.total;
       
      drawingPanel.setScores(b2, b1, now/1000);
      drawingPanel.repaint();

}
catch (NumberFormatException nfe) {

JOptionPane.showMessageDialog(drawingPanel, "�߸��� ���� �����Դϴ�.",  "���� �޼���", JOptionPane.ERROR_MESSAGE);

}

}

}
 //
		
		
		
	 
		