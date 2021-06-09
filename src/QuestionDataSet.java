import java.util.HashMap;

public class QuestionDataSet {

    private HashMap<Integer, Question> dataSetIdQuestion;
    private static QuestionDataSet instance = null;

    private QuestionDataSet() {
        this.addQuestionsToDataSet();
    }

    public static QuestionDataSet getInstance() {
        if (instance == null) {
            instance = new QuestionDataSet();
        }
        return instance;
    }

    private void addQuestionsToDataSet() {
        this.dataSetIdQuestion = new HashMap<>();
        dataSetIdQuestion.put(1, new Question("Wie heißt die Hauptstadt von Deutschland?",
                "Berlin", true));
        dataSetIdQuestion.put(2, new Question("In welcher Einheit wird der elektrische Widerstand gemessen?",
                "Ohm", true));
        dataSetIdQuestion.put(3, new Question("In welchem Land wohnen die meisten Menschen?",
                "China", false));
        dataSetIdQuestion.put(4, new Question("Wie lautet das chemische Symbol für Blei", "Pb", false));
        dataSetIdQuestion.put(5, new Question("Auf welchem Kontinent liegt die Wüste Sahara?", "Afrika", true));
        dataSetIdQuestion.put(6, new Question("Viele Autos hintereinander", "Stau", false));
    }

    public HashMap<Integer, Question> getDataSetIdQuestion() {
        return dataSetIdQuestion;
    }

}
