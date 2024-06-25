package myjavaproject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        try (InputStream imageStream = Main.class.getResourceAsStream("/images/image1.png")) {
            if (imageStream == null) {
                System.out.println("Image not found");
                return;
            }
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageStream.readAllBytes()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
            JLabel label = new JLabel(imageIcon);
            frame.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
}

