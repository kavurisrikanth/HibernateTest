package home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
    @NamedQuery(name = "Person_getAll", query = "from Person"),
    @NamedQuery(name = "Person_getByName", query = "from Person p where p.name = :name"),
    @NamedQuery(name = "Person_ageLessThan", query = "from Person p where p.age < :age"),
    @NamedQuery(name = "Person_getById", query = "from Person p where p.id = :id")
})
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    private String name;

    private int age;

    @Override
    public String toString() {
        return name + " - " + age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
