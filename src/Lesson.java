import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/** 
 * Занятие у конкретной специализации
 */
@XmlType(name="lesson")
class Lesson {
    private String location;
    private String specialty;

    @XmlElement(name="course")
    private Course course;

    private Integer specialtyMask;
    private LessonType type;

    /**
     * Аудитория, в которой проходит занятие
     */
    public String getLocation() {
        return location;
    }

    @XmlElement(name="location")
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Специализация ("all", "sm", "mm", или "sa")
     */
    public String getSpecialty() {
        return specialty;
    }

    String[][] getData() {
        String[][] Data = new String[1][3];
        for (int i = 0; i < 3; i++) {
            if (((specialtyMask >> i) & 1) == 1) {
                if (specialtyMask == 7 && i > 0) {
                    Data[0][i] = "<---";
                } else {
                    Data[0][i] = getCourse().getName() + " | " + 
                                 getCourse().getProf() + " | " + location;
                }
            } else {
                Data[0][i] = "";
            }
        }
        return Data;
    }

    private Integer getMask(String s) {
        if (s.equals("all")) return 1 + 2 + 4;
        if (s.equals("sa")) return 1;
        if (s.equals("sm")) return 2;
        if (s.equals("mm")) return 4;
        return 0;
    }

    @XmlAttribute(name="spec")
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
        specialtyMask = getMask(specialty);
    }

    /**
     * Тип занятия (кафедральное или семинар)
     */
    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    /**
     * Курс, к которому относится данное занятие 
     */
    public Course getCourse() {
        return course;
    }

    @XmlType
    @XmlEnum(String.class)
    static enum LessonType {
        @XmlEnumValue("chair") CHAIR,
        @XmlEnumValue("dep") DEPARTMENT
    }
}
