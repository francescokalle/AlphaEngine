package graphics;

import java.awt.image.BufferedImage;

public class Animation {
    private BufferedImage[] frames;
    private int currentFrame;
    private int speed;
    private int counter;

    public Animation(BufferedImage spriteSheet, int frameCount, int frameWidth, int frameHeight, int speed) {
        frames = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        this.speed = speed;
        this.currentFrame = 0;
    }

    public void update() {
        counter++;
        if (counter >= speed) {
            counter = 0;
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }
}
