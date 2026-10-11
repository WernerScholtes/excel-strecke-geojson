# excel-strecke-geojson

Java-Konsolenanwendung, die das Blatt `Konfiguration` einer Excel-Datei liest und pro `Strecke` eine GeoJSON-FeatureCollection schreibt.

## Voraussetzungen

- JDK 8 oder neuer
- Maven 3.9+

## Start

Pfad zur Excel-Datei in `src/main/resources/application.properties` setzen (`excel.input`) oder als Argument übergeben:

```powershell
mvn -q exec:java -Dexec.args="C:\pfad\datei.xlsx C:\pfad\ausgabe"
```

Ohne Ausgabeverzeichnis entstehen die Dateien im Ordner `geojson` neben der Excel-Datei.

Verarbeitet werden Zeilen, in denen `Standort`, `PermanentId`, `Latitude`, `Longitude` und `Strecke` gesetzt sind. Formeln (z. B. in `PermanentId`) nutzen den von Excel gespeicherten Ergebniswert.
