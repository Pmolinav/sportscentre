package net.pmolinav.bookingslib.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;

    @Column(name = "changeType", nullable = false)
    private String changeType;

    @Column(name = "originalEntity", nullable = false)
    private String originalEntity;

    @Column(name = "finalEntity")
    private String finalEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(id, history.id)
                && Objects.equals(creationDate, history.creationDate)
                && Objects.equals(changeType, history.changeType)
                && Objects.equals(originalEntity, history.originalEntity)
                && Objects.equals(finalEntity, history.finalEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, changeType, originalEntity, finalEntity);
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", changeType='" + changeType + '\'' +
                ", originalEntity='" + originalEntity + '\'' +
                ", finalEntity='" + finalEntity + '\'' +
                '}';
    }
}
