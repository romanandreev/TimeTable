import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="person", namespace="http://statmod.ru/staff")
public class Person {
    @XmlAttribute(name="id")
    private String id;

    @XmlElement(name="fn", namespace="http://statmod.ru/staff")
    private String firstName;

    @XmlElement(name="mn", namespace="http://statmod.ru/staff")
    private String middleName;

    @XmlElement(name="ln", namespace="http://statmod.ru/staff")
    private String lastName;

    /** Имя */
    public String getFirstName() {
        return firstName;
    }

    /** Фамилия */
    public String getLastName() {
        return lastName;
    }

    /** Отчество */
    public String getMiddleName() {
        return middleName;
    }

    public String getId() {
        return id;
    }
}
