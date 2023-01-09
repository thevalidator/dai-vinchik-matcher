/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import org.apache.logging.log4j.LogManager;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.matcher.impl.filter.AgeFilterImpl;
import ru.thevalidator.daivinchikmatcher.matcher.impl.filter.CityFilterImpl;
import ru.thevalidator.daivinchikmatcher.matcher.impl.filter.TextFilterImpl;
import ru.thevalidator.daivinchikmatcher.property.Account;
import ru.thevalidator.daivinchikmatcher.property.Property;
import ru.thevalidator.daivinchikmatcher.property.Proxy;
import ru.thevalidator.daivinchikmatcher.property.UserAgent;
import ru.thevalidator.daivinchikmatcher.service.Task;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class AppWindow extends javax.swing.JFrame {

    private static final Logger logger = LogManager.getLogger(AppWindow.class);
    private static int MAX_LINES = 400;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm.ss");
    private Property properties = null;
    private boolean isStarted = false;
    private SwingWorker worker;
    private Set<Filter> filters;

    /**
     * Creates new form AppWindow
     */
    public AppWindow() {
        filters = new HashSet<>();
        initProperties();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        accountComboBox = new javax.swing.JComboBox<>();
        userAgentComboBox = new javax.swing.JComboBox<>();
        proxyComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        proxyLabel = new javax.swing.JLabel();
        proxyToggleButton = new CustomToggleButton();
        startButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        addMenu = new javax.swing.JMenu();
        addAccountMenuItem = new javax.swing.JMenuItem();
        addUserAgentMenuItem = new javax.swing.JMenuItem();
        addProxyMenuItem = new javax.swing.JMenuItem();
        filterMenu = new javax.swing.JMenu();
        ageCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        cityCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        textCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Daivinchik matcher");
        setMinimumSize(new java.awt.Dimension(600, 500));
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        accountComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        accountComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(properties.getAccountNames()));
        accountComboBox.setMinimumSize(new java.awt.Dimension(200, 26));
        accountComboBox.setPreferredSize(new java.awt.Dimension(200, 26));
        jPanel1.add(accountComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 250, -1));

        userAgentComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userAgentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(properties.getUserAgentsNames()));
        userAgentComboBox.setMinimumSize(new java.awt.Dimension(200, 26));
        userAgentComboBox.setPreferredSize(new java.awt.Dimension(200, 26));
        jPanel1.add(userAgentComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 250, -1));

        proxyComboBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        proxyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(properties.getProxyAdresses()));
        proxyComboBox.setMinimumSize(new java.awt.Dimension(200, 26));
        proxyComboBox.setPreferredSize(new java.awt.Dimension(200, 26));
        jPanel1.add(proxyComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 250, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Account:");
        jLabel1.setMinimumSize(new java.awt.Dimension(200, 26));
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 26));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 100, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("User agent:");
        jLabel2.setMinimumSize(new java.awt.Dimension(200, 26));
        jLabel2.setPreferredSize(new java.awt.Dimension(200, 26));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 100, -1));

        proxyLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        proxyLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        proxyLabel.setText("Proxy:");
        proxyLabel.setMinimumSize(new java.awt.Dimension(200, 26));
        proxyLabel.setPreferredSize(new java.awt.Dimension(200, 26));
        jPanel1.add(proxyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 90, -1));

        proxyToggleButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        proxyToggleButton.setText("OFF");
        proxyToggleButton.setAlignmentY(0.0F);
        proxyToggleButton.setBorderPainted(false);
        proxyToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        proxyToggleButton.setMargin(new java.awt.Insets(2, 10, 2, 10));
        proxyToggleButton.setMaximumSize(new java.awt.Dimension(60, 26));
        proxyToggleButton.setMinimumSize(new java.awt.Dimension(60, 26));
        proxyToggleButton.setOpaque(true);
        proxyToggleButton.setPreferredSize(new java.awt.Dimension(60, 26));
        proxyToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proxyToggleButtonActionPerformed(evt);
            }
        });
        jPanel1.add(proxyToggleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, -1, -1));

        startButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        startButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        startButton.setForeground(new java.awt.Color(0, 0, 0));
        startButton.setText("START");
        startButton.setBorderPainted(false);
        startButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startButton.setMargin(new java.awt.Insets(2, 14, 2, 14));
        startButton.setMaximumSize(new java.awt.Dimension(70, 70));
        startButton.setMinimumSize(new java.awt.Dimension(70, 70));
        startButton.setPreferredSize(new java.awt.Dimension(70, 70));
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });
        jPanel1.add(startButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, -1, -1));

        logTextArea.setBackground(new java.awt.Color(51, 51, 51));
        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        jScrollPane1.setViewportView(logTextArea);

        jMenu1.setText("File");

        addMenu.setText("Add");

        addAccountMenuItem.setText("Account");
        addAccountMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAccountMenuItemActionPerformed(evt);
            }
        });
        addMenu.add(addAccountMenuItem);

        addUserAgentMenuItem.setText("User agent");
        addUserAgentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserAgentMenuItemActionPerformed(evt);
            }
        });
        addMenu.add(addUserAgentMenuItem);

        addProxyMenuItem.setText("Proxy");
        addProxyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProxyMenuItemActionPerformed(evt);
            }
        });
        addMenu.add(addProxyMenuItem);

        jMenu1.add(addMenu);

        jMenuBar1.add(jMenu1);

        filterMenu.setText("Filters");

        ageCheckBoxMenuItem.setSelected(true);
        filters.add(new AgeFilterImpl());
        ageCheckBoxMenuItem.setText("Age filter");
        ageCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageCheckBoxMenuItemActionPerformed(evt);
            }
        });
        filterMenu.add(ageCheckBoxMenuItem);

        cityCheckBoxMenuItem.setSelected(true);
        filters.add(new CityFilterImpl());
        cityCheckBoxMenuItem.setText("City filter");
        cityCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityCheckBoxMenuItemActionPerformed(evt);
            }
        });
        filterMenu.add(cityCheckBoxMenuItem);

        textCheckBoxMenuItem.setSelected(true);
        filters.add(new TextFilterImpl());
        textCheckBoxMenuItem.setText("Text filter");
        textCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCheckBoxMenuItemActionPerformed(evt);
            }
        });
        filterMenu.add(textCheckBoxMenuItem);

        jMenuBar1.add(filterMenu);

        jMenu2.setText("Help");

        jMenuItem1.setText("About");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void initProperties() {
        properties = Property.readFromJson(Property.PROP_FILE);
    }

    private void proxyToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proxyToggleButtonActionPerformed
        if (proxyToggleButton.isSelected()) {
            proxyComboBox.setEnabled(false);
            proxyToggleButton.setText("ON");
            appendToPane("PROXY IS ON");
        } else {
            proxyComboBox.setEnabled(true);
            proxyToggleButton.setText("OFF");
            appendToPane("PROXY IS OFF");
        }
    }//GEN-LAST:event_proxyToggleButtonActionPerformed

    //  LOG CONSOLE //
    public void appendToPane(String msg) {
        try {
            String timestamp = LocalDateTime.now().format(formatter);
            String line = "[" + timestamp + "] - " + msg + "\n";
            cleanConsole();
            logTextArea.append(line);
            logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        } catch (Exception e) {
            logger.error("APPEND METHOD: {}", e.getMessage());
        }
    }

    private void cleanConsole() {
        try {
            javax.swing.text.Element root = logTextArea.getDocument().getDefaultRootElement();
            if (root.getElementCount() > MAX_LINES) {
                javax.swing.text.Element firstLine = root.getElement(0);
                logTextArea.getDocument().remove(0, firstLine.getEndOffset());
            }
        } catch (BadLocationException e) {
            logger.error("CLEAN CONSOLE METHOD: {}", e.getMessage());
        }
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Component component = new JLabel();
        JScrollPane jScrollPane = new JScrollPane(component);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        JTextArea jTextArea = new JTextArea(
                """
                
                Likes profiles that matches your search
                criteria in Daivinchik bot for VK.
                
                
                v1.0.0.0-alpha-05
                [thevalidator]
                2023, January""");
        jTextArea.setColumns(20);
        jTextArea.setLineWrap(true);
        jTextArea.setRows(9);
        jTextArea.setEditable(false);
        jScrollPane.setViewportView(jTextArea);
        JLabel header = new JLabel();
        header.setText("Daivinchik matcher");
        header.setFont(new java.awt.Font("Segoe UI", 1, 14));
        header.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        header.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jScrollPane.setColumnHeaderView(header);
        JOptionPane.showMessageDialog(this, jScrollPane, "About", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (isStarted) {
            isStarted = false;
            setStartButtonStatus(-1);
            if (worker != null & !worker.isCancelled()) {
                worker.cancel(true);
                appendToPane("STOPPED");
            }

        } else {
            isStarted = true;
            setStartButtonStatus(1);
            Account account = properties.getAccounts().get(accountComboBox.getSelectedIndex());
            UserAgent userAgent = properties.getUserAgents().get(userAgentComboBox.getSelectedIndex());
            Proxy proxy = proxyToggleButton.isSelected() ? properties.getProxies().get(proxyComboBox.getSelectedIndex()) : null;

            Task task = new Task(account, proxy, userAgent, properties.getDelay(), filters);
            worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    task.run();

                    return null;
                }
            };
            worker.execute();
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void addAccountMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAccountMenuItemActionPerformed
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField tokenField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("User ID:"));
        panel.add(idField);
        panel.add(new JLabel("Token:"));
        panel.add(tokenField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add account",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                properties.getAccounts().add(new Account(nameField.getText().trim(),
                        Integer.valueOf(idField.getText().trim()),
                        tokenField.getText().trim()));
                Property.saveToJson(properties);
                this.initProperties();
                accountComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(properties.getAccountNames()));
            } catch (Exception e) {
                // Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, e);
            }

        }
    }//GEN-LAST:event_addAccountMenuItemActionPerformed

    private void addUserAgentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserAgentMenuItemActionPerformed
        JTextField name = new JTextField();
        JTextField value = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(name);
        panel.add(new JLabel("Value:"));
        panel.add(value);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add user agent",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                properties.getUserAgents().add(new UserAgent(name.getText().trim(), value.getText().trim()));
                Property.saveToJson(properties);
                this.initProperties();
                userAgentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(properties.getUserAgentsNames()));
            } catch (Exception e) {
                //Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, e);
            }

        }
    }//GEN-LAST:event_addUserAgentMenuItemActionPerformed

    private void addProxyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProxyMenuItemActionPerformed
        JTextField addressField = new JTextField();
        JTextField portField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("IP address:"));
        panel.add(addressField);
        panel.add(new JLabel("port:"));
        panel.add(portField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add proxy",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                properties.getProxies().add(new Proxy(addressField.getText().trim(), Integer.parseInt(portField.getText().trim())));
                Property.saveToJson(properties);
                this.initProperties();
                proxyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(properties.getProxyAdresses()));
            } catch (Exception e) {
                //Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }//GEN-LAST:event_addProxyMenuItemActionPerformed

    private void textCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCheckBoxMenuItemActionPerformed
        if (textCheckBoxMenuItem.isSelected()) {
            filters.add(new TextFilterImpl());
            appendToPane("AGE filter is ON");
        } else {
            Iterator it = filters.iterator();
            while (it.hasNext()) {
                Object item = it.next();
                if (item instanceof TextFilterImpl) {
                    it.remove();
                }
            }
            appendToPane("TEXT filter is OFF");
        }
    }//GEN-LAST:event_textCheckBoxMenuItemActionPerformed

    private void ageCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageCheckBoxMenuItemActionPerformed
        if (ageCheckBoxMenuItem.isSelected()) {
            filters.add(new AgeFilterImpl());
            appendToPane("AGE filter is ON");
        } else {
            Iterator it = filters.iterator();
            while (it.hasNext()) {
                Object item = it.next();
                if (item instanceof AgeFilterImpl) {
                    it.remove();
                }
            }
            appendToPane("AGE filter is OFF");
        }
    }//GEN-LAST:event_ageCheckBoxMenuItemActionPerformed

    private void cityCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityCheckBoxMenuItemActionPerformed
        if (cityCheckBoxMenuItem.isSelected()) {
            filters.add(new CityFilterImpl());
            appendToPane("CITY filter is ON");
        } else {
            Iterator it = filters.iterator();
            while (it.hasNext()) {
                Object item = it.next();
                if (item instanceof CityFilterImpl) {
                    it.remove();
                }
            }
            appendToPane("CITY filter is OFF");
        }
    }//GEN-LAST:event_cityCheckBoxMenuItemActionPerformed

    public void setStartButtonStatus(int status) {
        switch (status) {
            case 1:
                startButton.setEnabled(true);
                startButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
                startButton.setText("STOP");
                break;
            case -1:
                startButton.setEnabled(true);
                startButton.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
                startButton.setText("START");
                break;
            default:
                startButton.setEnabled(false);
                startButton.setText("WAIT");
                break;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> accountComboBox;
    private javax.swing.JMenuItem addAccountMenuItem;
    private javax.swing.JMenu addMenu;
    private javax.swing.JMenuItem addProxyMenuItem;
    private javax.swing.JMenuItem addUserAgentMenuItem;
    private javax.swing.JCheckBoxMenuItem ageCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem cityCheckBoxMenuItem;
    private javax.swing.JMenu filterMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JComboBox<String> proxyComboBox;
    private javax.swing.JLabel proxyLabel;
    private javax.swing.JToggleButton proxyToggleButton;
    private javax.swing.JButton startButton;
    private javax.swing.JCheckBoxMenuItem textCheckBoxMenuItem;
    private javax.swing.JComboBox<String> userAgentComboBox;
    // End of variables declaration//GEN-END:variables
}
