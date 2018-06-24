package g2d.utils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtils {
    public static BufferedImage readImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage flipImageVertically(BufferedImage srcImg) {
        BufferedImage dstImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        AffineTransform trans = AffineTransform.getTranslateInstance(0, srcImg.getHeight());
        trans.scale(1, -1);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BICUBIC);
        op.filter(srcImg, dstImg);
        return dstImg;
    }

    public static byte[] readRGBAPixels(BufferedImage img) {
        int pixels[] = img.getRGB(0, 0,
                img.getWidth(), img.getHeight(),
                null, 0, img.getWidth());
        byte data[] = new byte[pixels.length * 4];
        for (int i = 0; i < pixels.length; i++) {
            byte a = (byte) ((pixels[i] >> 24) & 0xFF);
            byte r = (byte) ((pixels[i] >> 16) & 0xFF);
            byte g = (byte) ((pixels[i] >> 8) & 0xFF);
            byte b = (byte) (pixels[i] & 0xFF);
            data[i * 4] = r;
            data[i * 4 + 1] = g;
            data[i * 4 + 2] = b;
            data[i * 4 + 3] = a;
        }
        return data;
    }
}