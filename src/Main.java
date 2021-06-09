public class Main {

    public static void main(String[] args) {
        QuestionDataSet.getInstance();
        new View(Model.getInstance(), MyTimer.getInstance()).startEventLoop();
        System.out.println("The crossword is filled. The game is ending.");
        Crossword.getInstance().printCrossword();
    }
}
