import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Client {
    static Scanner scanner = new Scanner(System.in);
    static String[] directions = {"Разработчик", "ИТ-инженер", "Аналитик"};
    static int[] groups_0 = {1111};
    static int[] groups_1 = {8888, 9999};
    static int[] groups_2 = {5234, 2234, 5255};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 1234)) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Добро пожаловать на платформу GeekBrains");
            while (true) {
                System.out.println("Главное меню: \n " +
                        "   1 - добавить студента \n " +
                        "   2 - удалить студента \n " +
                        "   3 - вывести студентов указанной группы \n " +
                        "   4 - вывести список студентов указанного направления \n " +
                        "   5 - сформировать список всех студентов университета \n " +
                        "   6 - вывести информацию по конкретному студенту \n " +
                        "   0 - выйти из приложения");
                System.out.print("  Выберите пункт меню: ");
                String numMenu = scanner.nextLine().trim();
                if (numMenu.equals("0")) {
                    dataOutputStream.writeUTF(numMenu);
                    System.out.println("  Будем рады вас видеть снова!");
                    break;
                }
                switch (numMenu) {
                    case "1":
                        dataOutputStream.writeUTF(request1());
                        break;
                    case "2":
                        dataOutputStream.writeUTF(request2());
                        break;
                    case "3":
                        dataOutputStream.writeUTF(request3());
                        break;
                    case "4":
                        dataOutputStream.writeUTF(request4());
                        break;
                    case "5":
                        dataOutputStream.writeUTF(request5());
                        break;
                    case "6":
                        dataOutputStream.writeUTF(request6());
                        break;
                }
                System.out.println();
                System.out.println("Получили сообщение от сервера: " + dataInputStream.readUTF());
                System.out.println();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }


    static String request1() {
        StringBuilder result = new StringBuilder("1;");
        result.append(personalData());
        System.out.println("  Укажите номер направления: ");
        System.out.print("    1 - Разработчик, 2 - ИТ-инженер, 3 - Аналитик: ");
        String input = scanner.nextLine();
        result.append(directions[Integer.parseInt(input.trim()) - 1] + ";");
        System.out.println("  Укажите номер группы зачисления: ");
        result.append(choiceGroupByDirection(input));
        return result.toString();
    }

    static String request2() {
        StringBuilder result = new StringBuilder("2;");
        result.append(personalData());
        return result.toString();
    }

    static String request3() {
        StringBuilder result = new StringBuilder("3;");
        System.out.println("  Укажите номер направления интересующей группы: ");
        System.out.print("    1 - Разработчик, 2 - ИТ-инженер, 3 - Аналитик: ");
        String inputDir = scanner.nextLine();
        System.out.println("  Укажите номер интересующей группы: ");
        System.out.println("    группы направления " + directions[Integer.parseInt(inputDir)-1] + ": " + groupsByDirection(Integer.parseInt(inputDir)));
        String inputGr = scanner.nextLine().trim();
        switch (inputDir) {
            case "1": result.append(groups_0[Integer.parseInt(inputGr)-1] + ";");
            case "2": result.append(groups_1[Integer.parseInt(inputGr)-1] + ";");
            case "3": result.append(groups_2[Integer.parseInt(inputGr)-1] + ";");
        }
        return result.toString();
    }

    static String request4() {
        StringBuilder result = new StringBuilder("4;");
        System.out.println("  Укажите номер направления: ");
        System.out.print("    1 - Разработчик, 2 - ИТ-инженер, 3 - Аналитик: ");
        String input = scanner.nextLine();
        result.append(directions[Integer.parseInt(input.trim())-1]);
        return result.toString();
    }

    static String request5() {
        return "5";
    }

    static String request6() {
        return "6;" + personalData();
    }


    static String personalData() {
        StringBuilder result = new StringBuilder();
        System.out.print("  Введите фамилию студента: ");
        String input = scanner.nextLine();
        result.append(input.trim() + ";");
        System.out.print("  Введите имя студента: ");
        input = scanner.nextLine();
        result.append(input.trim() + ";");
        System.out.print("  Введите номер телефона студента: ");
        input = scanner.nextLine();
        result.append(input.trim() + ";");
        return result.toString();
    }

    static String choiceGroupByDirection(String numDirection) {
        String toPrint = "    ";
        switch (numDirection) {
            case "1":
                toPrint += groupsByDirection(1);
                break;
            case "2":
                toPrint += groupsByDirection(2);
                break;
            case "3":
                toPrint += groupsByDirection(3);
                break;
        }
        System.out.print(toPrint + ": ");
        String input = scanner.nextLine();
        return input.trim();
    }
    static String groupsByDirection(int numDirection) {
        String result = "";
        switch (numDirection) {
            case 1:
                for (int i = 0; i < groups_0.length; i++) result += String.format("%d - %d  ", i + 1, groups_0[i]);
                break;
            case 2:
                for (int i = 0; i < groups_1.length; i++) result += String.format("%d - %d  ", i + 1, groups_1[i]);
                break;
            case 3:
                for (int i = 0; i < groups_2.length; i++) result += String.format("%d - %d  ", i + 1, groups_2[i]);
                break;
        }
        return result.trim();
    }
}
