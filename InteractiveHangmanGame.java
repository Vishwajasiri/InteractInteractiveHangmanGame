import java.util.*;

public class InteractiveHangmanGame {
    private Set<Character> correctGuesses;
    private Set<Character> incorrectGuesses;
    private static final int MAX_INCORRECT_GUESSES = 6;
    private static final Map<String, List<String>> categories = new HashMap<>();

    static {
        // Initialize categories with words
        categories.put("Programming", Arrays.asList("java", "python", "javascript", "ruby", "php", "html", "css", "sql", "swift", "csharp"));
        categories.put("Countries", Arrays.asList("usa", "china", "india", "russia", "brazil", "germany", "france", "japan", "italy", "canada"));
        categories.put("Animals", Arrays.asList("lion", "elephant", "tiger", "giraffe", "zebra", "monkey", "panda", "koala", "kangaroo", "hippopotamus"));
        categories.put("Movies", Arrays.asList("avatar", "titanic", "starwars", "jurassicpark", "inception", "harrypotter", "thegodfather", "forrestgump", "shawshank", "matrix"));
        categories.put("Sports", Arrays.asList("soccer", "basketball", "tennis", "golf", "cricket", "baseball", "volleyball", "swimming", "hockey", "football"));
    }

    public InteractiveHangmanGame() {
        initializeGame();
    }

    private void initializeGame() {
        correctGuesses = new HashSet<>();
        incorrectGuesses = new HashSet<>();
    }

    private String fetchRandomWord(String category) {
        List<String> words = categories.get(category);
        if (words == null || words.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    private void displayCategories() {
        System.out.println("Choose a category:");
        int i = 1;
        for (String category : categories.keySet()) {
            System.out.println(i++ + ". " + category);
        }
    }

    private void takeUserCategoryInput() {
        Scanner scanner = new Scanner(System.in);

        int categoryChoice;
        do {
            displayCategories();
            System.out.print("Enter the number corresponding to the category: ");
            categoryChoice = scanner.nextInt();
        } while (categoryChoice < 1 || categoryChoice > categories.size());

        List<String> categoryList = new ArrayList<>(categories.keySet());
        String selectedCategory = categoryList.get(categoryChoice - 1);

        String selectedWord = fetchRandomWord(selectedCategory);
        System.out.println("Category: " + selectedCategory);
        playGame(selectedWord);
    }

    private void displayWord(String selectedWord) {
        for (char letter : selectedWord.toCharArray()) {
            if (correctGuesses.contains(letter)) {
                System.out.print(letter + " ");
            } else {
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    private void displayHangman() {
        int incorrectGuessCount = incorrectGuesses.size();

        System.out.println("Incorrect Guesses (" + incorrectGuessCount + "): " + incorrectGuesses);

        System.out.println("Hangman:");
        // Add the hangman drawing logic here based on the incorrect guess count
        // You can reuse the logic from the previous code snippet
    }

    private void takeUserInput(String selectedWord) {
        Scanner scanner = new Scanner(System.in);

        displayWord(selectedWord);
        displayHangman();

        System.out.print("Enter a letter: ");
        char guess = scanner.next().toLowerCase().charAt(0);

        if (!Character.isLetter(guess)) {
            System.out.println("Please enter a valid letter.");
        } else if (correctGuesses.contains(guess) || incorrectGuesses.contains(guess)) {
            System.out.println("You've already guessed that letter. Try again.");
        } else {
            if (selectedWord.contains(String.valueOf(guess))) {
                correctGuesses.add(guess);
            } else {
                incorrectGuesses.add(guess);
            }
        }
    }

    private boolean isWordGuessed(String selectedWord) {
        for (char letter : selectedWord.toCharArray()) {
            if (!correctGuesses.contains(letter)) {
                return false;
            }
        }
        return true;
    }

    private void playGame(String selectedWord) {
        Scanner scanner = new Scanner(System.in);

        while (incorrectGuesses.size() < MAX_INCORRECT_GUESSES && !isWordGuessed(selectedWord)) {
            takeUserInput(selectedWord);
        }

        if (isWordGuessed(selectedWord)) {
            System.out.println("Congratulations! You guessed the word: " + selectedWord);
        } else {
            System.out.println("Sorry! You ran out of attempts. The word was: " + selectedWord);
            displayHangman();
        }

        scanner.close();
    }

    public static void main(String[] args) {
        InteractiveHangmanGame hangmanGame = new InteractiveHangmanGame();
        hangmanGame.takeUserCategoryInput();
    }
}