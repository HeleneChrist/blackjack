## Aufgabe 2

Um Überhaupt ein DHCP Paket zu empfangen, muss sich die Verbindung mit dem Router ädern, entwerder weil sich der Computer neu mit dem Router verbinedet, oder weil die Verbindung mit dem Router ausgefallen ist. Ich habe daher mit WhireShark meinen Ethernet Traffic gecaptured und immer wieder mal das LAN Kabel zezogen.

Der Computer sendet beim Verbinden mit dem Router einen Broadcast in dem er seine IP Adresse und verschiedene andere Service anfordert.
![DHCP-Paket_Broadcast](./Screenshot%20(15).png)

Als Antwort erhält der Computer ein DHCP ACK in dem die Angeforderten Informationen stehen, wie die IP Adresse. 
![DHCP-Paket-ACK](./Screenshot%20(14).png)