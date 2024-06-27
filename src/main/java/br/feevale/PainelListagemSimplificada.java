package br.feevale;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Painel para listagem simplificada de veículos.
 *
 * @author Ramon Pedro Menz
 */
public class PainelListagemSimplificada extends JPanel {

    /**
     * Construtor para criar o painel de listagem simplificada de veículos.
     */
    public PainelListagemSimplificada() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Listagem de Veículos Simplificada");
        add(label, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Marca", "Modelo", "Cor", "Ano de Fabricação", "Quilometragem Rodada", "Valor"};
        Object[][] data = select();

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
        setVisible(true);
    }

    /**
     * Realiza uma consulta no banco de dados para obter os dados dos veículos.
     *
     * @return Uma matriz bidimensional contendo os dados dos veículos.
     */
    public Object[][] select() {
        String SQL = "SELECT v.Id, m.Nome, v.Nome_Modelo, v.Cor, v.Ano_Fabricacao, " +
                "v.Quilometragem_Rodada, v.Valor FROM Veiculo v JOIN Marca m ON v.Id_Marca = m.Id;";
        try {
            Connection conn = DatabaseUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            ArrayList<Object> data = new ArrayList<>();
            while (rs.next()) {
                Object[] line = {
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("nome_modelo"),
                        rs.getString("cor"),
                        rs.getInt("ano_fabricacao"),
                        rs.getDouble("quilometragem_rodada") + " Km",
                        "R$ " + rs.getDouble("valor")
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
