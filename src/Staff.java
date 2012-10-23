import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="staff", namespace="http://statmod.ru/staff")
public class Staff {

    private Map<String, Person> personMap = new HashMap<String, Person>();

    private class PersonList extends LinkedList<Person> implements List<Person> {
        public boolean add(Person p) {
            personMap.put(p.getId(), p);
            return super.add(p);
        }
    }

    @XmlElement(name="person", namespace="http://statmod.ru/staff")
    private List<Person> persons = new PersonList();

    /** Получить сведения о человеке по его идентификатору */
    public Person getPersonById(String personId) {
        return personMap.get(personId);
    }
}
