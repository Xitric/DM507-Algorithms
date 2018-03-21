package ui.redblack;

import datastructures.RedBlackTree;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @author Kasper
 */
public class RedBlackTreeUI extends JFrame {

	private RedBlackTree rbt;
	private RedBlackTreeCanvas canvas;
	private JTextField keyField;
	private JRadioButton redRadio;
	private JRadioButton blackRadio;
	private JTextArea informationTextArea;

	public RedBlackTreeUI() {
		super("RedBlack Tree UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rbt = new RedBlackTree();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(setupCanvas());
		mainPanel.add(setupControlPanel());

		getContentPane().add(mainPanel, BorderLayout.CENTER);

		JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.PAGE_AXIS));
		getContentPane().add(informationPanel, BorderLayout.LINE_END);

		informationTextArea = new JTextArea();
		informationTextArea.setMargin(new Insets(8, 8, 8, 8));
		informationPanel.add(informationTextArea);

		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(actionEvent -> refresh());
		informationPanel.add(refreshButton);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel setupCanvas() {
		canvas = new RedBlackTreeCanvas(rbt);
		return canvas;
	}

	private JPanel setupControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

		//Insertion
		JPanel insertPanel = new JPanel();
		insertPanel.setLayout(new FlowLayout());
		keyField = new JTextField("Enter key");
		keyField.setPreferredSize(new Dimension(120, keyField.getPreferredSize().height));
		JButton insertButton = new JButton("Insert");
		insertButton.addActionListener(actionEvent -> insertAction());
		insertPanel.add(keyField);
		insertPanel.add(insertButton);

		//Deletion
		JPanel deletePanel = new JPanel();
		deletePanel.setLayout(new FlowLayout());
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(actionEvent -> deleteAction());
		deletePanel.add(deleteButton);

		//Force insertion
		JPanel forcePanel = new JPanel();
		forcePanel.setLayout(new FlowLayout());
		redRadio = new JRadioButton("Red", true);
		blackRadio = new JRadioButton("Black");
		ButtonGroup colorGroup = new ButtonGroup();
		colorGroup.add(redRadio);
		colorGroup.add(blackRadio);
		JButton leftButton = new JButton("Insert left");
		leftButton.addActionListener(actionEvent -> forceInsert(true));
		JButton rightButton = new JButton("Insert right");
		rightButton.addActionListener(actionEvent -> forceInsert(false));
		forcePanel.add(redRadio);
		forcePanel.add(blackRadio);
		forcePanel.add(leftButton);
		forcePanel.add(rightButton);

		controlPanel.add(insertPanel);
		controlPanel.add(deletePanel);
		controlPanel.add(forcePanel);

		return controlPanel;
	}

	private void refresh() {
		canvas.treeChanged();
		RedBlackTree.Node selected = canvas.getSelected() == null ? rbt.root : canvas.getSelected();

		String info = "Black height: " + rbt.getBlackHeight(selected) + "\n" +
				"Minimum: " + rbt.treeMinimum(selected).key + "\n" +
				"Maximum: " + rbt.treeMaximum(selected).key + "\n" +
				"Tree walk:\n" + Arrays.toString(rbt.inorderTreeWalk(selected));

		informationTextArea.setText(info);
		pack();
	}

	private void insertAction() {
		try {
			int key = Integer.parseInt(keyField.getText());
			rbt.insert(key);

			refresh();
		} catch (NumberFormatException e) {
			//Ignore
		}
	}

	private void forceInsert(boolean left) {
		if (canvas.getSelected() == null) return;

		int key;
		try {
			key = Integer.parseInt(keyField.getText());
		} catch (NumberFormatException e) {
			return;
		}

		RedBlackTree.Node node = new RedBlackTree.Node(key);
		if (redRadio.isSelected()) {
			node.color = RedBlackTree.Color.Red;
		} else {
			node.color = RedBlackTree.Color.Black;
		}

		node.parent = canvas.getSelected();
		node.left = RedBlackTree.NIL;
		node.right = RedBlackTree.NIL;
		if (left) {
			canvas.getSelected().left = node;
		} else {
			canvas.getSelected().right = node;
		}

		refresh();
	}

	private void deleteAction() {
		if (canvas.getSelected() != null) {
			rbt.delete(canvas.getSelected());

			refresh();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(RedBlackTreeUI::new);
	}
}
