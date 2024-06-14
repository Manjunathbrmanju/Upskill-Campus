package hrm;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.sql.*; 
public class LoginPage extends JFrame
{
    JFrame f; 
    JLabel l1,l2,l3; 
    JTextField t1; 
    JPasswordField t2; 
    JButton b1,b2;
    JPanel p1;
    LoginPage() 
    { 
        f = new JFrame("Login");
        f.setLayout(null); 
        
        p1 = new JPanel(); 
        p1.setLayout(null);
        p1.setBackground(Color.RED);
        p1.setBounds(0,0,900,106);
        f.add(p1);
        
        l3 = new JLabel("HR Mangement System");
        l3.setBounds(190,0,900,100); 
        l3.setFont(new Font("Segoe UI",Font.BOLD,48));
        l3.setForeground(Color.WHITE);
        p1.add(l3);
        
        l1 = new JLabel("UserName");
        l1.setBounds(250,200,200,30);
        l1.setFont(new Font("Segoe UI",Font.BOLD,24));
        f.add(l1); 
        
        l2 = new JLabel("Password");
        l2.setBounds(250,280,200,30);
        l2.setFont(new Font("Segoe UI",Font.BOLD,24));
        f.add(l2);
        
        t1 = new JTextField(); 
        t1.setBounds(450,200,200,30);
        t1.setFont(new Font("Segoe UI",Font.PLAIN,18));
        f.add(t1); 
        
        t2 = new JPasswordField(); 
        t2.setBounds(450,280,200,30);
        t2.setFont(new Font("Segoe UI",Font.PLAIN,18));
        f.add(t2); 
        
        b1 = new JButton("Login");
        b1.setBackground(Color.BLACK);
        b1.setBounds(250,360,120,50); 
        b1.setForeground(Color.WHITE);
        f.add(b1); 
        b1.addActionListener(new ActionListener()
        { 
           public void actionPerformed(ActionEvent e)
           {
               try
               {
                   ConnectionClass obj = new ConnectionClass();
                   String name =t1.getText();
                   String pass = t2.getText();
                   String query ="Select *from login where username ='"+name+"' and password ='"+pass+"'";
                   ResultSet rs = obj.stm.executeQuery(query);
                   if (rs.next())
                   {
                       JOptionPane.showMessageDialog(f,"Sucessfull Login","Success",JOptionPane.INFORMATION_MESSAGE);
                       new Dashboard();
                       f.dispose();
                   }
                   else
                   {
                       JOptionPane.showMessageDialog(f,"Invalid Login","Error",JOptionPane.ERROR_MESSAGE);
                       f.setVisible(false);
                       f.setVisible(true);
                       t1.setText(null);
                       t2.setText(null);
                   }
               }
               catch (Exception k)
               {
                   k.printStackTrace();
               }
           }
        });
        
        b2 = new JButton("Clear");
        b2.setBackground(Color.BLACK);
        b2.setBounds(450,360,120,50); 
        b2.setForeground(Color.WHITE);
        f.add(b2);
        b2.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                t1.setText(null); 
                t2.setText(null);
            }
        });
        
        ImageIcon image = new ImageIcon (getClass ().getResource ("/hrm/Pictures/lock.gif"));
        Image scaledImage = image.getImage ().getScaledInstance (900, 700,Image.SCALE_DEFAULT);
        image = new ImageIcon (scaledImage);
        JLabel imageLabel = new JLabel (image);
        imageLabel.setBounds (0,110,900,500); 
        f.add (imageLabel);
        
        t1.setText("Coder");
        t2.setText("123");
        f.getContentPane(); 
        f.setVisible(true); 
        f.setSize(900,500);
        f.setLocationRelativeTo(null);
        f.setResizable(false); 
        f.addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent e) 
            {
                f.dispose(); 
            }
        });
    }
    public static void main(String[] args)
    { 
        new LoginPage();
    }
}