import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SnakeController implements ActionListener, KeyListener
{
		private static SnakeModel sm;
		private SnakeView sv;
		private static JFrame f;
		public Timer timer;
		public boolean checkstart;
		
		public SnakeController()
		{
			timer=new Timer(50,this);
			timer.setRepeats(true);
			sm = new SnakeModel();
			sv = new SnakeView(sm, this);
			f = new JFrame("Snake");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setMenuBar(sv.bar);
			f.add(sv);
			f.pack();
			f.setVisible(true);
		}
	

	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			String cmd=ae.getActionCommand();
			if(cmd.equals("Nuova Partita"))
			{
				sm.reset();
				timer.start();
				checkstart=true;
			}
			else if(cmd.equals("Esci"))
				System.exit(0);
		}
		catch(NullPointerException npe)
		{	
			if(sm.getPlay()==false){
				sm.reset();
				timer.stop();
				checkstart=false;
			}
			else
			{
				sm.aggiorna();
			}
		}
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				new SnakeController();
			}
		});
	}


	public void keyPressed(KeyEvent ke) 
	{
		if(checkstart)
		{
			if((ke.getKeyCode()==KeyEvent.VK_UP) && sm.direzione()!=SnakeModel.Dir.giù)
			{
				sm.setDirezione(SnakeModel.Dir.su);
			}
			else if((ke.getKeyCode()==KeyEvent.VK_DOWN) && sm.direzione()!=SnakeModel.Dir.su)
			{
				sm.setDirezione(SnakeModel.Dir.giù);
			}
			else if((ke.getKeyCode()==KeyEvent.VK_LEFT) && sm.direzione()!=SnakeModel.Dir.dx)
			{
				sm.setDirezione(SnakeModel.Dir.sx);
			}
			else if((ke.getKeyCode()==KeyEvent.VK_RIGHT) && sm.direzione()!=SnakeModel.Dir.sx)
			{
				sm.setDirezione(SnakeModel.Dir.dx);
			}
		}
	}

	public void setStart(boolean z)
	{
		checkstart = z;
	}
	
	public boolean getStart()
	{
		return checkstart;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void died() 
	{

			JOptionPane.showMessageDialog(f," Gioco finito. ", " Message ",JOptionPane.WARNING_MESSAGE);

	}
}
