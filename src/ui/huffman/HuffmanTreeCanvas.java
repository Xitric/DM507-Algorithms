package ui.huffman;

import datastructures.HuffmanTree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasper
 */
public class HuffmanTreeCanvas extends JPanel {

	private static final int nodeRadius = 16;
	private HuffmanTree ht;
	private List<NodeWrapper> nodes;

	public HuffmanTreeCanvas(HuffmanTree ht) {
		super();
		this.ht = ht;
		nodes = new ArrayList<>();

		treeChanged();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (NodeWrapper wrapper : nodes) {
			//Draw node boundary
			g.drawOval(wrapper.centerX - nodeRadius, wrapper.centerY - nodeRadius, nodeRadius * 2, nodeRadius * 2);

			//Draw key value
			String key = null;
			if (wrapper.node.value == null) {
				key = "";
			} else {
				key = wrapper.node.value;
			}
			FontMetrics fm = g.getFontMetrics(g.getFont());

			int fx = fm.stringWidth(key) / 2;
			int fy = fm.getAscent() / 2;
			g.drawString(key, wrapper.centerX - fx, wrapper.centerY + fy);

			//Draw lines to connect to children
			if (wrapper.node.left != null) {
				g.drawLine(wrapper.centerX, wrapper.centerY + nodeRadius, wrapper.leftChildX, wrapper.childY - nodeRadius);
			}
			if (wrapper.node.right != null) {
				g.drawLine(wrapper.centerX, wrapper.centerY + nodeRadius, wrapper.rightChildX, wrapper.childY - nodeRadius);
			}
		}
	}

	public final void treeChanged() {
		nodes.clear();

		int treeHeight = ht.getHeight(ht.root);
		int width = (int) ((Math.pow(2, treeHeight) - 1) * 2 * nodeRadius);
		int height = treeHeight * 4 * nodeRadius + 2 * nodeRadius;

		addNode(width / 2, nodeRadius, ht.root, width / 4);

		setPreferredSize(new Dimension(width, height));
		setMaximumSize(getPreferredSize());
		revalidate();
		repaint();
	}

	private void addNode(int cx, int cy, HuffmanTree.Node node, int xOffset) {
		if (node == null) return;

		//Calculate positions of children
		int leftCx = cx - xOffset;
		int rightCx = cx + xOffset;
		int childY = cy + 4 * nodeRadius;

		nodes.add(new NodeWrapper(cx, cy, node, leftCx, rightCx, childY));

		//Add child nodes recursively
		addNode(leftCx, childY, node.left, xOffset / 2);
		addNode(rightCx, childY, node.right, xOffset / 2);
	}

	private static final class NodeWrapper {
		private int centerX;
		private int centerY;
		private int leftChildX;
		private int rightChildX;
		private int childY;
		private HuffmanTree.Node node;

		public NodeWrapper(int cx, int cy, HuffmanTree.Node node, int lcx, int rcx, int childY) {
			this.centerX = cx;
			this.centerY = cy;
			this.node = node;
			this.leftChildX = lcx;
			this.rightChildX = rcx;
			this.childY = childY;
		}
	}
}
