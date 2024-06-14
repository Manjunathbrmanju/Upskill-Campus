package hrm;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
public class Dashboard extends JFrame 
{
    JFrame ff;
    JButton b1,b2,b3,b4; 
    JLabel l1;
    JPanel p1,p2,p3;
    Dashboard()
    { 
        ff = new JFrame("Dashboard");
        ff.setLayout(null); 
        
        b1 = new JButton("Employee Mangement"); 
        b1.setBounds(10,120,180,50);
        b1.setFont(new Font("Arail",Font.BOLD,12));
        ff.add(b1); 
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            { 
                for (Frame f: getFrames())
                    f.dispose();
                new Dashboard();
                EmployeeMangement.instance = null;
                EmployeeMangement.getInstance();
            }
        });
        
        b2 = new JButton("Attendance"); 
        b2.setBounds(10,220,180,50);
        b2.setFont(new Font("Arail",Font.BOLD,12));
        ff.add(b2); 
        b2.addActionListener(new ActionListener()
        { 
            public void actionPerformed(ActionEvent e)
            { 
                for (Frame f: getFrames())
                    f.dispose();
                new Dashboard();
                Attendance.instance = null;
                Attendance.getInstance();
            }
        });
        
        b3 = new JButton("Leave"); 
        b3.setBounds(10,320,180,50);
        b3.setFont(new Font("Arail",Font.BOLD,12));
        ff.add(b3);
        b3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            { 
                for (Frame f: getFrames())
                    f.dispose();
                new Dashboard();
                Leave.instance = null;
                Leave.getInstance();
            }
        });

        b4 = new JButton("Logout"); 
        b4.setBounds(10,420,180,50);
        b4.setFont(new Font("Arail",Font.BOLD,12));
        ff.add(b4);
        b4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            { 
               for (Frame f: getFrames())
                    f.dispose();
               new LoginPage();
            }
        });
        
        p1 = new JPanel(); 
        p1.setLayout(null);
        p1.setBackground(Color.RED);
        p1.setBounds(0,0,950,106);
        ff.add(p1);
        
        l1 = new JLabel("HR Mangement System");
        l1.setBounds(220,0,950,100); 
        l1.setFont(new Font("Segoe UI",Font.BOLD,48));
        l1.setForeground(Color.WHITE);
        p1.add(l1);
        
        p2 = new JPanel(); 
        p2.setLayout(null);
        p2.setBackground(Color.ORANGE);
        p2.setBounds(0,0,200,800);
        ff.add(p2);
        
        p3 = new JPanel(); 
        p3.setLayout(null); 
        p3.setBackground(Color.WHITE); 
        p3.setBounds(200,106,750,695);
        ff.add(p3);
        
        ImageIcon image = new ImageIcon (getClass ().getResource ("/hrm/Pictures/main.png"));
        Image scaledImage = image.getImage ().getScaledInstance (700, 700,Image.SCALE_DEFAULT);
        image = new ImageIcon (scaledImage);
        JLabel imageLabel = new JLabel (image);
        imageLabel.setBounds (3,0,730,695); 
        p3.add (imageLabel);

        ff.getContentPane(); 
        ff.setVisible(true); 
        ff.setSize(950,800);
        ff.setResizable(false);
        ff.setLocationRelativeTo(null);
        ff.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args)
    { 
        new Dashboard();
    }
}