import java.io.*;
import java.util.*;
import java.util.regex.*;

public class LogAnalyzer {
    // Klasse til at repræsentere en log-post
    static class LogEntry {
        String timestamp;
        String level;
        String message;

        LogEntry(String timestamp, String level, String message) {
            this.timestamp = timestamp;
            this.level = level;
            this.message = message;
        }

        // Til CSV-udskrift (håndterer kommaer i beskeder)
        String toCsv() {
            return String.format("\"%s\",\"%s\",\"%s\"", timestamp, level, message);
        }
    }

    public static void main(String[] args) {
        // Stier til mapper (relative til projektmappen)
        String logDirPath = "logs";
        String outputFilePath = "output/error_report.csv";

        // Liste til at gemme alle fejl
        List<LogEntry> errors = new ArrayList<>();
        // Regex til parsing
        String logPattern = "\\[(.*?)\\] (INFO|ERROR|WARNING): (.*)";
        Pattern pattern = Pattern.compile(logPattern);

        // Læs alle .log-filer i logs-mappen
        File logDir = new File(logDirPath);
        File[] logFiles = logDir.listFiles((dir, name) -> name.endsWith(".log"));

        if (logFiles == null || logFiles.length == 0) {
            System.err.println("Ingen .log-filer fundet i " + logDirPath);
            return;
        }

        // Behandl hver logfil
        for (File logFile : logFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String timestamp = matcher.group(1);
                        String level = matcher.group(2);
                        String message = matcher.group(3);

                        if (level.equals("ERROR") || level.equals("WARNING")) {
                            errors.add(new LogEntry(timestamp, level, message));
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Fejl ved læsning af fil " + logFile.getName() + ": " + e.getMessage());
            }
        }

        // Opsummer fejl
        Map<String, Integer> summary = new HashMap<>();
        for (LogEntry entry : errors) {
            summary.put(entry.level, summary.getOrDefault(entry.level, 0) + 1);
        }

        // Eksportér til CSV
        try (PrintWriter writer = new PrintWriter(new File(outputFilePath))) {
            // Skriv fejl-detaljer
            writer.println("Timestamp,Level,Message");
            for (LogEntry entry : errors) {
                writer.println(entry.toCsv());
            }
            writer.println(); // Tom linje for adskillelse
            // Skriv opsummering
            writer.println("Summary");
            writer.println("Level,Count");
            for (Map.Entry<String, Integer> entry : summary.entrySet()) {
                writer.println(String.format("\"%s\",%d", entry.getKey(), entry.getValue()));
            }
            System.out.println("Resultater gemt i " + outputFilePath);
        } catch (FileNotFoundException e) {
            System.err.println("Fejl ved skrivning til CSV: " + e.getMessage());
        }
    }
}