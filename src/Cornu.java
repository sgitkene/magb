//  -------------   JOGL 2D Beispiel-Programm (Dreieck) -------------------

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Cornu
       implements WindowListener, GLEventListener
{

    //  ---------  globale Daten  ---------------------------

    String windowTitle = "JOGL-Application";
    int windowWidth = 800;
    int windowHeight = 600;
    String vShader = MyShaders.vShader0;                 // Vertex-Shader
    String fShader = MyShaders.fShader0;                 // Fragment-Shader
    Frame frame;
    GLCanvas canvas;                                     // OpenGL Window
    int programId;                                       // OpenGL-Id
    MyGLBase1 mygl;                                      // Hilfsfunktionen
    int maxVerts = 2048;                                 // max. Anzahl Vertices im Vertex-Array



    //  ---------  Methoden  --------------------------------

    public Cornu()                                   // Konstruktor
    { createFrame();
    }


    void createFrame()                                    // Fenster erzeugen
    {  Frame f = new Frame(windowTitle);
       f.setSize(windowWidth, windowHeight);
       f.addWindowListener(this);
       GLProfile glp = GLProfile.get(GLProfile.GL3);
       GLCapabilities glCaps = new GLCapabilities(glp);
       canvas = new GLCanvas(glCaps);
       canvas.addGLEventListener(this);
       f.add(canvas);
       f.setVisible(true);
   };


    public void zeichneStrecke(GL3 gl, float x1, float y1, float z1,
                                 float x2, float y2, float z2)
    {  mygl.rewindBuffer(gl);
       mygl.putVertex(x1,y1,z1);
       mygl.putVertex(x2,y2,z2);
       mygl.copyBuffer(gl);
       mygl.drawArrays(gl, GL3.GL_LINES);
    }


    public void zeichneDreieck(GL3 gl, float x1, float y1, float z1,
                                 float x2, float y2, float z2,
                                 float x3, float y3, float z3)
    {  mygl.rewindBuffer(gl);
       mygl.putVertex(x1,y1,z1);
       mygl.putVertex(x2,y2,z2);
       mygl.putVertex(x3,y3,z3);
       mygl.copyBuffer(gl);
       mygl.drawArrays(gl, GL3.GL_TRIANGLES);
    }


    //  ----------  OpenGL-Events   ---------------------------

    @Override
    public void init(GLAutoDrawable drawable)             //  Initialisierung
    {  GL3 gl = drawable.getGL().getGL3();
       System.out.println("OpenGl Version: " + gl.glGetString(gl.GL_VERSION));
       System.out.println("Shading Language: " + gl.glGetString(gl.GL_SHADING_LANGUAGE_VERSION));
       System.out.println();
       programId = MyShaders.initShaders(gl,vShader,fShader);
       mygl = new MyGLBase1(gl, programId, maxVerts);
       gl.glClearColor(0,0,1,1);                          // Hintergrundfarbe
    }


    @Override
    public void display(GLAutoDrawable drawable)
     { GL3 gl = drawable.getGL().getGL3();
       gl.glClear(GL3.GL_COLOR_BUFFER_BIT);      // Bildschirm loeschen
       mygl.setColor(0.7f, 0.7f, 0.7f);
       zeichneStrecke(gl,-1,0,0, 1,0,0);               // x-Achse
       zeichneStrecke(gl,0,-1,0, 0,1,0);               // y-Achse
       mygl.setColor(1,0,0);
       zeichneDreieck(gl,-0.5f,0,0, 0.5f,0,0,  0,0.8f,0);
     }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y,
                        int width, int height)
    {  GL3 gl = drawable.getGL().getGL3();
       // Set the viewport to be the entire window
       gl.glViewport(0, 0, width, height);
    }


    @Override
    public void dispose(GLAutoDrawable drawable)  { }                  // not needed


    //  -----------  main-Methode  ---------------------------

    public static void main(String[] args)
    { new Cornu();
    }

    //  ---------  Window-Events  --------------------

    public void windowClosing(WindowEvent e)
    {   System.out.println("closing window");
        System.exit(0);
    }
    public void windowActivated(WindowEvent e) {  }
    public void windowClosed(WindowEvent e) {  }
    public void windowDeactivated(WindowEvent e) {  }
    public void windowDeiconified(WindowEvent e) {  }
    public void windowIconified(WindowEvent e) {  }
    public void windowOpened(WindowEvent e) {  }

}