import java.util.Random;
import java.util.Scanner;

public class GuessNumberGame
{
    private static final int DEFAULT_MIN = 1;
    private static final int DEFAULT_MAX = 100;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Игра: Угадай число ===");
        boolean playAgain = true;

        while (playAgain) {
            playRound(scanner, DEFAULT_MIN, DEFAULT_MAX);
            playAgain = askPlayAgain(scanner);
        }

        System.out.println("Спасибо за игру! Пока.");
        scanner.close();
    }
    private static void playRound(Scanner scanner, int min, int max) {
        Random rand = new Random();
        int secret = rand.nextInt(max - min + 1) + min;
        int attempts = 0;
        System.out.printf("Я загадал число от %d до %d. Попробуй угадать!\n", min, max);

        while (true) {
            System.out.print("Введи число: ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Пустой ввод — введите целое число.");
                continue;
            }

            int guess;
            try {
                guess = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод — пожалуйста, введите целое число.");
                continue;
            }

            attempts++;

            if (guess < min || guess > max) {
                System.out.printf("Число вне диапазона %d..%d. Попробуйте снова.\n", min, max);
                continue;
            }

            if (guess < secret) {
                System.out.println("Больше!");
            } else if (guess > secret) {
                System.out.println("Меньше!");
            } else {
                System.out.printf("Правильно! Вы угадали число %d за %d %s.\n",
                        secret, attempts, attemptsWord(attempts));
                break;
            }
        }
    }
    private static boolean askPlayAgain(Scanner scanner) {
        while (true) {
            System.out.print("Сыграть ещё раз? (y/n): ");
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.isEmpty()) continue;
            char c = ans.charAt(0);
            if (c == 'y' || c == 'д') { // 'д' для "да"
                return true;
            } else if (c == 'n' || c == 'н') { // 'н' для "нет"
                return false;
            } else {
                System.out.println("Пожалуйста, введите 'y' (да) или 'n' (нет).");
            }
        }
    }
    private static String attemptsWord(int attempts) {
        int a = attempts % 100;
        if (a >= 11 && a <= 14) return "попыток";
        int last = a % 10;
        switch (last) {
            case 1: return "попытка";
            case 2:
            case 3:
            case 4: return "попытки";
            default: return "попыток";
        }
    }
}