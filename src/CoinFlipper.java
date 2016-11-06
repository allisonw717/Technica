import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
public class CoinFlipper {
	JFrame frame; 
	JPanel contentPane;
	JButton flipButton;
	JLabel coinImage;
	public CoinFlipper(){
		frame = new JFrame();
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		Image reHeads = null;
		Image reTails = null;
		try {
			reHeads = ImageIO.read(new File("src/heads.jpg"));
			reTails = ImageIO.read(new File("src/tails.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ImageIcon heads = new ImageIcon(reHeads.getScaledInstance(300,300,Image.SCALE_SMOOTH));
		ImageIcon tails = new ImageIcon(reTails.getScaledInstance(300,300,Image.SCALE_SMOOTH));
		flipButton = new JButton("Flip");
		flipButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int num = (int) (Math.random()*2);
            	if(num == 0)
            		coinImage.setIcon(heads);
            	else
            		coinImage.setIcon(tails);
            }
        });
				
		coinImage = new JLabel();
		coinImage.setIcon(heads);
		coinImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		flipButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(coinImage);
		contentPane.add(flipButton);
		frame.setPreferredSize(new Dimension(500,500));
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String Args[]){
		new CoinFlipper();
	}
}
