package gameClient.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/***
 * A class that draw the graph
 * 
 * @author yoel2
 *
 */

public class GraphPainter extends JFrame {
	private static int bottom = 100, top = 10, delta = 10;
	private static final long serialVersionUID = 1L;
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int width = screenSize.width;
	private static int height = screenSize.height;
	private JPanel panel;
	private int radius = 10;
	// private int agentX, agentY;
	private Point3D vertices[], pokemons[], agents[];
	private Point3D[] edges;
	private boolean pokemonsLife[];

	/**
	 * Builds the object O(|E| + |V| + |pokemons| + |agents|)
	 * 
	 * @param edges    - the edges
	 * @param vertices - the nodes
	 * @param pokemons - the pokemons
	 * @param agents   - the agents
	 */
	public GraphPainter(Point3D[] edges, Point3D[] vertices, Point3D[] pokemons, Point3D[] agents) {
		super();
		// System.out.println("w = "+width+", h = "+height);
		bottom = 100;
		top = 10;

		JLabel score = new JLabel("score: ");
		JLabel time = new JLabel("time: ");
		JPanel p = new JPanel();

		p.add(time);
		p.add(score);
		this.add(p);
		p.setBackground(Color.BLUE);
		this.panel = p;

		this.setBounds(top, top, width - bottom, height - bottom);

		// this.setSize(width - bottom,height - bottom);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("MyGraph");
		// this.setSize(420,420);
		this.vertices = new Point3D[vertices.length];
		this.agents = new Point3D[agents.length];
		for (int i = 0; i < vertices.length; i++) {
			this.vertices[i] = new Point3D(vertices[i]);
		}
		for (int i = 0; i < agents.length; i++) {
			this.agents[i] = new Point3D(agents[i]);
		}

		this.edges = new Point3D[edges.length];
		for (int i = 0; i < edges.length; i++) {
			this.edges[i] = new Point3D(edges[i]);
		}
		// agentX = (int) vertices[0].x();
		// agentY = (int) vertices[0].y();

		this.pokemons = new Point3D[pokemons.length];
		this.pokemonsLife = new boolean[pokemons.length];
		for (int i = 0; i < pokemons.length; i++) {
			this.pokemons[i] = new Point3D(pokemons[i]);
			this.pokemonsLife[i] = true;
		}
		// this.setVisible(true);
	}

	/**
	 * paints the graph
	 */
	public void paint(Graphics g) {

		g.clearRect(top, bottom, width, height);

		for (int i = 0; i < vertices.length; i++) {
			int x1 = (int) vertices[i].x(), y1 = (int) vertices[i].y();
			// int x2 = (int) vertices[i].x(), y2 = (int) vertices[i].y();
			g.setColor(Color.BLUE);

			g.fillOval(x1, y1, radius, radius);// vertex
			g.setColor(Color.BLACK);

		}
		for (int i = 0; i < this.edges.length; i += 2) {

			g.drawLine((int) this.edges[i].x(), (int) this.edges[i].y(), (int) this.edges[i + 1].x(),
					(int) this.edges[i + 1].y());// edge
		}

		// g.setColor(Color.BLUE);
		// int x1 = (int) vertices[vertices.length - 1].x(), y1 = (int)
		// vertices[vertices.length - 1].y();
		// g.fillOval(x1, y1, radius, radius);

		g.setColor(Color.GREEN);
		for (int i = 0; i < pokemons.length; i++) {
			if (pokemonsLife[i])
				g.fillOval((int) pokemons[i].x(), (int) pokemons[i].y(), radius, radius);
		}
		g.setColor(Color.RED);
		for (int i = 0; i < agents.length; i++) {
			g.fillOval((int) this.agents[i].x(), (int) this.agents[i].y(), radius, radius);
		}
	}

	/**
	 * Updates the graph P(|agents| + |pokemons|)
	 * 
	 * @param agents   - the agents
	 * @param pokemons - the pokemons
	 */
	public void updateGraph(Point3D[] agents, Point3D[] pokemons) {

		this.agents = new Point3D[agents.length];
		for (int i = 0; i < agents.length; i++) {
			this.agents[i] = new Point3D(agents[i]);
		}

		this.pokemons = new Point3D[pokemons.length];
		// this.pokemonsLife = new boolean[pokemons.length];
		for (int i = 0; i < pokemons.length; i++) {
			this.pokemons[i] = new Point3D(pokemons[i]);
			// this.pokemonsLife[i] = true;
		}
		repaint();

		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates the graph O(|V|*distance)
	 */
	public void createGraph() {
		for (int i = 1; i < vertices.length; i++) {
			Line3D line = new Line3D(vertices[i - 1], vertices[i]);
			double dist = vertices[i - 1].distance(vertices[i]);
			double step = dist + 1;
			if (delta != 0)
				step = dist / delta; // step on the edge [vertices[i-1], vertices[i]]
			// System.out.println("dist = "+dist+", step = "+step);
			for (double q = 0; q <= dist; q = q + step) {// go though the edge
				Point3D agent = line.getPointOnLine(q);
				// agentX = (int) agent.x();
				// agentY = (int) agent.y();
				for (int p = 0; p < pokemons.length; p++) {
					if (pokemonsLife[p] && (pokemons[p].distance(agent) <= step)) {
						pokemonsLife[p] = false;
					}
				}
				repaint();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// System.out.println("agentX = "+agentX+", agentY="+agentY);
			}
		}
	}

}
