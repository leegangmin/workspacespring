package polymorphism;

public class SamsungTV implements TV {
	private Speaker speaker;
	private int price;

	
	public SamsungTV() {
		//파라미터가 없는 생성자
	}
	
	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public SamsungTV(Speaker speaker, int price) {
		this.speaker = speaker;
		this.price = price;
	}

	@Override
	public void powerOn() {
		System.out.println("삼성 티비 전원 온");
		System.out.println("가격 : "+price);
	}

	@Override
	public void powerOff() {
		System.out.println("삼성 티비 전원 오프");
	
	}

	@Override
	public void volumeUp() {
//		speaker = new SonySpeaker();
		speaker.volumeUp();
	
	}
		
	@Override
	public void volumeDown() {
//		speaker = new SonySpeaker();
		speaker.volumeDown();
	
	}

}
