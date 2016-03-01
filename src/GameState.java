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
 * @author PabloJavier
 */
public class GameState {

    ArrayList<Cuadrado> cuadradosBottom;
    ArrayList<Cuadrado> cuadradosTop;
    Cuadrado jugador1;
    Cuadrado jugador2;

    public final static String CRLF = "\r\n";
    
    int width, height;

    public GameState()
    {
        cuadradosBottom = new ArrayList();
        cuadradosTop = new ArrayList();
        jugador1 = new Cuadrado(320, 320, 100, 100);
        jugador2 = new Cuadrado(320, 320, 100, 100);
        width = 640;
        height = 640;
    }

    public void initGame()
    {
        Cuadrado cuadrado;
        int randomx;
        int randomy;
        int xAnterior = 0;
        int x = 0;

        //PARA ARRIBA
        while (x < width)
        {
            randomx = (int) (Math.random() * (150 - 0)) + 10;
            randomy = (int) (Math.random() * (150 - 0)) + 10;
            x = xAnterior;
            cuadrado = new Cuadrado(x, 0, randomx, randomy);
            cuadradosTop.add(cuadrado);
            xAnterior = xAnterior + randomx;
        }

        //PARA ABAJO, Para abajo Para abajo
        x = 0;
        while (x < width)
        {
            randomx = (int) (Math.random() * (150 - 0)) + 10;
            randomy = (int) (Math.random() * (150 - 0)) + 10;
            x = xAnterior;
            cuadrado = new Cuadrado(x, height - randomy, randomx, randomy);
            cuadradosBottom.add(cuadrado);
            xAnterior = xAnterior + randomx;
        }
    }

    public void updateState()
    {
        Cuadrado cuadrado;
        int cuadroActual = 0;
        int randomx;
        int randomy;
        int xAnterior;

        //PARA ARRIBA
        for (int i = 0; i < cuadradosBottom.size(); i++)
        {
                //g.setColor(Color.white);
            //g.drawRect(cuadradosBottom.get(i).x0, cuadradosBottom.get(i).y0, cuadradosBottom.get(i).width, cuadradosBottom.get(i).height);
        }
        for (int i = 0; i < cuadradosBottom.size(); i++)
        {
            cuadradosBottom.get(i).x0 -= 10;
        }
        if ((cuadradosBottom.get(0).x0 + cuadradosBottom.get(0).width) <= 0)
        {
            cuadradosBottom.remove(0);
        }
        if ((cuadradosBottom.get(cuadradosBottom.size() - 1).x0 + cuadradosBottom.get(cuadradosBottom.size() - 1).width) <= width)
        {
            randomx = 120;
            randomy = (int) (Math.random() * (150 - 0)) + 10;
                //g.setColor(Color.red);
            //g.drawRect(x,0,randomx,randomy);
            xAnterior = cuadradosBottom.get(cuadradosBottom.size() - 1).x0 + cuadradosBottom.get(cuadradosBottom.size() - 1).width;
            cuadrado = new Cuadrado(xAnterior, 0, randomx, randomy);
            cuadradosBottom.add(cuadrado);
        }
        for (int i = 0; i < cuadradosBottom.size(); i++)
        {
                //g.setColor(Color.red);
            //g.drawRect(cuadradosBottom.get(i).x0, cuadradosBottom.get(i).y0, cuadradosBottom.get(i).width, cuadradosBottom.get(i).height);
        }

        //PARA ABAJO
        for (int i = 0; i < cuadradosTop.size(); i++)
        {
                //g.setColor(Color.white);
            //g.drawRect(cuadradosTop.get(i).x0, cuadradosTop.get(i).y0, cuadradosTop.get(i).width, cuadradosTop.get(i).height);
        }
        for (int i = 0; i < cuadradosTop.size(); i++)
        {
            cuadradosTop.get(i).x0 -= 10;
        }
        if ((cuadradosTop.get(0).x0 + cuadradosTop.get(0).width) <= 0)
        {
            cuadradosTop.remove(0);
        }
        if ((cuadradosTop.get(cuadradosTop.size() - 1).x0 + cuadradosTop.get(cuadradosTop.size() - 1).width) <= width)
        {
            randomx = 120;
            randomy = (int) (Math.random() * (150 - 0)) + 10;
                //g.setColor(Color.red);
            //g.drawRect(x,0,randomx,randomy);
            xAnterior = cuadradosTop.get(cuadradosTop.size() - 1).x0 + cuadradosTop.get(cuadradosTop.size() - 1).width;
            cuadrado = new Cuadrado(xAnterior, height - randomy, randomx, randomy);
            cuadradosTop.add(cuadrado);
        }
        for (int i = 0; i < cuadradosTop.size(); i++)
        {
                //g.setColor(Color.red);
            //g.drawRect(cuadradosTop.get(i).x0, cuadradosTop.get(i).y0, cuadradosTop.get(i).width, cuadradosTop.get(i).height);
            if (cuadradosTop.get(i).x0 <= jugador1.x0 && (cuadradosTop.get(i).x0 + cuadradosTop.get(i).width) >= jugador1.x0)
            {
                cuadroActual = i;
            }
        }
        gravedadArriba(cuadroActual);
        limitePared(cuadroActual+1); 
        
            //g.setColor(Color.white);
        //g.drawRect(jugador1.x0, jugador1.y0, jugador1.width, jugador1.height);
        //AQUI SE LLAMA LA GRAVEDAD

            //PlayerManage playerm1 = new PlayerManage(jugador1, cuadradosTop, cuadroActual);
        //Thread infThread = new Thread(playerm1);
        //infThread.start();
            //g.setColor(Color.black);
        //g.drawRect(jugador1.x0, jugador1.y0, jugador1.width, jugador1.height);
        try
        {
            Thread.sleep(50);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(VentanaJuego.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String serializeState()
    {
        String data = "";

        data += "J1 ["
                + jugador1.x0 + ","
                + jugador1.y0 + ","
                + jugador1.width + ","
                + jugador1.height
                + "]" + CRLF;

        data += "J2 ["
                + jugador1.x0 + ","
                + jugador1.y0 + ","
                + jugador1.width + ","
                + jugador1.height
                + "]" + CRLF;

        for (int i = 0; i < cuadradosTop.size(); i++)
        {
            Cuadrado cuadrado = cuadradosTop.get(i);
            data += "CT" + i + " ["
                    + cuadrado.x0 + ","
                    + cuadrado.y0 + ","
                    + cuadrado.width + ","
                    + cuadrado.height
                    + "]" + CRLF;
        }

        for (int i = 0; i < cuadradosBottom.size(); i++)
        {
            Cuadrado cuadrado = cuadradosBottom.get(i);
            data += "CB" + i + " ["
                    + cuadrado.x0 + ","
                    + cuadrado.y0 + ","
                    + cuadrado.width + ","
                    + cuadrado.height
                    + "]" + CRLF;
        }

        return data;
    }
    
    public void deSerializePiece(String piece)
    {        
        
        
        if(piece.startsWith("J"))
        {
            Cuadrado player;
            if (piece.startsWith("J1"))
            {
                player = jugador1;
            } else
            {
                player = jugador2;
            }

            piece = piece.substring(piece.indexOf("[") + 1);
            piece = piece.substring(0, piece.indexOf("]"));
            String values[] = piece.split(",");

            player.x0 = Integer.parseInt(values[0]);
            player.y0 = Integer.parseInt(values[1]);
            player.width = Integer.parseInt(values[2]);
            player.height = Integer.parseInt(values[3]);
            
        }
        else if(piece.startsWith("C"))
        {
            ArrayList<Cuadrado> list;
            if (piece.startsWith("CT"))
            {
                list = cuadradosTop;
            } else
            {
                list = cuadradosBottom;
            }
            
            int index = Integer.parseInt(piece.substring(2,piece.indexOf("[")).trim());
            
            piece = piece.substring(piece.indexOf("[") + 1);
            piece = piece.substring(0, piece.indexOf("]"));
            String values[] = piece.split(",");
            
            Cuadrado cuadrado = new Cuadrado(
                Integer.parseInt(values[0]), //x0
                Integer.parseInt(values[1]), //y0
                Integer.parseInt(values[2]), //width
                Integer.parseInt(values[3])  //height
            );
            if(index < list.size())
            {
                list.set(index, cuadrado);
            }
            else if(index == list.size())
            {
                list.add(cuadrado);
            }
            else
            {
                //wtf?
                throw new IndexOutOfBoundsException();
            }
        }
    }
    public void gravedadAbajo(int cuadroActual) {
        int jugador1Y;
        int pisoY;

        Cuadrado piso = cuadradosTop.get(cuadroActual);
        jugador1Y = jugador1.y0 + jugador1.height;
        pisoY = piso.y0;
        if (pisoY > jugador1Y) {
            jugador1.y0 += 5;
        }
    }
    public void gravedadArriba(int cuadroActual) {
        int jugador1Y;
        int pisoY;

        Cuadrado piso = cuadradosTop.get(cuadroActual);
        jugador1Y = jugador1.y0 + jugador1.height;
        pisoY = piso.y0;
        if (pisoY > jugador1Y) {
            jugador1.y0 -= 5;
        }
    }
    public void limitePared(int cuadroActual) {
        int jugador1X;
        int jugador1Y;
        int paredX;
        int pisoY;
        Cuadrado pared = cuadradosTop.get(cuadroActual);
        jugador1X = jugador1.x0 + jugador1.width;
        jugador1Y = jugador1.y0 + jugador1.height;
        pisoY = pared.y0;
        paredX = pared.x0;
        if (jugador1X == paredX && pisoY < jugador1Y) {
            jugador1.x0 -= 10;
        }
        else if(jugador1X > paredX && pisoY < jugador1Y){
            jugador1.x0 = paredX - jugador1.width;
        }
    }
    
}
