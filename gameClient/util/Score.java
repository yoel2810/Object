package gameClient.util;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/***
 * Shows the time and score
 * @author yoel hartman
 *
 */
public class Score extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel score,time;

	public Score()
	{
		super();
		this.score = new JLabel("score: ");
		this.time = new JLabel("time: ");
		JPanel p = new JPanel();
		score.setFont(new Font(score.getName(),Font.PLAIN,50));
		time.setFont(new Font(score.getName(),Font.PLAIN,50));
		this.setSize(600,150);
		p.add(time);
		p.add(score);
		this.add(p);
	}
	
	/**
	 * Updates the time and score
	 * @param time
	 * @param score
	 */
	public void updateScores(long time, int score)
	{
		this.time.setText("time: "+time+" ");
		this.score.setText("score: "+score+" ");
	}
}
