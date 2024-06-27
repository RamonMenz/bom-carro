package br.feevale;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Painel para listagem de marcas.
 *
 * @author Ramon Pedro Menz
 */
public class PainelListarMarcas extends JPanel {

    /**
     * Construtor para criar o painel de listagem de marcas.
     */
    public PainelListarMarcas() {
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        JLabel label = new JLabel("Listagem de Marcas");
        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Atualizar Listagem de Marcas");

        painelSuperior.setLayout(new GridLayout(1, 2));
        painelSuperior.add(label);
        painelSuperior.add(buttonPanel);

        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(button, BorderLayout.EAST);
        add(painelSuperior, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Nome", "Pais de Origem"};
        Object[][] data = select();

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        button.addActionListener(e -> atualizarTabela(model, columnNames));

        add(scrollPane);
        setVisible(true);
    }

    /**
     * Atualiza a tabela com os dados das marcas.
     *
     * @param model O modelo da tabela.
     * @param columnNames Os nomes das colunas da tabela.
     */
    private void atualizarTabela(DefaultTableModel model, String[] columnNames) {
        Object[][] data = select();
        model.setDataVector(data, columnNames);
    }

    /**
     * Realiza uma consulta no banco de dados para obter os dados das marcas.
     *
     * @return Uma matriz bidimensional contendo os dados das marcas.
     */
    public Object[][] select() {
        String SQL = "SELECT * FROM MARCA";
        try {
            Connection conn = DatabaseUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            ArrayList<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                Object[] line = {
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("pais_origem")
                };
                data.add(line);
            }
            conn.close();

            return data.toArray(new Object[0][]);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return new Object[0][];
    }
}
