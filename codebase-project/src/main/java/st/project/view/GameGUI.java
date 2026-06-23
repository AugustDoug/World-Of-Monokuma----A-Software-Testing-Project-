package st.project.view;

import javax.swing.*;

import st.project.controller.GameController;
import st.project.model.Game;
import st.project.model.Ranking;
import st.project.model.User;
import st.project.model.UserManager;

import java.awt.*;

// pré condições: uma sequencia de comandos válidos que independentemente
// de qual caminho façam, o player permaneça dentro do mapa
// esta classe deve ser responsável por desenhar o player de forma que ele nunca saia do mapa

// pós condições: o player estar dentro dos limites do mapa
// independente de qual comando válido seja enviado

public class GameGUI extends JFrame {

    private JTextArea outputArea;

    public Game game;
    public GameController controller;
    
    private JTextField inputField;

    public JPanel mapaPanel;
    public JLabel[][] mapa;
    private JLabel statusLabel;

    private ImageIcon playerIcon;

    private UserManager manager;

    public GameGUI(User usuario, UserManager manager) {

        this.manager = manager;

        game = new Game(usuario);

        controller = new GameController(game);

        playerIcon = new ImageIcon(getClass().getResource("/images/monoplayer.png"));
        
        setTitle("World of Monokuma");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.EAST);

        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setPreferredSize(new Dimension(250, 0));
        add(scroll, BorderLayout.EAST);

        inputField = new JTextField();
        add(inputField, BorderLayout.SOUTH);

        statusLabel = new JLabel();

        add(statusLabel, BorderLayout.NORTH);
        configurarInput();
        
        

        criarMapaVisual();

        setVisible(true);
        print("Welcome to the World Monokuma!");
        print("Digite comandos como: go north, go south...");
        
        

        atualizarStatus();

        

        atualizarMapa();
        atualizarStatus();
    }

    
    public boolean executarMovimento(String direcao) {

        if(direcao == null) {
            return false;
        }

        boolean moved =
            game.movePlayer(direcao);

        atualizarMapa();

        if(!moved) {

            print("Não pode ir nessa direção!");

            return false;
        }

        print("Movendo para " + direcao);

        atualizarStatus();

        return true;
    }

    public boolean venceu(boolean resultado){
        if(resultado){
            
            return true;
        }else{
            return false;
        }
    }


    private void atualizarMapa() {

        int[][] m = game.getMapa();

        // recria o mapa visual se tamanho mudou
        if(
            mapa == null ||
            mapa.length != m.length ||
            mapa[0].length != m[0].length
        ) {

            if(mapaPanel != null) {
                remove(mapaPanel);
            }

            mapaPanel = new JPanel(
                new GridLayout(m.length, m[0].length)
            );

            mapa = new JLabel[m.length][m[0].length];

            for(int i = 0; i < m.length; i++) {

                for(int j = 0; j < m[i].length; j++) {

                    mapa[i][j] =
                        new JLabel(" ", SwingConstants.CENTER);

                    mapa[i][j].setBorder(
                        BorderFactory.createLineBorder(Color.BLACK)
                    );

                    mapa[i][j].setPreferredSize(
                        new Dimension(60, 60)
                    );

                    mapa[i][j].setOpaque(true);

                    mapaPanel.add(mapa[i][j]);
                }
            }

            add(mapaPanel, BorderLayout.CENTER);

            revalidate();
            repaint();
        }

        // atualiza cores
        for(int i = 0; i < m.length; i++) {

            for(int j = 0; j < m[i].length; j++) {

                mapa[i][j].setText("");
                mapa[i][j].setIcon(null);

                if(m[i][j] == 1) {
                    mapa[i][j].setBackground(Color.DARK_GRAY);
                }
                else if(m[i][j] == 2) {
                    mapa[i][j].setBackground(Color.GREEN);
                }
                else if(m[i][j] == 3) {
                    mapa[i][j].setBackground(Color.RED);
                }
                else if(m[i][j] == 4) {
                    mapa[i][j].setBackground(Color.YELLOW);
                }
                else {
                    mapa[i][j].setBackground(Color.WHITE);
                }
            }
        }

        mapa
            [game.getPlayerX()]
            [game.getPlayerY()]
            .setIcon(playerIcon);
    }

    

    private void print(String text) {
        outputArea.append(text + "\n");
    }

    //Listener tratamento para testes

    private void configurarInput() {
        inputField.addActionListener(e -> tratarEntrada());
    }

    public void tratarEntrada() {

        String comando = inputField.getText();

        inputField.setText("");


        if(comando.equalsIgnoreCase("ranking")) {

            mostrarRanking(manager);

            return;
        }


        String resposta =
            controller.processarComando(comando);


        print(resposta);


        atualizarMapa();

        atualizarStatus();

        manager.salvarPontuacoes();

        if(game.venceu) {


            manager.salvarPontuacoes();


            JOptionPane.showMessageDialog(
                this,
                "Parabéns! Você venceu o jogo!"
            );


            dispose();
        }
    }

    private void atualizarStatus() {

        String chave =
            game.temChave()
            ? "SIM"
            : "NÃO";

        statusLabel.setText(
            "Nível: " + game.getNivel()
            + " | Pontos: " + game.getPontuacao()
            + " | Chave: " + chave
        );
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }
    
    public void mostrarRanking( UserManager manager) {

        print("===== RANKING =====");

        for(User u :
            Ranking.ordenar(
                manager.getUsuarios()
            )
        ) {

            print(
                u.getLogin()
                + " | Pontos: "
                + u.getPontuacao()
                + " | Sessões: "
                + u.getSessoes()

            );
        }
    }

    private void criarMapaVisual() {

        if(mapaPanel != null) {
            remove(mapaPanel);
        }

        int linhas = game.getMapa().length;
        int colunas = game.getMapa()[0].length;

        mapaPanel = new JPanel(
            new GridLayout(linhas, colunas)
        );

        mapa = new JLabel[linhas][colunas];

        for(int i = 0; i < linhas; i++) {

            for(int j = 0; j < colunas; j++) {

                mapa[i][j] =
                    new JLabel(" ", SwingConstants.CENTER);

                mapa[i][j].setBorder(
                    BorderFactory.createLineBorder(Color.BLACK)
                );

                mapa[i][j].setPreferredSize(
                    new Dimension(60, 60)
                );

                mapa[i][j].setOpaque(true);

                mapaPanel.add(mapa[i][j]);
            }
        }

        add(mapaPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}