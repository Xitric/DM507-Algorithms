package ui.redblack;

import datastructures.RedBlackTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasper
 */
public class RedBlackTreeCanvas extends JPanel {

	private static final int nodeRadius = 16;
	private RedBlackTree rbt;
	private List<NodeWrapper> nodes;
	private NodeWrapper selected;

	public RedBlackTreeCanvas(RedBlackTree rbt) {
		super();
		this.rbt = rbt;
		nodes = new ArrayList<>();

		treeChanged();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				clicked(mouseEvent.getX(), mouseEvent.getY());
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (selected != null) {
			g.setColor(new Color(255, 103, 0));
			g.fillOval(selected.centerX - (nodeRadius + 4), selected.centerY - (nodeRadius + 4), (nodeRadius + 4) * 2, (nodeRadius + 4) * 2);
		}

		for (NodeWrapper wrapper : nodes) {
			if (wrapper.node.color == RedBlackTree.Color.Black) {
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.RED);
			}

			//Draw node boundary
			g.fillOval(wrapper.centerX - nodeRadius, wrapper.centerY - nodeRadius, nodeRadius * 2, nodeRadius * 2);

			//Draw key value
			g.setColor(Color.WHITE);
			String key = String.valueOf(wrapper.node.key);
			FontMetrics fm = g.getFontMetrics(g.getFont());

			int fx = fm.stringWidth(key) / 2;
			int fy = fm.getAscent() / 2;
			g.drawString(key, wrapper.centerX - fx, wrapper.centerY + fy);

			//Draw lines to connect to children
			g.setColor(Color.BLACK);
			g.drawLine(wrapper.centerX, wrapper.centerY + nodeRadius, wrapper.leftChildX, wrapper.childY - nodeRadius);
			g.drawLine(wrapper.centerX, wrapper.centerY + nodeRadius, wrapper.rightChildX, wrapper.childY - nodeRadius);
		}
	}

	public final void treeChanged() {
		nodes.clear();

		int treeHeight = rbt.getMaxHeight(rbt.root);
		int width = (int) ((Math.pow(2, treeHeight) - 1) * 2 * nodeRadius);
		int height = treeHeight * 4 * nodeRadius + 2 * nodeRadius;

		addNode(width / 2, nodeRadius, rbt.root, width / 4);

		if (selected != null) {
			RedBlackTree.Node toSelect = rbt.treeSearch(rbt.root, selected.node.key);
			selected = null;
			if (toSelect != null) {
				for (NodeWrapper node: nodes) {
					if (node.node.key == toSelect.key){
						selected = node;
						break;
					}
				}
			}
		}

		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());
		revalidate();
		repaint();
	}

	private void addNode(int cx, int cy, RedBlackTree.Node node, int xOffset) {
		if (node == RedBlackTree.NIL) return;

		//Calculate positions of children
		int leftCx = cx - xOffset;
		int rightCx = cx + xOffset;
		int childY = cy + 4 * nodeRadius;

		nodes.add(new NodeWrapper(cx, cy, node, leftCx, rightCx, childY));

		//Add child nodes recursively
		addNode(leftCx, childY, node.left, xOffset / 2);
		addNode(rightCx, childY, node.right, xOffset / 2);
	}

	private NodeWrapper getNode(int x, int y) {
		for (NodeWrapper wrapper : nodes) {
			if (Math.hypot(x - wrapper.centerX, y - wrapper.centerY) < nodeRadius) {
				return wrapper;
			}
		}

		return null;
	}

	private void clicked(int x, int y) {
		selected = getNode(x, y);
		repaint();
	}

	public RedBlackTree.Node getSelected() {
		if (selected == null) return null;
		return selected.node;
	}

	private static final class NodeWrapper {
		private int centerX;
		private int centerY;
		private int leftChildX;
		private int rightChildX;
		private int childY;
		private RedBlackTree.Node node;

		public NodeWrapper(int cx, int cy, RedBlackTree.Node node, int lcx, int rcx, int childY) {
			this.centerX = cx;
			this.centerY = cy;
			this.node = node;
			this.leftChildX = lcx;
			this.rightChildX = rcx;
			this.childY = childY;
		}
	}
}
