/**
 * @author Jonny W
 * @version 1.0
 * @since 09-26-2022
 * This is a hangman game. The words and the gallows drawings came from a Java Bootcamp course on Udemy.com,
 * but the rest of the code is from me.
 */

import java.util.Scanner;

public class Hangman {

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};

    public static String[] gallows = {"+---+\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};

        public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        beginGame();

        String word = getRandomWord();
        String maskedWord = maskWord(word);
        String guesses = "";
        int gallowsPosition = -1;

        for(int i = 0; i < word.length() + gallows.length; i++){
            printInformation(guesses, maskedWord, gallowsPosition);
            String nextGuess = getNextGuess(guesses);
            guesses+= nextGuess;
            if(isCorrectGuess(nextGuess, word)){
                maskedWord = updateMaskedWord(nextGuess, maskedWord, word);
            }else{
                gallowsPosition++;
                }
            if(canConcludeGame(maskedWord, word, guesses, gallowsPosition)){
                break;
            }
        }




    }

    /**
     * This method is to print out an explanation of Hangman, and accept input to continue.
     */
    public static void beginGame(){
        System.out.println("Welcome to Hangman!\nYou will attempt to guess a word one letter at a time.\nIf you guess incorrectly, the gallows will draw. Once fully drawn, you lose. Guess the word correctly beforehand and you win!\nPlease press ENTER to continue: ");
        scan.nextLine();
    }

    /**
     * get a random word from the list of words to use throughout the game.
     * @return Random word from an array of words
     */
    public static String getRandomWord(){
        return words[((int)(Math.random()*words.length))];
    }

    /**
     * Turn any word into a masked word; replace each character with '_'
     * @param word
     * @return masked word
     */
    public static String maskWord(String word){
        String maskedWord = "";
        for(int i = 0; i < word.length(); i++)maskedWord += "_";
        return maskedWord;
    }
    
    /**
     * Adds a space between each character in a word 
     * @param word
     * @return word with a space between each character
     */
    public static String addSpacing(String word){
        String[] arr = word.split("");
        return String.join(" ", arr);
    }

    /**
     * Print the current status of the game. The gallows, if activated, the masked word, and the guesses so far
     * @param guesses a string of all the guessed characters so far
     * @param maskedWord the word masked, filled in with the correctly guessed characters
     * @param gallowsPosition index in the Gallows array based on incorrect guesses
     */
    public static void printInformation(String guesses, String maskedWord, int gallowsPosition){
        if(gallowsPosition < 0){} // do nothing
        else{
            System.out.println("\n"+gallows[gallowsPosition]);
        }

        System.out.println("\nWord to guess: " + addSpacing(maskedWord));
        System.out.println("\nGuesses so far: " + addSpacing(guesses));
    }

    /**
     * Prompts user for next character guess. Checks to see if it is a single character, and not already guessed.
     * If so, replay the prompt until it receives a valid character.
     * @param guesses The list of previous guesses from the user
     * @return a String value with a single new character the user entered
     */
    public static String getNextGuess(String guesses){
       boolean validGuess = false;
        String newGuess = "";
        do{
            System.out.print("\nPlease enter next guess: ");
            newGuess = scan.nextLine();
            if(newGuess.length() != 1){
                System.out.println("Please guess one letter.");
            }else if(guesses.contains(newGuess.toLowerCase())){
                System.out.println("Guess already attempted. Please try again.");
            }else{
                validGuess = true;
            }
        }while(!validGuess);

        return newGuess;
    }

    /**
     * Checks if the guessed character is within the word
     * @param guess
     * @param word
     * @return boolean
     */
    public static boolean isCorrectGuess(String guess, String word){
        return word.contains(guess.toLowerCase());
    }

    /**
     * Update the masked string to reveal all instances of the correctly guessed character 
     * @param guess
     * @param maskedWord
     * @param word
     * @return partially masked word with correctly guessed character(s) revealed
     */
    public static String updateMaskedWord(String guess, String maskedWord, String word){
        char[] maskedWordArray = maskedWord.toCharArray();
        char guessChar = guess.charAt(0);
        for(int i=0; i < maskedWordArray.length; i++){
            if(word.charAt(i) == guessChar) maskedWordArray[i] = guessChar;
        }

        return new String(maskedWordArray);
    }

    /**
     * Checks if the game can conclude, either by the gallow being completely drawn, or the word fully unmasked.
     * If so, print the concluding status.
     * @param maskedWord
     * @param word
     * @param guesses
     * @param gallowsPosition
     * @return an indicator if the game has concluded or not
     */
    public static boolean canConcludeGame(String maskedWord, String word, String guesses, int gallowsPosition){
        boolean conclude = false;

        if(maskedWord.equals(word)){
            printInformation(guesses, maskedWord, gallowsPosition);
            System.out.println("\nThe word was: " + addSpacing(word));
            System.out.println("\nYou win!\n");
            conclude = true;
        }
        else if(gallowsPosition == gallows.length-1){
            printInformation(guesses, maskedWord, gallowsPosition);
            System.out.println("\nThe word was: " + addSpacing(word));
            System.out.println("\nYou lose!\n");
            conclude = true;
        }else{
         conclude = false;
        }
        return conclude;
    }
}





