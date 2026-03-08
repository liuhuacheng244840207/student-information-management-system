package entity;

// 类名与文件名一致：Score
public class Score {
    // 类属性（严格统一大小写）
    private int scoreId;          // 成绩ID（属性名：scoreId）
    private int selectCourseId;   // 选课ID（属性名：selectCourseId）
    private float usualScore;     // 平时成绩（属性名：usualScore）
    private float finalScore;     // 期末成绩（属性名：finalScore）
    private float totalScore;     // 总评成绩（属性名：totalScore）

    // 1. 无参构造（类名与构造方法名一致）
    public Score() {}

    // 2. 新增成绩用的构造（不含scoreId）
    public Score(int selectCourseId, float usualScore, float finalScore, float totalScore) {
        this.selectCourseId = selectCourseId; // 与属性名一致
        this.usualScore = usualScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
    }

    // 3. 修改成绩用的构造（含scoreId）
    public  Score(int scoreId, float usualScore, float finalScore, float totalScore, boolean isUpdate) {
        this.scoreId = scoreId; // 与属性名一致
        this.usualScore = usualScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
    }

    // 4. Getter/Setter方法（严格遵循命名规范）
    public int getScoreId() { // 对应scoreId
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getSelectCourseId() { // 对应selectCourseId
        return selectCourseId;
    }

    public void setSelectCourseId(int selectCourseId) {
        this.selectCourseId = selectCourseId;
    }

    public float getUsualScore() { // 对应usualScore
        return usualScore;
    }

    public void setUsualScore(float usualScore) {
        this.usualScore = usualScore;
    }

    public float getFinalScore() { // 对应finalScore
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        this.finalScore = finalScore;
    }

    public float getTotalScore() { // 对应totalScore
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }
}