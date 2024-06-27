package br.feevale;

import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * Painel para inserção de novas marcas.
 *
 * @author Ramon Pedro Menz
 */
public class PainelInserirMarcas extends JPanel {
    private final JTextField nomeField;
    private final JTextField paisOrigemField;

    /**
     * Construtor para criar o painel de inserção de marcas.
     */
    public PainelInserirMarcas() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Insira uma nova marca");
        add(label, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel paisOrigemLabel = new JLabel("Pais de Origem");
        paisOrigemField = new JTextField();

        mainPanel.add(new JPanel());
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);
        mainPanel.add(paisOrigemLabel);
        mainPanel.add(paisOrigemField);
        add(mainPanel);

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Inserir Marca");
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);


        submitButton.addActionListener(e -> {
            if (areFieldsFilled()) {
                try {
                    Object[] novaMarca = {
                            DatabaseUtils.toTitleCase(nomeField.getText()),
                            DatabaseUtils.toTitleCase(paisOrigemField.getText()),
                    };
                    insert(novaMarca);
                    clearFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Verifica se os campos de nome e país de origem estão preenchidos.
     *
     * @return true se ambos os campos estiverem preenchidos, false caso contrário.
     */
    private boolean areFieldsFilled() {
        return !nomeField.getText().isEmpty() && !paisOrigemField.getText().isEmpty();
    }

    /**
     * Limpa os campos de nome e país de origem.
     */
    private void clearFields() {
        nomeField.setText("");
        paisOrigemField.setText("");
    }

    /**
     * Insere uma nova marca no banco de dados.
     *
     * @param novaMarca Um array contendo os dados da nova marca (nome e país de origem).
     */
    private void insert(Object[] novaMarca) {
        String SQL = "INSERT INTO Marca (Nome, Pais_Origem) VALUES (?, ?);";
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, (String) novaMarca[0]);
            pstmt.setString(2, (String) novaMarca[1]);
            pstmt.executeUpdate();

            conn.close();

            System.out.println("Novos valores inseridos na tabela Marcas: " + Arrays.toString(novaMarca));
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Nova marca inserida com sucesso!", "Inserção realizada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
