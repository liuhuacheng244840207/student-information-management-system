package entity;

public class Student {
    private int studentId;       // 学生ID
    private String studentName;  // 姓名
    private String gender;       // 性别
    private String studentNo;    // 学号
    private String className;    // 班级
    private String contact;      // 联系方式

    // 无参构造
    public Student() {}

    // Getter & Setter
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}