package pl.edu.uwr.lista1;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pl.edu.uwr.lista1.R;

class Question {
    private String content;
    private String[] answers;
    private String correct_answer;

    public Question(String content, String ans1, String ans2, String ans3, String ans4, int correct) {
        this.content = content;
        this.answers = new String[]{ans1, ans2, ans3, ans4};
        this.correct_answer = this.answers[correct - 1];
    }

    public String getContent() { return content; }
    public String[] getAnswers() { return answers; }
    public String getCorrect() { return correct_answer; }
}
public class MainActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup radioGroup;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private RadioButton selectedRadioButton;
    private Button nextButton;
    private TextView questionCounter;

    private int question_index = 0;
    private int score = 0;
    private int N = 10;
    private Question[] questions = new Question[N];

    void setupQuestion(int index) {
        questionText.setText(questions[index].getContent());
        radio1.setText(questions[index].getAnswers()[0]);
        radio2.setText(questions[index].getAnswers()[1]);
        radio3.setText(questions[index].getAnswers()[2]);
        radio4.setText(questions[index].getAnswers()[3]);
        radioGroup.clearCheck();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("question_count", question_index);
        outState.putInt("score", score);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score");
            question_index = savedInstanceState.getInt("question_count");
        }

        questionText = findViewById(R.id.question);
        radioGroup = findViewById(R.id.radio_group);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
        nextButton = findViewById(R.id.next_button);
        questionCounter = findViewById(R.id.question_counter);
        TextView scoreText = findViewById(R.id.score);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        // All the questions
        questions[0] = new Question("Kto jest autorem 'Pana Tadeusza'?", "Henryk Sienkiewicz", "Juliusz Słowacki", "Adam Mickiewicz", "Bolesław Prus", 3);
        questions[1] = new Question("Jak nazywa się najwyższy szczyt w Polsce?", "Rysy", "Giewont", "Kasprowy Wierch", "Śnieżka", 1);
        questions[2] = new Question("W którym roku odbyły się pierwsze nowożytne igrzyska olimpijskie?", "1896", "1900", "1912", "1920", 1);
        questions[3] = new Question("Który z oceanów jest największy?", "Atlantycki", "Indyjski", "Spokojny", "Arktyczny", 3);
        questions[4] = new Question("Które z tych zwierząt nie występuje jako znak zodiaku w chińskim horoskopie?", "Małpa", "Krowa", "Świnia", "Szczur", 2);
        questions[5] = new Question("Glajt to inne określenie:", "Balonu", "Latawca", "Paralotni", "Flyboardu", 3);
        questions[6] = new Question("Z którą z części mowy partykułę 'nie' piszemy osobno?", "Imiesłowem", "Czasownikiem", "Rzeczownikiem", "Przymiotnikiem", 2);
        questions[7] = new Question("Spójnikiem nie jest wyraz:", "Niech", "Lub", "I", "Jednak", 1);
        questions[8] = new Question("Do czego stosuje się pokost?", "Gotowania", "Impregnacji", "Dezynfekcji", "Oprysków", 2);
        questions[9] = new Question("Wyspą nie jest:", "Cejlon", "Madagaskar", "Honsiu", "Kamczatka", 4);

        // Setting up the first one
        questionCounter.setText("Pytanie " + (question_index + 1) + "/" + N);
        setupQuestion(question_index);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    System.out.println("No answer selected");
                }
                else {
                    selectedRadioButton = findViewById(selectedId);
                    String userAnswer = selectedRadioButton.getText().toString();

                    if (userAnswer.equals(questions[question_index].getCorrect())) {
                        score++;
                    }

                    if(question_index < (N - 1)) {
                        setupQuestion(++question_index);
                        progressBar.setProgress(question_index * 10);
                        questionCounter.setText("Pytanie " + (question_index + 1) + "/" + N);
                    }

                    else {
                        questionCounter.setText("Gratulacje");

                        scoreText.setText("Wynik: " + score + "/" + N + " pkt");
                        scoreText.setVisibility(View.VISIBLE);

                        questionText.setVisibility(View.GONE);
                        radioGroup.setVisibility(View.GONE);
                        nextButton.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}