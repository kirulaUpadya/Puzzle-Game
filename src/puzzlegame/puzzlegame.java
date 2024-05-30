/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package puzzlegame;

import java.util.Random;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Kirula
 */
public final class puzzlegame extends javax.swing.JFrame {
    static Music play =new Music();
    String filepath;
    String filepath1;
    
    public int count=1000;
    /**
     * Creates new form puzzlegame
     */
    
    static int ms =0;
    static int s =0;
    static int m =0;
    static int h =0;
    public static boolean state = true;
    
    public puzzlegame() {
        
        filepath = "C:/Users/Kirula/Downloads/movement-200697.wav";
        play.playMusic(filepath);
        initComponents();
        ShuffleNUmber();
        jRadioButton2.setSelected(true);
        connect();
        
    }
    
    Connection con;
    PreparedStatement pst;
    
    public void connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/puzzlegame","root","20011229");
        } catch (ClassNotFoundException | SQLException ex) {
            java.util.logging.Logger.getLogger(puzzlegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public void timerstop(){
       state = false;
   }
   public void timerStart(){
       state = true;
       timer();
   }
    
    public void timer(){
    state = true;
    Thread t = new Thread(){
        @Override
        public void run(){
            long startTime = System.nanoTime();
            long prevTime = startTime;
            long elapsedTime = 0;
            while(state){
                long currentTime = System.nanoTime();
                elapsedTime += currentTime - prevTime;
                prevTime = currentTime;
                
                while (elapsedTime >= 1000000) { // Check if 1 millisecond has elapsed
                    ms++;
                    if (ms >= 1000) {
                        ms = 0;
                        s++;
                        if (s >= 60) {
                            s = 0;
                            m++;
                            if (m >= 60) {
                                m = 0;
                                h++;
                            }
                        }
                    }
                    elapsedTime -= 1000000;
                    if (ms == 0 && s == 0 && m == 30 && h == 0) {
                        timerstop();
                        looser g = new looser();
                        g.setVisible(true);
                        dispose();
                    }
                    updateUI(); // Update UI components
                }
            }
        }
    };
    t.start();
}
    
    public void losegame(){
             if (count == 0) {
                 looser Main = new looser();
                 Main.setVisible(true);
                 this.dispose();
             }
    }
    
    void puzzlewin(){
          
          if(
                    Button1.getText().equals(Integer.toString(1)) 
                  && Button2.getText().equals(Integer.toString(2)) 
                  && Button3.getText().equals(Integer.toString(3)) 
                  && Button4.getText().equals(Integer.toString(4)) 
                  && Button5.getText().equals(Integer.toString(5)) 
                  && Button6.getText().equals(Integer.toString(6)) 
                  && Button7.getText().equals(Integer.toString(7)) 
                  && Button8.getText().equals(Integer.toString(8)) 
                  && Button9.getText().equals(Integer.toString(9)) 
                  && Button10.getText().equals(Integer.toString(10)) 
                  && Button11.getText().equals(Integer.toString(11)) 
                  && Button12.getText().equals(Integer.toString(12)) 
                  && Button13.getText().equals(Integer.toString(13)) 
                  && Button14.getText().equals(Integer.toString(14)) 
                  && Button15.getText().equals(Integer.toString(15))
                  && Button16.getText().equals("")){
               timerstop();
               try {
                int clicks = 1000 - Integer.parseInt(jblNumOFClicks.getText());
                
                String pname = playerName.getText();
                String mOves = String.valueOf(clicks);
                String time =txth.getText();
                
                pst = con.prepareStatement("INSERT INTO playerDetails(Pname,Moves,Time) VALUES(?,?,?)");
                pst.setString(1, pname);
                pst.setString(2, mOves);
                pst.setString(3, time);
                
                int k = pst.executeUpdate();
                
                if(k==1){
                   JOptionPane.showMessageDialog(this,"Record Added");
                   playerName.setText("");
                   playerName.requestFocus();
                }
                else{
                   JOptionPane.showMessageDialog(this,"Insertion failed");
                }
                

            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(puzzlegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
               winner Main = new winner();
               Main.setVisible(true);
               this.dispose();
          }
    }
    
    public void updateUI() {
    // Update UI components with current time values (ms, s, m, h)
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            txth.setText(""+h+" : "+m+" : "+s+" : "+ms);
        }
    }
    );
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
//    int counter;
//    //==========================================================================
    void emptySpotChecker(JButton Butt1,JButton Butt2)
    {
        String shuffleNumber = Butt2.getText();
        
        if("".equals(shuffleNumber))
        {
            Butt2.setText(Butt1.getText());
            Butt1.setText("");
            count--;
            jblNumOFClicks.setText(String.valueOf(count));
        }
        
        if(count ==1000){
            timerStart();
        }
    }
//    //==========================================================================
        public  void ShuffleNUmber(){
        int[] Numbers = new int[15];
        int i = 0 ,rndNum=0;
        boolean flag = false;
        do{
            Random randNumber = new Random();
            rndNum = randNumber.nextInt(15)+1;
            
            for(int j=0;j<15;j++){
                if(Numbers[j] == rndNum){
                    flag = true;
                }
            }
            
            if(flag == true){
                flag = false;
            }else{
                Numbers[i] = rndNum;
                i++;
            }
        }while(i<15);
        
        Button1.setText(String.valueOf(Numbers[0]));
        Button2.setText(String.valueOf(Numbers[1]));
        Button3.setText(String.valueOf(Numbers[2]));
        Button4.setText(String.valueOf(Numbers[3]));
        Button5.setText(String.valueOf(Numbers[4]));
        Button6.setText(String.valueOf(Numbers[5]));
        Button7.setText(String.valueOf(Numbers[6]));
        Button8.setText(String.valueOf(Numbers[7]));
        Button9.setText(String.valueOf(Numbers[8]));
        Button10.setText(String.valueOf(Numbers[9]));
        Button11.setText(String.valueOf(Numbers[10]));
        Button12.setText(String.valueOf(Numbers[11]));
        Button13.setText(String.valueOf(Numbers[12]));
        Button14.setText(String.valueOf(Numbers[13]));
        Button15.setText(String.valueOf(Numbers[14]));
        Button16.setText("");
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        playerName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Button4 = new javax.swing.JButton();
        Button1 = new javax.swing.JButton();
        Button2 = new javax.swing.JButton();
        Button3 = new javax.swing.JButton();
        Button8 = new javax.swing.JButton();
        Button7 = new javax.swing.JButton();
        Button6 = new javax.swing.JButton();
        Button5 = new javax.swing.JButton();
        Button10 = new javax.swing.JButton();
        Button12 = new javax.swing.JButton();
        Button11 = new javax.swing.JButton();
        Button9 = new javax.swing.JButton();
        Button13 = new javax.swing.JButton();
        Button15 = new javax.swing.JButton();
        Button14 = new javax.swing.JButton();
        Button16 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jblNumOFClicks = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jbtnSolution = new javax.swing.JButton();
        jbtnReset = new javax.swing.JButton();
        txth = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1340, 750));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 255, 0));
        jLabel5.setText("Player");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 40, 160, -1));

        playerName.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        playerName.setForeground(new java.awt.Color(102, 102, 102));
        getContentPane().add(playerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 70, 330, 40));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0,200));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 6));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Button4.setBackground(new java.awt.Color(204, 204, 255));
        Button4.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button4.setForeground(new java.awt.Color(204, 204, 0));
        Button4.setText("4");
        Button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button4ActionPerformed(evt);
            }
        });
        jPanel2.add(Button4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 120, 110));

        Button1.setBackground(new java.awt.Color(204, 204, 255));
        Button1.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button1.setForeground(new java.awt.Color(204, 204, 0));
        Button1.setText("1");
        Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button1ActionPerformed(evt);
            }
        });
        jPanel2.add(Button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 110));

        Button2.setBackground(new java.awt.Color(204, 204, 255));
        Button2.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button2.setForeground(new java.awt.Color(204, 204, 0));
        Button2.setText("2");
        Button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button2ActionPerformed(evt);
            }
        });
        jPanel2.add(Button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 120, 110));

        Button3.setBackground(new java.awt.Color(204, 204, 255));
        Button3.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button3.setForeground(new java.awt.Color(204, 204, 0));
        Button3.setText("3");
        Button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button3ActionPerformed(evt);
            }
        });
        jPanel2.add(Button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 120, 110));

        Button8.setBackground(new java.awt.Color(204, 204, 255));
        Button8.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button8.setForeground(new java.awt.Color(204, 204, 0));
        Button8.setText("8");
        Button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button8ActionPerformed(evt);
            }
        });
        jPanel2.add(Button8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, 120, 110));

        Button7.setBackground(new java.awt.Color(204, 204, 255));
        Button7.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button7.setForeground(new java.awt.Color(204, 204, 0));
        Button7.setText("7");
        Button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button7ActionPerformed(evt);
            }
        });
        jPanel2.add(Button7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 120, 110));

        Button6.setBackground(new java.awt.Color(204, 204, 255));
        Button6.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button6.setForeground(new java.awt.Color(204, 204, 0));
        Button6.setText("6");
        Button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button6ActionPerformed(evt);
            }
        });
        jPanel2.add(Button6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 120, 110));

        Button5.setBackground(new java.awt.Color(204, 204, 255));
        Button5.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button5.setForeground(new java.awt.Color(204, 204, 0));
        Button5.setText("5");
        Button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button5ActionPerformed(evt);
            }
        });
        jPanel2.add(Button5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 120, 110));

        Button10.setBackground(new java.awt.Color(204, 204, 255));
        Button10.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button10.setForeground(new java.awt.Color(204, 204, 0));
        Button10.setText("10");
        Button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button10ActionPerformed(evt);
            }
        });
        jPanel2.add(Button10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 120, 110));

        Button12.setBackground(new java.awt.Color(204, 204, 255));
        Button12.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button12.setForeground(new java.awt.Color(204, 204, 0));
        Button12.setText("12");
        Button12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button12ActionPerformed(evt);
            }
        });
        jPanel2.add(Button12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 270, 120, 110));

        Button11.setBackground(new java.awt.Color(204, 204, 255));
        Button11.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button11.setForeground(new java.awt.Color(204, 204, 0));
        Button11.setText("11");
        Button11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button11ActionPerformed(evt);
            }
        });
        jPanel2.add(Button11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 120, 110));

        Button9.setBackground(new java.awt.Color(204, 204, 255));
        Button9.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button9.setForeground(new java.awt.Color(204, 204, 0));
        Button9.setText("9");
        Button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button9ActionPerformed(evt);
            }
        });
        jPanel2.add(Button9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 120, 110));

        Button13.setBackground(new java.awt.Color(204, 204, 255));
        Button13.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button13.setForeground(new java.awt.Color(204, 204, 0));
        Button13.setText("13");
        Button13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button13ActionPerformed(evt);
            }
        });
        jPanel2.add(Button13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 120, 110));

        Button15.setBackground(new java.awt.Color(204, 204, 255));
        Button15.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button15.setForeground(new java.awt.Color(204, 204, 0));
        Button15.setText("15");
        Button15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button15ActionPerformed(evt);
            }
        });
        jPanel2.add(Button15, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 400, 120, 110));

        Button14.setBackground(new java.awt.Color(204, 204, 255));
        Button14.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button14.setForeground(new java.awt.Color(204, 204, 0));
        Button14.setText("14");
        Button14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button14ActionPerformed(evt);
            }
        });
        jPanel2.add(Button14, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, 120, 110));

        Button16.setBackground(new java.awt.Color(204, 204, 255));
        Button16.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        Button16.setForeground(new java.awt.Color(204, 204, 0));
        Button16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button16ActionPerformed(evt);
            }
        });
        jPanel2.add(Button16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 400, 120, 110));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 530, 520));

        jPanel8.setBackground(new java.awt.Color(0, 0, 0,100));
        jPanel8.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Clicks");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 204), 6));

        jblNumOFClicks.setFont(new java.awt.Font("Tahoma", 1, 90)); // NOI18N
        jblNumOFClicks.setForeground(new java.awt.Color(255, 0, 102));
        jblNumOFClicks.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jblNumOFClicks.setText("1000");
        jblNumOFClicks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 204), 6));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jblNumOFClicks, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jblNumOFClicks, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 150, 380, 520));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0,100));
        jPanel3.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 88)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Puzzle Game");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(63, 63, 63))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 700, 110));

        jPanel9.setBackground(new java.awt.Color(0, 0, 0,100));
        jPanel9.setOpaque(false);

        jbtnExit.setBackground(new java.awt.Color(255, 255, 204));
        jbtnExit.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jbtnExit.setForeground(new java.awt.Color(0, 204, 204));
        jbtnExit.setText("Exit");
        jbtnExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 158, 160), 4));
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });

        jbtnSolution.setBackground(new java.awt.Color(255, 255, 204));
        jbtnSolution.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jbtnSolution.setForeground(new java.awt.Color(0, 204, 204));
        jbtnSolution.setText("Solution");
        jbtnSolution.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 158, 160), 4));
        jbtnSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSolutionActionPerformed(evt);
            }
        });

        jbtnReset.setBackground(new java.awt.Color(255, 255, 204));
        jbtnReset.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jbtnReset.setForeground(new java.awt.Color(0, 204, 204));
        jbtnReset.setText("Reset");
        jbtnReset.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(95, 158, 160), 4));
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });

        txth.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        txth.setForeground(new java.awt.Color(204, 255, 204));
        txth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txth.setText("0 : 0 : 0 : 0");
        txth.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 6));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 60)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 255, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Timer");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnReset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnSolution, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37)
                .addComponent(jbtnSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 150, 360, 520));

        jRadioButton1.setBackground(new java.awt.Color(0, 0, 0));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(204, 255, 0));
        jRadioButton1.setText("Music Off");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, 30));

        jRadioButton2.setBackground(new java.awt.Color(102, 102, 102));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(204, 255, 0));
        jRadioButton2.setText("Music On");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/64cfd6c43c44d203552ba75d_ - 4.png"))); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(1385, 750));
        jLabel3.setMinimumSize(new java.awt.Dimension(1385, 750));
        jLabel3.setPreferredSize(new java.awt.Dimension(1385, 750));
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1330, 710));

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("New Game");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button4ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button4,Button3);
        emptySpotChecker(Button4,Button8);
    }//GEN-LAST:event_Button4ActionPerformed

    private void Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button1ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button1,Button2);
        emptySpotChecker(Button1,Button5);
    }//GEN-LAST:event_Button1ActionPerformed

    private void Button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button2ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button2,Button1);
        emptySpotChecker(Button2,Button3);
        emptySpotChecker(Button2,Button6);
    }//GEN-LAST:event_Button2ActionPerformed

    private void Button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button3ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button3,Button2);
        emptySpotChecker(Button3,Button4);
        emptySpotChecker(Button3,Button7);
    }//GEN-LAST:event_Button3ActionPerformed

    private void Button8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button8ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button8,Button4);
        emptySpotChecker(Button8,Button7);
        emptySpotChecker(Button8,Button12);
    }//GEN-LAST:event_Button8ActionPerformed

    private void Button7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button7ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button7,Button3);
        emptySpotChecker(Button7,Button6);
        emptySpotChecker(Button7,Button8);
        emptySpotChecker(Button7,Button11);
    }//GEN-LAST:event_Button7ActionPerformed

    private void Button6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button6ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button6,Button2);
        emptySpotChecker(Button6,Button5);
        emptySpotChecker(Button6,Button7);
        emptySpotChecker(Button6,Button10);
    }//GEN-LAST:event_Button6ActionPerformed

    private void Button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button5ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button5,Button1);
        emptySpotChecker(Button5,Button6);
        emptySpotChecker(Button5,Button9);
    }//GEN-LAST:event_Button5ActionPerformed

    private void Button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button10ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button10,Button6);
        emptySpotChecker(Button10,Button9);
        emptySpotChecker(Button10,Button11);
        emptySpotChecker(Button10,Button14);
    }//GEN-LAST:event_Button10ActionPerformed

    private void Button12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button12ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button12,Button8);
        emptySpotChecker(Button12,Button11);
        emptySpotChecker(Button12,Button16);
    }//GEN-LAST:event_Button12ActionPerformed

    private void Button11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button11ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button11,Button7);
        emptySpotChecker(Button11,Button10);
        emptySpotChecker(Button11,Button12);
        emptySpotChecker(Button11,Button15);
    }//GEN-LAST:event_Button11ActionPerformed

    private void Button9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button9ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button9,Button5);
        emptySpotChecker(Button9,Button10);
        emptySpotChecker(Button9,Button13);
    }//GEN-LAST:event_Button9ActionPerformed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        // TODO add your handling code here:
        timerstop();
        int choice = JOptionPane.showConfirmDialog(rootPane, "Do You wanna exit?", "Exit", JOptionPane.OK_CANCEL_OPTION);

        if (choice == JOptionPane.OK_OPTION) {
            System.exit(0);
        } else {
            timerStart();
        }
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void Button13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button13ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button13,Button9);
        emptySpotChecker(Button13,Button14);
    }//GEN-LAST:event_Button13ActionPerformed

    private void Button15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button15ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button15,Button11);
        emptySpotChecker(Button15,Button14);
        emptySpotChecker(Button15,Button16);
    }//GEN-LAST:event_Button15ActionPerformed

    private void Button14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button14ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button14,Button10);
        emptySpotChecker(Button12,Button13);
        emptySpotChecker(Button14,Button15);
    }//GEN-LAST:event_Button14ActionPerformed

    private void Button16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button16ActionPerformed
        // TODO add your handling code here:
        filepath1 = "C:/Users/Kirula/Downloads/minimalpopclick.wav";
        play.playMusic(filepath1);
        puzzlewin();
        losegame();
        emptySpotChecker(Button16,Button12);
        emptySpotChecker(Button16,Button15);
    }//GEN-LAST:event_Button16ActionPerformed

    private void jbtnSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSolutionActionPerformed
        // TODO add your handling code here:
        timerstop();
        Button1.setText("1");
        Button2.setText("2");
        Button3.setText("3");
        Button4.setText("4");
        Button5.setText("5");
        Button6.setText("6");
        Button7.setText("7");
        Button8.setText("8");
        Button9.setText("9");
        Button10.setText("10");
        Button11.setText("11");
        Button12.setText("12");
        Button13.setText("13");
        Button14.setText("14");
        Button15.setText("15");
        Button16.setText("");
    }//GEN-LAST:event_jbtnSolutionActionPerformed

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnResetActionPerformed
        // TODO add your handling code here:
        int num = 0;
        timerstop();
        
        int choice = JOptionPane.showConfirmDialog(rootPane, "Do You wanna reset?", "Reset", JOptionPane.OK_CANCEL_OPTION);

        if (choice == JOptionPane.OK_OPTION) {
            ShuffleNUmber();
            txth.setText("0 : 0 : 0 : 0");
            jblNumOFClicks.setText("0");
            count = 1000;
            ms =0;
            s =0;
            m =0;
            h =0;
            if(count == 999){
                timerStart();
        }
        } else {
            timerStart();
            }
        
        
    }//GEN-LAST:event_jbtnResetActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        puzzlegame pg = new puzzlegame();
        pg.setVisible(true);
        this.dispose();
        count =0;
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
        play.startMusic();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        play.stopMusic();
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(puzzlegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(puzzlegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(puzzlegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(puzzlegame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new puzzlegame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button1;
    private javax.swing.JButton Button10;
    private javax.swing.JButton Button11;
    private javax.swing.JButton Button12;
    private javax.swing.JButton Button13;
    private javax.swing.JButton Button14;
    private javax.swing.JButton Button15;
    private javax.swing.JButton Button16;
    private javax.swing.JButton Button2;
    private javax.swing.JButton Button3;
    private javax.swing.JButton Button4;
    private javax.swing.JButton Button5;
    private javax.swing.JButton Button6;
    private javax.swing.JButton Button7;
    private javax.swing.JButton Button8;
    private javax.swing.JButton Button9;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JLabel jblNumOFClicks;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnReset;
    private javax.swing.JButton jbtnSolution;
    public javax.swing.JLabel playerName;
    private javax.swing.JLabel txth;
    // End of variables declaration//GEN-END:variables
}
