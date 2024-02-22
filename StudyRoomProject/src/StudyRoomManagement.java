import java.util.*;
import java.io.*;

public class StudyRoomManagement {
	private ArrayList<Room> roomArray = new ArrayList<Room>();
	private String managerID; // 관리자 id
	static int total;
	// 생성자
	StudyRoomManagement(String managerID, ObjectInputStream OI) throws Exception {
      this.managerID = managerID;      
      roomArray = (ArrayList<Room>) OI.readObject();      
   }

	// managerID setter(getter은 보안을 위해 사용하지 않음)
	void setManagerID(String changeID) {
		this.managerID = changeID;
	}

	// managerID가 일치하는지 확인
	boolean matchMangerID(String managerID) {
		return (this.managerID.equals(managerID));
	}

	// 방 크기에 따른 "빈방" 찾기
	ArrayList<Room> searchEmptyRoom() throws Exception {
		ArrayList<Room> buff = new ArrayList<Room>();
		boolean foundRoom = false;

		for (int i = 0; i < roomArray.size(); i++) {
			Room room = roomArray.get(i);
			if (!room.isRoomUsed()) { // 입력된 방의 크기 이상의 capacity의 방을 출력
				buff.add(room);
				foundRoom = true;
			}
		}

		if (!foundRoom) // 찾지 못했을 시에 예외 처리
			throw new Exception("No Available Room");

		return buff;
	}
		
		// 방 이름으로 "방" 찾기
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

					if (!foundRoom) // 찾지 못했을 시에 예외 처리
						throw new Exception("No Available Room");

					return buff;
				}
				
				// 사용자 번호로 "방" 찾기
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

					if (!foundRoom) // 찾지 못했을 시에 예외 처리
						throw new Exception("No Available Room");

					return buff;
				}
		
		
	// 모든 방 찾기
	ArrayList<Room> searchRoom() throws Exception {
		if (roomArray.size() == 0)
			throw new Exception("No Room");
		else
			return roomArray;
	}
	
	//이미 있는 방인지 
	boolean searchRoom(String name) throws Exception{
		return roomArray.contains(new Room(name));
	}

	// 방 생성
	void makeRoom(Room newRoom) {
		roomArray.add(newRoom);
	}

	// 방 삭제 :equals 수정 완료
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

	// 방 사용(check-in) :equals 수정 완료
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

	// 방 사용 종료 및 사용 금액 반환 :equals 수정 완료
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
	
	// 방정보 저장, ObjectOutputStream은 UI에서 생성
	void writeRoomInfos(ObjectOutputStream output) throws Exception {
		output.writeObject(roomArray);
	}
}