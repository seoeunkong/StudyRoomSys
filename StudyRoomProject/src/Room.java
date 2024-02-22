import java.util.*;
import java.io.*;

//equals �Լ� �������̵� 
public class Room implements Serializable{
	private static final long serialVersionUID = 3015397676335049728L;
	
	private int capacity; // �ִ� �ο� ��
	private String roomName; // �� �̸�
	private int bill; // �ð� �� ����

	// check-in, check-out �� ���
	private boolean isUsed; // ���������
	private User user; // ������� ����� ��ü
	private Calendar startTime; // ��� ���� �ð�(��� ���� �ð��� ����)
	static List<Integer> list=new ArrayList<Integer>();
	Boolean change=false;
    int total;

	// ������
	Room(String roomName, int capacity, int bill) {
		this.capacity = capacity;
		this.roomName = roomName;
		this.bill = bill;
		this.isUsed = false;
	}
	Room(String roomName){
		this.roomName = roomName;
	}
	Room(User user){
		this.user = user;
	}

   
	// getter & setter
	int getCapacity() {
		return capacity;
	}

	String getRoomName() {
		return roomName;
	}

	int getBill() {
		return bill;
	}

	
	int getPay() {
		return total;
		//return list.get((Calendar.MONTH)-1);
	}
	
	
	User getUser() {
		return user;
	}

	void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	void setBill(int bill) {
		this.bill = bill;
	}

	boolean isRoomUsed() {
		return isUsed;
	}

	
	// üũ��
	void checkIn(User user) {
		this.user = user;
		startTime = new GregorianCalendar();
		isUsed = true;
	}

	void checkIn(User user, Calendar startTime) {
		this.user = user;
		this.startTime = startTime;
		isUsed = true;
	}

	// üũ�ƿ�
	int checkOut() {
		isUsed = false;
		user = null;
		return pay(new GregorianCalendar());
	}

	// �����ؾ��� �ݾ� ����(���� �ʿ� >> Management�� �̵�)
	int pay(Calendar end) {
		int usedTime = 0; // �� ���ð�(�ð����� ���. ex 2�ð� 30�� ��� >> usedTime = 3)

		// ��¥�� �Ѿ�� ���
		if (startTime.get(Calendar.DATE) != end.get(Calendar.DATE)) {
			//2�� �̻� ��¥�� �������� ��� 24�ð��� ���Ѵ�.
			usedTime += 24*(end.get(Calendar.DATE) - startTime.get(Calendar.DATE) - 1); 
			// ���� ��������, �ѽð� �̳��� ������� ���
			if (startTime.get(Calendar.HOUR_OF_DAY) == 23 && end.get(Calendar.HOUR_OF_DAY) == 0
					&& startTime.get(Calendar.MINUTE) + end.get(Calendar.MINUTE) <= 60) {
				usedTime += 1;
			}
			// ������ ���
			else {
				// �⺻ �ð�
				usedTime = 24 - startTime.get(Calendar.HOUR_OF_DAY) + end.get(Calendar.HOUR_OF_DAY);
				// ����� �� ���
				if ((60 - startTime.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60)
					usedTime += 1;
			}
			
			  if(startTime.get(Calendar.MONTH) != end.get(Calendar.MONTH)) {
				list.remove(end.get(Calendar.MONTH)-1);
				//change=true;
			}
			 

		}
		// �� �̿��� ���
		else {
			// �⺻ �ð�
			usedTime = end.get(Calendar.HOUR_OF_DAY) - startTime.get(Calendar.HOUR_OF_DAY);
			// ����� �� ���
			if ((60 - startTime.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60
					|| startTime.get(Calendar.MINUTE) - end.get(Calendar.MINUTE) == 0)
				usedTime += 1;
		}
		/*
		for(int i=0;i<(Calendar.MONTH)-1;i++) {
			list.add(0);
			
	}
		
		list.add((Calendar.MONTH)-1,usedTime*bill);
		System.out.println(list.get((Calendar.MONTH)-1));
		*/
		return usedTime * bill;
	}

	//ObjectOutStream���� ����
	void writeRoomInfo(ObjectOutputStream output) throws Exception {		
		output.writeObject(this);
	}
	
	/*equals �Լ�*/
	public boolean equals(Object o) {
		if(o instanceof Room) {
			//�� �̸�
			if(this.roomName!=null && ((Room) o).getRoomName()!=null && this.roomName.equals(((Room) o).getRoomName()))
					return true;
			//����� ��ȭ��ȣ
			else if(this.user!=null && ((Room) o).getUser()!=null && this.user.getPhoneNumber().equals(((Room) o).getUser().getPhoneNumber()))
				return true;
			
			else
				return false;
		}
		
//		else if(o instanceof String) 
//			return this.roomName.equals(o);
//		
//		else if(o instanceof User) {
//			return this.user.getPhoneNumber().equals(((User) o).getPhoneNumber());
//		}
		
		return false;
	}

}
