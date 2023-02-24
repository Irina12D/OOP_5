import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {

        // создаю здесь базу студентов GeekBrains - буду имитировать процесс связи сервера с БД

        String[] surnames = {"Гринёва", "Саранов", "Романов", "Сурков", "Пименева", "Крылова", "Хванов", "Петров", "Смирнов", "Алексеев"};
        String[] names = {"Оксана", "Максим", "Олег", "Давид", "Диана", "Светлана", "Денис", "Александр", "Сергей", "Марат"};
        String[]phones = {"11111111", "11115555", "11118888", "33337799", "33339977", "44445555", "33331111", "11110000", "44443030", "33338800"};
        String[]directions = {"Разработчик", "ИТ-инженер", "Разработчик", "ИТ-инженер", "Аналитик", "ИТ-инженер", "Разработчик", "Аналитик", "Аналитик", "ИТ-инженер"};
        int[]groups = {1111, 8888, 1111, 8888, 5234, 9999, 1111, 2234, 5255, 9999};

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Сервер запущен, ожидаем соединение...");
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился к серверу!");
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            GBUniversity GBstudents = new GBUniversity();
            for(int i=0; i<10; i++) {
                GBstudents.addStudent(surnames[i], names[i], phones[i], directions[i], groups[i]);
            }

            while (true) {
                String clientRequest = dataInputStream.readUTF();
                if (clientRequest.equals("0")) {
                    System.out.println("\nКлиент отключился от сервера");
                    break;
                }
                switch (clientRequest.charAt(0)) {
                    case '1':
                        System.out.println("\nКлиент запрашивает добавление студента");
                        dataOutputStream.writeUTF(request1(clientRequest, GBstudents));
                        System.out.println("Добавление студента выполнено успешно");
                        break;
                    case '2':
                        System.out.println("\nКлиент запрашивает удаление студента");
                        String result = request2(clientRequest, GBstudents);
                        dataOutputStream.writeUTF(result);
                        if(! result.contains("не найден"))
                            System.out.println("Удаление студента выполнено успешно");
                        else
                            System.out.println("Удаление не выполнено из-за отсутствия студента в базе данных");
                        break;
                    case '3':
                        System.out.println("\nКлиент запрашивает список группы");
                        dataOutputStream.writeUTF(request3(clientRequest, GBstudents));
                        System.out.println("Запрос выполнен, результат успешно отправлен");
                        break;
                    case '4':
                        System.out.println("\nКлиент запрашивает список направления");
                        dataOutputStream.writeUTF(request4(clientRequest, GBstudents));
                        System.out.println("Запрос выполнен, результат успешно отправлен");
                        break;
                    case '5':
                        System.out.println("\nКлиент запрашивает список всех студентов");
                        dataOutputStream.writeUTF(request5(clientRequest, GBstudents));
                        System.out.println("Запрос выполнен, результат успешно отправлен");
                        break;
                    case '6':
                        System.out.println("\nКлиент запрашивает информацию по студенту");
                        result = request6(clientRequest, GBstudents);
                        dataOutputStream.writeUTF(result);
                        if(! result.contains("не найден"))
                            System.out.println("Информация отправлена клиенту");
                        else
                            System.out.println("Информация не найдена из-за отсутствия студента в базе данных");
                        break;
                }

             }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String request1(String clientRequest, GBUniversity db) {
        String[] param = clientRequest.split(";");
        db.addStudent(param[1], param[2], param[3], param[4], Integer.parseInt(param[5]));
        return String.format("Слушатель %s %s успешно зачислен на направление %s в группу %s", param[1], param[2], param[4], param[5]);
    }

    static String request2(String clientRequest, GBUniversity db) {
        String[] param = clientRequest.split(";");
        return  db.removeStudent(param[1], param[2], param[3]);
    }

    static String request3(String clientRequest, GBUniversity db) {
        String[] param = clientRequest.split(";");
        return  db.studentsGroup(Integer.parseInt(param[1]));
    }

    static String request4(String clientRequest, GBUniversity db) {
        String[] param = clientRequest.split(";");
        return  db.studentsDirection(param[1]);
    }

    static String request5(String clientRequest, GBUniversity db) {
        return  db.studentsListalphabetical();
    }

    static String request6(String clientRequest, GBUniversity db) {
        String[] param = clientRequest.split(";");
        return  db.studentGetInfo(param[1], param[2], param[3]);
    }
}
