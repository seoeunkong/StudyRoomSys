import java.util.*;
import java.io.*;

//equals 함수 오버라이딩 
public class Room implements Serializable{
	private static final long serialVersionUID = 3015397676335049728L;
	
	private int capacity; // 최대 인원 수
	private String roomName; // 방 이름
	private int bill; // 시간 당 가격

	// check-in, check-out 시 사용
	private boolean isUsed; // 사용중인지
	private User user; // 사용중인 사용자 객체
	private Calendar startTime; // 사용 시작 시간(사용 종료 시간은 생략)
	static List<Integer> list=new ArrayList<Integer>();
	Boolean change=false;
    int total;

	// 생성자
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

	
	// 체크인
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

	// 체크아웃
	int checkOut() {
		isUsed = false;
		user = null;
		return pay(new GregorianCalendar());
	}

	// 지불해야할 금액 설정(수정 필요 >> Management로 이동)
	int pay(Calendar end) {
		int usedTime = 0; // 총 사용시간(시간으로 계산. ex 2시간 30분 사용 >> usedTime = 3)

		// 날짜를 넘어갔을 경우
		if (startTime.get(Calendar.DATE) != end.get(Calendar.DATE)) {
			//2일 이상 날짜가 지나갔을 경우 24시간을 더한다.
			usedTime += 24*(end.get(Calendar.DATE) - startTime.get(Calendar.DATE) - 1); 
			// 날이 지나가고, 한시간 이내로 사용했을 경우
			if (startTime.get(Calendar.HOUR_OF_DAY) == 23 && end.get(Calendar.HOUR_OF_DAY) == 0
					&& startTime.get(Calendar.MINUTE) + end.get(Calendar.MINUTE) <= 60) {
				usedTime += 1;
			}
			// 나머지 경우
			else {
				// 기본 시간
				usedTime = 24 - startTime.get(Calendar.HOUR_OF_DAY) + end.get(Calendar.HOUR_OF_DAY);
				// 사용한 분 고려
				if ((60 - startTime.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60)
					usedTime += 1;
			}
			
			  if(startTime.get(Calendar.MONTH) != end.get(Calendar.MONTH)) {
				list.remove(end.get(Calendar.MONTH)-1);
				//change=true;
			}
			 

		}
		// 그 이외의 경우
		else {
			// 기본 시간
			usedTime = end.get(Calendar.HOUR_OF_DAY) - startTime.get(Calendar.HOUR_OF_DAY);
			// 사용한 분 고려
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

	//ObjectOutStream으로 수정
	void writeRoomInfo(ObjectOutputStream output) throws Exception {		
		output.writeObject(this);
	}
	
	/*equals 함수*/
	public boolean equals(Object o) {
		if(o instanceof Room) {
			//방 이름
			if(this.roomName!=null && ((Room) o).getRoomName()!=null && this.roomName.equals(((Room) o).getRoomName()))
					return true;
			//사용자 전화번호
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
