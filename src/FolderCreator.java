import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;import net.miginfocom.swing.MigLayout;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

@SuppressWarnings("serial")
public class FolderCreator extends JFrame {

	private JPanel contentPane;
	private JTextField txtText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FolderCreator frame = new FolderCreator();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FolderCreator() {
		setResizable(false);
		setTitle("FolderCreator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 376, 91);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(44, 47, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel lblText = new JLabel("yyyy-mm-dd");
		lblText.setForeground(Color.GREEN);
		lblText.setBackground(Color.BLACK);
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		txtText = new JTextField();
		txtText.setToolTipText("Der Name des Faches");
		txtText.setBackground(new Color(35, 39, 42));
		txtText.setForeground(new Color(0, 255, 0));
		
		txtText.setText("");
		txtText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtText.setColumns(10);
		contentPane.setLayout(new MigLayout("", "[66px][286px]", "[44px]"));
		contentPane.add(lblText, "cell 0 0,alignx left,aligny center");
		contentPane.add(txtText, "cell 1 0,grow");
		
		// FirstDayOfWeek
		String FirstDayOfWeek = firstDayOfWeek();
		lblText.setText(FirstDayOfWeek+" ");
		// Check for Folder
		String folderName = folderCheck(FirstDayOfWeek);
		if (folderName == "/") { // no folder
			//new File(FirstDayOfWeek).mkdir();
		} else { // folder exist :)
			txtText.setText(folderName.replace(FirstDayOfWeek, "").trim());
		}
		// rename folder if text changed and close on enter
		txtText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (folderName == "/") {
					new File((FirstDayOfWeek+" "+txtText.getText()).trim()).mkdir();
				} else {
					File oldFolderName = new File(folderName);
					File newFolderName = new File((FirstDayOfWeek+" "+txtText.getText()).trim());
					oldFolderName.renameTo(newFolderName);
				}
				
				System.exit(0);
			}
		});
		
	}
	
	  public static String firstDayOfWeek() {
		    // get today and clear time of day
		    Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		    cal.clear(Calendar.MINUTE);
		    cal.clear(Calendar.SECOND);
		    cal.clear(Calendar.MILLISECOND);
		    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		    return String.format("%04d"+".%02d"+".%02d", cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH)+1), cal.get(Calendar.DAY_OF_MONTH));
		  }
		  
		  public static String folderCheck(String FirstDayOfWeek) {
		    //Creating a File object for directory
		    File folder = new File(Paths.get("").toAbsolutePath().normalize().toString());
		    File[] listOfFiles = folder.listFiles();
		    //List of all directories
		    List<String> directories = new ArrayList<>();
		    
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isDirectory()) {
		        directories.add(listOfFiles[i].getName());
		      }
		    }
		      for(String dName: directories) if (dName.startsWith(FirstDayOfWeek)) return dName;
		    return "/";
		  }

}