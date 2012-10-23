import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/** Информация об учебном курсе */
@XmlType(name="course", namespace="http://statmod.ru/courses")
public class CourseInfo {

    private String name;

    private Instructor instructor;

    @XmlAttribute(name="semester")
    private Integer semester;

    @XmlAttribute(name="year")
    private String year;

    @XmlAttribute(name="alias")
    private String alias;

    /** Название */
    public String getName() {
        return name;
    }

    @XmlElement(name="name", namespace="http://statmod.ru/courses")
    public void setName(String name) {
        this.name = name;
    }

    /** Преподаватель */
    public Instructor getInstructor() {
        return instructor;
    }

    /** Имя преподавателя */
    public String getInstructorName() {
        if (instructor == null) {
            return null; // Анонимус!
        }

        return instructor.getName();
    }

    @XmlElement(name="prof", namespace="http://statmod.ru/courses") 
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    /** Семестр, в котором проходит курс */
    public Integer getSemester() {
        return semester;
    }

    /** Идентификатор курса в XML-файлах */
    public String getAlias() {
        return alias;
    }
}
