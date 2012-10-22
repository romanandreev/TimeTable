import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="course")
public class Course {

    private String name;
    private String prof;

    /* Название */
    public String getName() {
        return name;
    }

    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    /* Преподаватель */
    public String getProf() {
        return prof;
    }

    @XmlElement(name="prof")
    public void setProf(String prof) {
        this.prof = prof;
    }
}
