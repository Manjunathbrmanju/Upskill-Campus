package hrm;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class EmployeeMangement extends JFrame 
{ 
    static JFrame f;
    JPanel p1; 
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,imageLabel;
    JComboBox cb1;
    JTextField t1,t2,t3,t4,t5,t6,t7,t8,t9;
    JRadioButton r1,r2;
    ButtonGroup bg; 
    JButton b1,b2,b3,b4,b5;
    String a,b;
    public static EmployeeMangement instance = null;
    String[] options = {"Sales","Accounts","Marketing","IT"};
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    EmployeeMangement()
    { 
        f = new JFrame("Employee Mangement");
        f.setLayout(null);
        
        p1 = new JPanel(); 
        p1.setLayout(null);
        p1.setBackground(Color.WHITE);
        p1.setBounds(0,0,950,50);
        f.add(p1);
        
        l1 = new JLabel("Employee Mangement");
        l1.setBounds(250,0,300,50); 
        l1.setFont(new Font("Segoe UI",Font.BOLD,24));
        l1.setForeground(Color.RED);
        p1.add(l1);
        
        ImageIcon image = new ImageIcon (getClass ().getResource ("/hrm/Pictures/emp.png"));
        Image scaledImage = image.getImage ().getScaledInstance (700, 700,Image.SCALE_DEFAULT);
        image = new ImageIcon (scaledImage);
        JLabel imageLabel = new JLabel (image);
        imageLabel.setBounds (3,60,730,570); 
        f.add (imageLabel);
        
        l2 = new JLabel("Department"); 
        l2.setBounds(40,0,150,100); 
        l2.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l2.setForeground(Color.BLACK); 
        imageLabel.add(l2);
        
        cb1 = new JComboBox(options);
        cb1.setSelectedIndex(-1);
        cb1.setBounds(180,40,150,30); 
        cb1.setFont(new Font("Segoe UI", Font.PLAIN,16));
        imageLabel.add(cb1);
        cb1.addItemListener(new ItemListener()
        { 
            public void itemStateChanged(ItemEvent e)
            { 
                String item = (String) e.getItem();
                if (item.equals("Sales")) 
                { 
                    t1.setText("10");
                    b = t1.getText();
                }
                else if (item.equals("Accounts")) 
                { 
                    t1.setText("20");
                    b = t1.getText();
                }
                else if (item.equals("Marketing")) 
                { 
                    t1.setText("30");
                    b = t1.getText();
                }
                else if (item.equals("IT")) 
                { 
                    t1.setText("40");
                    b = t1.getText();
                }
            }
        });
        
        l3 = new JLabel("Employee ID"); 
        l3.setBounds(40,80,150,100); 
        l3.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l3.setForeground(Color.BLACK);;
        imageLabel.add(l3);
        
        t1 = new JTextField(3); 
        t1.setBounds(180,120,150,30); 
        t1.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t1);
        t1.addKeyListener(new KeyAdapter()
        { 
            public void keyTyped(KeyEvent e)
            {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) 
                    e.consume();
                    if (t1.getText().length() == 3)
                    {
                        JOptionPane.showMessageDialog(imageLabel, "Only 3 digits are allowed!", "Error", JOptionPane.ERROR_MESSAGE);
                        t1.setText(null);
                    } 
            }
        });
        t1.addFocusListener(new FocusAdapter()
        { 
            public void focusLost(FocusEvent e)
            {
                if (t1.getText().length() == 3)
                { 
                    if (t1.getText().isEmpty())
                        JOptionPane.showMessageDialog(imageLabel,"Enter the Employee ID","Error",JOptionPane.ERROR_MESSAGE);
                    else if (Integer.parseInt(t1.getText().substring(0,1)) != 1 && Integer.parseInt(t1.getText().substring(0,1)) != 2 && Integer.parseInt(t1.getText().substring(0,1)) != 3&& Integer.parseInt(t1.getText().substring(0,1)) != 4)
                    {
                        JOptionPane.showMessageDialog(imageLabel,"Invalid ID","Error",JOptionPane.ERROR_MESSAGE);
                        t1.setText(null);
                    }
                    else
                    {
                        ConnectionClass obj = new ConnectionClass();
                        String sql = "Select *from empmang where eid = ?";
                        PreparedStatement ps = null;
                        try
                        {
                            ps = obj.con.prepareStatement(sql);
                            ps.setString(1, t1.getText());
                            ResultSet rs = null;
                            rs = ps.executeQuery();
                            if (rs.next())
                            {
                                JOptionPane.showMessageDialog(imageLabel,"ID already exist","Error",JOptionPane.ERROR_MESSAGE);
                                t1.setText(b);
                            }
                        }
                        catch (SQLException ex)
                        {
                            Logger.getLogger(EmployeeMangement.class.getName())
                            .log(Level.SEVERE, null, ex);
                        }
                    } 
                }
                else 
                { 
                    JOptionPane.showMessageDialog(imageLabel,"Invalid ID","Error",JOptionPane.ERROR_MESSAGE);
                    t1.setText(null);
                }
            }
        });
        
        l4 = new JLabel("Name"); 
        l4.setBounds(40,150,150,100); 
        l4.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l4.setForeground(Color.BLACK);;
        imageLabel.add(l4);
        
        t2 = new JTextField(); 
        t2.setBounds(180,190,150,30); 
        t2.setFont(new Font("Segeo UI",Font.PLAIN,16)); 
        imageLabel.add(t2);
        t2.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            { 
                char c = e.getKeyChar();
                if (!Character.isLetter(c)&& !Character.isWhitespace(c)) 
                    e.consume();
            } 
        });
        
        l5 = new JLabel("Date of Birth"); 
        l5.setBounds(40,230,150,100); 
        l5.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l5.setForeground(Color.BLACK);;
        imageLabel.add(l5);
        
        t3 = new JTextField("dd/mm/yyyy"); 
        t3.setBounds(180,270,150,30); 
        t3.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t3);
        t3.addFocusListener(new FocusAdapter()
        { 
            public void focusGained(FocusEvent e)
            {
                t3.setText(null);
            }
            public void focusLost(FocusEvent e)
            { 
                if (t3.getText().length() == 10)
                { 
                    int year,month,date,leapyear;
                    year =Integer.parseInt(t3.getText().substring(6,10));
                    month =Integer.parseInt(t3.getText().substring(3,5));
                    date = Integer.parseInt(t3.getText().substring(0,2));
                    if (year < 2023)
                    { 
                        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                        { 
                            if (date > 31)
                            { 
                                JOptionPane.showMessageDialog(imageLabel,"Invalid Date","Error",JOptionPane.ERROR_MESSAGE);
                                t3.setText("dd/mm/yyyy");
                            }
                        }
                        else if (month == 4 || month == 6 || month == 9 || month == 11)
                        { 
                            if (date > 30)
                            { 
                                JOptionPane.showMessageDialog(imageLabel,"Invalid Date","Error",JOptionPane.ERROR_MESSAGE); 
                                t3.setText("dd/mm/yyyy");
                            }
                        } 
                        else if (month == 2)
                        { 
                            leapyear = year%4; 
                            if (leapyear == 0)
                            { 
                                if (date > 29)
                                { 
                                    JOptionPane.showMessageDialog(imageLabel,"Invalid Date","Error",JOptionPane.ERROR_MESSAGE); 
                                    t3.setText("dd/mm/yyyy");
                                }
                            }
                            else 
                            { 
                                if (date > 28)
                                { 
                                    JOptionPane.showMessageDialog(imageLabel,"Invalid Date","Error",JOptionPane.ERROR_MESSAGE); 
                                    t3.setText("dd/mm/yyyy");
                                }
                            }
                        }
                        else 
                        { 
                            JOptionPane.showMessageDialog(imageLabel,"Invalid Month","Error",JOptionPane.ERROR_MESSAGE); 
                            t3.setText("dd/mm/yyyy");
                        }
                    }
                    else 
                    { 
                        JOptionPane.showMessageDialog(imageLabel,"Invalid year","Error",JOptionPane.ERROR_MESSAGE); 
                        t3.setText("dd/mm/yyyy");
                    }
                }
                else 
                { 
                    JOptionPane.showMessageDialog(imageLabel, "Use the correct format","Error",JOptionPane.ERROR_MESSAGE);
                    t3.setText("dd/mm/yyyy");
                }
            }
        });
        t3.addKeyListener(new KeyAdapter()
        { 
            public void keyTyped(KeyEvent e)
            { 
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '/') 
                    e.consume();
            }
        });
        
        l6 = new JLabel("Gender"); 
        l6.setBounds(40,310,150,100); 
        l6.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l6.setForeground(Color.BLACK);;
        imageLabel.add(l6);
        
        r1 = new JRadioButton("Male"); 
        r1.setBounds(180,340,70,40); 
        imageLabel.add(r1);
        r1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            { 
                if (r1.isSelected()) 
                    a ="Male";
            }
        });
        
        r2 = new JRadioButton("Female"); 
        r2.setBounds(250,340,70,40); 
        imageLabel.add(r2);
        r2.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                if (r2.isSelected())
                    a ="Female";
            }
        });
        
        bg = new ButtonGroup(); 
        bg.add(r1); 
        bg.add(r2);
        
        l7 = new JLabel("Address"); 
        l7.setBounds(40,390,150,100); 
        l7.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l7.setForeground(Color.BLACK);;
        imageLabel.add(l7);
        
        t4 = new JTextField(); 
        t4.setBounds(180,430,150,30); 
        t4.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t4);
        t4.addKeyListener(new KeyAdapter()
        { 
            public void keyTyped(KeyEvent e)
            { 
                char c = e.getKeyChar();
                if (!Character.isLetter(c)&& !Character.isWhitespace(c)) 
                    e.consume();
            }
        });
        
        l8 = new JLabel("Phone No."); 
        l8.setBounds(430,0,150,100); 
        l8.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l8.setForeground(Color.BLACK);;
        imageLabel.add(l8);
        
        t5 = new JTextField(); 
        t5.setBounds(530,40,150,30); 
        t5.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t5);
        t5.addFocusListener(new FocusAdapter()
        { 
            public void focusLost(FocusEvent e)
            {
                if (t5.getText().length() != 10)
                {
                    JOptionPane.showMessageDialog(imageLabel, "Enter 10 Digits", "Error", JOptionPane.ERROR_MESSAGE);
                    t5.setText(null);
                }
            }
        });
        t5.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                if (t5.getText().length() == 10)
                    e.consume();
                char c = e.getKeyChar();
                if (!Character.isDigit(c))
                    e.consume();
            }
        });
        
        l9 = new JLabel("Email ID"); 
        l9.setBounds(430,80,150,100); 
        l9.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l9.setForeground(Color.BLACK);;
        imageLabel.add(l9);
        
        t6 = new JTextField();
        t6.setBounds(530,120,150,30);
        t6.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t6);
        t6.addFocusListener(new FocusAdapter()
        { 
            public boolean isValidEmail(String email) 
            {
                return email.matches(EMAIL_PATTERN);
            }
            public void focusLost(FocusEvent e)
            { 
                if (!isValidEmail(t6.getText())) 
                    JOptionPane.showMessageDialog(imageLabel, "Invalid email address","Error",JOptionPane.ERROR_MESSAGE);
            }  
        });
        
        l10 = new JLabel("Position"); 
        l10.setBounds(430,150,150,100); 
        l10.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l10.setForeground(Color.BLACK);
        imageLabel.add(l10);
        
        t7 = new JTextField(); 
        t7.setBounds(530,190,150,30); 
        t7.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t7);
        t7.addKeyListener(new KeyAdapter()
        { 
            public void keyTyped(KeyEvent e)
            { 
                char c = e.getKeyChar();
                if (!Character.isLetter(c)&& !Character.isWhitespace(c)) 
                    e.consume();
            }
        });
        
        b1 = new JButton("Add");
        b1.setBackground(Color.WHITE);
        b1.setBounds(70,510,100,30); 
        b1.setForeground(Color.BLACK);
        imageLabel.add(b1);
        b1.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                if (isvalid())
                { 
                    try
                    {   
                        String eid,ename,dept,dob,gender,address,phoneno,emailid,position,experience,qualification;
                        ConnectionClass obj = new ConnectionClass();
                        eid =t1.getText(); 
                        ename = t2.getText(); 
                        dept = (String)cb1.getSelectedItem(); 
                        dob = t3.getText();
                        gender = a;
                        address = t4.getText(); 
                        phoneno = t5.getText(); 
                        emailid = t6.getText(); 
                        position = t7.getText();
                        experience = t8.getText();
                        qualification = t9.getText();
                        String query ="Insert into empmang (eid, ename, dept, dob, gender, address, phoneno, emailid, position,experience,qualification) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
                        PreparedStatement stmt = obj.con.prepareStatement(query);
                        stmt.setString(1, eid);
                        stmt.setString(2, ename);
                        stmt.setString(3, dept);
                        stmt.setString(4, dob);
                        stmt.setString(5, gender);
                        stmt.setString(6, address);
                        stmt.setString(7, phoneno);
                        stmt.setString(8, emailid);
                        stmt.setString(9, position);
                        stmt.setString(10, experience);
                        stmt.setString(11,qualification);
                        int rowsInserted = stmt.executeUpdate();
                        if (rowsInserted > 0)
                        { 
                            JOptionPane.showMessageDialog(imageLabel,"Added Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                            clr();
                        }
                        else 
                            JOptionPane.showMessageDialog(imageLabel,"Not Added","Error",JOptionPane.ERROR_MESSAGE);
                    }
                    catch (Exception k)
                    { 
                        k.printStackTrace();
                    }
                }
            }
        });
        
        b2 = new JButton("Clear");
        b2.setBackground(Color.WHITE);
        b2.setBounds(390,510,100,30); 
        b2.setForeground(Color.BLACK);
        imageLabel.add(b2);
        b2.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                clr();
            }
        });
        
        b3 = new JButton("View");
        b3.setBackground(Color.WHITE);
        b3.setBounds(550,510,100,30); 
        b3.setForeground(Color.BLACK);
        imageLabel.add(b3);
        b3.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                try 
                {
                    new View();
                    f.dispose();
                    instance = null;
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(EmployeeMangement.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        
        b4 = new JButton("Update");
        b4.setBackground(Color.WHITE);
        b4.setBounds(230,510,100,30); 
        b4.setForeground(Color.BLACK);
        imageLabel.add(b4);
        b4.setEnabled(false);
        b4.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            {
                if (isvalid())
                { 
                    String eid,ename,dob,gender = null,address,phoneno,emailid,position,experience,qualification;
                ConnectionClass obj = new ConnectionClass();
                PreparedStatement pstmt = null;
                PreparedStatement pst = null;
                PreparedStatement ps = null;
                    try 
                    {
                        pstmt = obj.con.prepareStatement("Update empmang set ename = ?,dob = ?,gender = ?,address = ?,phoneno = ?,emailid = ?,position = ?, experience = ?, qualification = ? where eid = ?");
                        pst = obj.con.prepareStatement("Update attend set ename = ? where eid = ?");
                        ps = obj.con.prepareStatement("Update leavemang set ename = ? where eid = ?");
                        eid =t1.getText();
                        ename = t2.getText();
                        dob = t3.getText();
                        if (r1.isSelected())
                            gender = "Male";
                        else if (r2.isSelected())
                            gender ="Female";
                        address = t4.getText();
                        phoneno = t5.getText();
                        emailid = t6.getText();
                        position = t7.getText();
                        experience = t8.getText();
                        qualification = t9.getText();
                        pstmt.setString(1,ename);
                        pstmt.setString(2,dob); 
                        pstmt.setString(3,gender);
                        pstmt.setString(4,address); 
                        pstmt.setString (5,phoneno);
                        pstmt.setString(6,emailid);  
                        pstmt.setString(7,position);
                        pstmt.setString(8,experience);
                        pstmt.setString(9,qualification);
                        pstmt.setString(10,eid);
                        pst.setString(1,ename); 
                        pst.setString(2,eid);
                        ps.setString(1,ename);
                        ps.setString(2, eid);
                        int rowsAffected = pstmt.executeUpdate();
                        pst.executeUpdate();
                        ps.executeUpdate();
                        if (rowsAffected > 0) 
                        {
                            JOptionPane.showMessageDialog(imageLabel, "Updated Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                            clr();
                            t1.setEnabled(true);
                            cb1.setEnabled(true);
                            b1.setEnabled(true);
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(imageLabel, "Not Updated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        pstmt.close();
                        obj.con.close();
                    } 
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(EmployeeMangement.class.getName())
                            .log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        ImageIcon back = new ImageIcon (getClass ().getResource ("/hrm/Pictures/back.png"));
        Image scaledIm = back.getImage ().getScaledInstance (70, 50,Image.SCALE_DEFAULT);
        back = new ImageIcon (scaledIm);
        b5 = new JButton(back);
        b5.setBounds (20,0,70,50);
        b5.setForeground(Color.WHITE);
        f.add (b5);
        b5.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                f.dispose();
                instance = null;
            }
        });
        
        l11 = new JLabel("Experience"); 
        l11.setBounds(430,230,150,100); 
        l11.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l11.setForeground(Color.BLACK);;
        imageLabel.add(l11);
        
        t8 = new JTextField(); 
        t8.setBounds(530,270,150,30); 
        t8.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t8);
        t8.addKeyListener(new KeyAdapter()
        { 
            public void keyTyped (KeyEvent e)
            { 
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) 
                    e.consume();
            }
        });
        
        l12 = new JLabel("Qualification"); 
        l12.setBounds(430,310,150,100); 
        l12.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l12.setForeground(Color.BLACK);;
        imageLabel.add(l12);
        
        t9 = new JTextField(); 
        t9.setBounds(530,350,150,30); 
        t9.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t9);
        t9.addKeyListener(new KeyAdapter()
        { 
            public void keyTyped(KeyEvent e)
            { 
                char c = e.getKeyChar();
                if (!Character.isLetter(c)&& !Character.isWhitespace(c)) 
                    e.consume();
            }
        });
        
        f.getContentPane(); 
        f.setVisible(true); 
        f.setSize(750,670);
        f.setResizable(false);
        f.setLocation(490,170);
        f.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                f.dispose();
                instance = null;
            }
        });
    }
    public boolean isvalid() 
    { 
        /*if (cb1.getSelectedIndex() == -1)
        { 
            JOptionPane.showMessageDialog(imageLabel,"Select the departmant","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        } */
        if (t1.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel, "Enter the Employee ID","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (t2.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the name","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t3.getText() =="dd/mm/yyyy")
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Date of Birth","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (!r1.isSelected() && !r2.isSelected())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Select the Gender","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t4.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Address","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t5.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Phone No.","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t6.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Email ID","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t7.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Position","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t8.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Experience","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (t9.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Qualification","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else
            return true;
    }
    public void clr()
    { 
        cb1.setSelectedIndex(-1);
        t1.setText(null); 
        t2.setText(null);
        t3.setText("dd/mm/yyyy"); 
        bg.clearSelection();
        t4.setText(null); 
        t5.setText(null);
        t6.setText(null);
        t7.setText(null);
        t8.setText(null);
        t9.setText(null);
        t1.setEnabled(true);
        cb1.setEnabled(true);
        b1.setEnabled(true);
        b4.setEnabled(false);
    }
    public static EmployeeMangement getInstance() 
    {
        if (instance == null)
            instance = new EmployeeMangement();
        return instance;
    }
    public static void main(String[] args)
    { 
        new EmployeeMangement();
    }
}