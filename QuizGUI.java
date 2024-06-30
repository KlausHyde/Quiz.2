import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel, startPanel, quizPanel, endPanel, minigamePanel;
    private JLabel questionLabel, feedbackLabel, hintLabel;
    private JButton[] optionButtons;
    private JButton startButton, restartButton, hintButton, minigameButton;
    private ButtonGroup buttonGroup;
    private int currentQuestion;

    public QuizGUI() {
        setTitle("Quiz Interativo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        initStartPanel();
        initQuizPanel();
        initEndPanel();
        initMinigamePanel();
        
        add(mainPanel);
        setVisible(true);
    }

    private void initStartPanel() {
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setBackground(new Color(173, 216, 230));
        
        JLabel titleLabel = new JLabel("Bem-vindo ao Quiz Interativo!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        startPanel.add(titleLabel, BorderLayout.CENTER);

        startButton = new JButton("Começar");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Quiz");
                updateQuestion();
            }
        });
        startPanel.add(startButton, BorderLayout.SOUTH);
        
        mainPanel.add(startPanel, "Start");
    }

    private void initQuizPanel() {
        quizPanel = new JPanel(new BorderLayout());
        quizPanel.setBackground(new Color(255, 250, 205));
        
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        quizPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2));
        optionButtons = new JButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            optionButtons[i].addActionListener(new OptionButtonListener());
            optionsPanel.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }
        quizPanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        feedbackLabel.setForeground(Color.RED);
        feedbackPanel.add(feedbackLabel, BorderLayout.NORTH);
        
        hintButton = new JButton("Dica");
        hintButton.setFont(new Font("Arial", Font.BOLD, 18));
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hintLabel.setText(getHint());
            }
        });
        feedbackPanel.add(hintButton, BorderLayout.CENTER);

        hintLabel = new JLabel("", SwingConstants.CENTER);
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        feedbackPanel.add(hintLabel, BorderLayout.SOUTH);

        quizPanel.add(feedbackPanel, BorderLayout.SOUTH);
        
        mainPanel.add(quizPanel, "Quiz");
    }

    private void initEndPanel() {
        endPanel = new JPanel(new BorderLayout());
        endPanel.setBackground(new Color(144, 238, 144));

        JLabel endLabel = new JLabel("Parabéns! Você completou o quiz.", SwingConstants.CENTER);
        endLabel.setFont(new Font("Arial", Font.BOLD, 32));
        endPanel.add(endLabel, BorderLayout.CENTER);

        restartButton = new JButton("Reiniciar Quiz");
        restartButton.setFont(new Font("Arial", Font.BOLD, 24));
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetQuiz();
                cardLayout.show(mainPanel, "Start");
            }
        });
        endPanel.add(restartButton, BorderLayout.SOUTH);

        mainPanel.add(endPanel, "End");
    }

    private void initMinigamePanel() {
        minigamePanel = new JPanel();
        minigamePanel.setLayout(new GridLayout(2, 3));
        minigamePanel.setBackground(new Color(255, 228, 225));

        JLabel minigameLabel = new JLabel("Escolha um Minigame!", SwingConstants.CENTER);
        minigameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        minigamePanel.add(minigameLabel);

        for (int i = 1; i <= 5; i++) {
            JButton minigameButton = new JButton("Minigame " + i);
            minigameButton.setFont(new Font("Arial", Font.BOLD, 18));
            minigameButton.addActionListener(new MinigameButtonListener());
            minigamePanel.add(minigameButton);
        }
        
        mainPanel.add(minigamePanel, "Minigame");
    }

    private class OptionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedButton = (JButton) e.getSource();
            String selectedAnswer = selectedButton.getText();
            if (checkAnswer(selectedAnswer)) {
                feedbackLabel.setText("Correto!");
                nextQuestion();
            } else {
                feedbackLabel.setText("Errado! Reiniciando o quiz...");
                resetQuiz();
                cardLayout.show(mainPanel, "Start");
            }
        }
    }

    private class MinigameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Lógica para iniciar o minigame selecionado
            // Você pode adicionar a lógica específica para cada minigame aqui
            JOptionPane.showMessageDialog(null, "Minigame em construção!");
        }
    }

    private void updateQuestion() {
        feedbackLabel.setText("");
        hintLabel.setText("");
        switch (currentQuestion) {
            case 0:
                questionLabel.setText("Quem foi o primeiro homem a pisar na Lua?");
                optionButtons[0].setText("Alan Shepard");
                optionButtons[1].setText("Buzz Aldrin");
                optionButtons[2].setText("Yuri Gagarin");
                optionButtons[3].setText("Neil Armstrong");
                break;
            case 1:
                questionLabel.setText("Quem desenvolveu a teoria da evolução?");
                optionButtons[0].setText("Charles Darwin");
                optionButtons[1].setText("Isaac Newton");
                optionButtons[2].setText("Albert Einstein");
                optionButtons[3].setText("Galileu Galilei");
                break;
            case 2:
                questionLabel.setText("Qual é o maior planeta do Sistema Solar?");
                optionButtons[0].setText("Júpiter");
                optionButtons[1].setText("Saturno");
                optionButtons[2].setText("Urano");
                optionButtons[3].setText("Netuno");
                break;
            case 3:
                questionLabel.setText("Qual é a capital do Japão?");
                optionButtons[0].setText("Tóquio");
                optionButtons[1].setText("Kyoto");
                optionButtons[2].setText("Osaka");
                optionButtons[3].setText("Hiroshima");
                break;
            case 4:
                questionLabel.setText("Qual é o país mais populoso do mundo?");
                optionButtons[0].setText("China");
                optionButtons[1].setText("Índia");
                optionButtons[2].setText("Estados Unidos");
                optionButtons[3].setText("Indonésia");
                break;
            case 5:
                questionLabel.setText("Quem foi o primeiro presidente dos EUA?");
                optionButtons[0].setText("George Washington");
                optionButtons[1].setText("Thomas Jefferson");
                optionButtons[2].setText("John Adams");
                optionButtons[3].setText("Abraham Lincoln");
                break;
            case 6:
                questionLabel.setText("Qual é a fórmula da água?");
                optionButtons[0].setText("H2O");
                optionButtons[1].setText("CO2");
                optionButtons[2].setText("O2");
                optionButtons[3].setText("H2");
                break;
            case 7:
                questionLabel.setText("Qual é o metal mais abundante na crosta terrestre?");
                optionButtons[0].setText("Alumínio");
                optionButtons[1].setText("Ferro");
                optionButtons[2].setText("Cálcio");
                optionButtons[3].setText("Sódio");
                break;
            case 8:
                questionLabel.setText("Qual é a capital do Brasil?");
                optionButtons[0].setText("Brasília");
                optionButtons[1].setText("São Paulo");
                optionButtons[2].setText("Rio de Janeiro");
                optionButtons[3].setText("Salvador");
                break;
            case 9:
                questionLabel.setText("Quem escreveu 'Dom Quixote'?");
                optionButtons[0].setText("Miguel de Cervantes");
                optionButtons[1].setText("William Shakespeare");
                optionButtons[2].setText("Machado de Assis");
                optionButtons[3].setText("Gabriel García Márquez");
                break;
            case 10:
                questionLabel.setText("Qual é o maior oceano do mundo?");
                optionButtons[0].setText("Oceano Pacífico");
                optionButtons[1].setText("Oceano Atlântico");
                optionButtons[2].setText("Oceano Índico");
                optionButtons[3].setText("Oceano Ártico");
                break;
            case 11:
                questionLabel.setText("Quem pintou a 'Mona Lisa'?");
                optionButtons[0].setText("Leonardo da Vinci");
                optionButtons[1].setText("Vincent van Gogh");
                optionButtons[2].setText("Pablo Picasso");
                optionButtons[3].setText("Claude Monet");
                break;
            case 12:
                questionLabel.setText("Qual é o país mais extenso do mundo?");
                optionButtons[0].setText("Rússia");
                optionButtons[1].setText("Canadá");
                optionButtons[2].setText("China");
                optionButtons[3].setText("Estados Unidos");
                break;
            case 13:
                questionLabel.setText("Quem escreveu 'A Odisséia'?");
                optionButtons[0].setText("Homero");
                optionButtons[1].setText("Virgílio");
                optionButtons[2].setText("Ovídio");
                optionButtons[3].setText("Sófocles");
                break;
            case 14:
                questionLabel.setText("Qual é o ponto mais alto da Terra?");
                optionButtons[0].setText("Monte Everest");
                optionButtons[1].setText("K2");
                optionButtons[2].setText("Kangchenjunga");
                optionButtons[3].setText("Monte Kilimanjaro");
                break;
            case 15:
                questionLabel.setText("Qual é o maior deserto do mundo?");
                optionButtons[0].setText("Deserto do Saara");
                optionButtons[1].setText("Deserto da Arábia");
                optionButtons[2].setText("Deserto de Gobi");
                optionButtons[3].setText("Deserto da Antártica");
                break;
            case 16:
                questionLabel.setText("Quem foi o cientista que formulou a lei da gravitação universal?");
                optionButtons[0].setText("Isaac Newton");
                optionButtons[1].setText("Galileu Galilei");
                optionButtons[2].setText("Albert Einstein");
                optionButtons[3].setText("Niels Bohr");
                break;
            case 17:
                questionLabel.setText("Qual é a montanha mais alta da África?");
                optionButtons[0].setText("Monte Kilimanjaro");
                optionButtons[1].setText("Monte Quênia");
                optionButtons[2].setText("Monte Atlas");
                optionButtons[3].setText("Monte Drakensberg");
                break;
            case 18:
                questionLabel.setText("Qual é o rio mais longo do mundo?");
                optionButtons[0].setText("Rio Nilo");
                optionButtons[1].setText("Rio Amazonas");
                optionButtons[2].setText("Rio Yangtze");
                optionButtons[3].setText("Rio Mississippi");
                break;
            case 19:
                questionLabel.setText("Quem descobriu a penicilina?");
                optionButtons[0].setText("Alexander Fleming");
                optionButtons[1].setText("Louis Pasteur");
                optionButtons[2].setText("Robert Koch");
                optionButtons[3].setText("Joseph Lister");
                break;
            case 20:
                questionLabel.setText("Qual é a capital da Austrália?");
                optionButtons[0].setText("Camberra");
                optionButtons[1].setText("Sydney");
                optionButtons[2].setText("Melbourne");
                optionButtons[3].setText("Brisbane");
                break;
            case 21:
                questionLabel.setText("Qual é a menor unidade da matéria?");
                optionButtons[0].setText("Átomo");
                optionButtons[1].setText("Molécula");
                optionButtons[2].setText("Elétron");
                optionButtons[3].setText("Quark");
                break;
            case 22:
                questionLabel.setText("Qual é a maior floresta tropical do mundo?");
                optionButtons[0].setText("Amazônia");
                optionButtons[1].setText("Floresta do Congo");
                optionButtons[2].setText("Floresta de Bornéu");
                optionButtons[3].setText("Floresta de Daintree");
                break;
            case 23:
                questionLabel.setText("Qual é a capital da França?");
                optionButtons[0].setText("Paris");
                optionButtons[1].setText("Marselha");
                optionButtons[2].setText("Lyon");
                optionButtons[3].setText("Bordéus");
                break;
            case 24:
                questionLabel.setText("Quem pintou 'A Última Ceia'?");
                optionButtons[0].setText("Leonardo da Vinci");
                optionButtons[1].setText("Michelangelo");
                optionButtons[2].setText("Raphael");
                optionButtons[3].setText("Donatello");
                break;
            case 25:
                questionLabel.setText("Qual é a moeda oficial do Japão?");
                optionButtons[0].setText("Iene");
                optionButtons[1].setText("Yuan");
                optionButtons[2].setText("Won");
                optionButtons[3].setText("Dólar");
                break;
            case 26:
                questionLabel.setText("Qual é o maior animal terrestre?");
                optionButtons[0].setText("Elefante Africano");
                optionButtons[1].setText("Rinoceronte");
                optionButtons[2].setText("Hipopótamo");
                optionButtons[3].setText("Girafa");
                break;
            case 27:
                questionLabel.setText("Quem escreveu 'Hamlet'?");
                optionButtons[0].setText("William Shakespeare");
                optionButtons[1].setText("Christopher Marlowe");
                optionButtons[2].setText("John Milton");
                optionButtons[3].setText("Geoffrey Chaucer");
                break;
            case 28:
                questionLabel.setText("Qual é a língua mais falada no mundo?");
                optionButtons[0].setText("Chinês Mandarim");
                optionButtons[1].setText("Inglês");
                optionButtons[2].setText("Espanhol");
                optionButtons[3].setText("Hindi");
                break;
            case 29:
                questionLabel.setText("Qual é o planeta mais próximo do Sol?");
                optionButtons[0].setText("Mercúrio");
                optionButtons[1].setText("Vênus");
                optionButtons[2].setText("Terra");
                optionButtons[3].setText("Marte");
                break;
        }
    }

    private boolean checkAnswer(String answer) {
        switch (currentQuestion) {
            case 0: return answer.equals("Neil Armstrong");
            case 1: return answer.equals("Charles Darwin");
            case 2: return answer.equals("Júpiter");
            case 3: return answer.equals("Tóquio");
            case 4: return answer.equals("China");
            case 5: return answer.equals("George Washington");
            case 6: return answer.equals("H2O");
            case 7: return answer.equals("Alumínio");
            case 8: return answer.equals("Brasília");
            case 9: return answer.equals("Miguel de Cervantes");
            case 10: return answer.equals("Oceano Pacífico");
            case 11: return answer.equals("Leonardo da Vinci");
            case 12: return answer.equals("Rússia");
            case 13: return answer.equals("Homero");
            case 14: return answer.equals("Monte Everest");
            case 15: return answer.equals("Deserto da Antártica");
            case 16: return answer.equals("Isaac Newton");
            case 17: return answer.equals("Monte Kilimanjaro");
            case 18: return answer.equals("Rio Nilo");
            case 19: return answer.equals("Alexander Fleming");
            case 20: return answer.equals("Camberra");
            case 21: return answer.equals("Átomo");
            case 22: return answer.equals("Amazônia");
            case 23: return answer.equals("Paris");
            case 24: return answer.equals("Leonardo da Vinci");
            case 25: return answer.equals("Iene");
            case 26: return answer.equals("Elefante Africano");
            case 27: return answer.equals("William Shakespeare");
            case 28: return answer.equals("Chinês Mandarim");
            case 29: return answer.equals("Mercúrio");
            default: return false;
        }
    }

    private String getHint() {
        switch (currentQuestion) {
            case 0: return "Ele foi o comandante da Apollo 11.";
            case 1: return "Ele escreveu 'A Origem das Espécies'.";
            case 2: return "Ele é conhecido por sua Grande Mancha Vermelha.";
            case 3: return "Ela é a maior cidade do Japão.";
            case 4: return "É o país com a maior população do mundo.";
            case 5: return "Ele foi um dos Pais Fundadores dos EUA.";
            case 6: return "Sua fórmula é composta por dois átomos de hidrogênio e um de oxigênio.";
            case 7: return "É um metal leve e resistente à corrosão.";
            case 8: return "É a cidade construída para ser a capital do Brasil.";
            case 9: return "Ele era um escritor espanhol do século XVII.";
            case 10: return "Este oceano cobre mais de 63 milhões de milhas quadradas.";
            case 11: return "Ele também pintou 'A Última Ceia'.";
            case 12: return "Este país se estende por duas continentes.";
            case 13: return "Ele também escreveu 'A Ilíada'.";
            case 14: return "Este pico está localizado no Himalaia.";
            case 15: return "Este deserto está coberto de gelo.";
            case 16: return "Ele também é conhecido por suas três leis do movimento.";
            case 17: return "Este pico está localizado na Tanzânia.";
            case 18: return "Este rio está localizado no nordeste da África.";
            case 19: return "Ele descobriu este antibiótico por acidente.";
            case 20: return "É a capital da Austrália, não é Sydney.";
            case 21: return "É a menor unidade de um elemento químico.";
            case 22: return "Esta floresta está localizada na América do Sul.";
            case 23: return "É a cidade das luzes.";
            case 24: return "Este artista era um gênio do Renascimento italiano.";
            case 25: return "Esta moeda tem o símbolo ¥.";
            case 26: return "Este animal tem uma tromba longa.";
            case 27: return "Ele é considerado o maior dramaturgo de todos os tempos.";
            case 28: return "É a língua mais falada na China.";
            case 29: return "Este planeta é o menor do Sistema Solar.";
            default: return "";
        }
    }

    private void nextQuestion() {
        currentQuestion++;
        if (currentQuestion < 30) {
            updateQuestion();
        } else {
            cardLayout.show(mainPanel, "End");
        }
    }

    private void resetQuiz() {
        currentQuestion = 0;
        updateQuestion();
    }

    public static void main(String[] args) {
        new QuizGUI();
    }
}
