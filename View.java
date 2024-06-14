package hrm;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.sql.*; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
public class View 
{
    JFrame f;
    JPanel p1; 
    JLabel l1,l2,imageLabel;
    JTable j1;
    JButton b1,b2,b3,b4;
    JTextField t1;
    JScrollPane scrollPane;
    String a,b;
    View() throws SQLException 
    {
        f = new JFrame("Employee Magement View");
        f.setLayout(null);
        
        p1 = new JPanel(); 
        p1.setLayout(null);
        p1.setBackground(Color.WHITE);
        p1.setBounds(0,0,950,50);
        f.add(p1);
        
        l1 = new JLabel("Employee Mangement View");
        l1.setBounds(220,0,500,50); 
        l1.setFont(new Font("Segoe UI",Font.BOLD,24));
        l1.setForeground(Color.RED);
        p1.add(l1);
        
        ImageIcon image = new ImageIcon (getClass ().getResource ("/hrm/Pictures/emp.png"));
        Image scaledImage = image.getImage ().getScaledInstance (700, 700,Image.SCALE_DEFAULT);
        image = new ImageIcon (scaledImage);
        JLabel imageLabel = new JLabel (image);
        imageLabel.setBounds (3,60,730,570); 
        f.add (imageLabel);
        
        ConnectionClass obj = new ConnectionClass();
        Statement stmt = obj.con.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from empmang");
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++)
            columnNames[i] = rsmd.getColumnName(i + 1);
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        while (rs.next())
        {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++)
                row[i] = rs.getObject(i + 1);
            model.addRow(row);
        }
        j1 = new JTable(model);
        j1.setFont(new Font("Segoe UI",Font.PLAIN,14));
        j1.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(j1);
        scrollPane.setBounds(50,120,630,380);
        imageLabel.add(scrollPane);
        j1.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting())
                {
                    int row = j1.getSelectedRow();
                    int column = j1.getSelectedColumn();
                    Object value = j1.getModel().getValueAt(row, 0);
                    if (value instanceof String)
                        a = (String) value;
                    else
                        a = value.toString();
                    t1.setText(a);
                    t1.setForeground(Color.WHITE);
                    b = t1.getText();
                }
            }
        });
        
        l2 = new JLabel("Enter the ID: ");
        l2.setBounds(180,30,150,50); 
        l2.setFont(new Font("Segoe UI",Font.BOLD,16));
        l2.setForeground(Color.BLACK);
        imageLabel.add(l2);
        
        t1 = new JTextField(3); 
        t1.setBounds(300,40,150,30); 
        t1.setFont(new Font("Segeo UI",Font.PLAIN,16));
        imageLabel.add(t1);
        t1.addFocusListener(new FocusAdapter()
        { 
            public void focusGained(FocusEvent e)
            { 
                t1.setText(null);
            }
            public void focusLost(FocusEvent e)
            {
                if (t1.getText().isEmpty())
                {
                    try 
                    {
                        f.dispose();
                        new View();
                    } 
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(View.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
                else if (Integer.parseInt(t1.getText().substring(0,1)) != 1 && Integer.parseInt(t1.getText().substring(0,1)) != 2 && Integer.parseInt(t1.getText().substring(0,1)) != 3 && Integer.parseInt(t1.getText().substring(0,1)) != 4)
                {
                        JOptionPane.showMessageDialog(imageLabel,"Invalid ID","Error",JOptionPane.ERROR_MESSAGE);
                    t1.setText(null);
                }
            }
        });
        t1.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                char c = e.getKeyChar();
                if (!Character.isDigit(c))
                    e.consume();
            }
        });
        
        b1 = new JButton("Search");
        b1.setBackground(Color.WHITE);
        b1.setBounds(480,40,100,30); 
        b1.setForeground(Color.BLACK);
        imageLabel.add(b1);
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
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
                       DefaultTableModel table = (DefaultTableModel)j1.getModel();
                       String search = t1.getText();
                       TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
                       j1.setRowSorter(tr);
                       tr.setRowFilter(RowFilter.regexFilter(search));
                    }
                    else 
                    { 
                        JOptionPane.showMessageDialog(imageLabel,"ID Not Found","Error",JOptionPane.ERROR_MESSAGE);
                        t1.setText(null);
                    }
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(View.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        });
        
        b2 = new JButton("Update");
        b2.setBackground(Color.WHITE);
        b2.setBounds(200,520,100,30); 
        b2.setForeground(Color.BLACK);
        imageLabel.add(b2);
        b2.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            {
                if (j1.getSelectedRow () == -1 || j1.getSelectionModel ().isSelectionEmpty ()) 
                { 
                    JOptionPane.showMessageDialog(imageLabel,"Selecct the row that you want to Update","Error",JOptionPane.ERROR_MESSAGE);
                }
                else 
                {
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    String query = "Select *from empmang where eid = ?";
                    try
                    {
                        ConnectionClass obj = new ConnectionClass();
                        pstmt = obj.con.prepareStatement(query);
                        pstmt.setString(1, b);
                        rs = pstmt.executeQuery();
                        while (rs.next())
                        {
                            String eid = rs.getString("eid");
                            String ename = rs.getString("ename");
                            String dept = rs.getString("dept");
                            String dob = rs.getString("dob");
                            String gender = rs.getString("gender");
                            String address = rs.getString("address");
                            String phoneno = rs.getString("phoneno");
                            String emailid = rs.getString("emailid");
                            String position = rs.getString("position");
                            String experience = rs.getString("experience");
                            String qualification = rs.getString("qualification");
                            f.dispose();
                            EmployeeMangement emp = new EmployeeMangement();
                            emp.t1.setText(eid);
                            emp.t1.setEnabled(false);
                            emp.t2.setText(ename);
                            //emp.cb1.setSelectedItem(dept);
                            emp.cb1.setEnabled(false);
                            emp.t3.setText(dob);
                            if (gender.equals("Male")) 
                                emp.r1.setSelected(true);
                            else 
                            emp.r2.setSelected(true);
                            emp.t4.setText(address);
                            emp.t5.setText(phoneno);
                            emp.t6.setText(emailid);
                            emp.t7.setText(position);
                            emp.t8.setText(experience);
                            emp.t9.setText(qualification);
                            emp.b1.setEnabled(false);
                            emp.b4.setEnabled(true);
                        }
                    }
                    catch (SQLException l)
                    {
                        l.printStackTrace();
                    }
                    finally
                    {
                        if (rs != null)
                        {
                            try
                            {
                                rs.close();
                            }
                            catch (SQLException l)
                            {
                                l.printStackTrace();
                            }
                        }
                        if (pstmt != null)
                        {
                            try
                            {
                                pstmt.close();
                            }
                            catch (SQLException l)
                            {
                                l.printStackTrace();
                            }
                        }
                    }   
                }
            }
        });
        
        b3 = new JButton("Delete");
        b3.setBackground(Color.WHITE);
        b3.setBounds(480,520,100,30); 
        b3.setForeground(Color.BLACK);
        imageLabel.add(b3);
        b3.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            {
                if (j1.getSelectedRow () == -1 || j1.getSelectionModel ().isSelectionEmpty ()) 
                { 
                   JOptionPane.showMessageDialog(imageLabel,"Select the Row that you want to Delete","Error",JOptionPane.ERROR_MESSAGE); 
                }
                else 
                {
                    String question = "Are you sure you want to delete?";
                    int choice = JOptionPane.showConfirmDialog(imageLabel, question, "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION)
                    {
                        ConnectionClass obj = new ConnectionClass();
                        PreparedStatement pstmt = null;
                        PreparedStatement pst = null;
                        PreparedStatement ps = null;
                        try
                        {
                            pstmt = obj.con.prepareStatement("Delete from empmang where eid = ?");
                            pst = obj.con.prepareStatement("Delete from attend where eid = ?");
                            ps = obj.con.prepareStatement("Delete from leavemang where eid = ?");
                            ps.setString(1,b);
                            pstmt.setString(1, a);
                            pst.setString(1,b);
                            int rowsAffected = pstmt.executeUpdate();
                            pst.executeUpdate();
                            ps.executeUpdate();
                            System.out.print(rowsAffected);
                            if (rowsAffected > 0)
                            {
                                JOptionPane.showMessageDialog(imageLabel, "Deleted Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                                t1.setText(null);
                                f.dispose();
                                new View();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(imageLabel, "Not Deleted", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            pstmt.close();
                            pst.close();
                            ps.close();
                            obj.con.close();
                        }
                        catch (SQLException ex)
                        {
                            Logger.getLogger(View.class.getName())
                                .log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                        t1.setText(null);
                }
            }
        });
        
        ImageIcon back = new ImageIcon (getClass ().getResource ("/hrm/Pictures/back.png"));
        Image scaledIm = back.getImage ().getScaledInstance (70, 50,Image.SCALE_DEFAULT);
        back = new ImageIcon (scaledIm);
        b4 = new JButton(back);
        b4.setBounds (20,0,70,50);
        b4.setForeground(Color.WHITE);
        f.add (b4);
        b4.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                f.dispose();
                new EmployeeMangement();
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
            }
        });
    }
    public static void main(String[] args) throws SQLException 
    { 
        new View();
    }
}