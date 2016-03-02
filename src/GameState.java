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

    ArrayList<Cuadrado> cuadradosBot;
    ArrayList<Cuadrado> cuadradosTop;
    ArrayList<Cuadrado> cuadradosMid;
    Cuadrado jugador1;
    Cuadrado jugador2;
    int cuadroTop, cuadroBottom;
    boolean gravityDown[], victoria[], fin, stuck[];

    public final static String CRLF = "\r\n";
    
    int width, height;

    public GameState()
    {
        cuadradosBot = new ArrayList();
        cuadradosTop = new ArrayList();
        cuadradosMid = new ArrayList();
        jugador1 = new Cuadrado(320, 300, 50, 50);
        jugador2 = new Cuadrado(320, 400, 50, 50);
        gravityDown = new boolean[2];
        victoria = new boolean[2];
        stuck = new boolean[2];
        fin = false;
        for(int i =0; i < gravityDown.length; i++)
        {
            gravityDown[i] = true;
            victoria[i] = false;
            stuck[i] = false;
        }
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
            randomx = 127;
            randomy = (int) (Math.random() * (150)) + 30;
            x = xAnterior;
            cuadrado = new Cuadrado(x, 0, randomx, randomy);
            cuadradosTop.add(cuadrado);
            xAnterior = xAnterior + randomx;
        }

        //PARA ABAJO, Para abajo Para abajo
        x = 0;
        xAnterior = 0;
        while (x < width)
        {
            randomx = 127;
            randomy = (int) (Math.random() * (150)) + 30;
            x = xAnterior;
            cuadrado = new Cuadrado(x, height - randomy, randomx, randomy);
            cuadradosBot.add(cuadrado);
            xAnterior = xAnterior + randomx;
        }
        x = width - 60;
        while (x < width)
        {
            randomx = (int) (Math.random() * (80)) + 60;
            randomy = (int) (Math.random() * (490 - 150)) + 150;
            int randomHeight = (int) (Math.random() * (40)) + 10;
            x = xAnterior;
            cuadrado = new Cuadrado(x, height - randomy, randomx, randomHeight);
            cuadradosMid.add(cuadrado);
            xAnterior = xAnterior + randomx;
        }
    }

    public void updateState()
    {
        Cuadrado cuadrado;
        int cuadroActual = 0, cuadroSiguiente = 0;
        int randomx;
        int randomy;
        int xAnterior;
        
        for(int i = 0; i < stuck.length; i++)
        {
            stuck[i] = false;
        }

        //PARTE DE ARRIBA DEL MUNDO
        
        //Corriendo la parte de arriba del mundo
        for (int i = 0; i < cuadradosTop.size(); i++)
        {
            cuadradosTop.get(i).x0 -= 10;
        }

        //Eliminando los cuadros de la parte de arriba del juego cuando ya no se ven
        if ((cuadradosTop.get(0).x0 + cuadradosTop.get(0).width) <= 0)
        {
            cuadradosTop.remove(0);
        }
        
        
        
        //Generando cuadros en la parte de arriba del mundo
        if ((cuadradosTop.get(cuadradosTop.size() - 1).x0 + cuadradosTop.get(cuadradosTop.size() - 1).width) <= width)
        {
            randomx = 127;
            randomy = (int) (Math.random() * (150 - 0)) + 30;
            xAnterior = cuadradosTop.get(cuadradosTop.size() - 1).x0 + cuadradosTop.get(cuadradosTop.size() - 1).width;
            cuadrado = new Cuadrado(xAnterior, 0, randomx, randomy);
            cuadradosTop.add(cuadrado);
        }
        
        //Corriendo la parte del mid del mundo
        for (int i = 0; i < cuadradosMid.size(); i++)
        {
            cuadradosMid.get(i).x0 -= 10;
        }

        //Eliminando los cuadros de la parte del mid del juego cuando ya no se ven
        if ((cuadradosMid.get(0).x0 + cuadradosMid.get(0).width) <= 0)
        {
            cuadradosMid.remove(0);
        }
        if ((cuadradosMid.get(cuadradosMid.size() - 1).x0 + cuadradosMid.get(cuadradosMid.size() - 1).width) <= width)
        {
            randomx = (int) (Math.random() * (80)) + 60;
            randomy = (int) (Math.random() * (490 - 150)) + 150;
            int randomHeight = (int) (Math.random() * (40)) + 10;
            xAnterior = width - 10;
            cuadrado = new Cuadrado(xAnterior, randomy, randomx, randomHeight);
            if(Math.random()<=0.10){
                cuadradosMid.add(cuadrado);
            }
            
        }
        //PARA ABAJO
        for (int i = 0; i < cuadradosBot.size(); i++)
        {
            cuadradosBot.get(i).x0 -= 10;
        }
        if ((cuadradosBot.get(0).x0 + cuadradosBot.get(0).width) <= 0)
        {
            cuadradosBot.remove(0);
        }
        if ((cuadradosBot.get(cuadradosBot.size() - 1).x0 + cuadradosBot.get(cuadradosBot.size() - 1).width) <= width)
        {
            randomx = 127;
            randomy = (int) (Math.random() * (150 - 0)) + 30;
            xAnterior = cuadradosBot.get(cuadradosBot.size() - 1).x0 + cuadradosBot.get(cuadradosBot.size() - 1).width;
            cuadrado = new Cuadrado(xAnterior, height - randomy, randomx, randomy);
            cuadradosBot.add(cuadrado);
        }
        
        boolean bandera = true;
        if (gravityDown[0]){
            for (int i = 0; i < cuadradosBot.size(); i++)
            {
                if (cuadradosBot.get(i).x0 <= jugador1.x0 && (cuadradosBot.get(i).x0 + cuadradosBot.get(i).width) >= jugador1.x0)
                {
                    cuadroActual = i;
                }
                if (  jugador1.x0 >= cuadradosBot.get(i).x0+cuadradosBot.get(i).width && bandera)
                {
                    cuadroSiguiente = i;
                    bandera = false;
                }
            }
            for (int i = 0; i < cuadradosTop.size(); i++)
            {
                if (cuadradosTop.get(i).x0 <= jugador1.x0 && (cuadradosTop.get(i).x0 + cuadradosTop.get(i).width) >= jugador1.x0)
                {
                    cuadroTop = i;
                }
            }
            gravedadAbajo(cuadroActual,jugador1);
            stuck[0] = stuck[0] || limiteParedBottom(cuadroSiguiente,jugador1); 
        }
        else{
            for (int i = 0; i < cuadradosTop.size(); i++)
            {
                if (cuadradosTop.get(i).x0 <= jugador1.x0 && (cuadradosTop.get(i).x0 + cuadradosTop.get(i).width) >= jugador1.x0)
                {
                    cuadroActual = i;
                }
                if (jugador1.x0 >= cuadradosTop.get(i).x0+cuadradosTop.get(i).width && bandera )
                {
                    cuadroSiguiente = i;
                    bandera = false;
                }
            }
            for (int i = 0; i < cuadradosBot.size(); i++)
            {
                if (cuadradosBot.get(i).x0 <= jugador1.x0 && (cuadradosBot.get(i).x0 + cuadradosBot.get(i).width) >= jugador1.x0)
                {
                    cuadroBottom = i;
                }
            }
            gravedadArriba(cuadroActual,jugador1);
            stuck[0] =  stuck[0] || limiteParedTop(cuadroSiguiente,jugador1); 
        }
        
        cuadroSiguiente = 0;
        bandera = true;
        if (gravityDown[1]){
            for (int i = 0; i < cuadradosBot.size(); i++)
            {
                if (cuadradosBot.get(i).x0 <= jugador2.x0 && (cuadradosBot.get(i).x0 + cuadradosBot.get(i).width) >= jugador2.x0)
                {
                    cuadroActual = i;
                }
                if (jugador2.x0 >= cuadradosBot.get(i).x0+cuadradosBot.get(i).width && bandera )
                {
                    cuadroSiguiente = i;
                    bandera = false;
                }
            }
            for (int i = 0; i < cuadradosTop.size(); i++)
            {
                if (cuadradosTop.get(i).x0 <= jugador2.x0 && (cuadradosTop.get(i).x0 + cuadradosTop.get(i).width) >= jugador2.x0)
                {
                    cuadroTop = i;
                }
            }
            gravedadAbajo(cuadroActual,jugador2);
            stuck[1] = stuck[1] || limiteParedBottom(cuadroSiguiente,jugador2); 
        }
        else{
            for (int i = 0; i < cuadradosTop.size(); i++)
            {
                if (cuadradosTop.get(i).x0 <= jugador2.x0 && (cuadradosTop.get(i).x0 + cuadradosTop.get(i).width) >= jugador2.x0)
                {
                    cuadroActual = i;
                }
                if ( jugador2.x0 >= cuadradosTop.get(i).x0+cuadradosTop.get(i).width && bandera)
                {
                    cuadroSiguiente = i;
                    bandera = false;
                }
            }
            for (int i = 0; i < cuadradosBot.size(); i++)
            {
                if (cuadradosBot.get(i).x0 <= jugador2.x0 && (cuadradosBot.get(i).x0 + cuadradosBot.get(i).width) >= jugador2.x0)
                {
                    cuadroBottom = i;
                }
            }
            gravedadArriba(cuadroActual,jugador2);
            stuck[1] = stuck[1] || limiteParedTop(cuadroSiguiente,jugador2); 
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
                + jugador2.x0 + ","
                + jugador2.y0 + ","
                + jugador2.width + ","
                + jugador2.height
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

        for (int i = 0; i < cuadradosBot.size(); i++)
        {
            Cuadrado cuadrado = cuadradosBot.get(i);
            data += "CB" + i + " ["
                    + cuadrado.x0 + ","
                    + cuadrado.y0 + ","
                    + cuadrado.width + ","
                    + cuadrado.height
                    + "]" + CRLF;
        }
        
        for (int i = 0; i < cuadradosMid.size(); i++)
        {
            Cuadrado cuadrado = cuadradosMid.get(i);
            data += "CM" + i + " ["
                    + cuadrado.x0 + ","
                    + cuadrado.y0 + ","
                    + cuadrado.width + ","
                    + cuadrado.height
                    + "]" + CRLF;
        }

        data += "G1 " + gravityDown[0] + CRLF;
        data += "G2 " + gravityDown[1] + CRLF;

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
            if (piece.startsWith("CT")){
                list = cuadradosTop;
            } 
            else if(piece.startsWith("CM")){
                list = cuadradosMid;
            }
            else{
                list = cuadradosBot;
            }
            
            int index = Integer.parseInt(piece.substring(2,piece.indexOf("[")).trim());
            
            piece = piece.substring(piece.indexOf("[") + 1);
            piece = piece.substring(0, piece.indexOf("]"));
            String values[] = piece.split(",");
            
            Cuadrado cuadrado = new Cuadrado(
                Integer.parseInt(values[0]), 
                Integer.parseInt(values[1]), 
                Integer.parseInt(values[2]), 
                Integer.parseInt(values[3])  
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
                throw new IndexOutOfBoundsException();
            }
        }
        else if(piece.startsWith("G"))
        {
            int index;
            if(piece.startsWith("G1"))
            {
                index = 0;
            }
            else
            {
                index = 1;
            }
            
            if(piece.substring(2).trim().equals("true"))
            {
                gravityDown[index] = true;
            }
            else
            {
                gravityDown[index] = false;
            }
        }
        
    }
    public void gravedadAbajo(int cuadroActual, Cuadrado jugador) {
        int jugadorY, jugadorX, paredY, topX, jugadorYo;
        int pisoY;
        boolean bandera = false;
        Cuadrado piso = cuadradosBot.get(cuadroActual);
        Cuadrado top = cuadradosTop.get(cuadroTop+1);
        topX = top.x0;
        paredY = top.y0 + top.height;
        jugadorY = jugador.y0 + jugador.height;
        jugadorX = jugador.x0 + jugador.width;
        pisoY = piso.y0;
        if (pisoY > jugadorY) {
            jugador.y0 += 5;
            bandera = true;
            
        }
        jugadorY = jugador.y0 + jugador.height;
        if (pisoY <= jugadorY && bandera) {
            jugador.y0 = pisoY-jugador.height-1;
        }
        jugadorYo = jugador.y0;
        if (jugadorX >= topX && paredY > jugadorYo) {
            jugador.x0 -= 10;
        }
        jugadorX = jugador.x0 + jugador.width;
        if(jugadorX < topX && paredY > jugadorYo){
            jugador.x0 = topX - jugador.width-1;
        }   
    }
    public void gravedadArriba(int cuadroActual, Cuadrado jugador) {
        int jugadorY, bottomX, paredY, jugadorYH, jugadorX;
        int pisoY;
        boolean bandera = false;
        Cuadrado piso = cuadradosTop.get(cuadroActual);
        Cuadrado bottom = cuadradosBot.get(cuadroBottom+1);
        bottomX = bottom.x0;
        paredY = bottom.y0;
        jugadorY = jugador.y0;
        jugadorX = jugador.x0 + jugador.width;
        pisoY = piso.y0 + piso.height;
        if (pisoY < jugadorY) {
            jugador.y0 -= 5;
            bandera = true;
        }
        jugadorY = jugador.y0 + jugador.height;
        if (pisoY >= jugadorY && bandera) {
            jugador.y0 = pisoY+1;
        }
        jugadorYH = jugador.y0 + jugador.height;;
        if (jugadorX >= bottomX && paredY < jugadorYH) {
            jugador.x0 -= 10;
        }
        jugadorX = jugador.x0 + jugador.width;
        if(jugadorX < bottomX && paredY < jugadorYH){
            jugador.x0 = bottomX - jugador.width-1;
        }
    }
    public boolean limiteParedBottom(int cuadroActual, Cuadrado jugador) {
        int jugadorX;
        int jugadorY;
        int paredX;
        int pisoY;
        boolean retorno = false;
        
        Cuadrado pared = cuadradosBot.get(cuadroActual);
        jugadorX = jugador.x0 + jugador.width;
        jugadorY = jugador.y0 + jugador.height;
        pisoY = pared.y0;
        paredX = pared.x0;
        if (jugadorX >= paredX && pisoY < jugadorY) {
            jugador.x0 -= 10;
            retorno = true;
        }
        jugadorX = jugador.x0 + jugador.width;
        if(jugadorX < paredX && pisoY < jugadorY){
            jugador.x0 = paredX - jugador.width-1;
        }
        
        return retorno;
    }
    public boolean limiteParedTop(int cuadroActual, Cuadrado jugador) {
        int jugadorX;
        int jugadorY;
        int paredX;
        int pisoY;
        boolean retorno = false;
        
        Cuadrado pared = cuadradosTop.get(cuadroActual);
        jugadorX = jugador.x0 + jugador.width;
        jugadorY = jugador.y0;
        pisoY = pared.y0 + pared.height;
        paredX = pared.x0;
        if (jugadorX >= paredX && pisoY > jugadorY) {
            jugador.x0 -= 10;
            retorno = true;
        }
        jugadorX = jugador.x0 + jugador.width;
        if(jugadorX < paredX && pisoY > jugadorY){
            jugador.x0 = paredX - jugador.width-1;
        }
        
        return retorno;
    }
}
