import java.util.*;

public class Model implements TimerObserver {

    private static Model instance = null;
    private Crossword crossword;
    private MyTimer timer;


    private TimerSubject timerSubject;

    private ArrayList<Player> players;
    private HashMap<Integer, Question> questionsHashMap;
    private Set<Integer> answeredQuestionsIds = new HashSet<>();
    private int currentPlayerId = 0;
    private int currentQuestionId = -1;


    private Model(TimerSubject timerSubject) {
        this.questionsHashMap = QuestionDataSet.getInstance().getDataSetIdQuestion();
        this.timer = MyTimer.getInstance();
        initializePlayers();
        this.crossword = Crossword.getInstance();
        this.timerSubject = timerSubject;
        this.timerSubject.registerObserver(this);
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model(MyTimer.getInstance());
        }
        return instance;
    }


    public void addAnsweredQuestionId(int questionId) {
        this.answeredQuestionsIds.add(questionId);
    }

    public boolean isCrosswordFilled() {
        return answeredQuestionsIds.size() == questionsHashMap.keySet().size();
    }

    public Question getQuestionById(int questionId) {
        return this.questionsHashMap.get(questionId);
    }

    public HashMap<Integer, Question> getCrosswordIdQuestion() {
        return this.questionsHashMap;
    }

    public String showQuestionsIds() {
        String questionsIds = "";
        for (int key : questionsHashMap.keySet()) {
            if (!answeredQuestionsIds.contains(key)) {
                questionsIds += key + ", ";
            }
        }

        if (questionsIds.isEmpty()) {
            return "";
        }
        return questionsIds.trim().substring(0, questionsIds.trim().length() - 1);
    }

    public String getBoughtLetter() {
        String word = this.questionsHashMap.get(currentQuestionId).getAnswer();
        int randomIndex = new Random().nextInt(word.length());
        String wordField = generateWordField();

        String[] wordFieldCharArr = wordField.split(" ");
        wordFieldCharArr[randomIndex] = String.valueOf(word.charAt(randomIndex));

        String output = String.join(" ", wordFieldCharArr);

        return output;
    }

    public boolean isGivenAnswerCorrect(String answer) {
        String word = getQuestionById(this.currentQuestionId).getAnswer();
        if (answer.equalsIgnoreCase(word)) {
            return true;
        }
        return false;
    }

    public String generateWordField() {
        int wordFieldsLen = this.questionsHashMap.get(currentQuestionId).getAnswer().length();
        String wordField = "";
        for (int i = 0; i < wordFieldsLen; i++) {
            wordField += "_ ";
        }
        return wordField;
    }

    public Player getCurrentPlayer() {
        return this.players.get(currentPlayerId);

    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void saveCurrentQuestionId(int currentQuestionId) {
        this.currentQuestionId = currentQuestionId;

    }

    public int getCurrentQuestionId() {
        return this.currentQuestionId;
    }

    public void updateCrossword() {
       Question question = getQuestionById(currentQuestionId);
       int[] wordIndexes = crossword.findIndexesByQuestionId(String.valueOf(currentQuestionId));
       crossword.fillCrosswordAnswer(question.isVertical(), wordIndexes[0], wordIndexes[1], String.valueOf(currentQuestionId));
    }

    public MyTimer getTimer() {
        return timer;
    }

    public void endTurn() {
        this.currentQuestionId = -1;
        this.timer.stopTimer();
        saveCurrentQuestionId(currentQuestionId);
        this.timer.stopTimer();

    }

    public void saveQuestionAnswer(String userInput) {
        getCurrentPlayer().getSavedAnswers().put(currentQuestionId, userInput);
        this.addAnsweredQuestionId(currentQuestionId);
        this.updateCrossword();
        this.timer.stopTimer();
        this.currentQuestionId = -1;
    }

    public void switchPlayer() {
        if (this.getCurrentPlayerId() == 1) {
            this.setCurrentPlayerId(0);
        } else {
            this.setCurrentPlayerId(this.getCurrentPlayerId() + 1);
        }
    }

    private void initializePlayers() {
        this.players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
    }

    public Crossword getCrosswordInstance() {
        return crossword;
    }

    @Override
    public void update() {
        endTurn();
    }
}
