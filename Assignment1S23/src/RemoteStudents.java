public class RemoteStudents extends Students {
    private double midterm;
    private double finalExam;
    private double assignments;
    private double discussion;

    public RemoteStudents(int id, String name, String type, double midterm, double finalExam, double assignments, double discussion) {
        super(id, name, type);
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.assignments = assignments;
        this.discussion = discussion;
    }

    public double getMidterm() {
        return midterm;
    }

    public void setMidterm(double midterm) {
        this.midterm = midterm;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
    }

    public double getAssignments() {
        return assignments;
    }

    public void setAssignments(double assignments) {
        this.assignments = assignments;
    }

    public double getDiscussion() {
        return discussion;
    }

    public void setDiscussion(double discussion) {
        this.discussion = discussion;
    }

    @Override
    public double score() {
        return (midterm * 0.3) + (finalExam * 0.3) + (assignments * 0.3) + (discussion * 0.1);
    }
}