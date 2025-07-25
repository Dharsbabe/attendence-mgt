package attt;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
public class EditAttendance {
    Connection con;
    DefaultTableModel model = new DefaultTableModel();

    public void editView() throws SQLException {

        connect();
        JFrame frame = new JFrame();
        Font text = new Font("Times New Roman", Font.PLAIN, 18);
        Font btn = new Font("Times New Roman", Font.BOLD, 20);

        //------------------------CLOSE---------------------------
        JLabel x = new JLabel("X");
        x.setForeground(Color.decode("#37474F"));
        x.setBounds(965, 10, 100, 20);
        x.setFont(new Font("Times New Roman", Font.BOLD, 20));
        frame.add(x);
        x.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        //----------------------------------------------------------

        //-----------------------BACK---------------------------------
        JLabel back = new JLabel("< BACK");
        back.setForeground(Color.decode("#37474F"));
        back.setFont(new Font("Times New Roman", Font.BOLD, 17));
        back.setBounds(18, 10, 100, 20);
        frame.add(back);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        //--------------------------------------------------------------

        //------------------Panel----------------------------------
        JPanel panel = new  JPanel();
        panel.setBounds(0, 0, 1000, 35);
        panel.setBackground(Color.decode("#DEE4E7"));
        frame.add(panel);
        //---------------------------------------------------------

        //----------------TABLE---------------------------------
        @SuppressWarnings("serial")
        JTable table=new JTable(){
            public boolean isCellEditable(int row,int column){
                if(column == 3)
                    return true;
                else
                    return false;
            }
        };
        model = (DefaultTableModel)table.getModel();
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("STATUS");
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        JScrollPane scPane=new JScrollPane(table);
        scPane.setBounds(500, 50, 480, 525);
        frame.add(scPane);
        //------------------------------------------------------

        //-------------------------DATE-------------------------
        JLabel dt = new JLabel("DATE : ");
        dt.setFont(text);
        dt.setBounds(25, 60, 75, 20);
        dt.setForeground(Color.decode("#DEE4E7"));
        frame.add(dt);
        JTextField dtbox= new JTextField();
        dtbox.setBounds(100, 60, 150, 25);
        dtbox.setBackground(Color.decode("#DEE4E7"));
        dtbox.setFont(text);
        dtbox.setForeground(Color.decode("#37474F"));
        String dateInString =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dtbox.setText(dateInString);
        frame.add(dtbox);
        //-------------------------------------------------------

        //--------------------CLASS---------------------------------
        JLabel classes = new JLabel("CLASS : ");
        classes.setFont(text);
        classes.setBounds(25, 150, 100, 20);
        classes.setForeground(Color.decode("#DEE4E7"));
        frame.add(classes);
        @SuppressWarnings("unchecked")
        JComboBox clss= new JComboBox(classEt());
        clss.setBounds(110, 150, 50, 25);
        frame.add(clss);
        //------------------------------------------------------------

        //----------------------VIEWBUTTON-----------------------
        JButton view = new JButton("VIEW");
        view.setBounds(175, 275, 150, 50);
        view.setFont(btn);
        view.setBackground(Color.decode("#DEE4E7"));
        view.setForeground(Color.decode("#37474F"));
        frame.add(view);
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tblupdt(String.valueOf(clss.getSelectedItem()), dtbox.getText());
            }
        });
        //-------------------------------------------------------

        //----------------------ABSENTBUTTON-----------------------
        JButton ab = new JButton("ABSENT");
        ab.setBounds(75, 365, 150, 50);
        ab.setFont(btn);
        ab.setBackground(Color.decode("#DEE4E7"));
        ab.setForeground(Color.decode("#37474F"));
        frame.add(ab);
        ab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setValueAt("Absent", table.getSelectedRow(), 2);
            }
        });
        //-------------------------------------------------------

        //----------------------PRESENTBUTTON-----------------------
        JButton pre = new JButton("PRESENT");
        pre.setBounds(275, 365, 150, 50);
        pre.setFont(btn);
        pre.setBackground(Color.decode("#DEE4E7"));
        pre.setForeground(Color.decode("#37474F"));
        frame.add(pre);
        pre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(table.getSelectedRow());
                table.setValueAt("Present", table.getSelectedRow(), 2);
            }
        });
        //-------------------------------------------------------

        //----------------------SUBMITBUTTON-----------------------
        JButton sbmt = new JButton("SUBMIT");
        sbmt.setBounds(75, 450, 150, 50);
        sbmt.setFont(btn);
        sbmt.setBackground(Color.decode("#DEE4E7"));
        sbmt.setForeground(Color.decode("#37474F"));
        frame.add(sbmt);
        sbmt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i= 0; i<table.getRowCount(); i++) {
                    try {
                        editItem(Integer.parseInt(String.valueOf(table.getValueAt(i, 0))), String.valueOf(table.getValueAt(i, 2)), dtbox.getText());
                    } catch (NumberFormatException | SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                for (int i=0; i < model.getRowCount(); i++) {
                    model.removeRow(i);
                    model.setRowCount(0);
                }
            }
        });
        //-------------------------------------------------------

        //----------------------DELETEBUTTON-----------------------
        JButton del = new JButton("DELETE");
        del.setBounds(275, 450, 150, 50);
        del.setFont(btn);
        del.setBackground(Color.decode("#DEE4E7"));
        del.setForeground(Color.decode("#37474F"));
        frame.add(del);
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adding = "DELETE FROM attend WHERE class = '"+String.valueOf(clss.getSelectedItem())+"' AND dt = '"+dtbox.getText()+"'";
                try {
                    Statement stm = con.createStatement();
                    stm.executeUpdate(adding);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                for (int i=0; i < model.getRowCount(); i++) {
                    model.removeRow(i);
                    model.setRowCount(0);
                }
            }
        });
        //-------------------------------------------------------

        //-------------------------------------------------------
        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.decode("#37474F"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //--------------------------------------------------------------
    }

    public void connect() throws SQLException {
        //ENTER PORT, USER, PASSWORD.
        String url = "jdbc:mysql://localhost:3306/sample";
        String user = "root";
        String pass = "Dhar@123";
        con = DriverManager.getConnection(url, user, pass);
    }

    public ResultSet dbSearch(String classes, String dt) throws SQLException {
        //ENTER PORT, USER, PASSWORD.
        String str1 = "SELECT * from attend, students where attend.stid=students.id AND attend.class = '"+classes+"' AND attend.dt = '"+dt+"'";
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery(str1);
        return rst;
    }

    public String[] classEt() throws SQLException {
        String str1 = "SELECT name from class";
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery(str1);
        String[] rt = new String[25];
        int i=0;
        while(rst.next()) {
            rt[i] = rst.getString("name");
            i++;
        }
        return rt;
    }

    public void tblupdt(String classes, String dt) {
        try {
            for (int i=0; i < model.getRowCount(); i++) {
                model.removeRow(i);
                model.setRowCount(0);
            }
            ResultSet res = dbSearch(classes, dt);
            for(int i=0; res.next(); i++) {
                model.addRow(new Object[0]);
                model.setValueAt(res.getInt("stid"), i, 0);
                model.setValueAt(res.getString("name"), i, 1);
                model.setValueAt(res.getString("status"), i, 2);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void editItem(int id, String status, String date) throws SQLException {
        String adding = "UPDATE attend SET status = '"+status+"' WHERE stid = "+id+" AND dt = '"+date+"'";
        Statement stm = con.createStatement();
        stm.executeUpdate(adding);
    }
}


