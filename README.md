### KANDIDATNUMMER

200005 | 200003

------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------

## PGR200 - Advanced Java Exam

![Screenshot](https://travis-ci.com/NickVatne/PGR200Exam.svg?branch=master)


###Kjøreinstruksjoner

#####Terminal/Cmd
   
- OBS! innlevering.properties ligger i PGR200Exam/Database/src/main/resources/innlevering.properties

- Åpne terminalen og naviger deg til prosjektmappen

- Kjør mvn clean install i prosjektmappen

- Kjør mvn package

- Naviger deg til http/target-mappen

- Skriv inn java -cp com.kristiania.pgr.http-0.0.1.jar com.kristiania.pgr200.http.HttpServer

- Åpne nytt terminal/cmd vindu

- Vaviger til Client/target mappe

- Skriv inn java -cp com.kristiania.pgr200.client-0.0.1.jar com.kristiania.pgr200.client.Client

#####IDE

- Kjør /PGR200Exam/HTTP/src/main/java/com/kristiania/pgr200/http/HttpServer.java

- Kjør /PGR200Exam/Client/src/main/java/com/kristiania/pgr200/client/Client.java

- Følg meny-valg i client-terminalvindu.

------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------


##### Database Design
![Database](documentation/DBSCHEMA.png)

------------------------------------------------------------------------------------------------------------
### Programkrav / Egenvurdering
- Programmet er bygget på en Maven Arkitektur med pgr200exam som root, med undermoduler kalt HTTP, Database og Client med tilhørende POM filer.
- Programmet bygger på TravisCI 
- Programmet er bygget på å kommunisere med GET og POST requests.
- Programmet anvender JUnit Test Library med
    - 8 tester representert i HTTP modulen
    - 5 tester representert i Database modulen
    - 0 tester representert i Client modulen
- I modulen Database finnes innlevering.properties som anvender curl for å leses.
- Grunnet begrenset tidsramme er databasen bygget på en enkel tabell, vi hadde derimot ønske om å anvende flere men grunnet en begrenset tidsramme var det ikke realistisk for oss å håndtere foreign keys, primary key forhold.
    (SE VEDLAGT "DATABASE DESIGN")
    
- Clienten har følgende commands "Create", "Show", "Update", "Exit".
- Databasen kommuniserer med HTTP som kravet i eksamensoppgaven spesifiserer.
- Database koden følger DAO pattern som ønsket i oppgaven.
- Vi har tatt høyde for SQL injections ved å anvende Prepared-Statements på tvers av Database/Http klassene/metodene.
- Screencast av programmeringsession funnet sted 31. Oktober 2018 (Ingen ny video ble tatt grunnet mangelende tid og en uforventet eksamensoppgave som var beskrevet som en individuell oppgave)
https://www.youtube.com/watch?v=9UEs4b3c9xI

------------------------------------------------------------------------------------------------------------

Fastsatte Eksamenskrav:

DATABASE
- DB skal inneholde Oppgavenavn, Beskrivelse, Status, Oppgavetakere
- DB Funksjoner:
  - Opprette
  - Liste opp
  - Se på detaljer
  - Endre oppgaver
------------------------------------------------------------------------------------------------------------

HTTP/SERVER
- Ta i bruk POST for opprettelse og oppdateringer av oppgaver
- Ta i bruk GET for å hente oppgaver
- Serveren lagrer oppgavene i Postgresql
- Conncection url, brukernavn og passord til DB skal ligge i properties-fil
------------------------------------------------------------------------------------------------------------

ANNET
- Demonstrere bruk av Github og Travis-CI
- README.md-fil som inneholder:
  - Kjøreinstruksjoner
  - Egenvurdering
  - Kort designbeskrivelse
  - Diagram med beskrivelse av programfunksjonalitet
  - Link til screencast video(parprogrammering)
  - Skrive tester i JUnit

------------------------------------------------------------------------------------------------------------
TAKK FOR OSS
