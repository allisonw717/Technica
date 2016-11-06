import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class MemeGenerator {
	private JFrame frame, selectionFrame,textFrame;
	private JPanel contentPane, selectionPane,textPane;
	private JLabel title;
	private JLabel imageLabel;
	private JButton importButton,selectButton, editTop, editBot,save, export,dankify;
	private JFileChooser fc; 
	private ArrayList<ImageIcon> images;
	private ArrayList<BufferedImage> bufferedImages;
	private ArrayList<BufferedImage> originals;
	private ImageIcon selectedImage;
	private BufferedImage selectedBuffer;
	private String text;
	private JTextField textField;
	private JTextArea log;
	private final int TOP_TEXT = 0;
	private final int BOTTOM_TEXT = 1;
	private int TEXT_INT;
	private int imageIndex;
	private boolean imported;
	public MemeGenerator(){
		frame = new JFrame();
		selectionFrame = new JFrame();
		contentPane = new JPanel();

		selectionPane = new JPanel();
		images = new ArrayList<ImageIcon>();
		bufferedImages = new ArrayList<BufferedImage>();
		originals = new ArrayList<BufferedImage>();
		setImages();
		imageIndex = 0;
		log = new JTextArea();
		imported = false;
		for(int i=0; i<4; i++){
			JButton button = new JButton();
			button.setIcon(images.get(i));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedImage = (ImageIcon) button.getIcon(); 
					for(int i=0; i<4;i++){
						if(selectedImage == images.get(i)){
							imageIndex = i;
						}
					}
					ImageIcon icon = new ImageIcon(originals.get(imageIndex));
					imageLabel.setIcon(icon);
					imageLabel.setText("");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  	
					selectionFrame.setVisible(false);
					editTop.setEnabled(true);
					editBot.setEnabled(true);

				}
			});

			selectionPane.add(button);
		}

		selectionPane.setLayout(new GridLayout(2,2));
		imageLabel = new JLabel("Photo will appear here");
		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		title = new JLabel("Meme Generator");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		selectButton = new JButton("Select Image");
		selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(imageIndex!=-1){
				BufferedImage copy = originals.get(imageIndex);
				bufferedImages.set(imageIndex,copy);
				//}
				imported = false;
				selectionFrame.setVisible(true);
				imageLabel.setIcon(selectedImage);
			}
		});


		importButton = new JButton("Import Image"); //do this later
		importButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		importButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imported = true;
				JFrame fileFrame = new JFrame();
				if (fc == null){
					fc = new JFileChooser();
					fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files","jpg","png","tif"));
					fc.setAcceptAllFileFilterUsed(false);
					int returnVal = fc.showDialog(fileFrame,"Use Image");
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						Image image = null;
						BufferedImage buffer = null;
						try{
							image = ImageIO.read(fc.getSelectedFile());
							buffer = ImageIO.read(fc.getSelectedFile());
						} catch (IOException e1){
						}
						selectedImage = new ImageIcon(image.getScaledInstance(450, 450, Image.SCALE_SMOOTH));
						selectedBuffer = getScaledImage(buffer, 450,450);
						File file = fc.getSelectedFile();
						imageLabel.setIcon(selectedImage);
						imageLabel.setText("");
						editTop.setEnabled(true);
						editBot.setEnabled(true);
						log.append("Attaching file: " + file.getName());
					} else {
						log.append("Attachment cancelled by user.");
					}
					log.setCaretPosition(log.getDocument().getLength());
					fc.setSelectedFile(null);

				}
			}
		});

		textFrame = new JFrame();
		textPane = new JPanel();
		save = new JButton("Save");
		textField = new JTextField(20);

		textPane.add(textField);
		textPane.add(save);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text = textField.getText();
				textFrame.setVisible(false);
				if(!imported){
					setText(bufferedImages.get(imageIndex),text,TEXT_INT);
					ImageIcon icon = new ImageIcon(bufferedImages.get(imageIndex));
					imageLabel.setIcon(icon);
					textField.setText("");
				}
				else{
					setText(selectedBuffer,text,TEXT_INT);
					ImageIcon icon = new ImageIcon(selectedBuffer);
					imageLabel.setIcon(icon);
					textField.setText("");
				}	
			}
		});


		text = "";
		editTop = new JButton("Add Top Text");
		editTop.setAlignmentX(Component.CENTER_ALIGNMENT);
		editTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFrame.setVisible(true);
				TEXT_INT = TOP_TEXT;
				editTop.setEnabled(false);
				export.setEnabled(true);
				dankify.setEnabled(true);
			}
		});
		editTop.setEnabled(false);


		editBot = new JButton("Add Bottom Text");
		editBot.setAlignmentX(Component.CENTER_ALIGNMENT);
		editBot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFrame.setVisible(true);
				TEXT_INT = BOTTOM_TEXT;
				editBot.setEnabled(false);
				export.setEnabled(true);
				dankify.setEnabled(true);
			}
		});
		editBot.setEnabled(false);

		export = new JButton("Export Meme");
		export.setAlignmentX(Component.CENTER_ALIGNMENT);
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File saveFile = new File("ExportedMeme.png");
				JFileChooser chooser = new JFileChooser();
				chooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files","jpg","png","tif"));
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setSelectedFile(saveFile);
				int rval = chooser.showSaveDialog(export);
				if (rval == JFileChooser.APPROVE_OPTION) {
					saveFile = chooser.getSelectedFile();
					try {
						if(!imported)
							ImageIO.write(bufferedImages.get(imageIndex), "PNG", saveFile);
						else
							ImageIO.write(selectedBuffer, "PNG", saveFile);
					} catch (IOException ex) {
						System.out.println("Export Failed");
					}
				}
			}
		});
		export.setEnabled(false);
		
		dankify = new JButton("Dankify B]");
		dankify.setAlignmentX(Component.CENTER_ALIGNMENT);
		dankify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!imported){
					dankify(bufferedImages.get(imageIndex));
					ImageIcon icon = new ImageIcon(bufferedImages.get(imageIndex));
					imageLabel.setIcon(icon);
				}
				else{
					dankify(selectedBuffer);
					ImageIcon icon = new ImageIcon(selectedBuffer);
					imageLabel.setIcon(icon);
				}
			}
		});
		dankify.setEnabled(false);


		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.add(title);
		contentPane.add(selectButton);
		contentPane.add(importButton);
		contentPane.add(imageLabel);
		contentPane.add(editTop);
		contentPane.add(editBot);
		contentPane.add(export);
		contentPane.add(dankify);

		textFrame.setContentPane(textPane);
		textFrame.setPreferredSize(new Dimension(300,100));
		textFrame.pack();
		textFrame.setVisible(false);
		textFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionFrame.setContentPane(selectionPane);
		selectionFrame.setPreferredSize(new Dimension(400,400));
		selectionFrame.pack();
		selectionFrame.setVisible(false);
		selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(contentPane);
		frame.setPreferredSize(new Dimension(600,670));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void setImages(){
		createAndAddIcon("src/awkwardPenguin.jpeg");
		createAndAddIcon("src/grumpyCat.jpg");
		createAndAddIcon("src/spongebob.jpg");
		createAndAddIcon("src/willyWonka.jpg");
	}
	private void createAndAddIcon(String fileName){
		Image image = null;
		BufferedImage img = null;
		try{
			image = ImageIO.read(new File(fileName));
			img = ImageIO.read(new File(fileName));
		} catch (IOException e1){

		}
		//ImageIcon iconBig = new ImageIcon(image.getScaledInstance(450, 450, Image.SCALE_SMOOTH));
		bufferedImages.add(getScaledImage(img, 450,450));
		originals.add(getScaledImage(img, 450,450));
		ImageIcon icon = new ImageIcon(image.getScaledInstance(170, 170, Image.SCALE_SMOOTH));
		images.add(icon);
	}
	private BufferedImage getScaledImage(Image srcImg, int w, int h){
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}
	private void setText(BufferedImage img, String s, int place){
		Graphics g = img.getGraphics();
		g.setColor(Color.WHITE);
		int fontSize = 50;
		Font font = new Font("Impact", Font.PLAIN, fontSize);
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics(font);
		int length = metrics.stringWidth(s);
		while(length > 450){
			if(fontSize>12)
				fontSize--;
			font = new Font("Impact", Font.PLAIN, fontSize);
			g.setFont(font);
			metrics = g.getFontMetrics(font);
			length = metrics.stringWidth(s);
		}
		int x = (img.getWidth() - metrics.stringWidth(s)) / 2;
		if(place == TOP_TEXT){
			g.drawString(s, x, 57);
		}
		else{
			g.drawString(s, x, 423);	
		}
	}
	private void dankify(BufferedImage img){
		Graphics g = img.getGraphics();
		g.setFont(new Font("Comic Sans",Font.BOLD,20));
		String[] comments = {"wow", "such meme" , "COOL","amaze"};
		Color[] colors = {Color.CYAN, Color.PINK, Color.magenta, Color.GREEN};
		for(int i=0; i<comments.length;i++){
			int x = (int) (Math.random()*400);
			int y = (int) (Math.random()*400) + 20;
			g.setColor(colors[(int) (Math.random()*colors.length)]);
			g.drawString(comments[i], x, y);
		}
	}
	public static void main (String args[]){
		new MemeGenerator();
	}
}
