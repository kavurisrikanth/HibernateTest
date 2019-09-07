package home;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "Road")
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(name = "Road_getById", query = "from Road r where r.id = :id")
})
public class Road {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    private String address;

    @Override
    public String toString() {
        return address;
    }
}
