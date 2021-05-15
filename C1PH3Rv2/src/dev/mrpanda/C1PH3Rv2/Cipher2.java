package dev.mrpanda.C1PH3Rv2;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.prefs.*;

public class Cipher2 {
	public static JFrame f = new JFrame("C1PH3R v2 // Created by MrPandaDev");
	public static JPanel p = new JPanel();
	public static Image frameIcon = Toolkit.getDefaultToolkit().getImage(Cipher2.class.getResource("resources/c1ph3r.png"));
	public static String filePath = null;
	
	public static void savePreference(String value) {
        Preferences prefs = Preferences.userNodeForPackage(Cipher2.class);                
        prefs.put("filePath", value); 
    }
	
	public static String readPreference() {
        Preferences prefs = Preferences.userNodeForPackage(Cipher2.class);
        return prefs.get("filePath", "default");  
    }  
	
	public static void menu() {
		p.removeAll();
		
		JLabel intro = new JLabel("C1PH3R", SwingConstants.CENTER);
		intro.setBounds(20,20,100,100);
		intro.setFont(new Font("Impact", Font.PLAIN, 32));
		intro.setForeground(Color.WHITE);
		
		JLabel version = new JLabel("v2");
		version.setBounds(106,82,20,20);
		version.setFont(new Font("Impact", Font.PLAIN, 12));
		version.setForeground(Color.GRAY);
		
		JLabel creator = new JLabel("Created by MrPandaDev");
		creator.setBounds(10,120,150,15);
		creator.setFont(new Font("Arial", Font.PLAIN,10));
		creator.setForeground(Color.GRAY);
		creator.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://mrpanda.dev"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton en = new JButton("Encrypt");
		en.setBounds(140,20,100,100);
		en.setBackground(Color.DARK_GRAY);
		en.setForeground(Color.WHITE);
		en.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
				    @Override
				    public void run() {
				    	encrypt();
				    }
				});
			}
		});
		
		JButton de = new JButton("Decrypt");
		de.setBounds(260,20,100,100);
		de.setBackground(Color.DARK_GRAY);
		de.setForeground(Color.WHITE);
		de.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
				    @Override
				    public void run() {
				    	decrypt();
				    }
				});
			}
		});
		
		p.add(version);
		p.add(intro);
		p.add(creator);
		p.add(en);
		p.add(de);
		p.setBackground(Color.BLACK);
		
		p.revalidate();
		p.repaint();
		
		f.setSize(395, 180);
	}
	
	public static File file = null;
	public static JFileChooser fileChooser = null;
	public static void encrypt() {
		p.removeAll();
		
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		
		JLabel keyAsk = new JLabel("Key Path:");
		keyAsk.setBounds(25,20,60,20);
		keyAsk.setForeground(Color.WHITE);
		
		JTextField key = new JTextField();
		key.setBounds(25,50,430,20);
		key.setHorizontalAlignment(JTextField.CENTER);
		key.setBackground(Color.LIGHT_GRAY);
		key.setFont(new Font("Consolas", Font.PLAIN, 12));
		if(!filePath.equals("default")) {
			key.setText(file.getAbsolutePath());
		} else {
			key.setText("No key file has chosen. To generate, enter a file name on the dialog.");
		}
		key.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		key.setEditable(false);
		key.setForeground(Color.BLACK);

		JLabel invalid = new JLabel();
		invalid.setBounds(25,590,200,40);
		invalid.setForeground(Color.RED);
		
		JButton select = new JButton();
		if(filePath.equals("default")) {
			select.setText("Select The Key File");
		} else {
			select.setText("Select Another Key File");
		}
	    select.setBounds(265,20,190,20);
		select.setBackground(Color.DARK_GRAY);
		select.setForeground(Color.WHITE);
	    select.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		fileChooser.setDialogTitle("To generate a key, enter a file name into the box.");
	    		int returnVal = fileChooser.showOpenDialog(f);
	    		
	    		if(returnVal == JFileChooser.APPROVE_OPTION) {
	    			file = fileChooser.getSelectedFile();

	    			if(!file.getAbsolutePath().substring(file.getAbsolutePath().length()-4).equals(".txt")) {
	    				file = new File(file.getAbsolutePath() + ".txt");
	    			}
	    			
	    			filePath = file.getAbsolutePath();
	    			
	    			key.setText(filePath);
	    			savePreference(filePath);
	    			
	    			select.setText("Select Another Key File");
	    			
	    			if(!file.exists()) {
	    				try {
							file.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				try {
							@SuppressWarnings("unused")
							Key temp = new Key(file);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    				
	    				invalid.setText("Created a new key file.");
						invalid.setForeground(Color.GREEN);
						
						ActionListener listener = new ActionListener(){
					        public void actionPerformed(ActionEvent event){
					            invalid.setText("");
					            invalid.setForeground(Color.RED);
					        }
					    };
					    
						Timer timer = new Timer(1000, listener);
					    timer.setRepeats(false);
					    timer.start();
	    			}
	    		}
	    	}
	    });
		
		JLabel plainAsk = new JLabel("Plaintext:");
		plainAsk.setBounds(25,90,80,20);
		plainAsk.setForeground(Color.WHITE);
		
		JTextArea plain = new JTextArea();
		plain.setBackground(Color.LIGHT_GRAY);
		plain.setForeground(Color.BLACK);
		plain.setLineWrap(true);
		plain.setWrapStyleWord(true);
		plain.setFont(new Font("Consolas", Font.PLAIN, 12));
		plain.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		
		JScrollPane plainScr = new JScrollPane(plain);
		plainScr.setBounds(25,120,430,200);
		plainScr.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.GRAY;
				this.trackColor = Color.DARK_GRAY;
			}
			
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
					
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = super.createIncreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
		});
		plainScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JButton textSelect = new JButton("Import");
		textSelect.setBounds(265,90,90,20);
	    textSelect.setBackground(Color.DARK_GRAY);
	    textSelect.setForeground(Color.WHITE);
	    textSelect.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		fileChooser.setDialogTitle("Select a plaintext file.");
	    		int returnVal = fileChooser.showOpenDialog(f);
	    		
	    		if(returnVal == JFileChooser.APPROVE_OPTION) {
	    			File f = fileChooser.getSelectedFile();
	    			
	    			if(!f.getAbsolutePath().substring(f.getAbsolutePath().length()-4).equals(".txt")) {
	    				f = new File(f.getAbsolutePath() + ".txt");
	    			}
	    			
	    			if(!f.exists())
	    				return;
	    			
	    			String s = "";
	    			
	    			List<String> list = null;
	    			try {
						list = Files.readAllLines(Paths.get(f.getAbsolutePath()), Charset.forName("ISO-8859-1"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			
	    			for(String str : list) {
	    				if(str.equals(""))
	    					s += "\n";
	    				else
	    					s += str + "\n";
	    			}
	    			
	    			plain.setText(s);
	    		}
	    	}
	    });
		
		JLabel c = new JLabel("Ciphertext:");
		c.setBounds(25,340,80,20);
		c.setForeground(Color.WHITE);

		JTextArea cipher = new JTextArea();
		cipher.setBackground(Color.LIGHT_GRAY);
		cipher.setForeground(Color.BLACK);
		cipher.setLineWrap(true);
		cipher.setEditable(false);
		cipher.setFont(new Font("Consolas", Font.PLAIN, 12));
		cipher.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		
		JScrollPane cipherScr = new JScrollPane(cipher);
		cipherScr.setBounds(25,370,430,200);
		cipherScr.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.GRAY;
				this.trackColor = Color.DARK_GRAY;
			}
			
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
					
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = super.createIncreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
		});
		cipherScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JButton copyPlain = new JButton("Copy");
		copyPlain.setBounds(365,90,90,20);
		copyPlain.setBackground(Color.DARK_GRAY);
		copyPlain.setForeground(Color.WHITE);
		copyPlain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String plainStr = plain.getText();
				
				if(plainStr.length() == 0 || !plainStr.matches("\\A\\p{ASCII}*\\z")) {
					return;
				}
				
				StringSelection stringSelection = new StringSelection(plainStr);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				
				invalid.setText("Copied Plaintext!");
				invalid.setForeground(Color.GREEN);
				
				ActionListener listener = new ActionListener(){
			        public void actionPerformed(ActionEvent event){
			            invalid.setText("");
			            invalid.setForeground(Color.RED);
			        }
			    };
			    
				Timer timer = new Timer(1000, listener);
			    timer.setRepeats(false);
			    timer.start();
			}
		});
		
		JButton copyCipher = new JButton("Copy");
		copyCipher.setBounds(365,340,90,20);
		copyCipher.setBackground(Color.DARK_GRAY);
		copyCipher.setForeground(Color.WHITE);
		copyCipher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cipherStr = cipher.getText();
				
				if(cipherStr.length() == 0) {
					return;
				}
				
				StringSelection stringSelection = new StringSelection(cipherStr);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				
				invalid.setText("Copied Ciphertext!");
				invalid.setForeground(Color.GREEN);
				
				ActionListener listener = new ActionListener(){
			        public void actionPerformed(ActionEvent event){
			            invalid.setText("");
			            invalid.setForeground(Color.RED);
			        }
			    };
			    
				Timer timer = new Timer(1000, listener);
			    timer.setRepeats(false);
			    timer.start();
			}
		});
		
		JButton back = new JButton("Go Back");
		back.setBounds(265,590,90,40);
		back.setBackground(Color.DARK_GRAY);
		back.setForeground(Color.WHITE);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
				    @Override
				    public void run() {
				    	menu();
				    }
				});
			}
		});
		
		JButton enc = new JButton("Encrypt");
		enc.setBounds(365,590,90,40);
		enc.setBackground(Color.DARK_GRAY);
		enc.setForeground(Color.WHITE);
		enc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(file == null) {
					invalid.setText("Invalid Key.");
					
					ActionListener listener = new ActionListener(){
				        public void actionPerformed(ActionEvent event){
				            invalid.setText("");
				        }
				    };
				    
					Timer timer = new Timer(1000, listener);
				    timer.setRepeats(false);
				    timer.start();
					return;
				} else if(!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				Key k = null;
				try {
					k = new Key(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					if(!k.isValid()) {
						invalid.setText("Invalid Key.");
						
						ActionListener listener = new ActionListener(){
					        public void actionPerformed(ActionEvent event){
					            invalid.setText("");
					        }
					    };
					    
						Timer timer = new Timer(1000, listener);
					    timer.setRepeats(false);
					    timer.start();
						return;
					} else {
						invalid.setText("");
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String p = plain.getText().trim();
				
				Encrypt enc = new Encrypt(p, k);

				if(!enc.isValid()) {
					invalid.setText("Invalid Plaintext.");
					
					ActionListener listener = new ActionListener(){
				        public void actionPerformed(ActionEvent event){
				            invalid.setText("");
				        }
				    };
				    
					Timer timer = new Timer(1000, listener);
				    timer.setRepeats(false);
				    timer.start();
					return;
				} else {
					invalid.setText("");
				}
				
				cipher.setText(enc.getCipher());
			}
		});
		
		JButton export = new JButton("Export");
		export.setBounds(265,340,90,20);
	    export.setBackground(Color.DARK_GRAY);
	    export.setForeground(Color.WHITE);
	    export.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(!(cipher.getText().length() > 0))
	    				return;
	    		fileChooser.setDialogTitle("Enter a file name to export.");
	    		int returnVal = fileChooser.showSaveDialog(f);
	    		
	    		if(returnVal == JFileChooser.APPROVE_OPTION) {
	    			File f = fileChooser.getSelectedFile();
	    			
	    			if(!f.getAbsolutePath().substring(f.getAbsolutePath().length()-4).equals(".txt")) {
	    				f = new File(f.getAbsolutePath() + ".txt");
	    			}
	    			
	    			if(f.exists()) {
	    				int n = JOptionPane.showConfirmDialog(Cipher2.f, "File already exists. Do you want to overwrite?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
	    				
	    				if(n == JOptionPane.CLOSED_OPTION) {
	    					return;
	    				} else if(n == JOptionPane.NO_OPTION) {
	    					actionPerformed(e);
	    					return;
	    				}
	    			} else {
	    				try {
							f.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    			}
	    			
	    			FileWriter writer = null;
					try {
						writer = new FileWriter(f);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			try {
						writer.write(cipher.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			
	    			try {
						writer.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			try {
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			
					invalid.setText("Exported Ciphertext!");
					invalid.setForeground(Color.GREEN);
					
					ActionListener listener = new ActionListener(){
				        public void actionPerformed(ActionEvent event){
				            invalid.setText("");
				            invalid.setForeground(Color.RED);
				        }
				    };
				    
					Timer timer = new Timer(1000, listener);
				    timer.setRepeats(false);
				    timer.start();
	    		}
	    	}
	    });
		
		p.add(select);
		p.add(copyPlain);
		p.add(textSelect);
		p.add(copyCipher);
		p.add(back);
		p.add(keyAsk);
		p.add(key);
		p.add(plainAsk);
		p.add(plainScr);
		p.add(c);
		p.add(cipherScr);
		p.add(invalid);
		p.add(enc);
		p.add(export);
		p.setBackground(Color.BLACK);
		
		p.revalidate();
		p.repaint();

		f.setSize(495, 690);
	}
	
	public static void decrypt() {
		p.removeAll();
		
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		
		JLabel keyAsk = new JLabel("Key Path:");
		keyAsk.setBounds(25,20,60,20);
		keyAsk.setForeground(Color.WHITE);
		
		JTextField key = new JTextField();
		key.setBounds(25,50,430,20);
		key.setHorizontalAlignment(JTextField.CENTER);
		key.setBackground(Color.LIGHT_GRAY);
		key.setFont(new Font("Consolas", Font.PLAIN, 12));
		if(!filePath.equals("default")) {
			key.setText(file.getAbsolutePath());
		} else {
			key.setText("No key file has chosen.");
		}
		key.setEditable(false);
		key.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		key.setForeground(Color.BLACK);

		JLabel invalid = new JLabel();
		invalid.setBounds(25,590,200,40);
		invalid.setForeground(Color.RED);
		
		JButton select = new JButton();
		if(filePath.equals("default")) {
			select.setText("Select The Key File");
		} else {
			select.setText("Select Another Key File");
		}
	    select.setBounds(265,20,190,20);
		select.setBackground(Color.DARK_GRAY);
		select.setForeground(Color.WHITE);
	    select.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		fileChooser.setDialogTitle("Select a key file.");
	    		int returnVal = fileChooser.showOpenDialog(f);
	    		
	    		if(returnVal == JFileChooser.APPROVE_OPTION) {
	    			file = fileChooser.getSelectedFile();
	    			
	    			if(!file.getAbsolutePath().substring(file.getAbsolutePath().length()-4).equals(".txt")) {
	    				file = new File(file.getAbsolutePath() + ".txt");
	    			}
	    			
	    			filePath = file.getAbsolutePath();
	    			
	    			key.setText(filePath);
	    			savePreference(filePath);
	    			
	    			select.setText("Select Another Key File");
	    		}
	    	}
	    });
		
		JLabel plainAsk = new JLabel("Plaintext:");
		plainAsk.setBounds(25,340,80,20);
		plainAsk.setForeground(Color.WHITE);
		
		JTextArea plain = new JTextArea();
		plain.setBackground(Color.LIGHT_GRAY);
		plain.setForeground(Color.BLACK);
		plain.setLineWrap(true);
		plain.setEditable(false);
		plain.setWrapStyleWord(true);
		plain.setFont(new Font("Consolas", Font.PLAIN, 12));
		plain.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		
		JScrollPane plainScr = new JScrollPane(plain);
		plainScr.setBounds(25,370,430,200);
		plainScr.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.GRAY;
				this.trackColor = Color.DARK_GRAY;
			}
			
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
					
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = super.createIncreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
		});
		plainScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JLabel c = new JLabel("Ciphertext:");
		c.setBounds(25,90,80,20);
		c.setForeground(Color.WHITE);

		JTextArea cipher = new JTextArea();
		cipher.setBackground(Color.LIGHT_GRAY);
		cipher.setForeground(Color.BLACK);
		cipher.setLineWrap(true);
		cipher.setFont(new Font("Consolas", Font.PLAIN, 12));
		cipher.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		
		JScrollPane cipherScr = new JScrollPane(cipher);
		cipherScr.setBounds(25,120,430,200);
		cipherScr.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.GRAY;
				this.trackColor = Color.DARK_GRAY;
			}
			
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
					
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = super.createIncreaseButton(orientation);
				button.setBackground(Color.DARK_GRAY);
				return button;
			}
		});
		cipherScr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JButton textSelect = new JButton("Import");
		textSelect.setBounds(265,90,90,20);
	    textSelect.setBackground(Color.DARK_GRAY);
	    textSelect.setForeground(Color.WHITE);
	    textSelect.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		fileChooser.setDialogTitle("Select a ciphertext file.");
	    		int returnVal = fileChooser.showOpenDialog(f);
	    		
	    		if(returnVal == JFileChooser.APPROVE_OPTION) {
	    			File f = fileChooser.getSelectedFile();
	    			
	    			if(!f.getAbsolutePath().substring(f.getAbsolutePath().length()-4).equals(".txt")) {
	    				f = new File(f.getAbsolutePath() + ".txt");
	    			}
	    			
	    			if(!f.exists())
	    				return;
	    			
	    			List<String> list = null;
	    			try {
	    				list = Files.readAllLines(Paths.get(f.getAbsolutePath()), Charset.forName("ISO-8859-1"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			
	    			String s = "";
	    			
	    			for(String str : list) {
	    				s += str;
	    			}

					cipher.setText(s);
	    		}
	    	}
	    });
		
		JButton copyPlain = new JButton("Copy");
		copyPlain.setBounds(365,340,90,20);
		copyPlain.setBackground(Color.DARK_GRAY);
		copyPlain.setForeground(Color.WHITE);
		copyPlain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String plainStr = plain.getText();
				
				if(plainStr.length() == 0 || !plainStr.matches("\\A\\p{ASCII}*\\z")) {
					return;
				}
				
				StringSelection stringSelection = new StringSelection(plainStr);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				
				invalid.setText("Copied Plaintext!");
				invalid.setForeground(Color.GREEN);
				
				ActionListener listener = new ActionListener(){
			        public void actionPerformed(ActionEvent event){
			            invalid.setText("");
			            invalid.setForeground(Color.RED);
			        }
			    };
			    
				Timer timer = new Timer(1000, listener);
			    timer.setRepeats(false);
			    timer.start();
			}
		});
		
		JButton copyCipher = new JButton("Copy");
		copyCipher.setBounds(365,90,90,20);
		copyCipher.setBackground(Color.DARK_GRAY);
		copyCipher.setForeground(Color.WHITE);
		copyCipher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cipherStr = cipher.getText();
				
				if(cipherStr.length() == 0) {
					return;
				}
				
				StringSelection stringSelection = new StringSelection(cipherStr);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				
				invalid.setText("Copied Ciphertext!");
				invalid.setForeground(Color.GREEN);
				
				ActionListener listener = new ActionListener(){
			        public void actionPerformed(ActionEvent event){
			            invalid.setText("");
			            invalid.setForeground(Color.RED);
			        }
			    };
			    
				Timer timer = new Timer(1000, listener);
			    timer.setRepeats(false);
			    timer.start();
			}
		});
		
		JButton back = new JButton("Go Back");
		back.setBounds(265,590,90,40);
		back.setBackground(Color.DARK_GRAY);
		back.setForeground(Color.WHITE);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
				    @Override
				    public void run() {
				    	menu();
				    }
				});
			}
		});
		
		JButton dec = new JButton("Decrypt");
		dec.setBounds(365,590,90,40);
		dec.setBackground(Color.DARK_GRAY);
		dec.setForeground(Color.WHITE);
		dec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(file == null || !file.exists()) {
					invalid.setText("Invalid Key.");
					
					ActionListener listener = new ActionListener(){
				        public void actionPerformed(ActionEvent event){
				            invalid.setText("");
				        }
				    };
				    
					Timer timer = new Timer(1000, listener);
				    timer.setRepeats(false);
				    timer.start();
					return;
				}
				
				Key k = null;
				try {
					k = new Key(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					if(!k.isValid()) {
						invalid.setText("Invalid Key.");
						
						ActionListener listener = new ActionListener(){
					        public void actionPerformed(ActionEvent event){
					            invalid.setText("");
					        }
					    };
					    
						Timer timer = new Timer(1000, listener);
					    timer.setRepeats(false);
					    timer.start();
						return;
					} else {
						invalid.setText("");
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String p = cipher.getText().trim();
				
				Decrypt dec = new Decrypt(p, k);

				if(!dec.isValid()) {
					invalid.setText("Invalid Ciphertext.");
					
					ActionListener listener = new ActionListener(){
				        public void actionPerformed(ActionEvent event){
				            invalid.setText("");
				        }
				    };
				    
					Timer timer = new Timer(1000, listener);
				    timer.setRepeats(false);
				    timer.start();
					return;
				} else {
					invalid.setText("");
				}
				
				plain.setText(dec.getPlain());
			}
		});
		
		JButton export = new JButton("Export");
		export.setBounds(265,340,90,20);
	    export.setBackground(Color.DARK_GRAY);
	    export.setForeground(Color.WHITE);
	    export.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(!(plain.getText().length() > 0))
	    				return;
	    		fileChooser.setDialogTitle("Enter a file name to export.");
	    		int returnVal = fileChooser.showSaveDialog(f);
	    		
	    		if(returnVal == JFileChooser.APPROVE_OPTION) {
	    			File f = fileChooser.getSelectedFile();
	    			
	    			if(!f.getAbsolutePath().substring(f.getAbsolutePath().length()-4).equals(".txt")) {
	    				f = new File(f.getAbsolutePath() + ".txt");
	    			}
	    			
	    			if(f.exists()) {
	    				int n = JOptionPane.showConfirmDialog(Cipher2.f, "File already exists. Do you want to overwrite?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
	    				
	    				if(n == JOptionPane.CLOSED_OPTION) {
	    					return;
	    				} else if(n == JOptionPane.NO_OPTION) {
	    					actionPerformed(e);
	    					return;
	    				}
	    			} else {
	    				try {
							f.createNewFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	    			}
	    			
	    			FileWriter writer = null;
					try {
						writer = new FileWriter(f);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			try {
						writer.write(plain.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			
	    			try {
						writer.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			try {
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    			
					invalid.setText("Exported Plaintext!");
					invalid.setForeground(Color.GREEN);
					
					ActionListener listener = new ActionListener(){
				        public void actionPerformed(ActionEvent event){
				            invalid.setText("");
				            invalid.setForeground(Color.RED);
				        }
				    };
				    
					Timer timer = new Timer(1000, listener);
				    timer.setRepeats(false);
				    timer.start();
	    		}
	    	}
	    });
		
		p.add(select);
		p.add(copyPlain);
		p.add(copyCipher);
		p.add(textSelect);
		p.add(back);
		p.add(keyAsk);
		p.add(key);
		p.add(plainAsk);
		p.add(plainScr);
		p.add(c);
		p.add(cipherScr);
		p.add(invalid);
		p.add(dec);
		p.add(export);
		p.setBackground(Color.BLACK);
		
		p.revalidate();
		p.repaint();

		f.setSize(495, 690);
	}
	
	public static void main(String[] args) throws IOException {	
		p.setLayout(null);
			
		f.getContentPane().add(p);
		f.setIconImage(frameIcon);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File (.txt)", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		
		filePath = readPreference();
			
		if(!filePath.equals("default")) {
			file = new File(filePath);
			fileChooser.setSelectedFile(file);
		}
			
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				menu();
				f.setVisible(true);
			}
		});
	}
}
