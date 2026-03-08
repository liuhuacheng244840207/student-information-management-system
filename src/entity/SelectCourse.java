// entity/SelectCourse.java（选课表实体）
package entity;
public class SelectCourse {
    private int selectCourseId; // 选课ID（主键）
    private int studentId; // 学生ID（外键关联学生表）
    private int courseId; // 课程ID（外键关联课程表）
    private String selectCourseTime;
    private String state; 
    
    // 无参/有参构造 + Getter/Setter
    public int getSelectCourseId() {
        return selectCourseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setSelectCourseId(int selectCourseId) {
        this.selectCourseId = selectCourseId; // 关键：把参数赋值给成员变量
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId; // 关键
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId; // 关键
    }
    
    public void setSelectCourseTime(String selectCourseTime) {
		this.selectCourseTime = selectCourseTime;
	}    

	public String getSelectCourseTime() {
		return selectCourseTime;
	}

	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}