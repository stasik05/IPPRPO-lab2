import java.util.Random;
import java.util.Scanner;

public class GuessNumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Игра: Угадай число ===");

        boolean playAgain = true;
        while (playAgain) {
            GameConfig config = chooseDifficulty(scanner); // выбор сложности
            playRound(scanner, config);
            playAgain = askPlayAgain(scanner);
        }

        System.out.println("Спасибо за игру! Пока.");
        scanner.close();
    }

    // Конфигурация игры (диапазон + попытки)
    static class GameConfig {
        int min;
        int max;
        int attemptsLimit;

        GameConfig(int min, int max, int attemptsLimit) {
            this.min = min;
            this.max = max;
            this.attemptsLimit = attemptsLimit;
        }
    }

    // Выбор уровня сложности
    private static GameConfig chooseDifficulty(Scanner scanner) {
        while (true) {
            System.out.println("Выберите уровень сложности:");
            System.out.println("1 - Лёгкий (1–50, 10 попыток)");
            System.out.println("2 - Средний (1–100, 7 попыток)");
            System.out.println("3 - Сложный (1–500, 5 попыток)");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return new GameConfig(1, 50, 10);
                case "2": return new GameConfig(1, 100, 7);
                case "3": return new GameConfig(1, 500, 5);
                default:
                    System.out.println("Некорректный ввод. Введите 1, 2 или 3.");
            }
        }
    }

    private static void playRound(Scanner scanner, GameConfig config) {
        Random rand = new Random();
        int secret = rand.nextInt(config.max - config.min + 1) + config.min;
        int attempts = 0;

        System.out.printf("Я загадал число от %d до %d. У вас %d %s.\n",
                config.min, config.max, config.attemptsLimit,
                attemptsWord(config.attemptsLimit));

        while (attempts < config.attemptsLimit) {
            System.out.print("Введи число: ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.println("Пустой ввод — введите число.");
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

            if (guess < config.min || guess > config.max) {
                System.out.printf("Число вне диапазона %d..%d. Попробуйте снова.\n",
                        config.min, config.max);
                continue;
            }

            if (guess < secret) {
                System.out.println("Больше!");
            } else if (guess > secret) {
                System.out.println("Меньше!");
            } else {
                System.out.printf("Правильно! Вы угадали число %d за %d %s.\n",
                        secret, attempts, attemptsWord(attempts));
                return;
            }
        }

        // Если попытки закончились
        System.out.printf("Попытки закончились! Загаданное число было: %d.\n", secret);
    }

    private static boolean askPlayAgain(Scanner scanner) {
        while (true) {
            System.out.print("Сыграть ещё раз? (y/n): ");
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.isEmpty()) continue;
            char c = ans.charAt(0);
            if (c == 'y' || c == 'д') {
                return true;
            } else if (c == 'n' || c == 'н') {
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
