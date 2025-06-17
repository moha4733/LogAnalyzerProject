Log Analyzer
Dette projekt er en automatiseret log-analyse løsning, der indsamler og analyserer systemlogfiler for fejl (ERROR) og advarsler (WARNING) og eksporterer resultaterne til en CSV-fil, som kan bruges i Excel eller Power BI.

Krav

Java: Version 8 eller nyere.
Logfiler: Skal være i formatet [YYYY-MM-DD HH:MM:SS] LEVEL: MESSAGE.

Mappestruktur
LogProjekt/
├── logs/
│   ├── system.log
│   ├── server2.log
├── out/
│   ├── error_report.csv  (genereret ved kørsel)
├── src/
│   ├── LogAnalyzer.java
├── README.md
├── .gitignore
├── dashboard.png  (valgfrit dashboard-billede)

Sådan kører du programmet

Placer logfiler (med .log ekstension) i logs-mappen.
Åbn projektet i IntelliJ IDEA.
Kør LogAnalyzer.java ved at højreklikke på filen og vælge Run 'LogAnalyzer.main()'.
Find resultaterne i out/error_report.csv.

Output
CSV-filen indeholder to sektioner:

Fejl-detaljer: Tabel med Timestamp, Level, Message for alle fundne fejl og advarsler.
Summary: Opsummering af antallet af hver fejltype (f.eks. "ERROR,3" og "WARNING,2").
Valgfrit: Et søjlediagram i dashboard.png viser en visuel opsummering af fejltyper, hvis inkluderet.

Teknologier

Java
Regex
CSV (kompatibel med Excel/Power BI)

Version

Udviklet og testet: 17. juni 2025
Testet med flere logfiler (system.log og server2.log).

