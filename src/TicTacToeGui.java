import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class TicTacToeGui implements Runnable{

	JMenuBar firstHill;
	
	// Constructors up top, the rest should be sorted alphabetically by method name
	public TicTacToeGui(){
		firstHill = new JMenuBar();
	}
	
	public static void main(String[] args){
		TicTacToeGui stephen = new TicTacToeGui();
		javax.swing.SwingUtilities.invokeLater(stephen);
		
	}
	
	public void makeFileMenu(){
		
	}
	
	public void run(){
		JFrame chase = new JFrame();
		chase.setSize(640,480);
		chase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		makeFileMenu();
		chase.setJMenuBar(firstHill);
		
		
		chase.setVisible(true);
	}
}
