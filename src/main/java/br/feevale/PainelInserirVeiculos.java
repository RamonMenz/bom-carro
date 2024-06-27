package br.feevale;

import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * Painel para inserção de novos veículos.
 *
 * @author Ramon Pedro Menz
 */
public class PainelInserirVeiculos extends JPanel {
    private final JTextField idMarcaField;
    private final JTextField nomeField;
    private final JTextField corField;
    private final JTextField anoFabricacaoField;
    private final JTextField quilometragemField;
    private final JTextField valorField;

    /**
     * Construtor para criar o painel de inserção de veículos.
     */
    public PainelInserirVeiculos() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Insira um novo veículo");
        add(label, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel idMarcaLabel = new JLabel("ID da Marca:");
        idMarcaField = new JTextField();
        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel corLabel = new JLabel("Cor:");
        corField = new JTextField();
        JLabel anoFabricacaoLabel = new JLabel("Ano de Fabricação:");
        anoFabricacaoField = new JTextField();
        JLabel quilometragemLabel = new JLabel("Quilometragem Rodada:");
        quilometragemField = new JTextField();
        JLabel valorLabel = new JLabel("Valor:");
        valorField = new JTextField();

        mainPanel.add(new JPanel());
        mainPanel.add(new JPanel());
        mainPanel.add(idMarcaLabel);
        mainPanel.add(idMarcaField);
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);
        mainPanel.add(corLabel);
        mainPanel.add(corField);
        mainPanel.add(anoFabricacaoLabel);
        mainPanel.add(anoFabricacaoField);
        mainPanel.add(quilometragemLabel);
        mainPanel.add(quilometragemField);
        mainPanel.add(valorLabel);
        mainPanel.add(valorField);
        add(mainPanel);

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Inserir Veículo");
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            if (areFieldsFilled()) {
                try {
                    Object[] novoVeiculo = {
                            Integer.parseInt(idMarcaField.getText()),
                            DatabaseUtils.toTitleCase(nomeField.getText()),
                            DatabaseUtils.toTitleCase(corField.getText()),
                            Integer.parseInt(anoFabricacaoField.getText()),
                            Double.parseDouble(quilometragemField.getText()),
                            Double.parseDouble(valorField.getText())
                    };
                    insert(novoVeiculo);
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
     * Verifica se todos os campos obrigatórios estão preenchidos.
     *
     * @return true se todos os campos estiverem preenchidos, false caso contrário.
     */
    private boolean areFieldsFilled() {
        return !idMarcaField.getText().isEmpty() &&
                !nomeField.getText().isEmpty() &&
                !corField.getText().isEmpty() &&
                !anoFabricacaoField.getText().isEmpty() &&
                !quilometragemField.getText().isEmpty() &&
                !valorField.getText().isEmpty();
    }

    /**
     * Limpa todos os campos de entrada.
     */
    private void clearFields() {
        idMarcaField.setText("");
        nomeField.setText("");
        corField.setText("");
        anoFabricacaoField.setText("");
        quilometragemField.setText("");
        valorField.setText("");
    }

    /**
     * Insere um novo veículo no banco de dados.
     *
     * @param novoVeiculo Um array contendo os dados do novo veículo.
     */
    private void insert(Object[] novoVeiculo) {
        String SQL = "INSERT INTO Veiculo (ID_Marca, Nome_Modelo, Cor, Ano_Fabricacao, Quilometragem_Rodada, Valor) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, (int) novoVeiculo[0]);
            pstmt.setString(2, (String) novoVeiculo[1]);
            pstmt.setString(3, (String) novoVeiculo[2]);
            pstmt.setInt(4, (int) novoVeiculo[3]);
            pstmt.setDouble(5, (double) novoVeiculo[4]);
            pstmt.setDouble(6, (double) novoVeiculo[5]);
            pstmt.executeUpdate();

            conn.close();

            System.out.println("Novos valores inseridos na tabela Veiculos: " + Arrays.toString(novoVeiculo));
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Novo veículo inserido com sucesso!", "Inserção realizada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
