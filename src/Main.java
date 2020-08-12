
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        for (; ; ) {
            try {
                if (args.length < 1) {
                    System.out.println("Введите комманду \"echo\" и текст");
                    String[] text = getArgs();
                    int count = getWordsCount(text);
                    String wordInCorrectCase = getCorrectCase(count);
                    System.out.println("\nРезультат\nВ тексте " + count +
                            " " + wordInCorrectCase);
                    getPopularWordsCount(text);
                } else {
                    String[] processedArgs = processArgs(args);
                    int count = getWordsCount(processedArgs);
                    String wordInCorrectCase = getCorrectCase(count);
                    System.out.println("\nРезультат\nВ тексте " + count +
                            " " + wordInCorrectCase);
                    getPopularWordsCount(processedArgs);
                } return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getWordsCount(String[] args) {
        return args.length;
    }

    public static String[] getArgs() {
        for (; ; ) {
            Scanner scanner = new Scanner(System.in);
            String text = scanner.nextLine();
            if (text != null) {
                String[] words = text.replaceAll("-", " ")
                        .replaceAll("[^a-zA-Zа-яА-Я0-9]", " ")
                        .split("\\s");
                String[] onlyWords = Stream.of(words)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toArray(String[]::new);
                if (!onlyWords[0].equals("echo") || onlyWords.length < 2) {
                    throw new IllegalArgumentException();
                }
                return Arrays.copyOfRange(onlyWords, 1, onlyWords.length);
            }
        }
    }

    public static String[] processArgs(String[] args) {
        String text = Stream.of(args)
                .map(s -> s.replaceAll("-", " "))
                .map(s -> s.replaceAll("[^a-zA-Zа-яА-Я0-9]", " "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .reduce((s, s2) -> s.concat(" ").concat(s2)).orElseThrow();
        return text.split("\\s");
    }

    public static void getPopularWordsCount(String[] words) {
        Map<String, Integer> wordsToCount = new TreeMap<>();
        List<String> wordList = Stream.of(words)
                .map(String::toLowerCase)
                .sorted()
                .collect(Collectors.toList());
        for (int i = 0; wordList.size() > 0; i++) {
            Set<String> uniqueWords = new TreeSet<>(wordList);
            for (String word : uniqueWords) {
                wordsToCount.put(word, i + 1);
                wordList.remove(word);
            }
        }
        List<Map.Entry<String, Integer>> toSort = new ArrayList<>(wordsToCount.entrySet());
        toSort.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        System.out.println("Top " + wordsToCount.size() + ": ");

        toSort.stream().limit(wordsToCount.size()).forEach(e -> System.out.format("%d - %s%n", e.getValue(), e.getKey()));
    }

    private static String getCorrectCase(int count) {
        if (count % 10 == 1 && count != 11)
            return "слово";
        else if ((count % 10 == 2 && count != 12) || (count % 10 == 3 && count != 13)
                || (count % 10 == 4 && count != 14)) return "слова";
        else return "слов";
    }
}
