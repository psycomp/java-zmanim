import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.File;
import javax.swing.*;

public class Demo {

	static SimpleDateFormat formatter = new SimpleDateFormat("EEEE MMMM d, h:mm:ss a");
	static JewishDateFormat jdf = new JewishDateFormat();

	public static void main(String[] args) {

		KeyListener kl = new KeyAdapter() { public void keyPressed(KeyEvent evt) { System.exit(0); } };

		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/SILEOT.ttf")));
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/Cardo104s.ttf")));
			// for(String name : ge.getAvailableFontFamilyNames()) { System.err.println(name); }
		} catch (Exception e) {}

		JFrame frame = new JFrame("Demo");
		frame.setSize(1080, 1080);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(kl);
		// frame.setUndecorated(true);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(1,2));

		JPanel middlePanelLeft = new JPanel();
		middlePanelLeft.setBackground(Color.BLACK);
		middlePanelLeft.setForeground(Color.WHITE);
		middlePanelLeft.setLayout(new GridLayout(2,1));
		middlePanelLeft.add(createLabel("Daf Yomi"));
		JLabel dyLabel = createFadingLabel();
		dyLabel.setFont(new Font("Cardo", Font.PLAIN, 50));
		middlePanelLeft.add(dyLabel);
		middlePanel.add(middlePanelLeft);

		JPanel middlePanelRight = new JPanel();
		middlePanelRight.setBackground(Color.BLACK);
		middlePanelRight.setForeground(Color.WHITE);
		middlePanelRight.setLayout(new GridLayout(2,1));
		middlePanelRight.add(createLabel("X"));
		middlePanelRight.add(createLabel("X"));
		middlePanel.add(middlePanelRight);

		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setForeground(Color.WHITE);
		mainPanel.setLayout(new GridLayout(5,1));

		JLabel label0 = createLabel("<html><center><table><tr><td>Agudas Yisroel of Madison</td></tr><tr><td>Zichron Chaim Tzvi</td></tr></table></center></html>");
		label0.setFont(label0.getFont().deriveFont(30.0f));
		mainPanel.add(label0);

		FadeOnChangeLabel label1 = createFadingLabel();
		label1.setBackground(Color.WHITE);
		label1.setForeground(Color.BLACK);
		mainPanel.add(label1);

		JLabel label2 = createLabel("Zmanim Calendar");
		label2.setFont(label2.getFont().deriveFont(96.0f));
		mainPanel.add(label2);

		mainPanel.add(middlePanel);

		FadeOnChangeLabel label3 = createFadingLabel();
		mainPanel.add(label3);

		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);

		while(true) {
			Date d = new Date();
			JewishDate jd = new JewishDate();
			DafYomi dy = new DafYomi();

			label1.setText(jdf.format(jd, "%h, %g %e, %b"));
			label3.setText(formatter.format(d));
			dyLabel.setText(dy.getDaf(jd, DafYomi.HEBREW));

			try { Thread.sleep(500); } catch (Exception e) {}
		}
    }

	static JLabel createLabel(String s) {
		JLabel label = new JLabel(s);
		label.setBackground(Color.BLACK);
		label.setForeground(Color.WHITE);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Cardo", Font.PLAIN, 72));
		return(label);
	}

	static FadeOnChangeLabel createFadingLabel() {
		FadeOnChangeLabel label = new FadeOnChangeLabel("");
		label.setBackground(Color.BLACK);
		label.setForeground(Color.WHITE);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Cardo", Font.PLAIN, 72));
		return(label);
	}
}

