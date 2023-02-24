public class Student {
    private String lastName;
    private String firstName;
    private String phone;
    private int group;
    private String direction;
    private int ID;
    Student(String lastName, String firstName, String phone, int group, String direction, int ID) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.group = group;
        this.direction = direction;
        this.ID = ID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPhone(String phone) { this.phone = phone; }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhone() { return phone; }

    public int getGroup() {
        return group;
    }

    public String getDirection() {
        return direction;
    }

    public int getID() {
        return ID;
    }

    public String getInfo() {
       return String.format("%s %s - слушатель %d группы направления %s", lastName, firstName, group, direction);
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}