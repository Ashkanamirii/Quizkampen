package Client;

import Config.Question;
import Server.Quizproperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by Axel Jeansson, Christoffer Grännby, Salem Koldzo, Iryna Gnatenko,
 * Date: 2020-11-12
 * Time: 13:47
 * Project: Quizkampen
 * Copyright: MIT
 */
public class Client implements ActionListener {

    private JFrame frame = new JFrame("QuizkampenClient");
    private JLabel messageLabel = new JLabel("");
    private JPanel questionPanel = new JPanel();
    public JTextArea questionArea = new JTextArea("");
    private JTextField category = new JTextField("");
    private JButton b1 = new JButton();
    private JButton b2 = new JButton();
    private JButton b3 = new JButton();
    private JButton b4 = new JButton();
    private JPanel cardPanel;
    private JPanel labelPanel;
    private CardLayout cardLayout;
    JPanel gamePanel = new JPanel();
    JTextField resultText = new JTextField();
    JPanel resultPanel = new JPanel();
    List<Question> q;

    int score;
    int round = 1;

    //ROUNDS
    JPanel newRound = new JPanel();
    JPanel players = new JPanel();
    JPanel round1 = new JPanel();
    JPanel round2 = new JPanel();
    JPanel round3 = new JPanel();
    JPanel result = new JPanel();
    ImageIcon reindeer = new ImageIcon("images\\reindeer.png");
    ImageIcon skull = new ImageIcon("images\\skull.png");
    JLabel playerOneIcon = new JLabel();
    JLabel playerOneName = new JLabel();
    JLabel playerTwoIcon = new JLabel();
    JLabel playerTwoName = new JLabel();
    JLabel versus = new JLabel("-");
    JLabel r1 = new JLabel("Runda 1");
    JLabel r2 = new JLabel("Runda 2");
    JLabel r3 = new JLabel("Runda 3");
    JLabel resultLabel = new JLabel("Resultat");
    JButton startNewRound = new JButton("");
    JTextField p1r1 = new JTextField("Resultat runda 1");
    JTextField p2r1 = new JTextField("Resultat runda 1");
    JTextField p1r2 = new JTextField("Resultat runda 2");
    JTextField p2r2 = new JTextField("Resultat runda 2");
    JTextField p1r3 = new JTextField("Resultat runda 3");
    JTextField p2r3 = new JTextField("Resultat runda 3");
    JTextField p1result = new JTextField("Slutresultat p1");
    JTextField p2result = new JTextField("Slutresultat p2");

     //______________________________________
    //Hårdkodade frågor (ersätt med frågor från databas?)
    private String[] questions = {"Vad heter vår lärare i OOP?", "Vad heter skolan?", "Vilken dag är bäst?", "Är java kul?", "Fungerar detta?"};

    private String[][] options = {
            {"Sigrun", "Mahmud", "Jonas", "Carl XVI Gustaf"},
            {"Chalmers", "Nackademin", "Handels", "Harvard"},
            {"Söndag", "Tisdag", "Lördag", "Måndag"},
            {"Nej", "Nej", "Nej", "Ibland"},
            {"Nej", "Kanske", "Ja", "Verkligen inte"}};

    private String[] categories = {"Java OOP", "Skolor", "Dagar", "Skoj", "Test"};

    private String[] answer = {"Sigrun", "Nackademin", "Lördag", "Ibland", "Ja"};

    //___________________________________
    private int correctGuesses;
    private int index = 0;
    private int index2 = 5;
    private int index3 = 10;
    private int nmbrOfQs = questions.length;// (Ersätt med längd på array/listan med frågor)


    private int amountOfRounds;



    private static int PORT = 23325;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    String userID = "";
    String opponentUserID = "";
    int player1round1;
    int player2round1;
    int player1round2;
    int player2round2;
    int player1round3;
    int player2round3;

    /**
     * Constructs the client by connecting to a server, laying out the
     * GUI and registering GUI listeners.
     */
    public Client(String serverAddress) {
        System.out.println("tjooo");
        try {
            System.out.println("hej");
            socket = new Socket(serverAddress, PORT);
            System.out.println("test");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("test2");

            System.out.println("test3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("apa");



        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        labelPanel = new JPanel();

        // Layout GUI
        frame.setLayout(new BorderLayout());
        labelPanel.setBackground(Color.lightGray);
        labelPanel.setSize(500, 150);
        messageLabel.setBackground(Color.lightGray);
        labelPanel.add(messageLabel);

        cardPanel.add(gamePanel, "game");
        cardPanel.add(resultPanel, "result");
        cardPanel.add(newRound, "newRound");
        gamePanel.setBackground(Color.black);
        gamePanel.setLayout(null);

        questionPanel.setBounds(25, 10, 425, 300);
        questionPanel.setLayout(null);
        questionPanel.setBackground(new Color(153, 216, 240));
        questionPanel.add(category);
        questionPanel.add(questionArea);

        questionArea.setBounds(50, 120, 325, 150);
        questionArea.setBackground(new Color(153, 216, 240));
        questionArea.setForeground(Color.BLACK);
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("Dialog", Font.BOLD, 15));

        category.setBounds(150, 0, 125, 30);
        category.setBackground(Color.WHITE);
        category.setEditable(false);
        category.setFont(new Font("Dialog", Font.BOLD, 20));

        b1.setBounds(25, 325, 200, 100);
        b1.setFont(new Font("Dialog", Font.BOLD, 10));
        b1.setFocusable(false);
        b1.addActionListener(this);

        b2.setBounds(250, 325, 200, 100);
        b2.setFont(new Font("Dialog", Font.BOLD, 10));
        b2.setFocusable(false);
        b2.addActionListener(this);

        b3.setBounds(25, 450, 200, 100);
        b3.setFont(new Font("Dialog", Font.BOLD, 10));
        b3.setFocusable(false);
        b3.addActionListener(this);

        b4.setBounds(250, 450, 200, 100);
        b4.setFont(new Font("Dialog", Font.BOLD, 10));
        b4.setFocusable(false);
        b4.addActionListener(this);

        gamePanel.add(questionPanel);
        gamePanel.add(b1);
        gamePanel.add(b2);
        gamePanel.add(b3);
        gamePanel.add(b4);

        frame.getContentPane().add(labelPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

    }

    public void play() throws Exception {
        cardLayout.show(cardPanel, "newRound");

        Quizproperties quizSettings = new Quizproperties();
        amountOfRounds = Integer.parseInt(quizSettings.getNumberOfRounds());

        try {
            startNewRound.setEnabled(true);
            startNewRound.setText("Starta runda 1");
            Object response;

            response = in.readObject();
            System.out.println(response);

            if (((String) response).startsWith("WELCOME")) {
                newRound();
                userID = ((String) response).substring(8);
                //opponentUserID = (userID.equals("playerOne") ? "playerTwo" : "playr");
                if (userID.equals("playerOne")) {
                    opponentUserID = "playerTwo";
                } else {
                    opponentUserID = "playerOne";
                }
                frame.setTitle("QuizkampenClient - Player " + userID);
            }

            while (true) {
                response = in.readObject();
                if (response instanceof List<?>) {
                    createQuestions((List<Question>) response);
                    System.out.println("fråga 1");
                } else if (response instanceof String) {

                    System.out.println("Testa");
                    if (response == null)
                        break;

                    if (((String) response).startsWith("YOUR_TURN")) {
                        System.out.println(userID + " startar");
                        System.out.println(response);
                        newRound();
                        startNewRound.setEnabled(true);

                    } else if (((String) response).startsWith("ROUND_OVER")) {
                        System.out.println("Båda har spelat.");
                    } else if (((String) response).startsWith("MESSAGE")) {
                        messageLabel.setText(((String) response).substring(8));
                    } else if (((String) response).startsWith("RESULT")) {
                        System.out.println("Båda har spelat.");
                        System.out.println("response-->" + response);
                        out.writeObject("ENDROUND");
                        response = ((String) response).substring(6);
                        response = ((String) response).replace("[", "");
                        response = ((String) response).replace("]", "");
                        System.out.println("list" + response);

                        String[] resultList = ((String) response).split(",");
                        System.out.println("resultList" + resultList);
                        if (resultList.length > 5){
                            startNewRound.setText("Slutspelat");
                            startNewRound.setEnabled(false);
                        }
                        else if (resultList.length >3){
                            startNewRound.setText("Starta runda 3");
                        }
                        else if (resultList.length >1){
                            startNewRound.setText("Starta runda 2");
                        }
                        else startNewRound.setText("Starta runda 1");
                        if (resultList.length == 2) {
                            startNewRound.setEnabled(true);
                            System.out.println("Runda ett är färdigspelad");
                            player1round1 = Integer.parseInt(resultList[0].trim());
                            player2round1 = Integer.parseInt(resultList[1].trim());

                            if (correctGuesses == player1round1) {
                                p2r1.setText(String.valueOf(player2round1));
                            } else
                                p2r1.setText(String.valueOf(player1round1));
                        }
                        if (resultList.length == 4) {
                            startNewRound.setEnabled(true);
                            System.out.println("Runda två är färdigspelad");
                            player1round2 = Integer.parseInt(resultList[3].trim());
                            player2round2 = Integer.parseInt(resultList[2].trim());
                            if (correctGuesses == player1round2) {
                                p2r2.setText(String.valueOf(player2round2));
                            } else
                                p2r2.setText(String.valueOf(player1round2));
                        }
                        if (resultList.length == 6) {
                            System.out.println("Matchen är färdigspelad!");
                            startNewRound.setEnabled(false);
                            player1round3 = Integer.parseInt(resultList[4].trim());
                            player2round3 = Integer.parseInt(resultList[5].trim());
                            int endScore1 = player1round1+player1round2+player1round3;
                            int endScore2 = player2round1+player2round2+player2round3;
                            if (correctGuesses == player1round3) {
                                p2r3.setText(String.valueOf(player2round3));
                                p2result.setText(String.valueOf(endScore2));
                            } else
                               p2result.setText(String.valueOf(endScore1));
                                p2r3.setText(String.valueOf(player1round3));



                            if(round == amountOfRounds && endScore1 > endScore2 || round == amountOfRounds && endScore1 < endScore2){
                                startNewRound.setEnabled(false);
                            }
                            else{
                                //Nothing
                            }


                            if (endScore1 > endScore2) {
                                if (endScore1 == score) {
                                    p1result.setText(String.valueOf(score));
                                    p2result.setText(String.valueOf(endScore2));
                                    System.out.println("You win");
                                //    displayResult("Congrats... You've won! Your score was: " + endScore1 + "\nYour opponents score was: " + endScore2);
                                    frame.setTitle("WON");
                                    startNewRound.setText("You've won!");
                                } else {
                                    p1result.setText(String.valueOf(endScore2));
                                    p2result.setText(String.valueOf(endScore1));
                                    System.out.println("You lose");
                                 //   displayResult("Sorry... You've lost! Your score was: " + endScore2 + "\nYour opponents score was: " + endScore1);
                                    frame.setTitle("LOST");
                                    startNewRound.setText("You lose");
                                }
                            } else if (endScore1 < endScore2) {
                                if (endScore1 == score) {
                                    System.out.println("You lose");
                               //     displayResult("Sorry... You've lost!");
                                    frame.setTitle("LOST");
                                    startNewRound.setText("You lose");
                                } else {
                                    System.out.println("You win");
                             //       displayResult("Congrats... You've WON");
                                    frame.setTitle("WON");
                                    startNewRound.setText("You've won!");
                                }
                            } else {
                                System.out.println("Draw");
                            //    displayResult("It's a Draw!");
                                startNewRound.setText("It's a Draw!");
                            }
                        break;}
                    }
                }
            }
        }finally {
            socket.close();
        }
    }



    private void displayResult(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void newRound() {
        cardLayout.show(cardPanel, "newRound");

        newRound.setLayout(null);
        newRound.setSize(500, 750);
        newRound.setOpaque(false);

        players.setBounds(0, 0, 500, 220);
        players.setBackground(Color.GREEN);
        players.setLayout(null);


        playerOneIcon.setBounds(35, 20, 150, 150);
        if (userID.equals("playerOne"))
            playerOneIcon.setIcon(reindeer);
        else
            playerOneIcon.setIcon(skull);
        playerOneName.setBounds(35, 170, 150, 30);
        playerOneName.setHorizontalAlignment(JLabel.CENTER);
        playerOneName.setText(userID);
        playerOneName.setFont(new Font("Dialog", Font.BOLD, 20));

        versus.setBounds(230, 80, 40, 20);
        versus.setFont(new Font("Dialog", Font.BOLD, 70));

        playerTwoIcon.setBounds(300, 20, 150, 150);
        if (userID.equals("playerOne"))
            playerTwoIcon.setIcon(skull);
        else
            playerTwoIcon.setIcon(reindeer);
        playerTwoName.setBounds(300, 170, 150, 30);
        playerTwoName.setText(opponentUserID);
        playerTwoName.setHorizontalAlignment(JLabel.CENTER);
        playerTwoName.setFont(new Font("Dialog", Font.BOLD, 20));

        players.add(playerOneIcon);
        players.add(playerOneName);
        players.add(versus);
        players.add(playerTwoIcon);
        players.add(playerTwoName);

        round1.setBounds(0, 220, 500, 100);
        GUIPerRound(round1, r1, p1r1, p2r1);
        round1.setBackground(Color.BLUE);
        round1.add(r1);
        round1.add(p1r1);
        round1.add(p2r1);

        round2.setBounds(0, 320, 500, 100);
        GUIPerRound(round2, r2, p1r2, p2r2);
        round2.add(r2);
        round2.add(p1r2);
        round2.add(p2r2);
        round2.setBackground(Color.RED);

        round3.setBounds(0, 420, 500, 100);
        GUIPerRound(round3, r3, p1r3, p2r3);
        round3.add(r3);
        round3.add(p1r3);
        round3.add(p2r3);
        round3.setBackground(Color.BLUE);

        result.setBounds(0, 520, 500, 180);
        result.setLayout(null);
        result.setBackground(Color.BLACK);

        startNewRound.setBounds(165, 80, 150, 50);
        startNewRound.setBackground(Color.WHITE);
        startNewRound.setEnabled(false);
        startNewRound.addActionListener(e -> {
            index = 0;
            index2 = 5;
            index3 = 10;
            correctGuesses = 0;
            try {
                // createQuestions();
                nextQ();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        resultLabel.setBounds(185, 20, 115, 50);
        resultLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultLabel.setForeground(Color.WHITE);
        p1result.setBounds(35, 20, 150, 50);
        p1result.setEditable(false);
        p1result.setHorizontalAlignment(JTextField.CENTER);
        p2result.setBounds(300, 20, 150, 50);
        p2result.setEditable(false);
        p2result.setHorizontalAlignment(JTextField.CENTER);
        result.add(resultLabel);
        result.add(p1result);
        result.add(p2result);
        result.add(startNewRound);

        newRound.add(players);
        newRound.add(round1);
        newRound.add(round2);
        newRound.add(round3);
        newRound.add(result);

    }

    private void GUIPerRound(JPanel round1, JLabel r1, JTextField p1r1, JTextField p2r1) {
        round1.setLayout(null);
        r1.setBounds(200, 35, 100, 50);
        r1.setFont(new Font("Dialog", Font.BOLD, 20));
        r1.setForeground(Color.WHITE);
        p1r1.setBounds(35, 35, 150, 50);
        p1r1.setEditable(false);
        p1r1.setHorizontalAlignment(JTextField.CENTER);
        p2r1.setBounds(300, 35, 150, 50);
        p2r1.setEditable(false);
        p2r1.setHorizontalAlignment(JTextField.CENTER);
    }

    private void createQuestions(List<Question> questionList) {
        this.q = questionList;
        System.out.println("funka nu!");
    }

    public void nextQ() throws IOException {

        cardLayout.show(cardPanel, "game");

        if (index == questions.length) {
            System.out.println("Slut " + correctGuesses);
            out.writeObject("ROUND_OVER " + correctGuesses);
            startNewRound.setEnabled(false);
            if (round == 1) {
                score = correctGuesses;
                p1r1.setText(String.valueOf(correctGuesses));
                round++;
                newRound();
            } else if (round == 2) {
                score += correctGuesses;
                p1r2.setText(String.valueOf(correctGuesses));
                round++;
                newRound();
            } else if (round == 3) {
                startNewRound.setText("Slutspelat");
                startNewRound.setEnabled(false);
                score += correctGuesses;
                p1r3.setText(String.valueOf(correctGuesses));
                round++;
                newRound();
            } else if (round > 3) {
                p1result.setText(String.valueOf(score));
                displayResult("Hej");
            }
        }

        if (round == 1) {
            if (index < categories.length) {
                roundGUISetting(index);
            }
        } else if (round == 2){
            if (index2 < 10) {
                roundGUISetting(index2);
            }

        } else if (round == 3) {
            if (index3 < 15) {
                roundGUISetting(index3);
            }
        }
    }

    private void roundGUISetting(int index) {
        category.setText(q.get(index).getCategory());
        questionArea.setText(q.get(index).getQuestion());

        questionArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        category.setHorizontalAlignment(JTextField.CENTER);

        b1.setBackground(Color.DARK_GRAY);
        b1.setForeground(Color.WHITE);
        b2.setBackground(Color.DARK_GRAY);
        b2.setForeground(Color.WHITE);
        b3.setBackground(Color.DARK_GRAY);
        b3.setForeground(Color.WHITE);
        b4.setBackground(Color.DARK_GRAY);
        b4.setForeground(Color.WHITE);

        b1.setText(q.get(index).getAnswers().get(0));
        b1.setEnabled(true);
        b2.setText(q.get(index).getAnswers().get(1));
        b2.setEnabled(true);
        b3.setText(q.get(index).getAnswers().get(2));
        b3.setEnabled(true);
        b4.setText(q.get(index).getAnswers().get(3));
        b4.setEnabled(true);
    }

    public void showAnswer() {
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);

        Timer pause = new Timer(200, e -> {

            b1.setEnabled(true);
            b2.setEnabled(true);
            b3.setEnabled(true);
            b4.setEnabled(true);

            index++;
            index2++;
            index3++;

            try {
                nextQ();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        pause.setRepeats(false);
        pause.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(index);
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);

        JButton src = (JButton) e.getSource();

        String svar = src.getText();

        if (svar.equals(q.get(index).getCorrectanswear()) || svar.equals(q.get(index2).getCorrectanswear())
                || svar.equals(q.get(index3).getCorrectanswear()) ) {
            System.out.println("Rätt!");
            src.setBackground(Color.GREEN);
            correctGuesses++;
        } else {
            System.out.println("Fel!");
            src.setBackground(Color.RED);
        }
        showAnswer();
    }

    /**
     * Runs the client as an application.
     */
    public static void main(String[] args) throws Exception {

        String serverAddress = (args.length == 0) ? "localhost" : args[1];
        Client client = new Client(serverAddress);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setSize(500, 750);
        client.frame.setVisible(true);
        client.frame.setResizable(false);
        client.play();
    }
}
