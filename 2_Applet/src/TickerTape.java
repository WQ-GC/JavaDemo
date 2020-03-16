import java.applet.*;
import java.awt.*;

public class TickerTape extends Applet implements Runnable {
  //Declare variables
	String inputText;
	String animSpeedString;
	Color color = new Color(255, 255, 255);
	int xpos;
	int fontLength, fontHeight;
	int animSpeed;
	Font font;
	Thread ttapeThread = null;
	Image im;
	Graphics osGraphics;
	boolean suspended = false;
	
	public void init() {
		inputText = getParameter("TEXT");
		animSpeedString = getParameter("SPEED");
		animSpeed = Integer.parseInt(animSpeedString);
		im = createImage(size().width, size().height);
		osGraphics = im.getGraphics();
		xpos = size().width;
		fontHeight = 4 * size().height / 5;
		font = new Font("Helvetica", 1, fontHeight);	
	}
	
	public void paint(Graphics g) {
		paintText(osGraphics);
		g.drawImage(im,	0, 0, null);
	}
	
	public void paintText(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0,  0,  size().width, size().height);
		g.clipRect(0,  0,  size().width, size().height);
		g.setFont(font);
		g.setColor(color);
		FontMetrics fmetrics = g.getFontMetrics();
		fontLength = fmetrics.stringWidth(inputText);
		fontHeight = fmetrics.getHeight();
		g.drawString(inputText, xpos, size().height - fontHeight / 4);		
	}
	
	//Start Applet as a Thread
	public void start() {
		if(ttapeThread == null) {
			ttapeThread = new Thread(this);
			ttapeThread.start();
		}
	}
	
	//Animate coord for drawing text
	public void setcoord() {
		xpos = xpos - animSpeed;
		if(xpos <- fontLength) {
			xpos = size().width;
		}
	}
	
	//Change Coord and Repaint
	public void run() {
		while(ttapeThread != null) {
			try { Thread.sleep(50);
			}	catch(InterruptedException e) {}
			setcoord();
			repaint();
		}		
	}
	
	//Repaint when buffer is updated
	public void update(Graphics g) {
		paint(g);
	}
	
	//Handle Mouse Clicks
	public boolean handleEvent(Event evt) {
		if(evt.id == Event.MOUSE_DOWN) {
			if(suspended) {
				ttapeThread.resume();
			} else {
				ttapeThread.suspend();
			}
			suspended = !suspended;
		}
		return true;
	}
	
	//Stop Thread, then Clean up before closing
	public void stop() {
		if(ttapeThread != null) {
			ttapeThread.stop();
		}
		ttapeThread = null;
	}
		
}//End TickerTape























