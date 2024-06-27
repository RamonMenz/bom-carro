package br.feevale;

import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * Painel para edição de veículos.
 *
 * @author Ramon Pedro Menz
 */
public class PainelEditarVeiculos extends JPanel {
    private final JComboBox<String> idsComboBox;
    private final JTextField idMarcaField;
    private final JTextField nomeField;
    private final JTextField corField;
    private final JTextField anoFabricacaoField;
    private final JTextField quilometragemField;
    private final JTextField valorField;

    /**
     * Construtor para criar o painel de edição de veículos.
     */
    public PainelEditarVeiculos() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Edite um veículo");
        add(label, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JLabel idsLabel = new JLabel("Selecione o ID do veículo que você deseja editar:");
        idsComboBox = new JComboBox<>();

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

        mainPanel.add(idsLabel);
        mainPanel.add(idsComboBox);
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
        JButton editButton = new JButton("Editar Veículo");
        JButton deleteButton = new JButton("Deletar Veículo");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        deleteButton.addActionListener(e -> {
            int selectedId = getSelectedId();
            if (selectedId != -1) {
                delete(selectedId);
            } else {
                JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Por favor, selecione um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        editButton.addActionListener(e -> {
            int editId = getSelectedId();
            if (editId != -1) {
                try {
                    if (isEditFieldFilled() && areFieldsFilled()) {
                        Object[] veiculoEditado = {
                                Integer.parseInt(idMarcaField.getText()),
                                DatabaseUtils.toTitleCase(nomeField.getText()),
                                DatabaseUtils.toTitleCase(corField.getText()),
                                Integer.parseInt(anoFabricacaoField.getText()),
                                Double.parseDouble(quilometragemField.getText()),
                                Double.parseDouble(valorField.getText())
                        };
                        update(editId, veiculoEditado);
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Por favor, selecione um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        loadVeiculoIds();
    }

    /**
     * Carrega os IDs dos veículos a partir do banco de dados e preenche o combobox com esses valores.
     */
    private void loadVeiculoIds() {
        String SQL = "SELECT id FROM Veiculo";
        try {
            Connection conn = DatabaseUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            idsComboBox.removeAllItems();
            idsComboBox.addItem("");

            while (rs.next()) {
                idsComboBox.addItem(String.valueOf(rs.getInt("id")));
            }

            conn.close();
            idsComboBox.setSelectedIndex(0);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtém o ID selecionado no combobox.
     *
     * @return O ID selecionado ou -1 se nenhum ID estiver selecionado.
     */
    private int getSelectedId() {
        String selectedItem = (String) idsComboBox.getSelectedItem();
        if (selectedItem != null && !selectedItem.isEmpty()) {
            return Integer.parseInt(selectedItem);
        }
        return -1;
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
     * Verifica se o campo de edição (combobox) está preenchido.
     *
     * @return true se o campo de edição estiver preenchido, false caso contrário.
     */
    private boolean isEditFieldFilled() {
        return idsComboBox.getSelectedItem() != null && !idsComboBox.getSelectedItem().equals("");
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
     * Deleta um veículo com o ID especificado.
     *
     * @param id O ID do veículo a ser deletado.
     */
    private void delete(int id) {
        String SQL = "DELETE FROM Veiculo WHERE id = ?";
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            conn.close();

            loadVeiculoIds();
            System.out.println("ID do Veículo deletado: " + id);
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Veículo com ID " + id + " foi deletado.", "Veículo Deletado", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza os dados de um veículo com o ID especificado.
     *
     * @param id O ID do veículo a ser atualizado.
     * @param veiculoEditado Um array contendo os dados atualizados do veículo.
     */
    private void update(int id, Object[] veiculoEditado) {
        String SQL = "UPDATE Veiculo SET id_marca = ?, nome_modelo = ?, cor = ?, ano_fabricacao = ?, quilometragem_rodada = ?, valor = ? WHERE id = ?";
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, (int) veiculoEditado[0]);
            pstmt.setString(2, (String) veiculoEditado[1]);
            pstmt.setString(3, (String) veiculoEditado[2]);
            pstmt.setInt(4, (int) veiculoEditado[3]);
            pstmt.setDouble(5, (double) veiculoEditado[4]);
            pstmt.setDouble(6, (double) veiculoEditado[5]);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();

            conn.close();

            loadVeiculoIds();
            System.out.println("Veículo atualizado: " + Arrays.toString(veiculoEditado));
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Veículo com ID " + id + " atualizado com sucesso!", "Veículo Atualizado", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
