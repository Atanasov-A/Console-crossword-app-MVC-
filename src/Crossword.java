public class Crossword {

    private String[][] crossword;
    private final String crosswordLine = "|_%s_|_%s_|_%s_|_%s_|_%s_|_%s_|_%s_|_%s_|_%s_|_%s_|";

    private static Crossword instance = null;

    public static Crossword getInstance() {
        if (instance == null) {
            instance = new Crossword();
        }
        return instance;
    }
    public Crossword() {
        this.createCrossword();
    }

    public void createCrossword() {
        this.crossword = new String[10][10];
        for (int row = 0; row < crossword.length; row++)
        {
            for (int col = 0; col < crossword[row].length; col++)
            {
                crossword[row][col] = "*";
            }
        }

        this.fillCrosswordVertical(0, 2, "1", false);
        this.fillCrosswordHorizontal(1, 0, "4", false);
        this.fillCrosswordHorizontal(6, 3, "3", false);
        this.fillCrosswordVertical(4, 5, "2", false);
        this.fillCrosswordVertical(0, 8, "5", false);
        this.fillCrosswordHorizontal(1, 5, "6", false);
    }

    public void fillCrosswordAnswer(boolean vertical, int questionRowIndex, int questionColIndex, String questionId) {
        if(vertical) {
            fillCrosswordVertical(questionRowIndex, questionColIndex, questionId, true);
        } else {
            fillCrosswordHorizontal(questionRowIndex, questionColIndex, questionId, true);
        }
    }

    public void fillCrosswordHorizontal(int questionRowIndex, int questionColIndex, String questionId, boolean showWord) {
        this.crossword[questionRowIndex][questionColIndex] = questionId;

        Question question = QuestionDataSet.getInstance().getDataSetIdQuestion().get(Integer.parseInt(questionId));
        String word = question.getAnswer();

        String[] splittedWord = word.split("");
        int splittedWordIndex = 0;

        for (int k = 1; k <= word.length(); k++) {
            int newIndex = k + questionColIndex;
            if (showWord) {
                this.crossword[questionRowIndex][newIndex] = splittedWord[splittedWordIndex].toUpperCase();
                splittedWordIndex++;
            } else {
                this.crossword[questionRowIndex][newIndex] = "_";
            }
        }
    }

    public void fillCrosswordVertical(int questionRowIndex, int questionColIndex, String questionId, boolean showWord) {
        this.crossword[questionRowIndex][questionColIndex] = questionId;

        Question question = QuestionDataSet.getInstance().getDataSetIdQuestion().get(Integer.parseInt(questionId));
        String word = question.getAnswer();

        String[] splittedWord = word.split("");
        int splittedWordIndex = 0;
        for (int k = 1; k <= word.length(); k++) {
            int newIndex = k + questionRowIndex;
            if (showWord) {
                this.crossword[newIndex][questionColIndex] = splittedWord[splittedWordIndex].toUpperCase();
                splittedWordIndex++;

            } else {
                this.crossword[newIndex][questionColIndex] = "_";
            }
        }
    }

    public void printCrossword() {
        for (int i = 0; i < this.crossword.length; i++) {
            System.out.println(String.format(crosswordLine, crossword[i]));
        }
    }

    // Find the indexes of the specific question.
    // Return array of indexes, where the question is located.
    // the first index is the row and the second the column
    public int[] findIndexesByQuestionId(String questionId) {
        int[] indexRowCol = new int[2];
        for (int row = 0; row < crossword.length; row++) {
            for (int col = 0; col < crossword[row].length; col++) {
                if (questionId.equals(crossword[row][col])){
                    indexRowCol[0] = row;
                    indexRowCol[1] = col;
                    break;
                }
            }
        }
        return indexRowCol;
    }

    public String[][] getCrossword() {
        return crossword;
    }

    public String getCrosswordLine() {
        return crosswordLine;
    }
}
