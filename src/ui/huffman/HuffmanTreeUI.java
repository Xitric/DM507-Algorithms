package ui.huffman;

import datastructures.HuffmanTree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Kasper
 */
public class HuffmanTreeUI extends JFrame {

	private HuffmanTree ht;
	private HuffmanTreeCanvas canvas;
	private JTextField keyField;
	private JTextArea informationTextArea;
	private JPanel mainPanel;

	public HuffmanTreeUI() {
		super("Huffman Tree UI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
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
		canvas = new HuffmanTreeCanvas(ht);
		return canvas;
	}

	private JPanel setupControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));

		//Insertion
		JPanel insertPanel = new JPanel();
		insertPanel.setLayout(new FlowLayout());
		keyField = new JTextField("Enter frequencies");
		keyField.setPreferredSize(new Dimension(250, keyField.getPreferredSize().height));
		JButton insertButton = new JButton("Create");
		insertButton.addActionListener(actionEvent -> insertAction());
		insertPanel.add(keyField);
		insertPanel.add(insertButton);
		insertPanel.add(new JLabel("For instance a:10, b:8, c:21, ..."));

		controlPanel.add(insertPanel);

		return controlPanel;
	}

	private void refresh() {
		canvas.treeChanged();

		StringBuilder info = new StringBuilder();
		String[] codes = ht.orderedTraversal();
		for (int i = 0; i < codes.length; i++) {
			info.append(ht.elements.get(i));
			info.append(": ");
			info.append(codes[i]);
			info.append("\n");
		}

		informationTextArea.setText(info.toString());
		pack();
	}

	private void insertAction() {
		try {
			java.util.List<Integer> frequencies = new ArrayList<>();
			java.util.List<String> elements = new ArrayList<>();

			String[] elementArray = keyField.getText().split(",");
			for (String elm : elementArray) {
				String[] elmA = elm.split(":");
				frequencies.add(Integer.parseInt(elmA[1]));
				elements.add(elmA[0]);
			}

			ht = HuffmanTree.createHuffmanTree(frequencies.toArray(new Integer[0]), elements.toArray(new String[0]));

			if (mainPanel.getComponentCount() == 2) {
				mainPanel.remove(0);
			}
			mainPanel.add(setupCanvas(), 0);

			refresh();
		} catch (NumberFormatException e) {
			//Ignore
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(HuffmanTreeUI::new);
	}
}
