package common;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WatermarkUtils {

    // 添加文字水印
    public static BufferedImage addTextWatermark(
            BufferedImage originalImage,
            String watermarkText,
            Color color,
            Font font,
            float opacity) {

        BufferedImage watermarkedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();
        g2d.drawImage(originalImage, 0, 0, null);

        // 设置水印透明度
        AlphaComposite alphaChannel = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaChannel);

        // 设置水印文字颜色和字体
        g2d.setColor(color);
        g2d.setFont(font);

        // 计算水印位置（居中）
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = (originalImage.getWidth() - fontMetrics.stringWidth(watermarkText)) / 2;
        int y = (originalImage.getHeight() - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();

        // 添加水印
        g2d.drawString(watermarkText, x, y);
        g2d.dispose();

        return watermarkedImage;
    }

    // 添加图片水印
    public static BufferedImage addImageWatermark(
            BufferedImage originalImage,
            BufferedImage watermarkImage,
            int x, int y,
            float opacity) throws IOException {

        BufferedImage watermarkedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D) watermarkedImage.getGraphics();
        g2d.drawImage(originalImage, 0, 0, null);

        // 设置水印透明度
        AlphaComposite alphaChannel = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaChannel);

        // 添加水印图片
        g2d.drawImage(watermarkImage, x, y, null);
        g2d.dispose();

        return watermarkedImage;
    }

    // 保存图片到文件
    public static void saveImage(BufferedImage image, String outputPath, String format)
            throws IOException {
        ImageIO.write(image, format, new File(outputPath));
    }
}
