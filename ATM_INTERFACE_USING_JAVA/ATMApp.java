import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private String firstName;
    private String middleName;
    private String lastName;
    private String aadhaarCard;
    private String panCard;
    private StringBuilder transactionHistory;

    public Account(String accountNumber, String pin, double initialBalance, String firstName, String middleName, String lastName, String aadhaarCard, String panCard) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.aadhaarCard = aadhaarCard;
        this.panCard = panCard;
        this.transactionHistory = new StringBuilder("Transaction History:\n");
        this.transactionHistory.append(String.format("Initial Balance: $%.2f\n", initialBalance));
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.append(String.format("Deposited: $%.2f\n", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.append(String.format("Withdrew: $%.2f\n", amount));
            return true;
        } else {
            return false;
        }
    }

    public String getTransactionHistory() {
        return transactionHistory.toString();
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public String getAadhaarCard() {
        return aadhaarCard;
    }

    public String getPanCard() {
        return panCard;
    }
}

public class ATMApp {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HashMap<String, Account> accounts;

    private JTextField createAccountNumberField;
    private JPasswordField createPinField;
    private JTextField createInitialBalanceField;
    private JTextField createFirstNameField;
    private JTextField createMiddleNameField;
    private JTextField createLastNameField;
    private JTextField createAadhaarField;
    private JTextField createPanCardField;

    private JTextField loginAccountNumberField;
    private JPasswordField loginPinField;

    private Account currentAccount;
    private JProgressBar progressBar;
    private JLabel balanceLabel;

    private JTextArea transactionHistoryArea;

    public ATMApp() {
        accounts = new HashMap<>();
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("ATM Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setResizable(true);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createMainMenuPanel(), "MainMenu");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createAccountPanel(), "CreateAccount");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create buttons with preferred width and height
        JButton loginButton = createButton("Login", "Click to log in to your account.");
        loginButton.setPreferredSize(new Dimension(250, 50));
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        JButton createAccountButton = createButton("Create Account", "Click to create a new account.");
        createAccountButton.setPreferredSize(new Dimension(250, 50));
        createAccountButton.addActionListener(e -> cardLayout.show(mainPanel, "CreateAccount"));

        JButton exitButton = createButton("Exit", "Exit the ATM system.");
        exitButton.setPreferredSize(new Dimension(250, 50));
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons with some spacing
        panel.add(Box.createVerticalStrut(30));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(createAccountButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(exitButton);

        return panel;
    }

    private JButton createButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255)); // Blue background for better visibility
        button.setForeground(Color.WHITE); // White text color
        return button;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        loginAccountNumberField = new JTextField(20);
        loginPinField = new JPasswordField(20);

        // Layout constraints for responsive design
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Account Number:"), gbc);

        gbc.gridx = 1;
        panel.add(loginAccountNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("PIN:"), gbc);

        gbc.gridx = 1;
        panel.add(loginPinField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.addActionListener(e -> {
            String accountNumber = loginAccountNumberField.getText();
            String pin = new String(loginPinField.getPassword());
            currentAccount = accounts.get(accountNumber);

            if (currentAccount != null && currentAccount.getPin().equals(pin)) {
                cardLayout.show(mainPanel, "Dashboard");
                updateBalanceAndHistory();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid account number or PIN.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        createAccountNumberField = new JTextField(20);
        createPinField = new JPasswordField(20);
        createInitialBalanceField = new JTextField(20);
        createFirstNameField = new JTextField(20);
        createMiddleNameField = new JTextField(20);
        createLastNameField = new JTextField(20);
        createAadhaarField = new JTextField(20);
        createPanCardField = new JTextField(20);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Account Number:"), gbc);

        gbc.gridx = 1;
        panel.add(createAccountNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("PIN:"), gbc);

        gbc.gridx = 1;
        panel.add(createPinField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Initial Balance:"), gbc);

        gbc.gridx = 1;
        panel.add(createInitialBalanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        panel.add(createFirstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Middle Name:"), gbc);

        gbc.gridx = 1;
        panel.add(createMiddleNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        panel.add(createLastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Aadhaar Card:"), gbc);

        gbc.gridx = 1;
        panel.add(createAadhaarField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("PAN Card:"), gbc);

        gbc.gridx = 1;
        panel.add(createPanCardField, gbc);

        JButton createButton = new JButton("Create Account");
        createButton.setPreferredSize(new Dimension(150, 40));
        createButton.addActionListener(e -> {
            String accountNumber = createAccountNumberField.getText();
            String pin = new String(createPinField.getPassword());
            double initialBalance;

            try {
                initialBalance = Double.parseDouble(createInitialBalanceField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid balance. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Account account = new Account(accountNumber, pin, initialBalance, createFirstNameField.getText(),
                    createMiddleNameField.getText(), createLastNameField.getText(), createAadhaarField.getText(), createPanCardField.getText());
            accounts.put(accountNumber, account);

            JOptionPane.showMessageDialog(frame, "Account Created Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "MainMenu");
        });

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(createButton, gbc);

        gbc.gridx = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        balanceLabel = new JLabel("Balance: $0.00", JLabel.CENTER);
        panel.add(balanceLabel, BorderLayout.NORTH);

        transactionHistoryArea = new JTextArea(10, 40);
        transactionHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionHistoryArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog(frame, "Enter deposit amount:");
            try {
                double depositAmount = Double.parseDouble(amount);
                currentAccount.deposit(depositAmount);
                updateBalanceAndHistory();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid deposit amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog(frame, "Enter withdrawal amount:");
            try {
                double withdrawAmount = Double.parseDouble(amount);
                if (currentAccount.withdraw(withdrawAmount)) {
                    updateBalanceAndHistory();
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid withdrawal amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            currentAccount = null;
            cardLayout.show(mainPanel, "MainMenu");
        });

        buttonsPanel.add(depositButton);
        buttonsPanel.add(withdrawButton);
        buttonsPanel.add(logoutButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateBalanceAndHistory() {
        balanceLabel.setText("Balance: $" + currentAccount.getBalance());
        transactionHistoryArea.setText(currentAccount.getTransactionHistory());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMApp());
    }
}
