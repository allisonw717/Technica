import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class MemeSelectionFrame extends JFrame{
	private JPanel imagePanel;
	private ArrayList<JButton> buttons;
	private ArrayList<ImageIcon> images;
	private Icon selectedImage;
	private JButton select;
	private boolean selected;
	public MemeSelectionFrame(){
		images = new ArrayList<ImageIcon>(4);
		buttons = new ArrayList<JButton>(4);
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(2,2));
		selected = false;
		setImages();
		for(int i=0; i<4; i++){
			JButton button = new JButton();
			button.setIcon(images.get(i));
			button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	selectedImage = button.getIcon();
	            	selected = true;
	            	try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	//close();
	            }
	        });
			buttons.add(button);
			imagePanel.add(button);
		}
		this.setContentPane(imagePanel);
		this.setPreferredSize(new Dimension(400,400));
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void setImages(){
		createAndAddIcon("src/awkwardPenguin.jpeg");
		createAndAddIcon("src/grumpyCat.jpg");
		createAndAddIcon("src/spongebob.jpg");
		createAndAddIcon("src/willyWonka.jpg");
	}
	private void createAndAddIcon(String fileName){
		Image image = null;
		try{
			image = ImageIO.read(new File(fileName));
		} catch (IOException e1){
			
		}
		ImageIcon icon = new ImageIcon(image.getScaledInstance(170, 170, Image.SCALE_SMOOTH));
		images.add(icon);
	}
	public boolean isSelected(){
		return selected;
	}
	public ImageIcon getImage(){
		return (ImageIcon) selectedImage;
	}
	public void close(){
		this.dispose();
	}
	public static void main (String Args[]){
		new MemeSelectionFrame();
	}
}
