//   Copyright 2004-2014 Jim Voris
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//
package com.qumasoft.guitools.admin;

import com.qumasoft.guitools.AbstractQVCSCommandDialog;
import com.qumasoft.qvcslib.ServerProperties;
import java.net.InetSocketAddress;
import javax.swing.JOptionPane;

/**
 * Server properties dialog.
 *
 * @author Jim Voris
 */
public class ServerPropertiesDialog extends AbstractQVCSCommandDialog {

    private static final long serialVersionUID = 8739997424968463297L;

    private String serverName;
    private String serverIPAddress;
    private int clientPort;
    private int adminServerPort;

    private boolean isOKFlag = false;

    /**
     * Creates new form ServerPropertiesDialog.
     *
     * @param parent the parent frame.
     * @param modal is this modal or not.
     */
    public ServerPropertiesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getRootPane().setDefaultButton(okButton);

        // Populate with default values.
        populateDefaultValues();
        center();
    }

    /**
     * Use this constructor for existing servers.
     *
     * @param parent the parent frame.
     * @param modal is this modal or not.
     * @param argServerName the name of the server.
     */
    public ServerPropertiesDialog(java.awt.Frame parent, boolean modal, String argServerName) {
        super(parent, modal);
        initComponents();
        getRootPane().setDefaultButton(okButton);

        // Load the dialog with existing data.
        loadData(argServerName);
        center();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
     * Editor.
     */
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        serverNameLabel = new javax.swing.JLabel();
        serverNameValue = new javax.swing.JTextField();
        serverIPAddressLabel = new javax.swing.JLabel();
        serverIPAddressValue = new javax.swing.JTextField();
        clientPortLabel = new javax.swing.JLabel();
        clientPortValue = new javax.swing.JTextField();
        serverAdminPortLabel = new javax.swing.JLabel();
        serverAdminPortValue = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setTitle("Server Properties");
        setName("Server Properties"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        serverNameLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        serverNameLabel.setText("Server Name:");

        serverNameValue.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        serverNameValue.setToolTipText("Enter server name");

        serverIPAddressLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        serverIPAddressLabel.setText("Server IP Address:");

        serverIPAddressValue.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        serverIPAddressValue.setToolTipText("Enter the server IP address");

        clientPortLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        clientPortLabel.setText("Client Port:");

        clientPortValue.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        clientPortValue.setToolTipText("Enter server listener port number");

        serverAdminPortLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        serverAdminPortLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        serverAdminPortLabel.setText("Admin Port:");
        serverAdminPortLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        serverAdminPortValue.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        serverAdminPortValue.setToolTipText("Enter server administrative listener port number");

        okButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        okButton.setText("OK");
        okButton.setMaximumSize(new java.awt.Dimension(80, 25));
        okButton.setMinimumSize(new java.awt.Dimension(80, 25));
        okButton.setPreferredSize(new java.awt.Dimension(80, 25));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.setMaximumSize(new java.awt.Dimension(80, 25));
        cancelButton.setMinimumSize(new java.awt.Dimension(80, 25));
        cancelButton.setPreferredSize(new java.awt.Dimension(80, 25));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(serverNameLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(serverNameValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(serverIPAddressLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(serverIPAddressValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(layout.createSequentialGroup()
                                        .add(clientPortLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(168, 168, 168)
                                        .add(serverAdminPortLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                        .add(layout.createSequentialGroup()
                                                .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                                .add(clientPortValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                .add(168, 168, 168)
                                                .add(serverAdminPortValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                        .add(15, 15, 15))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(serverNameLabel)
                        .add(4, 4, 4)
                        .add(serverNameValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(serverIPAddressLabel)
                        .add(1, 1, 1)
                        .add(serverIPAddressValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(clientPortLabel)
                                .add(serverAdminPortLabel))
                        .add(1, 1, 1)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(clientPortValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(serverAdminPortValue, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        closeDialog(null);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_okButtonActionPerformed
    {//GEN-HEADEREND:event_okButtonActionPerformed
        if (validateValues()) {
            setVisible(false);
            dispose();
            isOKFlag = true;
        }
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt)
    {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
        isOKFlag = false;
    }//GEN-LAST:event_closeDialog

    @Override
    public void dismissDialog() {
        cancelButtonActionPerformed(null);
    }

    boolean isOK() {
        return isOKFlag;
    }

    private boolean validateValues() {
        boolean retVal = true;
        serverName = serverNameValue.getText();
        if (serverName.length() == 0) {
            serverNameValue.requestFocusInWindow();
            JOptionPane.showConfirmDialog(EnterpriseAdmin.getInstance(), "You must define a server name.", "Server name error", JOptionPane.PLAIN_MESSAGE);
            retVal = false;
        }
        if (retVal) {
            try {
                serverIPAddress = serverIPAddressValue.getText();

                // Use this to validate the entered address.
                InetSocketAddress address = new InetSocketAddress(serverIPAddress, 0);
            } catch (IllegalArgumentException e) {
                serverIPAddressValue.requestFocusInWindow();
                retVal = false;
            }
        }
        if (retVal) {
            try {
                clientPort = Integer.decode(clientPortValue.getText()).intValue();
            } catch (NumberFormatException e) {
                clientPortValue.requestFocusInWindow();
                retVal = false;
            }
        }
        if (retVal) {
            try {
                adminServerPort = Integer.decode(serverAdminPortValue.getText()).intValue();
            } catch (NumberFormatException e) {
                serverAdminPortLabel.requestFocusInWindow();
                retVal = false;
            }
        }
        return retVal;
    }

    String getServerName() {
        return serverName;
    }

    int getClientPort() {
        return clientPort;
    }

    int getServerAdminPort() {
        return adminServerPort;
    }

    String getServerIPAddress() {
        return serverIPAddress;
    }

    private void loadData(String argServerName) {
        ServerProperties serverProperties = new ServerProperties(argServerName);
        if (argServerName.equals(serverProperties.getServerName())) {
            serverNameValue.setText(argServerName);
            serverIPAddressValue.setText(serverProperties.getServerIPAddress());
            clientPortValue.setText(Integer.toString(serverProperties.getClientPort()));
            serverAdminPortValue.setText(Integer.toString(serverProperties.getServerAdminPort()));
        }
    }

    private void populateDefaultValues() {
        clientPortValue.setText("9889");
        serverAdminPortValue.setText("9890");
    }
// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel clientPortLabel;
    private javax.swing.JTextField clientPortValue;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel serverAdminPortLabel;
    private javax.swing.JTextField serverAdminPortValue;
    private javax.swing.JLabel serverIPAddressLabel;
    private javax.swing.JTextField serverIPAddressValue;
    private javax.swing.JLabel serverNameLabel;
    private javax.swing.JTextField serverNameValue;
// End of variables declaration//GEN-END:variables
}
