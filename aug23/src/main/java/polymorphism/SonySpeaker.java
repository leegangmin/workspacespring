package polymorphism;

public class SonySpeaker implements Speaker {
	
	public SonySpeaker() {
		System.out.println("소니 스피커 객체 생성");
	}
	
	@Override
	public void volumeUp() {
		System.out.println("Sony Speaker Volume Up");
	}
	
	@Override
	public void volumeDown() {
		System.out.println("Sony Speaker Volume Down");
		
	}
}
