
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PabloJavier
 */
public class GGServer extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form GGServer
     */
    
    public boolean running;
    
    private static int puerto = 6321;
    
    private static int userQuantity = 2;
    
    private static Socket sockets[];
    private static BufferedReader in[];
    private static DataOutputStream out[];
    private Thread serverThread;
    private ServerConnection serverRun;
    private static JTextArea verbose;
    
    public final static String CRLF = "\r\n";
    
    
    
    public GGServer()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(GGServer.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        initComponents();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width , dim.height / 2 - this.getSize().height);
        
        running = true;
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                running = false;

            }
        });
        
        try
        {
            InetAddress address = InetAddress.getLocalHost();
            addressLabel.setText(address.getHostAddress());
        } catch (UnknownHostException ex)
        {
            addressLabel.setText("No idea");
            Logger.getLogger(GGServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sockets = new Socket[userQuantity];
        in = new BufferedReader[userQuantity];
        out = new DataOutputStream[userQuantity];
        
        serverRun = new ServerConnection();
        serverThread = new Thread(serverRun);
        serverThread.start();
        
        verbose = textAreaVerbose;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jButton1 = new javax.swing.JButton();
        addressLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaVerbose = new javax.swing.JTextArea();

        setTitle("Gravity Guy - Server");

        jButton1.setText("Stop server");
        jButton1.setActionCommand("stopButton");

        addressLabel.setText("I'm running on localhost");

        textAreaVerbose.setEditable(false);
        textAreaVerbose.setColumns(20);
        textAreaVerbose.setRows(5);
        jScrollPane1.setViewportView(textAreaVerbose);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addressLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(addressLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textAreaVerbose;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run()
    {
        this.setVisible(true);
    }

    private static void addLog(String text)
    {
        verbose.setText(verbose.getText() + text + "\n");
        
    }
    
    private static class ServerConnection implements Runnable{

        public ServerConnection()
        {
        }

        @Override
        public void run()
        {
            int quantity = 0;
            try
            {
                //Obtener puerto de entrada
                ServerSocket welcomeSocket = new ServerSocket(puerto);                
                while (quantity != 2)
                {
                    //Inizializa el socket para aceptar la conexion
                    Socket connectionSocket = welcomeSocket.accept();
                    
                    addLog("Connection received");
                    
                    sockets[quantity] = connectionSocket;
                    
                    in[quantity] = new BufferedReader(new InputStreamReader(sockets[quantity].getInputStream()));
                    out[quantity] = new DataOutputStream(sockets[quantity].getOutputStream());
                    
                    String request = in[quantity].readLine();

                    if (request.equals("HELLO"))
                    {
                        out[quantity].writeBytes("OK player " + quantity + CRLF);
                    } else
                    {
                        out[quantity].writeBytes("NOT player " + quantity + CRLF);
                    }

                    addLog("Player " + quantity + " connected");

                    
                    quantity++;
                    
                }
                
                startGame();
                
            } catch (IOException ex)
            {
                Logger.getLogger(GGServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private static void sendBoth(String message)
        {
            for(int i = 0; i < userQuantity; i++)
            {
                try
                {
                    out[i].writeBytes(message + CRLF);
                } catch (IOException ex)
                {
                    Logger.getLogger(GGServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            addLog(message);
        }
        
        public void startGame()
        {
            sendBoth("START GAME");
        }
    }
}
