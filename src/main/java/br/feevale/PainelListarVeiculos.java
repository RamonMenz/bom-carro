package br.feevale;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Painel para listagem de veículos.
 *
 * @author Ramon Pedro Menz
 */
public class PainelListarVeiculos extends JPanel {

    /**
     * Construtor para criar o painel de listagem de veículos.
     */
    public PainelListarVeiculos() {
        setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel();
        JLabel label = new JLabel("Listagem de Veículos");
        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("Atualizar Listagem de Veículos");

        painelSuperior.setLayout(new GridLayout(1, 2));
        painelSuperior.add(label);
        painelSuperior.add(buttonPanel);

        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(button, BorderLayout.EAST);
        add(painelSuperior, BorderLayout.NORTH);

        String[] columnNames = {"ID", "ID da Marca", "Nome", "Cor", "Ano de Fabricação", "Quilometragem Rodada", "Valor"};
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
     * Atualiza a tabela com os dados dos veículos.
     *
     * @param model O modelo da tabela.
     * @param columnNames Os nomes das colunas da tabela.
     */
    private void atualizarTabela(DefaultTableModel model, String[] columnNames) {
        Object[][] data = select();
        model.setDataVector(data, columnNames);
    }

    /**
     * Realiza uma consulta no banco de dados para obter os dados dos veículos.
     *
     * @return Uma matriz bidimensional contendo os dados dos veículos.
     */
    public Object[][] select() {
        String SQL = "SELECT * FROM VEICULO";
        try {
            Connection conn = DatabaseUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            ArrayList<Object> data = new ArrayList<>();
            while (rs.next()) {
                Object[] line = {
                        rs.getInt("id"),
                        rs.getInt("id_marca"),
                        rs.getString("nome_modelo"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getDouble("quilometragem_rodada"),
                        rs.getDouble("valor")
                };
                data.add(line);
            }
            conn.close();

            Object[][] dataArray = new Object[data.size()][];
            for (int i = 0; i < data.size(); i++) {
                dataArray[i] = (Object[]) data.get(i);
            }

            return dataArray;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}
