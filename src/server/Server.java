package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
  List<ClientHandler> clients;

  public  Server(){
    clients = new CopyOnWriteArrayList<>();


    ServerSocket server = null; // серверный соккет
    Socket socket = null; // Пользовательский соккет
    final int PORT = 8189; // Серверный порт

    try {
      server = new ServerSocket(PORT); // Создаем объект сервер
      System.out.println("Server started");




      
      while (true) {
        socket = server.accept(); // Блокирующий оператор. Который заставляет сервер ждать клиента типо потока чтоли также серверный порт выделит по этой команде соккет серверный и передаст в пользовательский
        System.out.println("Client connected");

        clients.add(new ClientHandler(this, socket));
      }
      
      
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {

        server.close(); // Обязательно закрываем сервер в любом случае
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  public void broadcastMsq(String msq) {
    for (ClientHandler c : clients) {
      c.sendMsg(msq);
    }
  }




}
