import javax.swing.*;
import java.awt.*;
public class wtfFrame extends JFrame{
	private JPanel contentPane;
	public wtfFrame(){
		
		contentPane = new JPanel();
		contentPane.add(new JLabel("WTFFF"));
		setContentPane(contentPane);
		setPreferredSize(new Dimension(50,50));
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String Args[]){
		new wtfFrame();
	}
}
