package home;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "InsurancePayout")
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "InsurancePayout_getById", query = "from InsurancePayout ip where ip.id = :id")
})
public class InsurancePayout {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @NotNull
    @OneToOne
    private Accident accident;

    private double amount;

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }
}
