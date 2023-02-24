import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class GBUniversity {
    private ArrayList<Student> students;

    private int generateID;
    private String designation;
    private String[] directions = {"Разработчик", "ИТ-инженер", "Аналитик"};

    {
        generateID = 1;
    }

    GBUniversity(){
        students = new ArrayList<>();
    }

    public void addStudent(String surname, String name, String phone, String direction, int group){
        Student student = new Student(surname, name, phone, group, direction, generateID++);
        students.add(student);
    }

    private int findStudent(String surName, String name, String phone)
    {
        if (students.isEmpty())
            return -1;
        int index = 0;
        for(Student student: students)
            if (student.getLastName().equals(surName)   &&
                student.getFirstName().equals(name)     &&
                student.getPhone().equals(phone))
                return index;
        return -1;
    }
    public String studentGetInfo(String surName, String name, String phone){
        int searchvalue = findStudent(surName, name, phone);
        if( searchvalue > -1) {
            return String.format("%s %s - слушатель %d группы направления %s", name, surName, students.get(searchvalue).getGroup(), students.get(searchvalue).getDirection());
        }
        return String.format("%s %s не найден(а) в списке слушателей", name, surName);
    }

    public String removeStudent(String surName, String name, String phone) {
        int searchvalue = findStudent(surName, name, phone);
        if (searchvalue > -1) {
            String result = String.format("%s %s отчислен из %d группы направления %s", name, surName, students.get(searchvalue).getGroup(), students.get(searchvalue).getDirection());
            students.remove(searchvalue);
            return result;
        }
        return String.format("%s %s не найден(а) в списке слушателей", name, surName);
    }

    private HashSet<Integer> groupsList(String direction)
    {
        if (students.isEmpty()) return null;
        HashSet<Integer> directionGroups = new HashSet<>();
        for(Student s: students)
        {
            if(s.getDirection().equals(direction))
                directionGroups.add(s.getGroup());
        }
        return directionGroups;
    }

    public ArrayList<Student> groupList(int group, ArrayList<Student> list)
    {
        if (list.isEmpty()) return null;
        ArrayList<Student> filter = new ArrayList<>();
        for(Student s: list)
        {
            if(s.getGroup() == group)
                filter.add(s);
        }
        return filter;
    }

    public ArrayList<Student> directionList(String direction, ArrayList<Student> list)
    {
        if (list.isEmpty()) return null;
        ArrayList<Student> filter = new ArrayList<>();
        for(Student s: list)
        {
            if(s.getDirection().equals(direction))
                filter.add(s);
        }
        return filter;
    }

    public String studentsListalphabetical(){
        String result = "Список всех студентов университета:" + "\n";
        ArrayList<Student> toPrint = sortResult(students);
        for(Student st: toPrint)
            result += " " + st.getInfo() + "\n";
        return result;
    }

    public String studentsGroup(int group){
        ArrayList<Student> byGroup = groupList(group, students);
        if (byGroup.isEmpty()) {
            return "Указанная группа ещё не сформирована";
        }
        String result = "Список студентов " + group + " группы:\n";
        ArrayList<Student> toPrint = sortResult(byGroup);
        for(Student st: toPrint)
            result+="     " + st.toString()+"\n";
        return result;
    }

    public String studentsDirection(String direction){
        ArrayList<Student> byDirection = directionList(direction, students);
        if (byDirection.isEmpty()) {
            return "На данном направлении нет слушателей";
        }
        String result = "Список студентов направления " + direction + '\n';
        HashSet groups = groupsList(direction);
        Iterator<Integer> numGroup = groups.iterator();
        while (numGroup.hasNext()){
            Integer gr = numGroup.next();
            ArrayList<Student> byGroups = groupList(gr, students);
            result += "   группа " + gr + '\n';
            ArrayList<Student> toPrint = sortResult(byGroups);
            for(Student st: toPrint)
                result += "     " + st.toString() + '\n';
        }
        return result;
    }

    public ArrayList<Student> sortResult(ArrayList<Student> list)
    {
        boolean obmen = true;
        int n = list.size();
        int k = 0;
        for (; k < n - 1 && obmen; k++)
        {
            obmen = false;
            for(int i = 0; i < n - k - 1; i++)
            {
                String prev = list.get(i).getLastName();
                String next = list.get(i+1).getLastName();
                if (prev.compareTo(next) > 0)
                {
                    Collections.swap(list, i, i + 1);
                    obmen = true;
                }
            }
        }
        return list;
    }
}
