package br.feevale;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa o menu de cabeçalho da aplicação.
 *
 * @author Ramon Pedro Menz
 */
public class HeaderMenu extends JMenuBar {

    /**
     * Construtor para criar o menu de cabeçalho.
     *
     * @param content O painel de conteúdo onde as ações do menu serão aplicadas.
     */
    public HeaderMenu(JPanel content) {
        JMenu menuVeiculos = new JMenu("Veículos");
        JMenu menuMarcas = new JMenu("Marcas");
        JMenu menuOutros = new JMenu("Outras Opções");

        JMenuItem itemListarVeiculos = new JMenuItem("Listar Veículos");
        JMenuItem itemInserirVeiculo = new JMenuItem("Inserir Veículo");
        JMenuItem itemEditarVeiculo = new JMenuItem("Editar Veículo");
        JMenuItem itemListarMarcas = new JMenuItem("Listar Marcas");
        JMenuItem itemInserirMarca = new JMenuItem("Inserir Marca");
        JMenuItem itemEditarMarca = new JMenuItem("Editar Marca");
        JMenuItem itemListaSimples = new JMenuItem("Listagem Simplificada");
        JMenuItem itemSair = new JMenuItem("Sair");

        menuVeiculos.add(itemListarVeiculos);
        menuVeiculos.add(itemInserirVeiculo);
        menuVeiculos.add(itemEditarVeiculo);
        menuMarcas.add(itemListarMarcas);
        menuMarcas.add(itemInserirMarca);
        menuMarcas.add(itemEditarMarca);
        menuOutros.add(itemListaSimples);
        menuOutros.add(itemSair);

        add(menuVeiculos);
        add(menuMarcas);
        add(menuOutros);

        itemListarVeiculos.addActionListener(e -> {
            content.removeAll();
            content.setLayout(new GridLayout());
            content.add(new PainelListarVeiculos());
            content.revalidate();
            content.repaint();
        });

        itemInserirVeiculo.addActionListener(e -> {
            JPanel painelSuperior = new JPanel();
            painelSuperior.setLayout(new GridLayout(1, 2, 100, 0));
            painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
            painelSuperior.add(new PainelInserirVeiculos());
            painelSuperior.add(new PainelListarMarcas());

            content.removeAll();
            content.setLayout(new GridLayout(2, 1));
            content.add(painelSuperior);
            content.add(new PainelListarVeiculos());
            content.revalidate();
            content.repaint();
        });

        itemEditarVeiculo.addActionListener(e -> {
            JPanel painelSuperior = new JPanel();
            painelSuperior.setLayout(new GridLayout(1, 2, 100, 0));
            painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
            painelSuperior.add(new PainelEditarVeiculos());
            painelSuperior.add(new PainelListarMarcas());

            content.removeAll();
            content.setLayout(new GridLayout(2, 1));
            content.add(painelSuperior);
            content.add(new PainelListarVeiculos());
            content.revalidate();
            content.repaint();
        });

        itemListarMarcas.addActionListener(e -> {
            content.removeAll();
            content.setLayout(new GridLayout());
            content.add(new PainelListarMarcas());
            content.revalidate();
            content.repaint();
        });

        itemInserirMarca.addActionListener(e -> {
            JPanel painelSuperior = new JPanel();
            painelSuperior.setLayout(new GridLayout(1, 2, 100, 0));
            painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
            painelSuperior.add(new PainelInserirMarcas());
            painelSuperior.add(new PainelListarMarcas());

            content.removeAll();
            content.setLayout(new GridLayout(2, 1));
            content.add(painelSuperior);
            content.revalidate();
            content.repaint();
        });

        itemEditarMarca.addActionListener(e -> {
            JPanel painelSuperior = new JPanel();
            painelSuperior.setLayout(new GridLayout(1, 2, 100, 0));
            painelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
            painelSuperior.add(new PainelEditarMarcas());
            painelSuperior.add(new PainelListarMarcas());

            content.removeAll();
            content.setLayout(new GridLayout(2, 1));
            content.add(painelSuperior);
            content.revalidate();
            content.repaint();
        });

        itemListaSimples.addActionListener(e -> {
            content.removeAll();
            content.setLayout(new GridLayout());
            content.add(new PainelListagemSimplificada());
            content.revalidate();
            content.repaint();
        });

        itemSair.addActionListener(e -> System.exit(0));
    }
}
