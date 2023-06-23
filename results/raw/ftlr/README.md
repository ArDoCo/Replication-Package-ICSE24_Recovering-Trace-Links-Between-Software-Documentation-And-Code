# FTLR Ergebnisse

Drei Verfahren:
- FTLR (WMD auf Elementebene)
- FileLevelWMD (WMD auf Artefaktebene)
- FileLevelAvg (Kosinus auf gemittelten Vektoren eines Artefakts)

jeweils mit oder ohne Methodenkommentare (MC)

tracelinks wurden mit folgenden Default-Schwellenwerten erzeugt:
- FTLR: `majority=0.59`, `final=0.44`
- FileLevelWMD: `0.506`
- FileLevelAvg: `0.741`

Die `*_eval_result.xlsx` enthalten die Ergebnisse im F1 bei einer systematischen Suche der besten Schwellenwerte. Die letzte Zeile enthält das zusammengefasste Ergebnis in Form:
| Opt:  |       |           |        |    | Default:  |        |    | MAP | LAG |
|-------|-------|-----------|--------|----|-----------|--------|----|-----|-----|
| Maj   | Final | Precision | Recall | F1 | Precision | Recall | F1 | MAP | LAG