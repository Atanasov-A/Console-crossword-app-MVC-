import java.util.Scanner;

public class Controller implements TimerObserver {

    private Model model;
    private View view;

    // After the time is up, the scanner is not closed, but
    // the user input should not be saved.
    private boolean shouldTakeUserInput = false;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.model.getTimer().registerObserver(this);
    }

    public void doit() {
        this.view.printCrossword();
        while (!this.model.doesQuestionExists()) {
            this.chooseQuestion();
        }

        this.view.showCommands();
        this.view.showQuestion();

        this.answerQuestionInputHandler();

        if (this.model.isCrosswordFilled()) {
            this.view.setRunning(false);
        }
    }

    private void chooseQuestion() {
        this.shouldTakeUserInput = true;
        view.showQuestionsList();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int chosenQuestionId = -1;
        try {
            chosenQuestionId = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Exception chooseQuestion " + e);
        }
        // Internally start timer
        this.model.saveCurrentQuestionId(chosenQuestionId);
    }

     private void answerQuestionInputHandler() {
            Scanner scanner = new Scanner(System.in);

            String userInput = scanner.nextLine();

            switch (userInput) {
                case Utils.BUY_TIME_20:
                    buyTime(20);
                    break;
                case Utils.BUY_TIME_30:
                    buyTime(30);
                    break;
                case Utils.BUY_LETTER:
                    buyLetter();
                    break;
                case Utils.PASS_THE_TURN_TO_THE_NEXT_PLAYER:
                    endPlayerTurn();
                    break;
                case Utils.CHECK_TIME_LEFT:
                    checkTimeLeft();
                    break;
                default:
                    savePlayerInput(userInput);
            }
    }

    private void endPlayerTurn() {
        this.view.showAdditionalInfo("End Turn");
        this.model.endTurn();
    }

    private void buyTime(int seconds) {
        if (seconds == 20 && this.model.getCurrentPlayer().getCredits() >= 1) {
            this.model.getCurrentPlayer().decreaseCredits(1);
            view.showBoughtExtraTime(seconds);
            this.model.getTimer().changeTimerTime(20);
        }  else if (seconds == 30 && this.model.getCurrentPlayer().getCredits() >= 2) {
            this.model.getCurrentPlayer().decreaseCredits(1);
            view.showBoughtExtraTime(seconds);
            this.model.getTimer().changeTimerTime(30);
        } else {
            view.showAdditionalInfo("Not enough credits");
        }
        view.showTimeLeft();
        view.showCreditsLeft();
        this.answerQuestionInputHandler();
    }

    private void buyLetter() {
        if (this.model.getCurrentPlayer().getCredits() >= 2) {
            this.model.getCurrentPlayer().decreaseCredits(2);
            view.showAdditionalInfo("Buying letter");
            view.showBoughtLetter();
            view.showCreditsLeft();
        } else {
            view.showAdditionalInfo("Not enough credits");
        }
        this.answerQuestionInputHandler();
    }

    private void savePlayerInput(String userInput) {
        // If the time is up, the scanner can not be closed you should click enter to end the turn
        if (userInput.isEmpty()) {
            this.model.endTurn();
        } else {
            // Hashmap ID - Question + Answer
            if (this.model.getCrosswordIdQuestion()
                    .get(this.model.getCurrentQuestionId())
                    .getAnswer().length() < userInput.length()) {
                view.showAdditionalInfo("The word can't be longer than the given field.");
                this.answerQuestionInputHandler();
            } else {
                if (this.model.isGivenAnswerCorrect(userInput)) {
                    this.model.saveQuestionAnswer(userInput);
                } else {
                    view.showAdditionalInfo("The given answer was wrong, please try again");
                    if (this.shouldTakeUserInput) {
                        answerQuestionInputHandler();
                    }
                }
            }
        }
    }

    private void checkTimeLeft() {
        view.showTimeLeft();
        this.answerQuestionInputHandler();
    }

    @Override
    public void update() {
        this.shouldTakeUserInput = false;
    }
}
