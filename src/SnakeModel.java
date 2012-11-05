import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;


public class SnakeModel extends Observable
{
	public static final int size = 40;
	private List<Point> snake;
	private Point mela;
	private Point mela2;
	private Point mela3;
	private Random rdm;
	public enum Dir {su,giù,dx,sx};
	private Dir direzione;
	private boolean isPlaying;
	public int punteggio = 0;
	int tempo=0;
	private Point p_tele;
	boolean tele;
	boolean tele2 = false;
	boolean doppio=false;
	int numMela2 = 0;
	
	public SnakeModel()
	{
		snake = new LinkedList<Point>();
		rdm = new Random();
		reset();
	}
	
	public void reset()
	{
		punteggio=0;
		tempo=0;
		snake.clear();
		Point p;
		p= new Point(0, size/2);
		snake.add(p);
		p= new Point(1, size/2);
		snake.add(p);
		direzione = Dir.dx;
		creaMela();
		isPlaying=true;
		tele=false;
	}
	
	public Point[] getSnake()
	{
		Point[] data = new Point[snake.size()];
		return snake.toArray(data);
	}
	
	public Point getMela()
	{
		return new Point(mela.x, mela.y);
	}
	
	public Point getMela2() 
	{
		return new Point(mela2.x, mela2.y);
	}
	
	public Point getMela3() 
	{
		return new Point(mela3.x, mela3.y);
	}
	
	private void creaMela()
	{
		if(doppio)
		{
			doppio=false;
			if(direzione == Dir.sx)
			{
				setDirezione(SnakeModel.Dir.su);
			}
			else if(direzione == Dir.dx)
			{
				setDirezione(SnakeModel.Dir.giù);
			}
			else if(direzione == Dir.giù)
			{
				setDirezione(SnakeModel.Dir.dx);
			}
			else if(direzione == Dir.su)
			{
				setDirezione(SnakeModel.Dir.sx);
			}
		}
		while(true)
		{
			mela = new Point(rdm.nextInt(size), rdm.nextInt(size));
			Iterator<Point> it = snake.iterator();
			boolean done=true;
			while(it.hasNext())
			{
				Point p = it.next();
				if(p.equals(mela))
				{
					done=false;
					break;
				}
			}
			if(done) return;
		}
	}
	
	private void creaMela2()
	{
		while(true)
		{
			punteggio = punteggio + 18;
			mela2 = new Point(rdm.nextInt(size), rdm.nextInt(size));
			Iterator<Point> it = snake.iterator();
			boolean done=true;
			while(it.hasNext())
			{
				Point p = it.next();
				if(p.equals(mela2))
				{
					done=false;
					break;
				}
			}
			if(done) return;
		}
	}
	
	private void creaMela3()
	{
		while(true)
		{
			punteggio = punteggio + 30;
			mela3 = new Point(rdm.nextInt(size), rdm.nextInt(size));
			Iterator<Point> it = snake.iterator();
			boolean done=true;
			while(it.hasNext())
			{
				Point p = it.next();
				if(p.equals(mela3))
				{
					done=false;
					break;
				}
			}
			if(done) return;
		}
	}
	
	public void setDirezione(Dir d)
	{
		direzione=d;
	}
	
	public Dir direzione()
	{
		return direzione;
	}
	
	public void aggiorna()
	{
		tempo= tempo + 50;
		
		if(!isPlaying)
		{
			SnakeController.died();
			return;
		}
		
		
		Point testa;
		Point nuovaTesta = new Point();
	
		if(tele){
			testa=p_tele;
			tele=false;
		}
		else 
		{
			testa=snake.get(snake.size()-1);
		}
		
		if(direzione == Dir.su)
		{
			nuovaTesta.x=testa.x;
			nuovaTesta.y=testa.y-1;
		}
		else if(direzione == Dir.giù)
		{
			nuovaTesta.x=testa.x;
			nuovaTesta.y=testa.y+1;
		}
		else if(direzione == Dir.dx)
		{
			nuovaTesta.x=testa.x+1;
			nuovaTesta.y=testa.y;
		}
		else if(direzione == Dir.sx)
		{
			nuovaTesta.x=testa.x-1;
			nuovaTesta.y=testa.y;
		}
		
		if(nuovaTesta.x<0) nuovaTesta.x=size-1;
		if(nuovaTesta.x>=size) nuovaTesta.x=0;
		if(nuovaTesta.y<0) nuovaTesta.y=size-1;
		if(nuovaTesta.y>=size) nuovaTesta.y=0;
		if(nuovaTesta.equals(mela))
		{
			snake.add(nuovaTesta);
			punteggio = punteggio + Math.round((tempo*25)/10000);
			if(punteggio>20 && punteggio%5==0)
			{
				numMela2++;
				if(numMela2==3)
				{
					creaMela3();
					doppio=true;
					tempo=0;
					numMela2=0;
				}
				else
				{
					creaMela2();
					tele2 = true;
					tempo=0;
				}
			}
			else
			{
				creaMela();
				tempo=0;
			}
		}
		else if(nuovaTesta.equals(mela2))
		{
			p_tele=new Point(rdm.nextInt(size),rdm.nextInt(size));
			tele=true;
			tele2 = false;
			creaMela();
			tempo=0;
		}
		else if(nuovaTesta.equals(mela3))
		{
			punteggio = punteggio +30 +  Math.round((tempo*25)/10000);
			int metsnake=Math.round(snake.size()/3);
			for(int i=1;i<metsnake;i++)
			{
				snake.remove(i);
			}
			creaMela();
			tempo=0;
		}
		else
		{
			Iterator<Point> it = snake.iterator();
			while(it.hasNext())
			{
				if(nuovaTesta.equals(it.next()))
				{
					isPlaying=false;
					SnakeController.died();
					break;
				}
			}
			if(isPlaying)
			{
				snake.add(nuovaTesta);
				snake.remove(0);
			}
		}
		
		if(tempo>9000)
		{
			creaMela();
			tempo=0;
		}
		setChanged();
		notifyObservers();
	}

	public int getPunteggio() 
	{
		return punteggio;
	}
	
	public boolean getPlay()
	{
		return isPlaying;
	}

	
}
