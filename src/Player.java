/**
 * Created by Axel Jeansson
 * Date: 2020-11-12
 * Time: 13:47
 * Project: Quizkampen
 * Copyright: MIT
 */
public class Player {
    private String username;
    private int userScore;
    private int matchesPlayed;
    private int wins;
    private int losses;
    private int questionsAnswered;
    private int correctAnswers;

    public Player(String username, int userScore){
        this.username = username;
        this.userScore = userScore;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setUserScore(int userScore){
        this.userScore = userScore;
    }

    public String getUsername(){
        return username;
    }
    public int getUserScore(){
        return userScore;
    }

    public void setMatchesPlayed(int matchesPlayed){
        this.matchesPlayed = matchesPlayed;
    }

    public void setWins(int wins){
        this.wins = wins;
    }

    public void setLosses(int losses){
        this.losses = losses;
    }

    public int getMatchesPlayed(){
        return matchesPlayed;
    }

    public int getWins(){
        return wins;
    }

    public int getLosses(){
        return losses;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
