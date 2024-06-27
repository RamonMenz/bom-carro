package br.feevale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe utilitária para operações de banco de dados.
 *
 * @author Ramon Pedro Menz
 */
public class DatabaseUtils {
    private static final String URL = "jdbc:postgresql://localhost:5432/bom-carro";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root123";

    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return A conexão estabelecida.
     * @throws SQLException Se ocorrer um erro ao conectar.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Converte uma string para o formato de título (capitalizando a primeira letra de cada palavra).
     *
     * @param input A string de entrada.
     * @return A string convertida para título.
     */
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }
}
