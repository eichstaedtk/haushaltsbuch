# Anwendung zur Führung eines Haushaltsbuches
![Gluecksschwein](gluecksschwein_klein.jpg)
# Inhaltsverzeichnis #
[1. Einführung und Ziele ](#1-einf%C3%BChrung-und-ziele)<br/>
[1.1 Aufgabenstellung](#11-aufgabenstellung)<br/>
[1.2 Qualitätsziele ](#12-qualit%C3%A4tsziele)<br/>
[1.3 Projektbeteiligte ](#13-projektbeteiligte-stakeholder)        
[2. Randbedingungen ](#2-randbedingungen)<br/>
[3. Kontextabgrenzung](#3-kontextabgrenzung)<br/>
[4. Lösungsstrategie](#4-l%C3%B6sungsstrategie)<br/>
[5. Baustein](#5-bausteinsicht)<br/>
[6. Laufzeitsicht](#6-laufzeitsicht)<br/>
[7. Verteilungssicht](#7-verteilungssicht)<br/>
[8. Betrieb und Wiederherstellung](#8-betrieb-und-wiederherstellung)<br/>
[8.1 Ansprechpartner , Service Level](#81-ansprechpartner-service-level)<br/>
[8.2 Installation / Konfiguration](#82-installation-konfiguration)<br/>
[9. Entwurfsentscheidungen](#9-entwurfsentscheidungen)<br/>
[10. Qualitätsziele](#10-qualit%C3%A4tsziele)<br/>
[11. Risiken und technische Schulden](#11-risiken-und-technische-schulden)<br/>
[12. Glossar](#12-glossar)<br/>

# 1. Einführung und Ziele #
Zielstellung dieser Anwendung ist es Anwendern das Erstellen eines gemeinsam zu nutzenden Haushaltbuches zur Eingabe der finanziellen Ausgaben und Einnahmen zu ermöglichen. Dazu soll es dem Anwender ermöglicht werden, alle Ausgaben und Einnahmen eines Haushaltes digital zu erfassen und entsprechende Ein- und Ausgabekategorien zuordnen zu können. 
Anwender sollen mehrere Haushaltsbücher gleichzeitig führen können. Jeder Anwender soll anderen Anwendern es ermöglichen können, gemeinsam ein Haushaltsbuch zu führen. Haushaltsbücher anderer Anwender dürfen nur durch autorisierte Anwender eingesehen und verwaltet werden. 
Die Software soll über die eingegebenen Ausgaben und Einnahmen Berichte über bestimmte Zeiträume und Kategorien darstellen können. Dazu soll der Benutzer jeweils die Möglichkeit bekommen den Berichtszeitraum und eine zur Verfügung stehende Berichtsform wählen zu können. Alle zur Verfügung stehenden Kategorien der Einnahmen und Ausgaben müssen durch die Benutzer selbst erstellt und gepflegt werden können. 
Alle Benutzer sollen sich mit einem Benutzernamen und Passwort an dem System authentifizieren. Die Softwareanwendung soll in einem Web Browser aufgerufen und gesteuert werden können. Dabei sollen die derzeit populären Browser Chrome und Firefox unterstützt werden. 

## 1.1 Aufgabenstellung ##

## 1.2 Qualitätsziele ##
Folgende nicht funktionale Anforderungen müssen bei Implementierung der Anwendung berücksichtigt werden. 

**Wartbarkeit**
Die Software soll schnell und einfach an neue Anforderungen angepasst werden können. Dazu sollen die bestehenden Anwendungen durch automatisierte Tests geprüft werden, sodass bei 
durchzuführenden Änderungen die Sicherheit besteht bereits bestehenden Funktionalitäten nicht beeinflusst zu haben.

**Usability / Gebrauchstauglichkeit**
Die Software soll einen hohen Grad an Gebrauchtstaglichkeit aufweisen. Das bedeutet, dass der Nutzer seine Ziele effizient, effektiv und mit einem hohen Grad an Zufriedenheit mit Hilfe der Software erreichen kann. Die Kriterien der Gebrauchstauglichkeit sind in der ISO 9241 festgehalten.  

## 1.3 Projektbeteiligte (Stakeholder) ##
Die Bedürfnisse folgender Projektbeteiligter sind zu berücksichtigen: 

* **Anwender:** Die Nutzerbedürfnisse und Ziele können durch eine mögliche Anwenderbefragungen im Vorfeld oder durch Anwendertests herausgearbeitet werden. 
* **Administratoren / IT - Betrieb:** Die Anforderungen an den Betrieb der Anwendung müssen berücksichtigt werden. Die Anwendung soll auf einem Server mit dem Betriebssystem Windows Server bzw. Debian Linux installiert werden können. Die Anwendung soll im Internet verfügbar gemacht werden können.
* **Entwicklungsteam:** Aufgrund der Vorkenntnisse und der bereits aus anderen Projekten bestehenden Erfahrungen bei der Umsetzung ähnlicher Projekte soll das Projekt mit Hilfe der Java Enterprise / Spring Framework Technologie umgesetzt werden.
  
# 2. Randbedingungen
Für die Anwendungsentwicklung stehen ca. 6 Monate zur Verfügung. Aufgrund personeller Schwierigkeiten im Bereich der Entwicklung besteht das Entwicklungsteam nur aus einer Person. 
# 3. Kontextabgrenzung

Die Anwendung ist als eine Web Serveranwendung umgesetzt, welche HTML Seiten auf Browseranfragen des Nutzers ausliefert. Die Anwendung selbst wird in einem Tomcat Anwendungserver installiert und ausgefürt. Die Anwendung kann mit Hilfe einer ausführbaren JAR Datei gestartet werden.    
Zusätzlich wird diese Anwendung in einem isolierten [Docker](https://www.docker.com/) Container ausgeführt. Mit Hilfe der Docker Technologie kann die Anwendung einfach gestartet und gestoppt und in verschiedenen Versionen ausgeführt werden.  
Alle Daten der Anwendung werden in einer relationalen Datenbank gespeichert. Als Datenbankmanagementsystem (DBMS) wurde die Software [Postgres](https://www.postgresql.org/) ausgewählt. Auch das DBMS wird in einem Docker Container ausgeführt. Beide Docker Conatiner werden 
gemeinsam mit Hilfe der Docker Compose Technologie gemeinsam ausgeführt und in einem Docker Netzwerk verbunden. Aller Docker und Docker Compose Konfigurationsdateien sind im Softwareprojekt enthalten. 

Folgende Abbildung veranschaulicht den gesamten Kontext der Anwendung: 

![Kontext der Anwendung](kontext-haushaltsbuch.png) 

Wie bereits beschrieben kann der Nutzer die Anwendung im Internet aufrufen. Dazu muss der Anwendungsserver über einen DNS Namen im Internet erreichbar sein. Alle Nutzerdaten werden nach Eingabe 
in der Postgres Datenbank gespeichert.
# 4. Lösungsstrategie

Die Anwendung Haushaltsbuch wurde mit Hilfe folgender Technologien umgesetzt: 

  * [Java 8](https://www.oracle.com/de/java/index.html) 
  * [Spring Boot 2](https://spring.io/projects/spring-boot)
  * Spring Data, Spring Web MVC, Thymeleaf HTML Template Engine, Spring Security 
  
Als Laufzeitumgebung wurde der Anwendungsserver Tomcat in der embedded Version von Spring Boot ausgewählt. Spring Boot ermöglicht den Berieb einer Anwendung als sogenanntes [Self-Contained System](https://scs-architecture.org/). Das bedeutet, dass im Rahmen des Build Prozesses ein installierbares Softwareartefakt, in diesem Fall eine JAR Datei, 
erstellt wird, welche alle notwendigen Bestandteile und Konfigurationen für den Betrieb enthält. So wird zum Beispiel beim ausführen der JAR Datei der embedded Tomcat Anwendungserver bereits fertig konfiguriert gestartet. 
Diese Vorgehensweise macht eine aufwendige und komplizierte Installation und Konfiguration auf einem Server überflüssig, sodass eine permanente wiederholbare Installation nach Phasen der Entwicklung der Anwendung durchgeführt werden kann. 

Als Build Tool wurde in diesem Projekt [Gradle](https://gradle.org/) verwendet. Gradle ermöglicht es über eine Build Konfigurationsdatei den Java Build Prozess zu steuern und durchzuführen. Gradle bietet weiterhin viele Plugings 
um den Build Prozess an die eigenen Bedürfnisse anzupassen. Es wurde zum Beispiel in diesem Projekt das Docker Plugin für das Erstellen des Docker Containers verwendet. Der Build Vorgang kann 
mit folgendem Befehl gestartet werden: 

    gradle build docker



Zusätzlich kann des Build Script mit Hilfe der Programmiersprache [Groovy](https://gradle.org/) umfangreich beeinflusst werden. 
   
# 5. Bausteinsicht

Die Anwendung ist in einer 3 Schichten Architektur umgesetzt worden. Alle Klassen und Packete sind dabei in folgende Schichten aufgeteilt worden: 

  1. **Application Layer**: Diese Schicht enthält alle Klassen zur Verwatung der Darstellung. Die Umsetzung der HTML Ansichten ist nach dem Design Pattern "Model View Controller" umgesetzt. Dies bedeutet, dass die Daten der HTML Seiten im Model verwaltet werden 
                 die Steuerung der Aktivitäten innerhalb der HTML Seiten über die Controller umgesetzt wird und mit Hilfe der View die HTML Ansichten erzeugt werden. Das MVC Pattern wird bereits durch das Spring Framework Spring Web MVC umgesetzt und unterstützt.
  2. **Domain Layer**: Diese Schicht enthält alle Klassen zur Steuerung der Anwendungsdaten und Prozesse. Die Klassen sind nach dem Ansatz des operativen Design aus dem Konzept des [Domain Driven Design](http://dddcommunity.org/) realisiert worden. 
  3. **Infrastructure Layer**: Diese Schicht enthält alle Klassen zur Steuerung und Verwaltung der Infratsrukturkomponenten. Dazu zählt die Konfiguration des Anwendungsframeworks Spring Security und Spring Web MVC. 
   
               

# 6. Laufzeitsicht
# 7. Verteilungssicht
# 8. Betrieb und Wiederherstellung #
## 8.1 Ansprechpartner , Service Level 
## 8.2 Installation / Konfiguration ##               
## 8.3 Wiederherstellung ##
# 10. Qualitätsziele
# 11. Risiken und technische Schulden
# 12. Glossar