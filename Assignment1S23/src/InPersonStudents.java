public class InPersonStudents extends Students {
    private double midterm;
    private double finalExam;
    private double assignments;

    public InPersonStudents(int id, String name, String type, double midterm, double finalExam, double assignments) {
        super(id, name, type);
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.assignments = assignments;
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

    @Override
    public double score() {
        return (midterm * 0.3) + (finalExam * 0.3) + (assignments * 0.4);
    }
}