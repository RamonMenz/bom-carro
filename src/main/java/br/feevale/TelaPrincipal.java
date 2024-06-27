package br.feevale;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa a tela principal da aplicação "Bom Carro".
 *
 * @author Ramon Pedro Menz
 */
public class TelaPrincipal extends JFrame {

    /**
     * Construtor para criar a tela principal.
     */
    public TelaPrincipal() {
        setTitle("Bom Carro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new GridLayout());
        content.setBorder(BorderFactory.createEmptyBorder(25, 25, 25,25));
        getContentPane().add(content, BorderLayout.CENTER);

        JMenuBar menu = new HeaderMenu(content);
        setJMenuBar(menu);

        JPanel telaListagemVeiculos = new PainelListarVeiculos();

        content.removeAll();
        content.add(telaListagemVeiculos);
        content.revalidate();
        content.repaint();

        setVisible(true);
    }

    /**
     * Método principal para iniciar a aplicação.
     *
     * @param args Argumentos da linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
