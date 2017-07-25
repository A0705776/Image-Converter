import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ImageTo16BitHex {

	private JFrame frame;
	private JTextField input_destination;
	private static JTextField output_destination;
	private JLabel lblStatus;
	private static JLabel status_label;
	private JButton input_dest_btn;
	private JButton output_dest_btn;
	private JButton enter_btn;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageTo16BitHex window = new ImageTo16BitHex();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
					window.frame.setTitle("Alamin's Image to 4 Bit Hex Converter");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ImageTo16BitHex() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 497, 205);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("This Program will convert an image file to a 16 bit array in a text document.");
		lblNewLabel.setBounds(10, 5, 461, 20);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel);
		
		input_destination = new JTextField();
		input_destination.setBounds(10, 36, 371, 20);
		frame.getContentPane().add(input_destination);
		input_destination.setColumns(10);
		input_destination.setToolTipText("BMP/PNG/JPG File Destination");
		input_destination.setText("BMP/PNG/JPG File Destination");
		
		input_dest_btn = new JButton("Get");
		input_dest_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(fc);
				if (response == JFileChooser.APPROVE_OPTION){
					String temp = fc.getSelectedFile().toString();
					if(temp.endsWith(".bmp")||temp.endsWith(".png")||temp.endsWith(".jpg")){
						input_destination.setForeground(Color.black);
						input_destination.setText(fc.getSelectedFile().toString());
					}else{
						input_destination.setForeground(Color.red);
						input_destination.setText("Wrong File Format");
					}
				}else{
					String temp =input_destination.getText();
					if(!temp.endsWith(".bmp")||!temp.endsWith(".png")||!temp.endsWith(".jpg")){
						input_destination.setForeground(Color.red);
						input_destination.setText("File open operation cancelled");
					}
				}
			}
		});
		input_dest_btn.setBounds(391, 36, 80, 23);
		frame.getContentPane().add(input_dest_btn);
		
		output_destination = new JTextField();
		output_destination.setBounds(10, 67, 371, 20);
		frame.getContentPane().add(output_destination);
		output_destination.setColumns(10);
		output_destination.setToolTipText("Output Destination");
		output_destination.setText("Output Destination");
		
		output_dest_btn = new JButton("Get");
		output_dest_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showSaveDialog(fc);
				if (response == JFileChooser.APPROVE_OPTION){
					if(fc.getSelectedFile().toString().endsWith(".txt")){
						output_destination.setForeground(Color.black);
						output_destination.setText(fc.getSelectedFile().toString());
					}else{
						output_destination.setForeground(Color.black);
						output_destination.setText(fc.getSelectedFile().toString()+".txt");
					}
				}else{
					if(output_destination.getText().equals("Output Destination")){
						output_destination.setForeground(Color.red);
						output_destination.setText("File open operation cancelled");
					}
				}
			}
		});
		output_dest_btn.setBounds(391, 66, 80, 23);
		frame.getContentPane().add(output_dest_btn);
		
		enter_btn = new JButton("Enter");
		enter_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemp = input_destination.getText();
				File inputf = new File(itemp);
				File outputf = new File(output_destination.getText());
				
				// create new file if file dose not exist 
				if(!outputf.canWrite() && output_destination.getText().endsWith(".txt")){
					try {
						outputf.createNewFile();
					} catch (IOException e1) {
						if(inputf.canWrite()){input_destination.setForeground(Color.black);}
						output_destination.setForeground(Color.red);
						status_label.setForeground(Color.red);
						status_label.setText("Not a correct file directory");
					}
				}
				
				//checks all combination and output appropriate error message
				if(inputf.canRead() && outputf.canWrite()) { 
					if(itemp.endsWith(".bmp")||itemp.endsWith(".png")||itemp.endsWith(".jpg")){
						input_destination.setForeground(Color.black);
						output_destination.setForeground(Color.black);
						Image2Hex(input_destination.getText(),output_destination.getText());
					}else{
						if(outputf.canWrite()){output_destination.setForeground(Color.black);}
						input_destination.setForeground(Color.red);
						status_label.setForeground(Color.red);
						status_label.setText("Not a correct file format");
					}
				}else if(!outputf.canWrite() && inputf.canRead()){
					input_destination.setForeground(Color.black);
					output_destination.setForeground(Color.red);
					status_label.setForeground(Color.red);
					status_label.setText("Not a correct file directory");
				}else if(!inputf.canRead() && outputf.canWrite()){
					output_destination.setForeground(Color.black);
					input_destination.setForeground(Color.red);
					status_label.setForeground(Color.red);
					status_label.setText("Not a correct file directory");
				}else{
					output_destination.setForeground(Color.red);
					input_destination.setForeground(Color.red);
					status_label.setForeground(Color.red);
					status_label.setText("Not a correct file directory");
				}
				
				
				
			}
		});
		enter_btn.setBounds(10, 100, 461, 23);
		frame.getContentPane().add(enter_btn);
		
		lblStatus = new JLabel("Status: ");
		lblStatus.setBounds(10, 129, 46, 14);
		frame.getContentPane().add(lblStatus);
		
		status_label = new JLabel("N/A");
		status_label.setBounds(59, 129, 412, 14);
		frame.getContentPane().add(status_label);
		
	}
	public static void Image2Hex(String BMPFileName,String OutputName){
	    BufferedImage image;
		try {
			image = ImageIO.read(new File(BMPFileName));
		
		    int[][] array2D = new int[image.getHeight()][image.getWidth()]; //*
	
		    for (int xPixel =  0; xPixel < image.getHeight() ; xPixel++) //*
		    {
		        for (int yPixel =  0; yPixel < image.getWidth() ; yPixel++) //*
		        {
		            int color = image.getRGB(yPixel, xPixel); //*
		                        
		            int colorB = color & 0xFF;
		            color = color >> 8;
		            int colorG = color & 0xFF;
		            color = color >> 8;
		            int colorR = color & 0xFF;
		            color = color >> 8;
		            int contrast = color & 0xFF;
		            
		            color = (int)(((contrast/(255.0))*15) + (int)((colorR/(255.0))*15)*16 + (int)((colorG/(255.0))*15)*256 + (int)((colorB/(255.0))*15)*4096);
		            
		            array2D[xPixel][yPixel] = color; 
		        }
		    }
		    String temp = output_destination.getText();
		    PrintWriter writer = new PrintWriter(OutputName, "UTF-8");
		    writer.println("const uint16_t " + temp.substring(temp.lastIndexOf("\\")+1,temp.lastIndexOf(".")) +"[] = {");
		    for(int xPixel = image.getHeight() - 1; xPixel >= 0 ; xPixel--){
		    	for(int yPixel =  0; yPixel < image.getWidth() ; yPixel++){
		    		writer.print("0x");
		    		temp = Integer.toHexString(array2D[xPixel][yPixel]).toUpperCase();
		    		for(int i = 4 - temp.length(); i > 0;i--){writer.print("0");}
		    		writer.print(temp);
		    		writer.print(", ");
		    	}
		    	writer.println();
		    }
		    writer.print("}");
		    status_label.setForeground(Color.black);
		    status_label.setText("Conpleted with dimention Height: " + image.getHeight() + "Pixels & Width: " + image.getWidth()+"Pixels");
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}
