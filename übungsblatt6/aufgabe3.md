## Aufgabe 3

a) In meinem Klasse C Netz sind aktuell 5 Hosts, wobei zwei davon mein Computer sind (nmap hat sowohl die Ethernet IP Adresse als auch die WLAN IP Adresse meines Computers gefunden). Als Befehl habe ich den nmap \<netzanteil meiner Ip\>.0/24 verwendet. Mit diesem Befehl scannt nmap alle Hostadressen zwischen 0 und 255

b) scanme.nmap.org verwendet Linux 5.0 - 5.14, MikroTik RouterOS 7.2 - 7.5 (Linux 5.6.3). Als Befehl habe ich nmap -O -v scanme.nmap.org verwendet. Das -O sorgt dafür das nach dem OS gescannt wird.

c) würde man mit dem Befehl nmap --script whois-domain.nse scanme.nmap.org herausfinden können, leider hat er bei mir nur den normalen nmap scan gemacht.

d) Auf der Seite https://nmap.org/book/reduce-scantime.html werden verschiedene Methoden beschrieben, die das Scannen von einer großen Menge von IP Adressen beschleunigt, darunter sind:
- Skip the port scan (-sn) when you only need to determine what hosts are online.
- Limit the number of ports scanned. 
- Skip advanced scan types
- Remember to turn off DNS resolution when it isn't necessary. 
- Execute Concurrent Nmap Instances
- Scan From a Favorable Network Location
- Increase Available Bandwidth and CPU Time

e) SYN-Scan ist ein Scan bei dem man einen TCP Handshake initialisiert, aber nicht beendet, sondern Abbricht. Nmap sendet ein TCP Paket mit der SYN Flag gestezt. Wenn der Computer mit SYN/ACK antwortet, ist der Port offen und nmap sendet RST um die Verbindung abzubrechen, bevor sie fertig etabliert ist. Sendet der Computer RST, ist der Port zu. Bei keiner Antwort oder einem ICMP Paket wird der Port gefiltert.

f) port 80 für http, port 8443 für https, 5432 für in meinem Fall postgresql (generell sind Datenbanken an diesem Port). 

