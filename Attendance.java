package hrm;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.sql.Timestamp;
public class Attendance extends JFrame
{
    static JFrame f;
    JPanel p1; 
    JLabel l1,l2,l3,l4,l5,imageLabel;
    JButton b1,b2,b3,b4,b5;
    JComboBox cb1;
    JTextField t1,t2;
    String[] options = {"Sales","Accounts","Marketing","IT"};
    String a,b;
    JRadioButton r1,r2;
    ButtonGroup bg;
    public static Attendance instance = null;
    protected static Object dispose;
    Attendance() 
    { 
        f = new JFrame("Attendance");
        f.setLayout(null);
        
        p1 = new JPanel(); 
        p1.setLayout(null);
        p1.setBackground(Color.WHITE);
        p1.setBounds(0,0,950,50);
        f.add(p1);
        
        l1 = new JLabel("Attendance");
        l1.setBounds(320,0,300,50); 
        l1.setFont(new Font("Segoe UI",Font.BOLD,24));
        l1.setForeground(Color.RED);
        p1.add(l1);
        
        ImageIcon image = new ImageIcon (getClass ().getResource ("/hrm/Pictures/Atnd.png"));
        Image scaledImage = image.getImage ().getScaledInstance (700, 700,Image.SCALE_DEFAULT);
        image = new ImageIcon (scaledImage);
        JLabel imageLabel = new JLabel (image);
        imageLabel.setBounds (3,60,730,570); 
        f.add (imageLabel);
        
        ImageIcon back = new ImageIcon (getClass ().getResource ("/hrm/Pictures/back.png"));
        Image scaledIm = back.getImage ().getScaledInstance (70, 50,Image.SCALE_DEFAULT);
        back = new ImageIcon (scaledIm);
        b1 = new JButton(back);
        b1.setBounds (20,0,70,50);
        b1.setForeground(Color.WHITE);
        f.add (b1);
        b1.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                f.dispose();
                instance = null;
            }
        });
        
        l2 = new JLabel("Department"); 
        l2.setBounds(230,0,150,100); 
        l2.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l2.setForeground(Color.BLACK); 
        imageLabel.add(l2);
        
        cb1 = new JComboBox(options);
        cb1.setSelectedIndex(-1);
        cb1.setBounds(370,40,150,30); 
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
        l3.setBounds(230,80,150,100); 
        l3.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l3.setForeground(Color.BLACK);;
        imageLabel.add(l3);
        
        t1 = new JTextField(3); 
        t1.setBounds(370,120,150,30); 
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
                               String ename = rs.getString("ename");
                               t2.setText(ename);
                           }
                           else
                           {
                               JOptionPane.showMessageDialog(imageLabel,"ID Not Found","Error",JOptionPane.ERROR_MESSAGE);
                               String question = "Do you add an Employee?";
                               int choice = JOptionPane.showConfirmDialog(imageLabel, question, "Question", JOptionPane.YES_NO_OPTION);
                               if (choice == JOptionPane.YES_OPTION)
                               { 
                                   String z;
                                   z = t1.getText(); 
                                   f.dispose(); 
                                   EmployeeMangement emp = new EmployeeMangement();
                                   if (Integer.parseInt(z.substring(0,1)) == 1)
                                   {
                                       emp.cb1.setSelectedItem("Sales");
                                       emp.t1.setText(z);
                                   }
                                   else if(Integer.parseInt(z.substring(0,1)) == 2) 
                                   { 
                                       emp.cb1.setSelectedItem("Accounts");
                                       emp.t1.setText(z);
                                   }
                                   else if (Integer.parseInt(z.substring(0,1)) == 3)
                                   { 
                                        emp.cb1.setSelectedItem("Marketing");
                                        emp.t1.setText(z);
                                   }
                                   else if (Integer.parseInt(z.substring(0,1)) == 4)
                                   { 
                                       emp.cb1.setSelectedItem("IT"); 
                                       emp.t1.setText(z);
                                   }
                               }
                               else 
                               { 
                                   t1.setText(b);
                                   t2.setText(null);
                               }
                           }
                       }
                       catch (SQLException ex)
                       {
                           Logger.getLogger(Attendance.class.getName())
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
        l4.setBounds(230,150,150,100); 
        l4.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l4.setForeground(Color.BLACK);;
        imageLabel.add(l4);
        
        t2 = new JTextField(); 
        t2.setBounds(370,190,150,30); 
        t2.setFont(new Font("Segeo UI",Font.PLAIN,16)); 
        imageLabel.add(t2);
        t2.setEnabled(false);
        
        l5 = new JLabel("Attendance"); 
        l5.setBounds(230,230,150,100); 
        l5.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l5.setForeground(Color.BLACK);;
        imageLabel.add(l5);
        
        r1 = new JRadioButton("Present"); 
        r1.setBounds(370,270,70,40); 
        imageLabel.add(r1);
        r1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            { 
                if (r1.isSelected()) 
                    a ="Present";
            }
        });
        
        r2 = new JRadioButton("Absent"); 
        r2.setBounds(440,270,70,40); 
        imageLabel.add(r2);
        r2.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                if (r2.isSelected())
                    a ="Absent";
            }
        });
        
        bg = new ButtonGroup(); 
        bg.add(r1); 
        bg.add(r2);
        
        b2 = new JButton("Add");
        b2.setBackground(Color.WHITE);
        b2.setBounds(70,510,100,30); 
        b2.setForeground(Color.BLACK);
        imageLabel.add(b2);
        b2.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                if (isvalid())
                { 
                   try
                   { 
                        String eid,ename,dept,attendance;
                        ConnectionClass obj = new ConnectionClass();
                        eid =t1.getText(); 
                        ename = t2.getText(); 
                        dept = (String)cb1.getSelectedItem();
                        attendance = a;
                        java.util.Date utilDate = new java.util.Date();
                        Date sqlDate = new Date(utilDate.getTime());
                        Timestamp sqlTimestamp = new Timestamp(utilDate.getTime());
                        String query ="Insert into attend (eid, ename, dept, attendance,date,time) values (?, ?, ?, ?,?,?)";
                        PreparedStatement stmt = obj.con.prepareStatement(query);
                        stmt.setString(1, eid);
                        stmt.setString(2, ename);
                        stmt.setString(3, dept);
                        stmt.setString(4, attendance);
                        stmt.setDate(5, sqlDate);
                        stmt.setTimestamp(6, sqlTimestamp);
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
        
        b3 = new JButton("Clear");
        b3.setBackground(Color.WHITE);
        b3.setBounds(390,510,100,30); 
        b3.setForeground(Color.BLACK);
        imageLabel.add(b3);
        b3.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                clr();
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
                    String eid,attendance = null;
                    ConnectionClass obj = new ConnectionClass();
                    PreparedStatement pstmt = null;
                    try 
                    {
                        pstmt = obj.con.prepareStatement("Update attend set attendance = ? where eid = ?");
                        eid =t1.getText();
                        if (r1.isSelected())
                            attendance ="Present";
                        else if (r2.isSelected())
                            attendance ="Absent";
                        pstmt.setString(1,attendance);
                        pstmt.setString(2,eid);
                        int rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) 
                        {
                            JOptionPane.showMessageDialog(imageLabel, "Updated Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                            clr();
                            t1.setEnabled(true);
                            cb1.setEnabled(true);
                            b2.setEnabled(true);
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
                        Logger.getLogger(Attendance.class.getName())
                            .log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        b5 = new JButton("View");
        b5.setBackground(Color.WHITE);
        b5.setBounds(550,510,100,30); 
        b5.setForeground(Color.BLACK);
        imageLabel.add(b5);
        b5.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            {
                try 
                {
                    new View2();
                    f.dispose();
                    instance = null;
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(Attendance.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
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
    public static Attendance getInstance() 
    {
        if (instance == null)
            instance = new Attendance();
        return instance;
    }
    public boolean isvalid() 
    { 
        /*if (cb1.getSelectedIndex() == -1)
        { 
            JOptionPane.showMessageDialog(imageLabel,"Select the Departement","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        } */
        if (t1.getText().isEmpty()) 
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Employee ID","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (!r1.isSelected() && !r2.isSelected())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Select the Attendance","Error",JOptionPane.ERROR_MESSAGE);
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
        bg.clearSelection();
        t1.setEnabled(true);
        cb1.setEnabled(true);
        b2.setEnabled(true);
        b4.setEnabled(false);
    }
    public static void main(String[] args)
    { 
        new Attendance();
    }
}