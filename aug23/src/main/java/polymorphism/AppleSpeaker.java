package polymorphism;

public class AppleSpeaker implements Speaker {
	
	public AppleSpeaker() {
		System.out.println("애플 스피커 객체 생성");
	}

	@Override
	public void volumeUp() {
		System.out.println("Apple Speaker Volume UP");

	}

	@Override
	public void volumeDown() {
		System.out.println("Apple Speaker Volume Down");

	}

}
