import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class WaraichFlashCardApp extends JFrame {

    static class Flashcard {
        String question;
        String answer;

        Flashcard(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    private ArrayList<Flashcard> cards = new ArrayList<>();
    private int currentIndex = 0;
    private boolean answerVisible = false;

    private JLabel cardLabel;
    private JLabel statusLabel;

    public WaraichFlashCardApp() {

        setTitle("Waraich Flash Card Quiz App");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sample Flashcards
        cards.add(new Flashcard("Capital of France?", "Paris"));
        cards.add(new Flashcard("2 + 2 = ?", "4"));
        cards.add(new Flashcard("Java Creator?", "James Gosling"));

        initializeUI();
        updateCard();
    }

    private void initializeUI() {

        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel(
                "📚 FLASHCARD QUIZ APP",
                SwingConstants.CENTER);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Flashcard Display
        cardLabel = new JLabel("", SwingConstants.CENTER);
        cardLabel.setFont(new Font("Arial", Font.BOLD, 28));
        cardLabel.setOpaque(true);
        cardLabel.setBackground(Color.WHITE);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

        add(cardLabel, BorderLayout.CENTER);

        // Status
        statusLabel = new JLabel("", SwingConstants.CENTER);

        // Navigation Buttons
        JButton previousButton = new JButton("⬅ Previous");
        JButton showAnswerButton = new JButton("Show Answer");
        JButton nextButton = new JButton("Next ➡");

        previousButton.addActionListener(e -> {
            if (cards.isEmpty()) return;

            currentIndex = (currentIndex - 1 + cards.size()) % cards.size();
            answerVisible = false;
            updateCard();
        });

        showAnswerButton.addActionListener(e -> {
            if (cards.isEmpty()) return;

            answerVisible = !answerVisible;

            if (answerVisible) {
                showAnswerButton.setText("Hide Answer");
            } else {
                showAnswerButton.setText("Show Answer");
            }

            updateCard();
        });

        nextButton.addActionListener(e -> {
            if (cards.isEmpty()) return;

            currentIndex = (currentIndex + 1) % cards.size();
            answerVisible = false;
            showAnswerButton.setText("Show Answer");
            updateCard();
        });

        // CRUD Buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        // ADD
        addButton.addActionListener(e -> {

            String question =
                    JOptionPane.showInputDialog(this,
                            "Enter Question:");

            if (question == null || question.trim().isEmpty())
                return;

            String answer =
                    JOptionPane.showInputDialog(this,
                            "Enter Answer:");

            if (answer == null || answer.trim().isEmpty())
                return;

            cards.add(new Flashcard(question, answer));

            currentIndex = cards.size() - 1;
            answerVisible = false;

            updateCard();
        });

        // EDIT
        editButton.addActionListener(e -> {

            if (cards.isEmpty())
                return;

            Flashcard card = cards.get(currentIndex);

            String question =
                    JOptionPane.showInputDialog(this,
                            "Edit Question:",
                            card.question);

            if (question == null)
                return;

            String answer =
                    JOptionPane.showInputDialog(this,
                            "Edit Answer:",
                            card.answer);

            if (answer == null)
                return;

            card.question = question;
            card.answer = answer;

            answerVisible = false;

            updateCard();
        });

        // DELETE
        deleteButton.addActionListener(e -> {

            if (cards.isEmpty())
                return;

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Delete this flashcard?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {

                cards.remove(currentIndex);

                if (cards.isEmpty()) {
                    cardLabel.setText("No Flashcards Available");
                    statusLabel.setText("0 / 0");
                    return;
                }

                if (currentIndex >= cards.size()) {
                    currentIndex = cards.size() - 1;
                }

                answerVisible = false;
                updateCard();
            }
        });

        // Navigation Panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.add(previousButton);
        navigationPanel.add(showAnswerButton);
        navigationPanel.add(nextButton);

        // CRUD Panel
        JPanel crudPanel = new JPanel();
        crudPanel.add(addButton);
        crudPanel.add(editButton);
        crudPanel.add(deleteButton);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        bottomPanel.add(navigationPanel, BorderLayout.CENTER);
        bottomPanel.add(crudPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateCard() {

        if (cards.isEmpty()) {
            cardLabel.setText("No Flashcards Available");
            statusLabel.setText("0 / 0");
            return;
        }

        Flashcard card = cards.get(currentIndex);

        if (answerVisible) {

            cardLabel.setText(
                    "<html><center><h1>"
                            + card.answer
                            + "</h1></center></html>");

        } else {

            cardLabel.setText(
                    "<html><center><h1>"
                            + card.question
                            + "</h1></center></html>");
        }

        statusLabel.setText(
                "Card "
                        + (currentIndex + 1)
                        + " of "
                        + cards.size());
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new WaraichFlashCardApp().setVisible(true);
        });
    }
}