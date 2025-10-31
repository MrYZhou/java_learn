package common;


import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTest {

    @Test
    public void test1() throws Exception {
        try {
            // 1. 加载原始图片
            BufferedImage originalImage = ImageIO.read(
                    new File("D:/Users/JNPF/Desktop/project/java_learn/jdk_character/src/test/resources/img/1.jpg"));

            // 2. 添加文字水印
            Font font = new Font("Arial", Font.BOLD, 60);
            BufferedImage textWatermarked = WatermarkUtils.addTextWatermark(
                    originalImage, "机密文件", Color.RED, font, 0.5f);
            WatermarkUtils.saveImage(textWatermarked, "output_text.jpg", "jpg");

            // 3. 添加图片水印
            BufferedImage watermarkImage = ImageIO.read(
                    new File("watermark.png"));
            BufferedImage imageWatermarked = WatermarkUtils.addImageWatermark(
                    originalImage, watermarkImage, 50, 50, 0.3f);
            WatermarkUtils.saveImage(imageWatermarked, "output_image.jpg", "jpg");

            System.out.println("水印添加完成！");
        } catch (IOException e) {
            System.err.println("处理图片时出错: " + e.getMessage());
        }
    }
}
