import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="courses", namespace="http://statmod.ru/courses")
public class CourseInfoList {
    private List<CourseInfo> courses = new ArrayList<CourseInfo>();

    @XmlAttribute(name="spec")
    private String specialty;

    @XmlAttribute(name="year")
    private String year;

    /** Специализация */
    public String getSpecialty() {
        return specialty;
    }

    /** Список курсов */
    @XmlElement(name="course", namespace="http://statmod.ru/courses")
    public List<CourseInfo> getCourses() {
        return courses;
    }

    public String getYear() {
        return year;
    }
}
