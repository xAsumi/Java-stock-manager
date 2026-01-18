import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    private JPanel mainPanel;
    private JTable tableStock;
    private JButton btnLivre;
    private JButton btnJeu;
    private JButton btnDVD;
    private JButton btnRefresh;
    private JButton btnDelete;
    private DefaultTableModel model;

    public Dashboard() {
        setTitle("Gestion de Stock");
        setContentPane(mainPanel);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columns = {"Type", "Titre", "Détails", "Prix"};
        model = new DefaultTableModel(columns, 0);
        tableStock.setModel(model);

        styleDashboard();
        refreshTable();

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        btnLivre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titre = JOptionPane.showInputDialog("Titre du Livre:");
                String auteur = JOptionPane.showInputDialog("Auteur:");
                String prix = JOptionPane.showInputDialog("Prix:");
                if (titre != null && prix != null) {
                    try {
                        Livre l = new Livre(titre, auteur, Double.parseDouble(prix));
                        MagasinDB.save(l);
                        refreshTable();
                    } catch (Exception ex) {
                    }
                }
            }
        });

        btnJeu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titre = JOptionPane.showInputDialog("Titre du Jeu:");
                String console = JOptionPane.showInputDialog("Console:");
                String prix = JOptionPane.showInputDialog("Prix:");
                if (titre != null && prix != null) {
                    try {
                        JeuVideo j = new JeuVideo(titre, console, Double.parseDouble(prix));
                        MagasinDB.save(j);
                        refreshTable();
                    } catch (Exception ex) {
                    }
                }
            }
        });

        btnDVD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titre = JOptionPane.showInputDialog("Titre du DVD:");
                String prix = JOptionPane.showInputDialog("Prix:");
                if (titre != null && prix != null) {
                    try {
                        DVD d = new DVD(titre, Double.parseDouble(prix));
                        MagasinDB.save(d);
                        refreshTable();
                    } catch (Exception ex) {
                    }
                }
            }
        });

        if (btnDelete != null) {
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = tableStock.getSelectedRow();
                    if (row == -1) {
                        JOptionPane.showMessageDialog(null, "Sélectionnez une ligne à supprimer !");
                    } else {
                        String rawTitle = (String) tableStock.getValueAt(row, 1);
                        String cleanTitle = rawTitle.replace("Titre : ", "").trim();

                        int choice = JOptionPane.showConfirmDialog(null, "Supprimer : " + cleanTitle + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            MagasinDB.deleteByTitre(cleanTitle);
                            refreshTable();
                        }
                    }
                }
            });
        }

        setVisible(true);
    }

    private void styleDashboard() {
        mainPanel.setBackground(new Color(30, 30, 30));

        tableStock.setBackground(new Color(45, 45, 45));
        tableStock.setForeground(Color.WHITE);
        tableStock.setSelectionBackground(new Color(0, 120, 215));
        tableStock.setSelectionForeground(Color.WHITE);
        tableStock.setRowHeight(30);
        tableStock.setShowGrid(false);
        tableStock.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tableStock.getTableHeader().setBackground(new Color(30, 30, 30));
        tableStock.getTableHeader().setForeground(Color.WHITE);
        tableStock.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = (JScrollPane) tableStock.getParent().getParent();
        scroll.getViewport().setBackground(new Color(30, 30, 30));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        styleButton(btnLivre, new Color(0, 120, 215));
        styleButton(btnJeu, new Color(0, 120, 215));
        styleButton(btnDVD, new Color(0, 120, 215));
        styleButton(btnRefresh, new Color(100, 100, 100));
        if (btnDelete != null) styleButton(btnDelete, new Color(220, 53, 69));

        if (btnLivre.getParent() instanceof JPanel) {
            ((JPanel) btnLivre.getParent()).setBackground(new Color(30, 30, 30));
        }
    }

    private void styleButton(JButton btn, Color color) {
        if (btn == null) return;
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void refreshTable() {
        model.setRowCount(0);
        ArrayList<Article> list = MagasinDB.load();

        for (Article a : list) {
            String type = "Article";
            String details = "-";

            if (a instanceof Livre) {
                type = "Livre";
                if (a.toString().contains("Auteur")) details = a.toString().split("Auteur :")[1];
            } else if (a instanceof JeuVideo) {
                type = "Jeu Vidéo";
                if (a.toString().contains("Console")) details = a.toString().split("Console :")[1];
            } else if (a instanceof DVD) {
                type = "DVD";
            }

            String rawString = a.toString();
            String cleanTitle = rawString;

            if (rawString.contains("Titre : ")) {
                int start = rawString.indexOf("Titre : ") + 8;
                int end = rawString.indexOf(" |", start);
                if (end == -1) end = rawString.length();
                cleanTitle = rawString.substring(start, end);
            } else {
                cleanTitle = rawString.split("\\|")[0];
            }

            cleanTitle = cleanTitle.replace("]", "").trim();

            model.addRow(new Object[]{type, cleanTitle, details, a.getPrixLocation() + " DH"});
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(-13487566));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, BorderLayout.NORTH);
        btnLivre = new JButton();
        btnLivre.setText("+ Livre");
        panel1.add(btnLivre, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        btnJeu = new JButton();
        btnJeu.setText("+ Jeu");
        panel1.add(btnJeu, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDVD = new JButton();
        btnDVD.setText("+ DVD");
        panel1.add(btnDVD, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");
        panel1.add(btnRefresh, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDelete = new JButton();
        btnDelete.setText("Supprimer");
        panel1.add(btnDelete, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, BorderLayout.CENTER);
        tableStock = new JTable();
        scrollPane1.setViewportView(tableStock);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}