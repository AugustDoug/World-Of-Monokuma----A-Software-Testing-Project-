package st.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {

    private JTextArea outputArea;
    public Game game;
    
    private JTextField inputField;

    private JPanel mapaPanel;
    private JLabel[][] mapa;

    private ImageIcon playerIcon;

   

    public GameGUI() {
        game = new Game();

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

        configurarInput();
        
        inputField.addActionListener(e -> {
            String comando = inputField.getText();
            inputField.setText("");

            executarMovimento(processarComando(comando));
            
        });

        mapaPanel = new JPanel(new GridLayout(3, 3));
        int linhas = game.getMapa().length;
        int colunas = game.getMapa()[0].length;

        mapa = new JLabel[linhas][colunas];
        mapaPanel.setLayout(new GridLayout(linhas, colunas));

        for(int i = 0; i < linhas; i++) {
            for(int j = 0; j < colunas; j++) {
                
                mapa[i][j] = new JLabel(" ", SwingConstants.CENTER);
                mapa[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                mapa[i][j].setPreferredSize(new Dimension(60, 60));
                mapa[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                mapa[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                mapaPanel.add(mapa[i][j]);
            }
        }
        

        
        JPanel container = new JPanel(new FlowLayout());
        container.add(mapaPanel);
        add(container, BorderLayout.CENTER);

        setVisible(true);
        print("Welcome to the World Monokuma!");
        print("Digite comandos como: go north, go south...");
        
        atualizarMapa();
    }

    
    public boolean executarMovimento(String direcao) {

        boolean temp = false;

        if(direcao == null){
            return temp;
        }
        boolean moved = game.movePlayer(direcao);

        atualizarMapa();

        if (!moved) {
            print("🚫 Não pode ir nessa direção!");
            temp = false;
        } else {
            print("Movendo para " + direcao);
            temp = true;
        }

        if (venceu(game.venceu())) {
            print("🎉 Você venceu!");
            return true;
        }else{
            return temp;
        }

    }

    public boolean venceu(boolean resultado){
        if(resultado){
            
            return true;
        }else{
            return false;
        }
    }

    public String processarComando(String input) {
        print("> " + input);

        if (input == null || input.trim().isEmpty()) {
            print("❌ Comando vazio!");
            return null;
        }

        String[] partes = input.trim().toLowerCase().split(" ");

        if (partes.length != 2) {
            print("❌ Comando inválido!");
            mostrarAjuda();
            return null;
        }

        if (!partes[0].equals("go")) {
            print("❌ Use o comando 'go'");
            mostrarAjuda();
            return null;
        }

        String direcao = partes[1];

        if (!direcaoValida(direcao)) {
            print("❌ Direção inválida!");
            mostrarAjuda();
            return null;
        }

        return direcao; // ✅ comando válido
    }


    private boolean direcaoValida(String d) {
        return d.equals("north") || d.equals("south") ||
            d.equals("east") || d.equals("west");
    }

    private void mostrarAjuda() {
        print("Comandos possíveis:");
        print("go north");
        print("go south");
        print("go east");
        print("go west");
    }


    private void atualizarMapa() {
        int[][] m = game.getMapa();

        for(int i = 0; i < m.length; i++) {

            for(int j = 0; j < m[i].length; j++) {

                mapa[i][j].setText("");
                mapa[i][j].setIcon(null);

                

                if (m[i][j] == 1) {
                    mapa[i][j].setBackground(Color.DARK_GRAY); // parede
                } else if (m[i][j] == 2) {
                    mapa[i][j].setBackground(Color.GREEN); // objetivo
                } else {
                    mapa[i][j].setBackground(Color.WHITE);
                }

                mapa[i][j].setOpaque(true);
            }
        }

        // jogador
        mapa[game.getX()][game.getY()].setIcon(playerIcon);
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

        executarMovimento(processarComando(comando));
    }

    public JTextField getInputField() {
        return inputField;
    }
    
}