import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class SnakeView extends JPanel implements Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1888718860934768403L;
	private SnakeModel sm;
	private SnakeController sc;
	MenuBar bar;
	Image mela, back;
	
	public SnakeView(SnakeModel sm, SnakeController sc)
	{
		bar=new MenuBar();
		Menu menu=new Menu("Gioco");
		menu.add(new MenuItem("Nuova Partita"));
		menu.addActionListener(sc);
		menu.add(new MenuItem("Esci"));
		menu.addActionListener(sc);
		bar.add(menu);
		this.sm=sm;
		this.sc=sc;
		setLayout(null);
		sm.addObserver(this);
		setPreferredSize(new Dimension(600, 600));
		addKeyListener(this.sc);
		setFocusable(true);
		
		//Carico immagini
		
		try {
			back = ImageIO.read(new File("img/back.JPG"));
		} catch (IOException e) 
		{
			
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(back, 0, 0, null);
		Point p;
		boolean c = true;
		boolean k = true;
		
		if(sm.tele2)
		{
			p = sm.getMela2();
			c = false;
		}
		else if(sm.doppio)
		{
			p = sm.getMela3();
			k = false;
		}
		else
		{
			p = sm.getMela();
		}
		
		double sx = (double) getWidth()/SnakeModel.size;
		double sy = (double) getHeight()/SnakeModel.size;
		AffineTransform at = AffineTransform.getScaleInstance(sx, sy);
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(at);
		Shape s = new Ellipse2D.Double(p.x, p.y,0.5,0.5);
		if(!c)
		{
			g2.setPaint(Color.white.darker());
			g2.fill(s);
			g2.setPaint(Color.white.brighter());
			g2.draw(s);
		}
		else if(!k)
		{
			g2.setPaint(Color.gray.darker());
			g2.fill(s);
			g2.setPaint(Color.gray.brighter());
			g2.draw(s);
		}
		else
		{
			g2.setPaint(Color.red.darker());
			g2.fill(s);
			g2.setPaint(Color.red.brighter());
			g2.draw(s);
		}
		Point[] body = sm.getSnake();
		for(int i=0; i<body.length; i++)
		{
			if(i==body.length-1)
			{
				Shape s1 = new Rectangle2D.Double(body[i].x, body[i].y, 0.5, 0.5);
				g2.setPaint(Color.red.brighter());
				g2.fill(s1);
				g2.setPaint(Color.RED.darker());
				g2.draw(s1);
			}
			else
			{
				Shape s1 = new Rectangle2D.Double(body[i].x, body[i].y, 0.5, 0.5);
				g2.setPaint(Color.green.darker());
				g2.fill(s1);
				g2.setPaint(Color.green.brighter());
				g2.draw(s1);
			}
		}
		
		g2.setFont(new Font("Arial",Font.ITALIC,4));
		g2.setColor(Color.DARK_GRAY);
		g2.setTransform(at);
		if(sc.checkstart)
		{
			g2.drawString(Integer.toString(sm.punteggio),30, 37);
		}
	}
	public void update(Observable o, Object data) 
	{
		repaint();
	}
}
