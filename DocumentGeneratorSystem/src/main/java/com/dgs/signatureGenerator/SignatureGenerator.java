package com.dgs.signatureGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SignatureGenerator {
    public static byte[] generateSignatureImage(String name) throws IOException {
        if (name.contains("@innogent.in")) {
            name = name.replace("@innogent.in", "");
        }

        BufferedImage bufferedImage = new BufferedImage(400, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        // Set rendering hints for smooth text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Set font and color
        g2d.setFont(new Font("Ink free", Font.PLAIN, 48));
        g2d.setColor(Color.BLACK);

        // Draw the name
        g2d.drawString(name, 10, 50);
        g2d.dispose();

        // Convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }
}

