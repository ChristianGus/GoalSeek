import java.awt.*;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
 
import java.text.*;
 
public class RechnerGui extends JPanel
                                    implements PropertyChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Values for the fields
	private double bar;
	private double dynamic;
	private double percent;
	private int years;
	private int month;
	private int sperrMonth;
	private int sperrYears;
	private int schuss;
	private double endval;
	private double tolerance;
	private double rente;
 

 
    //Labels to identify the fields
    private JLabel barJLabel;
	private JLabel dynamicJLabel;
	private JLabel percentJLabel;
	private JLabel yearsJLabel;
	private JLabel monthJLabel;
	private JLabel sperrMonthJLabel;
	private JLabel sperrYearsJLabel;
	private JLabel schussJLabel;
	private JLabel endvalJLabel;
	private JLabel toleranceJLabel;
	private JLabel renteJLabel;
	
 
    //Strings for the labels
    private static String barJLabelString = "Barwert";
	private static String dynamicJLabelString = "Dynamik nach jew. 1 Jahr";
	private static String percentJLabelString = "Zinssatz";
	private static String yearsJLabelString = "Einzahlungszeit Jahre";
	private static String monthJLabelString = "Einzahlungszeit Monate";
	private static String sperrMonthJLabelString = "Sperrzeit-Monate";
	private static String sperrYearsJLabelString = "Sperrzeit-Jahre";
	private static String schussJLabelString = "Vor-/Nachschüssig (1/0)";
	private static String endvalJLabelString = "Endwert";
	private static String toleranceJLabelString = "Toleranz";
	private static String renteJLabelString = "Rente";
 
    //Fields for data entry
	private JFormattedTextField barField;
	private JFormattedTextField dynamicField;
	private JFormattedTextField percentField;
	private JFormattedTextField yearsField;
	private JFormattedTextField monthField;
	private JFormattedTextField sperrMonthField;
	private JFormattedTextField sperrYearsField;
	private JFormattedTextField schussField;
	private JFormattedTextField endvalField;
	private JFormattedTextField renteField;
	
 
    //Formats to format and parse numbers
    private NumberFormat amountFormat;
    private NumberFormat percentFormat;
    private NumberFormat paymentFormat;
 
    public RechnerGui() {
        super(new BorderLayout());
        setUpFormats();
        
        //Init Values
        
        bar = 0;
        dynamic = 0;
        percent = 0;
        years = 0;
        month = 0;
        sperrMonth = 0;
        sperrYears = 0;
        schuss = 0;
        endval = 0;
        rente = 0;
        

        //Create the labels.
 
        barJLabel = new JLabel(barJLabelString);
    	dynamicJLabel = new JLabel(dynamicJLabelString);
    	percentJLabel = new JLabel(percentJLabelString);
    	yearsJLabel = new JLabel(yearsJLabelString);
    	monthJLabel = new JLabel(monthJLabelString);
    	sperrMonthJLabel = new JLabel(sperrMonthJLabelString);
    	sperrYearsJLabel = new JLabel(sperrYearsJLabelString);
    	schussJLabel = new JLabel(schussJLabelString);
    	endvalJLabel = new JLabel(endvalJLabelString);
    	toleranceJLabel = new JLabel(toleranceJLabelString);
    	renteJLabel= new JLabel(renteJLabelString);
    	
        //Create the text fields and set them up.
        barField = new JFormattedTextField(amountFormat);
        barField.setValue(bar);
        barField.setColumns(10);
        barField.addPropertyChangeListener("value", this);
        
        renteField = new JFormattedTextField(amountFormat);
        renteField.setValue(rente);
        renteField.setColumns(10);
        renteField.addPropertyChangeListener("value", this);
        
        endvalField = new JFormattedTextField(amountFormat);
        endvalField.setValue(endval);
        endvalField.setColumns(10);
        endvalField.addPropertyChangeListener("value", this);
        
        schussField = new JFormattedTextField(amountFormat);
        schussField.setValue(schuss);
        schussField.setColumns(10);
        schussField.addPropertyChangeListener("value", this);
        
        sperrYearsField = new JFormattedTextField(amountFormat);
        sperrYearsField.setValue(sperrYears);
        sperrYearsField.setColumns(10);
        sperrYearsField.addPropertyChangeListener("value", this);
        
        sperrMonthField = new JFormattedTextField(amountFormat);
        sperrMonthField.setValue(sperrMonth);
        sperrMonthField.setColumns(10);
        sperrMonthField.addPropertyChangeListener("value", this);
        
        monthField = new JFormattedTextField(amountFormat);
        monthField.setValue(month);
        monthField.setColumns(10);
        monthField.addPropertyChangeListener("value", this);
 
        yearsField = new JFormattedTextField(amountFormat);
        yearsField.setValue(years);
        yearsField.setColumns(10);
        yearsField.addPropertyChangeListener("value", this);
        
        percentField = new JFormattedTextField(percentFormat);
        percentField.setValue(percent);
        percentField.setColumns(10);
        percentField.addPropertyChangeListener("value", this);
 
        dynamicField = new JFormattedTextField(amountFormat);
        dynamicField.setValue(dynamic);
        dynamicField.setColumns(10);
        dynamicField.addPropertyChangeListener("value", this);
 
 
        //Tell accessibility tools about label/textfield pairs.
        barJLabel.setLabelFor(barField);
        percentJLabel.setLabelFor(percentField);
        dynamicJLabel.setLabelFor(dynamicField);
        yearsJLabel.setLabelFor(yearsField);
        monthJLabel.setLabelFor(monthField);
        sperrMonthJLabel.setLabelFor(sperrMonthField);
        sperrYearsJLabel.setLabelFor(sperrYearsField);
        schussJLabel.setLabelFor(schussField);
        endvalJLabel.setLabelFor(endvalField);
        renteJLabel.setLabelFor(renteField);
        
 
        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(barJLabel);
        labelPane.add(renteJLabel);
        labelPane.add(endvalJLabel);
        labelPane.add(percentJLabel);
        labelPane.add(dynamicJLabel);
        labelPane.add(yearsJLabel);
        labelPane.add(monthJLabel);
        labelPane.add(sperrMonthJLabel);
        labelPane.add(sperrYearsJLabel);
        labelPane.add(schussJLabel);
 
        //Layout the text fields in a panel.
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(barField);
        fieldPane.add(renteField);
        fieldPane.add(endvalField);
        fieldPane.add(percentField);
        fieldPane.add(dynamicField);
        fieldPane.add(yearsField);
        fieldPane.add(monthField);
        fieldPane.add(sperrMonthField);
        fieldPane.add(sperrYearsField);
        fieldPane.add(schussField);

        //Put the panels in this panel, labels on left,
        //text fields on right.
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }
 
    /** Called when a field's "value" property changes. */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        if (source == barField) {
            bar = ((Number)barField.getValue()).doubleValue();
        } else if (source == dynamicField) {
            dynamic = ((Number)dynamicField.getValue()).doubleValue();
        } else if (source == yearsField) {
            years = ((Number)yearsField.getValue()).intValue();
        } else if (source == percentField) {
            percent = ((Number)percentField.getValue()).doubleValue();
        } else if (source == monthField) {
            month = ((Number)monthField.getValue()).intValue();
        } else if (source == yearsField) {
            sperrMonth = ((Number)sperrMonthField.getValue()).intValue();
        } else if (source == sperrYearsField) {
            sperrYears = ((Number)sperrYearsField.getValue()).intValue();
        } else if (source == schussField) {
            schuss = ((Number)schussField.getValue()).intValue();
        }  else if (source == endvalField) {
            endval = ((Number)endvalField.getValue()).doubleValue();
        }  else if (source == renteField) {
            rente = ((Number)renteField.getValue()).doubleValue();
        }
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("KTB Rentenrechner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new RechnerGui());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
 
    //Create and set up number formats. These objects also
    //parse numbers input by user.
    private void setUpFormats() {
        amountFormat = NumberFormat.getNumberInstance();
 
        percentFormat = NumberFormat.getNumberInstance();
        percentFormat.setMinimumFractionDigits(3);
 
        paymentFormat = NumberFormat.getCurrencyInstance();
    }
}