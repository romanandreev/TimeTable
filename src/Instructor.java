import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlType;

/** Преподаватель */
@XmlType(name="prof")
public class Instructor {
    @XmlAttribute(name="id")
    private String id;
   
    @XmlValue
    private String name;

    /** Ф.И.О. */
    public String getName() {
        return name;
    }

    /** Идентификатор преподавателя в XML-файлах */
    public String getID() {
        return id;
    }
}
