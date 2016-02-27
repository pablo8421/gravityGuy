
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static int puerto = 2222;
    
    private int userQuantity = 2;
    private static Thread threads[];
    private static UserConnection userManager[];
    private Thread serverThread;
    private ServerConnection serverRun;
    
    
    
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
        
        userManager = new UserConnection[userQuantity];
        for(int i = 0; i < userQuantity; i++)
        {
            userManager[i] = new UserConnection();
        }
        
        threads = new Thread[userQuantity];
        for(int i = 0; i < userQuantity; i++)
        {
            threads[i] = new Thread(userManager[i]);
        }
        
        serverRun = new ServerConnection();
        serverThread = new Thread(serverRun);
        serverThread.start();
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

    private static class UserConnection implements Runnable {

        public Socket socket;
        public BufferedReader in;
        public DataOutputStream out;
        
        public int ident;
        public boolean initGame;
        
        public UserConnection()
        {
        }

        public void addSocket(Socket socket) throws IOException
        {
            this.socket = socket;
            //Obtener buffers
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            
        }
        
        @Override
        public void run()
        {
            try
            {
                boolean keepGoing = true;
                
                String request = in.readLine();
                
                if(request.equals("HELLO"))
                {
                    out.writeBytes("OK player "+ident);
                }
                
                while(keepGoing)
                {
                    
                }
            } catch (IOException ex)
            {
                Logger.getLogger(GGServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                    
                    userManager[quantity].addSocket(connectionSocket);
                    userManager[quantity].ident = quantity;
                    threads[quantity].start();
                    
                    quantity++;
                    
                }
            } catch (IOException ex)
            {
                Logger.getLogger(GGServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
