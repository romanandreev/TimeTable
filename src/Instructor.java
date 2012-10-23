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

    /** Фамилия */
    public String getName() {
        return name;
    }

    /** Идентификатор преподавателя в XML-файлах */
    public String getId() {
        return id;
    }

    public void fetchNameIfNotSet(Staff staff) {
        if (name != null && !name.equals(""))
            return;
        if (id == null)
            return;
        Person person = staff.getPersonById(id);
        if (person == null)
            return;
        name = person.getLastName();
    }
}
