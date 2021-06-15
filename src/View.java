
public class View  implements TimerObserver {

    private Model model;
    private Controller controller;
    private boolean running = true;

    public View(Model model) {
        this.model = model;
        this.controller = new Controller(model, this);
        this.model.getTimer().registerObserver(this);
    }

    public void showQuestionsList() {
        System.out.println(this.model.getCurrentPlayer().getName() + " Please choose question from the list " +
                this.model.showQuestionsIds());
    }

    public void showQuestion() {
        System.out.println("Question: " +
                this.model.getCrosswordIdQuestion().get(this.model.getCurrentQuestionId()).getQuestion() +
                "\nWord: " + this.model.generateWordField());
    }

    protected void showCommands() {
        System.out.println("To buy 20 seconds extra time, please press " + Utils.BUY_TIME_20);
        System.out.println("To buy 30 seconds extra time, please press " + Utils.BUY_TIME_30);
        System.out.println("To buy an extra letter, please press " + Utils.BUY_LETTER);
        System.out.println("To buy end your turn, please press  " + Utils.PASS_THE_TURN_TO_THE_NEXT_PLAYER);
        System.out.println("To check time left, please press  " + Utils.CHECK_TIME_LEFT);
    }

    public void showBoughtLetter() {
        System.out.println("Word: " + this.model.getBoughtLetter());
    }

    public void showBoughtExtraTime(int seconds) {
        System.out.printf("Buying %d seconds extra time.\n", seconds);
    }
    public void showTimeLeft() {
        System.out.printf("Time left: %d seconds\n", this.model.getTimer().getTimeLeftInSeconds());
    }
    public void showCreditsLeft() {
        System.out.printf("Credits left: %d \n", this.model.getCurrentPlayer().getCredits());
    }

    public void showAdditionalInfo(String msg) {
        System.out.println(msg);
    }

    public void printCrossword() {
        for (int i = 0; i < this.model.getCrosswordInstance().getCrossword().length; i++) {
            System.out.println(
                    String.format(this.model.getCrosswordInstance().getCrosswordLine(),
                            this.model.getCrosswordInstance().getCrossword()[i]));
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void startEventLoop() {
        while (this.running) {
            this.controller.doit();
        }
        if(!this.running) {
            System.out.println("The crossword is filled. The game is ending.");
            this.printCrossword();
        }
    }

    @Override
    public void update() {
            System.out.println("End turn. Please click enter to continue.");
    }

}


