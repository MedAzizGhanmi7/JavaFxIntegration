package CoursifyApp.entities;


public class ChapitreQuizzAnswer {
    private String text;
    private boolean isCorrect;

    @Override
    public String toString() {
        return "ChapitreQuizzAnswer{" +
                "text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    public ChapitreQuizzAnswer()
    {

    }
    public ChapitreQuizzAnswer(String text, boolean isCorrect) {
        if(text.contains(","))
            text = text.replaceAll(",", "");
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean correct) {
        isCorrect = correct;
    }
}
