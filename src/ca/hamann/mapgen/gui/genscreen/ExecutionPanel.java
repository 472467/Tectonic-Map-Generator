package ca.hamann.mapgen.gui.genscreen;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import ca.hamann.mapgen.gui.genscreen.mapactions.AddRiversAction;
import ca.hamann.mapgen.gui.genscreen.mapactions.DriftAction;
import ca.hamann.mapgen.gui.genscreen.mapactions.ErosionAction;
import ca.hamann.mapgen.gui.genscreen.mapactions.RotateAction;

public class ExecutionPanel extends JPanel {
	private GeneratorScreen screen;

	private List<JComponent> activeComponents = new ArrayList<JComponent>();

	private JTextField iterationsInput;

	public ExecutionPanel(GeneratorScreen screen) {
		this.screen = screen;
		createIterationsInput();
		add(createIteratedOperationsPanel());
		add(createRiversPanel());
		add(createRotationsPanel());
	}

	private JPanel createIteratedOperationsPanel() {
		JPanel iteratedOperations = new JPanel();
		iteratedOperations.setBorder(new EtchedBorder());

		iteratedOperations.add(new JLabel("Iterated Actions"));
		iteratedOperations.add(createdIteratedOperationsButtons());

		iteratedOperations.add(createIterationsInputPanel());
		return iteratedOperations;
	}

	private void createIterationsInput() {
		iterationsInput = initIterationsInput();
		activeComponents.add(iterationsInput);
	}

	private JPanel createIterationsInputPanel() {
		JPanel iterationsInputPanel = new JPanel();
		iterationsInputPanel.setBorder(new EtchedBorder());
		iterationsInputPanel.add(new JLabel("Iterations"));
		iterationsInputPanel.add(iterationsInput);
		return iterationsInputPanel;
	}

	private JPanel createdIteratedOperationsButtons() {
		JPanel iteratedOperationsButtons = new JPanel();

		iteratedOperationsButtons.add(initDriftButton());
		iteratedOperationsButtons.add(initErosionButton());
		return iteratedOperationsButtons;
	}

	private JPanel createRiversPanel() {
		JPanel rivers = new JPanel();
		rivers.setBorder(new EtchedBorder());
		rivers.add(new JLabel("Rivers"));
		rivers.add(initAddRiversButton());
		return rivers;
	}

	private JPanel createRotationsPanel() {
		JPanel rotations = new JPanel();
		rotations.setBorder(new EtchedBorder());
		rotations.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 1;
		rotations.add(initRotateWestButton(), constraints);
		constraints.gridx = 1;
		rotations.add(initRotateEastButton(), constraints);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		rotations.add(new JLabel("Rotate"), constraints);
		return rotations;
	}

	private JTextField initIterationsInput() {
		JTextField iterationsInput = new JTextField(6);
		iterationsInput.setText("1");
		iterationsInput.setHorizontalAlignment(JTextField.RIGHT);
		activeComponents.add(iterationsInput);
		return iterationsInput;
	}

	private JButton initDriftButton() {
		JButton drift = new JButton("Drift");
		drift.addActionListener(new DriftAction(screen));
		activeComponents.add(drift);
		return drift;
	}

	private JButton initErosionButton() {
		JButton erosion = new JButton("Erosion");

		erosion.addActionListener(new ErosionAction(screen));
		activeComponents.add(erosion);
		return erosion;
	}

	private JButton initRotateEastButton() {
		JButton rotateEast = new JButton("East");

		rotateEast.setMnemonic(KeyEvent.VK_PERIOD);

		rotateEast.addActionListener(new RotateAction(screen, true));
		activeComponents.add(rotateEast);
		return rotateEast;
	}

	private JButton initRotateWestButton() {
		JButton rotateWest = new JButton("West");

		rotateWest.setMnemonic(KeyEvent.VK_COMMA);

		rotateWest.addActionListener(new RotateAction(screen, false));

		activeComponents.add(rotateWest);
		return rotateWest;
	}

	private JButton initAddRiversButton() {
		JButton addRivers = new JButton("Add Rivers");

		addRivers.addActionListener(new AddRiversAction(screen));

		activeComponents.add(addRivers);
		return addRivers;
	}

	public void setEnableControls(boolean enabled) {
		for (JComponent c : activeComponents) {
			c.setEnabled(enabled);
		}
	}

	public int getIterations() {
		int parseInt;
		try {
			parseInt = Integer.parseInt(iterationsInput.getText().trim());
		} catch (NumberFormatException e) {
			parseInt = 0;
		}
		return parseInt;
	}
}
