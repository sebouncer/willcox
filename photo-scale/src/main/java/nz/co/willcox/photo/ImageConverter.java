package nz.co.willcox.photo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {

    public File createThumbnailCrop(String imageUrl, String targetPath) {
        final int imageSize = 100;
        File thumbnail = new File(targetPath);

        try {
            thumbnail.getParentFile().mkdirs();
            thumbnail.createNewFile();
            BufferedImage sourceImage = ImageIO.read(new File(imageUrl));
            float width = sourceImage.getWidth();
            float height = sourceImage.getHeight();

            BufferedImage img2;
            if (width > height) {
                float scaledWidth = (width / height) * (float) imageSize;
                float scaledHeight = imageSize;

                BufferedImage img = new BufferedImage((int) scaledWidth, (int) scaledHeight, sourceImage.getType());
                Image scaledImage = sourceImage.getScaledInstance((int) scaledWidth, (int) scaledHeight, Image.SCALE_SMOOTH);
                img.createGraphics().drawImage(scaledImage, 0, 0, null);

                int offset = (int) ((scaledWidth - scaledHeight) / 2f);
                img2 = img.getSubimage(offset, 0, imageSize, imageSize);
            }
            else if (width < height) {
                float scaledWidth = imageSize;
                float scaledHeight = (height / width) * (float) imageSize;

                BufferedImage img = new BufferedImage((int) scaledWidth, (int) scaledHeight, sourceImage.getType());
                Image scaledImage = sourceImage.getScaledInstance((int) scaledWidth, (int) scaledHeight, Image.SCALE_SMOOTH);
                img.createGraphics().drawImage(scaledImage, 0, 0, null);

                int offset = (int) ((scaledHeight - scaledWidth) / 2f);
                img2 = img.getSubimage(0, offset, imageSize, imageSize);
            }
            else {
                img2 = new BufferedImage(imageSize, imageSize, sourceImage.getType());
                Image scaledImage = sourceImage.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
                img2.createGraphics().drawImage(scaledImage, 0, 0, null);
            }
            ImageIO.write(img2, "png", thumbnail);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return thumbnail;
    }

}
