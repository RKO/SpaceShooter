package main;


import javax.sound.sampled.*;

public class Sound {
	private Clip clip;
	public static Sound laser = loadSound("/snd/hurt.wav");

	public static Sound loadSound(String fileName) {
		Sound sound = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); 
			volume.setValue(-10f);
		} catch (Exception e) {
			System.out.println(e);
		}
		return sound;
	}



	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				};//}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}