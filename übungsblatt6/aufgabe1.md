## Aufgabe 1

### Sliding Window

Das Sliding Window beim TCP protokoll bescheibt die Anzahl von Paketen die Das Protokoll sendet ohne eine Bestätigung für das vorhergehende Packet erhalten zu haben. Dank dem Sliding Window ist man in der Lage die Speicherfähigkeit des Mediums besser auszunutzen ohne dabei zuferlässigkeit aufzugeben. 

### TCP Tahoe, TCP Reno und TCP Vegas

TCP Tahoe, TCP Reno und TCP Vegas sind verschiedenen Ansätzte die Größe des Sliding Window festzulegen. TCP Tahoe ist die simpelste Lösung, es startet bei der Fenstergröße 1 und erhöht sie solange, bis die Pakete nicht mehr sicher ankommen, wonach es wieder auf Fentergröße 1 zurückspringt. TCP Reno hat ein ähnliches konzept, es springt aber nur zur halben Fenstergröße, was es zur schnelleren Version macht. TCP Vegas ist eine wieder andere Lösung, die eine Zeitmessung zwischen senden des Packets und dem entsprechenden ACK macht und die Fenstergröße darauf basierend festlegt. Bei Störungen wiederhohlt es die Messung und passt die Fenstergröße an.  
Gebraucht werden TCP Tahoe, TCP Reno und TCP Vega um die Fenstergröße dynamisch angepasst an das Übertragungmedium festzulegen. Ohne sie wäre TCP nicht in der lage die Datenraten mit ensprechender Sicherheit, dass die Packete ankommen, zu erreichen, die wir für unsere Internetstucktur brauchen.

### Andere Protokolle:

1. Ethernet: Ebene 1-3 Ist sowohl die Hardware, die das Netz aufbaut (ethernetkabel), als auch das dazugehörige Ethernet-Frame, der das Format der Daten bestimmt. (existiert also sowohl im Pysical Layer (Stecker), als auch im Link Layer(Kabel), als auch im Network Layer (Ethernet-Frame))

2. IP: Ebene 3: IP ist im Network Layer, da es das Adresseieren von Rechnern in einem Netzwerk auf Nwtzwerkebene und nicht auf Portebene vornimmt.

4. UDP: Ebene 4: Ist im Transportlayer, da es die Port zu Port Verbindung beinhaltet. 

5. TCP: Ebene 4: Siehe UDP

6. ICMP: Ebene 3: Wird über IP Übertragen, daher in der Selben Ebene wie IP


