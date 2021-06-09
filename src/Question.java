public class Question {

    private String question;
    private String answer;
    private boolean vertical;

    // If vertical flag is set the word should be filled vertically in the crossword
    public Question(String question, String answer, boolean vertical) {
        this.question = question;
        this.answer = answer;
        this.vertical = vertical;
    }

    public boolean isVertical() {
        return this.vertical;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

}
