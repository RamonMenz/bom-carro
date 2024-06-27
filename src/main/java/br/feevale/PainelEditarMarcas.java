package br.feevale;

import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;

/**
 * Painel para edição de marcas.
 *
 * @author Ramon Pedro Menz
 */
public class PainelEditarMarcas extends JPanel {
    private final JComboBox<String> idsComboBox;
    private final JTextField nomeField;
    private final JTextField paisOrigemField;

    /**
     * Construtor para criar o painel de edição de marcas.
     */
    public PainelEditarMarcas() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Edite uma marca");
        add(label, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new GridLayout(1, 2));

        JLabel idsLabel = new JLabel("Selecione o ID da marca que você deseja editar:");
        idsComboBox = new JComboBox<>();

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel paisOrigemLabel = new JLabel("Pais de Origem");
        paisOrigemField = new JTextField();

        comboPanel.add(idsLabel);
        comboPanel.add(idsComboBox);
        mainPanel.add(comboPanel);
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);
        mainPanel.add(paisOrigemLabel);
        mainPanel.add(paisOrigemField);
        add(mainPanel);

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Editar Marca");
        JButton deleteButton = new JButton("Deletar Marca");
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
                        Object[] marcaEditada = {
                                editId,
                                DatabaseUtils.toTitleCase(nomeField.getText()),
                                DatabaseUtils.toTitleCase(paisOrigemField.getText())
                        };
                        update(editId, marcaEditada);
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
        loadMarcaIds();
    }

    /**
     * Carrega os IDs das marcas a partir do banco de dados e preenche o combobox com esses valores.
     */
    private void loadMarcaIds() {
        String SQL = "SELECT id FROM Marca";
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
     * Verifica se os campos de nome e país de origem estão preenchidos.
     *
     * @return true se ambos os campos estiverem preenchidos, false caso contrário.
     */
    private boolean areFieldsFilled() {
        return !nomeField.getText().isEmpty() && !paisOrigemField.getText().isEmpty();
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
     * Limpa os campos de nome e país de origem.
     */
    private void clearFields() {
        nomeField.setText("");
        paisOrigemField.setText("");
    }

    /**
     * Deleta uma marca com o ID especificado.
     *
     * @param id O ID da marca a ser deletada.
     */
    private void delete(int id) {
        String SQL = "DELETE FROM Marca WHERE id = ?";
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            conn.close();

            loadMarcaIds();
            System.out.println("ID da Marca deletada: " + id);
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Marca com ID " + id + " foi deletada.", "Marca Deletada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Atualiza os dados de uma marca com o ID especificado.
     *
     * @param id O ID da marca a ser atualizada.
     * @param marcaEditada Um array contendo os dados atualizados da marca.
     */
    private void update(int id, Object[] marcaEditada) {
        String SQL = "UPDATE Marca SET nome = ?, pais_origem = ? WHERE id = ?";
        try {
            Connection conn = DatabaseUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SQL);

            pstmt.setString(1, (String) marcaEditada[1]);
            pstmt.setString(2, (String) marcaEditada[2]);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();

            conn.close();

            loadMarcaIds();
            System.out.println("Marca atualizada: " + Arrays.toString(marcaEditada));
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Marca com ID " + id + " atualizada com sucesso!", "Marca Atualizada", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
