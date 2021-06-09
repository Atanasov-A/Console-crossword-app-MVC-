import java.util.HashMap;

public class Player {

    private String name;
    private int credits;
    // Question id - answered word
    private final HashMap<Integer, String> savedAnswers;

    public Player(String name) {
        this.name = name;
        this.credits = 2;
        this.savedAnswers = new HashMap<>();
    };

    public int getCredits() {
        return credits;
    }

    public HashMap<Integer, String> getSavedAnswers() {
        return savedAnswers;
    }

    public String getName() {
        return name;
    }

    public void decreaseCredits(int amount) {
        this.credits = this.credits - amount;
    }
}
