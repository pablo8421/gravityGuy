
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kevin Avendaño
 */
public class VentanaJuego extends java.awt.Frame {

    /**
     * Creates new form Prueba
     */
    
    GameState gState;
    int estadoJuego;
    
    public final static String CRLF = "\r\n";
    
    Socket pingSocket;
    boolean gDown;
    DataOutputStream out;
    BufferedReader in;
    Thread thread;
    Thread thread2;
    Paintor paintor;
    Updater updater;
    
    int count;
    BufferedImage myFree[];
    BufferedImage myTrapped[];
    BufferedImage otherFree[];
    BufferedImage otherTrapped[];

    public VentanaJuego(Socket pingSocket, DataOutputStream out, BufferedReader in, int jugador) throws IOException {
        initComponents();
        this.pingSocket = pingSocket;
        this.out = out;
        this.in = in;
        this.addKeyListener(new myKeyListener());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        loadImages(jugador);
        while (!in.ready()){}
        String br = null;
        gDown = true;
        estadoJuego = 0;
        gState = new GameState();
                
        paintor = new Paintor(this);
        updater = new Updater();
        thread = new Thread(paintor);
        thread.start();
        thread2 = new Thread(updater);
        thread2.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(640, 640));
        setMinimumSize(new java.awt.Dimension(640, 640));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     */
    
    @Override
    public void paint(Graphics g) {
            super.paint(g);
            GameState now = gState;
            
            //g.setColor(Color.black);
            //g.fillRect(now.jugador1.x0, now.jugador1.y0, now.jugador1.width, now.jugador1.height);
            g.drawImage(myFree[count], now.jugador1.x0, now.jugador1.y0, this);
            g.drawImage(otherFree[count], now.jugador2.x0, now.jugador2.y0, this);
            count++;
            count = (count == 4)? 0 : count;
            
            g.setColor(Color.red);
            for (int i = 0; i < now.cuadradosTop.size(); i++) {
                g.fillRect(now.cuadradosTop.get(i).x0, now.cuadradosTop.get(i).y0, now.cuadradosTop.get(i).width, now.cuadradosTop.get(i).height);
            }
            
            for (int i = 0; i < now.cuadradosBottom.size(); i++) {
                g.fillRect(now.cuadradosBottom.get(i).x0, now.cuadradosBottom.get(i).y0, now.cuadradosBottom.get(i).width, now.cuadradosBottom.get(i).height);
            }
     } 

    private void loadImages(int jugador)
    {
        String myPath = "sprite/p" + jugador + "/";
        jugador = (jugador == 0)? 1:0;
        String otherPath = "sprite/p" + jugador + "/";
        myFree = new BufferedImage[4];
        otherFree = new BufferedImage[4];
        myTrapped = new BufferedImage[4];
        otherTrapped = new BufferedImage[4];
        count = 0;
        
        for(int i = 0; i < 4; i++)
        {
            try
            {
                System.out.println(myPath + "a" + i + ".png");
                myFree[i] = ImageIO.read(new File(myPath + "a" + i + ".png"));
                otherFree[i] = ImageIO.read(new File(otherPath + "a" + i + ".png"));
                myTrapped[i] = ImageIO.read(new File(myPath + "s" + i + ".png"));
                otherTrapped[i] = ImageIO.read(new File(otherPath + "s" + i + ".png"));

            } catch (IOException ex)
            {
                Logger.getLogger(VentanaJuego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class myKeyListener implements KeyListener {

        public myKeyListener()
        {
        }

        @Override
        public void keyTyped(KeyEvent e)
        {
            if (e.getKeyChar() == ' ' || true){

                gDown = !gDown;
                try
                {
                    out.writeBytes("GC " + gDown + CRLF);
                } catch (IOException ex)
                {
                    Logger.getLogger(VentanaJuego.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Send: GC " + gDown + CRLF);                
            }
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            
            
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            
        }
    }

    private class Paintor implements Runnable {

        VentanaJuego juego;
        
        public Paintor(VentanaJuego juego)
        {
            this.juego = juego;
        }

        @Override
        public void run()
        {
            while(estadoJuego == 0)
            {
                try
                {
                    while(!juego.in.ready()){}
                } catch (IOException ex)
                {
                    Logger.getLogger(VentanaJuego.class.getName()).log(Level.SEVERE, null, ex);
                }
                juego.repaint();
            }
            juego.repaint();
            if(estadoJuego == 1)
            {
                JOptionPane.showMessageDialog(null, "Gano! Felicidades!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Pos perdiste mijo");
            }
        }
    }
    
    private class Updater implements Runnable
    {

        @Override
        public void run()
        {
            GameState now = gState;
            while(true)
            {
                try
                {
                    while (!in.ready())
                    {
                    }

                    String br;
                    while (!(br = in.readLine()).equals("END STATE"))
                    {
                        if(br.equals("GANO"))
                        {
                            estadoJuego = 1;
                        }
                        else if(br.equals("PERDIO"))
                        {
                            estadoJuego = 2;
                        }
                        else
                        {
                            now.deSerializePiece(br);
                        }
                    }
                } catch (IOException ex)
                {
                    Logger.getLogger(VentanaJuego.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
