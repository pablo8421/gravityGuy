
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author César
 */
public class PlayerManage implements Runnable{

    Cuadrado jugador1;
    ArrayList<Cuadrado> cuadrados2;
    Graphics g;
    int cuadroActual;
    
    public PlayerManage(Cuadrado jugador1, ArrayList<Cuadrado> cuadrados2, int cuadroActual, Graphics g){
        this.jugador1 = jugador1;
        this.cuadrados2 = cuadrados2;
        this.cuadroActual = cuadroActual;
        this.g = g;
    }
    
    @Override
    public void run() {
        int jugador1Y;
        int pisoY;
        
        g.setColor(Color.white);
        g.drawRect(jugador1.x0, jugador1.y0, jugador1.width, jugador1.height);
        
        Cuadrado piso = cuadrados2.get(cuadroActual);
        jugador1Y = jugador1.y0 + jugador1.height;
        pisoY = piso.y0;
        if(pisoY > jugador1Y){
            jugador1.y0 += 5;   
        }
        
        g.setColor(Color.black);
        g.drawRect(jugador1.x0, jugador1.y0, jugador1.width, jugador1.height);
    }
    
}
