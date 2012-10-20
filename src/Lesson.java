import org.w3c.dom.Element;

class Lesson {
    private String location;
    private String specialty;
    private String name;
    private String prof;

    private Integer specialtyMask;
    private LessonType type;
    Lesson(Element e) throws Exception {
        location = Parser.getTextValue(e,"location");
        specialty = Parser.getTextValue(e,"spec");
        name = Parser.getTextValue(e,"name");
        prof = Parser.getTextValue(e,"prof");
        specialtyMask = Parser.getMask(specialty);
        System.out.println(location + " " + specialty + " " + name + " " + prof);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecialty() {
        return specialty;
    }

    String[][] getData() {
        String[][] Data = new String[1][3];
        for (int i = 0; i < 3; i++) {
            if (((specialtyMask >> i) & 1) == 1) {
                Data[0][i] = name + " | " + prof + " | " + location;
            } else {
                Data[0][i] = "-";
            }
        }
        return Data;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    static class LessonType {

    }
}
