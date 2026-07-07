import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class SubscriptionGUI extends JFrame implements ActionListener {

    // Stores all added plans
    private final ArrayList<AIModel> plans = new ArrayList<>();

    private JRadioButton personalRadio, proRadio;

    // Add-plan input fields
    private JTextField modelNameField, priceField, parametersField, contextWindowField;
    private JTextField tokenQuotaField, teamSlotsField;

    // Rows toggled by plan type
    private JPanel tokenQuotaRow, teamSlotsRow;

    private JButton addButton, clearFormButton;

    // Plans display table
    private JTable plansTable;
    private DefaultTableModel tableModel;

    private JTextField indexField;
    private JButton checkTypeButton, displayButton;

    // Card layout for plan actions
    private JPanel actionCards;
    private CardLayout cardLayout;
    private JPanel promptPanel, teamPanel;

    // Personal plan action fields
    private JTextField promptTextField, responseLengthField, buyAmountField;
    private JButton givePromptButton, buyTokensButton;

    // Pro plan action fields
    private JTextField memberNameField;
    private JButton addMemberButton, removeMemberButton;

    // Search member of proplans
    private JTextField searchField;
    private JButton searchButton;

    // File operation buttons
    private JButton exportButton, loadButton;

    public SubscriptionGUI() {
        setTitle("AI Subscription Manager");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //frame comes to middle
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    // Builds top title header
    private JPanel createHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        header.setBackground(new Color(15, 15, 25));
        JLabel title = new JLabel("AI Subscription Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(220, 225, 240));
        header.add(title);
        return header;
    }

    // Stacks all sections vertically
    private JPanel createMainPanel() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(new Color(20, 20, 32));
        main.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));

        main.add(createTypeSelectionPanel());
        main.add(Box.createVerticalStrut(10));
        main.add(createInputFormPanel());
        main.add(Box.createVerticalStrut(10));
        main.add(createActionCardsPanel());
        main.add(Box.createVerticalStrut(10));
        main.add(createBottomPanel());
        main.add(Box.createVerticalStrut(10));
        main.add(createTablePanel());
        return main;
    }

    // Radio buttons for plan type
    private JPanel createTypeSelectionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));// by default center 
        panel.setBackground(new Color(28, 28, 44));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150)),
                "Select Plan Type",TitledBorder.LEFT, TitledBorder.TOP,new Font("Segoe UI", Font.BOLD, 12), new Color(200, 200, 240)
            ));

        personalRadio = new JRadioButton("Personal Plan", true);// starting default personal plan is selected
        proRadio = new JRadioButton("Pro Plan");
        personalRadio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        proRadio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        personalRadio.setForeground(Color.WHITE);
        proRadio.setForeground(Color.WHITE);
        personalRadio.setBackground(new Color(28, 28, 44));
        proRadio.setBackground(new Color(28, 28, 44));

        ButtonGroup group = new ButtonGroup();
        group.add(personalRadio);
        group.add(proRadio);
            
        //action for each pllan differently
        personalRadio.addActionListener(e -> togglePlanType());
        proRadio.addActionListener(e -> togglePlanType());

        panel.add(personalRadio);
        panel.add(proRadio);
        return panel;
    }

    // Shows only the relevant plan field
    private void togglePlanType() {
        boolean isPersonal = personalRadio.isSelected();
        tokenQuotaRow.setVisible(isPersonal);
        teamSlotsRow.setVisible(!isPersonal);
        cardLayout.show(actionCards, isPersonal ? "Personal" : "Pro");
    }

    // Input fields for adding a plan
    private JPanel createInputFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(28, 28, 44));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150)),
                "Add New Plan",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), new Color(200, 200, 240)
            ));

        modelNameField  = createFieldWithHint("e.g., AI1");
        priceField = createFieldWithHint("e.g., 500.0");
        parametersField = createFieldWithHint("e.g., 100");
        contextWindowField = createFieldWithHint("e.g., 32000");
        tokenQuotaField = createFieldWithHint("e.g., 10000");
        teamSlotsField = createFieldWithHint("e.g., 5");

        addLabelledField(panel, "Model Name:", modelNameField);
        addLabelledField(panel, "Price (NPR/1L tokens):", priceField);
        addLabelledField(panel, "Parameters (billions):", parametersField);
        addLabelledField(panel, "Context Window (tokens):", contextWindowField);

        // Personal_only row
        tokenQuotaRow = createLabelledRow("Token Quota :", tokenQuotaField);
        panel.add(tokenQuotaRow);
        panel.add(Box.createVerticalStrut(5));

        // Pro_only row
        teamSlotsRow = createLabelledRow("Team Slots (Pro):", teamSlotsField);
        teamSlotsRow.setVisible(false);
        panel.add(teamSlotsRow);
        panel.add(Box.createVerticalStrut(5));

        panel.add(Box.createVerticalStrut(10));

        // Add and clear buttons row
        addButton  = createButton("Add Plan",new Color(67, 56, 202), "Add the selected plan type");
        clearFormButton = createButton("Clear Form", new Color(55, 65, 81),  "Reset all input fields");

        JPanel btnRow = new JPanel(new GridLayout(1, 2, 10, 0));
        btnRow.setBackground(new Color(28, 28, 44));
        btnRow.add(addButton);
        btnRow.add(clearFormButton);
        btnRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.add(btnRow);

        addFocusValidation(priceField, "Price", true);
        addFocusValidation(parametersField, "Parameters", false);
        addFocusValidation(contextWindowField, "Context window",false);
        addFocusValidation(tokenQuotaField, "Token quota", false);
        addFocusValidation(teamSlotsField, "Team slots", false);

        return panel;
    }

    // Scrollable table of all plans
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(28, 28, 44));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 150)),
            "All Subscription Plans",TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), new Color(200, 200, 240)
            ));

        String[] cols = {"Index", "Type", "Model", "Price", "Params(B)", "Context", "Tokens/Slots"};
        tableModel = new DefaultTableModel(cols, 0)
        {
            public boolean isCellEditable(int r, int c) 
            { 
                return false; 
            }
        };

        plansTable = new JTable(tableModel);
        plansTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        plansTable.setBackground(new Color(18, 18, 28));
        plansTable.setForeground(new Color(210, 215, 235));
        plansTable.setGridColor(new Color(40, 40, 60));
        plansTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        plansTable.getTableHeader().setBackground(new Color(38, 38, 54));
        plansTable.getTableHeader().setForeground(new Color(175, 180, 210));

        JScrollPane scroll = new JScrollPane(plansTable);
        scroll.setPreferredSize(new Dimension(0, 240));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // Builds Personal and Pro action cards
    private JPanel createActionCardsPanel() {
        cardLayout = new CardLayout();
        actionCards = new JPanel(cardLayout);
        actionCards.setBackground(new Color(28, 28, 44));

        // Personal plan card 
        promptPanel = new JPanel();
        promptPanel.setLayout(new BoxLayout(promptPanel, BoxLayout.Y_AXIS));
        promptPanel.setBackground(new Color(28, 28, 44));
        promptPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150)),
                "Prompt Actions (Personal Plan)",TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), new Color(200, 200, 240)
            ));

        promptTextField = createFieldWithHint("Enter prompt text...");
        responseLengthField = createFieldWithHint("e.g., 50");
        buyAmountField = createFieldWithHint("e.g., 100");

        addLabelledField(promptPanel, "Prompt:", promptTextField);
        addLabelledField(promptPanel, "Response Length:", responseLengthField);
        addLabelledField(promptPanel, "Buy Amount:", buyAmountField);

        givePromptButton = createButton("Give Prompt", new Color(67, 56, 202),  "Submit prompt");
        buyTokensButton  = createButton("Buy Tokens",  new Color(109, 40, 217), "Purchase extra tokens");

        JPanel promptBtnRow = new JPanel(new GridLayout(1, 2, 10, 0));
        promptBtnRow.setBackground(new Color(28, 28, 44));
        promptBtnRow.add(givePromptButton);
        promptBtnRow.add(buyTokensButton);
        promptBtnRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        promptPanel.add(promptBtnRow);

        // Pro plan card 
        teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel, BoxLayout.Y_AXIS));
        teamPanel.setBackground(new Color(28, 28, 44));
        teamPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150)),
                "Team Actions (Pro Plan)",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), new Color(200, 200, 240)
            ));

        memberNameField = createFieldWithHint("e.g., John Doe");
        addLabelledField(teamPanel, "Member Name:", memberNameField);

        addMemberButton = createButton("Add Member", new Color(4, 120, 87), "Add team member");
        removeMemberButton = createButton("Remove Member", new Color(153, 27, 27), "Remove team member");

        JPanel teamBtnRow = new JPanel(new GridLayout(1, 2, 10, 0));
        teamBtnRow.setBackground(new Color(28, 28, 44));
        teamBtnRow.add(addMemberButton);
        teamBtnRow.add(removeMemberButton);
        teamBtnRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        teamPanel.add(teamBtnRow);

        // Search member section inside Pro card
        teamPanel.add(Box.createVerticalStrut(10));

        JPanel searchSection = new JPanel();
        searchSection.setLayout(new BoxLayout(searchSection, BoxLayout.Y_AXIS));
        searchSection.setBackground(new Color(28, 28, 44));
        searchSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(67, 56, 202), 2),
            "  Search Member  ",TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13), new Color(130, 160, 255)
            ));

        searchField = createFieldWithHint("member name");
        addLabelledField(searchSection, "Member Name:", searchField);

        searchButton = createButton("Search Member", new Color(29, 78, 216), "Find member in Pro plans");
        JPanel searchBtnRow = new JPanel(new GridLayout(1, 1, 0, 0));
        searchBtnRow.setBackground(new Color(28, 28, 44));
        searchBtnRow.add(searchButton);
        searchBtnRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        searchSection.add(searchBtnRow);

        teamPanel.add(searchSection);

        actionCards.add(promptPanel, "Personal");
        actionCards.add(teamPanel, "Pro");

        return actionCards;
    }

    // Index input, check type, file buttons
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBackground(new Color(28, 28, 44));

        JLabel indexLabel = new JLabel("Index:");
        indexLabel.setForeground(new Color(180, 185, 210));
        panel.add(indexLabel);

        indexField = createFieldWithHint("e.g., 0");
        indexField.setPreferredSize(new Dimension(60, 28));
        panel.add(indexField);

        checkTypeButton = createButton("Check Type",  new Color(146, 64, 14), "Show plan type");
        displayButton   = createButton("Display All", new Color(29, 78, 216), "Refresh table");
        panel.add(checkTypeButton);
        panel.add(displayButton);

        panel.add(Box.createHorizontalStrut(20));

        exportButton = createButton("Export to File", new Color(4, 120, 87),  "Save all plans");
        loadButton   = createButton("Load from File", new Color(29, 78, 216), "View saved plans");
        panel.add(exportButton);
        panel.add(loadButton);

        return panel;
    }

    // Validates numeric field on focus lost
    private void addFocusValidation(JTextField field, String name, boolean isDecimal) 
    {
        field.addFocusListener(new FocusAdapter() 
            {
                public void focusLost(FocusEvent e) 
                {
                    validateNumberField(field, name, isDecimal);
                }
            });
    }

    // Highlights field red if invalid
    private boolean validateNumberField(JTextField field, String name, boolean isDecimal) {
        String text = field.getText().trim();
        if (text.isEmpty() || text.startsWith("e.g.")) 
        {
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            return false;
        }
        try
        {
            if (isDecimal)
            {
                double v = Double.parseDouble(text);
                if (v < 0)
                {
                    throw new NumberFormatException();
                }
            } 
            else 
            {
                int v = Integer.parseInt(text);
                if (v <= 0) 
                {
                    throw new NumberFormatException();
                }
            }
            field.setBorder(new CompoundBorder(new LineBorder(new Color(55, 55, 80), 1),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            return true;
        } catch (NumberFormatException ex) 
        {
            field.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            return false;
        }
    }

    // Creates styled text field with hint
    private JTextField createFieldWithHint(String hint)
    {
        JTextField f = new JTextField(hint);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBackground(new Color(16, 16, 28));
        f.setForeground(new Color(210, 215, 235));
        f.setCaretColor(Color.WHITE);
        f.setBorder(new CompoundBorder(new LineBorder(new Color(55, 55, 80), 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        f.addFocusListener(new FocusAdapter() 
            {
                public void focusGained(FocusEvent e) 
                {
                    if (f.getText().equals(hint)) {f.setText("");}
                }

                public void focusLost(FocusEvent e)
                {
                    if (f.getText().trim().isEmpty()) {f.setText(hint);}
                }
            });
        return f;
    }

    // Returns label + field as a row
    private JPanel createLabelledRow(String label, JTextField field) 
    {
        JPanel row = new JPanel(new BorderLayout(5, 0));
        row.setBackground(new Color(28, 28, 44));
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(new Color(180, 185, 210));
        lbl.setPreferredSize(new Dimension(160, 28));
        row.add(lbl, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return row;
    }

    // Adds labelled row directly to panel
    private void addLabelledField(JPanel panel, String label, JTextField field) 
    {
        panel.add(createLabelledRow(label, field));
        panel.add(Box.createVerticalStrut(5));
    }

    // Creates styled button with tooltip
    private JButton createButton(String text, Color bg, String tooltip) 
    {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setToolTipText(tooltip);
        b.addActionListener(this);
        return b;
    }

    // Routes button clicks to handlers
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();
        if (src == addButton)          
        { 
            if (personalRadio.isSelected()) 
            {
                addPersonalPlan(); 
            }

            else
            {
                addProPlan(); 
            }
        }
        else if (src == clearFormButton)    
        {
            clearForm();
        }
        else if (src == givePromptButton)   
        {
            givePrompt();
        }
        else if (src == buyTokensButton)    
        {
            buyTokens();
        }
        else if (src == checkTypeButton)    
        {
            checkPlanType();
        }

        else if (src == addMemberButton)    
        {
            addTeamMember();
        }
        else if (src == removeMemberButton) 
        {
            removeTeamMember();
        }
        else if (src == exportButton)       
        {
            exportToFile();
        }
        else if (src == loadButton)     
        {
            loadFromFile();
        }
        else if (src == displayButton)    
        {
            refreshTable();
        }
        else if (src == searchButton)    
        {
            searchMember();
        }
    }

    // Validates and adds personal plan
    private void addPersonalPlan() {
        if (!validateNumberField(priceField, "Price", true)  ||
        !validateNumberField(parametersField, "Parameters", false) ||
        !validateNumberField(contextWindowField, "Context window",false) ||
        !validateNumberField(tokenQuotaField, "Token quota", false)) 
        {
            showError("Please fix invalid fields (highlighted in red).");
            return;
        }
        String name = modelNameField.getText().trim();
        if (name.isEmpty() || name.startsWith("e.g.")) 
        { 
            showError("Model name required."); 
            return; 
        }

        double price = Double.parseDouble(priceField.getText().trim());
        int params = Integer.parseInt(parametersField.getText().trim());
        int context = Integer.parseInt(contextWindowField.getText().trim());
        int tokens = Integer.parseInt(tokenQuotaField.getText().trim());

        plans.add(new PersonalPlan(name, price, params, context, tokens));
        showInfo("Personal plan added.");
        clearFormFields();
    }

    // Validates and adds pro plan
    private void addProPlan() {
        if (!validateNumberField(priceField, "Price", true)  ||
        !validateNumberField(parametersField, "Parameters", false) ||
        !validateNumberField(contextWindowField, "Context window",false) ||
        !validateNumberField(teamSlotsField, "Team slots", false)) {
            showError("Please fix invalid fields.");
            return;
        }
        String name = modelNameField.getText().trim();
        if (name.isEmpty() || name.startsWith("e.g.")) 
        { showError("Model name required."); 
            return; 
        }

        double price  = Double.parseDouble(priceField.getText().trim());
        int params = Integer.parseInt(parametersField.getText().trim());
        int context = Integer.parseInt(contextWindowField.getText().trim());
        int slots = Integer.parseInt(teamSlotsField.getText().trim());

        plans.add(new ProPlan(name, price, params, context, slots));
        showInfo("Pro plan added.");
        clearFormFields();
    }

    // Gets valid index from indexField
    private int getDisplayNumber() {
        String s = indexField.getText().trim();
        if (s.isEmpty() || s.startsWith("e.g."))
        { 
            showError("Index required."); 
            return -1; 
        }
        try {
            int i = Integer.parseInt(s);
            if (i < 0 || i >= plans.size()) {
                showError("Index out of range (0-" + (plans.size() - 1) + ").");
                return -1;
            }
            return i;
        } catch (NumberFormatException e) 
        { 
            showError("Invalid index."); 
            return -1; 
        }
    }

    // Sends prompt to selected plan
    private void givePrompt() {
        int idx = getDisplayNumber();
        if (idx == -1)
        {return;
        }
        AIModel model = plans.get(idx);
        String text = promptTextField.getText().trim();
        if (text.isEmpty() || text.startsWith("Enter prompt")) 
        { 
            showError("Prompt text required."); 
            return;
        }
        if (!validateNumberField(responseLengthField, "Response length", false))
        { 
            showError("Invalid response length."); 
            return;
        }
        int len = Integer.parseInt(responseLengthField.getText().trim());

        if (model instanceof PersonalPlan) {
            JOptionPane.showMessageDialog(this, ((PersonalPlan) model).usePrompt(text, len));}
        else if (model instanceof ProPlan){      
            JOptionPane.showMessageDialog(this, ((ProPlan) model).usePrompt(text, len));}
    }

    // Buys tokens for personal plan
    private void buyTokens() {
        int idx = getDisplayNumber();
        if (idx == -1) 
        {
            return;
        }
        AIModel model = plans.get(idx);
        if (!(model instanceof PersonalPlan))
        { 
            showError("Only Personal plans can buy tokens.");
            return; 
        }
        if (!validateNumberField(buyAmountField, "Token amount", false)) 
        { 
            showError("Enter token amount."); 
            return; 
        }
        int amount = Integer.parseInt(buyAmountField.getText().trim());
        showInfo(((PersonalPlan) model).buyTokens(amount));
    }

    // Adds member to selected pro plan
    private void addTeamMember() {
        int idx = getDisplayNumber();
        if (idx == -1) 
        {
            return;
        }
        AIModel model = plans.get(idx);
        if (!(model instanceof ProPlan)) 
        { 
            showError("Only Pro plans have teams.");
            return; 
        }
        String name = memberNameField.getText().trim();
        if (name.isEmpty() || name.startsWith("e.g.")) 
        {
            showError("Member name required."); 
            return; 
        }
        showInfo(((ProPlan) model).addMember(name));
    }

    // Removes member from selected pro plan
    private void removeTeamMember() {
        int idx = getDisplayNumber();
        if (idx == -1) 
        {
            return;
        }
        AIModel model = plans.get(idx);
        if (!(model instanceof ProPlan)) 
        { 
            showError("Only Pro plans have teams.");
            return;
        }
        String name = memberNameField.getText().trim();
        if (name.isEmpty() || name.startsWith("e.g.")) 
        {
            showError("Member name required."); 
            return;
        }
        showInfo(((ProPlan) model).removeMember(name));
    }

    // Shows type of indexed plan
    private void checkPlanType() {
        int idx = getDisplayNumber();
        if (idx == -1) 
        {
            return;
        }
        String type = (plans.get(idx) instanceof PersonalPlan) ? "Personal Plan" : "Pro Plan";
        showInfo("Index " + idx + " is a " + type);
    }

    // Finds member across all pro plans
    private void searchMember() {
        String name = searchField.getText().trim();
        if (name.isEmpty() || name.startsWith("member name"))
        { 
            showError("Enter a member name to search."); 
            return;
        }
        boolean found = false;
        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i) instanceof ProPlan)
            {
                ProPlan pro = (ProPlan) plans.get(i);
                if (pro.hasMember(name)) 
                {
                    showInfo(name + " is a member of " + pro.getModelName() + " (index " + i + ").");
                    found = true;
                    break;
                }
            }
        }
        if (!found) 
        {
            showInfo(name + " is not a member of any Pro plan team.");
        }
    }

    // Reloads table from plans list
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < plans.size(); i++)
        {
            AIModel p = plans.get(i);
            String type = (p instanceof PersonalPlan) ? "Personal" : "Pro";
            String quota = (p instanceof PersonalPlan)? String.valueOf(((PersonalPlan) p).getAvailableTokens())
                : String.valueOf(((ProPlan) p).getTeamSlots());
            tableModel.addRow(new Object[]{ i, type, p.getModelName(), p.getPrice(), p.getParameterCount(), p.getContextWindow(), quota });
        }
    }

    // Resets only the add-plan fields
    private void clearFormFields() {
        modelNameField.setText("e.g., AI1");
        priceField.setText("e.g., 500.0");
        parametersField.setText("e.g., 100");
        contextWindowField.setText("e.g., 32000");
        tokenQuotaField.setText("e.g., 10000");
        teamSlotsField.setText("e.g., 5");
    }

    // Clears every input field on screen
    private void clearForm() {
        clearFormFields();
        promptTextField.setText("Enter prompt text...");
        responseLengthField.setText("e.g., 50");
        buyAmountField.setText("e.g., 100");
        memberNameField.setText("e.g., John Doe");
        searchField.setText("member name");
        indexField.setText("e.g., 0");
    }

    // Writes all plans to text file
    private void exportToFile() {
        if (plans.isEmpty())
        {
            showError("No plans to export."); 
            return; 
        }
        try (BufferedWriter w = new BufferedWriter(new FileWriter("plans_export.txt")))
        {
            for (int i = 0; i < plans.size(); i++) 
            {
                w.write("Index: " + i + "\n");
                w.write(plans.get(i).display());
                w.write("Type: " + (plans.get(i) instanceof ProPlan ? "Pro Plan" : "Personal Plan") + "\n\n");
            }
            showInfo("Exported to plans_export.txt");
        } catch (IOException ex) 
        { 
            showError("Export failed.");
        }
    }

    // Reads and shows saved file
    private void loadFromFile() {
        File f = new File("plans_export.txt");
        if (!f.exists())
        { 
            showError("File not found.");
            return; 
        }

        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
            JTextArea ta = new JTextArea(sb.toString());
            ta.setEditable(false);
            ta.setFont(new Font("Consolas", Font.PLAIN, 12));
            JScrollPane sp = new JScrollPane(ta);
            sp.setPreferredSize(new Dimension(500, 400));
            JOptionPane.showMessageDialog(this, sp, "File Content", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) 
        { 
            showError("Load failed."); 
        }
    }

    // Displays error dialog
    private void showError(String msg)
    {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Displays info dialog
    private void showInfo(String msg) 
    {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(SubscriptionGUI::new);
    }
}