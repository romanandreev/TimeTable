import org.w3c.dom.Element;

class Lesson {
    private String location;
    private String specialty;
    private Integer specialtyMask;
    private LessonType type;
    Lesson(Element e) throws Exception {
        location = Parser.getTextValue(e,"location");
        specialty = Parser.getTextValue(e,"spec");
        specialtyMask = Parser.getMask(specialty);
        System.out.println(location + " " + specialty);
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
