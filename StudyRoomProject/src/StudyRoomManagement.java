import java.util.*;
import java.io.*;

public class StudyRoomManagement {
	private ArrayList<Room> roomArray = new ArrayList<Room>();
	private String managerID; // ������ id
	static int total;
	// ������
	StudyRoomManagement(String managerID, ObjectInputStream OI) throws Exception {
      this.managerID = managerID;      
      roomArray = (ArrayList<Room>) OI.readObject();      
   }

	// managerID setter(getter�� ������ ���� ������� ����)
	void setManagerID(String changeID) {
		this.managerID = changeID;
	}

	// managerID�� ��ġ�ϴ��� Ȯ��
	boolean matchMangerID(String managerID) {
		return (this.managerID.equals(managerID));
	}

	// �� ũ�⿡ ���� "���" ã��
	ArrayList<Room> searchEmptyRoom() throws Exception {
		ArrayList<Room> buff = new ArrayList<Room>();
		boolean foundRoom = false;

		for (int i = 0; i < roomArray.size(); i++) {
			Room room = roomArray.get(i);
			if (!room.isRoomUsed()) { // �Էµ� ���� ũ�� �̻��� capacity�� ���� ���
				buff.add(room);
				foundRoom = true;
			}
		}

		if (!foundRoom) // ã�� ������ �ÿ� ���� ó��
			throw new Exception("No Available Room");

		return buff;
	}
		
		// �� �̸����� "��" ã��
				ArrayList<Room> SearchRoomName (String roomName) throws Exception {
					ArrayList<Room> buff = new ArrayList<Room>();
					boolean foundRoom = false;
					for (int i = 0; i < roomArray.size(); i++) {
						Room room = roomArray.get(i);
						if ((room.getRoomName()).equals(roomName)) {
							buff.add(room);
							foundRoom = true;
						}
					}

					if (!foundRoom) // ã�� ������ �ÿ� ���� ó��
						throw new Exception("No Available Room");

					return buff;
				}
				
				// ����� ��ȣ�� "��" ã��
				ArrayList<Room> SearchRoomPhone (String phoneNumber) throws Exception {
					ArrayList<Room> buff = new ArrayList<Room>();
					boolean foundRoom = false;

					for (int i = 0; i < roomArray.size(); i++) {
						Room room = roomArray.get(i);
						if (room.isRoomUsed()&&(room.getUser().getPhoneNumber()).equals(phoneNumber)) {
							buff.add(room);
							foundRoom = true;
						}
					}

					if (!foundRoom) // ã�� ������ �ÿ� ���� ó��
						throw new Exception("No Available Room");

					return buff;
				}
		
		
	// ��� �� ã��
	ArrayList<Room> searchRoom() throws Exception {
		if (roomArray.size() == 0)
			throw new Exception("No Room");
		else
			return roomArray;
	}
	
	//�̹� �ִ� ������ 
	boolean searchRoom(String name) throws Exception{
		return roomArray.contains(new Room(name));
	}

	// �� ����
	void makeRoom(Room newRoom) {
		roomArray.add(newRoom);
	}

	// �� ���� :equals ���� �Ϸ�
	void deleteRoom(String roomName) throws Exception {		
		Room room = new Room(roomName);
		boolean foundRoom = roomArray.contains(room);
		if(foundRoom) {
			roomArray.remove(room);
		}
		else {
			throw new Exception("no room");
		}
	}

	// �� ���(check-in) :equals ���� �Ϸ�
	void checkIn(String roomName, User user) throws Exception {
		Room room = new Room(roomName);
		boolean foundRoom = roomArray.contains(room);
		if(foundRoom) {
			roomArray.get(roomArray.indexOf(room)).checkIn(user);
		}
		else {
			throw new Exception("no room");
		}
	}

	// �� ��� ���� �� ��� �ݾ� ��ȯ :equals ���� �Ϸ�
	int checkOut(String roomName) throws Exception {
		Room room = new Room(roomName);
		boolean foundRoom = roomArray.contains(room);
		
		if(foundRoom) {
		   total+=roomArray.get(roomArray.indexOf(room)).checkOut();
		    
		    for (int i = 0; i < roomArray.size(); i++) {
				Room room1 = roomArray.get(i);
			//	room1.list.add(total, (Calendar.MONTH)-1);
				
				room1.total=total;
			}
		    
			return roomArray.get(roomArray.indexOf(room)).checkOut();
		}
		else {
			throw new Exception("no room");
		}
	}
	
	int total() {
		return total;
	}
	
	// ������ ����, ObjectOutputStream�� UI���� ����
	void writeRoomInfos(ObjectOutputStream output) throws Exception {
		output.writeObject(roomArray);
	}
}