package hrm;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Leave extends JFrame
{
    static JFrame f;
    JPanel p1; 
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,imageLabel;
    JButton b1,b2,b3,b4,b5;
    JComboBox cb1; 
    String a,b;
    String[] options = {"Sales","Accounts","Marketing","IT"};
    JTextField t1,t2,t3,t4,t5,t6;
     public static Leave instance = null;
    Leave()
    { 
        f = new JFrame("Leave");
        f.setLayout(null);
        
        p1 = new JPanel(); 
        p1.setLayout(null);
        p1.setBackground(Color.WHITE);
        p1.setBounds(0,0,950,50);
        f.add(p1);
        
        l1 = new JLabel("Leave");
        l1.setBounds(350,0,300,50); 
        l1.setFont(new Font("Segoe UI",Font.BOLD,24));
        l1.setForeground(Color.RED);
        p1.add(l1);
        
        ImageIcon image = new ImageIcon (getClass ().getResource ("/hrm/Pictures/lv.jpeg"));
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
                            Logger.getLogger(Leave.class.getName())
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
        t2.setEnabled(false);
        
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
                        String eid,ename,dept,fromdate,todate,noofdays,reason;
                        ConnectionClass obj = new ConnectionClass();
                        eid =t1.getText(); 
                        ename = t2.getText(); 
                        dept = (String)cb1.getSelectedItem();
                        fromdate = t3.getText(); 
                        todate = t4.getText(); 
                        noofdays = t5.getText();
                        reason = t6.getText();
                        String query ="Insert into leavemang (eid, ename, dept,fromdate,todate,noofdays,reason) values (?, ?, ?,?,?,?,?)";
                        PreparedStatement stmt = obj.con.prepareStatement(query);
                        stmt.setString(1, eid);
                        stmt.setString(2, ename);
                        stmt.setString(3, dept);
                        stmt.setString(4,fromdate);
                        stmt.setString(5,todate); 
                        stmt.setString(6,noofdays);
                        stmt.setString(7,reason);
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
                    String eid,fromdate,todate,noofdays,reason;
                    ConnectionClass obj = new ConnectionClass();
                    PreparedStatement pstmt = null;
                    try 
                    {
                        pstmt = obj.con.prepareStatement("Update leavemang set fromdate = ?, todate = ?, noofdays = ?, reason =? where eid = ?");
                        eid = t1.getText();
                        fromdate = t3.getText();
                        todate = t4.getText();
                        noofdays = t5.getText();
                        reason = t6.getText();
                        pstmt.setString(1,fromdate);
                        pstmt.setString(2,todate);
                        pstmt.setString(3,noofdays);
                        pstmt.setString(4,reason);
                        pstmt.setString(5,eid);
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
                        Logger.getLogger(Leave.class.getName())
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
                    new View3();
                    f.dispose();
                    instance = null;
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(Leave.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        
        l5 = new JLabel("From Date"); 
        l5.setBounds(430,0,150,100);
        l5.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l5.setForeground(Color.BLACK);;
        imageLabel.add(l5);
        
        t3 = new JTextField("dd/mm/yyyy"); 
        t3.setBounds(530,40,150,30);
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
                    if (year >= 2023)
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
                if (t3.getText().length() == 10)
                    e.consume();
            }
        });
        
        l6 = new JLabel("To Date"); 
        l6.setBounds(430,80,150,100); 
        l6.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l6.setForeground(Color.BLACK);;
        imageLabel.add(l6);
        
        t4 = new JTextField("dd/mm/yyyy"); 
        t4.setBounds(530,120,150,30);
        t4.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t4);
        t4.addFocusListener(new FocusAdapter()
        { 
            public void focusGained(FocusEvent e)
            {
                t4.setText(null);
            }
            public void focusLost(FocusEvent e)
            { 
                if (t4.getText().length() == 10)
                { 
                    int year,month,date,leapyear,newyear,newmonth,newdate;
                    year =Integer.parseInt(t4.getText().substring(6,10));
                    month =Integer.parseInt(t4.getText().substring(3,5));
                    date = Integer.parseInt(t4.getText().substring(0,2));
                    if (year >= 2023)
                    { 
                        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                        { 
                            if (date > 31)
                            { 
                                JOptionPane.showMessageDialog(imageLabel,"Invalid Date","Error",JOptionPane.ERROR_MESSAGE);
                                t4.setText("dd/mm/yyyy");
                            }
                        }
                        else if (month == 4 || month == 6 || month == 9 || month == 11)
                        { 
                            if (date > 30)
                            { 
                                JOptionPane.showMessageDialog(imageLabel,"Invalid Date","Error",JOptionPane.ERROR_MESSAGE); 
                                t4.setText("dd/mm/yyyy");
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
                                    t4.setText("dd/mm/yyyy");
                                }
                            }
                            else 
                            { 
                                if (date > 28)
                                { 
                                    JOptionPane.showMessageDialog(imageLabel,"Invalid Date","Error",JOptionPane.ERROR_MESSAGE); 
                                    t4.setText("dd/mm/yyyy");
                                }
                            }
                        }
                        else 
                        { 
                            JOptionPane.showMessageDialog(imageLabel,"Invalid Month","Error",JOptionPane.ERROR_MESSAGE); 
                            t4.setText("dd/mm/yyyy");
                        }
                        newyear = Integer.parseInt(t3.getText().substring(6,10));
                        newmonth = Integer.parseInt(t3.getText().substring(3,5));
                        newdate = Integer.parseInt(t3.getText().substring(0,2));
                        if (newyear > year)
                        { 
                            JOptionPane.showMessageDialog(imageLabel,"The To Date Year is Smaller than the From Date Year","Error",JOptionPane.ERROR_MESSAGE);
                            t4.setText("dd/mm/yyyy");
                        }
                        else if (newmonth < month && newyear == year)
                        { 
                            JOptionPane.showMessageDialog(imageLabel,"The To Date Month is Smaller than the From Date Month","Error",JOptionPane.ERROR_MESSAGE);
                            t4.setText("dd/mm/yyyy");
                        }
                        else if (newdate > date && newmonth == month)
                        {
                            JOptionPane.showMessageDialog(imageLabel,"The To Date Date is Smaller than the From Date Date","Error",JOptionPane.ERROR_MESSAGE); 
                            t4.setText("dd/mm/yyyy");
                        }
                        if (t4.getText() != "dd/mm/yyyy")
                        { 
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            String date1 = t3.getText();
                            String date2 = t4.getText();
                            LocalDate localDate1 = LocalDate.parse(date1, formatter);
                            LocalDate localDate2 = LocalDate.parse(date2, formatter);
                            long days = ChronoUnit.DAYS.between(localDate1, localDate2);
                            t5.setText(String.valueOf(days));
                        }
                    }
                    else 
                    { 
                        JOptionPane.showMessageDialog(imageLabel,"Invalid year","Error",JOptionPane.ERROR_MESSAGE); 
                        t4.setText("dd/mm/yyyy");
                    }
                }
                else 
                { 
                    JOptionPane.showMessageDialog(imageLabel, "Use the correct format","Error",JOptionPane.ERROR_MESSAGE);
                    t4.setText("dd/mm/yyyy");
                }
            }
        });
        t4.addKeyListener(new KeyAdapter()
        { 
            public void keyTyped(KeyEvent e)
            { 
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '/') 
                    e.consume();
            }
        });
        
        l7 = new JLabel("No. of Days"); 
        l7.setBounds(430,150,150,100);
        l7.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l7.setForeground(Color.BLACK);;
        imageLabel.add(l7);
        
        t5 = new JTextField(); 
        t5.setBounds(530,190,150,30);
        t5.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t5);
        t5.setEnabled(false);

        l8 = new JLabel("Reason"); 
        l8.setBounds(230,230,150,100);
        l8.setFont(new Font("Segoe UI",Font.BOLD,16)); 
        l8.setForeground(Color.BLACK);;
        imageLabel.add(l8);
        
        t6 = new JTextField(); 
        t6.setBounds(370,270,150,30);
        t6.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t6);
        t6.addKeyListener(new KeyAdapter()
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
            JOptionPane.showMessageDialog(imageLabel,"Select the Department","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }*/
        if (t1.getText().isEmpty())
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the Employee ID","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t3.getText() == "dd/mm/yyyy")
        { 
            JOptionPane.showMessageDialog(imageLabel, "Enter the From Date","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (t4.getText() =="dd/mm/yyyy")
        { 
            JOptionPane.showMessageDialog(imageLabel,"Enter the To Date","Error",JOptionPane.ERROR_MESSAGE); 
            return false;
        }
        else if (t6.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(imageLabel,"Enter the Reason","Error",JOptionPane.ERROR_MESSAGE); 
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
        t3.setText(null);
        t4.setText(null);
        t5.setText(null);
        t6.setText(null);
        t1.setEnabled(true);
        cb1.setEnabled(true);
        b2.setEnabled(true);
        b4.setEnabled(false);
    }
    public static Leave getInstance() 
    {
        if (instance == null)
            instance = new Leave();
        return instance;
    }
    public static void main(String[] args)
    { 
        new Leave();
    }
}