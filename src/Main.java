public class Main {

    public static void main(String[] args) {
        QuestionDataSet.getInstance();
        new View(Model.getInstance()).startEventLoop();
    }
}
