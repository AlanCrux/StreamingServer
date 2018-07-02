#!/usr/bin/env python
 
#importamos el modulo socket
import socket
from pydub import AudioSegment
import os
import threading

class ClientThread(threading.Thread):
    def __init__(self,clientAddress,clientsocket):
        threading.Thread.__init__(self)
        self.sc = clientsocket
        print ("Se conecto un nuevo cliente OwO")
    def run(self):
        while True:  
          #Se reciben los datos de la cancion artista/album/nombre de la cancion
          datosRecibidos = self.sc.recv(1000)
          datosCancion = datosRecibidos
          print 'datos cancion: ' + datosCancion
          datosRecibidos = ""

          #Se crea el archivo temporal en donde sera guardada la cancion que se recibe
          #Agregar ruta donde se guardara en la mac como temporal 
          myfile = open('cancionRecibida.mp3', 'wb')

          #Se construye la cancion recibida 
          print 'Escribiendo cancion ....'
          while True:
            data = self.sc.recv(4096)
            if not data: break
            myfile.write(data)
          myfile.close()
          print 'Escritura finalizada :)'
          
          #Se busca en donde (ruta) esta guardada la cancion recibida
          print '----------- Buscando cancion recibida -------------'
          target = "cancionRecibida.mp3"
          initial_dir = '/home'
          path = ''
          for root, _, files in os.walk(initial_dir):
              if target in files:
                path = os.path.join(root, target)
                break
          print '------------- Donde esta la cancion recibida --------------'
          print(path)
          break  

        #Se crea la ruta en donde seran guardadas las canciones convertidas
        try:
          partesRuta = datosCancion.split("/")
          print partesRuta
          artista = partesRuta[0]
          album = partesRuta[1]
          nombreCancion = partesRuta[2]
          directorioCancion = artista+"/"+album+"/"
          ruta = "/home/alan/TequilaMusic/Repositorio/"+directorioCancion
          print '------------- Ruta donde se guarda la cancion convertida -----------'
          print ruta
          os.makedirs(ruta)
        except OSError:
          pass

        #Se le asigna una letra al nombre de la cancion para representar su calidad
        rutaBaja = ruta+nombreCancion+"B"
        print 'Ruta de calidad baja: '
        print rutaBaja
        rutaMedia = ruta+nombreCancion+"M"
        print 'Ruta de calidad Media: '
        print rutaMedia
        rutaAlta = ruta+nombreCancion+"A"
        print 'Ruta de calidad alta: '
        print rutaAlta

        #Se transforma la cancion a las 3 calidades requeridas
        print 'Guardando en calidad Baja'
        sound = AudioSegment.from_file(path)
        sound.export(rutaBaja, format="mp3", bitrate="48k")

        print 'Guardando en calidad Media'
        sound = AudioSegment.from_file(path)
        sound.export(rutaMedia, format="mp3", bitrate="160k")

        print 'Guardando en calidad Alta'
        sound = AudioSegment.from_file(path)
        sound.export(rutaAlta, format="mp3", bitrate="320k")

        #Se borra el archivo temporal de la cancion (cancionRecibida.mp3)
        print '------------ Eliminando temporal --------------'
        os.remove(path)

        #Se cierra conexion con el cliente
        self.sc.close()
        print 'Cliente desconectado :3'
        print 'Esperando nuevo clientes OuO!'
        s.close

LOCALHOST = ''
PORT = 9091
#instanciamos un objeto para trabajar con el socket
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((LOCALHOST, PORT))
print("PastranaServer activo...")
print("Esperando clientes UuU")
while True:
    s.listen(100)
    clientsock, clientAddress = s.accept()
    newthread = ClientThread(clientAddress, clientsock)
    newthread.start()