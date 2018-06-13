package ui.binary;

import datastructures.BinarySearchTree;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @author Kasper
 */
public class BinarySearchTreeUI extends JFrame {

	private BinarySearchTree bst;
	private BinarySearchTreeCanvas canvas;
	private JTextField keyField;
	private JTextArea informationTextArea;

	public BinarySearchTreeUI() {
		super("Binary Search Tree UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bst = new BinarySearchTree();

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
		canvas = new BinarySearchTreeCanvas(bst);
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
		JButton leftButton = new JButton("Insert left");
		leftButton.addActionListener(actionEvent -> forceInsert(true));
		JButton rightButton = new JButton("Insert right");
		rightButton.addActionListener(actionEvent -> forceInsert(false));
		forcePanel.add(leftButton);
		forcePanel.add(rightButton);

		controlPanel.add(insertPanel);
		controlPanel.add(deletePanel);
		controlPanel.add(forcePanel);

		return controlPanel;
	}

	private void refresh() {
		canvas.treeChanged();
		BinarySearchTree.Node selected = canvas.getSelected() == null ? bst.root : canvas.getSelected();

		String info = "Minimum: " + bst.treeMinimum(selected).key + "\n" +
				"Maximum: " + bst.treeMaximum(selected).key + "\n" +
				"Tree walk:\n" + Arrays.toString(bst.orderedTraversal());

		informationTextArea.setText(info);
		pack();
	}

	private void insertAction() {
		try {
			int key = Integer.parseInt(keyField.getText());
			bst.insert(key);

			refresh();
			keyField.setText("");
			keyField.requestFocus();
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

		BinarySearchTree.Node node = new BinarySearchTree.Node();
		node.key = key;
		node.parent = canvas.getSelected();
		node.left = null;
		node.right = null;

		if (left) {
			canvas.getSelected().left = node;
		} else {
			canvas.getSelected().right = node;
		}

		bst.size++;

		refresh();
	}

	private void deleteAction() {
		if (canvas.getSelected() != null) {
			bst.delete(canvas.getSelected());

			refresh();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(BinarySearchTreeUI::new);
	}
}
