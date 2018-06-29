#!/usr/bin/env python
 
#importamos el modulo para trabajar con sockets
import socket
from pydub import AudioSegment

HOST = 'localhost'
PORT = 1234
ADDR = (HOST,PORT)
#Creamos un objeto socket para el servidor. Podemos dejarlo sin parametros pero si 
#quieren pueden pasarlos de la manera server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect(ADDR)
client.send("Los_Angeles_Azules/")
client.send("De_Plaza_en_Plaza/")

client.send("La_Cumbia_del_Infinito")

print "Enviando cancion"
sound = "/home/esmeralda/TequilaMusic/Repositorio/Los_Angeles_Azules_De_Plaza_en_Plaza/La_Cumbia_del_Infinito.mp3"
bytes = open(sound).read()
print len(bytes)
client.send(bytes)

#print 'ruta donde se guardo la cancion'
#print client.recv(1024)

client.close()

