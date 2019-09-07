package home;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Accident")
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Accident_getById", query = "from Accident a where a.id = :id")
})
public class Accident {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne
    private Road on;

    @NotNull
    @ManyToOne
    private Person victim;

    @NotNull
    private Date date;
}
