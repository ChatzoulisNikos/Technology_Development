package app.gui.jtableview;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.math3.util.Pair;

public class SimpleTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L; // provides versioning information for the class
	private final String[ ] columnNames = {"Year","Value"}; // an array of column names representing the headers of the table .
    private List<Pair<Integer, Integer>> data;  // a list of pair <int, int> representing the data in the table, where each pair corresponds to a row with a year and a value.
    private String requestName; // A String representing the name of the request associated with the data . 

	public SimpleTableModel(String requestName, List<Pair<Integer, Integer>> data) { // constructor which initializes  data and requestName with the provided values.
		super();
		this.data = data;
		this.requestName = requestName;
	}
	//the following are overridden methods from AbstractTableModel 
	@Override
	public int getRowCount() {
		return this.data.size(); // Return the number of rows in the table, which is the size of the data list.
	}
	
	@Override
	public int getColumnCount() { // Returns the number of columns , which is always 2 
		return 2;
	}
	@Override
	public String getColumnName(int col) {
        return columnNames[col]; // Returns the column name for the given index.
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) { //Return the value at the specified row and column indices . It retrieves the year column 0 and the value for column 1.
		if(rowIndex<=getRowCount()-1)
			if(columnIndex == 0)
				return data.get(rowIndex).getFirst();
			else if(columnIndex == 1)
				return data.get(rowIndex).getSecond();

		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public Class getColumnClass(int c) { // Returns the class type of the elements in the specified column. It uses the class type of the first element in the column.
        return getValueAt(0, c).getClass();
    }
	
	public String[] getColumnNames() { // Return the array of column names.
		return columnNames;
	}

	public List<Pair<Integer, Integer>> getData() { // Returns the list of data pairs .
		return data;
	}

	public String getRequestName() { // Returns the request name.
		return requestName;
	}

	@Override
	public String toString() { // provides a string representation of the SimpleTableModel object for debugging or logging purposes.
		return "SimpleTableModel [" +
				"requestName=" + requestName + 
				", columnNames=" + Arrays.toString(columnNames) + 
				", data=" + data.toString() + 
				"]";
	}

	
	
	
}
