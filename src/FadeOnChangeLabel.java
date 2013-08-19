import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;

public class FadeOnChangeLabel extends JLabel implements Runnable {
    
	boolean animating = false;
	int fade = 100;
	String newText = null;

	public FadeOnChangeLabel(String s) {
		super(s);
		setOpaque(true);
	}

	public FadeOnChangeLabel() { this(""); }

	public void setText(String s) { if(!s.equals(getText())) animate(s); }

	private void animate(String s) {
		if(!animating && this.getParent()!=null){
			newText = s;
			new Thread(this).start();
		}
	}

	public void run() {
		animating = true;
		try {
			while(fade > 0) {
				repaint();
				fade--;
				Thread.sleep(10);
			}
		} catch (Exception e) {
			System.out.println("FadeOnChangeLabel encountered an error: " + e.getMessage());
		} finally {
			animating = false;
			super.setText(newText);
			fade = 100;
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		Insets i = getInsets();
		g2.setColor(Color.BLACK);
		g2.fillRect(i.left, i.top, getWidth()-i.left-i.right, getHeight()-i.top-i.bottom);

		g2.setColor(Color.WHITE);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Font currentFont = getFont();
		float fontSize = currentFont.getSize2D();
		FontMetrics fm = g2.getFontMetrics(currentFont);

		Composite savedComposite = g2.getComposite();

		// Fade value
		float t = (float)fade/100.0f;

		// Size of the label
		int panelWidth = getWidth();
		int panelHeight = getHeight();

		int x, y;
		int textWidth, textHeight;
		Rectangle2D rect;
		
		if(animating) {
			// New Text Fading In
			rect = fm.getStringBounds(newText, g2);
			textHeight = (int)rect.getHeight();
			textWidth = (int)rect.getWidth();
			x = (panelWidth  - textWidth)  / 2;
			y = (panelHeight - textHeight) / 2  + fm.getAscent() + fm.getDescent();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-t*t));
			g2.drawString(newText, x, y);
		}

		// Current Text, Possibly Fading Out
		rect = fm.getStringBounds(getText(), g2);
		textHeight = (int)rect.getHeight();
		textWidth = (int)rect.getWidth();
		x = (panelWidth  - textWidth)  / 2;
		y = (panelHeight - textHeight) / 2  + fm.getAscent() + fm.getDescent();
		
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-(1-t)*(1-t)));
		g2.drawString(getText(), x, y);

		g2.setComposite(savedComposite);
	}
}
