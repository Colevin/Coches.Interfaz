package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class JunctionsTable extends JPanel {
	private static final long serialVersionUID = 1L;
	private JunctionsTableModel tableModel;

	JunctionsTable(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Junctions", TitledBorder.LEFT, TitledBorder.TOP));

		this.tableModel = new JunctionsTableModel(ctrl);
		JTable tabla = new JTable(tableModel);

		
		tabla.setShowHorizontalLines(false);
		tabla.setShowVerticalLines(false);

		
		tabla.setFillsViewportHeight(true);


		JScrollPane scroll = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}
}