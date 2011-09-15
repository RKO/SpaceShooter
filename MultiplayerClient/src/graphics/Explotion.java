package graphics;

public class Explotion extends Animation {

	public Explotion(int x, int y, int width, int height) {
		super(x, y, width, height);
		String[] images = new String[]{ImageHandler.EXPLOTION1, ImageHandler.EXPLOTION2, ImageHandler.EXPLOTION3, ImageHandler.EXPLOTION4};
		setImages(images);
	}
}
