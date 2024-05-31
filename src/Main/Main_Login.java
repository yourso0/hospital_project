package Main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Reservation.Res_Main;
import Reservation.Res_Select;
import User.Delete_User;
import User.Modify_User;
import User.SignUp_User;

public class Main_Login extends JDialog {
    private JTextField tfID;
    private JPasswordField passwordPW;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main_Login dialog = new Main_Login();
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    dialog.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the dialog.
     */
    public Main_Login() {
        setResizable(false);
        setTitle("연구중심병원");
        setBounds(100, 100, 701, 536);
        getContentPane().setLayout(null);
        
        // 메인 이미지 레이블
        JLabel lblMainImg = new JLabel();
        lblMainImg.setIcon(new ImageIcon(Main_Login.class.getResource("/img/mainimg1.jpg")));
        lblMainImg.setFont(new Font("굴림", Font.BOLD, 21));
        lblMainImg.setHorizontalAlignment(SwingConstants.CENTER);
        lblMainImg.setBackground(new Color(255, 255, 255));
        lblMainImg.setOpaque(true);
        lblMainImg.setBounds(0, 0, 687, 414);
        getContentPane().add(lblMainImg);
        
        // 로그인 버튼
        JButton btnRes = new JButton("로그인");
        btnRes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        btnRes.setBounds(584, 424, 91, 56);
        getContentPane().add(btnRes);
        
        // 회원가입 버튼
        JButton btnSignUp = new JButton("회원가입");
        btnSignUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUp_User signUp = new SignUp_User();
                signUp.setModal(true);
                signUp.setVisible(true);
            }
        });
        btnSignUp.setBounds(10, 424, 91, 23);
        getContentPane().add(btnSignUp);
        
        // 아이디/비밀번호 찾기 버튼
        JButton btnFind = new JButton("아이디/비밀번호 찾기");
        btnFind.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findIDPW();
            }
        });
        btnFind.setBounds(10, 457, 194, 26);
        getContentPane().add(btnFind);
        
        // 회원탈퇴 버튼
        JButton btnDelete = new JButton("회원탈퇴");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Delete_User delete = new Delete_User();
                delete.setModal(true);
                delete.setVisible(true);
            }
        });
        btnDelete.setBounds(113, 424, 91, 23);
        getContentPane().add(btnDelete);
        
        // 예약확인 버튼
        JButton btnInsert = new JButton("예약확인");
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Res_Select reservationSelect = new Res_Select();
                reservationSelect.setModal(true);
                reservationSelect.setVisible(true);
            }
        });
        btnInsert.setBounds(216, 457, 109, 26);
        getContentPane().add(btnInsert);
        
        // 회원정보 수정 버튼
        JButton btnModify = new JButton("회원정보 수정");
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Modify_User modify = new Modify_User();
                modify.setModal(true);
                modify.setVisible(true);
            }
        });
        btnModify.setBounds(214, 424, 111, 23);
        getContentPane().add(btnModify);
        
        // 아이디 입력 필드
        tfID = new JTextField();
        tfID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordPW.requestFocus();
                }
            }
        });
        tfID.setColumns(10);
        tfID.setBounds(452, 431, 120, 21);
        getContentPane().add(tfID);
        
        // 비밀번호 입력 필드
        passwordPW = new JPasswordField();
        passwordPW.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
        passwordPW.setBounds(452, 459, 120, 21);
        getContentPane().add(passwordPW);
        
        // 비밀번호 레이블
        JLabel lblPW = new JLabel("비밀번호:");
        lblPW.setBounds(379, 462, 61, 15);
        getContentPane().add(lblPW);
        
        // 아이디 레이블
        JLabel lblID = new JLabel("아이디:");
        lblID.setBounds(390, 434, 50, 15);
        getContentPane().add(lblID);
    }

    // 아이디, 비밀번호 찾기
    protected void findIDPW() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root", "990714");
            String name = JOptionPane.showInputDialog(null, "이름을 입력하세요.");
            String mobile = JOptionPane.showInputDialog(null, "전화번호를 입력하세요.");
            String sql = "SELECT * FROM logintbl WHERE name = ? AND mobile = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, mobile);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "아이디: " + rs.getString("id") + "\n비밀번호: " + rs.getString("pw"), "아이디/비밀번호 찾기 성공", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "입력하신 정보와 일치하는 계정이 없습니다.", "아이디/비밀번호 찾기 실패", JOptionPane.ERROR_MESSAGE);
            }
            pstmt.close();
            con.close();
        } catch (ClassNotFoundException e1) {
            System.out.println("JDBC 드라이버 로드 에러");
        } catch (SQLException e1) {
            System.out.println("DB 연결 오류");
        }
    }

    // 로그인
    protected void login() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB", "root", "990714");
           
            if (tfID.getText().equals("") || String.valueOf(passwordPW.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 모두 입력해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "SELECT * FROM logintbl WHERE id = ? AND pw = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tfID.getText());
            pstmt.setString(2, String.valueOf(passwordPW.getPassword()));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "로그인에 성공하셨습니다.");
                Res_Main reservation = new Res_Main(tfID.getText());
                reservation.setModal(true);
                reservation.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호를 확인해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                tfID.requestFocus();
            }
            pstmt.close();
            con.close();
        } catch (ClassNotFoundException e1) {
            System.out.println("JDBC 드라이버 로드 에러");
        } catch (SQLException e1) {
            System.out.println("DB 연결 오류");
        }
    }
}
