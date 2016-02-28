
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    ArrayList<Cuadrado> cuadrados;
    ArrayList<Cuadrado> cuadrados2;
    Cuadrado jugador1=new Cuadrado(320, 320, 100, 100);;
    Cuadrado jugador2;
    Socket pingSocket;
    PrintWriter out;
    BufferedReader in;
    

    public VentanaJuego(Socket pingSocket, PrintWriter out, BufferedReader in) {
        initComponents();
        this.pingSocket = pingSocket;
        this.out = out;
        this.in = in;
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

    public void paint(Graphics g) {
    //Here is how we used to draw a square with width
        //of 200, height of 200, and starting at x=50, y=50.
        cuadrados = new ArrayList();
        cuadrados2 = new ArrayList();
        Cuadrado cuadrado;
        int randomx;
        int cuadroActual=0;
        int randomy;
        int xAnterior = 0;
        int x = 0;

        //PARA ARRIBA
        while (x < this.getWidth()) {
            randomx = (int) (Math.random() * (150 - 0)) + 10;
            randomy = (int) (Math.random() * (150 - 0)) + 10;
            x = xAnterior;
            cuadrado = new Cuadrado(x, 0, randomx, randomy);
            cuadrados.add(cuadrado);
            xAnterior = xAnterior + randomx;
        }

        //PARA ABAJO, Para abajo Para abajo
        x = 0;
        while (x < this.getWidth()) {
            randomx = (int) (Math.random() * (150 - 0)) + 10;
            randomy = (int) (Math.random() * (150 - 0)) + 10;
            x = xAnterior;
            cuadrado = new Cuadrado(x, this.getHeight() - randomy, randomx, randomy);
            cuadrados2.add(cuadrado);
            xAnterior = xAnterior + randomx;
        }
        
        g.drawRect(jugador1.x0, jugador1.y0, jugador1.width, jugador1.height);
        while (true) {
            //PARA ARRIBA
            /*for (int i = 0; i < cuadrados.size(); i++) {
                g.setColor(Color.white);
                g.drawRect(cuadrados.get(i).x0, cuadrados.get(i).y0, cuadrados.get(i).width, cuadrados.get(i).height);
            }
            for (int i = 0; i < cuadrados.size(); i++) {
                cuadrados.get(i).x0 -= 10;
            }
            if ((cuadrados.get(0).x0 + cuadrados.get(0).width) <= 0) {
                cuadrados.remove(0);
            }
            if ((cuadrados.get(cuadrados.size() - 1).x0 + cuadrados.get(cuadrados.size() - 1).width) <= this.getWidth()) {
                randomx = (int) (Math.random() * (150 - 0)) + 10;
                randomy = (int) (Math.random() * (150 - 0)) + 10;
            //g.setColor(Color.red);
                //g.drawRect(x,0,randomx,randomy);
                xAnterior = cuadrados.get(cuadrados.size() - 1).x0 + cuadrados.get(cuadrados.size() - 1).width;
                cuadrado = new Cuadrado(xAnterior, 0, randomx, randomy);
                cuadrados.add(cuadrado);
            }
            for (int i = 0; i < cuadrados.size(); i++) {
                g.setColor(Color.red);
                g.drawRect(cuadrados.get(i).x0, cuadrados.get(i).y0, cuadrados.get(i).width, cuadrados.get(i).height);
            }*/

            //PARA ABAJO
            for (int i = 0; i < cuadrados2.size(); i++) {
                g.setColor(Color.white);
                g.drawRect(cuadrados2.get(i).x0, cuadrados2.get(i).y0, cuadrados2.get(i).width, cuadrados2.get(i).height);
            }
            for (int i = 0; i < cuadrados2.size(); i++) {
                cuadrados2.get(i).x0 -= 10;
            }
            if ((cuadrados2.get(0).x0 + cuadrados2.get(0).width) <= 0) {
                cuadrados2.remove(0);
            }
            if ((cuadrados2.get(cuadrados2.size() - 1).x0 + cuadrados2.get(cuadrados2.size() - 1).width) <= this.getWidth()) {
                randomx = (int) (Math.random() * (150 - 0)) + 10;
                randomy = (int) (Math.random() * (150 - 0)) + 10;
            //g.setColor(Color.red);
                //g.drawRect(x,0,randomx,randomy);
                xAnterior = cuadrados2.get(cuadrados2.size() - 1).x0 + cuadrados2.get(cuadrados2.size() - 1).width;
                cuadrado = new Cuadrado(xAnterior, this.getHeight() - randomy, randomx, randomy);
                cuadrados2.add(cuadrado);
            }
            for (int i = 0; i < cuadrados2.size(); i++) {
                g.setColor(Color.red);
                g.drawRect(cuadrados2.get(i).x0, cuadrados2.get(i).y0, cuadrados2.get(i).width, cuadrados2.get(i).height);
                if(cuadrados2.get(i).x0 <= jugador1.x0 && (cuadrados2.get(i).x0 + cuadrados2.get(i).width) >= jugador1.x0){
                    cuadroActual = i;
                }
            }
            
            //AQUI SE LLAMA LA GRAVEDAD
            
            PlayerManage playerm1 = new PlayerManage(jugador1,cuadrados2,cuadroActual,g);
            Thread infThread = new Thread(playerm1);
            infThread.start();
            
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(VentanaJuego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
