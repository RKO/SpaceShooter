package main;


public class PackageCounter {
	private static int seconds = 1;
	private static long start = 0;
	private static int frames = 1;
	private static int fps;
	
    public static void tick() {
        frames++;
        if(System.currentTimeMillis() - start >= 1000) {
        	seconds++;
            start = System.currentTimeMillis();
            
        }
        fps = (frames/seconds);
        if(seconds > 60) {
        	seconds = 1;
        	frames = 0;
        	System.out.println("FPS reset");
        }
    }
    
    public static double getPPS() {
        return fps;
    }
}
