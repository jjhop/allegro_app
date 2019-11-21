Kompilacja wraz z testami

    $ gradle clean test bootJar

Uruchomienie aplikacji lokalnie

    $ gradle bootRun

lub po zbudowaniu (`gradle clean test bootJar`)

    $ java -jar build/libs/github-repositories-0.0.1-SNAPSHOT.jar
    
Po uruchomieniu warto poeksperymentować z udostępnianym API. W tym celu należy uruchomić ulubioną 
przeglądarkę www i pokierować ją pod adres `http://localhost:5150/swagger-ui.html`


Uruchomienie w środowisku produkcyjnym

Po zbudowaniu (`gradle clean test bootJar`) plik jar uzyskany w wyniku tego polecenia (znajdujący się w katalogu 
`build/libs/`) należy przenieść do pożądanej lokalizacji (np. `/opt`) na docelowym serwerze i uruchomić za pomocą polecenia

    $ java  -jar /opt/github-repositories-0.0.1-SNAPSHOT.jar

lub jeśli chcemy zmienić port (np. na `8080`)

    $ java -Dserver.port=8080 -jar /opt/github-repositories-0.0.1-SNAPSHOT.jar 

